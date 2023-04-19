/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Entities.Categorie;
import Services.ServiceProduit;
import Entities.Produit;
import Services.ServiceCategorie;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.runtime.Debug.id;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Notifications;


/**
 * FXML Controller class
 *
 * @author msi
 */
public class ProduitController implements Initializable {
    
    ServiceProduit sc =new ServiceProduit();
    ObservableList<Produit> ProduitList;
   int index=-1;
   
  
   private int idCategorieToadd ;
    @FXML
    private ImageView imageProduitView;
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField Recherche;
    
    
      public int getIdCategorieToadd() {
        return idCategorieToadd;
    }

    public void setIdCategorieToadd(int idCategorieToadd) {
        this.idCategorieToadd = idCategorieToadd;
    }
String path="";
    @FXML
    private Button AddP;
    @FXML
    private TextField idProduitField;
    @FXML
    private TextField nomProduitFiled;
    @FXML
    private TextField brandProduitFiled;
    @FXML
    private TextField descriptionProduitFiled;
    @FXML
    private TextField prixProduitFiled;
    @FXML
    private TextField imageProduitFiled;
    @FXML
    private TableColumn<Produit, Integer> idProduit;
    @FXML
    private TableColumn<Produit, String> NomProduit;
    @FXML
    private TableColumn<Produit, String> brandProduit;
    @FXML
    private TableColumn<Produit, String> DescriptionProduit;
    @FXML
    private TableColumn<Produit, Float> PrixProduit;
    @FXML
    private TableColumn<Produit, String> imageProduit;
    @FXML
    private TableView<Produit>TableView;
    @FXML
    private Button modifierProduit;
    @FXML
    private Button supprimerProduit;
    @FXML
    private ComboBox<String> catPField;
    @FXML
    private TableColumn<Produit, String> CategoryP;
    @FXML
    private Button selectImageBtn;
    /**
     * Initializes the controller clas
     * @param url
     */
    
    
   
