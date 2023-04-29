package viewCour;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entity.Exercices;
import entity.Cours;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import services.ServiceCours;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;


public class DetailsEXController  {
 
    @FXML
    private Label nomCoursLabel;

    @FXML
    private Label nomLabel;

    @FXML
    private Label dureeLabel;

    @FXML
    private Label repetitionsLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label machineLabel;
   

    private Exercices selectedExercice;

private StringProperty nomCours = new SimpleStringProperty();
private StringProperty nom = new SimpleStringProperty();
private StringProperty description = new SimpleStringProperty();
private IntegerProperty duree = new SimpleIntegerProperty();
private IntegerProperty nombreRepetitions = new SimpleIntegerProperty();
private StringProperty machine = new SimpleStringProperty();


public void initialize() {
    // Liens entre les propriétés et les labels
    nomCoursLabel.textProperty().bind(nomCours);
    nomLabel.textProperty().bind(nom);
    descriptionLabel.textProperty().bind(description);
    dureeLabel.textProperty().bind(duree.asString());
    repetitionsLabel.textProperty().bind(nombreRepetitions.asString());
    machineLabel.textProperty().bind(machine);
    
}

public void setSelectedExercice(Exercices exercice) {
    selectedExercice = exercice;
    nom.set(selectedExercice.getNom());
    description.set(selectedExercice.getDesc_exercices());
    duree.set(selectedExercice.getDuree_exercices());
    nombreRepetitions.set(selectedExercice.getNombre_repetitions());
    machine.set(selectedExercice.getMachine());
    
  try {
      ServiceCours serviceCours = new ServiceCours();
Cours cours = serviceCours.getCoursById(selectedExercice.getId_cours());

        // Récupération du cours associé à l'exercice
        if (cours != null) {
         nomCours.set(cours.getNom());        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
  
}}