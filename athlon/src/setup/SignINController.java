/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import entity.User;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
    @FXML
    private ComboBox<String> emailDropdown;
    private static final String FILENAME = "confE.bin";
    @FXML
    private CheckBox rememberMe;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            ObservableList<String> emailList = FXCollections.observableArrayList(getEmails());
    emailDropdown.setItems(emailList);

    // show the email dropdown below the email field when it is clicked
    tfEmail.setOnMouseClicked(event -> {
        emailDropdown.show();
    });

    // update the email field with the selected email address from the dropdown
    emailDropdown.setOnAction(event -> {
        String selectedEmail = emailDropdown.getSelectionModel().getSelectedItem();
        tfEmail.setText(selectedEmail);
        emailDropdown.hide();
    });
    }







    
    
    
    
      public void saveLastConnectedUser(String email) {
        List<String> emails = getEmails();
        if (!emails.contains(email)) {
            if (emails.size() >= 3) {
                emails.remove(0);
            }
            emails.add(email);
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
                for (String savedEmail : emails) {
                    out.writeObject(savedEmail);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static List<String> getEmails() {
        List<String> emails = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME))) {
            Object obj;
            while ((obj = in.readObject()) != null) {
                if (obj instanceof String) {
                    String email = (String) obj;
                    emails.add(email);
                }
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return emails;
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
                    
                    
                    if(rememberMe.isSelected()) saveLastConnectedUser(usrEmail) ;
                    
                    
                    
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
                    
                    
                }  else {
                       
                        lblErrorLogin.setText("verify your email or password");
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
