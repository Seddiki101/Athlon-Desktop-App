/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Services.ServiceProduit;
import Entities.Produit;
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
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class ProduitController implements Initializable {
    
    ServiceProduit sc =new ServiceProduit();
    ObservableList<Produit> ProduitList;
   int index=-1;

    @FXML
    private Button AddP;
    @FXML
    private TextField idProduitField;
    @FXML
    private TextField nomProduitFiled;
    @FXML
    private TextField brandProduitFiled;
    @FXML
    private TextField descriptionProduitFiled;
    @FXML
    private TextField prixProduitFiled;
    @FXML
    private TextField imageProduitFiled;
    @FXML
    private TableColumn<Produit, Integer> idProduit;
    @FXML
    private TableColumn<Produit, String> NomProduit;
    @FXML
    private TableColumn<Produit, String> brandProduit;
    @FXML
    private TableColumn<Produit, String> DescriptionProduit;
    @FXML
    private TableColumn<Produit, Float> PrixProduit;
    @FXML
    private TableColumn<Produit, String> imageProduit;
    @FXML
    private TableView<Produit>TableView;
    @FXML
    private Button modifierProduit;
    @FXML
    private Button supprimerProduit;

    /**
     * Initializes the controller clas
     * @param url
     */
    
    
    
   
         public void updateTable() {
           ObservableList<Produit> Produit = FXCollections.observableArrayList(sc.afficherProduit());

         idProduit.setCellValueFactory(new PropertyValueFactory<>("id"));
           NomProduit.setCellValueFactory(new PropertyValueFactory<>("nom"));
         brandProduit.setCellValueFactory(new PropertyValueFactory<>("brand"));
       DescriptionProduit.setCellValueFactory(new PropertyValueFactory<>("description"));
        PrixProduit.setCellValueFactory(new PropertyValueFactory<>("prix"));
       imageProduit.setCellValueFactory(new PropertyValueFactory<>("image"));

        System.out.println("affichage" + sc.afficherProduit());
         TableView.setItems(Produit);
     }
        
        

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    show();
    }    

    public void show() {
        ObservableList<Produit> ProduitList = FXCollections.observableArrayList(sc.afficherProduit());
        System.out.println("affichage" + sc.afficherProduit());
              idProduit.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomProduit.setCellValueFactory(new PropertyValueFactory<>("nom"));
       brandProduit.setCellValueFactory(new PropertyValueFactory<>("brand"));
        DescriptionProduit.setCellValueFactory(new PropertyValueFactory<>("description"));
       PrixProduit.setCellValueFactory(new PropertyValueFactory<>("prix"));
        imageProduit.setCellValueFactory(new PropertyValueFactory<>("image"));
        
       TableView.setItems(ProduitList);
    }
    
    @FXML
    private void ajouterP(ActionEvent event) {
    String nomProduit = nomProduitFiled.getText();
    String brandProduit = brandProduitFiled.getText();
    String descriptionProduit = descriptionProduitFiled.getText();
    String prixProduit = prixProduitFiled.getText();
    String imageProduit = imageProduitFiled.getText();
    
    // Vérification de la saisie du nom du produit
    if (nomProduit.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Veuillez saisir un nom de produit valide.");
        return;
    }
    
    // Vérification de la saisie de la marque du produit
    if (brandProduit.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Veuillez saisir une marque de produit valide.");
        return;
    }
    
    // Vérification de la saisie de la description du produit
    if (descriptionProduit.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Veuillez saisir une description de produit valide.");
        return;
    }
    
    // Vérification de la saisie du prix du produit
    if (!prixProduit.matches("^\\d+(\\.\\d{1,2})?$")) {
        JOptionPane.showMessageDialog(null, "Veuillez saisir un prix de produit valide.");
        return;
    }
    
    // Vérification de la saisie du chemin d'accès de l'image du produit
    if (imageProduit.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Veuillez saisir un chemin d'accès d'image de produit valide.");
        return;
    }
    
    // Toutes les saisies sont valides, création et ajout du produit
    Produit c = new Produit();
    c.setNom(nomProduit);
    c.setBrand(brandProduit);
    c.setDescription(descriptionProduit);
    c.setPrix(Float.parseFloat(prixProduit));
    c.setImage(imageProduit);
    
    sc.ajouterProduit(c);
    updateTable();
    JOptionPane.showMessageDialog(null, "Produit ajouté.");
}

    @FXML
    private void getSelected(MouseEvent event) {
      index =  TableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        idProduitField.setText(idProduit.getCellData(index).toString());
        nomProduitFiled.setText(NomProduit.getCellData(index));
        brandProduitFiled.setText(brandProduit.getCellData(index));
        descriptionProduitFiled.setText(DescriptionProduit.getCellData(index));
        prixProduitFiled.setText(PrixProduit.getCellData(index).toString());
         imageProduitFiled.setText(imageProduit.getCellData(index));
    }

    @FXML
    private void ModifierProduit(ActionEvent event) {
        
         Produit c = new Produit();
        c.setId(Integer.parseInt(idProduitField.getText()));
       c.setNom(nomProduitFiled.getText());
        c.setBrand(brandProduitFiled.getText());
        c.setDescription(descriptionProduitFiled.getText());
        c.setPrix(Float.parseFloat(prixProduitFiled.getText()));
        c.setImage(imageProduitFiled.getText());

        sc.modifierProduit(c);
      updateTable();
        JOptionPane.showMessageDialog(null, "Produit modifié");

    }

    @FXML
    private void supprimerProduit(ActionEvent event) {
         Produit c = new Produit();
        c.setId(Integer.parseInt(idProduitField.getText()));
       c.setNom(nomProduitFiled.getText());
        c.setBrand(brandProduitFiled.getText());
        c.setDescription(descriptionProduitFiled.getText());
        c.setPrix(Float.parseFloat(prixProduitFiled.getText()));
        c.setImage(imageProduitFiled.getText());

        sc.supprimerProduit(c);
       updateTable();
        JOptionPane.showMessageDialog(null, "Produit supprimé");
    }

    
    
}