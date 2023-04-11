/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.tests;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import edu.esprit.entities.Commande;
import edu.esprit.services.CommandeCRUD;
import edu.esprit.utils.MyConnection;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Seif
 */
public class MainClass {
    public static void main(String[] args) {
     //  MyConnection mc = new MyConnection(); 
    CommandeCRUD pcd = new CommandeCRUD();
   //  Commande c2= new Commande(new Timestamp(1680370025),"3","pending",20);
    // pcd.ajouterCommande2(c2); 
        System.out.println(pcd.afficherCommande());
