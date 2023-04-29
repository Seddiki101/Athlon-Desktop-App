/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author msi
 */
public class Produit {

    private int id, idCategory, likes;
    private String nom, brand, description, image, nomCategory;
    private float prix;
    private float moyRating;

    public float getMoyRating() {
        return moyRating;
    }

    public void setMoyRating(float moyRating) {
        this.moyRating = moyRating;
    }
    
    

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

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

    public Produit(int idCategory, String nom, String brand, String description, String image, String nomCategory, float prix) {
        this.idCategory = idCategory;
        this.nom = nom;
        this.brand = brand;
        this.description = description;
        this.image = image;
        this.nomCategory = nomCategory;
        this.prix = prix;
    }

    public Produit(int aInt, String string, double aDouble) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public void setNomCategory(String nomCategory) {
        this.nomCategory = nomCategory;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public String getNomCategory() {
        return nomCategory;
    }

    public Produit(String image) {
        this.image = image;
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

    // @Override
    // public String toString() {
    //   return "Produit{" + "id=" + id + ", nom=" + nom + ", brand=" + brand + ", description=" + description + ", image=" + image + ", prix=" + prix + '}';
    // }
    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", idCategory=" + idCategory + ", nom=" + nom + ", brand=" + brand + ", description=" + description + ", image=" + image + ", nomCategory=" + nomCategory + ", prix=" + prix + '}';
    }

}
