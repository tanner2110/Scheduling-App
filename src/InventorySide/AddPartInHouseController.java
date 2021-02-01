package InventorySide;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Utility.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 11/11/2020
 * AddPartInHouseController is controller class for the part in house page
 */
public class AddPartInHouseController implements Initializable {


    @FXML
    private TextField AddPartInNameTB;
    @FXML
    private TextField AddPartInInvoTB;
    @FXML
    private TextField AddPartInMaxTB;
    @FXML
    private TextField AddPartInPriceTB;
    @FXML
    private TextField AddPartInMinTB;
    @FXML
    private TextField idTB;
    @FXML
    private TextField AddPartInMachineIdTB;

    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outSourcedRadio;



    @FXML
    private Label errorLabel;

    /**
     * int id is used for storing product or part id
     */
    private int id;  //for the part id


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        inHouseRadio.fire();


        try {
            Statement sqlStatement = Utility.LogInController.returnConnection().createStatement();
            String sqlCommand2 = "SELECT part_ID FROM inHouse_parts " +
                    "UNION ALL " +
                    "SELECT part_id FROM outSourced_parts " +
                    "ORDER BY part_id DESC;";
            ResultSet sqlResult2 = sqlStatement.executeQuery(sqlCommand2);                    //creates car id

            if(sqlResult2.next()) {

                id = sqlResult2.getInt("part_ID") + 1;      //creates and sets id textbox
            }
            else {
                id = 1;
            }
            idTB.setText(Integer.toString(id));

        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }

    }


    /**
     *
     * @param mouseEvent save button that adds a part to the list
     */
    public void AddPartSaveBut(MouseEvent mouseEvent) throws SQLException {

        try {
            if (Integer.parseInt(AddPartInMaxTB.getText()) > Integer.parseInt(AddPartInMinTB.getText())) { // check if min is less than max
                String name = AddPartInNameTB.getText();
                double price = Double.parseDouble(AddPartInPriceTB.getText());
                int inventory = Integer.parseInt(AddPartInInvoTB.getText());
                int min = Integer.parseInt(AddPartInMinTB.getText());
                int max = Integer.parseInt(AddPartInMaxTB.getText());
                String machineId = AddPartInMachineIdTB.getText();


                InHouse part = new InHouse(id, name, price, inventory, min, max, machineId);
                Inventory.addPart(part);


                Statement sqlStatement = Utility.LogInController.returnConnection().createStatement();
                String sqlCommand = "INSERT INTO `WJ07LG4`.`inHouse_parts`" +
                        "(      `part_id`," +
                        "`partName`," +
                        "`inventory`," +
                        "`cost`," +
                        "`max`," +
                        "`min`," +
                        "`batch_number`)" +
                        "VALUES" +
                        "(" + id + ",'" +
                        AddPartInNameTB.getText() + "','" +
                        AddPartInInvoTB.getText() + "','" +
                        AddPartInPriceTB.getText() + "','" +
                        AddPartInMaxTB.getText() + "','" +
                        AddPartInMinTB.getText() + "','" +
                        AddPartInMachineIdTB.getText()  +"');";
                sqlStatement.execute(sqlCommand);

              SceneLoader.loadInventoryMainScreen();


            }//end first if
            else {errorLabel.setText("Max must be greater than min");}
        }
        catch (NumberFormatException | IOException e) {
            errorLabel.setText("Enter proper values in each box.");
        }









}

    /**
     *
     * @param mouseEvent cancel button for add part form
     * @throws Exception
     */
    public void AddPartCancelBut(MouseEvent mouseEvent) throws Exception {

        SceneLoader.loadInventoryMainScreen();

    }


    /**
     *
     * @param mouseEvent  radio button that changes the add part from inhouse to outsourced.
     * @throws Exception
     */
    public void AddPartRadioOut(MouseEvent mouseEvent) throws Exception {

       SceneLoader.loadOutSourcedPart();


    }


}
