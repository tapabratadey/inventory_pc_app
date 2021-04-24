package controller;

import javafx.application.Platform;
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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    public TableView productTable;
    public TableColumn productID;
    public TableColumn productName;
    public TableColumn productInvLevel;
    public TableColumn productPriceUnit;

    private TableView partTable;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInvLevel;
    public TableColumn partPriceUnit;
    public TextField searchProductsTxt;
    public TextField searchPartTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productTable.setItems(Inventory.getProducts());

        productID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInvLevel.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPriceUnit.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        /*for (Part part: Inventory.getParts()){
            System.out.println(part.getName());
        }*/
        partTable.setItems(Inventory.getParts());
        partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceUnit.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }
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

    public void toModifyPart(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/ModifyPart.fxml")));
        Parent root = (Parent) loader.load();
        ModifyPart modPart = loader.getController();
        Part part = partTable.getSelectionModel().getSelectedItems();
        int idx = partTable.getSelectionModel().getSelectedIndex();
        if (part != null){ modPart.partToModify(part, idx); }

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 668, 400);
        stage.setTitle("Modify Part");

        //Set the Scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }

    public void toAddProduct(ActionEvent actionEvent) throws IOException {
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

    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyProduct.fxml")));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 1032, 645);
        stage.setTitle("Modify Product");

        //Set the Scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }

    public void exitProgram(ActionEvent actionEvent) throws IOException {
        System.exit(0);
    }

    public void searchProductsButton(ActionEvent actionEvent) {
        String searchProdText = searchProductsTxt.getText();
        productTable.getSelectionModel().select(Inventory.searchProducts(searchProdText));
    }

    public void searchPartButton(ActionEvent actionEvent) {
        String searchPartText = searchPartTxt.getText();
        partTable.getSelectionModel().select(Inventory.searchParts(searchPartText));

    }

    public void toDeleteProduct(ActionEvent actionEvent) {
        Alert alertUser = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        Optional<ButtonType> optButton = alertUser.showAndWait();
        if (optButton.isPresent() && optButton.get() == ButtonType.OK){
            ObservableList<Product> products, aPart;
            products = productTable.getItems();
            aPart = productTable.getSelectionModel().getSelectedItems();
            aPart.forEach(products::remove);
        }
    }

    public void toDeletePart(ActionEvent actionEvent) {
        Alert alertUser = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        Optional<ButtonType> optButton = alertUser.showAndWait();
        if (optButton.isPresent() && optButton.get() == ButtonType.OK){
            ObservableList<Part> parts, aPart;
            parts = partTable.getItems();
            aPart = partTable.getSelectionModel().getSelectedItems();
            aPart.forEach(parts::remove);
        }
    }
}
