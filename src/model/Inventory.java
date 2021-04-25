package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Inventory class holds:
 * 1. parts list
 * 2. products list
 * 3. addPart(): adds an incoming part to the parts list
 * 4. addProduct(): adds an incoming product to the products list
 * 5. searchParts(): searches for a part by finding a match with its partToSearch param
 * 6. searchProducts(): searches for a product by finding a match with its partToSearch param
 *
 * RUNTIME_ERROR: incompatible types error occurred in searchProducts()
 *                while assigning int to string. fixed by using: String.valueOf
 * FUTURE_ENHANCEMENT:search functions can be improved.
 *                    search box can autocomplete using a drop-down list.
 *
 */

public class Inventory {
    public static ObservableList<Part> parts = FXCollections.observableArrayList();
    public static ObservableList<Product> products = FXCollections.observableArrayList();

    public static void addPart(Part part){ parts.add(part); }
    public static void addProduct(Product product){ products.add(product); }
    public static ObservableList<Part> getParts(){ return parts; }
    public static ObservableList<Product> getProducts(){
        return products;
    }
    public static Part searchParts(String partToSearch){
        for (Part part:Inventory.getParts()) {
            if(part.getName().contains(partToSearch) ||
                    new Integer (part.getId()).toString().equals(partToSearch))
                return part;
        }
        Alert alertUser = new Alert(Alert.AlertType.ERROR, "Part not found");
        Optional<ButtonType> optButton = alertUser.showAndWait();
        return null;
    }
    public static Product searchProducts(String partToSearch){
        for (Product prod:Inventory.getProducts()) {
            if(prod.getName().contains(partToSearch) ||
                   new Integer (prod.getId()).toString().equals(partToSearch))
                return prod;
        }
        Alert alertUser = new Alert(Alert.AlertType.ERROR, "Product not found");
        Optional<ButtonType> optButton = alertUser.showAndWait();
        return null;
    }
}