package InventorySide;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
 *ModifyProductController is the controller of the modify product page
 */
public class ModifyCarController implements Initializable {

    /**
     * selectedProduct is used to store the current product that is being modified
     */
    private static Car selectedProduct;

    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> idCol;
    @FXML
    private TableColumn<Part, String> nameCol;   // parts table columns
    @FXML
    private TableColumn<Part, Integer> inventoryCol;
    @FXML
    private TableColumn<Part, Double> priceCol;

    //////////

    @FXML
    private TableView<Part> asPartTable;
    @FXML
    private TableColumn<Part, Integer> asIdCol;
    @FXML
    private TableColumn<Part, String> asNameCol;   // parts table columns
    @FXML
    private TableColumn<Part, Integer> asInventoryCol;
    @FXML
    private TableColumn<Part, Double> asPriceCol;


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
    private TextField partSearchBox;

    @FXML
    private Label errorLabel;

    public static ObservableList<Car> tempCarList2 = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Inventory.clearPartList();
        selectedProduct = Inventory.getUpdatingCar();
        idTB.setText(Integer.toString(selectedProduct.getId()));
        maxTB.setText(Integer.toString(selectedProduct.getMax()));
        minTB.setText(Integer.toString(selectedProduct.getMin()));
        nameTB.setText(selectedProduct.getName());
        inventoryTB.setText(Integer.toString(selectedProduct.getStock()));
        priceTB.setText(Double.toString(selectedProduct.getPrice()));


        /**
         * adds parts to parts list below
         */
        try {
        Statement sqlStatement = LogInController.returnConnection().createStatement();
        Statement sqlStatement3 = LogInController.returnConnection().createStatement();
        String sqlCommand = "SELECT * FROM WJ07LG4.inHouse_parts;";
        ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);
        String sqlCommand3 = "SELECT * FROM WJ07LG4.outSourced_parts;";
        ResultSet sqlResult3 = sqlStatement3.executeQuery(sqlCommand3);

        while (sqlResult.next()) {

            InHouse part = new InHouse(sqlResult.getInt("part_id"), sqlResult.getString("partName"), sqlResult.getInt("inventory"),
                    sqlResult.getInt("cost"), sqlResult.getInt("max"), sqlResult.getInt("min"), sqlResult.getString("batch_number"));
            Inventory.addPart(part);

        }

