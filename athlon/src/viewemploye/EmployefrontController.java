/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewemploye;

import entity.employe;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.ConnectionDB;

/**
 * FXML Controller class
 *
 * @author auo1
 */
public class EmployefrontController implements Initializable {
    
    
      private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    
   
    
    ObservableList<employe> EmployeList = FXCollections.observableArrayList();


    @FXML
    private TableView<employe> tableFront;
    @FXML
    private TableColumn<employe,String> frontprenom;
    @FXML
    private TableColumn<?, ?> idetat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        afficheremploye() ; 
        
        // TODO
    }    
    
 public void updateEmployeEtat(int employeId, String newEtat) {
    cnx = ConnectionDB.getInstance().getCnx();
    try {
        String query = "UPDATE employe SET etat = ? WHERE id = ?";
        PreparedStatement smt = cnx.prepareStatement(query);
        smt.setString(1, newEtat);
        smt.setInt(2, employeId);
        int rowsUpdated = smt.executeUpdate();
        System.out.println(rowsUpdated + " rows updated.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public ObservableList<employe> getEmployeList() {
    cnx = ConnectionDB.getInstance().getCnx();
    EmployeList.clear();

    try {
     String query2 = "SELECT e.id, e.cin, e.nom, e.prenom, e.salaire,  "
    + "CASE "
    + "WHEN NOW() NOT BETWEEN c.date_d AND c.date_f THEN 'disponible' "
    + "ELSE 'non disponible' "
    + "END AS etat "
    + "FROM employe e LEFT JOIN conge c ON e.id = c.employe_id";
        PreparedStatement smt = cnx.prepareStatement(query2);
        employe emp;
        ResultSet rs = smt.executeQuery();

       while (rs.next()) {
  emp = new employe(rs.getInt("id"), rs.getInt("cin"), rs.getString("nom"), rs.getString("prenom"), rs.getFloat("salaire"), rs.getString("etat"));

    
    EmployeList.add(emp);
}
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return EmployeList;
}
      @FXML
    public void afficheremploye() {
        ObservableList<employe> list = getEmployeList();
   
         frontprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
   idetat.setCellValueFactory(new PropertyValueFactory<>("etat"));
       
          idetat.setOnEditCommit(event -> {
    employe emp = (employe) event.getRowValue();
    emp.setEtat((String) event.getNewValue());
    updateEmployeEtat(emp.getId(), (String) event.getNewValue());
});
        tableFront.setItems(list);
    }
       
}
