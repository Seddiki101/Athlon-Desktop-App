/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import Entities.Produit;
import Util.MyDB;
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
public class ServiceProduit implements IService<Produit> {
     Statement Ste;
    Connection cnx ;
    public ServiceProduit(){
        cnx = MyDB.getInstance().getCnx();
    }
    
     @Override
    public void ajouterProduit(Produit t) {
   String qry ="INSERT INTO `produit`(`brand`, `description`, `prix`, `image`, `nom`) VALUES ('"+t.getBrand()+"','"+t.getDescription()+"','"+t.getPrix()+"','"+t.getImage()+"','"+t.getNom()+"')";
        try {
            Statement stm = cnx.createStatement();
       
        stm.executeUpdate(qry);
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
        }
    }
    
    /*@Override
     public  void  supprimerProduit ( int  id ) {
       try {
            Statement stm=cnx.createStatement();
            String query="delete from produit where id = '"+id+"'";
           
            stm.executeUpdate(query);
            
       } catch (SQLException ex) {
           Logger.getLogger(ServiceProduit.class.getName()).log(Level.SEVERE, null, ex);
       }
     }
*/
    @Override
    public List<Produit> afficherProduit() {
    List<Produit> produits = new ArrayList();
        String qry ="SELECT * FROM `produit`";
     try {
         Statement stm = cnx.createStatement();
         ResultSet rs = stm.executeQuery(qry);
         
         while (rs.next()){
             Produit p = new Produit();
             p.setId(rs.getInt("id"));
             p.setBrand(rs.getString("brand"));
              p.setDescription(rs.getString("description"));
               p.setPrix(rs.getFloat("prix"));
           
          
            
                p.setImage(rs.getString("image"));
                  p.setNom(rs.getString("nom"));
          
             produits.add(p);
         }
                 
         return produits;
     } catch (SQLException ex) {
         System.out.println(ex.getMessage());
     }
    
    return produits;
    }
/*
    @Override
    public void modifierProduit (int id, String nom, String brand,String description, int prix, String image){
         String requete="UPDATE produit SET nom='"+nom+"', brand='"+brand+"', description='"+description+"',prix='"+prix+"',image='"+image+"'  WHERE id='"+id+"'";
         
         
         try{
            
             Ste = cnx.createStatement();
            Ste.executeUpdate(requete);
            System.out.println("Produit modifié");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

   
   */

    @Override
    public boolean modifierProduit(Produit t) {
   try {
            String qry = "UPDATE `produit` SET `brand` = '" + t.getBrand() + "', `description` = '" + t.getDescription() + "',`prix` = '" + t.getPrix() + "', `image` = '" + t.getImage() + "', `nom` = '" + t.getNom() + "' WHERE `id` = '" + t.getId() + "'";
           Statement stm = cnx.createStatement();

            stm.executeUpdate(qry);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }  }
    

    @Override
    public void supprimerProduit(Produit t) {
        String qry = "DELETE FROM produit WHERE id=?";
    try {
        PreparedStatement pstmt = cnx.prepareStatement(qry);
        pstmt.setInt(1, t.getId());
        pstmt.executeUpdate();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }    }
    
    
    
    
}
