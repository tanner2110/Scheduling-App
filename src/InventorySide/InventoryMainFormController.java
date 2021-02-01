package InventorySide;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
 * MainFormController has a lot on it, it directs you to all the other pages and displays all of the inventory
 */
public class InventoryMainFormController implements Initializable {



    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partId;
    @FXML
    private TableColumn<Part, String> partName;   // parts table columns
    @FXML
    private TableColumn<Part, Integer> inventory;
    @FXML
    private TableColumn<Part, Double> price;


    ///////////////////////


    @FXML
    private TableView<Car> productTable;
    @FXML
    private TableColumn<Car, Integer> productIdCol;
    @FXML
    private TableColumn<Car, String> productNameCol;   //product table columns
    @FXML
    private TableColumn<Car, Integer> inventoryCol;
    @FXML
    private TableColumn<Car, Double> priceCol;

    @FXML
    private  TextField productSearchBox;
    @FXML
    private  TextField partSearchBox;

    @FXML
    private Label warningLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Inventory.clearPartList();
        Inventory.clearCarList();

        try {


            Statement sqlStatement = LogInController.returnConnection().createStatement();
            Statement sqlStatement3 = LogInController.returnConnection().createStatement();
            String sqlCommand = "SELECT * FROM WJ07LG4.inHouse_parts;";
            ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);
            String sqlCommand3 = "SELECT * FROM WJ07LG4.outSourced_parts;";
            ResultSet sqlResult3 = sqlStatement3.executeQuery(sqlCommand3);

            while (sqlResult.next()) {

                InHouse part = new InHouse(sqlResult.getInt("part_id"), sqlResult.getString("partName"), sqlResult.getInt("cost"),
                        sqlResult.getInt("inventory"), sqlResult.getInt("min"), sqlResult.getInt("max"), sqlResult.getString("batch_number"));
                Inventory.addPart(part);

            }

            while (sqlResult3.next()) {

                OutSourced part = new OutSourced(sqlResult3.getInt("part_id"), sqlResult3.getString("partName"), sqlResult3.getInt("cost"),
                        sqlResult3.getInt("inventory"), sqlResult3.getInt("min"), sqlResult3.getInt("max"), sqlResult3.getString("company_name"));
                Inventory.addPart(part);

            }

            Statement sqlStatement4 = LogInController.returnConnection().createStatement();
            String sqlCommand4 = "SELECT * FROM WJ07LG4.Cars;";                       //add cars to visible list
            ResultSet sqlResult4 = sqlStatement4.executeQuery(sqlCommand4);
            while (sqlResult4.next()) {

                Car product = new Car(sqlResult4.getInt("car_id"), sqlResult4.getString("car_name"), sqlResult4.getInt("car_price"),
                        sqlResult4.getInt("car_inventory"), 0, 100);
                Inventory.addCar(product);

            }


        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }

        productTable.setItems(Inventory.getAllCars());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


        partsTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory.setCellValueFactory(new PropertyValueFactory<>("stock"));


    }

/**
 * A feature I would implement in the future would most likely be a recycling bin that would let you restore parts or products
 * that have been deleted. It would let you look back at past parts and products and also restore them in needed.
 */


    /**
     * @param mouseEvent modify part button, loads modify screen
     * @throws Exception
     */
    public void ModPartButClick(MouseEvent mouseEvent) throws Exception {


        if (partsTable.getSelectionModel().getSelectedItem() != null){
            Inventory.storeUpdatingPart(partsTable.getSelectionModel().getSelectedItem()); //stores selected object can retrieve with other method

            if (partsTable.getSelectionModel().getSelectedItem().checkClass().equals("InHouse")) {  //if else checks for in house or out sourced class
                SceneLoader.loadinHousePartModify();
            }

            else {
                SceneLoader.loadOutSourcedPartModify();
                }


    }

    }


    /**
     *
     * @param mouseEvent exit button
     */
    public void appointmentMainButClicked(MouseEvent mouseEvent) throws IOException {

        Utility.SceneLoader.loadMainPage();
    }



    /**
     *
     * @param mouseEvent delete product button
     */
    public void DeleteProdBut(MouseEvent mouseEvent) throws IOException, SQLException {

        Statement sqlStatement4 = LogInController.returnConnection().createStatement();
        String sqlCommand4 = "Select * from appointments where customer_car_id = "+ productTable.getSelectionModel().getSelectedItem().getId() ;
        ResultSet sqlResult4 = sqlStatement4.executeQuery(sqlCommand4);

            if(!sqlResult4.next()) {
                ConfirmWindowController.showConfirmBoxProduct(productTable.getSelectionModel().getSelectedItem());
            }
            else {
                warningLabel.setText("Must delete all appointments with this car before you can delete it.");
            }
    }
    /**
     *
     * @param mouseEvent modify product button
     */
    public void ModProdBut(MouseEvent mouseEvent) throws Exception {

        if (productTable.getSelectionModel().getSelectedItem() != null) {
           Inventory.storeUpdatingCar(productTable.getSelectionModel().getSelectedItem());
            SceneLoader.loadModifyCar();
        }
    }



    /**
     *
     * @param mouseEvent add product button
     */
    public void AddProdBut(MouseEvent mouseEvent) throws Exception {

    SceneLoader.loadAddCar();
    }


    /**
     *
     * @param mouseEvent delete part button
     */
    public void DeletePartBut(MouseEvent mouseEvent) throws IOException {

        ConfirmWindowController.showConfirmBoxPart(partsTable.getSelectionModel().getSelectedItem());


    }



    /**
     *
     * @param mouseEvent add part button
     */
    public void AddPartBut(MouseEvent mouseEvent) throws Exception{

       SceneLoader.loadinHousePart();
    }







