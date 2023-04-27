/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ath;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author k
 */
public class Ath extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       // Parent root = FXMLLoader.load(getClass().getResource("/viewCour/GUI/client.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/setup/setup.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/gui/Dashboard.fxml"));
        Scene scene = new Scene(root);
        
        stage.setTitle("Athlon");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
