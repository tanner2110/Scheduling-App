package CustomerAppointmentSide;

import MainClass.Main;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import Utility.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 12/9/2020
 *
 */

public class CustomerController implements Initializable {

    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private ComboBox<String> divisionComboBox;
    @FXML
    private TextField customerNameTB;
    @FXML
    private TextField addressTB;
    @FXML
    private TextField zipTB;
    @FXML
    private TextField phoneTB;
    @FXML
    private TextField customerIdTB;
    @FXML
    private Button secondAddCustomerNewButton;
    @FXML
    private Button saveCustomerChangesButton;
    @FXML
    private Button addnewCustomerButton;
    @FXML
    private Button cancelAddNewCustomer;

    @FXML
    private Label confirmationLabel;
    @FXML
    private Label errorLabel;



    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> cIdCol;
    @FXML
    private TableColumn<Customer, String> cNameCol;
    @FXML
    private TableColumn<Customer, String>  cAddressCol;
    @FXML
    private TableColumn<Customer, String> cZipCol  ;
    @FXML
    private TableColumn<Customer, String> cPhoneCol ;
    @FXML
    private TableColumn<Customer, String> cDivisionCol ;



    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> appIdCol ;
    @FXML
    private TableColumn <Appointment, String> appTitleCol ;
    @FXML
    private TableColumn<Appointment, String> appDescriptionCol ;
    @FXML
    private TableColumn <Appointment, String> appLocationCol;
    @FXML
    private TableColumn<Appointment, String> appContactCol  ;
    @FXML
    private TableColumn<Appointment, String> appTypeCol  ;
    @FXML
    private TableColumn<Appointment, String> appStartCol ;
    @FXML
    private TableColumn <Appointment, String> appEndCol;
    @FXML
    private TableColumn <Appointment, Integer> appCustIdCol ;


    /**
     * customerId is used to store customer id to pass along when needed
     */
    private static int customerId;

    /**
     * appointment is used to store an appointment object and pass to modify appointment form
     */
    private static Appointment appointment;



    private String sqlCommand5;


    /**
     * customer list and appointment list are used to display customers and appointments in table views.
     */
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    secondAddCustomerNewButton.setVisible(false);
    cancelAddNewCustomer.setVisible(false);     //hides two buttons for adding customers

