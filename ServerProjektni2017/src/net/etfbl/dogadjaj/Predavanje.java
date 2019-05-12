/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.etfbl.dogadjaj;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import net.etfbl.korisnici.Organizator;
import net.etfbl.korisnici.Predavac;
import net.etfbl.korisnici.Ucesnik;

/**
 *
 * @author Office
 */
public class Predavanje extends Dogadjaj{

    private String tema;
    private Predavac predavac;
    private String sadrzaj;
      
    public Predavanje(String tema, Predavac predavac, String sadrzaj, String naziv, GregorianCalendar pocetak, GregorianCalendar kraj, String opis, Organizator organizator, ArrayList<Ucesnik> ucesnici,ArrayList<Dogadjaj> dogadjaji,File fajl) {
        super(naziv, pocetak, kraj, opis, organizator, ucesnici,dogadjaji,fajl);
        this.tema = tema;
        this.predavac = predavac;
        this.sadrzaj = sadrzaj;
    }
    
}
