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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.mail.MessagingException;
import util.ConnectionDB;
import util.HelperV2;
import util.PwdHasher;

/**
 * FXML Controller class
 *
 * @author k
 */
public class ForgotPassController implements Initializable {

    @FXML
    private TextField fpEmail;
    @FXML
    private TextField fpVcode;
    @FXML
    private PasswordField fpass;
    @FXML
    private PasswordField fpassC;
    @FXML
    private AnchorPane apForgotpss;
    @FXML
    private AnchorPane apForgotmail;
    @FXML
    private AnchorPane apForgotVerification;
    
    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    
    private int idu;
    private String clef;//fancy old french
    @FXML
    private Label lblErrorFP;
    
    

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
             lblErrorFP.setVisible(false);
            apForgotpss.setVisible(false);
            apForgotVerification.setVisible(false);
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public String getClef() {
        return clef;
    }

    public void setClef(String clef) {
        this.clef = clef;
    }

     
     //recherche d user
    @FXML
     public void searchForgetfulUser( )
     {
         
        if(fpEmail.getText().isEmpty() )
        {
         //msg error
        }
        else
        {
            cnx = ConnectionDB.getInstance().getCnx();
            String requete4="select * from user where email=?";
                        try {
            PreparedStatement pst = cnx.prepareStatement(requete4);
            pst.setString(1, fpEmail.getText() );
           
            ResultSet rs= pst.executeQuery();
            
            if(rs.next()){
             int idu =rs.getInt("id");  
            //check if mail is verified
            int v=rs.getInt("is_verified");
            
            if(v==1){

                    String k = HelperV2.getRondoKey(6);
                
                    try {
                        HelperV2.sendVariableEmail(fpEmail.getText() , "Password verification Code : "+ k +"\n if you did not perform this action . Contact tech support ","Forgot Password " );                      
                    } catch (MessagingException ex) {
                        System.out.println(ex);
                    }
                    
                    System.out.println("code reset pwd "+k);
                    
                        //show other things
                     apForgotVerification.setVisible(true);
                     //hide somethings
                      apForgotmail.setVisible(false);
                      
                      //set global vars
                      setIdu(idu);
                      setClef(k);
                     metamorphosis();
                
            }
            //un verified users can t be trusted
            
            }
            
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            
            
            
        }
        
      }        
     
     //kafka
    @FXML
     public void metamorphosis()
     {
        int i=getIdu();
        String s=getClef();
                 
         
         if(! fpVcode.getText().isEmpty() )
         {
             if( s==fpVcode.getText() ){

             apForgotpss.setVisible(true);
             apForgotVerification.setVisible(false);
             apForgotpss.setVisible(true);
             }
             //System.out.println("preliminary " + apForgotpss.isVisible() );
             
         }   
     }
     
    @FXML
     public void regainAccess()
     {
         int i=getIdu();
         if ( fpass.getText().isEmpty() || fpassC.getText().isEmpty()  )
         {
            //msg error
             System.out.println("password can t be empty ");
             lblErrorFP.setText("new password can t be empty ");
             lblErrorFP.setVisible(true);
         }
         else{
              if( fpass.getText().equals( fpassC.getText() ) )
              {
                //msg error  
                  System.out.println("password not match ");
                  lblErrorFP.setText("passwords do not match ");
                  lblErrorFP.setVisible(true);
              }
              else{
                  //update pss
                               cnx = ConnectionDB.getInstance().getCnx();
            String query="update user set password=? where id=? ";
            try {
                
                String pewDie;
                    pewDie = PwdHasher.hashPassword(fpassC.getText());
                
            PreparedStatement smt = cnx.prepareStatement(query);
            smt.setString(1, pewDie );
            smt.setInt(2, i );
            
            smt.executeUpdate();
            System.out.println("test 55");
            
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
               
            //msg de jawek bahi
            //System.out.println("final");
                     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                     alert.setTitle(" Athlon :: Successfully changed password ");
                     alert.setHeaderText(null);
                     alert.setContentText("Proceed to sign in");
                     alert.showAndWait();
                     
                //now change the anchorpane here
                //FXMLLoader loader = new FXMLLoader(getClass().getResource("/setup/setup.fxml"));
                //setupController controllerB = loader.getController();
                //controllerB.switchForgotten2();
            
              }
             
         }   
     } 



}
