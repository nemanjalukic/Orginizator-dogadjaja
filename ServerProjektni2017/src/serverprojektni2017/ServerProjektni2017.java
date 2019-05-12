/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverprojektni2017;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import net.etfbl.korisnici.Ucesnik;
import net.etfbl.korisnici.Organizator;
import net.etfbl.dogadjaj.Dogadjaj;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Timer;
import net.etfbl.korisnici.Napomena;
import net.etfbl.korisnici.Posijetilac;
import net.etfbl.korisnici.Predavac;

/**
 *
 * @author Office
 */
public class ServerProjektni2017 {

    public static final int TCP_PORT = 9000;
    public static ArrayList<Dogadjaj> dogadjaji=new ArrayList<>();
    public static ArrayList<Organizator> organizatori=new ArrayList<>();
    public static ArrayList<Ucesnik> ucesnici=new ArrayList<>();
    public static ArrayList<Predavac> predavaci= new ArrayList<>();
    public static ArrayList<String> autori= new ArrayList<>();
    public static ArrayList<String> izvodjaci= new ArrayList<>();
    public static ArrayList<Posijetilac> posijetioci=new ArrayList();
    public static ArrayList<Posijetilac> prijavljeni=new ArrayList();
    
    public static ServerSocket ss1;
    public static ArrayList<ObjectOutputStream> broadcast=new ArrayList<>();
     public static void init() {
     ArrayList<Ucesnik> uce= new ArrayList<Ucesnik>();
        uce.add(new Ucesnik("kk","bb","dd"));
        uce.add(new Ucesnik("kk1","bb1","dd1"));
        ucesnici.addAll(uce);
        Ucesnik uc1=new Ucesnik("kk11","bb12","dd11");
        ucesnici.add(uc1);
        Napomena nap=new Napomena(new GregorianCalendar(2017,0,28),"nesto");
        Napomena nap1=new Napomena(new GregorianCalendar(2017,0,21),"nesto");
        ArrayList<Napomena> napomene=new ArrayList<>();
        napomene.add(nap);
        napomene.add(nap1);
        Organizator org= new Organizator("kk1","bb1","dd1","kkl",napomene);
        Organizator org1= new Organizator("kk11","bb11","dd11","kkl1",napomene);
        Dogadjaj dog= new Dogadjaj("naziv",new GregorianCalendar(2017,0,28,13,24),new  GregorianCalendar(2017,1,28,13,24),"kk",org,uce,null,null);
        Dogadjaj dog1= new Dogadjaj("naziv1",new GregorianCalendar(2017,0,27,13,24),new  GregorianCalendar(2017,1,28,13,24),"kk",org,uce,null,null);
        dogadjaji.add(dog);
        dogadjaji.add(dog1);
        organizatori.add(org);
        organizatori.add(org1);
        predavaci.add(new Predavac("nemanja","lukic"));
        predavaci.add(new Predavac("petar","petrovic"));
        autori.add("neko");
        autori.add("drugi autor");
        izvodjaci.add("prvi izvodjac");
        izvodjaci.add("drugi izvodjac");
        try {
            BufferedReader in = new BufferedReader(new FileReader("./users.txt"));
            String s;
            while ((s = in.readLine()) != null) {
                posijetioci.add(new Posijetilac(s.split(",")[0],s.split(",")[2],s.split(",")[3],s.split(",")[4],s.split(",")[5]));
                }
            }
         catch (IOException ex) {
            ex.printStackTrace();
        }
        
        }
     
    public static void main(String[] args) {
          init();
         try {
            int clientCounter = 0;
           
            ServerSocket ss = new ServerSocket(TCP_PORT);
            ss1 = new ServerSocket(TCP_PORT+1);
            ServerSocket ss2 = new ServerSocket(TCP_PORT+2);
            System.out.println("Server running...");
            while (true) {
                
                Socket sock = ss.accept();
                Socket sock2 = ss2.accept();
                System.out.println("Client accepted: "
                        + (++clientCounter));
               
                ServerThread st = new ServerThread(sock,sock2, clientCounter);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void dodajKorisnike(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("./users.txt"));
            String s;
            while ((s = in.readLine()) != null) {
                posijetioci.add(new Posijetilac(s.split(",")[0],s.split(",")[2],s.split(",")[3],s.split(",")[4],s.split(",")[5]));
                }
            }
         catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
