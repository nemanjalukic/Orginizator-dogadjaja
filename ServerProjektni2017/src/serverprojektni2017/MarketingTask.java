/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverprojektni2017;

import com.sun.corba.se.spi.activation.Server;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.etfbl.dogadjaj.Dogadjaj;
import net.etfbl.korisnici.Posijetilac;
import static serverprojektni2017.ServerProjektni2017.broadcast;
import static serverprojektni2017.ServerProjektni2017.posijetioci;
import static serverprojektni2017.ServerProjektni2017.prijavljeni;

/**
 *
 * @author Office
 */
public class MarketingTask extends TimerTask {
    
    private Dogadjaj dog;
    public MarketingTask(Dogadjaj dog) {
        super();
        this.dog=dog;
    }

    @Override
    public void run() {
            for(Posijetilac pos :prijavljeni){    
            PrintWriter writer = null;
                try {
                    File theDir = new File(dog.getNaziv());
                    theDir.mkdir();
                    writer = new PrintWriter("./" + dog.getNaziv() + "/" + pos.getIme()+".txt", "UTF-8");
                        writer.println(pos.getIme()+ " "+pos.getPrezime()+ "," + dog.vratiPocetak() + "," + dog.getNaziv()+","+dog.getOpis());
                    
                } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                } finally {
                    writer.close();
                }}
            for(ObjectOutputStream oo:broadcast){
            try {
            oo.writeBoolean(true);
            oo.flush();
            oo.writeObject("Posijetite "+dog.getNaziv());
            } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    }
};
