/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author auo1
 */
public class employe {
     int id;
        int cin;
    String nom;
    String prenom;
    float salaire;

    public employe(int id, int cin, String nom, String prenom, float salaire) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.salaire = salaire;
    }

    public int getId() {
        return id;
    }

    public int getCin() {
        return cin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    @Override
    public String toString() {
        return "employe{" + "id=" + id + ", cin=" + cin + ", nom=" + nom + ", prenom=" + prenom + ", salaire=" + salaire + '}';
    }
  
   
   
    
    
}
