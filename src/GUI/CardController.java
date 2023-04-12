/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Produit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    @FXML
    private ImageView VehiculeImg;
    @FXML
    private Label VehiculeName;
    @FXML
    private Label marque;
    private  String [] colors = {"B9E5FF","BDB2FE","FB9AA8","FF5056"} ;


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
       VehiculeImg.setImage(image);
        VehiculeName.setText(modele.getNom());
        marque.setText(modele.getDescription());
        box.setStyle("-fx-background-color: #" +colors[(int)(Math.random()*colors.length)] 
                +" ; -fx-background-radius: 15;"
                +"-fx-effect : dropshadow(three-pass-box , rgba(0,0,0,0.1) , 10 , 0 ,0 , 10 ) ;");
        
    }
}
