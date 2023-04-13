/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Order;
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
import javafx.geometry.Rectangle2D;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.OrderService;

public class GestionOrderController implements Initializable {

    OrderService orderService;
    ObservableList<Order> listOrders = FXCollections.observableArrayList();
    Order order;
    GestionOrderController gestionOrderController;

    @FXML
    private TableView<Order> commandeTableView;
    @FXML
    private TableColumn<Order, String> date;
    @FXML
    private TableColumn<Order, String> Etat;
    @FXML
    private TableColumn<Order, String> nomClientCell;
    @FXML
    private TableColumn<Order, String> actionCell;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gestionOrderController = this;
        orderService = new OrderService();
        LoadData();
    }

    public void refreshTable() {

        listOrders.clear();
        listOrders.addAll(orderService.getAll());
        commandeTableView.setItems(listOrders);

    }

    private void LoadData() {

        refreshTable();
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        Etat.setCellValueFactory(new PropertyValueFactory<>("state"));
        nomClientCell.setCellValueFactory(cellData
                -> {
            return new SimpleStringProperty(cellData.getValue().getUser().getId() + "");
        });
        actionCell.setCellFactory(createActionButton());

    }

    private Callback<TableColumn<Order, String>, TableCell<Order, String>> createActionButton() {
        Callback<TableColumn<Order, String>, TableCell<Order, String>> cellFoctory = (TableColumn<Order, String> param) -> {
            // make cell containing buttons
            final TableCell<Order, String> cell = new TableCell<Order, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        FontAwesomeIconView logoIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmation de suppression ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                            alert.showAndWait();

                            if (alert.getResult() == ButtonType.YES) {
                                order = commandeTableView.getSelectionModel().getSelectedItem();
                                orderService.delete(order);
                                refreshTable();
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {

                                order = commandeTableView.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUpdateOrder.fxml"));

                                Parent root;
                                root = loader.load();

                                AddUpdateOrderController addUpdateOrderController = loader.getController();
                                addUpdateOrderController.initializeOrderController(gestionOrderController);
                                addUpdateOrderController.initializeTextField(order);
                                addUpdateOrderController.setWindowType("Update");
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(GestionOrderController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        logoIcon.setOnMouseClicked((MouseEvent event) -> {

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        HBox.setMargin(logoIcon, new Insets(2, 3, 0, 2));
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
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("AddUpdateOrder.fxml"));
            Parent root = fxml.load();
            AddUpdateOrderController addUpdateOrderController = fxml.getController();
            addUpdateOrderController.setWindowType("Ajout");
            addUpdateOrderController.initializeOrderController(this);
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();

            Scene scene = new Scene(root, 600.0, Math.min(400.0, screenBounds.getHeight()));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