///////start search methods

    /**
     *
     * @param keyEvent
     * SearchPartTextBox method is the first of a series of methods that do the search box function. It is activated
     * once a key is pressed. It calls a total of three other methods that take the part list, break it into individual parts
     * takes the name and id of those parts, breaks those properties into chunks that are the same size of the search box text
     * and compares them to see if they are the same. Once a match is found, it is added to a temporary list that is eventually displayed.
     * The following 4 methods are pretty similar throughout the rest of the classes that require a search function. There are small
     * tweaks for different objects.
     */
    public void SearchPartTextBox(KeyEvent keyEvent) {
        showPartSearchList(); //starts part search and display in table with 3 below methods that do the job on key press

    }
  /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * showPartSearchList checks the search box for text, sends on to next method if has text
     */
  public void showPartSearchList(){

        if (partSearchBox.getText().equals("") || partSearchBox.getText() == null){  //checks if search box is empty
            partsTable.setItems(Inventory.getAllParts());
        }
        else {
                Inventory.clearTempPartList();
            for (Part part : listOfMatchedParts(Inventory.getAllParts())) {
                Inventory.addTempPart(part);}
                                                                        //called if search box not empty
            partsTable.setItems(Inventory.getAllTempParts());
        }
    }
///////////////////////////////////////////////////////////////////////////////////

    /**
     *
      * @param list of matched parts
     * @return
     * listOfMatchedParts breaks down list of parts and sends on to next method to see if property matches search box, if does
     * it adds to a temp list
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
     * I had a runtime error in this method. Whenever I was typing in the search box to look for parts, whenever i always typed more than
     * four characters, I would get an array out of bounds error. This was because the array parts in the below method would be the size of the name of the current
     * part that is being tested to see if it is a match. But once I typed a string longer then the length of the first part name that is tested, the out of bounds
     * error would occur. The first part name was bolt and would always go out of bounds on the 5th key press. I corrected it by putting an if statement right above the spot the
     * out of bounds check would occur.
     * @param typedText
     * @param comparedPart
     * @return
     * partTextCompare breaks down the part properties and compares them to the search box text
     */
 public boolean partTextCompare(String typedText, Part comparedPart){  //each part is sent here along with search text to see if the name matches.
     //start of name search
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

     //start of id search
     String[] idSplit = Integer.toString(comparedPart.getId()).split("(?!^)");
     splitPart = "";
     for(int s = 0; s < textSize; s++){  //adds parts of split text into partial word to match for typed text
         if(idSplit.length == s) break; //done to keep array parts from going out of bounds of key types longer than part name
         splitPart = splitPart.concat(idSplit[s]);
     }
     if(splitPart.equals(text)) return true;  //checks to see if typed text is same as part id text
     //end id search

     else return false;
 }
 //end search for part;

 ////////////////////////////////////////////////////////////////////////////

    // start of product search methods

    /**
     *
     * @param keyEvent
     * SearchProdTextBox starts product search
     */
    public void SearchProdTextBox(KeyEvent keyEvent) {

     showProductSearchList();

    }

    /////////////////////////////////////////////////////////////

    /**
     * showProductSearchList checks product search box
     */
    public void showProductSearchList(){

        if (productSearchBox.getText().equals("") || productSearchBox.getText() == null){  //checks if search box is empty
            productTable.setItems(Inventory.getAllCars());
        }
        else {
            Inventory.clearTempProdList();
            for (Car product : listOfMatchedProducts(Inventory.getAllCars())) { //called if search box not empty
                Inventory.addTempCar(product);}
            productTable.setItems(Inventory.getAllTempCars());
        }
    }


    ///////////////////////////////////////////////////

    /**
     *
     * @param list of matched products
     * @return
     * listOfMatchedProducts breaks down product list and sends to compare
     */
    public ObservableList<Car> listOfMatchedProducts (ObservableList<Car> list) {

        ObservableList<Car> matchedProducts = FXCollections.observableArrayList(); //another temporary list to hold searched products
        for (Car product : list) {
            if(productTextCompare(productSearchBox.getText(),  product)){  //this line checks if part and typed text are the same and adds part in next line if are, sends to method below to check
                matchedProducts.add(product);
            }

        }

        return matchedProducts;
    }

    ///////////////////////////////////////////////

    /**
     *
     * @param typedText search box
     * @param comparedProduct product comapring to text
     * @return
     * productTextCompare compares product properties with search text
     */
    public static boolean productTextCompare(String typedText, Car comparedProduct){

        //start of name search
        String[] nameSplit = comparedProduct.getName().split("(?!^)");
        String text = typedText;
        String splitProduct = "";
        int textSize = typedText.length();
        for(int s = 0; s < textSize; s++){
            //RIGHT HERE, is where I put in the code to stop the method from exceeding array and return false, because if it is longer than the name, it is not a match anyways.
            if(nameSplit.length == s) break; //done to keep array parts from going out of bounds of key types longer than part name
            splitProduct = splitProduct.concat(nameSplit[s]);//creates a string of same length as search box text to see if matches part name
        }
        if(splitProduct.toLowerCase().equals(text)) return true;  //checks to see if typed text is same as part name text

        //start of id search
        String[] idSplit = Integer.toString(comparedProduct.getId()).split("(?!^)");
        splitProduct = "";
        for(int s = 0; s < textSize; s++){  //adds parts of split text into partial word to match for typed text
            if(idSplit.length == s) break; //done to keep array parts from going out of bounds of key types longer than part name
            splitProduct = splitProduct.concat(idSplit[s]);
        }
        if(splitProduct.equals(text)) return true;  //checks to see if typed text is same as part id text
            // end id search

        else return false;

    }

}





