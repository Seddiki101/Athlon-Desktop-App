/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.ProductRating;
import Entities.Produit;
import Util.MyDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author msi
 */
public class ServiceRating {
      Statement Ste;
    Connection cnx ;
    public ServiceRating(){
        cnx = MyDB.getInstance().getCnx();
    }
    
    
     public void ajouterRating(ProductRating p) {
   String qry = "INSERT INTO `ratiings`(`userName`, `rating`) VALUES ('"+p.getUserName()+"','"+p.getRating()+"')";
        try {
            Statement stm = cnx.createStatement();
       
        stm.executeUpdate(qry);
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
        }
    }
}
