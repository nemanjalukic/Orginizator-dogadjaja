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
public class Predavac extends Osoba implements Serializable {
    

    public Predavac(String ime, String prezime) {
       super(ime,prezime);
    }
}
