package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inventory;
import model.InHouse;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class AddPart {
    public RadioButton toggleOutsourced;
    public boolean toggleInHouse = true;
    public TextField addPartMachineID;
    public TextField partID;
    public TextField addPartName;
    public TextField addPartInv;
    public TextField addPartPriceCost;
    public TextField addPartMax;
    public TextField addPartMin;
    public RadioButton toggleInHouseBtn;
    public Label machineIDLabelTxt;

    public void addPartSaveButton(ActionEvent actionEvent) throws IOException{
        int idCounter = 0;
        for (Part part: Inventory.getParts()){
            if (part.getId() > idCounter){
                idCounter = part.getId();
            }
        }
//        System.out.println("String.valueOf(++idCounter)");
//        System.out.println(String.valueOf(++idCounter));
        partID.setText(String.valueOf(++idCounter));
        String partName = addPartName.getText();
        int invLevel = Integer.parseInt(addPartInv.getText());
        double priceCost = Double.parseDouble(addPartPriceCost.getText());
        int partMin = Integer.parseInt(addPartMin.getText());
        int partMax = Integer.parseInt(addPartMax.getText());

        try {
            if (partMin > partMax){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min value is not allowed to be greater than Max");
                alert.showAndWait();
            }
            else if (invLevel > partMax || invLevel < partMin){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory level cannot be less than min or greater " +
                        "than max");
                alert.showAndWait();
            }
            else{
                if (toggleOutsourced.isSelected()){
                    String compName = addPartMachineID.getText();
                    Outsourced addPart = new Outsourced(idCounter, partName,  priceCost,invLevel, partMin,
                            partMax, compName);
                    Inventory.addPart(addPart);
                }
                if (toggleInHouseBtn.isSelected()){
//                    System.out.println(addPartMachineID.getText());
                    int machineID = Integer.parseInt(addPartMachineID.getText());
                    InHouse addPart = new InHouse(idCounter, partName, priceCost,invLevel, partMin,
                            partMax, machineID);
                    Inventory.addPart(addPart);
                }
            }
           /* for (Part part: Inventory.getParts()){
                System.out.println(part.getName());
            }*/
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
        catch(NumberFormatException e){
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input");
            alert.showAndWait();
        }
    }

    public void addPartCancelButton(ActionEvent actionEvent) throws IOException {
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

    public void addPartinHouse(ActionEvent actionEvent) {
        toggleInHouse = true;
        machineIDLabelTxt.setText("Machine ID");
    }

    public void addPartOutsourced(ActionEvent actionEvent) {
        toggleInHouse = false;
        machineIDLabelTxt.setText("Company Name");
    }
}
