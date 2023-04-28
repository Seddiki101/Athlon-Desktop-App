/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entite.Order;
import entite.OrderItem;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import service.OrderItemService;
import service.OrderService;

public class CartController implements Initializable {

    OrderService orderService;
    OrderItemService orderItemService;

    @FXML
    private AnchorPane paine;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label totale1Label;
    @FXML
    private Label Rtotale2Label;
    @FXML
    private Label RemiseLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orderService = new OrderService();
        orderItemService = new OrderItemService();
        int i = 0;
        totale1Label.setText("Totale:  " + Order.getTotale() + " DT");
        if (Order.getTotale() > 100) {
            RemiseLabel.setText("Remise :  10%");
            Rtotale2Label.setText("Totale:  " + (Order.getTotale() - Order.getTotale() * 10 / 100) + " DT");
        } else {
            RemiseLabel.setText("Remise :  0%");
            Rtotale2Label.setText("Totale:  " + Order.getTotale() + " DT");
        }

        for (OrderItem oi : Order.getOrderItems()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CartItem.fxml"));

                Parent root;
                root = loader.load();

                CartItemController cartItemController = loader.getController();
                cartItemController.setData(oi);

                gridPane.add(root, 0, i);
                i++;
            } catch (IOException ex) {
                Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void add_Commande(ActionEvent event) {
        
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Stripe.fxml"));
            Parent root;
            root = loader.load();
            StripeController p = loader.getController();
            p.setPrix((int) Order.getTotale()*10);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            if (p.getReturn()) {
                
                Order order = new Order();
                order.setState("placed");
                order.setDate(new Timestamp(System.currentTimeMillis()));
                if (Order.getTotale() > 100) {
                    order.setRemise(10);
                }
                int idOrder = orderService.insertID(order);
                order.setId(idOrder);
                for (OrderItem oi : Order.orderItems) {
                    oi.setOrder(order);
                    orderItemService.insert(oi);
                }
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Order succès");
                alert.setTitle("Succès");
                alert.show();
                Order.orderItems = new ArrayList<>();
                BaseController.baseController.LoadCart1();
            }} catch (IOException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
