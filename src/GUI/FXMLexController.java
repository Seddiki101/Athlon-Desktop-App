/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import edu.esprit.entities.Cours;
import edu.esprit.entities.Exercices;
import edu.esprit.services.ServiceExercices;
import edu.esprit.services.ServiceCours;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class FXMLexController implements Initializable {

    @FXML
    private TableView<Exercices> tableview;
    
    @FXML
    private TableColumn<Exercices, Integer> colid;
    
    @FXML
    private TableColumn<Exercices, Integer> colcours_id;
            
    @FXML
    private TableColumn<Exercices, String> colnom;
    
    @FXML
    private TableColumn<Exercices, Integer> colduree_exercices;
    
    @FXML
    private TableColumn<Exercices, Integer> colnombre_repetitions;
    
    @FXML
    private TableColumn<Exercices, String> coldesc_exercices;
    
    @FXML
    private TableColumn<Exercices, String> colmachine;
 
    @FXML private TextField NOM;
    @FXML private TextField duree;
    @FXML private TextField Nombre_de_repetitions;
    @FXML private TextField Description;
    @FXML private TextField Machine;
    @FXML private ComboBox<String> coursIDComboBox;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button updateButton;
    @FXML private Button clearButton;

    @FXML private VBox inputFields;
    
    private ObservableList<Exercices> observableList;
    private Exercices selectedExercices;

    @Override
   public void initialize(URL url, ResourceBundle rb) {
        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        selectedExercices = newSelection;
        NOM.setText(selectedExercices.getNom());
        duree.setText(String.valueOf(selectedExercices.getDuree_exercices()));
        Nombre_de_repetitions.setText(String.valueOf(selectedExercices.getNombre_repetitions()));
        Description.setText(selectedExercices.getDesc_exercices());
        Machine.setText(selectedExercices.getMachine());
    }
});
        try {
            // Load events from database into the tableview
            ServiceExercices dao = new ServiceExercices();
            ServiceCours daoC = new ServiceCours();
            try {
                // Get the ArrayList from the service
                ArrayList<Exercices> exercices = dao.AfficherEX();
                
                // Convert the ArrayList to an ObservableList
                observableList = FXCollections.observableArrayList(exercices);
                
                // Set the ObservableList as the data source for the TableView
                tableview.setItems(observableList);
                
            } catch (SQLException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Set cell value factories for the table columns
            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colduree_exercices.setCellValueFactory(new PropertyValueFactory<>("duree_exercices"));
            colnombre_repetitions.setCellValueFactory(new PropertyValueFactory<>("Nombre de répétitions"));
            coldesc_exercices.setCellValueFactory(new PropertyValueFactory<>("desc_exercices"));
            colmachine.setCellValueFactory(new PropertyValueFactory<>("machine"));
            
            // Set the items for the combo box
            ArrayList<Cours> cours = daoC.afficherCrs();
            ObservableList<String> coursList = FXCollections.observableArrayList();
            for (Cours c : cours) {
                coursList.add(c.getNom());
            }
            coursIDComboBox.setItems(coursList);
            
            
            
            // Set the cell factory for the combo box
            coursIDComboBox.setCellFactory(param -> new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("");
                    } else {
                        setText(item);
                    }
                }
            });
            
            // Set the value factory for the combo box
            coursIDComboBox.setButtonCell(new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("");
                    } else {
                        setText(item);
                    }
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(FXMLexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    
@FXML

private void addItem(ActionEvent event) throws SQLException {
    ServiceExercices dao = new ServiceExercices();
    ServiceCours daoC = new ServiceCours();

    String  nom = NOM.getText();
    int duree_exercices = Integer.parseInt(duree.getText());
    int nombre_repetitions = Integer.parseInt(Nombre_de_repetitions.getText());
    String desc_exercices = Description.getText();
    String machine = Machine.getText();
    
    
        if (!validateFields(nom, duree_exercices, nombre_repetitions, desc_exercices, machine)) {
        return;
    }
    // Get the selected course from the ComboBox
    String nomCours = coursIDComboBox.getValue(); // récupérer le nom du cours sélectionné
    ArrayList<Cours> selectedCoursList = daoC.rechercherCrsParNom(nomCours);
Cours selectedCours = selectedCoursList.get(0);


    Exercices exercice = new Exercices(nom, duree_exercices, nombre_repetitions, desc_exercices, machine);
    
    // Set the course id for the exercice
    exercice.setId_cours(selectedCours.getId());

    
    try {
        dao.ajouterEX(exercice);

        // Refresh the TableView
        tableview.getItems().clear();
        
        try {
            // Get the ArrayList from the service
            ArrayList<Exercices> exercices = dao.AfficherEX();


            // Convert the ArrayList to an ObservableList
            observableList = FXCollections.observableArrayList(exercices);

            // Set the ObservableList as the data source for the TableView
            tableview.setItems(observableList);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableview.refresh();
    } catch (SQLException ex) {
        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
}


   @FXML
private void deleteItem() {
    ServiceExercices dao = new ServiceExercices();
    Exercices selectedEvent = tableview.getSelectionModel().getSelectedItem();
    if (selectedEvent != null) {
        try {
            dao.deleteEX(selectedEvent.getId());
            tableview.getItems().remove(selectedEvent);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // Get the ArrayList from the service
            ArrayList<Exercices> exercices = dao.AfficherEX();

            // Convert the ArrayList to an ObservableList
            observableList = FXCollections.observableArrayList(exercices);

            // Set the ObservableList as the data source for the TableView
            tableview.setItems(observableList);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableview.refresh();
    }
}

@FXML

private void updateItem(ActionEvent event) throws SQLException {
    ServiceExercices dao = new ServiceExercices();
    ServiceCours daoC = new ServiceCours();

    String nom = NOM.getText();
    int duree_exercices = Integer.parseInt(duree.getText());
    int nombre_repetitions = Integer.parseInt(Nombre_de_repetitions.getText());
    String desc_exercices = Description.getText();
    String machine = Machine.getText();
    
    
        if (!validateFields(nom, duree_exercices, nombre_repetitions, desc_exercices, machine)) {
        return;
    }
    // Get the selected course from the ComboBox
    String nomCours = coursIDComboBox.getValue(); // récupérer le nom du cours sélectionné
    ArrayList<Cours> selectedCoursList = daoC.rechercherCrsParNom(nomCours);
    Cours selectedCours = selectedCoursList.get(0);

    // Get the selected item from the TableView
     if (selectedCours == null) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Aucune sélection");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une ligne dans le tableau.");
        alert.showAndWait();
        return;
    }
    Exercices selectedExercice = tableview.getSelectionModel().getSelectedItem();
    selectedExercice.setNom(nom);
    selectedExercice.setDuree_exercices(duree_exercices);
    selectedExercice.setNombre_repetitions(nombre_repetitions);
    selectedExercice.setDesc_exercices(desc_exercices);
    selectedExercice.setMachine(machine);
    selectedExercice.setId_cours(selectedCours.getId());
    
    try {
        dao.updateEX(selectedExercice);

        // Refresh the TableView
        tableview.getItems().clear();
        
        try {
            // Get the ArrayList from the service
            ArrayList<Exercices> exercices = dao.AfficherEX();


            // Convert the ArrayList to an ObservableList
            observableList = FXCollections.observableArrayList(exercices);

            // Set the ObservableList as the data source for the TableView
            tableview.setItems(observableList);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableview.refresh();
    } catch (SQLException ex) {
        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public void handleClearButtonAction(ActionEvent event) {
    NOM.clear();
    duree.clear();
    Nombre_de_repetitions.clear();
    Description.clear();
    Machine.clear();
}
 


    private void refreshTable() {
    try {
        ServiceExercices dao = new ServiceExercices();
        ServiceCours daoC = new ServiceCours();

        List<Exercices> events = dao.AfficherEX();
        ObservableList<Exercices> observableEvents = FXCollections.observableArrayList(events);
        tableview.setItems(observableEvents);

        // Populate the course ComboBox
        List<Cours> cours = daoC.afficherCrs();
        ObservableList<String> observableCourses = FXCollections.observableArrayList();
        for (Cours cour : cours) {
            observableCourses.add(cour.getNom());
        }
        coursIDComboBox.setItems(observableCourses);

    } catch (SQLException ex) {
        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
private boolean validateFields(String nom, int duree_exercices , int nombre_repetitions , String  desc_exercices , String machine) {
    if (nom.isEmpty() || desc_exercices.isEmpty() || duree_exercices == 0 || nombre_repetitions == 0 || machine.isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Champ vide");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs");
        alert.showAndWait();
        return false;
    } else if (nom.length() > 20 || desc_exercices.length() > 50 || duree_exercices > 30 || nombre_repetitions > 5 || machine.length() > 15) {
Alert alert = new Alert(AlertType.ERROR);
alert.setTitle("Données invalides");
alert.setHeaderText(null);
String message = "";
if (nom.length() > 20) {
message += "Le nom du cours ne doit pas dépasser 20 caractères.\n";
}
if (desc_exercices.length() > 50) {
message += "La description du cours ne doit pas dépasser 50 caractères.\n";
}
if (duree_exercices > 30) {
message += "La durée de l'exercice ne doit pas dépasser 30 minutes.\n";
}
if (nombre_repetitions > 5) {
message += "Le nombre de répétitions ne doit pas dépasser 5 répétitions.\n";
}
if (machine.length() > 15) {
message += "La machine ne doit pas dépasser 15 caractères.\n";
}
alert.setContentText(message);
alert.showAndWait();
return false;
}
    return true;
}



private void showAlert(String title, String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
}