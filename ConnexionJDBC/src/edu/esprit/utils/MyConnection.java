/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Seif
 */
public class MyConnection {
    
    public String url="jdbc:mysql://localhost:3306/athlon";
    public String login="root";
    public String pwd="";
    Connection cnx;
    public static MyConnection instance;
    
    private MyConnection(){
    
        try {
        cnx =  DriverManager.getConnection(url,login,pwd);
            System.out.println("Connection etablie ");
        } catch (SQLException ex) {
           
            System.err.println(ex.getMessage());
        }
    
    }
    public Connection geCnx(){
         return cnx;
    }

    public static MyConnection getInstance(){
        if(instance==null){
        instance = new MyConnection();
 
        }
     return instance;
    
    }
    
    public Object getCnx() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
