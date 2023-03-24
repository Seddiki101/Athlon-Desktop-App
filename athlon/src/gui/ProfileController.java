/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author k
 */
public class ProfileController implements Initializable {

    @FXML
    private ImageView ivProfile;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfAdress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InputStream stream;
        try {
            stream = new FileInputStream("B:/project/jav/fx/athlon/src/media/profil.png");
            Image image = new Image(stream);
            ivProfile.setImage(image);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
     tfNom.setText( SessionManager.getUser().getNom() );
     tfPrenom.setText( SessionManager.getUser().getPrenom() );
     tfEmail.setText( SessionManager.getUser().getEmail() );
     tfPassword.setText( SessionManager.getUser().getPassword() );
        
    }    
    
}
