package GUI;


import edu.esprit.entities.Cours;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javax.mail.MessagingException;

public class FXMLController implements Initializable {
    @FXML
    private TableView<Cours> tableview;
    
   
    
    @FXML
    private TableColumn<Cours, String> colnom;
    @FXML
    private TableColumn<Cours, String> coldescription_cours;
    @FXML
    private TableColumn<Cours, Integer> colduree_cours;
    @FXML
    private TableColumn<Cours, Integer> colcapacity;
    @FXML
    private TableColumn<Cours, String> colniveau_cours;
    
    @FXML private TextField NOM;
    @FXML private TextField Description;
    @FXML private TextField Duree;
    @FXML private TextField Capacity;
    @FXML private TextField Niveau;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button updateButton;
    @FXML private Button clearButton;
    
    private ObservableList<Cours> observableList;
    private Cours selectedCours;
  

   
@FXML
private VBox parent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {


        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            selectedCours = newSelection;
            NOM.setText(selectedCours.getNom());
            Description.setText(selectedCours.getDescription_cours());
            Duree.setText(String.valueOf(selectedCours.getDuree_cours()));
            Capacity.setText(String.valueOf(selectedCours.getCapacity()));
            Niveau.setText(selectedCours.getNiveau_cours());
        }
    });
    try {
    ServiceCours dao = new ServiceCours();
    ArrayList<Cours> cours = dao.afficherCrs();
    ObservableList<Cours> observableList = FXCollections.observableArrayList(cours);


        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        coldescription_cours.setCellValueFactory(new PropertyValueFactory<>("description_cours"));
        colduree_cours.setCellValueFactory(new PropertyValueFactory<>("duree_cours"));
        colcapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colniveau_cours.setCellValueFactory(new PropertyValueFactory<>("niveau_cours"));

        tableview.setItems(observableList);

    } catch (SQLException ex) {
        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @FXML
    private void addItem(ActionEvent event) throws MessagingException {
        ServiceCours dao = new ServiceCours();
        String nom = NOM.getText();
        String description_cours = Description.getText();
        int duree_cours = Integer.parseInt(Duree.getText());
        int capacity = Integer.parseInt(Capacity.getText());
        String niveau_cours = Niveau.getText();



        if (!validateFields(nom, description_cours, duree_cours, capacity, niveau_cours)) {
        return;
    }


        Cours cour = new Cours(nom, description_cours, duree_cours, capacity, niveau_cours);


        try {
            dao.ajouterCrs(cour);

            // Refresh the TableView
            tableview.getItems().clear();

           try {
                // Get the ArrayList from the service
               ArrayList<Cours> cours = dao.afficherCrs();


                // Convert the ArrayList to an ObservableList
                observableList = FXCollections.observableArrayList(cours);

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
        ServiceCours dao = new ServiceCours();
        Cours selectedEvent = tableview.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            try {
                dao.deleteCrs(selectedEvent.getId());
                tableview.getItems().remove(selectedEvent);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                // Get the ArrayList from the service
                ArrayList<Cours> cours = dao.afficherCrs();

                // Convert the ArrayList to an ObservableList
                observableList = FXCollections.observableArrayList(cours);

                // Set the ObservableList as the data source for the TableView
                tableview.setItems(observableList);

            } catch (SQLException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tableview.refresh();
        }
    }

    @FXML
    private void updateItem(ActionEvent event) {
        if (selectedCours == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une ligne dans le tableau.");
            alert.showAndWait();
            return;
        }

        try {
            ServiceCours dao = new ServiceCours();
            selectedCours.setNom(NOM.getText());
            selectedCours.setDescription_cours(Description.getText());
            selectedCours.setDuree_cours(Integer.parseInt(Duree.getText()));
            selectedCours.setCapacity(Integer.parseInt(Capacity.getText()));
            selectedCours.setNiveau_cours(Niveau.getText());
            dao.updateCrs(selectedCours);
            tableview.refresh();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        NOM.clear();
        Description.clear();
        Duree.clear();
        Capacity.clear();
        Niveau.clear();
    }



       private void actualiserTableau() throws SQLException {
        ServiceCours service = new ServiceCours();
        List<Cours> listCours = service.afficherCrs();
        ObservableList<Cours> obListCours = FXCollections.observableArrayList(listCours);
        tableview.setItems(obListCours);
    }
       private void remplirChamps(Cours selected) {
        NOM.setText(selected.getNom());
        Description.setText(selected.getDescription_cours());
        Duree.setText(String.valueOf(selected.getDuree_cours()));
        Capacity.setText(String.valueOf(selected.getCapacity()));
        Niveau.setText(selected.getNiveau_cours());
    }


private boolean validateFields(String nom, String description_cours, int duree_cours, int capacity, String niveau_cours) {
    if (nom.isEmpty() || description_cours.isEmpty() || duree_cours == 0 || capacity == 0 || niveau_cours.isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Champ vide");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs");
        alert.showAndWait();
        return false;
    } else if (nom.length() > 20 || description_cours.length() > 50 || duree_cours > 120 || capacity > 50 || niveau_cours.length() > 15) {
Alert alert = new Alert(AlertType.ERROR);
alert.setTitle("Données invalides");
alert.setHeaderText(null);
String message = "";
if (nom.length() > 20) {
message += "Le nom du cours ne doit pas dépasser 20 caractères.\n";
}
if (description_cours.length() > 50) {
message += "La description du cours ne doit pas dépasser 50 caractères.\n";
}
if (duree_cours > 120) {
message += "La durée du cours ne doit pas dépasser 120 minutes.\n";
}
if (capacity > 50) {
message += "La capacité du cours ne doit pas dépasser 50 abonnés.\n";
}
if (niveau_cours.length() > 15) {
message += "Le niveau du cours ne doit pas dépasser 15 caractères.\n";
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