         public void updateTable() {
           ObservableList<Produit> Produit = FXCollections.observableArrayList(sc.afficherProduit());

         idProduit.setCellValueFactory(new PropertyValueFactory<>("id"));
           NomProduit.setCellValueFactory(new PropertyValueFactory<>("nom"));
         brandProduit.setCellValueFactory(new PropertyValueFactory<>("brand"));
       DescriptionProduit.setCellValueFactory(new PropertyValueFactory<>("description"));
        PrixProduit.setCellValueFactory(new PropertyValueFactory<>("prix"));
       imageProduit.setCellValueFactory(new PropertyValueFactory<>("image"));
       CategoryP.setCellValueFactory(new PropertyValueFactory<>("nomCategory"));
        System.out.println("affichage" + sc.afficherProduit());
         TableView.setItems(Produit);
     }
        
        

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    show();
  }    

    public void show() {
        ObservableList<Produit> ProduitList = FXCollections.observableArrayList(sc.afficherProduit());
        System.out.println("affichage" + sc.afficherProduit());
              idProduit.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomProduit.setCellValueFactory(new PropertyValueFactory<>("nom"));
       brandProduit.setCellValueFactory(new PropertyValueFactory<>("brand"));
        DescriptionProduit.setCellValueFactory(new PropertyValueFactory<>("description"));
       PrixProduit.setCellValueFactory(new PropertyValueFactory<>("prix"));
        imageProduit.setCellValueFactory(new PropertyValueFactory<>("image"));
        CategoryP.setCellValueFactory(new PropertyValueFactory<>("nomCategory"));
       TableView.setItems(ProduitList);
       
       chercherProduit();
       
        ServiceCategorie sc = new ServiceCategorie();
        List<String> nomsCataegory = new ArrayList<>();
        for (Categorie c : sc.afficherCategorie()) {
            nomsCataegory.add(c.getNom());

        }
        catPField.setItems(FXCollections.observableArrayList(nomsCataegory));

        catPField.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // get the selected Personne object based on the selected nom value
                Categorie selectedLibelle = null;
                for (Categorie c: sc.afficherCategorie()) {
                    if (c.getNom().equals(newValue)) {
                        selectedLibelle = c;
                        break;
                    }
                }
                if (selectedLibelle != null) {
                    int id = selectedLibelle.getId();
                    System.out.println("Selected Categorie id: " + id);
                    // do something with the id...
                    setIdCategorieToadd(id);
                }
            }
        });
    }


       
    
    
    @FXML
    private void ajouterP(ActionEvent event) {
        
                        Image photo=imageProduitView.getImage();

    String nomProduit = nomProduitFiled.getText();
    String brandProduit = brandProduitFiled.getText();
    String descriptionProduit = descriptionProduitFiled.getText();
    Float prixProduit =(  Float.parseFloat(prixProduitFiled.getText()));
    
                String imagePath = path.substring(path.lastIndexOf("/img/"));
          Produit u = new Produit(nomProduit, brandProduit, descriptionProduit, imagePath,prixProduit);



   
    // Vérification de la saisie du nom du produit
    if (nomProduit.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Veuillez saisir un nom de produit valide.");
        return;
    }
    
    // Vérification de la saisie de la marque du produit
    if (brandProduit.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Veuillez saisir une marque de produit valide.");
        return;
    }
    
    // Vérification de la saisie de la description du produit
    if (descriptionProduit.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Veuillez saisir une description de produit valide.");
        return;
    }
    
     if (prixProduitFiled.getText().isEmpty()) {
    JOptionPane.showMessageDialog(null, "Veuillez saisir un prix de produit valide.");
    return;
}
    
   // Vérification de la saisie du prix du produit
    if (prixProduit <= 0) {
        JOptionPane.showMessageDialog(null, "Le prix doit être supérieur à zéro.");
        return;
    }
   // Vérification que la quantité et le prix sont des nombres valide
    
   

    // Toutes les saisies sont valides, création et ajout du produit
    Produit c = new Produit();
    c.setNom(nomProduit);
    c.setBrand(brandProduit);
    c.setDescription(descriptionProduit);
    c.setPrix(Float.parseFloat(prixProduitFiled.getText()));
  
    c.setImage(imagePath);
     c.setIdCategory(idCategorieToadd);
     
    // try {

//c.setPrix(Float.parseFloat(prixProduitFiled.getText()));
  Notifications.create()
                    .title("Notification")
                    .text("produit ajoutè.")
                    .position(Pos.BOTTOM_RIGHT)
                    .showInformation();
//} catch (NumberFormatException e) {
//JOptionPane.showMessageDialog(null, "La quantité et le prix doivent être des nombres.");
//return;
//}
     
    
   

    sc.ajouterProduit(c);
    updateTable();
   
    
    

    }
   

    @FXML
    private void getSelected(MouseEvent event) {
                 Produit c=TableView.getSelectionModel().getSelectedItem();

      index =  TableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        idProduitField.setText(idProduit.getCellData(index).toString());
        nomProduitFiled.setText(NomProduit.getCellData(index));
        brandProduitFiled.setText(brandProduit.getCellData(index));
        descriptionProduitFiled.setText(DescriptionProduit.getCellData(index));
        prixProduitFiled.setText(PrixProduit.getCellData(index).toString());
 Image image = new Image(new File(c.getImage()).toURI().toString());
            imageProduitView.setImage(image);         //coachingField.setValue(coursR.getCellData(index));
        // catPField.setValue(CategoryP.getCellData(index).toString());
    }

    @FXML
    private void ModifierProduit(ActionEvent event) {
        
         Produit c = new Produit();
              String path =  imageProduit.getText();

        c.setId(Integer.parseInt(idProduitField.getText()));
       c.setNom(nomProduitFiled.getText());
        c.setBrand(brandProduitFiled.getText());
        c.setDescription(descriptionProduitFiled.getText());
        c.setPrix(Float.parseFloat(prixProduitFiled.getText()));
        c.setImage(path);
        c.setNomCategory(catPField.getValue());
        c.setIdCategory(idCategorieToadd);
        sc.modifierProduit(c);
      updateTable();
        JOptionPane.showMessageDialog(null, "Produit modifié");

    }

    @FXML
    private void supprimerProduit(ActionEvent event) {
         Produit c = new Produit();
        c.setId(Integer.parseInt(idProduitField.getText()));
       c.setNom(nomProduitFiled.getText());
        c.setBrand(brandProduitFiled.getText());
        c.setDescription(descriptionProduitFiled.getText());
        c.setPrix(Float.parseFloat(prixProduitFiled.getText()));
        c.setImage(imageProduitFiled.getText());
c.setNomCategory( catPField.getValue());
        sc.supprimerProduit(c);
       updateTable();
        JOptionPane.showMessageDialog(null, "Produit supprimé");
    }

    @FXML
    private void selectImage(MouseEvent event) {
       
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose Image File");

    // Ajouter un filtre pour n'afficher que les fichiers d'images
    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
    );

    // Afficher la boîte de dialogue de sélection de fichiers
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
    // load the selected image into the image view
    path=selectedFile.getAbsolutePath().replace("\\", "/");

        Image image = new Image(selectedFile.toURI().toString());
    imageProduitView.setImage(image);
    }
    }

    @FXML
    private void produitFront(ActionEvent event) throws IOException {
        
          pane.getChildren().clear();
        Parent Content = FXMLLoader.load(getClass().getResource("frontProduit.fxml"));
        pane.getChildren().setAll(Content);
    }

    @FXML
    private void stat(ActionEvent event) {
    generateStatistics();
        
    }

    public Map<String, Integer> getProduitCountByCategory(List<Produit> produits) {
    Map<String, Integer> countMap = new HashMap<>();

    for (Produit produit : produits) {
        String category = produit.getNomCategory();
        if (countMap.containsKey(category)) {
            countMap.put(category, countMap.get(category) + 1);
        } else {
            countMap.put(category, 1);
        }
    }

    return countMap;
}



