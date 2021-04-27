package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * AddProduct controls the AddProduct view
 * initliaze(): updates table view calling updateTable()
 *              sets up parts and associated tables
 * RUNTIME_ERROR: NumberFormatException while converting string to ints
 *                used Integer.parseInt();
 * FUTURE_ENHANCEMENT:  Add more product info
 */

public class AddProduct implements Initializable {
    //vars
    public TableView<Part> partTable;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInvLevel;
    public TableColumn partPriceUnit;
    public TextField searchPartsTxt;
    public TextField id;
    public TextField max;
    public TextField min;
    public TextField price;
    public TextField inv;
    public TextField name;
    public TableView<Part> associatedPartTable;
    public TableColumn associatedPartID;
    public TableColumn associatedPartName;
    public TableColumn associatedPartInvLevel;
    public TableColumn associatedPartPriceUnit;
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getParts());
        partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceUnit.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        associatedPartID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        associatedPartInvLevel.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        associatedPartPriceUnit.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }

    /**
     * Takes the user back to the Main view
     * @param actionEvent
     * @throws IOException
     */
    public void addProductCancelButton(ActionEvent actionEvent) throws IOException {
        Alert alertUser = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        Optional<ButtonType> optButton = alertUser.showAndWait();
        if (optButton.isPresent() && optButton.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 600);
            stage.setTitle("Inventory System");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * searches for a part in the parts table
     * @param actionEvent
     */
    public void searchPartButton(ActionEvent actionEvent) {
        String searchPartText = searchPartsTxt.getText();
        ObservableList<Part> parts = Inventory.searchParts(searchPartText);
        if (parts.size() == 0){
            Alert alertUser = new Alert(Alert.AlertType.ERROR, "Part not found");
            alertUser.showAndWait();
        }else{
            partTable.setItems(parts);
            searchPartsTxt.setText("");
        }
    }

    /**
     * saveProduct saves a new part to to parts list
     * @param actionEvent
     */
    public void saveProduct(ActionEvent actionEvent) {
        try {
            int idCounter = 0;
            for (Product prod: Inventory.getProducts()){
                if (prod.getId() > idCounter){
                    idCounter = prod.getId();
                }
            }
            partID.setText(String.valueOf(++idCounter));
            String pName = name.getText();
            int invLevel = Integer.parseInt(inv.getText());
            double priceCost = Double.parseDouble(price.getText());
            int partMin = Integer.parseInt(min.getText());
            int partMax = Integer.parseInt(max.getText());
            if (partMin > partMax){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Min value is not allowed to be greater than Max. " +
                        "Please try again.");
                alert.showAndWait();
            }
            else if (invLevel > partMax || invLevel < partMin){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Inventory level cannot be less than min or greater " +
                        "than max. Please try again.");
                alert.showAndWait();
            }else{
                Product product = new Product(idCounter, pName, priceCost, invLevel, partMin, partMax);
                for (int i = 0; i < associatedParts.size(); i++){
//                    System.out.println(product.)
                    product.setAssociatedParts(associatedParts.get(i));
                }
                Inventory.addProduct(product);
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
                Stage stage = (Stage) ((Parent)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 600);
                stage.setTitle("Inventory System");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch(NumberFormatException | IOException e){
            System.out.println(e);
            Alert alertUser = new Alert(Alert.AlertType.CONFIRMATION, "Invalid input. Try again.");
            Optional<ButtonType> optButton = alertUser.showAndWait();
        }
    }

    /**
     * deleteAssPart removes an associated part from the associated parts table
     * @param actionEvent
     */
    public void deleteAssPart(ActionEvent actionEvent) {
        Alert alertUser = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        Optional<ButtonType> optButton = alertUser.showAndWait();
        try {
            if (optButton.isPresent() && optButton.get() == ButtonType.OK) {
                Part part = associatedPartTable.getSelectionModel().getSelectedItem();
                if (part != null){
                    associatedParts.remove(part);
                    associatedPartTable.setItems(associatedParts);
                }
            }
        } catch (IndexOutOfBoundsException | NoSuchElementException err) {
            System.out.println(err);
        }
    }

    /**
     * addProductButton adds a part to the associated parts table
     * @param actionEvent
     */
    public void addProductButton(ActionEvent actionEvent) {
        Part part = partTable.getSelectionModel().getSelectedItem();
        if (part != null && !associatedParts.contains(part)){
            associatedParts.add(part);
            associatedPartTable.setItems(associatedParts);
        }
    }
}