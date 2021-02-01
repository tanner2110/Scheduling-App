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
 * ModifyPartOutController is the controller of out sourced parts page
 */
public class ModifyPartOutController implements Initializable {




    @FXML
    private TextField nameTB;
    @FXML
    private TextField companyNameTB;
    @FXML
    private RadioButton outRadio;
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
            private Label warningLabel;

    /**
     * selectedPart is used to store the part that is being modified
     */
    OutSourced selectedPart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedPart = (OutSourced) Inventory.getUpdatingPart();
        idTB.setText(Integer.toString(selectedPart.getId()));        //retrieves the object and sets its text boxes
        maxTB.setText(Integer.toString(selectedPart.getMax()));
        minTB.setText(Integer.toString(selectedPart.getMin()));
        nameTB.setText(selectedPart.getName());
        inventoryTB.setText(Integer.toString(selectedPart.getStock()));
        priceTB.setText(Double.toString(selectedPart.getPrice()));
        companyNameTB.setText(selectedPart.getCompanyName());
        outRadio.fire();

    }


    /**
     *
     * @param mouseEvent save part
     * @throws IOException
     * SaveModPartBut saves changes to out sourced part
     */
    public void SaveModPartBut(MouseEvent mouseEvent) throws IOException, SQLException {

       /* selectedPart.setId(Integer.valueOf(idTB.getText()));
        selectedPart.setMax((Integer.valueOf(maxTB.getText())));
        selectedPart.setMin(Integer.valueOf(minTB.getText()));
        selectedPart.setPrice(Double.valueOf(priceTB.getText()));
        selectedPart.setStock(Integer.valueOf(inventoryTB.getText()));
        selectedPart.setName(nameTB.getText());
        selectedPart.setCompanyName(companyNameTB.getText());*/

        try {
            Statement sqlStatement = LogInController.returnConnection().createStatement();
            String sqlCommand = "UPDATE WJ07LG4.outSourced_parts SET" +
                    " part_id=" + idTB.getText() + "," +
                    " partName = '" + nameTB.getText() + "'," +
                    " inventory = " + inventoryTB.getText() + "," +
                    " cost = " + priceTB.getText() + "," +
                    "max = " + maxTB.getText() + "," +
                    "min =" + minTB.getText() + "," +
                    "company_name ='" + companyNameTB.getText() + "'" +
                    " WHERE part_id = " + idTB.getText() + ";";
            sqlStatement.execute(sqlCommand);
            SceneLoader.loadInventoryMainScreen();
        }
        catch (SQLSyntaxErrorException e) {
            warningLabel.setText("Make sure each box has a proper value");
        }

    }

    /**
     *
     * @param mouseEvent cancel
     * @throws Exception
     * CancelModPartBut cancels changes
     */
    public void CancelModPartBut(MouseEvent mouseEvent) throws Exception {

        SceneLoader.loadInventoryMainScreen();
    }
}
