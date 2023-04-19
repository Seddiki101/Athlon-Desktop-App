/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import edu.esprit.entities.Cours;
import edu.esprit.services.ServiceCours;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLclController {
    @FXML

private TableView<Cours> tableView;
    
    private ServiceCours serviceCours = new ServiceCours();
    private List<Cours> cours;
    private Cours selectedCourse;
    
  public void initialize() throws SQLException {
      
    // Récupérer la liste des cours
    cours = serviceCours.afficherCrs();

    // Afficher les noms des cours dans la table
    TableColumn<Cours, String> nomCol = new TableColumn<>("Nom");
    nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
    tableView.getColumns().setAll(nomCol);
    tableView.getItems().addAll(cours);

    // Ecouter les clics sur une ligne du tableau
     tableView.setOnMouseClicked((MouseEvent event) -> {
            selectedCourse = tableView.getSelectionModel().getSelectedItem();
        });
    }


    @FXML
 
private void handleConsult() throws IOException {
    // Vérifier si un cours est sélectionné
    if (selectedCourse != null) {
        // Ouvrir l'interface "Détails du cours" dans une nouvelle fenêtre
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsCrs.fxml"));
        Parent root = loader.load();
        DetailsCoursController controller = loader.getController();
        controller.setSelectedCourse(selectedCourse);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(selectedCourse.getNom());
        stage.setScene(scene);
        stage.show();
    } else {
        // Afficher un message d'erreur si aucun cours n'est sélectionné
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Aucun cours sélectionné");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un cours.");
        alert.showAndWait();
    }
}

}
