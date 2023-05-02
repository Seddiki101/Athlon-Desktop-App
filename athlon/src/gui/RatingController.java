/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import entity.ProductRating;
import entity.Produit;
import services.ServiceRating;
import util.ConnectionDB;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class RatingController implements Initializable {

    @FXML
    private TextField userNameTextField;

    ServiceRating sc = new ServiceRating();

    private Produit product;
    private Connection connection;
    @FXML
    private AnchorPane pane;
    /**
     * Initializes the controller class.
     */
    private int rating = 0;
    @FXML
    private Rating ratingFieldStar;
    private Button closeButton;
    private Stage parentStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
     

        ratingFieldStar.ratingProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                rating = newValue.intValue();
            }
        });
        

    }

    private List<ProductRating> productRatings = new ArrayList<>();

    public void saveRating(int productId, String userName, int rating) {

       
        sc.ajouterRating(productId,userName,rating);
    }

    @FXML
    private void addProductRating(ActionEvent event) throws SQLException {
        int productId = ConnectionDB.getPickedPRoductId();
        System.out.println("pickedId: " + productId);
        String userName = userNameTextField.getText();
        if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez saisir votre nom svp .");
            return;
        }
        saveRating(productId, userName, rating);

    }
    
   
    }


