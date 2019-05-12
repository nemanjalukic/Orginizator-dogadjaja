/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.etfbl.korisnici;

import java.io.Serializable;

/**
 *
 * @author Office
 */
public class Osoba implements Serializable{
    private String ime;
    private String prezime;
    
     public Osoba(String ime, String prezime) {
        this.ime = ime;
        this.prezime = prezime;
    }
    
    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }
    
}
