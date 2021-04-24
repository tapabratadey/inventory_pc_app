package controller;

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
    public TableView partTable;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInvLevel;
    public TableColumn partPriceUnit;
    public TextField addPartMax;
    public TextField addPartMin;
    public TextField searchPartTxt;
    Product productToModify;
    public TextField id;
    public TextField max;
    public TextField min;
    public TextField price;
    public TextField inv;
    public TextField name;
    public TableView associatedPartTable;
    public TableColumn associatedPartID;
    public TableColumn associatedPartName;
    public TableColumn associatedPartInvLevel;
    public TableColumn associatedPartPriceUnit;
    Product asctdPart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
        partTable.setItems(Inventory.getParts());
        partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceUnit.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        asctdPart = new Product(1, "test", 10.0, 5, 5, 5);
        associatedPartTable.setItems(asctdPart.getAssociatedParts());
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
            //load widget hierarchy of next screen
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
            //get the stage from an event's source widget
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            //Create the New Scene
            Scene scene = new Scene(root, 1200, 600);
            stage.setTitle("Inventory System");
            //Set the Scene on the stage
            stage.setScene(scene);
            //raise the curtain
                stage.show();
    }
}

    /**
     * searches for a part in the parts table
     * @param actionEvent
     */
    public void searchPartButton(ActionEvent actionEvent) {
        String searchPartText = searchPartTxt.getText();
        partTable.getSelectionModel().select(Inventory.searchParts(searchPartText));
    }

    /**
     * saveProduct saves a new part to to parts list
     * @param actionEvent
     */
    public void saveProduct(ActionEvent actionEvent) {
        int idCounter = 0;
        for (Product product: Inventory.getProducts()){
            if (product.getId() > idCounter){
                idCounter = product.getId();
            }
        }
        partID.setText(String.valueOf(++idCounter));
        String pName = name.getText();
        int invLevel = Integer.parseInt(inv.getText());
        double priceCost = Double.parseDouble(price.getText());
        int partMin = Integer.parseInt(min.getText());
        int partMax = Integer.parseInt(max.getText());
        try {
            asctdPart.setId(idCounter);
            asctdPart.setName(pName);
            asctdPart.setPrice(priceCost);
            asctdPart.setStock(invLevel);
            asctdPart.setMin(partMin);
            asctdPart.setMax(partMax);
            Inventory.addProduct(asctdPart);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
            //get the stage from an event's source widget
            Stage stage = (Stage) ((Parent)actionEvent.getSource()).getScene().getWindow();
            //Create the New Scene
            Scene scene = new Scene(root, 1200, 600);
            stage.setTitle("Inventory System");
            //Set the Scene on the stage
            stage.setScene(scene);
            //raise the curtain
            stage.show();
        }
        catch(NumberFormatException | IOException e){
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input");
            alert.showAndWait();
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
                int idx = associatedPartTable.getSelectionModel().getSelectedIndex();
                if (idx >= 0) {
                    asctdPart.getAssociatedParts().remove(associatedPartTable.getSelectionModel().getSelectedItem());
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
        ObservableList<Part> aPart;
        aPart = partTable.getSelectionModel().getSelectedItems();
        aPart.forEach((part) -> {
            int idx = partTable.getSelectionModel().getSelectedIndex();
            if (part != null){ asctdPart.setAssociatedParts(part); }
        });
    }

    /**
     * updateTable sets up the parts Table
     */
    public void updateTable(){
        partTable.setItems(Inventory.getParts());
    }
}
