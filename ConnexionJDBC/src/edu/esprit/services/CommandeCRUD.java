/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services; 
import edu.esprit.entities.Commande;
import edu.esprit.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Seif
 */
public class CommandeCRUD {
    Connection cnx2;
    public CommandeCRUD(){
        cnx2=MyConnection.getInstance().geCnx();
    }
    
    public void ajouterCommande(){
        try {
            String requete="INSERT INTO commande (date,user,statue,remise)"
                    + "VALUES ('2023-04-01','3','seif','10')";
            Statement st = cnx2.createStatement();
            st.executeUpdate(requete);
            System.out.println("Commande Ajoutée avec succes "); 
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
    }
    
    public void ajouterCommande2(Commande C){
        try {
            String requete2= "INSERT INTO commande (date,user,statue,remise)"
                    +"Values (?,?,?,?)";
            
            PreparedStatement pst =cnx2.prepareStatement(requete2);
            pst.setTimestamp(1,C.getDate());
            pst.setString(2,C.getUser());
            pst.setString(3,C.getStatue());
            pst.setFloat(4,C.getRemise());
            pst.executeUpdate();
            System.out.println("Votre commande est ajoutée");
                    
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
      
  
    }
    public List<Commande> afficherCommande(){
        List<Commande> myList= new ArrayList<>();
        try {
            
            String requete3="SELECT * FROM commande";
            Statement st = cnx2.createStatement();
            ResultSet rs = st.executeQuery(requete3);
            while(rs.next()){
               Commande c = new Commande();
               c.setId_c(rs.getInt(1));
               c.setDate(rs.getTimestamp("date"));
               c.setUser(rs.getString("user"));
               c.setStatue(rs.getString("statue"));
               c.setRemise(rs.getFloat("remise"));
               myList.add(c);
            
            
            }
            
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return myList;
    
    }
    public void modifierCommande (int id_c, String nom, Date date,String user, String status, float remise) throws SQLException{
         String requete="UPDATE commande SET nom='"+nom+"', date='"+date+"', user='"+user+"',status='"+status+"',remise='"+remise+"'  WHERE id_c='"+id_c+"'";
         
         
         
            
           Statement st = cnx2.createStatement();
            st.executeUpdate(requete);
            System.out.println("Commande modifié");
        
     }
    
    
     public  void  supprimerCommande ( int  id ) {
       try {
            Statement stm=cnx2.createStatement();
            String query="delete from commande where id_c='"+id_c+"'";
           
            stm.executeUpdate(query);
            
       } catch (SQLException ex) {
           Logger.getLogger(CommandeCRUD.class.getName()).log(Level.SEVERE, null, ex);
       }
     }

    }
    


