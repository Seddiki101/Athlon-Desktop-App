/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.tests;

import edu.esprit.entities.Cours;
import edu.esprit.entities.Exercices;
import edu.esprit.services.ServiceCours;
import edu.esprit.services.ServiceExercices;
import edu.esprit.utils.MyConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import edu.esprit.entities.Exercices;
import edu.esprit.services.ServiceExercices;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.mail.MessagingException;

public class MainClass {
    public static void main(String[] args) throws SQLException, MessagingException {
        ServiceCours sc = new ServiceCours();
        
  /*      
Exercices exercice1 = new Exercices();
    exercice1.setId_cours(42);
    exercice1.setNom("Exercice 1");
    exercice1.setDuree_exercices(30);
    exercice1.setNombre_repetitions(10);
    exercice1.setDesc_exercices("Description de l'exercice 1");
    exercice1.setMachine("Machine 1");
    try {
        service.ajouterEX(exercice1);
    } catch (SQLException e) {
        e.printStackTrace();
    }        service.ajouterEX(ex1);
        System.out.println("Exercice ajouté avec succès.");
        */
        // Mettre à jour un exercice
        // Modifier un exercice
    // Ajouter un article
        Cours c1 = new Cours("football", "jouer football", "moyen",22,90);
        try {
            sc.ajouterCrs(c1);
            System.out.println("Cours ajouté avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    } }