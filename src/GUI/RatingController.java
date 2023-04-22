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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class RatingController implements Initializable {

    @FXML
    private TextField userNameTextField;
    @FXML
    private Rating productRating;
   
 ServiceRating sc =new ServiceRating();
 
 private Produit product;
    private Connection connection;
    @FXML
    private AnchorPane pane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
       
    
    private List<ProductRating> productRatings = new ArrayList<>();

  

  /*  public void addProductRating() {
        String userName = userNameTextField.getText();
        float rating = (float) productRating.getRating();
        productRatings.add(new ProductRating(userName, rating));
        float averageRating = calculateAverageRating();
        System.out.println("Average rating: " + averageRating);
        // Ajoutez ici le code pour stocker la note moyenne dans votre base de données ou ailleurs
    }
*/
    private float calculateAverageRating() {
        float totalRating = 0;
        for (ProductRating productRating : productRatings) {
            totalRating += productRating.getRating();
        }
        return totalRating / productRatings.size();
    }

   @FXML
    private void addProductRating(ActionEvent event) throws SQLException {
       
        
        
          String userName = userNameTextField.getText();
        float rating = (float) productRating.getRating();
          ProductRating t = new ProductRating(userName,rating);
        productRatings.add(new ProductRating(userName, rating));
        
         
       // float averageRating = calculateAverageRating();
     //  System.out.println("Average rating: " + averageRating);
             //Ajoutez ici le code pour stocker la note moyenne dans votre base de données ou ailleurs
    

ProductRating c = new ProductRating();

c.setUserName(userName);
c.setRating(rating);

sc.ajouterRating(c);
addRatingToDatabase(product.getId(), userName, (int) rating);
      
    }
     
    public void addRatingToDatabase(int id_produit, String userName, int rating) {
  try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ratings");
       PreparedStatement statement = connection.prepareStatement("INSERT INTO `ratings` (`id_produit`, `user_name`, `rating`) VALUES (?, ?, ?)")) {
    statement.setInt(1, id_produit);
    statement.setString(2, userName);
    statement.setInt(3, rating);
    statement.executeUpdate();
  } catch (SQLException e) {
    e.printStackTrace();
  }
}
    
    
   /* @FXML
    private void addProductRating(ActionEvent event) throws SQLException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("path/to/Produit.fxml"));
    ProduitController productController = loader.getController();
        ActionEvent Produit = null;

    // Appelle la fonction du ProductController
    productController.ajouterP(Produit);
        
    }*/
    }


    


