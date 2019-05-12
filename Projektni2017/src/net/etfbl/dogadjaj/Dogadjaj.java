package net.etfbl.dogadjaj;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import net.etfbl.korisnici.Organizator;
import net.etfbl.korisnici.Ucesnik;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Office
 */
public class Dogadjaj implements Serializable,Comparable<Dogadjaj>{
    private String naziv;
    private GregorianCalendar pocetak;
    private GregorianCalendar kraj;
    private String opis;
    private Organizator organizator;
    private ArrayList<Ucesnik> ucesnici;
    private ArrayList<Dogadjaj> dogadjaji;
    private File fajl;

    
    public Dogadjaj(String naziv, GregorianCalendar pocetak, GregorianCalendar kraj, String opis, Organizator organizator, ArrayList<Ucesnik> ucesnici,ArrayList<Dogadjaj> dogadjaji,File fajl) {
        this.naziv = naziv;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.opis = opis;
        this.organizator = organizator;
        this.ucesnici = ucesnici;
        this.dogadjaji = dogadjaji;
        this.fajl=fajl;
    }

    public File getFajl() {
        return fajl;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getPocetak() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        
        return sdf.format(pocetak.getTime());
    }

    public String getKraj() {
         SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        
        return sdf.format(kraj.getTime());
    }
    
    public String vratiPocetak(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        
        return sdf.format(pocetak.getTime());
    }
    public String getOpis() {
        return opis;
    }

    public Organizator getOrganizator() {
        return organizator;
    }

    public ArrayList<Ucesnik> getUcesnici() {
        return ucesnici;
    }

    public ArrayList<Dogadjaj> getDogadjaji() {
        return dogadjaji;
    }
    
    @Override
    public String toString() {
        return naziv;
    }

    

    @Override
    public int compareTo(Dogadjaj t) {
       return this.pocetak.compareTo(t.pocetak);
    }
}
