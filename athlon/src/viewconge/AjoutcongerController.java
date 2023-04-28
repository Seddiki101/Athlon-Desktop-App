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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    String sql = "SELECT id,cin, nom, prenom,salaire,etat FROM employe";
    PreparedStatement st = cnx.prepareStatement(sql);
    ResultSet rs = st.executeQuery();

    while (rs.next()) {
        employe emp = new employe(rs.getInt("id"),rs.getInt("cin"), rs.getString("nom"), rs.getString("prenom"),rs.getFloat("salaire"),rs.getString("etat"));
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
private void createConge() {
    cnx = ConnectionDB.getInstance().getCnx();
    String query = "INSERT INTO conge (employe_id, date_d, date_f, type) VALUES (?, ?, ?, ?)";

    try {
        if (type.getText().isEmpty()) {
            //empty fields
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Athlon :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Enter all blank fields!!");
            alert.showAndWait();
        } else if (type.getText().length() < 3) {
            //user bs
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Athlon :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Nom doit être significative!!");
            alert.showAndWait();
        } else {
            // Get the start and end date values from the UI components
            LocalDate startDate = textdateD.getValue();
            LocalDate endDate = textdateF.getValue();
            int employeeId = IdEmploye.getValue().getId();
            String selectQuery2 = "SELECT COUNT(*) FROM conge WHERE employe_id = ? AND date_f >= ? AND date_d <= ?";
            PreparedStatement selectStmt2 = cnx.prepareStatement(selectQuery2);
            selectStmt2.setInt(1, employeeId);
            selectStmt2.setDate(2, java.sql.Date.valueOf(startDate));
            selectStmt2.setDate(3, java.sql.Date.valueOf(endDate));
            ResultSet resultSet2 = selectStmt2.executeQuery();

            if (resultSet2.next() && resultSet2.getInt(1) > 0) {
                // Display an error message indicating that the employee already has a conge for the selected date range.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Athlon :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Employee already has a conge for the selected date range!");
                alert.showAndWait();
            } else {
                // Retrieve all the conge requests for the given dates
                String selectQuery = "SELECT COUNT(DISTINCT employe_id) FROM conge WHERE date_d <= ? AND date_f >= ?";
                PreparedStatement selectStmt = cnx.prepareStatement(selectQuery);
                selectStmt.setDate(1, java.sql.Date.valueOf(endDate));
                selectStmt.setDate(2, java.sql.Date.valueOf(startDate));
                ResultSet resultSet = selectStmt.executeQuery();
                int count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }

                if (count >= 3) {
                    // Display an error message indicating that the maximum number of conge requests has been reached.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Athlon :: Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Maximum number of conge requests reached for different employees between the selected dates!!");
                    alert.showAndWait();
                } else {
                    // Insert the new conge request into the database.
                    PreparedStatement insertStmt = cnx.prepareStatement(query);
                    insertStmt.setInt(1, employeeId);
                    insertStmt.setDate(2, java.sql.Date.valueOf(startDate));
                    insertStmt.setDate(3, java.sql.Date.valueOf(endDate));
                    insertStmt.setString(4, type.getText());
                    insertStmt.executeUpdate();

                    System.out.println("ajout avec succee");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Athlon :: BIENVENNUE");
                    alert.setHeaderText(null);
                    alert.setContentText("Conger Ajouter");
                    alert.showAndWait();
                }
            }
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
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
        IdEmploye.setValue(new employe(congee.getEmployeId(), 0, null, null, 0,null));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateDebutStr = dateFormat.format(congee.getDateDebut());
        String dateFinStr = dateFormat.format(congee.getDateFin());
        textdateD.setValue(LocalDate.parse(dateDebutStr));
        textdateF.setValue(LocalDate.parse(dateFinStr));
        type.setText(String.valueOf(congee.getType()));
    }
}

    
    
  
    
}
