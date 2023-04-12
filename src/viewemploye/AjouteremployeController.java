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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import util.ConnectionDB;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author auo1
 */
public class AjouteremployeController implements Initializable {

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
        // TODO
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
                alert.showAndWait();
                
                }
                
                
            }catch(SQLException ex){
         System.out.println(ex.getMessage());
            }

         //
            
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
