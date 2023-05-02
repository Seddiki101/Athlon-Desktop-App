/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Produit;
import entity.Categorie;
import services.ServiceCategorie;
import services.ServiceProduit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;



/**
 * FXML Controller class
 *
 * @author msi
 */
public class CategorieController implements Initializable {
ServiceCategorie sc =new ServiceCategorie();
    ObservableList<Categorie> CategorieList;
   int index=-1;
   
@FXML
    private TextField IdCatFiled;
    @FXML
    private TextField nomCatFiled;
    @FXML
    private TextField imageCatFiled;
    @FXML
    private Button AjouterCat;
    @FXML
    private TableView<Categorie> TableViewCat;
    @FXML
    private Button modifierCat;
    @FXML
    private Button supprimerCat;
    @FXML
    private TableColumn<Categorie, Integer> idCat;
    @FXML
    private TableColumn<Categorie, String> nomCat;
    private TableColumn<Categorie, String> imageCat;

    /**
     * Initializes the controller class.
     */
    
    public void updateTable() {
           ObservableList<Categorie> Categorie = FXCollections.observableArrayList(sc.afficherCategorie());

         idCat.setCellValueFactory(new PropertyValueFactory<>("id"));
           nomCat.setCellValueFactory(new PropertyValueFactory<>("nom"));
         
      // imageCat.setCellValueFactory(new PropertyValueFactory<>("image"));

    //    System.out.println("affichage" + sc.afficherCategorie());
         TableViewCat.setItems(Categorie);
     }
    
    public void show() {
        ObservableList<Categorie> CategorieList = FXCollections.observableArrayList(sc.afficherCategorie());
       // System.out.println("affichage" + sc.afficherCategorie());
              idCat.setCellValueFactory(new PropertyValueFactory<>("id"));
       nomCat.setCellValueFactory(new PropertyValueFactory<>("nom"));
      
       // imageCat.setCellValueFactory(new PropertyValueFactory<>("image"));
        
        TableViewCat.setItems(CategorieList);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     show();
    }    

    @FXML
    private void AjouterCat(ActionEvent event) {
    String nomCategorie = nomCatFiled.getText();
    String imageCategorie = imageCatFiled.getText();
    
    // Vérification de la saisie du nom de la catégorie
    if (nomCategorie.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Veuillez saisir un nom de catégorie valide.");
        return;
    }
    
    // Vérification de la saisie du chemin d'accès de l'image de la catégorie
    //if (imageCategorie.isEmpty()) {
      //  JOptionPane.showMessageDialog(null, "Veuillez saisir un chemin d'accès d'image de catégorie valide.");
      //  return;
   // }
    
    // Toutes les saisies sont valides, création et ajout de la catégorie
    Categorie c = new Categorie();
    c.setNom(nomCategorie);
    c.setImage(imageCategorie);
    
    sc.ajouterCategorie(c);
    updateTable();
    JOptionPane.showMessageDialog(null, "Catégorie ajoutée.");
}

    @FXML
    private void modifierCat(ActionEvent event) {
          Categorie  c = new  Categorie (); 
        c.setId(Integer.parseInt(IdCatFiled.getText()));
       c.setNom(nomCatFiled.getText());
       
      //  c.setImage(imageCatFiled.getText());

        sc.modifierCategorie (c);
      updateTable();
        JOptionPane.showMessageDialog(null, "Categorie modifiée");
    }

    @FXML
    private void supprimerCat(ActionEvent event) {
        Categorie c = new Categorie();
        c.setId(Integer.parseInt(IdCatFiled.getText()));
       c.setNom(nomCatFiled.getText());
       
     //   c.setImage(imageCatFiled.getText());

        sc.supprimerCategorie(c);
       updateTable();
        JOptionPane.showMessageDialog(null, "Categorie supprimée");
    }

    @FXML
    private void getSelectedCat(MouseEvent event) {
        index = TableViewCat.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        IdCatFiled.setText(idCat.getCellData(index).toString());
       nomCatFiled.setText(nomCat.getCellData(index));
        
         imageCatFiled.setText(imageCat.getCellData(index));
    }
   
    
}
