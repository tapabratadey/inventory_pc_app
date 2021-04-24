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
import model.Part;
import model.InHouse;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
        primaryStage.setTitle("Inventory System");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }

    private static void addTestData(){
        Product testProd1 = new Product(1, "testProduct1", 10.0, 3, 3, 1);
        Inventory.addProduct(testProd1);

        Product testProd2 = new Product(2, "testProduct2", 15.0, 5, 5, 3);
        Inventory.addProduct(testProd2);

        Part testPart1 = new InHouse(1, "testPart1", 5.0, 8, 2, 3, 1);
        Inventory.addPart(testPart1);

        Part testPart2 = new InHouse(2, "testPart2", 6.0, 9, 2, 8, 2);
        Inventory.addPart(testPart2);
    }

    public static void main(String[] args) {

        addTestData();

        launch(args);

    }
}
