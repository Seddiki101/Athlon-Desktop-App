package viewCour;

import entity.Cours;
import services.ServiceCours;
import java.sql.SQLException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

public class DetailsCoursController {
@FXML
private WebView webView;
    @FXML
    private Label nomLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label niveauLabel;

    @FXML
    private Label capaciteLabel;

    @FXML
    private Label dureeLabel;

    @FXML
    private Button reserverButton;

    private Cours selectedCourse;

    private StringProperty nom = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty niveau = new SimpleStringProperty();
    private IntegerProperty capacite = new SimpleIntegerProperty();
    private IntegerProperty duree = new SimpleIntegerProperty();

    public void initialize() {
        // Liens entre les propriétés et les labels
        nomLabel.textProperty().bind(nom);
        descriptionLabel.textProperty().bind(description);
        niveauLabel.textProperty().bind(niveau);
        capaciteLabel.textProperty().bind(capacite.asString());
        dureeLabel.textProperty().bind(duree.asString());
    }

    public void setSelectedCourse(Cours course) {
        selectedCourse = course;
        nom.set(selectedCourse.getNom());
        description.set(selectedCourse.getDescription_cours());
        niveau.set(selectedCourse.getNiveau_cours());
        capacite.set(selectedCourse.getCapacity());
        duree.set(selectedCourse.getDuree_cours());
        
    }


   @FXML
private void handleReservation() throws SQLException {
    if (selectedCourse.getCapacity() > 0) {
        // Injecter votre service dans le contrôleur
        ServiceCours serviceCours = new ServiceCours();
        selectedCourse.setCapacity(selectedCourse.getCapacity() - 1);//cours selectionné decremente 1
        serviceCours.updateCrs(selectedCourse);//mettre à jour les modifications
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Réservation");
        alert.setHeaderText(null);
        alert.setContentText("Votre réservation a été enregistrée pour le cours " + selectedCourse.getNom() + ".");
        alert.showAndWait();
        capaciteLabel.textProperty().bind(selectedCourse.capacityProperty().asString());//mettre à jour automatiquement l'affichage
    } else {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Capacité maximale atteinte");
        alert.setHeaderText(null);
        alert.setContentText("Désolé, la capacité maximale pour le cours " + selectedCourse.getNom() + " a été atteinte.");
        alert.showAndWait();
    }
}
}
