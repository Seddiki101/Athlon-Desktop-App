/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entite.Order;
import entite.OrderItem;
import entite.Produit;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import service.OrderItemService;
import service.OrderService;

/**
 * FXML Controller class
 *
 * @author Seif Boulabiar
 */
public class AddUpdateOrderItemController implements Initializable {

    OrderService orderService;
    OrderItemService orderItemService;
    OrderItem orderItem;
    String type;
    GestionOrderItemController gestionOrderItemController;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label TitleLabel;
    @FXML
    private TextField quantitéFiled;
    @FXML
    private Button actionButton;
    @FXML
    private ComboBox<Produit> ProduitCB;
    @FXML
    private ComboBox<Order> OrderCB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orderItemService = new OrderItemService();
        orderService = new OrderService();
        ObservableList<Order> list = FXCollections.observableArrayList();
        list.addAll(orderService.getAll());
        OrderCB.setItems(list);
    }

    @FXML
    private void ajoutOrDelete(ActionEvent event) {
        if (controleDeSaisie()) {
            if (type.equals("Update")) {

                orderItem.setQuantity((int) Float.parseFloat(quantitéFiled.getText()));
                orderItem.setOrder(OrderCB.getSelectionModel().getSelectedItem());

                update(orderItem);
            } else {
                orderItem = new OrderItem();
                orderItem.setQuantity((int) Float.parseFloat(quantitéFiled.getText()));
                orderItem.setOrder(OrderCB.getSelectionModel().getSelectedItem());

                ajout(orderItem);
            }
            gestionOrderItemController.refreshTable();
        }

    }

    private void update(OrderItem oi) {

        if (orderItemService.update(oi)) {
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

    private void ajout(OrderItem oi) {

        if (orderItemService.insert(oi)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("ajout avec succès");
            alert.setTitle("Succès");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("mise à jour fail ");
            alert.setTitle("fail");
            alert.show();
        }

    }

    public void setWindowType(String type) {
        this.type = type;
        actionButton.setText(type);
        TitleLabel.setText(type + " orderItem");
    }

    public void initializeTextField(OrderItem oi) {
        orderItem = oi;
        OrderCB.getSelectionModel().select(oi.getOrder());
        quantitéFiled.setText(oi.getQuantity() + "");

    }

    private boolean controleDeSaisie() {

        if (OrderCB.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir l' order");
            alert.setTitle("fail");
            alert.show();
            return false;
        }

        try {
            Float.parseFloat(quantitéFiled.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(quantitéFiled.getText() + " n'est pas un nombre valide (nombre)");
            alert.setTitle("fail");
            alert.show();
            return false;
        }

        return true;
    }

    void initializeOrderItemController(GestionOrderItemController gestionOrderItemController) {
        this.gestionOrderItemController = gestionOrderItemController;
    }
}
