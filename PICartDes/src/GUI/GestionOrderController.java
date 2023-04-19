/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Element;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Order;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Alert.AlertType;
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
//import javax.swing.text.Document;
import service.OrderService;
import com.itextpdf.text.Document;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.transformation.SortedList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class GestionOrderController implements Initializable {

    OrderService orderService;
    ObservableList<Order> listOrders = FXCollections.observableArrayList();
    Order order;
    GestionOrderController gestionOrderController;

    @FXML
    private TableView<Order> commandeTableView;
    @FXML
    private TableColumn<Order, String> date;
    @FXML
    private TableColumn<Order, String> Etat;
    @FXML
    private TableColumn<Order, String> nomClientCell;
    @FXML
    private TableColumn<Order, String> actionCell;
    @FXML
    private TextField searchInput;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gestionOrderController = this;
        orderService = new OrderService();
        LoadData();
        searchEmploye() ;
    }

    public void refreshTable() {

        listOrders.clear();
        listOrders.addAll(orderService.getAll());
        commandeTableView.setItems(listOrders);

    }

    private void LoadData() {

        refreshTable();
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        Etat.setCellValueFactory(new PropertyValueFactory<>("state"));
        nomClientCell.setCellValueFactory(cellData
                -> {
            return new SimpleStringProperty(cellData.getValue().getUser().getId() + "");
        });
        actionCell.setCellFactory(createActionButton());

    }

    private Callback<TableColumn<Order, String>, TableCell<Order, String>> createActionButton() {
        Callback<TableColumn<Order, String>, TableCell<Order, String>> cellFoctory = (TableColumn<Order, String> param) -> {
            // make cell containing buttons
            final TableCell<Order, String> cell = new TableCell<Order, String>() {
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
                                order = commandeTableView.getSelectionModel().getSelectedItem();
                                orderService.delete(order);
                                refreshTable();
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {

                                order = commandeTableView.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUpdateOrder.fxml"));

                                Parent root;
                                root = loader.load();

                                AddUpdateOrderController addUpdateOrderController = loader.getController();
                                addUpdateOrderController.initializeOrderController(gestionOrderController);
                                addUpdateOrderController.initializeTextField(order);
                                addUpdateOrderController.setWindowType("Update");
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(GestionOrderController.class.getName()).log(Level.SEVERE, null, ex);
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
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("AddUpdateOrder.fxml"));
            Parent root = fxml.load();
            AddUpdateOrderController addUpdateOrderController = fxml.getController();
            addUpdateOrderController.setWindowType("Ajout");
            addUpdateOrderController.initializeOrderController(this);
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();

            Scene scene = new Scene(root, 600.0, Math.min(400.0, screenBounds.getHeight()));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    
    
    public void searchEmploye() {
        FilteredList<Order> filteredData = new FilteredList<>(listOrders, p -> true);
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Order -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Order.getState().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Integer.toString(Order.getId()).contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        // wrap the filtered list in a SortedList
        SortedList<Order> sortedData = new SortedList<>(filteredData);
        // bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(commandeTableView.comparatorProperty());
        // add sorted (and filtered) data to the table
        commandeTableView.setItems(sortedData);
    }
    
    
    
    
    
    
    
    
  
    
    
    
    
    
    
    
    
    
    
    @FXML
    public void generatePDF(ActionEvent event) {
    
    // Create content list
List<String> content = new ArrayList<>();

// Add header row to the content list
content.add("First   Name   Last Name   Email   Phone   DateIns");

// Populate table content from userList TableView
for (Order user : commandeTableView.getItems()) {
    String row = user.toString() ;
    content.add(row);
}

// Create a new PDF document
PDDocument document = new PDDocument();

// Add a new page to the document
PDPage page = new PDPage();
document.addPage(page);

// Create a new font for the header
PDType1Font font = PDType1Font.HELVETICA;

// Add content to the PDF document
try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
    // Add the title
    contentStream.beginText();
    contentStream.setFont(font, 36);
    contentStream.newLineAtOffset(50, 650);
    contentStream.showText("User List");
    contentStream.endText();

    // Add the table data
    contentStream.beginText();
    contentStream.setFont(font, 12);
    contentStream.newLineAtOffset(50, 600);

    for (int i = 0; i < content.size(); i++) {
        contentStream.showText(content.get(i));
        contentStream.newLineAtOffset(0, -20);
    }

    contentStream.endText();
    contentStream.close();
}catch(Exception e) {
        e.printStackTrace();
}

// Save the PDF document to a file or stream
    try {
        document.save("order_list.pdf");
        document.close();
    } catch (IOException e) {
        e.printStackTrace();
    }


}
    
    
    
    
    
    
    
    

        
    }

   
    
    

