/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.ProductRating;
import Entities.Produit;
import Services.ServiceRating;
import Util.MyDB;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
   
 ServiceRating sc =new ServiceRating();
 
 private Produit product;
    private Connection connection;
    @FXML
    private AnchorPane pane;
    @FXML
    private RadioButton star3Button;
    @FXML
    private RadioButton star4Button;
    @FXML
    private RadioButton star5Button;
    @FXML
    private RadioButton star2Button;
    @FXML
    private RadioButton star1Button;
    /**
     * Initializes the controller class.
     */
    private int rating = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        star1Button.setOnAction(e -> rating = 1);
    star2Button.setOnAction(e -> rating = 2);
    star3Button.setOnAction(e -> rating = 3);
    star4Button.setOnAction(e -> rating = 4);
    star5Button.setOnAction(e -> rating = 5);
    }
       
    
    private List<ProductRating> productRatings = new ArrayList<>();

  

  
     public void saveRating(int productId, String userName, int rating) {
       
        
       
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Athlon", "root", "");
        String query = "INSERT INTO ratings (id_produit, userName, rating) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, productId);
        stmt.setString(2, userName);
        stmt.setInt(3, rating);
        stmt.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}
   
    
    @FXML
    private void addProductRating(ActionEvent event) throws SQLException {
       int productId = 1; // Replace with the actual product ID
    String userName = userNameTextField.getText();
     if (userName.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Veuillez saisir votre nom svp .");
        return;
    }
    saveRating(productId, userName, rating);
        
    }
    }


    


