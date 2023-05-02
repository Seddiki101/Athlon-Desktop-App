/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class MenuProduitController implements Initializable {

    @FXML
    private VBox mainPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ProduitMenu(ActionEvent event) throws IOException {
        
           mainPane.getChildren().clear();
        Parent Content = FXMLLoader.load(getClass().getResource("Produit.fxml"));
        mainPane.getChildren().setAll(Content);
    }

    @FXML
    private void categorieMenu(ActionEvent event) throws IOException {
         mainPane.getChildren().clear();
        Parent Content = FXMLLoader.load(getClass().getResource("Categorie.fxml"));
        mainPane.getChildren().setAll(Content);
    }

    private void FrontProd(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/GUI/FrontProduit.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();}
        catch (IOException ex) {
            System.out.println(ex);        }
    }

    @FXML
    private void front(MouseEvent event) throws IOException {
        
         mainPane.getChildren().clear();
        Parent Content = FXMLLoader.load(getClass().getResource("frontProduit.fxml"));
       mainPane.getChildren().setAll(Content);
    }
    }
    

