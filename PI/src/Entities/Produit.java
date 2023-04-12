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
public class Produit {
    
     private int id;
    private String nom , brand,description,image ;
    private float prix;

    public Produit() {
    }

    public Produit(int id, String nom, String brand, String description, String image, float prix) {
        this.id = id;
        this.nom = nom;
        this.brand = brand;
        this.description = description;
        this.image = image;
        this.prix = prix;
    }

    public Produit(String nom, String brand, String description, String image, float prix) {
        this.nom = nom;
        this.brand = brand;
        this.description = description;
        this.image = image;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public float getPrix() {
        return prix;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom=" + nom + ", brand=" + brand + ", description=" + description + ", image=" + image + ", prix=" + prix + '}';
    }
    
    
    
    
}
