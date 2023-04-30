/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewArticle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Houssem Charef
 */
public class BaseController implements Initializable {

    public static BaseController baseController;

    @FXML
    private Label ConnectedUserNameLabel;
    @FXML
    private Label TitreLabel;
    @FXML
    private ImageView logoImageview;
    @FXML
    private HBox gestionSujetButton;
    @FXML
    private HBox GestionArticleButon;
    @FXML
    private StackPane AnchorePaneLayout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        baseController = this;
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/viewArticle/GestionSujet.fxml"));
            removeSelectedStyle();
            TitreLabel.setText("Gestion Sujet");
            gestionSujetButton.getStyleClass().add("btn-selected");
            AnchorePaneLayout.getChildren().removeAll();
            AnchorePaneLayout.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeSelectedStyle() {
        gestionSujetButton.getStyleClass().clear();
        GestionArticleButon.getStyleClass().clear();

        gestionSujetButton.getStyleClass().add("btns");
        GestionArticleButon.getStyleClass().add("btns");

    }

    @FXML
    private void LoadGestionSujet(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/viewArticle/GestionSujet.fxml"));
            removeSelectedStyle();
            TitreLabel.setText("Gestion Sujet");
            gestionSujetButton.getStyleClass().add("btn-selected");
            AnchorePaneLayout.getChildren().removeAll();
            AnchorePaneLayout.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void LoadGestionArticle(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/viewArticle/GestionArticle.fxml"));
            removeSelectedStyle();
            TitreLabel.setText("Gestion Article");
            GestionArticleButon.getStyleClass().add("btn-selected");
            AnchorePaneLayout.getChildren().removeAll();
            AnchorePaneLayout.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LoadGestionComment(MouseEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/viewArticle/GestionComment.fxml"));
            removeSelectedStyle();
            TitreLabel.setText("Gestion comment");
            AnchorePaneLayout.getChildren().removeAll();
            AnchorePaneLayout.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
