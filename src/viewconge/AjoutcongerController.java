/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewconge;

import entity.conge;
import entity.employe;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
    @FXML
    private TextField textidconge;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
List<employe> employesList = new ArrayList<>();
List<String> ids = new ArrayList<>();

try {
    cnx = ConnectionDB.getInstance().getCnx();

    String sql = "SELECT id,cin, nom, prenom,salaire FROM employe";
    PreparedStatement st = cnx.prepareStatement(sql);
    ResultSet rs = st.executeQuery();

    while (rs.next()) {
        employe emp = new employe(rs.getInt("id"),rs.getInt("cin"), rs.getString("nom"), rs.getString("prenom"),rs.getFloat("salaire"));
        employesList.add(emp);
        ids.add(String.valueOf(emp.getId()));
    }
} catch (SQLException e) {
    e.printStackTrace();
}

ObservableList<employe> obsEmployesList = FXCollections.observableArrayList(employesList);
IdEmploye.setItems(obsEmployesList);

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

    
    @FXML
private void updateConge() {
    cnx = ConnectionDB.getInstance().getCnx();
    String query = "UPDATE conge SET date_d = ?, date_f = ?, type = ? WHERE id = ?";

    try {
        if (type.getText().isEmpty()) {
            // empty fields
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Athlon :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all fields!");
            alert.showAndWait();
        } else if (type.getText().length() < 3) {
            // user bs
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Athlon :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Nom doit etre significative!");
            alert.showAndWait();
        } else {
            PreparedStatement smt = cnx.prepareStatement(query);
            smt.setDate(1, java.sql.Date.valueOf(textdateD.getValue()));
            smt.setDate(2, java.sql.Date.valueOf(textdateF.getValue()));
            smt.setString(3, type.getText());
          smt.setInt(4, Integer.parseInt (textidconge.getText())); // assuming you have a Conge object with an ID field
            
            smt.executeUpdate();
            System.out.println("Update successful");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Athlon :: Update Successful");
            alert.setHeaderText(null);
            alert.setContentText("Conger updated");
            alert.showAndWait();
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

    public void setCongeData(conge congee) {
    if (congee != null) {
        // Set the data of the selected conge in the form fields
        textidconge.setText(String.valueOf(congee.getId()));
        IdEmploye.setValue(new employe(congee.getEmployeId(), 0, null, null, 0));
        textdateD.setValue(LocalDate.parse(congee.getDateDebut()));
        textdateF.setValue(LocalDate.parse(congee.getDateFin()));
       type.setText(String.valueOf(congee.getType()));
    }
}
    
    
  
    
}
