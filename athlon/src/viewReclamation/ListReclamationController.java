/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewReclamation;

import entity.Reclamation;
import entity.User;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.ConnectionDB;

/**
 * FXML Controller class
 *
 * @author k
 */
public class ListReclamationController implements Initializable {

    @FXML
    private TableView< Reclamation> tvRecs;
    @FXML
    private TableColumn<?, ?> colTitre;
    @FXML
    private TableColumn<?, ?> colDesc;
    @FXML
    private TextField tfsearchRecs;
    @FXML
    private Button btnTriTitre;
    @FXML
    private Button btnModifRecs;
    @FXML
    private Button btnSupprimRecs;
    @FXML
    private Button btnPDFrecs;
    @FXML
    private Button btnRefreshRecs;

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    Reclamation rec = null;
    ObservableList<Reclamation> ReclamationList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refreshingRecs();
    }

    public ObservableList<Reclamation> getReclamationList() {
        cnx = ConnectionDB.getInstance().getCnx();
        ReclamationList.clear();

        try {
            String query2 = "SELECT * FROM  Reclamation ";
            PreparedStatement smt = cnx.prepareStatement(query2);
            Reclamation rico;
            ResultSet rs = smt.executeQuery();

            while (rs.next()) {
                rico = new Reclamation(rs.getInt("id"), rs.getString("titre"), rs.getString("desipticon"));
                ReclamationList.add(rico);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ReclamationList;
    }

    @FXML
    public void refreshingRecs() {
        ObservableList<Reclamation> list = getReclamationList();
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        tvRecs.setItems(list);
    }

    @FXML
    public void sortingTitre() {
        ObservableList<Reclamation> listrec = getReclamationList();
        FXCollections.sort(listrec, Comparator.comparing(Reclamation::getTitre));
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        tvRecs.setItems(listrec);
    }

    @FXML
    private void SuppressionRecs(ActionEvent event) {
        Reclamation rico = (Reclamation) tvRecs.getSelectionModel().getSelectedItem();
        if (rico != null) {
            //
            try {
                String requete = "delete from Reclamation where id=?";
                PreparedStatement pst = cnx.prepareStatement(requete);
                pst.setInt(1, rico.getId());
                pst.executeUpdate();
                //System.out.println("Reclamation supprimée");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            //
            refreshingRecs();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Athlon :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Reclamation supprimé");
            alert.showAndWait();
        } else {
            System.out.println("Reclamation not selected ");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Athlon :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select Reclamation from the table ");
            alert.showAndWait();
        }
    }

    public void searchingRecs() {
        FilteredList<Reclamation> filteredList = new FilteredList<>(ReclamationList, p -> true);
        // add a change listener to the search field to update the filtered list
        tfsearchRecs.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(rico -> {
                // if search text is empty, show all users
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // convert search text to lowercase and compare with reclamation attributes
                String lowerCaseSearch = newValue.toLowerCase();
                return rico.getTitre().toLowerCase().contains(lowerCaseSearch)
                        || rico.getDesc().toLowerCase().contains(lowerCaseSearch);
            });
        });

        // wrap the filtered list in a SortedList to enable sorting
        SortedList<Reclamation> sortedList = new SortedList<>(filteredList);
        // bind the sorted list to the TableView
        tvRecs.setItems(sortedList);
        //
    }

    public void modificationRecs(ActionEvent event) {
        Reclamation rico = (Reclamation) tvRecs.getSelectionModel().getSelectedItem();
        if (rico != null) {
            //
            try {
                //load reclamation crud fxml with fields set up
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewReclamation/ReclamationCRUD.fxml"));
                // Get the root node of the new FXML file
                Parent root77 = loader.load();
                // Get the controller of the new FXML file
                ReclamationCRUDController controllerB = loader.getController();
                // Get the User variable to pass to ControllerB
                // Call the method in ControllerB to pass the User variable
                controllerB.receiveRec(rico);
                // Set the scene for the new FXML file
                Scene scene77 = new Scene(root77);
                Stage newStage77 = new Stage();
                newStage77.setScene(scene77);
                newStage77.show();
                /*
                Stage stage77 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage77.setScene(scene77);
                stage77.show();
                */
                //
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            //
            refreshingRecs();

        } else {
            System.out.println("Reclamation not selected ");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Athlon :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select Reclamation from the table ");
            alert.showAndWait();
        }

    }

}
