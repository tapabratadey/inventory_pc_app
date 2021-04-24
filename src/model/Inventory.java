package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Inventory {
    public static ObservableList<Part> parts = FXCollections.observableArrayList();
    public static ObservableList<Product> products = FXCollections.observableArrayList();
//    public static ObservableList<Product> associatedParts = FXCollections.observableArrayList();

    public static void addPart(Part part){ parts.add(part); }
    public static void addProduct(Product product){ products.add(product); }
//    public static void setAssociatedParts(Part part){
//        associatedParts.add(part);
//    }

    public static ObservableList<Part> getParts(){ return parts; }
    public static ObservableList<Product> getProducts(){
        return products;
    }
//    public static ObservableList<Part> getAssociatedParts(){
//        return associatedParts;
//    }
    public static Part searchParts(String partToSearch){
        for (Part part : getParts()){
            String idInString = String.valueOf(part.getId());
            if ((part.getName().contains(partToSearch)) ||
                    (idInString == partToSearch)){
                return part;
            }
            else{
                Alert alertUser = new Alert(Alert.AlertType.ERROR, "Part not found");
                Optional<ButtonType> optButton = alertUser.showAndWait();
            }

        }
        return null;
    }
    public static Product searchProducts(String partToSearch){
        for (Product prod : getProducts()){
            String idInString = String.valueOf(prod.getId());
            if ((prod.getName().contains(partToSearch)) ||
                    (idInString == partToSearch)){
                return prod;
            }
            else{
                Alert alertUser = new Alert(Alert.AlertType.ERROR, "Product not found");
                Optional<ButtonType> optButton = alertUser.showAndWait();
            }
        }
        return null;
    }
}



