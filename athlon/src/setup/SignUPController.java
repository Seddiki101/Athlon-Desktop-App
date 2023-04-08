/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.mail.MessagingException;
import util.ConnectionDB;
import util.HelperV2;
import util.PwdHasher;

/**
 * FXML Controller class
 *
 * @author k
 */
public class SignUPController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Button btnSignup;
    
    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void crateAcc(){
        cnx = ConnectionDB.getInstance().getCnx();
            String query="INSERT INTO user ( nom, prenom, email, password, dateins)"
                    + "VALUES (?, ?, ?, ?, ?)";
            
            try {
                if(tfNom.getText().isEmpty() | tfPrenom.getText().isEmpty() | tfEmail.getText().isEmpty() | tfPassword.getText().isEmpty() )
                {
                  //empty fields
                                  Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Athlon  :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Entrer all blank fields !!");
                alert.showAndWait();  
                }
                
                else if ( tfPassword.getText().length() < 8 ){
                //pwd < 8
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Athlon :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Password doit etre sup 8 caractéres !!");
                alert.showAndWait();  
                }
                
                else{
                    
                    PreparedStatement smt = cnx.prepareStatement(query);
                    String pew;
                    pew = PwdHasher.hashPassword(tfPassword.getText());
                    System.out.println("bigg pribmem  ");
                    
                    
                           Date datei = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                            String strdate = formatter.format(datei);
        
            smt.setString(1, tfNom.getText());
            smt.setString(2, tfPrenom.getText());
            smt.setString(3, tfEmail.getText());
            smt.setString(4, pew );
            smt.setString(5, strdate );

            smt.executeUpdate();
             
                System.out.println("ajout avec succee");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Athlon :: BIENVENNUE");
                alert.setHeaderText(null);
                alert.setContentText("Vous Etes Inscrit !! \n proceed to sign in ");
                alert.showAndWait();
                

                
                    try {

                        
                        //we try to send mail

                        HelperV2.sendWelcomeEmail(tfEmail.getText() , "Dear new user, \n Welcome to our app! We are glad you signed up" );
                        System.out.println("mail fct called ");
                        
                    } catch (MessagingException ex) {
                        System.out.println(ex);
                    }

                                    //now hide this and show login
                
                }
                
                
            }catch(SQLException ex){
         System.out.println(ex.getMessage());
            }
        
        
    }

    
}
