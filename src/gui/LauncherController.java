/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author k
 */
public class LauncherController implements Initializable {

    @FXML
    private ImageView ivProfileLauncher;
    
    
    @FXML
    private Label tfUsernameLauncher;
    @FXML
    private AnchorPane apLauncher;
    
    AnchorPane profileL,addrecL;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tfUsernameLauncher.setText(SessionManager.getUser().getPrenom());
        
        InputStream stream;
                 try {
            //image adding         
            stream = new FileInputStream("C:/Users/auo1/Desktop/javaFX/athlon/src/media/profil.png");
            Image image = new Image(stream);
            ivProfileLauncher.setImage(image);
            //handling the bound on mouse click
           // ivProfileLauncher.setPickOnBounds(true);
            ivProfileLauncher.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setNode(profileL);
            }
        });
            
            
            
            // now the navigaton bar
            profileL = FXMLLoader.load(getClass().getResource("/viewUser/Profile.fxml"));
            addrecL = FXMLLoader.load(getClass().getResource("/viewReclamation/ReclamationCRUD.fxml"));
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        
    }    
    
    
            private void setNode(Node node) {
        apLauncher.getChildren().clear();
        apLauncher.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    
    
        public void switchProfileL(ActionEvent event) {
           setNode(profileL);
    }
        
    @FXML
            public void switchAddRecL(ActionEvent event) {
           setNode(addrecL);
    }    

    @FXML
    private void switchProfileL(MouseEvent event) {
    }
    
            
      


}
