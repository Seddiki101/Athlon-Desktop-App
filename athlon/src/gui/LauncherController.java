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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.SessionManager;
import viewCour.ExercicesController;

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
    
    AnchorPane profileL,addrecL,verifyL,gymBot,coachL,courf,exercicef,prodf,panierf;

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
            stream = new FileInputStream("B:/project/jav/fx/athlon/src/media/profil.png");
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
            verifyL = FXMLLoader.load(getClass().getResource("/setup/acconfirm.fxml"));
            gymBot = FXMLLoader.load(getClass().getResource("/Advanced/chi.fxml"));
            coachL = FXMLLoader.load(getClass().getResource("/viewemploye/employefront.fxml"));
            courf  = FXMLLoader.load(getClass().getResource("/viewCour/client.fxml"));
            exercicef  = FXMLLoader.load(getClass().getResource("/viewCour/Exercices.fxml"));
            //prodf = FXMLLoader.load(getClass().getResource("frontProduit.fxml"));
            
            setNode(coachL);
            
            if(SessionManager.getUser().getVerified()!=1)
            {
                setNode(verifyL);
            }
            
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
            
                        public void switchBot(ActionEvent event) {
           setNode(gymBot);
    }    
    
    @FXML
                    public void switchEmployefront(ActionEvent event) {
           setNode(coachL);
    }
                    
                    
    @FXML
        public void switchCourfront(ActionEvent event) {
           //setNode(courf);
           
                     try {
                 //FXMLLoader loader = new FXMLLoader(getClass().getResource("B:/project/jav/fx/equipa/Athlon-Desktop-App-Employe/athlon/src/viewCour/detailsEX.fxml"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewCour/client.fxml"));
                 Parent root69 = loader.load();
                 Scene scene69 = new Scene(root69);
                 Stage newStage69 = new Stage();
                 newStage69.setScene(scene69); 
                 newStage69.show();
             } catch (IOException ex) {
                 System.out.println(ex);
             }
           
    }                  
      
     

    @FXML
        public void switchExercicefront(ActionEvent event) {
           //setNode(exercicef);

                        try {
                 //FXMLLoader loader = new FXMLLoader(getClass().getResource("B:/project/jav/fx/equipa/Athlon-Desktop-App-Employe/athlon/src/viewCour/detailsEX.fxml"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewCour/Exercices.fxml"));
                 Parent root69 = loader.load();
                 Scene scene69 = new Scene(root69);
                 Stage newStage69 = new Stage();
                 newStage69.setScene(scene69); 
                 newStage69.show();
             } catch (IOException ex) {
                 System.out.println(ex);
             }
           
           
    }   

    @FXML
    private void switchProfileL(MouseEvent event) {
    }
    
    
    
    @FXML
           public void switchProduitfront(ActionEvent event) throws IOException {
          // setNode(prodf);
           
                                try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/frontProduit.fxml"));
                 Parent root69 = loader.load();
                 Scene scene69 = new Scene(root69);
                 Stage newStage69 = new Stage();
                 newStage69.setScene(scene69); 
                 newStage69.show();
             } catch (IOException ex) {
                 System.out.println(ex);
             }
                            
           }
    
            
               @FXML
           public void switchPanierfront(ActionEvent event) {
           //setNode(prodf);
   
                                try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewCommande/Base.fxml"));
                 Parent root69 = loader.load();
                 Scene scene69 = new Scene(root69);
                 Stage newStage69 = new Stage();
                 newStage69.setScene(scene69); 
                 newStage69.show();
             } catch (IOException ex) {
                 System.out.println(ex);
             }
           
           
           }
           
           
           
           
              @FXML
           public void switchArticlefront(ActionEvent event) {
           //setNode(prodf);
   
                                try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewArticle/Base.fxml"));
                 Parent root69 = loader.load();
                 Scene scene69 = new Scene(root69);
                 Stage newStage69 = new Stage();
                 newStage69.setScene(scene69); 
                 newStage69.show();
             } catch (IOException ex) {
                 System.out.println(ex);
             }
           
           
           }
           

}
