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
    String etat;
 

   
  



    public employe(int id, int cin, String nom, String prenom, float salaire, String etat ) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.salaire = salaire;
          this.etat = etat ; 
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getEtat() {
        return etat;
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
