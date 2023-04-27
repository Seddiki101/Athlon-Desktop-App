/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author k
 */
public class setupController implements Initializable {

    @FXML
    private AnchorPane holderPane;

    @FXML
    private ImageView ivLogo;
    
    AnchorPane signup,signin,oublie;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 
        InputStream stream;
        
         try {
            signup = FXMLLoader.load(getClass().getResource("SignUP.fxml"));
            signin = FXMLLoader.load(getClass().getResource("signIN.fxml"));
            oublie = FXMLLoader.load(getClass().getResource("ForgotPass.fxml"));
            setNode(signin);
            
            stream = new FileInputStream("C:/Users/auo1/Desktop/javaFXvf/athlon/src/media/logo.png");
            Image image = new Image(stream);
            ivLogo.setImage(image);
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
        //     
    }    

    
        private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    
    
    @FXML
    private void switchSignin(ActionEvent event) {
        setNode(signin);
    }

    @FXML
    private void switchSignup(ActionEvent event) {
        setNode(signup);
    }
    
        @FXML
    private void switchForgotten(ActionEvent event) {
        setNode(oublie);
    }


    public void switchForgotten2() {
        setNode(oublie);
    }

    
}
