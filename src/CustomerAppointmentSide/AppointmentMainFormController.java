package CustomerAppointmentSide;

import MainClass.Main;
import Utility.SceneLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import Utility.TimeUtilClass;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 12/9/2020
 *
 */

public class AppointmentMainFormController implements Initializable {


    @FXML
    private Label mainFormLabelName;
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


    @FXML
    private ComboBox monthBox;
    @FXML
    private ComboBox appContactCombo;
    @FXML
    private ComboBox appointmentTypeComboBox;
    @FXML
    private ComboBox monthBox2;
    @FXML
    private Label appReportLabel;
    @FXML
    private Button clearReportBut;
    @FXML
    private Button generateScheduleBut;
    @FXML
    private Button generateReportBut;
    @FXML
    private Button monthSearchBut;
    @FXML
    private Button clearByMonthBut;
    @FXML
    private Button generateAppReportButFirst;
    @FXML
    private Button genScheduleBut;
    @FXML
    private Button viewAppByWeekBut;
    @FXML
    private Button customersButton;
    @FXML
    private ComboBox customerComboBox;
    @FXML
    private ComboBox monthBox3;
    @FXML
    private Button generateCustomerReport;
    @FXML
    private Label reportLabel1;
    @FXML
    private Label reportLabel2;
    @FXML
    private Label contactMonthLabelSchedule;
    @FXML
    private Label contactLabelSchedule;

    private String sqlCommand5;


    @FXML
    private Label upcomingAppLabel;

    /**
     * appointmentList and appointmentList2 are used for storing all appoitments and certain appointments that have been queried.
     */
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointmentList2 = FXCollections.observableArrayList();

    /**
     * months is a map to get the integer values of the month strings.
     */
    private HashMap<String, String> months = new HashMap<String, String>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainFormLabelName.setText("Welcome " + Utility.LogInController.returnUserName());

