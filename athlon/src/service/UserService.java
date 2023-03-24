/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.ConnectionDB;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author k
 */
public class UserService {
    
    
    public void signup(User usr){
        try {
            
       Date datei = new Date();
       SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
       String strdate = formatter.format(datei);
            
            String requete= "INSERT INTO `user` (email, password, nom, prenom, roles , dateins ) VALUES ('"
                + usr.getEmail()+"','"
                + usr.getPassword()+"','"
                + usr.getNom()+"','"
                + usr.getPrenom()+"','"    
                + strdate +"')";
            System.out.print(requete);
            
           
            PreparedStatement pst = new ConnectionDB().getCnx().prepareStatement(requete);

          
            pst.executeUpdate();

            System.out.println("Compte creé ...");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
}
