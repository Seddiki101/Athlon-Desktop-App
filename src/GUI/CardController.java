/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Produit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class CardController implements Initializable {

    @FXML
    private HBox box;
   
    
    private  String [] colors = {"B9E5FF","BDB2FE","FB9AA8","FF5056"} ;
    @FXML
    private Label ProduitName;
    @FXML
    private ImageView ProduitImg;
    @FXML
    private Label description;
    @FXML
    private Label prixP;
    @FXML
    private Label catp;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     public void setData(Produit modele)
    {
      Image image = new Image(getClass().getResourceAsStream(modele.getImage())) ;
       ProduitImg.setImage(image);
        ProduitName.setText(modele.getNom());
        description.setText(modele.getDescription());
       prixP.setText(String.valueOf(modele.getPrix()));
        
          catp.setText(modele.getNomCategory());
        box.setStyle("-fx-background-color: #" +colors[(int)(Math.random()*colors.length)] 
                +" ; -fx-background-radius: 15;"
                +"-fx-effect : dropshadow(three-pass-box , rgba(0,0,0,0.1) , 10 , 0 ,0 , 10 ) ;");
        
         
        
        
         
        
    }
 @FXML
    private void noteP(ActionEvent event) throws IOException {
     box.getChildren().clear();
        Parent Content = FXMLLoader.load(getClass().getResource("Rating.fxml"));
        box.getChildren().setAll(Content); 
            // Set the productId attribute with the ID of the current product
    
    }


}
