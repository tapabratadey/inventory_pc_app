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

public class AddProduct implements Initializable {
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
    public void searchPartButton(ActionEvent actionEvent) {
        String searchPartText = searchPartTxt.getText();
        partTable.getSelectionModel().select(Inventory.searchParts(searchPartText));
    }


    public void saveProduct(ActionEvent actionEvent) {
        int idCounter = 0;
        for (Product product: Inventory.getProducts()){
            if (product.getId() > idCounter){
                idCounter = product.getId();
            }
        }
//        System.out.println("String.valueOf(++idCounter)");
//        System.out.println(String.valueOf(++idCounter));
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

    public void addProductButton(ActionEvent actionEvent) {
        ObservableList<Part> aPart;
        aPart = partTable.getSelectionModel().getSelectedItems();
        aPart.forEach((part) -> {
            int idx = partTable.getSelectionModel().getSelectedIndex();
            if (part != null){ asctdPart.setAssociatedParts(part); }
        });

    }
}
