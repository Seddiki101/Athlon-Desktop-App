/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewCommande;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.Order;
import entity.OrderItem;
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
import services.OrderItemService;
import services.OrderService;

public class GestionOrderItemController implements Initializable {

    OrderItemService orderItemService;
    ObservableList<OrderItem> listOrderItem = FXCollections.observableArrayList();
    OrderItem orderItem;
    GestionOrderItemController gestionOrderItemController;

    @FXML
    private TableView<OrderItem> commandeItemTableView;
    @FXML
    private TableColumn<OrderItem, String> orderIdCell;
    @FXML
    private TableColumn<OrderItem, String> StatutCell;
    @FXML
    private TableColumn<OrderItem, String> ProduitCell;
    @FXML
    private TableColumn<OrderItem, String> quantiteCell;
    @FXML
    private TableColumn<OrderItem, String> actionCell;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gestionOrderItemController = this;
        orderItemService = new OrderItemService();
        LoadData();
    }

    public void refreshTable() {

        listOrderItem.clear();
        listOrderItem.addAll(orderItemService.getAll());
        commandeItemTableView.setItems(listOrderItem);

    }

    private void LoadData() {

        refreshTable();
        orderIdCell.setCellValueFactory(cellData
                -> {
            return new SimpleStringProperty(cellData.getValue().getOrder().getId() + "");
        });
        StatutCell.setCellValueFactory(cellData
                -> {
            return new SimpleStringProperty(cellData.getValue().getOrder().getState());
        });

        ProduitCell.setCellValueFactory(cellData
                -> {
            return new SimpleStringProperty(cellData.getValue().getProduct().getId() + "");
        });
        quantiteCell.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        actionCell.setCellFactory(createActionButton());

    }

    private Callback<TableColumn<OrderItem, String>, TableCell<OrderItem, String>> createActionButton() {
        Callback<TableColumn<OrderItem, String>, TableCell<OrderItem, String>> cellFoctory = (TableColumn<OrderItem, String> param) -> {
            // make cell containing buttons
            final TableCell<OrderItem, String> cell = new TableCell<OrderItem, String>() {
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
                                orderItem = commandeItemTableView.getSelectionModel().getSelectedItem();
                                orderItemService.delete(orderItem);
                                refreshTable();
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {

                                orderItem = commandeItemTableView.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUpdateOrderItem.fxml"));

                                Parent root;
                                root = loader.load();

                                AddUpdateOrderItemController addUpdateOrderItemController = loader.getController();
                                addUpdateOrderItemController.initializeOrderItemController(gestionOrderItemController);
                                addUpdateOrderItemController.initializeTextField(orderItem);
                                addUpdateOrderItemController.setWindowType("Update");
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(GestionOrderItemController.class.getName()).log(Level.SEVERE, null, ex);
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
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("AddUpdateOrderItem.fxml"));
            Parent root = fxml.load();
            AddUpdateOrderItemController addUpdateOrderItemController = fxml.getController();
            addUpdateOrderItemController.setWindowType("Ajout");
            addUpdateOrderItemController.initializeOrderItemController(this);
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();

            Scene scene = new Scene(root, 600.0, Math.min(400.0, screenBounds.getHeight()));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionOrderItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
