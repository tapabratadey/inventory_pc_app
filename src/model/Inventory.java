package model;

import com.sun.javafx.collections.ElementObservableListDecorator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    public static ObservableList<Part> parts = FXCollections.observableArrayList();
    public static ObservableList<Product> products = FXCollections.observableArrayList();

    public static void addPart(Part part){ parts.add(part); }
    public static void addProduct(Product product){ products.add(product); }
    public static ObservableList<Part> getParts(){ return parts; }
    public static ObservableList<Product> getProducts(){
        return products;
    }

}



