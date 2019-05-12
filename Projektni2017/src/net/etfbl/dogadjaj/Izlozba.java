/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.etfbl.dogadjaj;

import net.etfbl.dogadjaj.Dogadjaj;
import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import net.etfbl.korisnici.Organizator;
import net.etfbl.korisnici.Ucesnik;

/**
 *
 * @author Office
 */
public class Izlozba extends Dogadjaj {
    
    private String tema;
    private ArrayList<String> autori;
    
    public Izlozba(String tema, ArrayList<String> autori, String naziv, GregorianCalendar pocetak, GregorianCalendar kraj, String opis, Organizator organizator, ArrayList<Ucesnik> ucesnici, ArrayList<Dogadjaj> dogadjaji,File fajl) {
        super(naziv, pocetak, kraj, opis, organizator, ucesnici, dogadjaji,fajl);
        this.tema = tema;
        this.autori = autori;
    }

    public String getTema() {
        return tema;
    }

    public ArrayList<String> getAutori() {
        return autori;
    }
    
    
}