        while (sqlResult3.next()) {

            OutSourced part = new OutSourced(sqlResult3.getInt("part_id"), sqlResult3.getString("partName"), sqlResult3.getInt("inventory"),
                    sqlResult3.getInt("cost"), sqlResult3.getInt("max"), sqlResult3.getInt("min"), sqlResult3.getString("company_name"));
            Inventory.addPart(part);

        }

    }
    catch(SQLException throwables){
        throwables.printStackTrace();
    }


        /**
         * adds associated parts to associated parts list below.
         */
        try {
            String sqlCommand5 = "select * from outSourced_parts where part_id IN (" +
                    "select part_id from associated_outSourced_car_parts where car_id = "+idTB.getText()+");";
            ResultSet sqlResult5;
            String sqlCommand6 = "select * from inHouse_parts where part_id IN (" +
                    "select part_id from associated_inHouse_car_parts where car_id = "+idTB.getText()+");";

            ResultSet sqlResult6;
            Statement sqlStatement5 = LogInController.returnConnection().createStatement();
            sqlResult5 = sqlStatement5.executeQuery(sqlCommand5);
            Statement sqlStatement6 = LogInController.returnConnection().createStatement();
            sqlResult6 = sqlStatement6.executeQuery(sqlCommand6);

            while (sqlResult5.next()){

                InHouse part = new InHouse(sqlResult5.getInt("part_id"), sqlResult5.getString("partName"), sqlResult5.getInt("inventory"),
                        sqlResult5.getInt("cost"), sqlResult5.getInt("max"), sqlResult5.getInt("min"), sqlResult5.getString("company_name"));
                Inventory.addTempPart(part);

            }
            while (sqlResult6.next()){

                InHouse part = new InHouse(sqlResult6.getInt("part_id"), sqlResult6.getString("partName"), sqlResult6.getInt("inventory"),
                        sqlResult6.getInt("cost"), sqlResult6.getInt("max"), sqlResult6.getInt("min"), sqlResult6.getString("batch_number"));
                Inventory.addTempPart(part);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        partTable.setItems(Inventory.getAllParts());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));



        //for (Part part : selectedProduct.getAllAssociatedParts()) {   // for loop to add associated parts to a temp list to prevent deletion from actual list
            //Inventory.addTempPart(part);}
        asPartTable.setItems(Inventory.getAllTempParts());
        asIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asInventoryCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        asPriceCol.setCellValueFactory(new PropertyValueFactory<>("stock"));






    }

    /**
     *
     * @param mouseEvent save prod
     * @throws IOException
     * ModProdSaveBut saves changes to product
     */
    public void ModProdSaveBut(MouseEvent mouseEvent) throws IOException, SQLException {


        try {
            if (Integer.parseInt(minTB.getText()) < Integer.parseInt(maxTB.getText())) {
                selectedProduct.clearAssociatedParts();
                selectedProduct.addAssociatedParts(Inventory.getAllTempParts());
                selectedProduct.setId(Integer.valueOf(idTB.getText()));
                selectedProduct.setMax((Integer.valueOf(maxTB.getText())));
                selectedProduct.setMin(Integer.valueOf(minTB.getText()));
                selectedProduct.setPrice(Double.valueOf(priceTB.getText()));
                selectedProduct.setStock(Integer.valueOf(inventoryTB.getText()));
                selectedProduct.setName(nameTB.getText());



                ////////////////deleting associated parts to put redo list with any changes
                Statement sqlStatement = LogInController.returnConnection().createStatement();
                String sqlCommand = "DELETE FROM `WJ07LG4`.`associated_inHouse_car_parts` WHERE car_id = "+idTB.getText()+";";
                sqlStatement.execute(sqlCommand);
                sqlCommand =  "DELETE FROM `WJ07LG4`.`associated_outSourced_car_parts` WHERE car_id = "+idTB.getText()+";";
                sqlStatement.execute((sqlCommand));

                Statement sqlStatement4 = LogInController.returnConnection().createStatement();

                Statement sqlStatement2 = LogInController.returnConnection().createStatement();
////////////////////////////


                ////////////////////////////////////////////////////////////////
                for( Part part: Inventory.getAllTempParts()){

                    if(part.checkClass().equals("InHouse")){

                        String sqlCommand4 = "INSERT INTO `WJ07LG4`.`associated_inHouse_car_parts` (car_id, part_id) VALUES" +
                                "(" + idTB.getText() +"," + part.getId() + " );";               //sql insert of inHouse part to database
                        sqlStatement4.execute(sqlCommand4);

                    }                                                   //adding associated parts list back into database

                    if(part.checkClass().equals(("OutSourced"))){

                        String sqlCommand2 = "INSERT INTO `WJ07LG4`.`associated_outSourced_car_parts` (car_id, part_id) VALUES" +
                                "(" + idTB.getText() +"," + part.getId() + " );";

                        sqlStatement2.execute(sqlCommand2);

                    }

                }



                SceneLoader.loadInventoryMainScreen();
                Inventory.clearTempPartList();
            } else {
                errorLabel.setText("Max must be greater than min.");
            }
        } catch (NumberFormatException | IOException e){
                errorLabel.setText("Enter proper values in each box.");
        }


///////////////////////////////////////////////////////////////////////////


    }

    /**
     *
     * @param mouseEvent cancel mod prod
     * @throws Exception
     * ModProdCancelBut cancels changes to product, clears temp associated parts list
     */
    public void ModProdCancelBut(MouseEvent mouseEvent) throws Exception {

        SceneLoader.loadInventoryMainScreen();
        Inventory.clearTempPartList();
    }

    /**
     *
     * @param mouseEvent remove associated part
     * @throws IOException
     * ModProdRemoveAssocPartBut removes associated part
     */
    public void ModProdRemoveAssocPartBut(MouseEvent mouseEvent) throws IOException {

        ConfirmWindowController.showConfirmBoxPart2(asPartTable.getSelectionModel().getSelectedItem());
    }

    /**
     *
     * @param mouseEvent add prod
     * ModProdAddBut adds to associated part list
     */
    public void ModProdAddBut(MouseEvent mouseEvent) {

        if (partTable.getSelectionModel().getSelectedItem() != null) {
            Inventory.addTempPart(partTable.getSelectionModel().getSelectedItem());
        }                                                     //end if

    }


    //start search methods
    /////////////////////////////////////////////////////////////////////////////////////////////
    /**
     *
     * @param keyEvent search box key press activates search methods
     */
    public void partSearch(KeyEvent keyEvent) {
        showPartSearchList();

    }

    /////////////////////////////////////////////////////////////////////
    /**
     * similar method to previous, checks if search box empty or full
     * showPartSearchList similar to previous search methods, starts order of three methods that search the object
     */
    public void showPartSearchList(){

        if (partSearchBox.getText().equals("") || partSearchBox.getText() == null){  //checks if search box is empty
            partTable.setItems(Inventory.getAllParts());
        }
        else {
            Inventory.clearTempPartList2(); //clears list each time key pressed for new results
            for (Part part : listOfMatchedParts(Inventory.getAllParts())) {  //called if search box not empty
                Inventory.addTempPart2(part);}
            partTable.setItems(Inventory.getAllTempParts2());
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * similar to previous, sends each part to method to check if matched
     * @param list of matched parts
     * @return matched parts
     */
    public ObservableList<Part> listOfMatchedParts (ObservableList<Part> list) {

        ObservableList<Part> matchedParts = FXCollections.observableArrayList(); //another temporary list to hold searched parts
        for (Part part : list) {
            if(partTextCompare(partSearchBox.getText(),  part)){  //this line checks if part and typed text are the same and adds part in next line if are, sends to method below to check
                matchedParts.add(part);
            }

        }

        return matchedParts;
    }

    //////////////////////////////////////////////////////////////////////////////////////

    /**
     * similar to previous, compares objects name and id to typed text to see if a match is found.
     * @param typedText search box text
     * @param comparedPart current part comparing
     * @return true if match
     */
    public boolean partTextCompare(String typedText, Part comparedPart){  //each part is sent here along with search text to see if the name matches.
        /////////////start of name search
        String[] nameSplit = comparedPart.getName().split("(?!^)");
        String text = typedText;
        String splitPart = "";
        int textSize = typedText.length();
        for(int s = 0; s < textSize; s++){
            //RIGHT HERE, is where I put in the code to stop the method from exceeding array and return false, because if it is longer than the name, it is not a match anyways.
            if(nameSplit.length == s) break; //done to keep array parts from going out of bounds of key types longer than part name
            splitPart = splitPart.concat(nameSplit[s]);//creates a string of same length as search box text to see if matches part name
        }
        if(splitPart.toLowerCase().equals(text)) return true;  //checks to see if typed text is same as part name text

        //////////////////////start of id search
        String[] idSplit = Integer.toString(comparedPart.getId()).split("(?!^)");
        splitPart = "";
        for(int s = 0; s < textSize; s++){  //adds parts of split text into partial word to match for typed text
            if(idSplit.length == s) break; //done to keep array parts from going out of bounds of key types longer than part name
            splitPart = splitPart.concat(idSplit[s]);
        }
        if(splitPart.equals(text)) return true;  //checks to see if typed text is same as part id text
            ////////////////end id search

        else return false;
    }


    //end search for part;





}
