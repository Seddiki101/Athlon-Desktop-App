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
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import util.ConnectionDB;

/**
 * FXML Controller class
 *
 * @author k
 */
public class ListUserController implements Initializable {

    @FXML
    private TableView<User> tvUser;
    @FXML
    private TableColumn<?, ?> colNom;
    @FXML
    private TableColumn<?, ?> colPrenom;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colPhone;
    @FXML
    private TableColumn<?, ?> colDateins;
    @FXML
    private TextField tfsearch;

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    User user = null;
    ObservableList<User> UserList = FXCollections.observableArrayList();
    
    @FXML
    private Button btnTriDateins;
    @FXML
    private Button btnTriNom;
    @FXML
    private Button btnModifUsr;
    @FXML
    private Button btnSupprimUsr;
    @FXML
    private Button btnPDFusr;
    @FXML
    private Button btnRefresh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    @FXML
    public void refreshing() {
        ObservableList<User> list = getUserList();

        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colDateins.setCellValueFactory(new PropertyValueFactory<>("dateins"));
        //colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        //colroles.setCellValueFactory(new PropertyValueFactory<>("roles"));

        tvUser.setItems(list);
    }

    @FXML
    public void sortingName() {
        ObservableList<User> listusr = getUserList();
        FXCollections.sort(listusr, Comparator.comparing(User::getNom));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colDateins.setCellValueFactory(new PropertyValueFactory<>("dateins"));
        tvUser.setItems(listusr);
    }

    @FXML
    public void sortingDateins() {
        ObservableList<User> listusr = getUserList();
        FXCollections.sort(listusr, Comparator.comparing(User::getDateins));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colDateins.setCellValueFactory(new PropertyValueFactory<>("dateins"));
        tvUser.setItems(listusr);
    }

    @FXML
    public void searching() {

        FilteredList<User> filteredList = new FilteredList<>(UserList, p -> true);

        // add a change listener to the search field to update the filtered list
        tfsearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(user -> {
                // if search text is empty, show all users
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // convert search text to lowercase and compare with user attributes
                String lowerCaseSearch = newValue.toLowerCase();
                return user.getEmail().toLowerCase().contains(lowerCaseSearch)
                        || user.getNom().toLowerCase().contains(lowerCaseSearch)
                        || user.getPrenom().toLowerCase().contains(lowerCaseSearch)
                        || user.getDateins().toString().toLowerCase().contains(lowerCaseSearch);
            });
        });

        // wrap the filtered list in a SortedList to enable sorting
        SortedList<User> sortedList = new SortedList<>(filteredList);

        // bind the sorted list to the TableView
        tvUser.setItems(sortedList);
        //

    }
    
    
    @FXML
        private void Suppression(ActionEvent event) {    
        User usr = (User) tvUser.getSelectionModel().getSelectedItem();
        if( usr!=null ) {
        //
                try {
            String requete="delete from user where id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,usr.getId());
            pst.executeUpdate();
            
            System.out.println("Utlisateur est supprimée");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        //
        refreshing();        
        Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Athlon :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Utilisateur supprimé");
                alert.showAndWait();  
    }
        else{
            System.out.println("User not selected ");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Athlon :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a user from the table ");
                alert.showAndWait();  
        }
    }    

    public void modification()
    {
        
    }
        
        
        
}
