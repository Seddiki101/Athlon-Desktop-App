/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author k
 */
public class ConnectionDB {
 

    public String url="jdbc:mysql://localhost:3306/athlontest";
    public String login="root";
    public String pwd=""; 
    Connection cnx;
    
        private static int pickedPRoductId;
    
    public static ConnectionDB instance;

    public ConnectionDB() {
        try {
           cnx = DriverManager.getConnection(url ,login ,pwd);
           System.out.println("established connection ");
        }  
        catch (SQLException ex) {
           System.err.print(ex.getMessage());
        }  
    }
    
    
        public Connection getCnx() {
        return cnx;
    }
    
    public static ConnectionDB getInstance(){
        if(instance == null){
        instance = new ConnectionDB();
        }
        return instance;
    }
    
        public static int getPickedPRoductId() {
        return pickedPRoductId;
    }

    public static void setPickedPRoductId(int aPickedPRoductId) {
        pickedPRoductId = aPickedPRoductId;
    }
    
    
    
}
