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

public class ModifyPart {
    public TextField modifyPartMin;
    public TextField partID;
    public RadioButton toggleInHouseBtn;
    public RadioButton toggleOutsourced;
    public Label machineIDLabelTxt;
    public TextField modifyPartMachineID;
    public TextField modifyPartMax;
    public TextField modifyPartPriceCost;
    public TextField modifyPartInv;
    public TextField modifyPartName;
    public boolean toggleInHouse = true;
    Part partToModify;
    int toModifyIdx;

    public void modifyPartCancelButton(ActionEvent actionEvent) throws IOException {
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
    public void modifyPartinHouse(ActionEvent actionEvent) {
        toggleInHouse = true;
        machineIDLabelTxt.setText("Machine ID");
    }

    public void modifyPartOutsourced(ActionEvent actionEvent) {
        toggleInHouse = false;
        machineIDLabelTxt.setText("Company Name");
    }

    public void modifyPartSaveButton(ActionEvent actionEvent) throws IOException {
        int id = partToModify.getId();
        String partName = modifyPartName.getText();
        int invLevel = Integer.parseInt(modifyPartInv.getText());
        double priceCost = Double.parseDouble(modifyPartPriceCost.getText());
        int partMin = Integer.parseInt(modifyPartMin.getText());
        int partMax = Integer.parseInt(modifyPartMax.getText());
        try {
            if (toggleOutsourced.isSelected()) {
                String compName = modifyPartMachineID.getText();
                Outsourced addPart = new Outsourced(id, partName, priceCost, invLevel, partMin,
                        partMax, compName);
                Inventory.getParts().set(toModifyIdx, addPart);
            }
            if (toggleInHouseBtn.isSelected()) {
                //                    System.out.println(addPartMachineID.getText());
                int machineID = Integer.parseInt(modifyPartMachineID.getText());
                InHouse addPart = new InHouse(id, partName, priceCost, invLevel, partMin,
                        partMax, machineID);
                Inventory.getParts().set(toModifyIdx, addPart);
            }

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));

            //get the stage from an event's source widget
            Stage stage = (Stage) ((Parent) actionEvent.getSource()).getScene().getWindow();

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

    public void partToModify(Part part, int idx) {
        partToModify = part;
        toModifyIdx = idx;

        if (part instanceof InHouse){
            InHouse inHousePart = (InHouse)part;
            toggleInHouseBtn.setSelected(true);
            machineIDLabelTxt.setText("Machine ID");
            modifyPartName.setText(inHousePart.getName());
            modifyPartInv.setText(Integer.toString(inHousePart.getStock()));
            modifyPartPriceCost.setText(Double.toString(inHousePart.getPrice()));
            modifyPartMax.setText(Integer.toString(inHousePart.getMax()));
            modifyPartMin.setText(Integer.toString(inHousePart.getMin()));
            machineIDLabelTxt.setText(Integer.toString(inHousePart.getMachineID()));
        }
    }
}
