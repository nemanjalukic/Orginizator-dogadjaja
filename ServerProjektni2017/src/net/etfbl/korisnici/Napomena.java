/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.etfbl.korisnici;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 *
 * @author Office
 */
public class Napomena implements Serializable{
    private GregorianCalendar dat;
    private String napomena;

    public Napomena(GregorianCalendar dat, String napomena) {
        this.dat = dat;
        this.napomena = napomena;
    }

    public GregorianCalendar getDat() {
        return dat;
    }

    public String getNapomena() {
        return napomena;
    }
    public String vratiDatum(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        
        return sdf.format(dat.getTime());
    }
    
    public String toString() {
        return napomena;
    }
    
    
    
}
