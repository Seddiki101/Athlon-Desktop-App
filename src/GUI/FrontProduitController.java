/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Produit;
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

}
