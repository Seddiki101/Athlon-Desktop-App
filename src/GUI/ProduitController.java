/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Entities.Categorie;
import Services.ServiceProduit;
import Entities.Produit;
import Services.ServiceCategorie;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.runtime.Debug.id;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * FXML Controller class
 *
 * @author msi
 */
public class ProduitController implements Initializable {
    
    ServiceProduit sc =new ServiceProduit();
    ObservableList<Produit> ProduitList;
   int index=-1;
   
  
   private int idCategorieToadd ;
    @FXML
    private ImageView imageProduitView;
    
    
      public int getIdCategorieToadd() {
        return idCategorieToadd;
    }

    public void setIdCategorieToadd(int idCategorieToadd) {
        this.idCategorieToadd = idCategorieToadd;
    }
String path="";
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
    @FXML
    private ComboBox<String> catPField;
    @FXML
    private TableColumn<Produit, String> CategoryP;
    @FXML
    private Button selectImageBtn;
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
       CategoryP.setCellValueFactory(new PropertyValueFactory<>("nomCategory"));
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
        CategoryP.setCellValueFactory(new PropertyValueFactory<>("nomCategory"));
       TableView.setItems(ProduitList);
       
       
        ServiceCategorie sc = new ServiceCategorie();
        List<String> nomsCataegory = new ArrayList<>();
        for (Categorie c : sc.afficherCategorie()) {
            nomsCataegory.add(c.getNom());

        }
        catPField.setItems(FXCollections.observableArrayList(nomsCataegory));

        catPField.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // get the selected Personne object based on the selected nom value
                Categorie selectedLibelle = null;
                for (Categorie c: sc.afficherCategorie()) {
                    if (c.getNom().equals(newValue)) {
                        selectedLibelle = c;
                        break;
                    }
                }
                if (selectedLibelle != null) {
                    int id = selectedLibelle.getId();
                    System.out.println("Selected Categorie id: " + id);
                    // do something with the id...
                    setIdCategorieToadd(id);
                }
            }
        });
    }


       
    
    
    @FXML
    private void ajouterP(ActionEvent event) {
        
                        Image photo=imageProduitView.getImage();

    String nomProduit = nomProduitFiled.getText();
    String brandProduit = brandProduitFiled.getText();
    String descriptionProduit = descriptionProduitFiled.getText();
    Float prixProduit =(  Float.parseFloat(prixProduitFiled.getText()));
    
    String imagePath = path;
          Produit u = new Produit(nomProduit, brandProduit, descriptionProduit, imagePath,prixProduit);



   
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
  //  if (!prixProduit.matches("^\\d+(\\.\\d{1,2})?$")) {
    //    JOptionPane.showMessageDialog(null, "Veuillez saisir un prix de produit valide.");
     //   return;
    //}
    
    // Vérification de la saisie du chemin d'accès de l'image du produit
   // if (imageProduit.isEmpty()) {
    //    JOptionPane.showMessageDialog(null, "Veuillez saisir un chemin d'accès d'image de produit valide.");
      //  return;
    //}
   // if (imageProduitFiled.getText().isEmpty()) {
     //   JOptionPane.showMessageDialog(null, "Veuillez sélectionner une image de produit.");
     //   return;
    //}
    
   

    // Toutes les saisies sont valides, création et ajout du produit
    Produit c = new Produit();
    c.setNom(nomProduit);
    c.setBrand(brandProduit);
    c.setDescription(descriptionProduit);
    c.setPrix(prixProduit);
    c.setImage(imagePath);
     c.setIdCategory(idCategorieToadd);
     
    
   

    sc.ajouterProduit(c);
    updateTable();
    JOptionPane.showMessageDialog(null, "Produit ajouté.");
    
    
}
    
   

    @FXML
    private void getSelected(MouseEvent event) {
                 Produit c=TableView.getSelectionModel().getSelectedItem();

      index =  TableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        idProduitField.setText(idProduit.getCellData(index).toString());
        nomProduitFiled.setText(NomProduit.getCellData(index));
        brandProduitFiled.setText(brandProduit.getCellData(index));
        descriptionProduitFiled.setText(DescriptionProduit.getCellData(index));
        prixProduitFiled.setText(PrixProduit.getCellData(index).toString());
 Image image = new Image(new File(c.getImage()).toURI().toString());
            imageProduitView.setImage(image);         //coachingField.setValue(coursR.getCellData(index));
        // catPField.setValue(CategoryP.getCellData(index).toString());
    }

    @FXML
    private void ModifierProduit(ActionEvent event) {
        
         Produit c = new Produit();
              String path =  imageProduit.getText();

        c.setId(Integer.parseInt(idProduitField.getText()));
       c.setNom(nomProduitFiled.getText());
        c.setBrand(brandProduitFiled.getText());
        c.setDescription(descriptionProduitFiled.getText());
        c.setPrix(Float.parseFloat(prixProduitFiled.getText()));
        c.setImage(path);
        c.setNomCategory(catPField.getValue());
        c.setIdCategory(idCategorieToadd);
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
c.setNomCategory( catPField.getValue());
        sc.supprimerProduit(c);
       updateTable();
        JOptionPane.showMessageDialog(null, "Produit supprimé");
    }

    @FXML
    private void selectImage(MouseEvent event) {
       
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose Image File");

    // Ajouter un filtre pour n'afficher que les fichiers d'images
    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
    );

    // Afficher la boîte de dialogue de sélection de fichiers
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
    // load the selected image into the image view
    path=selectedFile.getAbsolutePath().replace("\\", "/");

        Image image = new Image(selectedFile.toURI().toString());
    imageProduitView.setImage(image);
    }
    }

    
    
    

    
}