        try {
            Statement sqlStatement = LogInController.returnConnection().createStatement();
            /////////////////////////////////////////////////////////////////////////start block
            String sqlCommand = "SELECT Country FROM WJ07LG4.countries;";
            ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);           //this  block adds the contacts names to combo box from sql db
            while (sqlResult.next()) {
                countryComboBox.getItems().addAll(sqlResult.getString("Country"));
            }/////////////////////////////////////////////////////////////////end block
        }
        catch (SQLException throwable) {
            throwable.printStackTrace();

        }



        cIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        cNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));  //customer cells initialized
        cZipCol.setCellValueFactory(new PropertyValueFactory<>("customerZipCode"));
        cPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        cDivisionCol.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));



        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));        //appointment cells initialized
        appContactCol.setCellValueFactory(new PropertyValueFactory<>("carId"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartTime"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndTime"));        //appointment cells initialized
        appCustIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerId"));





        try {
            Statement sqlStatement2 = LogInController.returnConnection().createStatement();
            String sqlCommand2 = "SELECT * FROM WJ07LG4.customers;";
            ResultSet sqlResult2 = sqlStatement2.executeQuery(sqlCommand2);
            Statement sqlStatement3 = LogInController.returnConnection().createStatement();

            while (sqlResult2.next()) {

                String sqlCommand3 = "SELECT Division FROM WJ07LG4.first_level_divisions WHERE Division_ID =" + sqlResult2.getInt("Division_ID") + " ;";
                ResultSet sqlResult3 = sqlStatement3.executeQuery(sqlCommand3); //sql command 3 gets the division name from the division number in customer.
                sqlResult3.next();
                Customer customer = new Customer(
                        sqlResult2.getInt("Customer_ID"),
                        sqlResult2.getString("Customer_Name"),
                        sqlResult2.getString("Address"),
                        sqlResult2.getString("Postal_Code"),
                        sqlResult2.getString("Phone"),
                        sqlResult3.getString("Division"));


                        customerList.add(customer);//adds customer object to list

            }/////////////////////////////////////////////////////////////////end block
        }
        catch (SQLException throwable) {
            throwable.printStackTrace();

        }

        customerTable.setItems(customerList); //adds customers to tableview



    }//end of initialize


    /**
     *
     * @return ccustomer id where needed to fill forms
     */
    public static int returnCustomerId(){
        return customerId;

    }


    /**
     *
     * @return gets the appointment object when needed to fill modify form
     */
    public static Appointment returnAppointment() {

        return appointment;
    }


    /**
     * laods main page when hit
     * @param mouseEvent
     * @throws IOException
     */
    public void cancelButtonClicked(MouseEvent mouseEvent) throws IOException {

        SceneLoader.loadMainPage();
    }


    /**
     * addAppointmentButtonClicked loads add a new appointment page
     * @param mouseEvent
     * @throws IOException
     */
    public void addAppointmentButtonClicked(MouseEvent mouseEvent) throws IOException {



            if (customerIdTB.getText().equals("") ){
                confirmationLabel.setText("Must select a customer before adding an appointment.");
            }
            else {
                customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
                SceneLoader.loadAppointmentPage();}

    }


    /**
     * saveCustomerChangesButClicked saves changes made to an existing customer
     * @param mouseEvent
     */
    public void saveCustomerChangesButClicked(MouseEvent mouseEvent) throws SQLException {

        errorLabel.setText("");

        if (divisionComboBox.getValue() != null && countryComboBox != null && !phoneTB.getText().equals("") && !zipTB.getText().equals("") && !addressTB.getText().equals("") && !customerNameTB.getText().equals("")) {
            try {

                Statement sqlStatement = LogInController.returnConnection().createStatement();
                Statement sqlStatement2 = LogInController.returnConnection().createStatement();
                String sqlCommand2 = "SELECT DIVISION_ID FROM `WJ07LG4`.`first_level_divisions` WHERE DIVISION = '" + divisionComboBox.getValue() + "';";
                ResultSet sqlResult = sqlStatement2.executeQuery(sqlCommand2);
                sqlResult.next();

                String sqlCommand = "UPDATE `WJ07LG4`.`customers` SET" +
                        " Customer_Name='" + customerNameTB.getText() + "'," +
                        " Address = '" + addressTB.getText() + "'," +
                        " Postal_Code = '" + zipTB.getText() + "'," +
                        "Phone = '" + phoneTB.getText() + "'," +
                        "Last_Update =CURRENT_TIMESTAMP," +
                        "Last_Updated_By ='" + LogInController.returnUserName() + "' ," +
                        "Division_ID =" + sqlResult.getString("Division_ID") +
                        " WHERE Customer_ID = " + Integer.valueOf(customerIdTB.getText()) + ";";

                sqlStatement.execute(sqlCommand);
            } catch (MySQLIntegrityConstraintViolationException e) {
                e.printStackTrace();
            }
            ////////
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            customer.setCustomerAddress(addressTB.getText());
            customer.setCustomerName(customerNameTB.getText());
            customer.setCustomerDivision(divisionComboBox.getValue());       //this block changes the customers values in the object list
            customer.setCustomerPhone(phoneTB.getText());                               //that is used for displaying the customers.
            customer.setCustomerZipCode(zipTB.getText());
            customerTable.refresh();
            /////////


            confirmationLabel.setText("Your changes have been saved.");
            //////////////////////////////////
        }//end addustomersave method
        else {errorLabel.setText("Make sure each field has a proper value");}
    }


    /**
     * addNewCustomerButClicked clears the modify customer text boxes and adds a customer id and allows adding a new customer
     * @param mouseEvent
     * @throws SQLException
     */
    public void addNewCustomerButClicked(MouseEvent mouseEvent) throws SQLException {
        confirmationLabel.setText("");
        secondAddCustomerNewButton.setVisible(true);
        saveCustomerChangesButton.setVisible(false);
        addnewCustomerButton.setVisible(false);
        cancelAddNewCustomer.setVisible(true);
        customerIdTB.clear();
        phoneTB.clear();
        zipTB.clear();
        customerNameTB.clear();
        addressTB.clear();


        /////////////////////////////////////////////////
        Statement sqlStatement2 = LogInController.returnConnection().createStatement();
        String sqlCommand2 = "select Customer_ID FROM `WJ07LG4`.`customers` order by Customer_ID desc;";
        ResultSet sqlResult2 = sqlStatement2.executeQuery(sqlCommand2);
        sqlResult2.next();
        //////////////////// this block sorts appointment_id descending, adds 1 to make a unique appointment id and sets the textbox./////
        customerId = sqlResult2.getInt("Customer_ID");
        customerIdTB.setText(Integer.toString(customerId +1));
        ////////////////////////////////////////////////////////////////////////////
    }


    /**
     * cancelAddNewCustomerButClicked clears fields and replaces buttons
     * @param mouseEvent
     */
    public void cancelAddNewCustomerButClicked(MouseEvent mouseEvent) {
        saveCustomerChangesButton.setVisible(true);
        addnewCustomerButton.setVisible(true);
        secondAddCustomerNewButton.setVisible(false);
        cancelAddNewCustomer.setVisible(false);
        customerIdTB.clear();
        phoneTB.clear();
        zipTB.clear();
        customerNameTB.clear();
        addressTB.clear();

    }


    /**
     * saveAddNewCustomerButClicked takes data from fields and adds it to the sql database on button click
     * @param mouseEvent
     * @throws SQLException
     */
    public void saveAddNewCustomerButClicked(MouseEvent mouseEvent) throws SQLException {

        if (divisionComboBox.getValue() != null && countryComboBox != null && !phoneTB.getText().equals("") && !zipTB.getText().equals("") && !addressTB.getText().equals("") && !customerNameTB.getText().equals("")) {
            saveCustomerChangesButton.setVisible(true);
            addnewCustomerButton.setVisible(true);
            secondAddCustomerNewButton.setVisible(false);
            cancelAddNewCustomer.setVisible(false);

            try {

                Statement sqlStatement = LogInController.returnConnection().createStatement();
                Statement sqlStatement2 = LogInController.returnConnection().createStatement();
                String sqlCommand2 = "SELECT DIVISION_ID FROM `WJ07LG4`.`first_level_divisions` WHERE DIVISION = '" + divisionComboBox.getValue() + "';";
                ResultSet sqlResult = sqlStatement2.executeQuery(sqlCommand2);
                sqlResult.next();

                String sqlCommand = "INSERT INTO `WJ07LG4`.`customers`" +
                        "(`Customer_ID`," +
                        "`Customer_Name`," +
                        "`Address`," +
                        "`Postal_Code`," +
                        "`Phone`," +
                        "`Create_Date`," +
                        "`Created_By`," +
                        "`Last_Update`," +
                        "`Last_Updated_By`," +
                        "`Division_ID`)" +
                        "VALUES" +
                        "(" + Integer.valueOf(customerIdTB.getText()) + ",'" +
                        customerNameTB.getText() + "','" +
                        addressTB.getText() + "','" +
                        zipTB.getText() + "','" +
                        phoneTB.getText() + "'," +
                        "CURRENT_TIMESTAMP," +
                        "'" + LogInController.returnUserName() + "'" +
                        "," + "CURRENT_TIMESTAMP," +
                        "'" + LogInController.returnUserName() + "', " +
                        sqlResult.getString("Division_ID") + ");";


                sqlStatement.execute(sqlCommand);

                confirmationLabel.setText(customerNameTB.getText() + " has been added successfully! ");

                sqlStatement.close();
                sqlStatement2.close();
            } catch (MySQLIntegrityConstraintViolationException e) {
              e.printStackTrace();

            }


            Customer customer = new Customer(Integer.parseInt(customerIdTB.getText()), customerNameTB.getText(), addressTB.getText(),
                    zipTB.getText(), phoneTB.getText(), divisionComboBox.getValue().toString());
            customerList.add(customer);

            customerIdTB.clear();
            phoneTB.clear();
            zipTB.clear();
            customerNameTB.clear();
            addressTB.clear();
            errorLabel.setText("");
        } else {
            errorLabel.setText("Make sure each field has a value!");
            //////////////////////////////////
        }//end addnewcustomersave method
    }

    /**
     * divisionFilterCountrySelected sorts the divisions and places them in a combo box depending on what country is selected.
     * @param mouseEvent
     */
    public void divisionFilterCountrySelected(MouseEvent mouseEvent) {

        if(countryComboBox.getSelectionModel().getSelectedItem() != null) {
            try {
                int countryID;
                Statement sqlStatement = LogInController.returnConnection().createStatement();
                Statement sqlStatement2 = LogInController.returnConnection().createStatement();
                /////////////////////////////////////////////////////////////////////////start block
                String sqlCommand2 = " SELECT Country_ID FROM WJ07LG4.countries WHERE Country ='" + countryComboBox.getSelectionModel().getSelectedItem() +"';";
                ResultSet sqlResult2 = sqlStatement2.executeQuery(sqlCommand2);
                sqlResult2.next();
                countryID = sqlResult2.getInt("Country_ID");
                String sqlCommand = "SELECT Division FROM WJ07LG4.first_level_divisions WHERE COUNTRY_ID =" + countryID + ";";
                ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);
                divisionComboBox.getItems().clear();
                  //this  block adds the division names to combo box from sql db
                while (sqlResult.next()) {
                    divisionComboBox.getItems().addAll(sqlResult.getString("Division"));
                }/////////////////////////////////////////////////////////////////end block
                sqlStatement.close();
                sqlStatement2.close();
            }
            catch (SQLException throwable) {
                throwable.printStackTrace();



            }
        }
    }

    /**
     * countryComboBoxClicked clears the division box when the country box is clicked to make way for new divisions.
     * @param mouseEvent
     */
    public void countryComboBoxClicked(MouseEvent mouseEvent) {
        divisionComboBox.getItems().clear();
    } //required to keep division box from repeat filling

    public void deleteCustomer(MouseEvent mouseEvent) throws SQLException {

        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            if (appointmentList.size() < 1) { //checks if customer has appointments
                confirmationLabel.setText(customerTable.getSelectionModel().getSelectedItem().getCustomerName() + " has been deleted.");
                Statement sqlStatement = LogInController.returnConnection().createStatement();
                String sqlCommand = "delete from WJ07LG4.customers where customer_id =" + customerTable.getSelectionModel().getSelectedItem().getCustomerId() + ";";
                sqlStatement.execute(sqlCommand);
                customerList.remove(customerTable.getSelectionModel().getSelectedItem());
                sqlStatement.close();
                customerIdTB.clear();
                phoneTB.clear();
                zipTB.clear();
                customerNameTB.clear();
                addressTB.clear();
            } else {
                confirmationLabel.setText("Customer appointments must be deleted before customer deletion.");
            }
        }
        else {confirmationLabel.setText("Must first select a customer to delete.");}

    }

    /**
     *
     * @param mouseEvent deletes customer appointment on press
     * @throws SQLException
     */
    public void deleteAppointment(MouseEvent mouseEvent) throws SQLException {

        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
            confirmationLabel.setText(appointment.getAppointmentType() + " appointment of ID " + appointment.getAppointmentId() + " has been deleted.");
            Statement sqlStatement = LogInController.returnConnection().createStatement();
            String sqlCommand = "delete from WJ07LG4.appointments where appointment_id =" + appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId() + ";";
            sqlStatement.execute(sqlCommand);
            sqlCommand = "delete from `WJ07LG4`.`appointment_reserved_parts` where appointment_id =" + appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId() + ";";
            sqlStatement.execute(sqlCommand);
            appointmentList.remove(appointment);
        }
        else {
            confirmationLabel.setText("Must select an appointment first.");
        }

    }


    /**
     * customerRowClicked sets the customer textboxes with the info from customer objects which are made from database information.
     * @param mouseEvent
     * @throws SQLException
     */
    public void customerRowClicked(MouseEvent mouseEvent) throws SQLException {

        confirmationLabel.setText("");
        secondAddCustomerNewButton.setVisible(false);
        saveCustomerChangesButton.setVisible(true);
        addnewCustomerButton.setVisible(true);
        cancelAddNewCustomer.setVisible(false);

        if (customerTable.getSelectionModel().getSelectedItem() != null) {

            appointmentTable.getItems().clear();
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            customerIdTB.setText(String.valueOf(customer.getCustomerId()));
            customerNameTB.setText(customer.getCustomerName());        //sets text boxes to data from customer objects
            addressTB.setText(customer.getCustomerAddress());
            zipTB.setText(customer.getCustomerZipCode());
            phoneTB.setText(customer.getCustomerPhone());
            divisionComboBox.setValue(customer.getCustomerDivision());
            Statement sqlStatement = LogInController.returnConnection().createStatement();
            Statement sqlStatement2 = LogInController.returnConnection().createStatement();
            String sqlCommand = "select country_id from WJ07LG4.first_level_divisions where division = '" + divisionComboBox.getValue() + "' ;";
            ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);       //these two sql commands are needed to get country name to put inside of
            sqlResult.next();                                                  //country combo box when a customer is selected
            String sqlCommand2 = "select country from WJ07LG4.countries where country_id= " + sqlResult.getInt("Country_ID") + ";";
            ResultSet sqlResult2 = sqlStatement2.executeQuery(sqlCommand2);
            sqlResult2.next();
            countryComboBox.setValue(sqlResult2.getString("Country"));


            ////////////////start appointment grab code when customer clicked on and displayed

            try {

                String sqlCommand4 = "SELECT * FROM WJ07LG4.appointments WHERE customer_id = " + Integer.parseInt(customerIdTB.getText()) + " ;";
                ResultSet sqlResult4 = sqlStatement.executeQuery(sqlCommand4);
                //sqlResult4.next();

                while (sqlResult4.next()) {
                    String startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlResult4.getTimestamp("Start"));
                    startDate = TimeUtilClass.convertTimeToLocal(startDate).toLocalDate().toString() + "  " + TimeUtilClass.convertTimeToLocal(startDate).toLocalTime().toString(); //converts db time to local time
                    String endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlResult4.getTimestamp("End"));
                    endDate = TimeUtilClass.convertTimeToLocal(endDate).toLocalDate().toString() + "  " + TimeUtilClass.convertTimeToLocal(endDate).toLocalTime().toString();
                    sqlCommand5 = "SELECT car_name FROM WJ07LG4.Cars WHERE car_id = " + sqlResult4.getInt("customer_car_id") + " ;";
                    ResultSet sqlResult5 = sqlStatement2.executeQuery(sqlCommand5);
                    sqlResult5.next();

                    appointment = new Appointment(
                            sqlResult4.getInt("Appointment_id"),
                            sqlResult4.getString("Title"),
                            sqlResult4.getString("Description"),
                            sqlResult4.getString("Garage_Bay"),
                            sqlResult4.getString("Type"),
                            startDate,
                            endDate,
                            sqlResult4.getInt("Customer_ID"),
                            sqlResult5.getString("car_name"));

                    appointmentList.add(appointment);//adds appointment object to list
                    appointmentTable.setItems(appointmentList); //displays list of appointments for selected customer

                }/////////////////////////////////////////////////////////////////end block
            } catch (SQLException throwable) {
                throwable.printStackTrace();

            }


        }
/////////////
    }


    /**
     *
     * @param mouseEvent loads the modify appointment page
     * @throws IOException
     */
    public void modifyAppointmentButtonClicked(MouseEvent mouseEvent) throws IOException {

        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            appointment = appointmentTable.getSelectionModel().getSelectedItem();
            SceneLoader.loadAppointmentModifyPage();
        }
        else {
            confirmationLabel.setText("Must select an appointment first.");
        }

    }
}
