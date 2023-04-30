/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewArticle;

import entity.Sujet;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import services.SujetService;

/**
 * FXML Controller class
 *
 * @author Houssem Charef
 */
public class AddUpdateSujetController implements Initializable {

    SujetService service;
    Sujet sujet;
    String type;
    private GestionSujetController controller;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label TitleLabel;
    @FXML
    private TextField nomField;
    @FXML
    private TextField descriptionFiled;
    @FXML
    private Button actionButton;
    @FXML
    private TextField imageFiled;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new SujetService();
    }

    @FXML
    private void ajoutOrDelete(ActionEvent event) {
        if (controleDeSaisie()) {
            if (type.equals("Update")) {

                sujet.setNom(nomField.getText());
                sujet.setDescription(descriptionFiled.getText());
                sujet.setImg(imageFiled.getText());

                update(sujet);
            } else {
                sujet = new Sujet();
                sujet.setNom(nomField.getText());
                sujet.setDescription(descriptionFiled.getText());
                sujet.setImg(imageFiled.getText());
                ajout(sujet);
            }
            controller.refreshTable();
        }
    }

    private void update(Sujet m) {

        if (service.update(m)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("mise à jour avec succès");
            alert.setTitle("Succès");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("mise à jour fail !! ");
            alert.setTitle("fail");
            alert.show();
        }
    }

    private void ajout(Sujet m) {

        if (service.insert(m)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("ajout avec succès");
            alert.setTitle("Succès");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("mise à jour fail ");
            alert.setTitle("fail");
            alert.show();
        }

    }

    public void setWindowType(String type) {
        this.type = type;
        actionButton.setText(type);
        TitleLabel.setText(type + " Sujet");
    }

    public void initializeTextField(Sujet m) {
        sujet = m;
        nomField.setText(m.getNom());
        descriptionFiled.setText(m.getDescription());
        imageFiled.setText(m.getImg());

    }

    private boolean controleDeSaisie() {

        if (nomField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir le nom");
            alert.setTitle("fail");
            alert.show();
            return false;
        }
        if (descriptionFiled.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir le description");
            alert.setTitle("fail");
            alert.show();
            return false;
        }

        if (descriptionFiled.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir l'image");
            alert.setTitle("fail");
            alert.show();
            return false;
        }

        return true;
    }

    void initializeController(GestionSujetController controller) {
        this.controller = controller;
    }

}
