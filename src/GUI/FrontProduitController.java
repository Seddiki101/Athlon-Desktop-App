/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Categorie;
import Entities.Produit;
import Services.ServiceCategorie;
import Services.ServiceProduit;
import Util.MyDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class FrontProduitController implements Initializable {

    private List<Produit> recentlyadd;

    Connection cnx;
    Statement stmt;
    @FXML
    private Pagination pagination;

    private int rowsPerPage = 2;
    private int pageCount;
    @FXML
    private TextField prixminFiled;
    @FXML
    private TextField prixmaxFiled;
    @FXML
    private Button filtrerPrixButton;
    @FXML
    private VBox categoriesBox;

    
    private List<Categorie> categories;
private int selectedCategoryId;
    public FrontProduitController() {
        cnx = MyDB.getInstance().getCnx();
    }
    
   
    
    

    FXMLLoader loader = new FXMLLoader(getClass().getResource("Rating.fxml"));
/*
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ServiceProduit sm = new ServiceProduit();
        recentlyadd = new ArrayList<>(sm.afficherProduit());
        try {
            for (Produit value : recentlyadd) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(value);
                cardlayoout.getChildren().add(cardBox);
        
            
            
}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   */  
    private int cardCount = 0;
    private VBox rowContainer;

    public void initialize(URL url, ResourceBundle rb) {

        ServiceProduit sm = new ServiceProduit();
        recentlyadd = new ArrayList<>(sm.afficherProduit());
        pageCount = (int) Math.ceil((double) recentlyadd.size() / rowsPerPage);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);
         // Récupérer les catégories depuis la base de données
    ServiceCategorie sc = new ServiceCategorie();
    categories = sc.afficherCategorie();
   
    // Ajouter des boutons pour chaque catégorie
    for (Categorie categorie : categories) {
        Button button = new Button(categorie.getNom());
        button.setOnAction(event -> {
            // Enregistrer l'ID de la catégorie sélectionnée
            selectedCategoryId = categorie.getId();
            // Afficher les produits associés à la catégorie sélectionnée
            displayProduits();
        });
       HBox.setMargin(button, new Insets(10, 10, 10, 10)); // marge autour du bouton
categoriesBox.getChildren().add(button);
    }

    // Afficher les produits par défaut (toutes les catégories)
    selectCategory(selectedCategoryId);
    selectedCategoryId = 0;
    displayProduits();


    }
    private void displayProduits() {
    // Récupérer les produits depuis la base de données pour la catégorie sélectionnée
    ServiceProduit sm = new ServiceProduit();
    List<Produit> produits;
    if (selectedCategoryId == 0) {
        produits = sm.afficherProduit();
    } else {
        produits = sm.afficherProduitParCategorie(selectedCategoryId);
    }

    // Mettre à jour la liste de produits récents
    recentlyadd.clear();
    recentlyadd.addAll(produits);

    // Afficher les produits avec la pagination
    pageCount = (int) Math.ceil((double) recentlyadd.size() / rowsPerPage);
    pagination.setPageCount(pageCount);
    pagination.setPageFactory(this::createPage);
    }

    private Region createPage(int pageIndex) {
       int startIndex = pageIndex * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, recentlyadd.size());
        List<Produit> pageProducts = recentlyadd.subList(startIndex, endIndex);

       HBox pageContainer = new HBox(); // wrap VBox in HBox
    pageContainer.setSpacing(20.0);
    pageContainer.setAlignment(Pos.CENTER); // set alignment to CENTER


        try {
            for (Produit value : pageProducts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(value);
                pageContainer.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pageContainer;
    }
    @FXML
    private void filterPrxi(ActionEvent event) {
        
       float min = Float.parseFloat(prixminFiled.getText());
        float max = Float.parseFloat(prixmaxFiled.getText());
        System.out.println("min: "+ min);
        System.out.println("max: "+ max);
        ServiceProduit sm = new ServiceProduit();
        recentlyadd = new ArrayList<>(sm.filterProduitsParPrix(min, max));
        System.out.println("produits with filtre: " + sm.filterProduitsParPrix(min, max));
        pageCount = (int) Math.ceil((double) recentlyadd.size() / rowsPerPage);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);
    }

public void selectCategory(int categoryId) {
    // Mettre à jour la variable selectedCategoryId avec l'ID de la catégorie sélectionnée
    selectedCategoryId = categoryId;
    System.out.println("Catégorie sélectionnée : " + selectedCategoryId);

    // Appeler la méthode displayProduits() pour afficher les produits de la catégorie sélectionnée
    displayProduits();
}
}
