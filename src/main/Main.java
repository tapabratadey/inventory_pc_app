/**
 *
 * @author tapabratadey  Place Your Name Here
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.Product;
import model.Inventory;
//import model.Part;
//import model.InHouse;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
        primaryStage.setTitle("Inventory System");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {

        Product testProd1 = new Product(1, "testProd1", 10.0, 3, 3, 1);
        Inventory.addProduct(testProd1);

        launch(args);

    }
}
