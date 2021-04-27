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
    public static ObservableList<Part> searchParts(String partToSearch){
        ObservableList<Part> findParts = FXCollections.observableArrayList();
        for (Part part:parts){
            String convert = String.valueOf(part.getId());
            if (part.getName().contains(partToSearch) || convert.contains(partToSearch)){
                findParts.add(part);
            }
        }
        return findParts;
    }
    public static ObservableList<Product> searchProducts(String partToSearch){
        ObservableList<Product> findProducts = FXCollections.observableArrayList();
        for (Product prod:products){
            String convert = String.valueOf(prod.getId());
            if (prod.getName().contains(partToSearch) || convert.contains(partToSearch)){
                findProducts.add(prod);
            }
        }
        return findProducts;
    }
}