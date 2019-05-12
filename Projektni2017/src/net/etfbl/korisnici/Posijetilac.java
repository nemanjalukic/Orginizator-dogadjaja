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
public class Posijetilac extends Osoba implements Serializable {

    
    private String adresa;
    private String telefon;
    private String email;
    
    public Posijetilac(String ime, String prezime, String adresa, String telefon, String email) {
        super(ime,prezime);
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
    }
    
    public String getAdresa() {
        return adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }
    
    
}
