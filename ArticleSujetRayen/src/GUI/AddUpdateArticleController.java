/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entites.Article;
import entites.Sujet;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import services.ArticleService;
import services.SujetService;

/**
 * FXML Controller class
 *
 * @author wasli rayen
 */
public class AddUpdateArticleController implements Initializable {

    ArticleService service;
    SujetService sujetService;
    Article article;
    String type;
    private GestionArticleController controller;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label TitleLabel;
    @FXML
    private TextField titreField;
    @FXML
    private TextField auteurField;
    @FXML
    private TextField descriptionFirld;
    @FXML
    private TextField imageField;
    @FXML
    private ComboBox<Sujet> sujetCB;
    @FXML
    private Button actionButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new ArticleService();
        sujetService = new SujetService();

        ObservableList<Sujet> list = FXCollections.observableArrayList();
        list.addAll(sujetService.getAll());
        sujetCB.setItems(list);
    }

    @FXML
    private void ajoutOrDelete(ActionEvent event) {
        if (controleDeSaisie()) {
            if (type.equals("Update")) {

                article.setSujet(sujetCB.getSelectionModel().getSelectedItem());
                article.setAuteur(auteurField.getText());
                article.setDescription(descriptionFirld.getText());
                article.setImg(imageField.getText());
                article.setTitre(titreField.getText());

                update(article);
            } else {
                article = new Article();
                article.setSujet(sujetCB.getSelectionModel().getSelectedItem());
                article.setAuteur(auteurField.getText());
                article.setDescription(descriptionFirld.getText());
                article.setImg(imageField.getText());
                article.setTitre(titreField.getText());

                ajout(article);
            }
            controller.refreshTable();
        }
    }

    private void update(Article o) {

        if (service.update(o)) {
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

    private void ajout(Article o) {

        if (service.insert(o)) {

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
        TitleLabel.setText(type + " Article");
    }

    public void initializeTextField(Article o) {
        article = o;

        sujetCB.getSelectionModel().select(o.getSujet());
        auteurField.setText(o.getAuteur());
        descriptionFirld.setText(o.getDescription());
        imageField.setText(o.getImg() + "");
        titreField.setText(o.getTitre());

    }

    private boolean controleDeSaisie() {

        if (auteurField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir le nom medecin");
            alert.setTitle("fail");
            alert.show();
            return false;
        }

        if (descriptionFirld.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir le type");
            alert.setTitle("fail");
            alert.show();
            return false;
        }

        if (imageField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir le note");
            alert.setTitle("fail");
            alert.show();
            return false;
        }
        if (titreField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir le note");
            alert.setTitle("fail");
            alert.show();
            return false;
        }
        if (sujetCB.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir le Sujet");
            alert.setTitle("fail");
            alert.show();
            return false;
        }

        return true;
    }

    void initializeController(GestionArticleController controller) {
        this.controller = controller;
    }

}
