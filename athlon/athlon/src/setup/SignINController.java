/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import entity.User;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.ConnectionDB;
import util.PwdHasher;
import util.SessionManager;


/**
 * FXML Controller class
 *
 * @author k
 */
public class SignINController implements Initializable {

    @FXML
    private AnchorPane apSigin;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField pfPWD;
    @FXML
    private Button btnSignIn;
    @FXML
    private Label lblErrorLogin;
    
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
    }    

    @FXML
    private void login() {
    String usrEmail = tfEmail.getText().toString();
    String usrPass = pfPWD.getText().toString();
  //  String usrPass = PwdHasher.hashPassword(pfPWD.getText());
    
            String query2="select * from user where email=?";
            cnx = ConnectionDB.getInstance().getCnx();
            
           try{
              PreparedStatement smt = cnx.prepareStatement(query2);
       
               smt.setString(1,usrEmail );
               //smt.setString(2,usrPass );
               ResultSet rs= smt.executeQuery();
               User p;
               //if user exists
                if(rs.next()){
                    //here teleport
                    String hashedPassword = rs.getString("password");
                    if (PwdHasher.verifyPassword(usrPass, hashedPassword)) {
                // Passwords match, user is authenticated

                    //getting user info
                     p=new User(rs.getInt("id"),rs.getString("email"),rs.getString("password"),rs.getString("roles"),rs.getString("nom"),rs.getString("prenom"),rs.getInt("phone"),rs.getString("adres"),rs.getInt("is_verified") );
                     
                     User.setCurrent_User(p);
                     SessionManager.getInstace(p);
                     //System.out.println(" email of current user ");
                     //System.out.println(User.Current_User.getEmail());
                     
                     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                     alert.setTitle(" Athlon :: Success Message");
                     alert.setHeaderText(null);
                     alert.setContentText("Vous etes connecté");
                     alert.showAndWait();
                     
                    //Now we loadview based on user role
                    if(p.getRoles().contains("ROLE_ADMIN") ) {
                      //show dashboard                                   
                          Parent t = FXMLLoader.load(getClass().getResource("/gui/Dashboard.fxml"));
                          Stage stage = new Stage();
                          stage = (Stage)(btnSignIn).getScene().getWindow();
                          Scene scene = new Scene(t);
                          stage.setScene(scene);
                          stage.show();
                    
                    }
                    else if (p.getRoles().contains("ROLE_USER") ){
                        //show regular user thingy
                        Parent t = FXMLLoader.load(getClass().getResource("/gui/launcher.fxml"));
                          Stage stage = new Stage();
                          stage = (Stage)(btnSignIn).getScene().getWindow();
                          Scene scene = new Scene(t);
                          stage.setScene(scene);
                          stage.show();
                          
                    }      
                    
                    
                }  
                    //here teleport
                }
                        else {
                        //verify your email or password
                        lblErrorLogin.setText("verify your email or password");
                    }
                  
      
           }catch(Exception ex){
           System.out.println(ex.getMessage());
    }
           
}//login
    
}