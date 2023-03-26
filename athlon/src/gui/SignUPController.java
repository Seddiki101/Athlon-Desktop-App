/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.ConnectionDB;

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
                    
                           Date datei = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                            String strdate = formatter.format(datei);
        
            smt.setString(1, tfNom.getText());
            smt.setString(2, tfPrenom.getText());
            smt.setString(3, tfEmail.getText());
            smt.setString(4, tfPassword.getText());
            smt.setString(5, strdate );

            smt.executeUpdate();
             
                System.out.println("ajout avec succee");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Athlon :: BIENVENNUE");
                alert.setHeaderText(null);
                alert.setContentText("Vous Etes Inscrit !!");
                alert.showAndWait();
                    
                //now hide this and show login
                
                
                }
                
                
            }catch(SQLException ex){
         System.out.println(ex.getMessage());
            }
        
        
    }
    
}
