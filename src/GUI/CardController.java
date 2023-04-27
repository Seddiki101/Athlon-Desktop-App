/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Util.MyDB;
import Entities.Produit;
import Entities.ProduitLike;
import Services.ServiceProduit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class CardController implements Initializable {

    @FXML
    private HBox box;

    private String[] colors = {"FF5056"};
    @FXML
    private Label ProduitName;
    @FXML
    private ImageView ProduitImg;
    @FXML
    private Label description;
    @FXML
    private Label prixP;
    @FXML
    private Label catp;

    private int pickedId;
    @FXML
    private Label moyRating;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Produit modele) {
        pickedId = modele.getId();
        Image image = new Image(getClass().getResourceAsStream(modele.getImage()));
        ProduitImg.setImage(image);
        ProduitName.setText(modele.getNom());
        description.setText(modele.getDescription());
        prixP.setText(String.valueOf(modele.getPrix()));
        moyRating.setText(String.valueOf(modele.getMoyRating()));
        catp.setText(modele.getNomCategory());
        box.setStyle("-fx-background-color: #" + colors[(int) (Math.random() * colors.length)]
                + " ; -fx-background-radius: 15;"
                + "-fx-effect : dropshadow(three-pass-box , rgba(0,0,0,0.1) , 10 , 0 ,0 , 10 ) ;");

        // Ajouter le code pour afficher les likes
        ServiceProduit sm = new ServiceProduit();
        HBox lc = new HBox();
        FontAwesomeIconView like = new FontAwesomeIconView(FontAwesomeIcon.HEART_ALT);
        like.setGlyphSize(25);
        like.setCursor(Cursor.HAND);
        if (sm.islikedbyuser(modele.getId()).isEmpty()) {
            like.setGlyphName("HEART_ALT");
            like.setFill(Color.RED);
            like.setGlyphSize(25);
            like.setCursor(Cursor.HAND);
        } else {
           like.setGlyphName("HEART");
           like.setFill(Color.RED);
            like.setGlyphSize(25);
            like.setCursor(Cursor.HAND);
        }
        Label nblike = new Label("15");
        nblike.setPrefSize(40, 25);
        nblike.setTranslateX(10);
        nblike.setTranslateY(-2);
        int l = sm.likes(modele.getId()).size();
        if (l == 0) {
            nblike.setText("0");
        } else {
            nblike.setText(String.valueOf(l));
        }
        like.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent lk) {
                List<ProduitLike> test = sm.likes(modele.getId());
                if (sm.islikedbyuser(modele.getId()).isEmpty()) {
                    sm.ajouterlike(modele.getId());
                    like.setGlyphName("HEART");
                    like.setFill(Color.RED);
                    like.setGlyphSize(25);
                    like.setCursor(Cursor.HAND);
                    int li = sm.likes(modele.getId()).size();
                    nblike.setText(String.valueOf(li));
                } else {
                    sm.Supprimerlike(modele.getId());
                    like.setGlyphName("HEART_ALT");
                    like.setFill(Color.RED);
                    like.setGlyphSize(25);
                    like.setCursor(Cursor.HAND);
                    int li = sm.likes(modele.getId()).size();
                    nblike.setText(String.valueOf(li));
                }
            }
        });

        lc.getChildren().add(like);
        lc.getChildren().add(nblike);
        box.getChildren().add(lc);

    }

    @FXML
    private void noteP(ActionEvent event) throws IOException {
        box.getChildren().clear();
        Parent Content = FXMLLoader.load(getClass().getResource("Rating.fxml"));
        box.getChildren().setAll(Content);
        // Set the productId attribute with the ID of the current product
        MyDB.setPickedPRoductId(pickedId);
    }

}
