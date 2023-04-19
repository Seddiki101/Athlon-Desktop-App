/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import javax.mail.MessagingException;
import edu.esprit.entities.Cours;
import edu.esprit.utils.MyConnection;
import java.net.PasswordAuthentication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Transport;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;


public class ServiceCours {

    private Connection con = MyConnection.getInstance().getConnection();

    public void ajouterCrs(Cours cours) throws SQLException, MessagingException {
        String requete = "INSERT INTO cours (nom, description_cours, niveau_cours, capacity, duree_cours) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(requete);
        pst.setString(1, cours.getNom());
        pst.setString(2, cours.getDescription_cours());
        pst.setString(3, cours.getNiveau_cours());
        pst.setInt(4, cours.getCapacity());
        pst.setInt(5, cours.getDuree_cours());
        pst.executeUpdate();
        System.out.println("Cours ajouté avec succès !");
        String destinataire = "fedibenkhlifa81@gmail.com";
        try {
            sendEmail(destinataire, cours);
        } catch (MessagingException ex) {
            Logger.getLogger(ServiceCours.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendEmail(String destinataire, Cours cours) throws MessagingException, MessagingException {
        String username = "contact.fithealth23@gmail.com";
        String password = "qavkrnciihzjmtkp";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(destinataire));
        message.setSubject("Nouveau cours ajouté");
        message.setText("Le cours " + cours.getNom() + " a été ajouté avec succès !\n\n" +
               "Détails du cours :\n" +
               "Description : " + cours.getDescription_cours() + "\n" +
               "Niveau : " + cours.getNiveau_cours() + "\n" +
               "Capacité : " + cours.getCapacity() + "\n" +
               "Durée : " + cours.getDuree_cours() + "\n");
        Transport transport = session.getTransport("smtp");
        transport.connect(username, password);
        Transport.send(message);
        transport.close();

        System.out.println("Le message a été envoyé avec succès à " + destinataire);
    }



    public void updateCrs(Cours cours) throws SQLException {
        String requete = "UPDATE cours SET nom=?, description_cours=?, niveau_cours=?, capacity=?, duree_cours=? WHERE id=?";
        PreparedStatement pst = con.prepareStatement(requete);
        pst.setString(1, cours.getNom());
        pst.setString(2, cours.getDescription_cours());
        pst.setString(3, cours.getNiveau_cours());
        pst.setInt(4, cours.getCapacity());
        pst.setInt(5, cours.getDuree_cours());
        pst.setInt(6, cours.getId());
        pst.executeUpdate();
        System.out.println("Cours modifié avec succès !");
    }

    public void deleteCrs(int id) throws SQLException {
        String requete = "DELETE FROM cours WHERE id=?";
        PreparedStatement pst = con.prepareStatement(requete);
        pst.setInt(1, id);
        pst.executeUpdate();
        System.out.println("Cours supprimé avec succès !");
    }
   

    public ArrayList<Cours> rechercherCrsParNom(String nom) throws SQLException {
    ArrayList<Cours> listeCrs = new ArrayList<>();
    String query = "SELECT * FROM cours WHERE nom LIKE '%" + nom + "%'";
    PreparedStatement ps = con.prepareStatement(query);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        Cours crs = new Cours();
        crs.setId(rs.getInt("id"));
        crs.setNom(rs.getString("nom"));
        crs.setDescription_cours(rs.getString("description_cours"));
        listeCrs.add(crs);
    }
    return listeCrs;
}


    public ArrayList<Cours> afficherCrs() throws SQLException {
        ArrayList<Cours> coursList = new ArrayList<>();
        String requete = "SELECT * FROM cours";
        PreparedStatement pst = con.prepareStatement(requete);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Cours cours = new Cours();
            cours.setId(rs.getInt("id"));
            cours.setNom(rs.getString("nom"));
            cours.setDescription_cours(rs.getString("description_cours"));
            cours.setNiveau_cours(rs.getString("niveau_cours"));
            cours.setCapacity(rs.getInt("capacity"));
            cours.setDuree_cours(rs.getInt("duree_cours"));
            coursList.add(cours);
        }
        return coursList;
    }
}
