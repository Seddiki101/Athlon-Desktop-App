/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;
import java.sql.Timestamp;



/**
 *
 * @author Seif
 */
public class Commande {

    private int id_c;
    private Timestamp date;
    private String user;
    private String statue;
    private float remise;

    
    public Commande(){
    
    }
    
    
    public Commande(Timestamp date, String user, String statue, float remise) {
        this.date = date;
        this.user = user;
        this.statue = statue;
        this.remise = remise;
    }

    public Commande(int id_c, Timestamp date, String user, String statue, float remise) {
        this.id_c = id_c;
        this.date = date;
        this.user = user;
        this.statue = statue;
        this.remise = remise;
    }

 
    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public float getRemise() {
        return remise;
    }

    public void setRemise(float remise) {
        this.remise = remise;
    }


    
    
    
    
    
}