package CustomerAppointmentSide;

import MainClass.Main;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import InventorySide.InHouse;
import InventorySide.Part;
import Utility.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;


/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 12/9/2020
 *
 */

public class AppointmentController implements Initializable {




    @FXML
    private TextField appointmentIdTB;
    @FXML
    private TextField appointmentTitleTB;
    @FXML
    private TextField  appointmentDescriptionTB;
    @FXML
    private TextField  appointmentTypeTB;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TextField appointmentCustomerIdTB;
    @FXML
    private TextField appointmentUserIdTB;
    @FXML
    private TextField appointmentLocationTB;
    @FXML
    private ComboBox startTimePicker;
    @FXML
    private ComboBox endTimePicker;
    @FXML
    private ComboBox startTimePicker2;
    @FXML
    private ComboBox endTimePicker2;
    @FXML
    private ComboBox contactComboBox;
    @FXML
    private ComboBox customerCarComboBox;
    @FXML
    private ComboBox requiredAppointmentPartsComboBox;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TableView<Part> requiredPartTable;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private Button addRequiredPartButton;

    @FXML
    private Label appConflictLabel;

    private final ObservableList<Part> requiredPartList = FXCollections.observableArrayList();
    private ArrayList<String> requiredPartListStringName = new ArrayList<String>();

    /**
     * appointmentId is used in generating a unique appointment id.
     */
    private int appointmentId;