public void generateStatistics() {
    // Retrieve the list of products
    ObservableList<Produit> produitList = FXCollections.observableArrayList(sc.afficherProduit());

    // Calculate the number of products by category
    Map<String, Integer> countMap = getProduitCountByCategory(produitList);

    // Create the data for the chart
    XYChart.Series<String, Number> countData = new XYChart.Series<>();
    for (String category : countMap.keySet()) {
        countData.getData().add(new XYChart.Data<>(category, countMap.get(category)));
    }

    // Create the chart
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
    chart.getData().add(countData);

    // Show the chart in a dialog box
    Alert chartAlert = new Alert(Alert.AlertType.INFORMATION);
    chartAlert.setTitle("Product Statistics");
    chartAlert.setHeaderText("Number of Products by Category");
    chartAlert.getDialogPane().setContent(chart);
    chartAlert.showAndWait();
}
    
   public void chercherProduit() {
    FilteredList<Produit> filteredData = new FilteredList<>(FXCollections.observableArrayList(sc.afficherProduit()), b -> true);
    Recherche.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(rec -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (rec.getNom().toLowerCase().contains(lowerCaseFilter)
                    || rec.getDescription().toLowerCase().contains(lowerCaseFilter)
                    || rec.getBrand().toLowerCase().contains(lowerCaseFilter)
                    || String.valueOf(rec.getId()).contains(lowerCaseFilter)
                    || String.valueOf(rec.getIdCategory()).contains(lowerCaseFilter)
                    || String.valueOf(rec.getPrix()).contains(lowerCaseFilter)
                    || rec.getNomCategory().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }
        });
    });
    SortedList<Produit> sortedData = new SortedList<>(filteredData);
    sortedData.comparatorProperty().bind(TableView.comparatorProperty());
    TableView.setItems(sortedData);
}

    @FXML
    private void imprimerproduit(ActionEvent event) {
        
        Produit selectedObject = TableView.getSelectionModel().getSelectedItem();

    if (selectedObject != null) {
        int id = selectedObject.getId();
        //int idCategory = selectedObject.getIdCategory();
        String nom = selectedObject.getNom();
        String brand = selectedObject.getBrand();
        String description = selectedObject.getDescription();
        String nomCategory = selectedObject.getNomCategory();
        double prix = selectedObject.getPrix();
        
        
        try {
            // Chemin du fichier sur le bureau
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            String fileName = "donnees.txt";

            // Création du fichier sur le bureau
            File file = new File(desktopPath + fileName);
            PrintWriter writer = new PrintWriter(file);

            // Écriture des données dans le fichier
            writer.println("id: " + id);
            writer.println("nom: " + nom);
            writer.println("brand: " + brand);
            writer.println("description: " + description);
            writer.println("nomCategory: " + nomCategory);
            writer.println("prix: " + prix);
            

            // Fermeture du fichier
            writer.close();

            System.out.println("Données imprimées dans le fichier " + desktopPath + fileName + ".");
            
            // Ouverture de la boîte de dialogue d'impression
            PrinterJob job = PrinterJob.getPrinterJob();
            boolean ok = job.printDialog();
            if (ok) {
                job.print();
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de l'impression des données sur le fichier.");
            e.printStackTrace();
        } catch (PrinterException ex) {
            System.err.println("Erreur lors de l'impression des données.");
            ex.printStackTrace();
        }
    }
    }

    @FXML
    private void pdfproduit(MouseEvent event) {
         Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, new FileOutputStream("Détail du produit.pdf"));

            document.open();

            Paragraph paragraph = new Paragraph("Détails du produit");
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            document.add(Chunk.NEWLINE);

            PdfPTable pdfTable = new PdfPTable(2);

            Produit produit = TableView.getSelectionModel().getSelectedItem();

            pdfTable.addCell("Nom du champ");
            pdfTable.addCell("Valeur");

            pdfTable.addCell("ID");
            pdfTable.addCell(String.valueOf(produit.getId()));

            pdfTable.addCell("Marque");
            pdfTable.addCell(String.valueOf(produit.getBrand()));

            pdfTable.addCell("Nom Produit");
            pdfTable.addCell(String.valueOf(produit.getNom()));

            pdfTable.addCell("Description");
            pdfTable.addCell(produit.getDescription());

            pdfTable.addCell("Nom Categorie");
            pdfTable.addCell(produit.getNomCategory());

            pdfTable.addCell("Prix");
            pdfTable.addCell(String.valueOf(produit.getPrix()));

           

            document.add(pdfTable);

            document.close();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Export PDF");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier PDF a été généré avec succès !");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /*  @FXML
    private void pdfproduit(MouseEvent event) {
        
     Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, new FileOutputStream("Liste des produits.pdf"));

            document.open();

            Paragraph paragraph = new Paragraph("Liste des produits");
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            document.add(Chunk.NEWLINE);

            PdfPTable pdfTable = new PdfPTable(7);

            // ajouter les noms des colonnes sur le tableau
            pdfTable.addCell("ID");
            pdfTable.addCell("Nom du produit");
            pdfTable.addCell("Marque");
            pdfTable.addCell("Description");
            pdfTable.addCell("Prix");
            pdfTable.addCell("Catégorie");
          

            // inserer les valeurs de la tableview dans le tableau
            for (Produit produit : TableView.getItems()) {
               
                pdfTable.addCell(String.valueOf(produit.getId()));
                pdfTable.addCell(String.valueOf(produit.getNom()));
                pdfTable.addCell(String.valueOf(produit.getBrand()));
                pdfTable.addCell(produit.getDescription());
               
                pdfTable.addCell(String.valueOf(produit.getPrix()));
                pdfTable.addCell(produit.getNomCategory());
            }

            document.add(pdfTable);

            document.close();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Export PDF");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier PDF a été généré avec succès !");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }    
        
    }*/

    
}