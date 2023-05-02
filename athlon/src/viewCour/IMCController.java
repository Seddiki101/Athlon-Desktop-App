package viewCour;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class IMCController {
    @FXML
private WebView videoWebView;
    @FXML
    private TextField weightField;
    
    @FXML
    private TextField heightField;
    
    @FXML
    private Label bmiLabel;
    
@FXML
private Label categoryLabel;
@FXML
private Label healthRiskLabel;
@FXML
private Label adviceLabel;

private String getCategory(double bmi) {
    if (bmi < 18.5) {
        return "Insuffisance pondérale";
    } else if (bmi < 25) {
        return "Poids santé";
    } else if (bmi < 30) {
        return "Surpoids";
    } else if (bmi < 35) {
        return "Obésité de classe I";
    } else if (bmi < 40) {
        return "Obésité de classe II";
    } else {
        return "Obésité de classe III";
    }
}

private String getHealthRisk(double bmi) {
    if (bmi < 18.5) {
        return "Accrue";
    } else if (bmi < 25) {
        return "Faible";
    } else if (bmi < 30) {
        return "Accrue";
    } else if (bmi < 35) {
        return "Élevé";
    } else if (bmi < 40) {
        return "Très élevé";
    } else {
        return "Extrêmement élevé";
    }
}

private String getAdvice(double bmi) {
    if (bmi < 18.5) {
        return "Vous devriez essayer de prendre du poids en suivant un régime alimentaire sain et en faisant de l'exercice régulièrement.";
    } else if (bmi < 25) {
        return "Continuez à maintenir un mode de vie sain en faisant de l'exercice régulièrement et en mangeant une alimentation équilibrée.";
    } else if (bmi < 30) {
        return "Essayez de perdre du poids en suivant un régime alimentaire sain et en faisant de l'exercice régulièrement.";
    } else if (bmi < 35) {
        return "Vous êtes exposé à des risques pour la santé élevés. Consultez un médecin pour obtenir des conseils sur la façon de perdre du poids.";
    } else if (bmi < 40) {
        return "Vous êtes exposé à des risques pour la santé très élevés. Consultez un médecin pour obtenir des conseils sur la façon de perdre du poids.";
    } else {
        return "Vous êtes exposé à des risques pour la santé extrêmement élevés. Consultez un médecin pour obtenir des conseils sur la façon de perdre du poids.";
    }
}
private void loadVideo(String videoId) {
        String url = "https://www.youtube.com/embed/" + videoId;
        WebEngine webEngine = videoWebView.getEngine();
        webEngine.load(url);
    }


   @FXML
private void calculateIMC() {
    try {
        double weight = Double.parseDouble(weightField.getText());
        double height = Double.parseDouble(heightField.getText());
        
        double bmi = weight / (height * height);
        
bmiLabel.setText(String.format("%.2f", bmi));
categoryLabel.setText(String.format("%s", getCategory(bmi)));
        healthRiskLabel.setText(String.format("%s", getHealthRisk(bmi)));
        adviceLabel.setText(String.format("%s", getAdvice(bmi)));
      
        String category = getCategory(bmi);
        String videoId;
        switch (category) {
            case "Insuffisance pondérale":
                videoId = "NYcVkhHxRq4";
                break;
            case "Poids santé":
                videoId = "SpDao5v2wLM";
                break;
            case "Surpoids":
                videoId = "P8Ra9daP7WU";
                break;
            case "Obésité de classe I":
                videoId = "P8Ra9daP7WU";
                break;
            case "Obésité de classe II":
                videoId = "P8Ra9daP7WU";
                break;
            case "Obésité de classe III":
                videoId = "P8Ra9daP7WU";
                break;
            default:
                videoId = "";
                break;
        }

        if (!videoId.isEmpty()) {
            loadVideo(videoId);
        }
    } catch (NumberFormatException ex) {
        bmiLabel.setText("Veuillez saisir des valeurs valides pour le poids et la taille.");
    }
}}