/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entity.Categorie;
import entity.Produit;


import util.ConnectionDB;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author msi
 */
public class ServiceCategorie implements ICService<Categorie> {
    
     private Statement Ste;
    Connection cnx ;
    public ServiceCategorie(){
        cnx = ConnectionDB.getInstance().getCnx();
    }
    
      @Override
    public void ajouterCategorie(Categorie t) {
   String qry ="INSERT INTO `categorie`(`nom`,`image`) VALUES ('"+t.getNom()+"','"+t.getImage()+"')";
        try {
            Statement stm = cnx.createStatement();
       
        stm.executeUpdate(qry);
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
        }
    }
    
    @Override
     public  void  supprimerCategorie ( Categorie t ) {
       String qry = "DELETE FROM categorie WHERE id=?";
    try {
        PreparedStatement pstmt = cnx.prepareStatement(qry);
        pstmt.setInt(1, t.getId());
        pstmt.executeUpdate();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }    }

    @Override
    public List<Categorie> afficherCategorie() {
    List<Categorie> categories = new ArrayList();
        String qry ="SELECT * FROM `categorie`";
     try {
         Statement stm = cnx.createStatement();
         ResultSet rs = stm.executeQuery(qry);
         
         while (rs.next()){
             Categorie p = new Categorie();
             p.setId(rs.getInt(1));
             p.setNom(rs.getString("nom"));
             
                p.setImage(rs.getString(3));
            
             categories.add(p);
         }
                 
         return categories;
     } catch (SQLException ex) {
         System.out.println(ex.getMessage());
     }
    
    return categories;
    }

    @Override
    public boolean modifierCategorie(Categorie t) {
        try {
            String qry = "UPDATE `categorie` SET `nom` = '" + t.getNom() + "', `image` = '" + t.getImage() +  "' WHERE `id` = '" + t.getId() + "'";
           Statement stm = cnx.createStatement();

            stm.executeUpdate(qry);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }  }
    

   
    
    
    
}


