package viewCour;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import services.ServiceCours;
import services.ServiceExercices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import entity.Cours;
import entity.Exercices;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ExercicesController implements Initializable{

    
  

    @FXML
    private TableView<Exercices> tblExercices;

    @FXML
    private TableColumn<Exercices, String> colExercice;

    @FXML
    private TableColumn<Exercices, String> colNomCours;
    
    private ServiceExercices serviceExercices = new ServiceExercices();
    private ServiceCours serviceCours = new ServiceCours();
    @FXML
    private Button ConstButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colExercice.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNomCours.setCellValueFactory(cellData -> {
            Integer cours_id = cellData.getValue().getId_cours();
            try {
                Cours cours = serviceCours.getCoursById(cours_id);
                return new Label(cours.getNom()).textProperty();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        try {
            ArrayList<Exercices> exercices = serviceExercices.AfficherEX();
            tblExercices.getItems().addAll(exercices);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
private void handleConst(ActionEvent event) throws IOException, SQLException {
    Exercices selectedExercice = tblExercices.getSelectionModel().getSelectedItem();
    if(selectedExercice != null) {
        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewCour/detailsEX.fxml"));
        Parent root = loader.load();
        DetailsEXController controller = loader.getController();
        controller.setSelectedExercice(selectedExercice);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        */
        
        
        
        
        //
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewCour/detailsEX.fxml"));
                Parent root77 = loader.load();
                viewCour.DetailsEXController controllerB = loader.getController();
                Scene scene77 = new Scene(root77);
                Stage newStage77 = new Stage();
                newStage77.setScene(scene77);
                newStage77.show();
        
     }   
        
        
        
    }
    
    public void exesex()
    {
         Exercices selectedExercice = tblExercices.getSelectionModel().getSelectedItem();
    if(selectedExercice != null) {
             try {
                 //FXMLLoader loader = new FXMLLoader(getClass().getResource("B:/project/jav/fx/equipa/Athlon-Desktop-App-Employe/athlon/src/viewCour/detailsEX.fxml"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewCour/detailsEX.fxml"));
                 Parent root77 = loader.load();
                 viewCour.DetailsEXController controllerB = loader.getController();
                 Scene scene77 = new Scene(root77);
                 Stage newStage77 = new Stage();
                 newStage77.setScene(scene77); 
                 newStage77.show();
             } catch (IOException ex) {
                 Logger.getLogger(ExercicesController.class.getName()).log(Level.SEVERE, null, ex);
             }
        
    }    
    }
    
    
    
    
    
    

}
