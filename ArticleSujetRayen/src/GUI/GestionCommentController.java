/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entites.Article;
import entites.Comment;
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
import javafx.scene.control.Button;
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
import services.CommentService;

/**
 * FXML Controller class
 *
 * @author Wasli rayen
 */
public class GestionCommentController implements Initializable {

    CommentService service;
    ObservableList<Comment> list = FXCollections.observableArrayList();
    Comment comment;
    GestionCommentController controller;
    public static Article article;
    @FXML
    private TableView<Comment> TableView;
    @FXML
    private TableColumn<Comment, String> articleCell;
    @FXML
    private TableColumn<Comment, String> commentCell;
    @FXML
    private TableColumn<Comment, String> createdOnCell;
    @FXML
    private TableColumn<Comment, String> ActionCell;
    @FXML
    private Button ButtonAjout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = this;
        service = new CommentService();
        LoadData();
    }

    public void refreshTable() {

        list.clear();
        list.addAll(service.getAllByArticle(article.getId()));
        TableView.setItems(list);
    }

    private void LoadData() {

        refreshTable();
        articleCell.setCellValueFactory(cellData
                -> {
            return new SimpleStringProperty(cellData.getValue().getArticle().getTitre());
        });

        commentCell.setCellValueFactory(new PropertyValueFactory<>("comment"));
        createdOnCell.setCellValueFactory(new PropertyValueFactory<>("created_on"));

        ActionCell.setCellFactory(createActionButton());

    }

    private Callback<TableColumn<Comment, String>, TableCell<Comment, String>> createActionButton() {
        Callback<TableColumn<Comment, String>, TableCell<Comment, String>> cellFoctory = (TableColumn<Comment, String> param) -> {
            // make cell containing buttons
            final TableCell<Comment, String> cell = new TableCell<Comment, String>() {
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
                                comment = TableView.getSelectionModel().getSelectedItem();
                                service.delete(comment);
                                refreshTable();
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {

                                comment = TableView.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUpdateComment.fxml"));

                                Parent root;
                                root = loader.load();

                                AddUpdateCommentController controllera = loader.getController();
                                controllera.initializeController(controller);
                                controllera.initializeTextField(comment);
                                controllera.setWindowType("Update");
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(GestionCommentController.class.getName()).log(Level.SEVERE, null, ex);
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
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("AddUpdateComment.fxml"));
            Parent root = fxml.load();
            AddUpdateCommentController controller = fxml.getController();
            AddUpdateCommentController.article = article;
            controller.setWindowType("Ajout");
            controller.initializeController(this);
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();

            Scene scene = new Scene(root, 600.0, Math.min(400.0, screenBounds.getHeight()));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionCommentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
