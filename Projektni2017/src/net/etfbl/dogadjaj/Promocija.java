/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.etfbl.dogadjaj;

import net.etfbl.dogadjaj.Dogadjaj;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import net.etfbl.korisnici.Organizator;
import net.etfbl.korisnici.Ucesnik;

/**
 *
 * @author Office
 */
public class Promocija extends Dogadjaj {
    private String proizvodKompanija;

    public Promocija(String proizvodKompanija, String naziv, GregorianCalendar pocetak, GregorianCalendar kraj, String opis, Organizator organizator, ArrayList<Ucesnik> ucesnici,ArrayList<Dogadjaj> dogadjaji,File fajl) {
        super(naziv, pocetak, kraj, opis, organizator, ucesnici,dogadjaji,fajl);
        this.proizvodKompanija = proizvodKompanija;
    }
    
    
}
