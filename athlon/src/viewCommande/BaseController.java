/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewCommande;

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
 * @author Seif Boulabiar
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
    private HBox gestionOrderButton;
    @FXML
    private HBox GestionOrderItemButon;
    @FXML
    private StackPane AnchorePaneLayout;
    @FXML
    private HBox cartButon;
    @FXML
    private HBox produitButton;
    @FXML
    private Label Produit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        baseController = this;
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/viewCommande/GestionOrder.fxml"));
            removeSelectedStyle();
            TitreLabel.setText("Gestion Evenement");
            gestionOrderButton.getStyleClass().add("btn-selected");
            AnchorePaneLayout.getChildren().removeAll();
            AnchorePaneLayout.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void LoadGestionOrder(MouseEvent event) {
        Parent root;

        try {
            root = FXMLLoader.load(getClass().getResource("/viewCommande/GestionOrder.fxml"));
            removeSelectedStyle();
            TitreLabel.setText("Gestion Order");
            gestionOrderButton.getStyleClass().add("btn-selected");
            AnchorePaneLayout.getChildren().removeAll();
            AnchorePaneLayout.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void LoadGestionOrderItem(MouseEvent event) {
        Parent root;

        try {
            root = FXMLLoader.load(getClass().getResource("/viewCommande/GestionOrderItem.fxml"));
            removeSelectedStyle();
            TitreLabel.setText("Gestion Order Item");
            GestionOrderItemButon.getStyleClass().add("btn-selected");
            AnchorePaneLayout.getChildren().removeAll();
            AnchorePaneLayout.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeSelectedStyle() {
        gestionOrderButton.getStyleClass().clear();
        GestionOrderItemButon.getStyleClass().clear();
        cartButon.getStyleClass().clear();
        produitButton.getStyleClass().clear();

        gestionOrderButton.getStyleClass().add("btns");
        GestionOrderItemButon.getStyleClass().add("btns");
        cartButon.getStyleClass().add("btns");
        produitButton.getStyleClass().add("btns");

    }

    @FXML
    private void LoadCart(MouseEvent event) {
        try {
            Parent root;

            root = FXMLLoader.load(getClass().getResource("/viewCommande/Cart.fxml"));
            removeSelectedStyle();
            TitreLabel.setText("Panier");
            cartButon.getStyleClass().add("btn-selected");
            AnchorePaneLayout.getChildren().removeAll();
            AnchorePaneLayout.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void LoadProduit(MouseEvent event) {
        try {
            Parent root;

            root = FXMLLoader.load(getClass().getResource("/viewCommande/GestionProduit.fxml"));
            removeSelectedStyle();
            TitreLabel.setText("Produit");
            produitButton.getStyleClass().add("btn-selected");
            AnchorePaneLayout.getChildren().removeAll();
            AnchorePaneLayout.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LoadCart1() {
        try {
            Parent root;

            root = FXMLLoader.load(getClass().getResource("/viewCommande/Cart.fxml"));
            removeSelectedStyle();
            TitreLabel.setText("Panier");
            cartButon.getStyleClass().add("btn-selected");
            AnchorePaneLayout.getChildren().removeAll();
            AnchorePaneLayout.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
