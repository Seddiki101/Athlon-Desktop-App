/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entite.Order;
import entite.OrderItem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Seif Boulabiar
 */
public class CartItemController {

    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Button deleteBtn;
    @FXML
    private Spinner<Integer> quantity;
    @FXML
    private Label subtotal;

    public void setData(OrderItem orderItem) {
//                image.setImage(new Image(getClass().getResourceAsStream(orderItem.getProduct().getImage())));
        name.setText(orderItem.getProduct().getMarque());
        price.setText("TND " + String.valueOf(orderItem.getProduct().getPrix()));
        subtotal.setText("TND " + orderItem.getProduct().getPrix() * orderItem.getQuantity());

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory.setValue(orderItem.getQuantity());
        quantity.setValueFactory(valueFactory);

        quantity.valueProperty().addListener((obs, oldValue, newValue)
                -> {
           
            if (newValue < oldValue) {
                Order.removeOne(orderItem);
            } else {
                Order.addproduct(orderItem);
            }
            //update
                BaseController.baseController.LoadCart1();

        });
        deleteBtn.setOnAction(event -> {
            Order.remove(orderItem);
            BaseController.baseController.LoadCart1();
        });
    }

}
