/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewemploye;

import entity.employe;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import util.ConnectionDB;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author auo1
 */
public class AjouteremployeController implements Initializable {

    public static ObservableList<employe> getEmployes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Initializes the controller class.
     */
      private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
     @FXML
    private TextField textidemploye;
    @FXML
    private TextField textcinemploye;
    @FXML
    private TextField textnomemploye;
    @FXML
    private TextField textprenomemploye;
    @FXML
    private TextField textsalaireemploye;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       employe emp ;
    }    
    @FXML
    private void createEmploye()
    {
    

             cnx = ConnectionDB.getInstance().getCnx();
            String query="INSERT INTO employe ( cin, nom, prenom, salaire)"
                    + "VALUES (?, ?, ?, ?)";   
            
         //
                     
            try {
                if(textcinemploye.getText().isEmpty() | textnomemploye.getText().isEmpty() | textsalaireemploye.getText().isEmpty())
                {
                  //empty fields
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Athlon :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Entrer all blank fields !!");
                alert.showAndWait();  
                }
                
                else if ( textnomemploye.getText().length() < 3  ){
                //user bs
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Athlon :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Nom  doit etre significative !!");
                alert.showAndWait();  
                }
                
                else{

                    PreparedStatement smt = cnx.prepareStatement(query);
                    
            smt.setString(1, textcinemploye.getText());
            smt.setString(2, textnomemploye.getText());
            smt.setString(3, textprenomemploye.getText());
            smt.setString(4, textsalaireemploye.getText());
            // maybe add condition if null dont send //SessionManager.getUser().getId() // big problem
             //getting current user id // 35 

            //System.out.print("hehe");    
            
            smt.executeUpdate();
             
                System.out.println("ajout avec succee");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Athlon :: BIENVENNUE");
                alert.setHeaderText(null);
                alert.setContentText("Employe Ajouter");
                    String destinataire = "mohamedaymen.ghrab@esprit.tn";
                     String cinText = textcinemploye.getText();
String salaireText = textsalaireemploye.getText();

int cin = Integer.parseInt(cinText);
float salaire = Float.parseFloat(salaireText);
                    employe emp = new employe(0,0,null,null,0,null);
emp.setCin(cin);
emp.setNom(textnomemploye.getText());
emp.setPrenom(textprenomemploye.getText());
emp.setSalaire(salaire);
                     try {
            sendEmail(destinataire,emp);
        } catch (MessagingException ex) {
            Logger.getLogger(employe.class.getName()).log(Level.SEVERE, null, ex);
        }
                alert.showAndWait();
                
                }
                
                
            }catch(SQLException ex){
         System.out.println(ex.getMessage());
            }

         //
            
    }
    
    public void sendEmail(String destinataire, employe emp) throws MessagingException, MessagingException {
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
        message.setSubject("Nouveau employe ajouté");
        message.setText("L Employe " + emp.getNom() + " a été ajouté avec succès !\n\n" +
               "Détails du Employe :\n" +
               "CIN : " + emp.getCin() + "\n" +
               "Nom : " + emp.getNom() + "\n" +
               "Prenom : " + emp.getPrenom() + "\n" +
               "Salaire : " + emp.getSalaire() + "\n");
        Transport transport = session.getTransport("smtp");
        transport.connect(username, password);
        Transport.send(message);
        transport.close();

        System.out.println("Le message a été envoyé avec succès à " + destinataire);
    }
     @FXML
public void updateEmployee() {
    cnx = ConnectionDB.getInstance().getCnx();
    String query = "UPDATE employe SET cin = ?, nom = ?, prenom = ?, salaire = ? WHERE id = ?";

    try {
        if ( textcinemploye.getText().isEmpty() || textnomemploye.getText().isEmpty() || textsalaireemploye.getText().isEmpty()) {
            // empty fields
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Athlon :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.showAndWait();
        } else if (textnomemploye.getText().length() < 3) {
            // name too short
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Athlon :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Name must be at least 3 characters long!");
            alert.showAndWait();
        } else {
            PreparedStatement smt = cnx.prepareStatement(query);

            smt.setString(1, textcinemploye.getText());
            smt.setString(2, textnomemploye.getText());
            smt.setString(3, textprenomemploye.getText());
            smt.setString(4, textsalaireemploye.getText());
            smt.setInt(5, Integer.parseInt(textidemploye.getText()));
           
            int rowsAffected = smt.executeUpdate();

            if (rowsAffected == 1) {
                System.out.println("Employee updated successfully");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Athlon :: Success");
                alert.setHeaderText(null);
                alert.setContentText("Employee updated successfully");
                alert.showAndWait();
            }
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

    public void setEmployeeData(employe employee) {
    if (employee != null) {
        // Set the data of the selected employee in the form fields
        textidemploye.setText(String.valueOf(employee.getId()));
        textcinemploye.setText(String.valueOf(employee.getCin()));
        textnomemploye.setText(employee.getNom());
       textprenomemploye.setText(employee.getPrenom());
       textsalaireemploye.setText(String.valueOf(employee.getSalaire()));
    }
}


    
}
