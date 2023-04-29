/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.User;
import java.util.HashSet;
import java.util.Set;
import java.util.prefs.Preferences;



/**
 *
 * @author MSI GF63
 */
public final class SessionManager {
 
    private static SessionManager instance;
 
    private static User user;
   

    private SessionManager(User u ) {
        this.user = u ;
        //this.user.consume(u);   // eli 9rineh 8alet 
    }
 

    public static SessionManager getInstace(User u) {
        if(instance == null) {
            instance = new SessionManager( u );
        }
        return instance;
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public static void setInstance(SessionManager instance) {
        SessionManager.instance = instance;
    }

    
   
    /*
    public static void cleanUserSession() {
    id=0;
     nom="";
     email="";
     roles="";
    }
    */
    
    /*
    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + nom + '\'' +
                "email='" + email + '\'' +
               
                "id = '" + id + '\'' +
           
                ", privileges=" + roles +
                
            '}';
    }
    */
 
    /*
    static class cleanUserSession {
 
        public cleanUserSession() {
     id=0;
     nom="";
     email="";
     roles="";
        }
    }
    */

    public static User getUser() {
        return user;
    }
}