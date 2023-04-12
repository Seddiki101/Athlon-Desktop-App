/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author msi
 */
public class Categorie {
     private int id;
    private String nom , image ;

    public Categorie() {
    }

    public Categorie(int id, String nom, String image) {
        this.id = id;
        this.nom = nom;
        this.image = image;
    }

    public Categorie(String nom, String image) {
        this.nom = nom;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Categorie{" + "id=" + id + ", nom=" + nom + ", image=" + image + '}';
    }
    
    
    
}
