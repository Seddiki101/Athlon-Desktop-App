/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewArticle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.Sujet;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.SujetService;

/**
 * FXML Controller class
 *
 * @author Houssem Charef
 */
public class GestionSujetController implements Initializable {

    SujetService service;
    ObservableList<Sujet> list = FXCollections.observableArrayList();
    Sujet sujet;
    GestionSujetController controller;
    @FXML
    private TableView<Sujet> TableView;
    @FXML
    private TableColumn<Sujet, String> nomCell;
    @FXML
    private TableColumn<Sujet, String> descriptionCell;
    @FXML
    private TableColumn<Sujet, String> imageCell;
    @FXML
    private TableColumn<Sujet, String> ActionCell;
    @FXML
    private Button ButtonAjout;
    @FXML
    private TextField searchTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = this;
        service = new SujetService();
        LoadData();
    }

    public void refreshTable() {

        list.clear();
        list.addAll(service.getAll());
        TableView.setItems(list);

        if (list.size() > 0) {
            FilteredList<Sujet> filterData = recherche(list);
            String a = searchTextField.getText();
            TableView.setItems(filterData);
        }

    }

    private void LoadData() {

        refreshTable();
        nomCell.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionCell.setCellValueFactory(new PropertyValueFactory<>("description"));
        imageCell.setCellValueFactory(new PropertyValueFactory<>("img"));
        ActionCell.setCellFactory(createActionButton());

    }

    private Callback<TableColumn<Sujet, String>, TableCell<Sujet, String>> createActionButton() {
        Callback<TableColumn<Sujet, String>, TableCell<Sujet, String>> cellFoctory = (TableColumn<Sujet, String> param) -> {
            // make cell containing buttons
            final TableCell<Sujet, String> cell = new TableCell<Sujet, String>() {
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
                                sujet = TableView.getSelectionModel().getSelectedItem();
                                service.delete(sujet);
                                refreshTable();
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {

                                sujet = TableView.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUpdateSujet.fxml"));

                                Parent root;
                                root = loader.load();

                                AddUpdateSujetController controllera = loader.getController();
                                controllera.initializeController(controller);
                                controllera.initializeTextField(sujet);
                                controllera.setWindowType("Update");
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(GestionSujetController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void openAjoutDialog(ActionEvent event) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("AddUpdateSujet.fxml"));
            Parent root = fxml.load();
            AddUpdateSujetController controller = fxml.getController();
            controller.setWindowType("Ajout");
            controller.initializeController(this);
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();

            Scene scene = new Scene(root, 600.0, Math.min(400.0, screenBounds.getHeight()));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionSujetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private FilteredList<Sujet> recherche(ObservableList llistMaldie) {
        FilteredList<Sujet> filterData = new FilteredList<Sujet>(llistMaldie, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(SearchModel -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String serachKeeyword = newValue.toLowerCase();
                if (((Sujet) SearchModel).getNom().toLowerCase().contains(serachKeeyword)) {
                    return true;
                } else if ((((Sujet) SearchModel).getDescription() + "").toLowerCase().contains(serachKeeyword)) {
                    return true;
                } else if ((((Sujet) SearchModel).getNom() + "").toLowerCase().contains(serachKeeyword)) {
                    return true;
                }

                return false;
            });

        });

        return filterData;
    }

}