    /**
     * openHour and closeHour are used for keeping appointments with business hours
     */
    private static int openHour = 800;
    private static int closeHour = 2200;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

startTimePicker.getItems().addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
endTimePicker.getItems().addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"); //times for time picker boxes
startTimePicker2.getItems().addAll("00", "15", "30", "45");
endTimePicker2.getItems().addAll("00", "15", "30", "45");

startTimePicker.setValue("01");
startTimePicker2.setValue("00");
endTimePicker.setValue("01"); //default time picker times
endTimePicker2.setValue("00");
startDatePicker.setValue(LocalDate.now());
endDatePicker.setValue(LocalDate.now());

        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));


        try {
            Statement sqlStatement = LogInController.returnConnection().createStatement();
            Statement sqlStatement2 = LogInController.returnConnection().createStatement();


            contactComboBox.getItems().add(1);
            contactComboBox.getItems().add(2);
            contactComboBox.getItems().add(3);



            /////////////////////////////////////////////////
            String sqlCommand3 = "select appointment_id FROM WJ07LG4.appointments order by appointment_id desc;";
            String sqlCommand4 = "Select * from WJ07LG4.Cars;";
            ResultSet sqlResult3 = sqlStatement.executeQuery(sqlCommand3);//////////////////// this block sorts appointment_id descending, adds 1 to make a unique appointment id and sets the textbox./////
            ResultSet sqlResult4 = sqlStatement2.executeQuery(sqlCommand4);

            if(sqlResult3.next())
            {
                appointmentId = Integer.parseInt(sqlResult3.getString("Appointment_Id")) + 1; //generates appoitnment ID from sorted sql query plus 1
            }
            else {
                appointmentId = 1;
            }

            while(sqlResult4.next())
            {
                customerCarComboBox.getItems().addAll(sqlResult4.getString("car_name"));
            }
            appointmentIdTB.setText(Integer.toString(appointmentId));
            ////////////////////////////////////////////////////////////////////////////
            appointmentCustomerIdTB.setText(String.valueOf(CustomerController.returnCustomerId()));
            appointmentUserIdTB.setText(String.valueOf(LogInController.returnUserId()));


            sqlStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * saveAppointment saves an appointment and does various checks to see if combo boxes are empty, and if time is during business hours, if an appointment exits during that time
     * @param mouseEvent
     * @throws SQLException
     * @throws ParseException
     */
    public void saveAppointment(MouseEvent mouseEvent) throws SQLException, ParseException {


        boolean requiredPartsInStock = false;
        boolean requiredPartsInStock2 = true;


        //the following checks if parts are in stock for the required list
        Statement sqlStatement10 = LogInController.returnConnection().createStatement();
        for(Part part:requiredPartList){
            String sqlCommand10 = "SELECT inventory FROM inHouse_parts WHERE partName = '" + part.getName() + "'   UNION ALL  SELECT inventory FROM outSourced_parts WHERE partName = '"+part.getName()+ "';";
            ResultSet sqlResult10 = sqlStatement10.executeQuery(sqlCommand10);
            sqlResult10.next();
            int occurrences = Collections.frequency(requiredPartListStringName, part.getName());
            if(sqlResult10.getInt("inventory") < occurrences){
                appConflictLabel.setText(part.getName() + " inventory is to low for the required amount, please schedule at least 2 weeks out.");
                break;
            }
            requiredPartsInStock = true;
        }



        ///////////////checks if start date is two weeks after current date.
        LocalDateTime sTime2 = LocalDateTime.parse(startDatePicker.getValue().toString() + "T" + startTimePicker.getValue().toString() + ":" + startTimePicker2.getValue().toString() + ":00");
        LocalDateTime now = LocalDateTime.now();
        boolean isTwoWeeksAfter = false;
         if (sTime2.isAfter (now.plusDays(13))){
             isTwoWeeksAfter = true;

        }
////////////////////////below checks if list has no required parts
        if (requiredPartList.size() == 0){
            requiredPartsInStock = true;
        }


/////////////below checks if parts in stock or 2 weeks out appointment to start next sequence of checks and save the appointment
        if (requiredPartsInStock == true || isTwoWeeksAfter == true) {


            LocalDateTime sTime = LocalDateTime.parse(startDatePicker.getValue().toString() + "T" + startTimePicker.getValue().toString() + ":" + startTimePicker2.getValue().toString() + ":00");
            LocalDateTime eTime = LocalDateTime.parse(endDatePicker.getValue().toString() + "T" + endTimePicker.getValue().toString() + ":" + endTimePicker2.getValue().toString() + ":00"); //storing dates from date picker
            if (sTime.isBefore(eTime)) {

                if (contactComboBox.getValue() != null && appointmentCustomerIdTB != null && appointmentIdTB != null && appointmentTypeTB != null
                        && appointmentDescriptionTB != null && appointmentTitleTB != null) {

                    String chosenStartDate = startDatePicker.getValue().toString() + " " + startTimePicker.getValue().toString() + ":" + startTimePicker2.getValue().toString() + ":00";
                    String chosenEndDate = endDatePicker.getValue().toString() + " " + endTimePicker.getValue().toString() + ":" + endTimePicker2.getValue().toString() + ":00";


                    ZonedDateTime zStart;
                    ZonedDateTime zEnd;

                    zStart = TimeUtilClass.convertTimeToUtc(chosenStartDate);
                    zEnd = TimeUtilClass.convertTimeToUtc(chosenEndDate);

                    int chosenStart = 0;  //these used to make a number out of to compare time to eastern time to see if it is in range.
                    int chosenEnd = 0;

                    if (startTimePicker2.getValue().toString().equals("00")) {
                        chosenStart = Integer.valueOf(TimeUtilClass.convertTimeEastern(chosenStartDate).getHour() + String.valueOf(TimeUtilClass.convertTimeEastern(chosenStartDate).getMinute()) + "0");
                    } //a zero is trimmed for some reason so need this if
                    else {
                        chosenStart = Integer.parseInt(String.valueOf(TimeUtilClass.convertTimeEastern(chosenStartDate).getHour()) + String.valueOf(TimeUtilClass.convertTimeEastern(chosenStartDate).getMinute()));
                    }

                    if (endTimePicker2.getValue().toString().equals("00")) {
                        chosenEnd = Integer.parseInt(String.valueOf(TimeUtilClass.convertTimeEastern(chosenEndDate).getHour()) + String.valueOf(TimeUtilClass.convertTimeEastern(chosenEndDate).getMinute()) + "0");
                    } //a zero is trimmed for some reason so need this if
                    else {
                        chosenEnd = Integer.parseInt(String.valueOf(TimeUtilClass.convertTimeEastern(chosenEndDate).getHour()) + String.valueOf(TimeUtilClass.convertTimeEastern(chosenEndDate).getMinute()));
                    }


                    //this block and if check if appointment time is in business hours.
                    if (chosenStart >= openHour && chosenStart <= closeHour && chosenEnd <= closeHour && chosenEnd >= openHour) {  //check for business hours

                        boolean fourNext = false;
                        boolean fiveNext = false;
                        boolean sixNext = false;
                        boolean sevenNext = false;
                        Statement sqlStatement2 = LogInController.returnConnection().createStatement();
                        Statement sqlStatement5 = LogInController.returnConnection().createStatement();
                        Statement sqlStatement6 = LogInController.returnConnection().createStatement();
                        Statement sqlStatement7 = LogInController.returnConnection().createStatement();
                        String zStartDay = zStart.getYear() + "-" + zStart.getMonthValue() + "-" + zStart.getDayOfMonth();
                        String zEndDay = zEnd.getYear() + "-" + zEnd.getMonthValue() + "-" + zEnd.getDayOfMonth();

                        String sqlCommand4 = " SELECT * FROM WJ07LG4.appointments WHERE START >= '" + zStartDay + " " + zStart.getHour() + ":" + zStart.getMinute() + ":00" + "' " +
                                " AND START <=  '" + zEndDay + " " + zEnd.getHour() + ":" + zEnd.getMinute() + ":00" + "';";
                        String sqlCommand5 = " SELECT * FROM WJ07LG4.appointments WHERE END >= '" + zStartDay + " " + zStart.getHour() + ":" + zStart.getMinute() + ":00" + "' " +     //queries check if an appointment exists already in time range
                                "AND END <= '" + zEndDay + " " + zEnd.getHour() + ":" + zEnd.getMinute() + ":00" + "' ;";
                        String sqlCommand7 = " SELECT * FROM WJ07LG4.appointments WHERE START <= '" + zStartDay + " " + zStart.getHour() + ":" + zStart.getMinute() + ":00" + "' " +
                                "AND END >='" + zEndDay + " " + zEnd.getHour() + ":" + zEnd.getMinute() + ":00" + "' ;";
                        String sqlCommand6 = " SELECT * FROM WJ07LG4.appointments WHERE START <= '" + zStartDay + " " + zStart.getHour() + ":" + zStart.getMinute() + ":00" + "' " +
                                "AND END >='" + zStartDay + " " + zStart.getHour() + ":" + zStart.getMinute() + ":00" + "' ;";
                        ResultSet sqlResult6 = sqlStatement6.executeQuery(sqlCommand6);
                        ResultSet sqlResult4 = sqlStatement2.executeQuery(sqlCommand4);
                        ResultSet sqlResult5 = sqlStatement5.executeQuery(sqlCommand5);
                        ResultSet sqlResult7 = sqlStatement7.executeQuery(sqlCommand7);


                        while (sqlResult4.next()) {               //sql command 4 and this if statement check if there is an appointment in the selected time
                            if (sqlResult4.getString("Garage_Bay").equals(contactComboBox.getValue().toString())) {
                                appConflictLabel.setText("Try a different garage or schedule App. 15 minutes after conflicting one. This appointment start date has a conflict with an appointment starting at "
                                        + TimeUtilClass.convertTimeToLocal2(sqlResult4.getTimestamp("Start").toString()) + "  and ending at "
                                        + TimeUtilClass.convertTimeToLocal2(sqlResult4.getTimestamp("End").toString()));

                                fourNext = true;
                            }
                        }
                        while (sqlResult5.next()) {
                            if (sqlResult5.getString("Garage_Bay").equals(contactComboBox.getValue().toString())) {
                                appConflictLabel.setText("Try a different garage or schedule App. 15 minutes after conflicting one. This appointment end date has a conflict with an appointment starting at "
                                        + TimeUtilClass.convertTimeToLocal2(sqlResult5.getTimestamp("Start").toString()) + "  and ending at "
                                        + TimeUtilClass.convertTimeToLocal2(sqlResult5.getTimestamp("End").toString()));                          //if an if statement triggered here means has conflicting appointment.
                                fiveNext = true;
                            }
                        }
                        while (sqlResult6.next()) {
                            if (sqlResult6.getString("Garage_Bay").equals(contactComboBox.getValue().toString())) {
                                appConflictLabel.setText("Try a different garage or schedule App. 15 minutes after conflicting one. This appointment has a conflict with an appointment starting at "
                                        + TimeUtilClass.convertTimeToLocal2(sqlResult6.getTimestamp("Start").toString()) + "  and ending at "
                                        + TimeUtilClass.convertTimeToLocal2(sqlResult6.getTimestamp("End").toString()));
                                sixNext = true;
                            }
                        }

                        while (sqlResult7.next()) {
                            if (sqlResult7.getString("Garage_Bay").equals(contactComboBox.getValue().toString())) {
                                appConflictLabel.setText("Try a different garage or schedule App. 15 minutes after conflicting one. This appointment has a conflict with an appointment starting at "
                                        + TimeUtilClass.convertTimeToLocal2(sqlResult7.getTimestamp("Start").toString()) + "  and ending at "
                                        + TimeUtilClass.convertTimeToLocal2(sqlResult7.getTimestamp("End").toString()));
                                sevenNext = true;
                            }
                        }

                        if (fourNext == false & fiveNext == false & sixNext == false & sevenNext == false) {         //booleans from above if statement if no conflict.
                            try {

                                String startDate = startDatePicker.getValue().toString() + " " + startTimePicker.getValue() + ":" + startTimePicker2.getValue() + ":00";
                                String endDate = endDatePicker.getValue().toString() + " " + endTimePicker.getValue() + ":" + endTimePicker2.getValue() + ":00";

                                //Connection dbConnection = DBconnection.startConnection();
                                Statement sqlStatement = LogInController.returnConnection().createStatement();
                                String sqlCommand2 = "select car_id from WJ07LG4.Cars where car_name = '" + customerCarComboBox.getValue().toString() + "';";
                                ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand2);//used to grab the contact combo box contact id
                                sqlResult.next();//done to place sql result in place to grab
                                String sqlCommand = "INSERT INTO `WJ07LG4`.`appointments`" +
                                        "(`Appointment_ID`," +
                                        "`Title`," +
                                        "`Description`," +
                                        "`Type`," +
                                        "`Start`," +
                                        "`End`," +
                                        "`Create_Date`," +
                                        "`Created_By`," +
                                        "`Last_Update`," +
                                        "`Last_Updated_By`," +                                          //sql command to store appointment.
                                        "`Customer_ID`," +
                                        "`User_ID`," +
                                        "`Garage_Bay`," +
                                        "`customer_car_id`)" +
                                        "VALUES" +
                                        "(" + appointmentId + ",'" +
                                        appointmentTitleTB.getText().trim() + "','" +
                                        appointmentDescriptionTB.getText() + "','" +
                                        appointmentTypeTB.getText().trim() + "','" +
                                        TimeUtilClass.convertTimeToUtc(startDate).toLocalDateTime() + "','" +
                                        TimeUtilClass.convertTimeToUtc(endDate).toLocalDateTime() + "'," +
                                        "CURRENT_TIMESTAMP," +
                                        "'" + LogInController.returnUserName() + "'," +
                                        " CURRENT_TIMESTAMP," +
                                        "'" + LogInController.returnUserName() + "'," +
                                        "" + CustomerController.returnCustomerId() + ",'" +
                                        appointmentUserIdTB.getText() + "'," +
                                        contactComboBox.getValue() + "," +
                                        sqlResult.getString("car_id") + ");";


                                sqlStatement.execute(sqlCommand);
                                sqlStatement.close();
                                SceneLoader.loadCustomerPage();
                                sqlStatement2.close();
                                sqlStatement5.close();
                                sqlStatement6.close();
                            } catch (MySQLIntegrityConstraintViolationException | IOException e) {

                                e.printStackTrace();


                            }
                        }
                    } else { //else  if appointment not in proper time for open close
                        appConflictLabel.setText("Appointment must be inside of business hours 8:00 AM to 10:00 PM U.S Eastern Time");
                    }

                } else {
                    appConflictLabel.setText("Make sure each box has a proper value before saving");
                }

            } else {
                appConflictLabel.setText("Start date must be before end date.");


            }
////////////////////


            Statement sqlStatement8 = LogInController.returnConnection().createStatement();

            Statement sqlStatement9 = LogInController.returnConnection().createStatement();

            Statement sqlStatement11 = LogInController.returnConnection().createStatement();


            for (Part part : requiredPartList) {

                String sqlCommand8 = " SELECT part_id FROM inHouse_parts WHERE partName = '" + part.getName() + "'   UNION ALL  SELECT part_id FROM outSourced_parts WHERE partName = '" + part.getName() + "';";

                ResultSet sqlResult8 = sqlStatement8.executeQuery(sqlCommand8);
                sqlResult8.next();
                String sqlCommand9 = " Insert into WJ07LG4.appointment_reserved_parts (part_id, appointment_id) Values (" + sqlResult8.getString("part_id") + "," + appointmentId + ");";

                sqlStatement9.execute(sqlCommand9);



                //subtracting from inventory when parts reserved for an appointment
                String sqlCommand11 = "UPDATE WJ07LG4.inHouse_parts SET inventory = inventory - 1 WHERE partName ='" + part.getName() + "';";
                String sqlCommand12 = "UPDATE WJ07LG4.outSourced_parts SET inventory = inventory - 1 WHERE partName ='" + part.getName() + "';";
                if (part.checkClass().equals("InHouse")){
                    sqlStatement11.execute(sqlCommand11);
                }
                else {
                    sqlStatement11.execute(sqlCommand12);
                }



            }


/////////////


        }


    }

    /**
     * cancelButtonclicked heads back to the customer page
     * @param mouseEvent
     * @throws IOException
     */
    public void cancelButtonclicked(MouseEvent mouseEvent) throws IOException {

        SceneLoader.loadCustomerPage();


    }



    public void addRequiredPartButton(MouseEvent mouseEvent) {


        if(requiredAppointmentPartsComboBox.getValue() != null) {
            Part part = new InHouse(0, requiredAppointmentPartsComboBox.getValue().toString(), 0, 0, 0, 0, "0");
            requiredPartList.add(part);
            requiredPartListStringName.add(requiredAppointmentPartsComboBox.getValue().toString());
            requiredPartTable.getItems().addAll(part);
        }



    }

    public void loadCarParts(ActionEvent actionEvent) {

        if(customerCarComboBox.getSelectionModel().getSelectedItem() != null) {
            try {
                int carId;
                Statement sqlStatement = LogInController.returnConnection().createStatement();
                Statement sqlStatement2 = LogInController.returnConnection().createStatement();
                /////////////////////////////////////////////////////////////////////////start block
                String sqlCommand2 = " SELECT car_id FROM WJ07LG4.Cars WHERE car_name ='" + customerCarComboBox.getSelectionModel().getSelectedItem() +"';";
                ResultSet sqlResult2 = sqlStatement2.executeQuery(sqlCommand2);
                sqlResult2.next();
                carId = sqlResult2.getInt("car_id");
                String sqlCommand = "SELECT partName FROM WJ07LG4.outSourced_parts where part_id IN (SELECT part_id FROM associated_outSourced_car_parts WHERE car_id ="+ carId + ");";
                String sqlCommand3 = "SELECT partName FROM WJ07LG4.inHouse_parts where part_id IN (SELECT part_id FROM associated_inHouse_car_parts WHERE car_id ="+ carId + ");";
                ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);
                requiredAppointmentPartsComboBox.getItems().clear();
                //this  block adds the division names to combo box from sql db
                while (sqlResult.next()) {
                    requiredAppointmentPartsComboBox.getItems().addAll(sqlResult.getString("partName"));
                }/////////////////////////////////////////////////////////////////end block
                sqlResult = sqlStatement.executeQuery(sqlCommand3);
                while (sqlResult.next()) {
                    requiredAppointmentPartsComboBox.getItems().addAll(sqlResult.getString("partName"));
                }
                sqlStatement.close();
                sqlStatement2.close();
            }
            catch (SQLException throwable) {
                throwable.printStackTrace();



            }
        }


    }
}
