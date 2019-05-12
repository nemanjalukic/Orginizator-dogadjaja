package serverprojektni2017;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.etfbl.dogadjaj.Dogadjaj;
import net.etfbl.dogadjaj.Izlozba;
import net.etfbl.dogadjaj.Koncert;
import net.etfbl.dogadjaj.Predavanje;
import net.etfbl.dogadjaj.Promocija;
import net.etfbl.korisnici.Organizator;
import net.etfbl.korisnici.Posijetilac;
import net.etfbl.korisnici.Predavac;
import net.etfbl.korisnici.Ucesnik;
import static serverprojektni2017.ServerProjektni2017.autori;
import static serverprojektni2017.ServerProjektni2017.broadcast;
import static serverprojektni2017.ServerProjektni2017.dogadjaji;
import static serverprojektni2017.ServerProjektni2017.izvodjaci;
import static serverprojektni2017.ServerProjektni2017.organizatori;
import static serverprojektni2017.ServerProjektni2017.posijetioci;
import static serverprojektni2017.ServerProjektni2017.predavaci;
import static serverprojektni2017.ServerProjektni2017.prijavljeni;
import static serverprojektni2017.ServerProjektni2017.ucesnici;


    public class ServerThread extends Thread {

    public ServerThread(Socket sock,Socket sock2, int value) {
        this.sock = sock;
        this.sock2 = sock2;
        this.value = value;
        try {
            oos = new ObjectOutputStream(sock.getOutputStream());
            oos2 = new ObjectOutputStream(sock2.getOutputStream());
            ois = new ObjectInputStream(sock.getInputStream());
            broadcast.add(oos2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        start();
    }
    @Override
     public void run() {

        try {
            
            String login = (String) ois.readObject();
            String[] loginElements = login.split("#");
            
                if (checkLogin(loginElements[0], loginElements[1]) && !isUserLogged(loginElements[0], loginElements[1]))
                {
                
                    oos.writeObject("OK");
                    oos.flush();
                    prijavljeni.add(nadjiPosijetioca(loginElements[0]));
                    oos.writeUnshared(nadjiPosijetioca(loginElements[0]));
                    oos.flush();
                    
                    
                String option = "";
                while (!"END".equals(option)) {
                    
                    option = (String) ois.readObject(); 
                    if(option.equals("pretraga")){
                        Collections.sort(dogadjaji);
                        oos.writeUnshared(dogadjaji);
                        oos.flush();
                    }
                    else if(option.equals("izbrisi")){
                        
                        String ime=(String) ois.readObject();
                        Dogadjaj pom=null;
                        for(Dogadjaj d: dogadjaji)
                        {
                            if(d.getNaziv().equals(ime))
                                pom=d;
                               
                        }
                        dogadjaji.remove(pom);
                        Collections.sort(dogadjaji);
                        oos.writeUnshared(dogadjaji);
                        oos.flush();
                    }
                    else if(option.equals("podaci")){
                        oos.writeUnshared(dogadjaji);
                        oos.flush();
                        oos.writeUnshared(organizatori);
                        oos.flush();
                        oos.writeUnshared(ucesnici);
                        oos.flush();
                        oos.writeUnshared(predavaci);
                        oos.flush();
                        oos.writeUnshared(autori);
                        oos.flush();
                        oos.writeUnshared(izvodjaci);
                        oos.flush();
                    }
                    else if(option.equals("novidogadjaj"))
                    {   
                        String por=(String) ois.readObject();
                        if(checkEvenst(por))
                        {
                        oos.writeObject("ok");
                        oos.flush();
                        String k= (String) ois.readObject();
                        if(k.equals("koncert"))
                        {
                            Koncert kon=(Koncert) ois.readObject();
                            dogadjaji.add(kon);
                        }
                        else if(k.equals("promocija"))
                        {
                            Promocija prom=(Promocija) ois.readObject();
                            dogadjaji.add(prom);
                        }
                        else if(k.equals("predavanje"))
                        {
                            Predavanje pred=(Predavanje) ois.readObject();
                            dogadjaji.add(pred);
                        }
                        else if(k.equals("izlozba"))
                        {
                            Izlozba iz=(Izlozba) ois.readObject();
                            dogadjaji.add(iz);
                        }
                        else if(k.equals("ostalo"))
                        {
                            Dogadjaj kon=(Dogadjaj) ois.readObject();
                            dogadjaji.add(kon);
                        }
                    }
                    else{
                            oos.writeObject("Dogadjaj sa ovim imenom već postoji");
                            oos.flush();
                        }
                    }
                    else if(option.equals("marketing")){
                        
                        String ime=(String) ois.readObject();
                        for(Dogadjaj d: dogadjaji)
                        {   if (d.getNaziv().equals(ime)){
                                if (d instanceof Promocija || d instanceof Koncert) {
                                    oos.writeBoolean(true);
                                    oos.flush();
                                    MarketingTask task1 = new MarketingTask(d);
                                    new Timer().schedule(task1, 0);
                                }
                                else oos.writeBoolean(false);
                                oos.flush();
                               
                            }
                        }
                        
                        
                    }
                    else if(option.equals("dodajpodaci")){
                       oos.writeUnshared(organizatori);
                        oos.flush();
                        oos.writeUnshared(ucesnici);
                        oos.flush();
                        oos.writeUnshared(predavaci);
                        oos.flush();
                        
                    }
                    else if(option.startsWith("Dodaj")){
                       if(option.split("#")[1].equals("organizator")){
                           if(checkOrganizator(option.split("#")[2],option.split("#")[3],option.split("#")[4],option.split("#")[5])){
                                oos.writeBoolean(true);
                                oos.flush();
                                Organizator organizator =(Organizator)ois.readObject();
                                organizatori.add(organizator);
                           }
                           else{
                               oos.writeBoolean(false);
                               oos.flush();
                           }
                          
                       }
                       else if(option.split("#")[1].equals("ucesnik")){
                           if(checkUcesnik(option.split("#")[2],option.split("#")[3],option.split("#")[4])){
                                oos.writeBoolean(true);
                                oos.flush();
                                Ucesnik ucesnik =(Ucesnik)ois.readObject();
                                ucesnici.add(ucesnik);
                           }
                           else{
                               oos.writeBoolean(false);
                               oos.flush();
                           }
                       }
                       else if(option.split("#")[1].equals("predavac")){
                           if(checkPredavac(option.split("#")[2],option.split("#")[3])){
                                oos.writeBoolean(true);
                                oos.flush();
                                Predavac predavac =(Predavac)ois.readObject();
                                predavaci.add(predavac);
                                
                           }
                           else{
                               oos.writeBoolean(false);
                               oos.flush();
                           }
                           
                       }
                        
                    }
                    else if(option.startsWith("Izmijeni")){
                        if(option.split("#")[1].equals("organizator")){
                            Organizator organizator = (Organizator) ois.readObject();
                            if(ois.readBoolean()){
                                if(!checkOrganizator(organizator.getIme(),organizator.getPrezime(),organizator.getTelefon(),organizator.getEmail())){
                                    Organizator org = (Organizator) ois.readObject();
                                    izbrisiOrganizator(organizator);
                                    organizatori.add(org);
                                }
                                else{
                                    Organizator org = (Organizator) ois.readObject();
                                    organizatori.add(org);
                                }
                            }
                            
                        }
                        else if(option.split("#")[1].equals("ucesnik")){
                            Ucesnik ucesnik = (Ucesnik) ois.readObject();
                            if(ois.readBoolean()){
                                if(!checkUcesnik(ucesnik.getIme(),ucesnik.getPrezime(),ucesnik.getOrganizacija())){
                                    Ucesnik uce = (Ucesnik) ois.readObject();
                                    izbrisiUcesnik(ucesnik);
                                    ucesnici.add(uce);

                                }
                                else{
                                    Ucesnik uce = (Ucesnik) ois.readObject();
                                    ucesnici.add(uce);
                                }
                            }
                        }
                        else if(option.split("#")[1].equals("predavac")){
                            Predavac predavac = (Predavac) ois.readObject();
                            if(ois.readBoolean()){
                                if(!checkPredavac(predavac.getIme(),predavac.getPrezime()))
                                {
                                    Predavac pred = (Predavac) ois.readObject();
                                    izbrisiPredavac(predavac);
                                    predavaci.add(pred);
                                }
                                else{
                                    Predavac pred = (Predavac) ois.readObject();
                                    predavaci.add(pred);
                                }
                            }
                            
                        }
                    }
                    else if(option.startsWith("Izbrisi"))
                    {
                        if(option.split("#")[1].equals("organizator")){
                            Organizator organizator = (Organizator) ois.readObject();
                            izbrisiOrganizator(organizator);
                            
                        }
                        else if(option.split("#")[1].equals("ucesnik")){
                            Ucesnik ucesnik = (Ucesnik) ois.readObject();
                            izbrisiUcesnik(ucesnik);
                            
                        }
                        else if(option.split("#")[1].equals("predavac")){
                            Predavac predavac = (Predavac) ois.readObject();
                            izbrisiPredavac(predavac);
                            
                        }
                    }
                }
                prijavljeni.remove(nadjiPosijetioca(loginElements[0]));
                }
                 else if(isUserLogged(loginElements[0], loginElements[1])){
                oos.writeObject("Greška već ste prijavljeni");
                oos.flush();
            }
            else{
                oos.writeObject("Greška pokušajte ponovo");
                oos.flush();
            }
                
            oos.close();
            ois.close();
            sock.close();
            broadcast.remove(oos2);
            oos2.writeBoolean(false);
            oos2.flush();
            oos2.close();
            sock2.close();

            
            System.out.println("[Client " + value + "] se odjavio");
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
     
        private synchronized boolean checkLogin(String username, String password) {
        try {
            BufferedReader in = new BufferedReader(new FileReader("./users.txt"));
            String s;
            while ((s = in.readLine()) != null) {
                if (s.startsWith(username)) {
                    String u = s.split(",")[0];
                    String p = s.split(",")[1];
                    if (u.equals(username) && p.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    private synchronized Posijetilac nadjiPosijetioca(String posijetilac) {
        for (Posijetilac k : posijetioci) {
            if (posijetilac.equals(k.getIme())) {
                return k;
            }
        }
        return null;
    }
    private boolean checkOrganizator(String ime,String prezime,String tel,String email) {
        boolean pom=true;
        for(Organizator or: organizatori)
        {
            if(or.getIme().equals(ime)&& or.getPrezime().equals(prezime) && or.getTelefon().equals(tel) && or.getEmail().equals(email))
                pom=false;
        }
        return pom;
    } 
    private void izbrisiOrganizator(Organizator org) {
        Organizator pom=null;
        for(Organizator or: organizatori)
        {
            if(or.getIme().equals(org.getIme()) && or.getPrezime().equals(org.getPrezime()) && or.getTelefon().equals(org.getTelefon()) && or.getEmail().equals(org.getEmail()) && org.getNapomene().equals(org.getNapomene()))
                pom=or;
        }
        organizatori.remove(pom);

    }
    private void izbrisiUcesnik(Ucesnik org) {
        Ucesnik pom=null;
        for(Ucesnik or: ucesnici)
        {
            if(or.getIme().equals(org.getIme()) && or.getPrezime().equals(org.getPrezime()) && or.getOrganizacija().equals(org.getOrganizacija()))
                pom=or;
        }
        ucesnici.remove(pom);

    }
    private void izbrisiPredavac(Predavac org) {
        Predavac pom=null;
        for(Predavac or: predavaci)
        {
            if(or.getIme().equals(org.getIme()) && or.getPrezime().equals(org.getPrezime()) )
                pom=or;
        }
        predavaci.remove(pom);

    }
      private boolean checkUcesnik(String ime,String prezime,String organizacija) {
        boolean pom=true;
        for(Ucesnik or: ucesnici)
        {
            if(or.getIme().equals(ime)&& or.getPrezime().equals(prezime) && or.getOrganizacija().equals(organizacija))
                pom=false;
        }
        return pom;
    }
        private boolean checkPredavac(String ime,String prezime) {
        boolean pom=true;
        for(Predavac or: predavaci)
        {
            if(or.getIme().equals(ime)&& or.getPrezime().equals(prezime))
                pom=false;
        }
        return pom;
    }
     private synchronized boolean isUserLogged(String username, String password) {
        if(checkLogin(username,password)){
            for(Posijetilac k: prijavljeni){
                if (k.getIme().equals(username)) {
                    return true;
                }
            }
       
        return false;}
        else{
            return false;
        }
    }
    private boolean checkEvenst(String name){
        boolean b=true;
        for(Dogadjaj d:dogadjaji)   
        {
            if(d.getNaziv().equals(name))
            {
                b=false;
            }
        }
        return b;
    }    
       
    private Socket sock;
    private Socket sock2;
    private int value;
    private ObjectOutputStream oos;
    private ObjectOutputStream oos2;
    private ObjectInputStream ois;
}
