/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewReclamation;

import entity.Reclamation;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.ConnectionDB;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author k
 */
public class ReclamationCRUDController implements Initializable {

    @FXML
    private TextField tfTitre;
    @FXML
    private TextArea taDesc;
    @FXML
    private Button btnSubmitRec;
    @FXML
    private Button btnEditRec;
    
    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    int modifiableID;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnEditRec.setVisible(false);
        btnSubmitRec.setVisible(true); 
    }


    @FXML
    private void createReclamation()
    {
             cnx = ConnectionDB.getInstance().getCnx();
            String query="INSERT INTO reclamation ( titre, desipticon, user_x_id)"
                    + "VALUES (?, ?, ?)";   
            
         //
                     
            try {
                if(tfTitre.getText().isEmpty() | taDesc.getText().isEmpty() )
                {
                  //empty fields
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Athlon :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Entrer all blank fields !!");
                alert.showAndWait();  
                }
                
                else if ( tfTitre.getText().length() < 3 | taDesc.getText().length() < 4 ){
                //user bs
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Athlon :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Titre et desc doivent etre significative !!");
                alert.showAndWait();  
                }
                
                else{

                    PreparedStatement smt = cnx.prepareStatement(query);
                    
            smt.setString(1, tfTitre.getText());
            smt.setString(2, taDesc.getText());
            // maybe add condition if null dont send //SessionManager.getUser().getId() // big problem
            smt.setInt(3, SessionManager.getUser().getId() ); //getting current user id // 35 

            //System.out.print("hehe");    
            
            smt.executeUpdate();
             
                System.out.println("ajout avec succee");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Athlon :: BIENVENNUE");
                alert.setHeaderText(null);
                alert.setContentText("Reclamation envoye");
                alert.showAndWait();
                
                }
                
                
            }catch(SQLException ex){
         System.out.println(ex.getMessage());
            }

         //
            
    }
    
    public void receiveRec(Reclamation rico)
    {
        tfTitre.setText(rico.getTitre());
        taDesc.setText(rico.getDesc());
        modifiableID=rico.getId();
        
        btnEditRec.setVisible(true);
        btnSubmitRec.setVisible(false); 
    }
    
    public void updateRec()
    {       cnx = ConnectionDB.getInstance().getCnx();
            String requete2="update Reclamation set titre=?,desipticon=? where id=? ";
            try {
            PreparedStatement pst = cnx.prepareStatement(requete2);
            pst.setString(1, tfTitre.getText() );
            pst.setString(2, taDesc.getText() );
            pst.setInt(3, modifiableID );
            
            //System.out.println("is "+modifiableID);
           pst.executeUpdate();
            
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            //System.out.println("test 3");
            //now redirect the user to another page
        
    }
    
    
    
}
