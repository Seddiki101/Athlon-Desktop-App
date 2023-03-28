/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author k
 */
public class DashboardController implements Initializable {

    @FXML
    private BorderPane bpDashboard;
    @FXML
    private AnchorPane apDashboard;

    AnchorPane profile,listUsr,listRecs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             // preparing the pages
            try {
          //        profile = FXMLLoader.load(getClass().getResource("/gui/page.fxml"));
            profile = FXMLLoader.load(getClass().getResource("/viewUser/Profile.fxml"));
            listUsr = FXMLLoader.load(getClass().getResource("/viewUser/ListUser.fxml"));
            listRecs = FXMLLoader.load(getClass().getResource("/viewReclamation/ListReclamation.fxml"));
          //  setNode(listUsr);
            
            } catch (IOException ex) {
            System.out.println(ex);
            }
    }
    
        private void setNode(Node node) {
        apDashboard.getChildren().clear();
        apDashboard.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    
    
    public void switchUserList(ActionEvent event) {
        setNode(listUsr);
    }
    
    public void switchRecList(ActionEvent event) {
        setNode(listRecs);
    }
        
    public void switchProfile(ActionEvent event) {
        //this won t work unless you login
           setNode(profile);
    }
    
    
}