        appContactCombo.setVisible(false);
        appointmentTypeComboBox.setVisible(false);
        monthBox2.setVisible(false);
        clearReportBut.setVisible(false);
        generateScheduleBut.setVisible(false);
        generateReportBut.setVisible(false);
        customerComboBox.setVisible(false);
        monthBox3.setVisible(false);
        generateCustomerReport.setVisible(false);
        reportLabel1.setVisible(false);
        reportLabel2.setVisible(false);
        contactLabelSchedule.setVisible(false);
        contactMonthLabelSchedule.setVisible(false);



        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));        //appointment cells initialized
        appContactCol.setCellValueFactory(new PropertyValueFactory<>("carId"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartTime"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndTime"));        //appointment cells initialized
        appCustIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerId"));

        monthBox.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");


        months.put("January", "01");
        months.put("February", "02");
        months.put("March", "03");
        months.put("April", "04");
        months.put("May", "05");
        months.put("June", "06");
        months.put("July", "07");
        months.put("August", "08");
        months.put("September", "09");
        months.put("October", "10");
        months.put("November", "11");
        months.put("December", "12");





        try {

            Statement sqlStatement = Utility.LogInController.returnConnection().createStatement();
            Statement sqlStatement2 = Utility.LogInController.returnConnection().createStatement();

            String sqlCommand4 = "SELECT * FROM WJ07LG4.appointments ORDER BY Start ASC;";
            ResultSet sqlResult4 = sqlStatement.executeQuery(sqlCommand4);
            while (sqlResult4.next()) {
                String startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlResult4.getTimestamp("Start"));
                startDate = TimeUtilClass.convertTimeToLocal(startDate).toLocalDate().toString() + "  " + TimeUtilClass.convertTimeToLocal(startDate).toLocalTime().toString();
                String endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlResult4.getTimestamp("End"));
                endDate = TimeUtilClass.convertTimeToLocal(endDate).toLocalDate().toString() + "  " + TimeUtilClass.convertTimeToLocal(endDate).toLocalTime().toString();
                sqlCommand5 = "SELECT car_name FROM WJ07LG4.Cars WHERE car_id = " + sqlResult4.getInt("customer_car_id") + " ;";
                ResultSet sqlResult5 = sqlStatement2.executeQuery(sqlCommand5);
                sqlResult5.next();
                Appointment appointment = new Appointment(                                //fills table with appointments
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

            sqlCommand4 = "SELECT * FROM WJ07LG4.appointments where start between current_time() and date_add(current_time, INTERVAL 15 MINUTE);"; //used for appointment alert with in 15 minutes
            sqlResult4 = sqlStatement.executeQuery(sqlCommand4);
            if (sqlResult4.next()){
                String minute = "";
                String startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlResult4.getTimestamp("Start"));
                ZonedDateTime zDate = TimeUtilClass.convertTimeToLocal(startDate);
                if(zDate.getMinute() == 0){//if statement done because if minute is a zero, it cuts off a zero.
                    minute = "00";
                }else {minute = String.valueOf(zDate.getMinute());}
                upcomingAppLabel.setText("Warning " + Utility.LogInController.returnUserName() + ". Appointment Imminent. Appointment of ID " + sqlResult4.getInt("Appointment_ID") + " at "
                        + zDate.getHour() + ":" + minute +
                        " on " + sqlResult4.getDate("Start") +  " will start soon.");

            }
            else {
                upcomingAppLabel.setText("No appointments within 15 minutes " + Utility.LogInController.returnUserName());
            }
        }
        catch (SQLException throwable) {
            throwable.printStackTrace();

        }






    }

    /**
     * loads customer page
     * @param mouseEvent
     * @throws IOException
     */
    public void customerButtonClicked(MouseEvent mouseEvent) throws IOException {

        SceneLoader.loadCustomerPage();
    }

    /**
     * sorts appoitnents by month
     * @param mouseEvent
     * @throws SQLException
     */
    public void searchMonthCllicked(MouseEvent mouseEvent) throws SQLException {

        appointmentList2.clear();


        try {
            Statement sqlStatement = Utility.LogInController.returnConnection().createStatement();
            Statement sqlStatement2 = Utility.LogInController.returnConnection().createStatement();
            String sqlCommand4 = "SELECT * FROM WJ07LG4.appointments WHERE START LIKE '_____" +months.get(monthBox.getValue())+ "%' ORDER BY Start ASC;"; //month search with sql query
            ResultSet sqlResult4 = sqlStatement.executeQuery(sqlCommand4);
            while (sqlResult4.next()) {
                String startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlResult4.getTimestamp("Start"));
                startDate = TimeUtilClass.convertTimeToLocal(startDate).toLocalDate().toString() + "  " + TimeUtilClass.convertTimeToLocal(startDate).toLocalTime().toString();
                String endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlResult4.getTimestamp("End"));
                endDate = TimeUtilClass.convertTimeToLocal(endDate).toLocalDate().toString() + "  " + TimeUtilClass.convertTimeToLocal(endDate).toLocalTime().toString();
                sqlCommand5 = "SELECT car_name FROM WJ07LG4.Cars WHERE car_id = " + sqlResult4.getInt("customer_car_id") + " ;";
                ResultSet sqlResult5 = sqlStatement2.executeQuery(sqlCommand5);
                sqlResult5.next();
                Appointment appointment = new Appointment(
                        sqlResult4.getInt("Appointment_id"),
                        sqlResult4.getString("Title"),
                        sqlResult4.getString("Description"),
                        sqlResult4.getString("Garage_Bay"),
                        sqlResult4.getString("Type"),
                        startDate,
                        endDate,
                        sqlResult4.getInt("Customer_ID"),
                        sqlResult5.getString("car_name"));

                appointmentList2.add(appointment);//adds appointment object to list

            }/////////////////////////////////////////////////////////////////end block
        }
        catch(SQLException throwable){
            throwable.printStackTrace();

        }

        appointmentTable.setItems(appointmentList2); //displays list of appointments for selected customer
    }

    /**
     * shows appointments during the current week
     * @param mouseEvent
     * @throws SQLException
     */
    public void viewWeekAppButClicked(MouseEvent mouseEvent) throws SQLException {


        appointmentList2.clear();

        try {
            Statement sqlStatement = Utility.LogInController.returnConnection().createStatement();
            Statement sqlStatement2 = Utility.LogInController.returnConnection().createStatement();
            String sqlCommand4 = "SELECT * FROM WJ07LG4.appointments WHERE start >= current_date and start <= date_add(current_date, INTERVAL 7 DAY) ORDER BY START ASC;"; //search week query
            ResultSet sqlResult4 = sqlStatement.executeQuery(sqlCommand4);
            while (sqlResult4.next()) {
                String startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlResult4.getTimestamp("Start"));
                startDate = TimeUtilClass.convertTimeToLocal(startDate).toLocalDate().toString() + "  " + TimeUtilClass.convertTimeToLocal(startDate).toLocalTime().toString();
                String endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlResult4.getTimestamp("End"));
                endDate = TimeUtilClass.convertTimeToLocal(endDate).toLocalDate().toString() + "  " + TimeUtilClass.convertTimeToLocal(endDate).toLocalTime().toString();
                sqlCommand5 = "SELECT car_name FROM WJ07LG4.Cars WHERE car_id = " + sqlResult4.getInt("customer_car_id") + " ;";
                ResultSet sqlResult5 = sqlStatement2.executeQuery(sqlCommand5);
                sqlResult5.next();
                Appointment appointment = new Appointment(
                        sqlResult4.getInt("Appointment_id"),
                        sqlResult4.getString("Title"),
                        sqlResult4.getString("Description"),
                        sqlResult4.getString("Garage_Bay"),
                        sqlResult4.getString("Type"),
                        startDate,
                        endDate,
                        sqlResult4.getInt("Customer_ID"),
                        sqlResult5.getString("car_name"));

                appointmentList2.add(appointment);//adds appointment object to list
                appointmentTable.setItems(appointmentList2); //displays list of appointments for selected customer

            }/////////////////////////////////////////////////////////////////end block
        }
        catch(SQLException throwable){
            throwable.printStackTrace();

        }
        appointmentTable.setItems(appointmentList2);


    }

    /**
     * resets the appointment table
     * @param mouseEvent
     */
    public void clearMonthSearchButClicked(MouseEvent mouseEvent) {
        appointmentTable.setItems(appointmentList);
    }

    /**
     * hides buttons, shows buttons to create a contact schedule
     * @param mouseEvent
     * @throws SQLException
     */
    public void generateScheduleClicked(MouseEvent mouseEvent) throws SQLException {
        appContactCombo.getItems().clear();
        generateScheduleBut.setVisible(false);
        genScheduleBut.setVisible(false);
        monthBox.setVisible(false);
        monthSearchBut.setVisible(false);
        clearByMonthBut.setVisible(false);
        viewAppByWeekBut.setVisible(false);
        customersButton.setVisible(false);
        generateAppReportButFirst.setVisible(false);



        generateScheduleBut.setVisible(true);
        appContactCombo.setVisible(true);
        monthBox2.setVisible(true);
        clearReportBut.setVisible(true);
        contactLabelSchedule.setVisible(true);
        contactMonthLabelSchedule.setVisible(true);

        appContactCombo.getItems().add(1);
        appContactCombo.getItems().add(2);
        appContactCombo.getItems().add(3);

        monthBox2.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");


    }

    /**
     * exits out of the report buttons
     * @param mouseEvent
     */
    public void clearReportButtonClicked(MouseEvent mouseEvent) {


        genScheduleBut.setVisible(true);
        monthBox.setVisible(true);
        monthSearchBut.setVisible(true);
        clearByMonthBut.setVisible(true);
        viewAppByWeekBut.setVisible(true);
        generateAppReportButFirst.setVisible(true);
        customersButton.setVisible(true);



        generateScheduleBut.setVisible(false);
        generateReportBut.setVisible(false);
        appointmentTypeComboBox.setVisible(false);
        monthBox2.setVisible(false);
        clearReportBut.setVisible(false);
        appContactCombo.setVisible(false);

        customerComboBox.setVisible(false);
        monthBox3.setVisible(false);
        generateCustomerReport.setVisible(false);

        reportLabel1.setVisible(false);
        reportLabel2.setVisible(false);

        contactLabelSchedule.setVisible(false);
        contactMonthLabelSchedule.setVisible(false);

        appointmentTypeComboBox.getItems().clear();
        customerComboBox.getItems().clear();
        monthBox2.getItems().clear();
        monthBox3.getItems().clear();

        appReportLabel.setText("");






    }



    /**
     * shows buttons for a appointment type report when clicked
     * @param mouseEvent
     * @throws SQLException
     */
    public void generateAppReportClicked(MouseEvent mouseEvent) throws SQLException {

        generateScheduleBut.setVisible(false);
        genScheduleBut.setVisible(false);
        monthBox.setVisible(false);
        monthSearchBut.setVisible(false);
        clearByMonthBut.setVisible(false);
        viewAppByWeekBut.setVisible(false);
        customersButton.setVisible(false);
        generateAppReportButFirst.setVisible(false);

        monthBox2.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthBox3.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");



        generateReportBut.setVisible(true);
        appointmentTypeComboBox.setVisible(true);
        monthBox2.setVisible(true);
        clearReportBut.setVisible(true);
        customerComboBox.setVisible(true);
        monthBox3.setVisible(true);
        generateCustomerReport.setVisible(true);
        reportLabel1.setVisible(true);
        reportLabel2.setVisible(true);


        Statement sqlStatement = Utility.LogInController.returnConnection().createStatement();
        String sqlCommand = "SELECT DISTINCT Type FROM WJ07LG4.appointments;";
        ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);
        while(sqlResult.next()){
           appointmentTypeComboBox.getItems().add(sqlResult.getString("Type"));
        }


        Statement sqlStatement2 = Utility.LogInController.returnConnection().createStatement();
        String sqlCommand2 = "SELECT Customer_Name FROM WJ07LG4.customers;";
        ResultSet sqlResult2 = sqlStatement2.executeQuery(sqlCommand2);
        while (sqlResult2.next()){
            customerComboBox.getItems().add(sqlResult2.getString("Customer_Name"));
        }




    }


    /**
     * generates contact schedule when clicked.
     * @param mouseEvent
     * @throws SQLException
     */
    public void generateScheduleClickedSecond(MouseEvent mouseEvent) throws SQLException {


        if(appContactCombo.getValue() != null && monthBox2.getValue() != null) {
            appointmentList2.clear();
            Statement sqlStatement = Utility.LogInController.returnConnection().createStatement();
            Statement sqlStatement2 = Utility.LogInController.returnConnection().createStatement();
            String sqlCommand5 = "SELECT * FROM WJ07LG4.appointments WHERE Garage_Bay = " + appContactCombo.getValue() + " AND " +
                    "START LIKE '_____" + months.get(monthBox2.getValue()) + "%' ORDER BY Start ASC;";
            ResultSet sqlResult5 = sqlStatement.executeQuery(sqlCommand5);
            appointmentList2.clear();
            appointmentTable.setItems(appointmentList2);

            while (sqlResult5.next()) {
                String startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlResult5.getTimestamp("Start"));
                startDate = TimeUtilClass.convertTimeToLocal(startDate).toLocalDate().toString() + "  " + TimeUtilClass.convertTimeToLocal(startDate).toLocalTime().toString();
                String endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlResult5.getTimestamp("End"));
                endDate = TimeUtilClass.convertTimeToLocal(endDate).toLocalDate().toString() + "  " + TimeUtilClass.convertTimeToLocal(endDate).toLocalTime().toString();
                 String sqlCommand6 = "SELECT car_name FROM WJ07LG4.Cars WHERE car_id = " + sqlResult5.getInt("customer_car_id") + " ;";
                ResultSet sqlResult6 = sqlStatement2.executeQuery(sqlCommand6);
                sqlResult6.next();
                Appointment appointment = new Appointment(
                        sqlResult5.getInt("Appointment_id"),
                        sqlResult5.getString("Title"),
                        sqlResult5.getString("Description"),
                        sqlResult5.getString("Garage_Bay"),
                        sqlResult5.getString("Type"),
                        startDate,
                        endDate,
                        sqlResult5.getInt("Customer_ID"),
                        sqlResult6.getString("car_name"));

                appointmentList2.add(appointment);//adds appointment object to list

                appointmentTable.setItems(appointmentList2); //displays list of appointments for selected customer


            }

        }

    }



    /**
     * @param mouseEvent
     * @throws SQLException
     */
    public void generateReportClickedSecond(MouseEvent mouseEvent) throws SQLException {

        Statement sqlStatement = Utility.LogInController.returnConnection().createStatement();
        String sqlCommand = "";

       if(monthBox2.getValue() == null && appointmentTypeComboBox.getValue() != null ) {

           sqlCommand = "SELECT COUNT(Type) FROM WJ07LG4.appointments WHERE TYPE ='" + appointmentTypeComboBox.getValue().toString() + "';";
           ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);
           sqlResult.next();
           appReportLabel.setText("The total number of appointments of type "+appointmentTypeComboBox.getValue().toString() + " are " + sqlResult.getString(1));  //SECOND LAMBDA

       }

        if(appointmentTypeComboBox.getValue() == null && monthBox2.getValue() != null) {
            sqlCommand = "SELECT COUNT(Start) FROM WJ07LG4.appointments WHERE START LIKE '_____" + months.get(monthBox2.getValue()) + "%' ORDER BY Start ASC;";
            ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);
            sqlResult.next();
            appReportLabel.setText("The number of appointments in"+ monthBox2.getValue().toString()+ " total " + sqlResult.getString(1));
        }

        if( appointmentTypeComboBox.getValue() != null && monthBox2.getValue() != null) {
            sqlCommand = "Select COUNT(Type) FROM WJ07LG4.appointments WHERE TYPE ='" + appointmentTypeComboBox.getValue().toString().trim() + "' AND " +
                    "START LIKE '_____" + months.get(monthBox2.getValue()) + "%'ORDER BY START ASC;";

            ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);
            sqlResult.next();
            appReportLabel.setText("The number of appointments of type " + appointmentTypeComboBox.getValue().toString()+ " in " + monthBox2.getValue().toString()+ " are "+ sqlResult.getString(1));

        }



    }

    /**
     * generates a customer report when clicked
     * @param mouseEvent
     * @throws SQLException
     */
    public void genCustReport(MouseEvent mouseEvent) throws SQLException {


        if(customerComboBox.getValue() != null) {
            if (monthBox3.getValue() != null || customerComboBox.getValue() != null) {
                Statement sqlStatement = Utility.LogInController.returnConnection().createStatement();
                String sqlCommand = "";
                Statement sqlStatement2 = Utility.LogInController.returnConnection().createStatement();
                String sqlCommand2 = "SELECT CUSTOMER_ID FROM WJ07LG4.customers WHERE CUSTOMER_NAME = '" + customerComboBox.getValue().toString() + "';";
                ResultSet sqlResult2 = sqlStatement2.executeQuery(sqlCommand2);
                sqlResult2.next();


                if (monthBox3.getValue() == null && customerComboBox.getValue() != null) {

                    sqlCommand = "SELECT COUNT(Type) FROM WJ07LG4.appointments WHERE CUSTOMER_ID =" + sqlResult2.getInt("Customer_Id") + ";";
                    ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);
                    sqlResult.next();
                    appReportLabel.setText(customerComboBox.getValue().toString() + " has " + sqlResult.getString(1) + " total appointments ");
                }

                if (customerComboBox.getValue() == null && monthBox3.getValue() != null) {
                    sqlCommand = "SELECT COUNT(Start) FROM WJ07LG4.appointments WHERE START LIKE '_____" + months.get(monthBox3.getValue()) + "%' ORDER BY Start ASC;";
                    ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);
                    sqlResult.next();
                    appReportLabel.setText(monthBox3.getValue().toString() + " has the following number of appointments " + sqlResult.getString(1));
                }

                if (customerComboBox.getValue() != null && monthBox3.getValue() != null) {
                    sqlCommand = "Select COUNT(Type) FROM WJ07LG4.appointments WHERE CUSTOMER_ID = " + sqlResult2.getInt("Customer_Id") + " AND " +
                            "START LIKE '_____" + months.get(monthBox3.getValue()) + "%'ORDER BY START ASC;";

                    ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);
                    sqlResult.next();
                    appReportLabel.setText(customerComboBox.getValue().toString() + " has " + sqlResult.getString(1) + " appointments during " + monthBox3.getValue().toString() + "");
                }


            }
        }
        else {
            appReportLabel.setText("Select a customer.");
        }

    }


    public void InventoryButtonClicked(MouseEvent mouseEvent) throws IOException {


        SceneLoader.loadInventoryMainScreen();



    }
}
