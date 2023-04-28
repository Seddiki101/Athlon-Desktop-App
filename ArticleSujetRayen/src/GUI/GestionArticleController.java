/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entites.Article;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import services.ArticleService;

/**
 * FXML Controller class
 *
 * @author Houssem Charef
 */
public class GestionArticleController implements Initializable {

    ArticleService service;
    ObservableList<Article> list = FXCollections.observableArrayList();
    Article article;
    GestionArticleController controller;
    @FXML
    private TableView<Article> TableView;
    @FXML
    private TableColumn<Article, String> titreCell;
    @FXML
    private TableColumn<Article, String> auteurCell;
    @FXML
    private TableColumn<Article, String> descriptionCell;
    @FXML
    private TableColumn<Article, String> imgCell;
    @FXML
    private TableColumn<Article, String> likeCell;
    @FXML
    private TableColumn<Article, String> dislikeCell;
    @FXML
    private TableColumn<Article, String> ActionCell;
    @FXML
    private Button ButtonAjout;
    @FXML
    private TableColumn<Article, String> sujetCell1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = this;
        service = new ArticleService();
        LoadData();
        TableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    article = TableView.getSelectionModel().getSelectedItem();
                    GestionCommentController.article = article;
                    BaseController.baseController.LoadGestionComment(event);
                }
            }
        });
    }

    public void refreshTable() {

        list.clear();
        list.addAll(service.getAll());
        TableView.setItems(list);
    }

    private void LoadData() {

        refreshTable();
        titreCell.setCellValueFactory(new PropertyValueFactory<>("titre"));
        sujetCell1.setCellValueFactory(cellData
                -> {
            return new SimpleStringProperty(cellData.getValue().getSujet().getNom());
        });
        auteurCell.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        descriptionCell.setCellValueFactory(new PropertyValueFactory<>("description"));
        imgCell.setCellValueFactory(new PropertyValueFactory<>("img"));
        likeCell.setCellValueFactory(new PropertyValueFactory<>("like"));
        dislikeCell.setCellValueFactory(new PropertyValueFactory<>("dislike"));

        ActionCell.setCellFactory(createActionButton());

    }

    private Callback<TableColumn<Article, String>, TableCell<Article, String>> createActionButton() {
        Callback<TableColumn<Article, String>, TableCell<Article, String>> cellFoctory = (TableColumn<Article, String> param) -> {
            // make cell containing buttons
            final TableCell<Article, String> cell = new TableCell<Article, String>() {
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
                        FontAwesomeIconView likeIcone = new FontAwesomeIconView(FontAwesomeIcon.ARROW_CIRCLE_UP);
                        FontAwesomeIconView dislikelikeIcone = new FontAwesomeIconView(FontAwesomeIcon.ARROW_CIRCLE_DOWN);
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

                        likeIcone.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        dislikelikeIcone.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmation de suppression ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                            alert.showAndWait();

                            if (alert.getResult() == ButtonType.YES) {
                                article = TableView.getSelectionModel().getSelectedItem();
                                service.delete(article);
                                refreshTable();
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {

                                article = TableView.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUpdateArticle.fxml"));

                                Parent root;
                                root = loader.load();

                                AddUpdateArticleController controllera = loader.getController();
                                controllera.initializeController(controller);
                                controllera.initializeTextField(article);
                                controllera.setWindowType("Update");
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(GestionArticleController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        likeIcone.setOnMouseClicked((MouseEvent event) -> {
                            article = TableView.getSelectionModel().getSelectedItem();
                            service.updateLike(article);
                            refreshTable();
                        });

                        dislikelikeIcone.setOnMouseClicked((MouseEvent event) -> {
                            article = TableView.getSelectionModel().getSelectedItem();
                            service.updateDislike(article);
                            refreshTable();
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon, likeIcone, dislikelikeIcone);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        HBox.setMargin(likeIcone, new Insets(2, 3, 0, 2));

                        HBox.setMargin(dislikelikeIcone, new Insets(2, 3, 0, 2));
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
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("AddUpdateArticle.fxml"));
            Parent root = fxml.load();
            AddUpdateArticleController controller = fxml.getController();
            controller.setWindowType("Ajout");
            controller.initializeController(this);
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();

            Scene scene = new Scene(root, 600.0, Math.min(400.0, screenBounds.getHeight()));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionArticleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
