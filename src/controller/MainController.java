package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Inventory;
import model.Product;
import model.Part;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * MainController controls the Main view
 * The view has two tables: Parts and Products,
 * Add/modify/delete buttons for both tables.
 * And an exit button.
 * RUNTIME_ERROR: IndexOutOfBoundsException in toDeleteProduct()
 *                fixed it by grabbing the table's selected index
 *                then if (index >= 0) remove the product
 * FUTURE_ENHANCEMENT: Using a db to store data than computer memory.
 */

public class MainController implements Initializable {
    // vars
    public TableView<Product> productTable;
    public TableColumn productID;
    public TableColumn productName;
    public TableColumn productInvLevel;
    public TableColumn productPriceUnit;

    public TableView<Part> partTable;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInvLevel;
    public TableColumn partPriceUnit;
    public TextField searchProductsTxt;
    public TextField searchPartsTxt;

    /**
     * @param url
     * @param resourceBundle
     * populates and sets up Product and Parts tables
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productTable.setItems(Inventory.getProducts());
        productID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInvLevel.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPriceUnit.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        partTable.setItems(Inventory.getParts());
        partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceUnit.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }

    /**
     * click on add button under parts table fires toAddPart() which loads addpart.fxml
     * @param actionEvent
     * @throws IOException
     */

    public void toAddPart(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddPart.fxml")));
        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        //Create the New Scene
        Scene scene = new Scene(root, 668, 400);
        stage.setTitle("Add Part");
        //Set the Scene on the stage
        stage.setScene(scene);
        //raise the curtain
        stage.show();
    }

    /**
     * Click on modify button under parts table fires toModifyPart() which loads ModifyPart.fxml
     * A selected part and it's index to be modified gets passed to the partToModify()
     * @param actionEvent
     * @throws IOException
     */

    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        Part part = partTable.getSelectionModel().getSelectedItem();
        if (part == null){
            Alert alertUserErr = new Alert(Alert.AlertType.ERROR, "Please select a part to modify");
            alertUserErr.showAndWait();
        }else{
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/ModifyPart.fxml")));
            Parent root = (Parent) loader.load();
            ModifyPart modPart = loader.getController();
            int idx = partTable.getSelectionModel().getSelectedIndex();
            modPart.partToModify(part, idx);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 668, 400);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * click on add button under Products table fires toAddProduct() which loads addProduct.fxml
     * @param actionEvent
     * @throws IOException
     */

    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        ObservableList<Part> aPart;
        aPart = partTable.getSelectionModel().getSelectedItems();
        aPart.forEach((part) -> {
            System.out.println(part.getId());
        });
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddProduct.fxml")));
        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        //Create the New Scene
        Scene scene = new Scene(root, 1032, 645);
        stage.setTitle("Add Product");
        //Set the Scene on the stage
        stage.setScene(scene);
        //raise the curtain
        stage.show();
    }

    /**
     * Click on modify button under Products table fires toModifyProduct() which loads ModifyProduct.fxml
     * A selected product and it's index to be modified gets passed to the productToModify()
     * @param actionEvent
     * @throws IOException
     */

    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        Product product = productTable.getSelectionModel().getSelectedItem();
        if (product == null){
            Alert alertUserErr = new Alert(Alert.AlertType.ERROR, "Please select a product to modify");
            alertUserErr.showAndWait();
        }else{
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/ModifyProduct.fxml")));
            Parent root = (Parent) loader.load();
            ModifyProduct modProduct = loader.getController();
            int idx = productTable.getSelectionModel().getSelectedIndex();
            modProduct.productToModify(product, idx);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1032, 645);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * click on the exit button calls exitProgram which exits the program safely
     * @param actionEvent
     * @throws IOException
     */

    public void exitProgram(ActionEvent actionEvent) throws IOException {
        System.exit(0);
    }

    /**
     * Product search string gets passed to Inventory.searchProducts which returns a product
     * the product gets selected in the productTable
     * @param actionEvent
     * @throws IOException
     */

    public void searchProductsButton(ActionEvent actionEvent) {
        String searchProdText = searchProductsTxt.getText();
        ObservableList<Product> products = Inventory.searchProducts(searchProdText);
        if (products.size() == 0){
            Alert alertUser = new Alert(Alert.AlertType.ERROR, "Product not found");
            alertUser.showAndWait();
        }else{
            productTable.setItems(products);
            searchPartsTxt.setText("");
//            products.forEach((product) -> {
//                productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//                productTable.getSelectionModel().select(product);
//            });
        }

    }

    /**
     * Part search string gets passed to Inventory.searchParts which returns a product
     * the part gets selected in the partTable
     * @param actionEvent
     * @throws IOException
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
     * click on delete button under products table fires toDeleteProduct()
     * a selected product gets removed
     * An confirmation alert is sent to the user.
     * @param actionEvent
     * @throws IOException
     */

    public void toDeleteProduct(ActionEvent actionEvent) {
        Alert alertUser = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        Optional<ButtonType> optButton = alertUser.showAndWait();
        try{
            if (optButton.isPresent() && optButton.get() == ButtonType.OK) {
                ObservableList<Product> products, aPart;
                products = productTable.getItems();
                aPart = productTable.getSelectionModel().getSelectedItems();
                aPart.forEach((part) -> {
                    if (part.getAssociatedParts().size() == 0){
                        int idx = productTable.getSelectionModel().getSelectedIndex();
                        if (idx >= 0) { products.remove(idx); }
                    }else{
                        Alert alertUserErr = new Alert(Alert.AlertType.ERROR, "Cannot delete a product with an " +
                                "associated part");
                        alertUserErr.showAndWait();
                    }

                });

            }
        }
        catch(IndexOutOfBoundsException | NoSuchElementException err){
            System.out.println(err);
        }
    }

    /**
     * click on delete button under parts table fires toDeletePart()
     * a selected part gets removed
     * An confirmation alert is sent to the user.
     * @param actionEvent
     * @throws IOException
     */

    public void toDeletePart(ActionEvent actionEvent) {
        Alert alertUser = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        Optional<ButtonType> optButton = alertUser.showAndWait();
        if (optButton.isPresent() && optButton.get() == ButtonType.OK){
            ObservableList<Part> parts, aPart;
            parts = partTable.getItems();
            aPart = partTable.getSelectionModel().getSelectedItems();
            int idx = partTable.getSelectionModel().getSelectedIndex();
            if (idx >= 0) { parts.remove(idx); }
        }
    }
}
