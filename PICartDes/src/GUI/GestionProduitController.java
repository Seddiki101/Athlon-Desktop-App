/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Order;
import entite.OrderItem;
import entite.Produit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.OrderItemService;

/**
 * FXML Controller class
 *
 * @author Seif Boulabiar
 */
public class GestionProduitController implements Initializable {

    OrderItemService orderItemService;
    ObservableList<Produit> listOrderItem = FXCollections.observableArrayList();

    @FXML
    private TableView<Produit> commandeItemTableView;
    @FXML
    private TableColumn<Produit, String> ProduitCell;
    @FXML
    private TableColumn<Produit, String> quantiteCell;
    @FXML
    private TableColumn<Produit, String> actionCell;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orderItemService = new OrderItemService();
        LoadData();
    }

    public void refreshTable() {

        listOrderItem.clear();
        listOrderItem.addAll(orderItemService.produit());
        commandeItemTableView.setItems(listOrderItem);

    }

    private void LoadData() {

        refreshTable();
        ProduitCell.setCellValueFactory(cellData
                -> {
            return new SimpleStringProperty(cellData.getValue().getMarque() + "");
        });
        quantiteCell.setCellValueFactory(cellData
                -> {
            return new SimpleStringProperty(cellData.getValue().getPrix() + "");
        });

        actionCell.setCellFactory(createActionButton());

    }

    private Callback<TableColumn<Produit, String>, TableCell<Produit, String>> createActionButton() {
        Callback<TableColumn<Produit, String>, TableCell<Produit, String>> cellFoctory = (TableColumn<Produit, String> param) -> {
            // make cell containing buttons
            final TableCell<Produit, String> cell = new TableCell<Produit, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView logoIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
                        logoIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );

                        logoIcon.setOnMouseClicked((MouseEvent event) -> {
                            Produit p = commandeItemTableView.getSelectionModel().getSelectedItem();
                            OrderItem orderItem = new OrderItem();
                            orderItem.setProduct(p);
                            orderItem.setQuantity(1);
                            Order.addproduct(orderItem);

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("produit ajouter au panier");
                            alert.setTitle("Succès");
                            alert.show();
                        });

                        HBox managebtn = new HBox(logoIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(logoIcon, new Insets(2, 2, 0, 3));
                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        return cellFoctory;
    }

    @FXML
    private void OpenAjoutDialog(ActionEvent event) {
    }

}
