/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewUser;

import entity.User;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import util.ConnectionDB;

/**
 * FXML Controller class
 *
 * @author k
 */
public class ListUtilisateurController implements Initializable {

    @FXML
    private GridPane gpUsers;
    
    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    User user = null;
    ObservableList<User> UserList = FXCollections.observableArrayList();

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            gpUsers.add(new Label("First Name"), 0, 0);
            gpUsers.add(new Label("Last Name"), 1, 0);
            gpUsers.add(new Label("Email"), 2, 0);
            gpUsers.add(new Label("Phone"), 3, 0);
            gpUsers.add(new Label("Date Ins"), 4, 0);
            
            ObservableList<User> listu = getUserList();
                int rowIndex = 1;
                for (User user : listu) {
                gpUsers.add(new Label(user.getNom()), 0, rowIndex);
                gpUsers.add(new Label(user.getPrenom()), 1, rowIndex);
                gpUsers.add(new Label(user.getEmail()), 2, rowIndex);
                gpUsers.add(new Label("" + user.getPhone()), 3, rowIndex);
                gpUsers.add(new Label("" + user.getDateins()), 4, rowIndex);
                rowIndex++;
    }
            
            
    }    
    
    
        public ObservableList<User> getUserList() {
        cnx = ConnectionDB.getInstance().getCnx();
        UserList.clear();

        try {
            String query2 = "SELECT * FROM  user ";
            PreparedStatement smt = cnx.prepareStatement(query2);
            User usr;
            ResultSet rs = smt.executeQuery();

            while (rs.next()) {
                usr = new User(rs.getInt("id"), rs.getString("email"), rs.getString("roles"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("phone"), rs.getDate("dateins"));
                //System.out.println(usr);
                UserList.add(usr);
            }
            //System.out.println(UserList);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return UserList;
    }

    
}
