/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entite.Order;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import service.OrderService;

/**
 * FXML Controller class
 *
 * @author Houssem Charef
 */
public class AddUpdateOrderController implements Initializable {

    OrderService orderService;
    Order order;
    String type;
    GestionOrderController gestionOrderController;
    @FXML
    private Button actionButton;
    @FXML
    private ComboBox<String> statutCB;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label TitleLabel1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orderService = new OrderService();
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("pending", "placed");
        statutCB.setItems(list);
    }

    @FXML
    private void ajoutOrDelete(ActionEvent event) {
        if (controleDeSaisie()) {
            if (type.equals("Update")) {

                order.setState(statutCB.getSelectionModel().getSelectedItem());
                update(order);
            } else {
                order = new Order();
                order.setDate(new Timestamp(System.currentTimeMillis()));
                order.setState(statutCB.getSelectionModel().getSelectedItem());

                ajout(order);
            }
            gestionOrderController.refreshTable();
        }
    }

    private void update(Order o) {

        if (orderService.update(o)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("mise à jour avec succès");
            alert.setTitle("Succès");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("mise à jour fail !! ");
            alert.setTitle("fail");
            alert.show();
        }
    }

    private void ajout(Order o) {

        if (orderService.insert(o)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("ajout avec succès");
            alert.setTitle("Succès");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ajout fail ");
            alert.setTitle("fail");
            alert.show();
        }

    }

    public void setWindowType(String type) {
        this.type = type;
        actionButton.setText(type);
        TitleLabel1.setText(type + " order");
    }

    public void initializeTextField(Order o) {
        order = o;
        statutCB.getSelectionModel().select(o.getState());

    }

    private boolean controleDeSaisie() {

        if (statutCB.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir le statut");
            alert.setTitle("fail");
            alert.show();
            return false;
        }

        return true;
    }

    void initializeOrderController(GestionOrderController gestionOrderController) {
        this.gestionOrderController = gestionOrderController;
    }

}
