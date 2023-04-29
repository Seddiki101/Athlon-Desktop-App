/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewUser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import util.ConnectionDB;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author k
 */
public class ProfileController implements Initializable {

    //@FXML
    //private ImageView ivProfile;
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
    @FXML
    private Button btnEditUsr;
    
    
    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        /*
        InputStream stream;
        try {
            stream = new FileInputStream("B:/project/jav/fx/athlon/src/media/profil.png");
            Image image = new Image(stream);
            ivProfile.setImage(image);
            
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        */
        
        
     tfNom.setText( SessionManager.getUser().getNom() );
     tfPrenom.setText( SessionManager.getUser().getPrenom() );
     tfEmail.setText( SessionManager.getUser().getEmail() );
     tfPassword.setText( SessionManager.getUser().getPassword() );
     tfPhone.setText( ""+SessionManager.getUser().getPhone() ); //Outstanding move
     tfAdress.setText( SessionManager.getUser().getAdres() );
        
    }    
    
    
    
    @FXML
    public void updateUsr()
    {
             cnx = ConnectionDB.getInstance().getCnx();
            String query="update user set nom=?, prenom=?, email=?, password=?,phone=?,adres=? where id=? ";
            try {
            PreparedStatement smt = cnx.prepareStatement(query);
            smt.setString(1, tfNom.getText() );
            smt.setString(2, tfPrenom.getText() );
            smt.setString(3, tfEmail.getText() );
            smt.setString(4, tfPassword.getText() );
            smt.setString(5, tfPhone.getText() );
            smt.setString(6, tfAdress.getText() );
            smt.setInt(7, SessionManager.getUser().getId() );
            
            
            smt.executeUpdate();
            //System.out.println("test 55");
            
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }
    
    
    
    
    
    
    
    
    public void showMap()
    {        
    WebView wbVw = new WebView();
    WebEngine wbEngine = wbVw.getEngine();
    wbEngine.load("https://www.openstreetmap.org");
        // add a listener to the web engine's location property
    wbEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
        // newValue will contain the URL of the selected location
        String selectedLocation = newValue;
        //System.out.println("Selected location: " + selectedLocation);
        tfAdress.setText(selectedLocation);
    });
    
    AnchorPane pane = new AnchorPane(wbVw);
    Scene scene = new Scene(pane);
    Stage stage = new Stage();
    stage.setTitle("Zoom In to your location . It will be saved");
    stage.setScene(scene);
    stage.show();
    
    }
    
    
    
    
    
    
    
    
    
}
