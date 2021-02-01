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
 *AddPartOutSourcedController is the controller for the add part out sourced page
 */
public class AddPartOutSourcedController implements Initializable {



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
    private TextField companyNameTB;
    @FXML
    private TextField idTB;

    @FXML
    private RadioButton outRadioButton;

    @FXML
    private Label errorLabel;

    /**
     * int id is used for storing product or part id
     */
    private int id;  //for the part id


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        outRadioButton.fire();


        try {
            Statement sqlStatement = LogInController.returnConnection().createStatement();
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
     * @param mouseEvent save button add part
     */
    public void AddPartSaveButOut(MouseEvent mouseEvent) throws SQLException {

        try {
            if (Integer.parseInt(maxTB.getText()) > Integer.parseInt(minTB.getText())) { // check if min is less than max
                //id = Inventory.createId();
                String name = nameTB.getText();
                double price = Double.parseDouble(priceTB.getText());
                int inventory = Integer.parseInt(inventoryTB.getText());
                int min = Integer.parseInt(minTB.getText());
                int max = Integer.parseInt(maxTB.getText());
                String companyName = companyNameTB.getText();
                OutSourced part = new OutSourced(id, name, price, inventory, min, max, companyName);
                Inventory.addPart(part);

                Statement sqlStatement = LogInController.returnConnection().createStatement();
                String sqlCommand = "INSERT INTO `WJ07LG4`.`outSourced_parts`" +
                        "(`part_id`," +
                        "`partName`," +
                        "`inventory`," +
                        "`cost`," +
                        "`max`," +
                        "`min`," +
                        "`company_name`)" +
                        "VALUES" +
                        "(" + id + ",'" +
                        nameTB.getText() + "','" +
                        inventoryTB.getText()+ "','" +
                        priceTB.getText() + "','" +
                        maxTB.getText() + "','" +
                        minTB.getText() + "','" +
                        companyNameTB.getText() + "'" + ");";
                sqlStatement.execute(sqlCommand);

                SceneLoader.loadInventoryMainScreen();
        }
        else {errorLabel.setText("Max must be greater than min");}
        } catch (NumberFormatException | IOException e) {
            errorLabel.setText("Enter proper values in each box");
        }








    }

    /**
     *
     * @param mouseEvent cancel button add part screen
     * @throws Exception
     */
    public void AddPartCancelButOut(MouseEvent mouseEvent) throws Exception {

        SceneLoader.loadInventoryMainScreen();
    }


    /**
     *
     * @param mouseEvent loads in house part form
     * @throws Exception
     */
    public void InHouseRadioClick(MouseEvent mouseEvent) throws Exception {

        SceneLoader.loadinHousePart();

    }
}
