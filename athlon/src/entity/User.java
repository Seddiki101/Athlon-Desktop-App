/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 * @author k
 */
public class User {
    int id;
    String email;
    String password;
    String roles;
    String nom;
    String prenom;
    int phone;
    String adres;
    Date dateins;
    
    public static User Current_User;

    public User() {
    }

    public User(int id, String nom, String email, String roles ) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.nom = nom;
    }
    
    
    
    public void consume(User u )
    {
        this.id = u.getId();
        this.email = u.getEmail();
        this.roles = u.getRoles();
        this.nom = u.getNom();
        this.prenom = u.getPrenom();
        this.phone = u.getPhone();
        this.adres = u.getAdres();
        this.dateins = u.getDateins();        
    }

    public User(int id, String email, String password, String roles, String nom, String prenom, int phone, String adres) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.nom = nom;
        this.prenom = prenom;
        this.phone = phone;
        this.adres = adres;    
    }
    
    

    public User(int id, String email, String password, String roles, String nom, String prenom, int phone, String adres, Date dateins) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.nom = nom;
        this.prenom = prenom;
        this.phone = phone;
        this.adres = adres;
        this.dateins = dateins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public Date getDateins() {
        return dateins;
    }

    public void setDateins(Date dateins) {
        this.dateins = dateins;
    }
    
    
      public static User getCurrent_User() {
        return Current_User;
    }

    public static void setCurrent_User(User Current_User) {
        User.Current_User = Current_User;
    }
    
    
    

}
