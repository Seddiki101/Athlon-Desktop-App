package viewconge;

import entity.conge;
import entity.employe;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import util.ConnectionDB;
import viewemploye.AjouteremployeController;


public class CongelistController implements Initializable {

    @FXML
    private TableView<conge> tableconge;

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
   @FXML
private TableColumn<?, ?> Collidconge;
@FXML
private TableColumn<?, ?> Collidemployeconge;
@FXML
private TableColumn<?, ?> Colldatedconge;
@FXML
private TableColumn<?, ?> Colldatefconge;
@FXML
private TableColumn<?, ?> Colltypeconge;




    ObservableList<conge> CongeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    
        afficheconge();
    }

    public ObservableList<conge> getCongeList() {
        cnx = ConnectionDB.getInstance().getCnx();
        CongeList.clear();

        try {
            String query2 = "SELECT * FROM  conge ";
          
            PreparedStatement smt = cnx.prepareStatement(query2);
             
            conge mohamed;
            ResultSet rs = smt.executeQuery();
           ;

            while (rs.next()) {
               
                mohamed = new conge(rs.getInt("id"), rs.getInt("employe_id"),rs.getDate("date_d"),
                        rs.getDate("date_f"), rs.getString("type"));
                
                
                CongeList.add(mohamed);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return CongeList;
    }

 @FXML
public void afficheconge() {
    ObservableList<conge> list = getCongeList();
  Collidconge.setCellValueFactory(new PropertyValueFactory<>("id"));
Collidemployeconge.setCellValueFactory(new PropertyValueFactory<>("employeId"));
Colldatedconge.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
Colldatefconge.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
Colltypeconge.setCellValueFactory(new PropertyValueFactory<>("type"));

    tableconge.setItems(list);
}

    public void deleteConge(conge congeToDelete) {
    cnx = ConnectionDB.getInstance().getCnx();
    
    try {
        String query = "DELETE FROM conge WHERE id = ?";
        PreparedStatement smt = cnx.prepareStatement(query);
        smt.setInt(1, congeToDelete.getId());
        
        smt.executeUpdate();
        System.out.println("conge deleted successfully!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}


   @FXML
    private void handleAjoutButton(ActionEvent event) {
        try {
            // Load the "add employee" FXML file and create a new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewconge/ajoutconger.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
    
     @FXML
private void handleDeleteButton(ActionEvent event) {
    conge selectedEmployee = tableconge.getSelectionModel().getSelectedItem();
    
    if (selectedEmployee != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Employee");
        alert.setHeaderText("Are you sure you want to delete this employee?");
        alert.setContentText(selectedEmployee.getEmployeId() + " " + selectedEmployee.getType());
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            deleteConge(selectedEmployee);

            tableconge.getItems().remove(selectedEmployee);
        }
    } else {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText("No Employee Selected");
        alert.setContentText("Please select an employee in the table.");
        alert.showAndWait();
    }
}



     @FXML
    private void handleAjoutButtonedit(ActionEvent event) {
        // Get the selected employee from the table
        conge selectedEmploye = tableconge.getSelectionModel().getSelectedItem();
 
        if (selectedEmploye != null) {
            try {
                // Load the "add employee" FXML file and create a new scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewconge/ajoutconger.fxml"));
                Parent root = loader.load();

                // Get the controller for the "add employee" scene
                AjoutcongerController controller = loader.getController();

                // Set the selected employee's data in the "add employee" form
                controller.setCongeData(selectedEmploye);

                // Show the "add employee" form
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
   @FXML
private void handleCalendrierbutton(ActionEvent event) {
       try {
            // Load the "add employee" FXML file and create a new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewconge/Calendar.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
}


}
