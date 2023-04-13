/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewconge;

import entity.employe;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.ConnectionDB;
import viewemploye.AjouteremployeController;

/**
 * FXML Controller class
 *
 * @author auo1
 */
public class AjoutcongerController implements Initializable {
    
      private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private DatePicker textdateD;
    @FXML
    private DatePicker textdateF;
    @FXML
    private TextField type;
   
    @FXML
    private ComboBox<employe> IdEmploye;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
  
    }    
    
    
    
    
 
    
    
    
    
      @FXML
    private void createConge()
    {
             cnx = ConnectionDB.getInstance().getCnx();
            String query="INSERT INTO conge ( employe_id, date_d, date_f, type)"
                    + "VALUES (?, ?, ?, ?)";   
            
         //
                     
            try {
                if( type.getText().isEmpty())
                {
                  //empty fields
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Athlon :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Entrer all blank fields !!");
                alert.showAndWait();  
                }
                
                else if ( type.getText().length() < 3  ){
                //user bs
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Athlon :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Nom  doit etre significative !!");
                alert.showAndWait();  
                }
                
                else{

                     PreparedStatement smt = cnx.prepareStatement(query);
           smt.setInt(1, IdEmploye.getValue().getId());
             smt.setDate(2, java.sql.Date.valueOf(textdateD.getValue()));
             smt.setDate(3, java.sql.Date.valueOf(textdateF.getValue()));
            smt.setString(4, type.getText());
            // maybe add condition if null dont send //SessionManager.getUser().getId() // big problem
             //getting current user id // 35 

            //System.out.print("hehe");    
            
            smt.executeUpdate();
             
                System.out.println("ajout avec succee");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Athlon :: BIENVENNUE");
                alert.setHeaderText(null);
                alert.setContentText("Conger Ajouter");
                alert.showAndWait();
                
                }
                
                
            }catch(SQLException ex){
         System.out.println(ex.getMessage());
            }

         //
            
    }
    
    
    
  
    
}
