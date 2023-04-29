package Advanced;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.exo;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * FXML Controller class
 *
 * @author k
 */
public class ChiController implements Initializable {

    @FXML
    private Label reponse;
    @FXML
    private TextField input;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO   
    }   
    
    
    
    @FXML
    public void drax()
    {
        String userInput = input.getText();
        
        
        try {
            //URL url = new URL("https://exercisedb.p.rapidapi.com/exercises/target/"+userInput+"?limit=1");
             URL url = new URL("https://exercisedb.p.rapidapi.com/exercises/target/" + userInput + "?limit=1");
            try {
                System.out.println("test 6");
                System.out.println("Establishing connection to API endpoint...");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("x-rapidapi-host", "exercisedb.p.rapidapi.com");
                connection.setRequestProperty("x-rapidapi-key", "59054fc8fdmsh5863191fd6484c6p132dedjsnb5ebdc71fa34");
                connection.setRequestProperty("accept", "application/json");
                InputStream responseStream = connection.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(responseStream);
                System.out.println("Response received from API:");
                System.out.println(root);
                
                //reponse.setText(root.toString() );
                /*
                
                 List<exo> lista = new ArrayList <exo>();
                
            for (JsonNode exercise : root) {
            String exerciseName = exercise.get("name").asText();
            String exerciseDescription = exercise.get("description").asText();
            
                        exo e = new exo(exerciseName,exerciseDescription);
                        lista.add(e);
            
            }
            
            */
            
            // reponse.setText( lista.toString() );
             
            
            
           

                
       
            } catch (IOException ex) {
                Logger.getLogger(ChiController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ChiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
