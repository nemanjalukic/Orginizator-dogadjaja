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
public class Ucesnik extends Osoba implements Serializable{
    
    private String organizacija;

    public Ucesnik(String ime, String prezime, String organizacija) {
        super(ime,prezime);
        this.organizacija = organizacija;
    }

    public String getOrganizacija() {
        return organizacija;
    }

    @Override
    public String toString() {
        return getIme() + " " + getPrezime() + "-" + organizacija;
    }
    
    
    
}
