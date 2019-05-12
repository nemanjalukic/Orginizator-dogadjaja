/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.etfbl.dogadjaj;

import java.io.File;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import net.etfbl.korisnici.Organizator;
import net.etfbl.korisnici.Ucesnik;

/**
 *
 * @author Office
 */
public class Koncert extends Dogadjaj {
    
    private ArrayList<String> izvodjaci;
    private LocalTime trajanje;

    public Koncert(ArrayList<String> izvodjaci, LocalTime trajanje, String naziv, GregorianCalendar pocetak, GregorianCalendar kraj, String opis, Organizator organizator, ArrayList<Ucesnik> ucesnici, ArrayList<Dogadjaj> dogadjaji,File fajl) {
        super(naziv, pocetak, kraj, opis, organizator, ucesnici, dogadjaji,fajl);
        this.izvodjaci = izvodjaci;
        this.trajanje = trajanje;
    }

    public ArrayList<String> getIzvodjaci() {
        return izvodjaci;
    }

    public LocalTime getTrajanje() {
        return trajanje;
    }

    
    
}
