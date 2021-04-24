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
import model.Product;
import model.Inventory;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * ModifyProduct controls the ModifyProduct view
 * initliaze(): updates table view calling updateTable()
 *              sets up parts and associated tables
 * modifyProductCancelButton(): returns the user back to main view
 * productToModify(): retrieves the product to modify
 * addProductButton(): adds parts to associated parts lists
 * deleteAssPart(): removes parts from associated parts table
 * saveProduct(): save a new part and it's associated parts to the list
 * searchPartButton(): search button handler
 * addProductCancelButton(): returns the user back to the main view
 * RUNTIME_ERROR: NumberFormatException while converting string to ints
 *                used Integer.parseInt();
 * FUTURE_ENHANCEMENT: Add more product info.
 */


public class ModifyProduct implements Initializable {
    public TableView partTable;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInvLevel;
    public TableColumn partPriceUnit;
    public TextField addPartMax;
    public TextField addPartMin;
    public TextField searchPartTxt;
    public TextField id;
    public TextField max;
    public TextField min;
    public TextField price;
    public TextField inv;
    public TextField name;
    Product productToModify;

    public TableView associatedPartTable;
    public TableColumn associatedPartID;
    public TableColumn associatedPartName;
    public TableColumn associatedPartInvLevel;
    public TableColumn associatedPartPriceUnit;
    Product asctdPart;
    int toModifyIdx;

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

    public void modifyProductCancelButton(ActionEvent actionEvent) throws IOException {
        Alert alertUser = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        Optional<ButtonType> optButton = alertUser.showAndWait();
        if (optButton.isPresent() && optButton.get() == ButtonType.OK) {
            //load widget hierarchy of next screen
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
            //get the stage from an event's source widget
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            //Create the New Scene
            Scene scene = new Scene(root, 1200, 600);
            stage.setTitle("Inventory System");
            //Set the Scene on the stage
            stage.setScene(scene);
            //raise the curtain
            stage.show();
        }
    }

    public void productToModify(Product product, int idx) {
        productToModify = product;
        toModifyIdx = idx;
        Product newProduct = product;
        id.setText(Integer.toString(newProduct.getId()));
        name.setText(newProduct.getName());
        max.setText(Integer.toString(newProduct.getMax()));
        min.setText(Integer.toString(newProduct.getMin()));
        price.setText(Double.toString(newProduct.getPrice()));
        inv.setText(Integer.toString(newProduct.getStock()));
    }

    public void addProductButton(ActionEvent actionEvent) {
        ObservableList<Part> aPart;
        aPart = partTable.getSelectionModel().getSelectedItems();
        aPart.forEach((part) -> {
            int idx = partTable.getSelectionModel().getSelectedIndex();
            if (part != null){ asctdPart.setAssociatedParts(part); }
        });

    }

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

    public void saveProduct(ActionEvent actionEvent) throws IOException {
        int idPart = asctdPart.getId();
        String partName = name.getText();
        int invLevel = Integer.parseInt(inv.getText());
        double priceCost = Double.parseDouble(price.getText());
        int partMin = Integer.parseInt(min.getText());
        int partMax = Integer.parseInt(max.getText());
        int machineID = Integer.parseInt(id.getText());
        Product addProduct = new Product(idPart, partName, priceCost, invLevel, partMin,
                partMax);
        Inventory.getProducts().set(toModifyIdx, addProduct);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
        //get the stage from an event's source widget
        Stage stage = (Stage) ((Parent) actionEvent.getSource()).getScene().getWindow();
        //Create the New Scene
        Scene scene = new Scene(root, 1200, 600);
        stage.setTitle("Inventory System");
        //Set the Scene on the stage
        stage.setScene(scene);
        //raise the curtain
        stage.show();
    }

    public void searchPartButton(ActionEvent actionEvent) {
        String searchPartText = searchPartTxt.getText();
        partTable.getSelectionModel().select(Inventory.searchParts(searchPartText));
    }

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
    public void updateTable(){
        partTable.setItems(Inventory.getParts());
    }
}
