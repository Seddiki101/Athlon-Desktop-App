/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewArticle;

import entity.Article;
import entity.Comment;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import services.CommentService;

/**
 * FXML Controller class
 *
 * @author Houssem Charef
 */
public class AddUpdateCommentController implements Initializable {

    CommentService service;
    Comment comment;
    public static Article article;
    String type;
    private GestionCommentController controller;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label TitleLabel;
    @FXML
    private TextField nomField;
    @FXML
    private Button actionButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new CommentService();
    }

    @FXML
    private void ajoutOrDelete(ActionEvent event) {
        if (controleDeSaisie()) {
            if (type.equals("Update")) {

                comment.setComment(nomField.getText());
                comment.setArticle(article);

                update(comment);
            } else {
                comment = new Comment();
                comment.setComment(nomField.getText());
                comment.setArticle(article);

                ajout(comment);
            }
            controller.refreshTable();
        }
    }

    private void update(Comment m) {

        if (service.update(m)) {
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

    private void ajout(Comment m) {

        if (service.insert(m)) {

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
        TitleLabel.setText(type + " Comment");
    }

    public void initializeTextField(Comment m) {
        comment = m;
        nomField.setText(m.getComment());

    }

    private boolean controleDeSaisie() {

        if (nomField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir le nom");
            alert.setTitle("fail");
            alert.show();
            return false;
        }

        return true;
    }

    void initializeController(GestionCommentController controller) {
        this.controller = controller;
    }

}
