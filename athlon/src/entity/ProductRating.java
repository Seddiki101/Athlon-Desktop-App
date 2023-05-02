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
public class ProductRating {
    int id_rating,id_produit;
     private String userName;
    private float rating;


    public ProductRating(int id_rating, int id_produit, String userName, float rating) {
        this.id_rating = id_rating;
        this.id_produit = id_produit;
        this.userName = userName;
        this.rating = rating;
    }

    public int getId_rating() {
        return id_rating;
    }

    public void setId_rating(int id_rating) {
        this.id_rating = id_rating;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }
   
    public ProductRating(String userName, float rating) {
        this.userName = userName;
        this.rating = rating;
    }

    public ProductRating() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
    
    
}
