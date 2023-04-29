/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author MSI
 */
public class ProduitLike {
    int id , idproduit ;

    public ProduitLike() {
    }

    public ProduitLike(int id, int idproduit) {
        this.id = id;
        this.idproduit = idproduit;
       
    }

   

    public int getId() {
        return id;
    }

    public int getIdproduit() {
        return idproduit;
    }

  

    public void setId(int id) {
        this.id = id;
    }

    public void setIdproduit(int idpost) {
        this.idproduit = idproduit;
    }

    
    
}