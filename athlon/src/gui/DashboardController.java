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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    @FXML
    private Button btnProfile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    //this loads pages as tabbed widget in qt 
    private void loadPager(String page)
    {
        Parent root = null ;
        
        try {
            
            root = FXMLLoader.load(getClass().getResource( "/gui/"+ page +".fxml" ));
        } catch (IOException ex) {
             System.out.println(ex.getMessage());
        }
        
        bpDashboard.setCenter(root);
        //apDashboard.getChildren().add(root);
        
    }
    
    @FXML
    private void btnProfileClicked()
    {
        //loadPager("Profile");
        
        try {   
           // AnchorPane pp = FXMLLoader.load(getClass().getResource("/gui/Profile.fxml"));
             // apDashboard.getChildren().setAll(pp);
            
                          Parent t = FXMLLoader.load(getClass().getResource("/gui/Profile.fxml"));
                          Stage stage = new Stage();
                          stage = (Stage)(btnProfile).getScene().getWindow();
                          Scene scene = new Scene(t);
                          stage.setScene(scene);
                          stage.show();
             
        } catch (IOException ex) {
             System.out.println(ex.getMessage());
        }
        
        

        
      
    }
    
}
