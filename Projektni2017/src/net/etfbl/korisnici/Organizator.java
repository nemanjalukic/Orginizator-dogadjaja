package net.etfbl.korisnici;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Office
 */
public class Organizator extends Osoba implements Serializable {
   
    private String telefon;
    private String email;
    private ArrayList<Napomena> napomene;

    public Organizator(String ime, String prezime, String telefon, String email, ArrayList<Napomena> napomene) {
        super(ime,prezime);
        this.telefon = telefon;
        this.email = email;
        this.napomene=napomene;
    }

    public ArrayList<Napomena> getNapomene() {
        return napomene;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }

    
}
