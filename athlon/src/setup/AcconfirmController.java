/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.mail.MessagingException;
import util.ConnectionDB;
import util.HelperV2;
import util.PwdHasher;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author k
 */
public class AcconfirmController implements Initializable {

    @FXML
    private AnchorPane apHideConfirm;
    @FXML
    private TextField tfverf;
    private String cle;
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
        apHideConfirm.setVisible(false);
    }    

    public String getCle() {
        return cle;
    }

    public void setCle(String cle) {
        this.cle = cle;
    }
    
    
    
    
    //show this page when user logs in if he is not confirmed
    @FXML
    public void sendVmail()
    {
        String ky = HelperV2.getRondoKey(6);
        setCle(ky);
        System.out.println("code verif "+ky);
        
         try {
                        HelperV2.sendVariableEmail(SessionManager.getUser().getEmail() , "Email verification Code : "+ ky ,"Email Verification " );                      
                    } catch (MessagingException ex) {
                        System.out.println(ex);
                    }
        
         
        apHideConfirm.setVisible(true);
   
    }
    
    @FXML
    public void Verifikation()
    {
        String kle= getCle();
        
        //System.out.println("test 31 ");
         if(! tfverf.getText().isEmpty() )
         {
             //System.out.println("test 32.5 " + tfverf.getText() );
             
             
             
             if( tfverf.getText().equals(kle)  ) {

                      //System.out.println("test 33 ");
                 
            cnx = ConnectionDB.getInstance().getCnx();
            String query="update user set is_verified=1 where id=? ";
            try {                
            PreparedStatement smt = cnx.prepareStatement(query);
            smt.setInt(1, SessionManager.getUser().getId() );            
            smt.executeUpdate();
            
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
                     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                     alert.setTitle(" Athlon :: Verification successful ");
                     alert.setHeaderText(null);
                     alert.setContentText("Your email has been verified ");
                     alert.showAndWait();      
         }         
         } 
        
    }
    
}
