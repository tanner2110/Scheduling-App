package InventorySide;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Utility.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ResourceBundle;


/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 11/11/2020
 * ModifyPartInHouseController is controller of in house parts page
 */
public class ModifyPartInHouseController implements Initializable {


    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private TextField nameTB;
    @FXML
    private TextField inventoryTB;
    @FXML
    private TextField maxTB;
    @FXML
    private TextField priceTB;
    @FXML
    private TextField minTB;
    @FXML
    private TextField idTB;
    @FXML
    private TextField machineIdTB;
    @FXML
    private Label warningLabel;


    /**
     * selectedPart is used to store the part that is being updated
     */
    InHouse selectedPart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedPart = (InHouse) Inventory.getUpdatingPart();
        idTB.setText(Integer.toString(selectedPart.getId()));
        maxTB.setText(Integer.toString(selectedPart.getMax()));
        minTB.setText(Integer.toString(selectedPart.getMin()));
        nameTB.setText(selectedPart.getName());
        inventoryTB.setText(Integer.toString(selectedPart.getStock()));
        priceTB.setText(Double.toString(selectedPart.getPrice()));
        machineIdTB.setText((selectedPart.getMachineId()));
        inHouseRadio.fire();



    }

    /**
     *
     * @param mouseEvent save part
     * @throws IOException
     * ModPartSaveBut saves changes to part in house
     */
        public void ModPartSaveBut(MouseEvent mouseEvent) throws IOException, SQLException {
          /* selectedPart.setId(Integer.valueOf(idTB.getText()));
            selectedPart.setMax((Integer.valueOf(maxTB.getText())));
            selectedPart.setMin(Integer.valueOf(minTB.getText()));
            selectedPart.setPrice(Double.valueOf(priceTB.getText()));
            selectedPart.setStock(Integer.valueOf(inventoryTB.getText()));
            selectedPart.setName(nameTB.getText());
            selectedPart.setMachineId(machineIdTB.getText());*/

            try {
                Statement sqlStatement = LogInController.returnConnection().createStatement();
                String sqlCommand = "UPDATE WJ07LG4.inHouse_parts SET" +
                        " part_id=" + idTB.getText() + "," +
                        " partName = '" + nameTB.getText() + "'," +
                        " inventory = " + inventoryTB.getText() + "," +
                        " cost = " + priceTB.getText() + "," +
                        "max = " + maxTB.getText() + "," +
                        "min =" + minTB.getText() + "," +
                        "batch_number ='" + machineIdTB.getText() + "'" +
                        " WHERE part_id = " + idTB.getText() + ";";
                sqlStatement.execute(sqlCommand);

                SceneLoader.loadInventoryMainScreen();
            }
            catch (SQLSyntaxErrorException e){

                warningLabel.setText("Make sure each box has a proper value");

            }




    }

    /**
     *
     * @param mouseEvent cancel
     * @throws Exception
     * ModPartCancelBut cancels changes to part exits
     */
    public void ModPartCancelBut(MouseEvent mouseEvent) throws Exception {

        SceneLoader.loadInventoryMainScreen();
    }
}
