/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewemploye;




import entity.Reclamation;
import entity.User;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import util.ConnectionDB;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


/**
 * FXML Controller class
 *
 * @author auo1
 */
public class EmployelistController implements Initializable {

    @FXML
    private TableView<employe> tableemploye;
    @FXML
    private TableColumn<?, ?> Collcinemploye;
    @FXML
    private TableColumn<?, ?> Collnomemploye;
    @FXML
    private TableColumn<?, ?> Collprenomemploye;
    @FXML
    private TableColumn<?, ?> Collsalaireemploye;
    
      private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    
   
    
    ObservableList<employe> EmployeList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> Collidemploye;
    @FXML
    private TextField searchInput;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficheremploye () ;
                searchEmploye();

       
        
    }    
       public ObservableList<employe> getEmployeList() {
        cnx = ConnectionDB.getInstance().getCnx();
        EmployeList.clear();

        try {
            String query2 = "SELECT * FROM  employe ";
            PreparedStatement smt = cnx.prepareStatement(query2);
            employe mohamed;
            ResultSet rs = smt.executeQuery();

            while (rs.next()) {
                // public employe(int id, int cin, String nom, String prenom, String salaire)
                mohamed = new employe(rs.getInt("id"), rs.getInt("cin"), rs.getString("nom"),rs.getString("prenom"),rs.getFloat("salaire"));
                EmployeList.add(mohamed);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return EmployeList;
    }
       
       
       
       
    
    @FXML
    public void afficheremploye() {
        ObservableList<employe> list = getEmployeList();
         Collidemploye.setCellValueFactory(new PropertyValueFactory<>("id"));
       Collcinemploye.setCellValueFactory(new PropertyValueFactory<>("cin"));
         Collnomemploye.setCellValueFactory(new PropertyValueFactory<>("nom"));
          Collprenomemploye.setCellValueFactory(new PropertyValueFactory<>("prenom"));
          Collsalaireemploye.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        tableemploye.setItems(list);
    }
    
    
    
     public void updateEmployee(String cin, String nom, String prenom, String salaire, int id) {
        cnx = ConnectionDB.getInstance().getCnx();
        String query = "UPDATE employe SET cin = ?, nom = ?, prenom = ?, salaire = ? WHERE id = ?";

        try {
            if (cin.isEmpty() || nom.isEmpty() || salaire.isEmpty()) {
                // empty fields
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Athlon :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields!");
                alert.showAndWait();
            } else if (nom.length() < 3) {
                // name too short
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Athlon :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Name must be at least 3 characters long!");
                alert.showAndWait();
            } else {
                PreparedStatement smt = cnx.prepareStatement(query);

                smt.setString(1, cin);
                smt.setString(2, nom);
                smt.setString(3, prenom);
                smt.setString(4, salaire);
                smt.setInt(5, id);

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
    
    
    
    
    
    
    
    
    
    
    
    public void deleteEmployee(employe employeeToDelete) {
    cnx = ConnectionDB.getInstance().getCnx();
    
    try {
        String query = "DELETE FROM employe WHERE id = ?";
        PreparedStatement smt = cnx.prepareStatement(query);
        smt.setInt(1, employeeToDelete.getId());
        
        smt.executeUpdate();
        System.out.println("Employee deleted successfully!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

    
    
    
    
    
    
    
    
        @FXML
public void generatePDF(ActionEvent event) {
    
    // Create content list
List<String> content = new ArrayList<>();

// Add header row to the content list
content.add("FirstName   Last Name   Email   Etat   ");

// Populate table content from userList TableView
for (employe employee : tableemploye.getItems()) {
    String row = employee.toString() ;
    content.add(row);
}

// Create a new PDF document
PDDocument document = new PDDocument();

// Add a new page to the document
PDPage page = new PDPage();
document.addPage(page);

// Create a new font for the header
PDType1Font font = PDType1Font.HELVETICA;

// Add content to the PDF document
try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
    // Add the title
    contentStream.beginText();
    contentStream.setFont(font, 36);
    contentStream.newLineAtOffset(50, 650);
    contentStream.showText("Employe List");
    contentStream.endText();

    // Add the table data
    contentStream.beginText();
    contentStream.setFont(font, 12);
    contentStream.newLineAtOffset(50, 600);

    for (int i = 0; i < content.size(); i++) {
        contentStream.showText(content.get(i));
        contentStream.newLineAtOffset(0, -20);
    }

    contentStream.endText();
    contentStream.close();
}catch(Exception e) {
        e.printStackTrace();
}

// Save the PDF document to a file or stream
    try {
        document.save("employe_list.pdf");
        document.close();
    } catch (IOException e) {
        e.printStackTrace();
    }


}
  
    
    
    
    
  public void searchEmploye() {
        FilteredList<employe> filteredData = new FilteredList<>(EmployeList, p -> true);
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employe -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (employe.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employe.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Integer.toString(employe.getCin()).contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        // wrap the filtered list in a SortedList
        SortedList<employe> sortedData = new SortedList<>(filteredData);
        // bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(tableemploye.comparatorProperty());
        // add sorted (and filtered) data to the table
        tableemploye.setItems(sortedData);
    }
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
     @FXML
    private void handleAjoutButton(ActionEvent event) {
        try {
            // Load the "add employee" FXML file and create a new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewemploye/ajouteremploye.fxml"));
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
    private void handleAjoutButtonedit(ActionEvent event) {
        // Get the selected employee from the table
        employe selectedEmploye = tableemploye.getSelectionModel().getSelectedItem();
 
        if (selectedEmploye != null) {
            try {
                // Load the "add employee" FXML file and create a new scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewemploye/ajouteremploye.fxml"));
                Parent root = loader.load();

                // Get the controller for the "add employee" scene
                AjouteremployeController controller = loader.getController();

                // Set the selected employee's data in the "add employee" form
                controller.setEmployeeData(selectedEmploye);

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
private void handleDeleteButton(ActionEvent event) {
    employe selectedEmployee = tableemploye.getSelectionModel().getSelectedItem();
    
    if (selectedEmployee != null) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Employee");
        alert.setHeaderText("Are you sure you want to delete this employee?");
        alert.setContentText(selectedEmployee.getNom() + " " + selectedEmployee.getPrenom());
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            deleteEmployee(selectedEmployee);

            tableemploye.getItems().remove(selectedEmployee);
        }
    } else {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText("No Employee Selected");
        alert.setContentText("Please select an employee in the table.");
        alert.showAndWait();
    }
}


}
