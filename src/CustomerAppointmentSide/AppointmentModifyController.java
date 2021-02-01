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
import InventorySide.InHouse;
import InventorySide.Part;
import Utility.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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

public class AppointmentModifyController implements Initializable {




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
    private DatePicker endDatePicker;
    @FXML
    private Label appointmentModifyWarningLabel;
    @FXML
    private ComboBox requiredAppointmentPartsComboBox;


    @FXML
    private TableView<Part> requiredPartTable;
    @FXML
    private TableColumn<Part, String> partNameCol;

    private final ObservableList<Part> requiredPartList = FXCollections.observableArrayList();
    private ArrayList<String> requiredPartListStringName = new ArrayList<String>();
    private ArrayList<String> requiredPartListStringName2 = new ArrayList<String>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    startTimePicker.getItems().addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    endTimePicker.getItems().addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"); //times for time picker boxes
    startTimePicker2.getItems().addAll("00", "15", "30", "45");
    endTimePicker2.getItems().addAll("00", "15", "30", "45");

    partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));




        contactComboBox.getItems().add(1);
        contactComboBox.getItems().add(2);
        contactComboBox.getItems().add(3);


        Appointment appointment = CustomerController.returnAppointment();  //returns appointment object to fill in text boxes

        appointmentIdTB.setText(String.valueOf(appointment.getAppointmentId()));
        appointmentTitleTB.setText(appointment.getAppointmentTitle());
        appointmentDescriptionTB.setText(appointment.getAppointmentDescription());
        appointmentTypeTB.setText(appointment.getAppointmentType());
        appointmentCustomerIdTB.setText(String.valueOf(appointment.getAppointmentCustomerId()));
        contactComboBox.setValue(appointment.getAppointmentLocation());
        customerCarComboBox.setValue(appointment.getCarId());



        String startDate = "";
        try {
            Statement sqlStatement = LogInController.returnConnection().createStatement();
            String sqlCommand = "SELECT Start FROM WJ07LG4.appointments where appointment_id = " + appointment.getAppointmentId()+ ";";
            ResultSet sqlResult4 = sqlStatement.executeQuery(sqlCommand);
            sqlResult4.next();
            startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlResult4.getTimestamp("Start"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        startDate = TimeUtilClass.convertTimeToLocal(startDate).toLocalDate().toString() + "  " + TimeUtilClass.convertTimeToLocal(startDate).toLocalTime().toString();
        String[] dateArray = startDate.split("(?!^)");


        //java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date4.toString());
        String year = dateArray[0] + dateArray[1] + dateArray[2] + dateArray[3];                  //realized to late I could use getHour(), getMonth(), getDay() with the zonedDate so i did it this way.
        String month = dateArray[5] + dateArray[6];
        String day = dateArray[8] + dateArray[9];  //was having trouble splitting a zone date to put in the selection boxes, so had to split it into an array
        String hour = dateArray[12] + dateArray[13];
        String minute = dateArray[15] + dateArray[16];

        LocalDate dat =  LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

        startDatePicker.setValue(dat);

        startTimePicker.setValue(hour);
        startTimePicker2.setValue(minute);




        String endDate = "";
        try {
            Statement sqlStatement = LogInController.returnConnection().createStatement();
            String sqlCommand = "SELECT End FROM WJ07LG4.appointments where appointment_id = " + appointment.getAppointmentId()+ ";";
            ResultSet sqlResult4 = sqlStatement.executeQuery(sqlCommand);
            sqlResult4.next();
            endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sqlResult4.getTimestamp("End"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        endDate = TimeUtilClass.convertTimeToLocal(endDate).toLocalDate().toString() + "  " + TimeUtilClass.convertTimeToLocal(endDate).toLocalTime().toString();



        String[] dateArray2 = endDate.split("(?!^)");

        String year2 = dateArray2[0] + dateArray2[1] + dateArray2[2] + dateArray2[3];
        String month2 = dateArray2[5] + dateArray2[6];
        String day2 = dateArray2[8] + dateArray2[9];
        String hour2 = dateArray2[12] + dateArray2[13];
        String minute2 = dateArray2[15] + dateArray2[16];

        LocalDate dat2 =  LocalDate.of(Integer.parseInt(year2), Integer.parseInt(month2), Integer.parseInt(day2));
        endDatePicker.setValue(dat2);
        endTimePicker.setValue(hour2);
        endTimePicker2.setValue(minute2);



        try {
            Statement  sqlStatement2 = LogInController.returnConnection().createStatement();
            String sqlCommand2 = "Select * from WJ07LG4.Cars;";
            ResultSet sqlResult2 = sqlStatement2.executeQuery(sqlCommand2);
            while(sqlResult2.next())
            {
                customerCarComboBox.getItems().addAll(sqlResult2.getString("car_name"));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }





        try {
            Statement sqlStatement11 = LogInController.returnConnection().createStatement();
            Statement sqlStatement12 = LogInController.returnConnection().createStatement();





            String sqlCommand11 = "select part_id from appointment_reserved_parts where appointment_id = "+appointmentIdTB.getText()+" ;";
            ResultSet sqlResult11 = sqlStatement11.executeQuery(sqlCommand11);

        while (sqlResult11.next()){
            String sqlCommand12 = " select partName from inHouse_parts where part_id = "+sqlResult11.getString("part_id")+" union select partName from outSourced_parts where part_id = " +
                    ""+ sqlResult11.getString("part_id") + ";";
            ResultSet sqlResult12 = sqlStatement12.executeQuery(sqlCommand12);
            sqlResult12.next();
            Part part = new InHouse(0, sqlResult12.getString("partName"), 0, 0 , 0 , 0, "0");
            requiredPartTable.getItems().addAll(part);
        }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * saveAppointment saves changes to a modified appointment and does the same checks as the orginal save appointment
     * @param mouseEvent
     * @throws SQLException
     */
    public void saveAppointment(MouseEvent mouseEvent) throws SQLException {

        boolean requiredPartsInStock = false;
        boolean requiredPartsInStock2 = true;

        //the following checks if parts are in stock for the reuiqred list
        Statement sqlStatement10 = LogInController.returnConnection().createStatement();
        for(Part part:requiredPartList){
            String sqlCommand10 = "SELECT inventory FROM inHouse_parts WHERE partName = '" + part.getName() + "'   UNION ALL  SELECT inventory FROM outSourced_parts WHERE partName = '"+part.getName()+ "';";
            ResultSet sqlResult10 = sqlStatement10.executeQuery(sqlCommand10);
            sqlResult10.next();
            int occurrences = Collections.frequency(requiredPartListStringName2, part.getName());
            if(sqlResult10.getInt("inventory") < occurrences){
                appointmentModifyWarningLabel.setText(part.getName() + " inventory is to low for the required amount, please schedule at least 2 weeks out.");
                break;
            }
            requiredPartsInStock = true;
        }
        ///////////////
        if (requiredPartList.size() == 0){
            requiredPartsInStock = true;

        }


        ///////////////checks if start date is two weeks after current date.
        LocalDateTime sTime2 = LocalDateTime.parse(startDatePicker.getValue().toString() + "T" + startTimePicker.getValue().toString() + ":" + startTimePicker2.getValue().toString() + ":00");
        LocalDateTime now = LocalDateTime.now();
        boolean isTwoWeeksAfter = false;
        if (sTime2.isAfter (now.plusDays(13))){
            isTwoWeeksAfter = true;

        }
////////////////////////



        if (requiredPartsInStock == true || isTwoWeeksAfter == true) {


            LocalDateTime sTime = LocalDateTime.parse(startDatePicker.getValue().toString() + "T" + startTimePicker.getValue().toString() + ":" + startTimePicker2.getValue().toString() + ":00");
            LocalDateTime eTime = LocalDateTime.parse(endDatePicker.getValue().toString() + "T" + endTimePicker.getValue().toString() + ":" + endTimePicker2.getValue().toString() + ":00");
            if (sTime.isBefore(eTime)) {


                if (contactComboBox.getValue() != null && appointmentCustomerIdTB != null && appointmentIdTB != null && appointmentTypeTB != null
                        && appointmentDescriptionTB != null && appointmentTitleTB != null) {

                    boolean fourNext = false;
                    boolean fiveNext = false; //these boolean used to change true if sql values have a next value without triggering a next skip.
                    boolean sixNext = false;
                    boolean sevenNext = false;

                    String chosenStartDate = startDatePicker.getValue().toString() + " " + startTimePicker.getValue().toString() + ":" + startTimePicker2.getValue().toString() + ":00";
                    String chosenEndDate = endDatePicker.getValue().toString() + " " + endTimePicker.getValue().toString() + ":" + endTimePicker2.getValue().toString() + ":00";

                    ZonedDateTime zStart;
                    ZonedDateTime zEnd;
                    zStart = TimeUtilClass.convertTimeToUtc(chosenStartDate);
                    zEnd = TimeUtilClass.convertTimeToUtc(chosenEndDate);

                    String zStartDay = zStart.getYear() + "-" + zStart.getMonthValue() + "-" + zStart.getDayOfMonth();
                    String zEndDay = zEnd.getYear() + "-" + zEnd.getMonthValue() + "-" + zEnd.getDayOfMonth();


                    Statement sqlStatement2 = LogInController.returnConnection().createStatement();
                    Statement sqlStatement5 = LogInController.returnConnection().createStatement();
                    Statement sqlStatement6 = LogInController.returnConnection().createStatement();
                    Statement sqlStatement7 = LogInController.returnConnection().createStatement();
                    String sqlCommand4 = " SELECT * FROM WJ07LG4.appointments WHERE START >= '" + zStartDay + " " + zStart.getHour() + ":" + zStart.getMinute() + ":00" + "' " +
                            " AND START <=  '" + zEndDay + " " + zEnd.getHour() + ":" + zEnd.getMinute() + ":00" + "';";
                    String sqlCommand5 = " SELECT * FROM WJ07LG4.appointments WHERE END >= '" + zStartDay + " " + zStart.getHour() + ":" + zStart.getMinute() + ":00" + "' " +     //queries check if an appoint exists already in time range
                            "AND END <= '" + zEndDay + " " + zEnd.getHour() + ":" + zEnd.getMinute() + ":00" + "' ;";
                    String sqlCommand7 = " SELECT * FROM WJ07LG4.appointments WHERE START <= '" + zStartDay + " " + zStart.getHour() + ":" + zStart.getMinute() + ":00" + "' " +
                            "AND END >='" + zEndDay + " " + zEnd.getHour() + ":" + zEnd.getMinute() + ":00" + "' ;";
                    String sqlCommand6 = " SELECT * FROM WJ07LG4.appointments WHERE START <= '" + zStartDay + " " + zStart.getHour() + ":" + zStart.getMinute() + ":00" + "' " +
                            "AND END >='" + zStartDay + " " + zStart.getHour() + ":" + zStart.getMinute() + ":00" + "' ;";
                    ResultSet sqlResult6 = sqlStatement6.executeQuery(sqlCommand6);
                    ResultSet sqlResult4 = sqlStatement2.executeQuery(sqlCommand4);
                    ResultSet sqlResult5 = sqlStatement5.executeQuery(sqlCommand5);                                //my sql is not the sharpest so I probably could of done less queries with better joins.
                    ResultSet sqlResult7 = sqlStatement7.executeQuery(sqlCommand7);


                    if (sqlResult4.next()) {
                        if (Integer.valueOf(appointmentIdTB.getText()) != sqlResult4.getInt("Appointment_Id")) {//sql command 4 and this if statement check if there is an appointment in the selected time
                            appointmentModifyWarningLabel.setText("App. Must be 15 minutes after previous App. This appointment start date has a conflict with an appointment starting at "
                                    + TimeUtilClass.convertTimeToLocal2(sqlResult4.getTimestamp("Start").toString()) + "  and ending at "
                                    + TimeUtilClass.convertTimeToLocal2(sqlResult4.getTimestamp("End").toString()));
                            fourNext = true;
                        }
                    } else {
                        fourNext = false;
                    }
                    if (sqlResult5.next()) {
                        if (Integer.valueOf(appointmentIdTB.getText()) != sqlResult5.getInt("Appointment_Id")) {
                            appointmentModifyWarningLabel.setText("App. Must be 15 minutes after previous App. This appointment end date has a conflict with an appointment starting at "
                                    + TimeUtilClass.convertTimeToLocal2(sqlResult5.getTimestamp("Start").toString()) + "  and ending at "
                                    + TimeUtilClass.convertTimeToLocal2(sqlResult5.getTimestamp("End").toString()));
                            fiveNext = true;
                        }
                    } else {
                        fiveNext = false;
                    }
                    if (sqlResult6.next()) {
                        if (Integer.valueOf(appointmentIdTB.getText()) != sqlResult6.getInt("Appointment_Id")) {
                            appointmentModifyWarningLabel.setText("App. Must be 15 minutes after previous App. This appointment has a conflict with an appointment starting at "
                                    + TimeUtilClass.convertTimeToLocal2(sqlResult6.getTimestamp("Start").toString()) + "  and ending at "
                                    + TimeUtilClass.convertTimeToLocal2(sqlResult6.getTimestamp("End").toString()));
                            sixNext = true;
                        }
                    } else {
                        sixNext = false;                                     //the above 3 sql commands and the following if check if an appointment is in the database that conflicts with the selected one.
                    }
                    if (sqlResult7.next()) {
                        if (Integer.valueOf(appointmentIdTB.getText()) != sqlResult7.getInt("Appointment_Id")) {
                            appointmentModifyWarningLabel.setText("App. Must be 15 minutes after previous App. This appointment has a conflict with an appointment starting at "
                                    + TimeUtilClass.convertTimeToLocal2(sqlResult7.getTimestamp("Start").toString()) + "  and ending at "
                                    + TimeUtilClass.convertTimeToLocal2(sqlResult7.getTimestamp("End").toString()));
                            sevenNext = true;
                        }
                    } else {
                        sevenNext = false;                                     //the above 3 sql commands and the following if check if an appointment is in the database that conflicts with the selected one.
                    }

                    if (fourNext == false & fiveNext == false & sixNext == false & sevenNext == false) {

                        try {
                            Statement sqlStatement = LogInController.returnConnection().createStatement();
                            String sqlCommand2 = "select car_id from WJ07LG4.Cars where car_name = '" + customerCarComboBox.getValue() + "';";
                            ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand2);//used to grab the contact combo box contact id
                            sqlResult.next();

                            String startDate = startDatePicker.getValue().toString() + " " + startTimePicker.getValue() + ":" + startTimePicker2.getValue() + ":00";
                            String endDate = endDatePicker.getValue().toString() + " " + endTimePicker.getValue() + ":" + endTimePicker2.getValue() + ":00";
                            String sqlCommand = "UPDATE WJ07LG4.appointments SET " +
                                    "Title = '" + appointmentTitleTB.getText() + "'," +
                                    "Description = '" + appointmentDescriptionTB.getText() + "'," +
                                    "Garage_Bay = '" + contactComboBox.getValue() + "'," +
                                    "Type = '" + appointmentTypeTB.getText() + "'," +
                                    "Start = '" + TimeUtilClass.convertTimeToUtc(startDate).toLocalDateTime() + ":00' ," +
                                    "End = '" + TimeUtilClass.convertTimeToUtc(endDate).toLocalDateTime() + ":00' ," +
                                    "Customer_ID = " + appointmentCustomerIdTB.getText() + " ," +
                                    "customer_car_id  = " + sqlResult.getInt("car_id") + " ," +
                                    " Last_Update = CURRENT_TIMESTAMP ," +
                                    " Last_Updated_By = '" + LogInController.returnUserName() + "'" +
                                    " WHERE Appointment_ID =" + appointmentIdTB.getText() + ";";



                            sqlStatement.execute(sqlCommand);
                            sqlStatement.close();
                            sqlStatement2.close();
                            sqlStatement5.close();
                            sqlStatement6.close();
                            SceneLoader.loadCustomerPage();
                        } catch (MySQLIntegrityConstraintViolationException | IOException e) {

                            e.printStackTrace();


                        }

                    }
                    //Appointment appointment = new Appointment()

                    // dbConnection.close();

                } else {
                    appointmentModifyWarningLabel.setText("Make sure each box has a proper value before saving.");
                }
            } else {
                appointmentModifyWarningLabel.setText("Start time must be before end time.");
            }

        }


        ////////////////////


        Statement sqlStatement8 = LogInController.returnConnection().createStatement();

        Statement sqlStatement9 = LogInController.returnConnection().createStatement();

        Statement sqlStatement11 = LogInController.returnConnection().createStatement();


        for (Part part : requiredPartList) {

            String sqlCommand8 = " SELECT part_id FROM inHouse_parts WHERE partName = '" + part.getName() + "'   UNION ALL  SELECT part_id FROM outSourced_parts WHERE partName = '" + part.getName() + "';";

            ResultSet sqlResult8 = sqlStatement8.executeQuery(sqlCommand8);
            sqlResult8.next();
            String sqlCommand9 = " Insert into WJ07LG4.appointment_reserved_parts (part_id, appointment_id) Values (" + sqlResult8.getString("part_id") + "," + appointmentIdTB.getText() + ");";
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

    /**
     * cancelButtonclicked sends back to customer page
     * @param mouseEvent
     * @throws IOException
     */
    public void cancelButtonclicked(MouseEvent mouseEvent) throws IOException {

        SceneLoader.loadCustomerPage();


    }

    /**
     * addREquiredPartButton adds a part to a list to associate those parts with that appointment
     * @param mouseEvent
     */
    public void addRequiredPartButton(MouseEvent mouseEvent) {

        if(requiredAppointmentPartsComboBox.getValue() != null) {

            Part part = new InHouse(0, requiredAppointmentPartsComboBox.getValue().toString(), 0, 0, 0, 0, "0");
            requiredPartList.add(part);
            requiredPartListStringName2.add(part.getName());
            requiredPartTable.getItems().addAll(part);
        }
    }



    public void loadParts(MouseEvent mouseEvent) {

        if(customerCarComboBox.getSelectionModel().getSelectedItem() != null) {
            try {
                int carId;
                Statement sqlStatement = LogInController.returnConnection().createStatement();
                Statement sqlStatement2 = LogInController.returnConnection().createStatement();

                String sqlCommand2 = " SELECT car_id FROM WJ07LG4.Cars WHERE car_name ='" + customerCarComboBox.getSelectionModel().getSelectedItem() +"';";
                ResultSet sqlResult2 = sqlStatement2.executeQuery(sqlCommand2);
                sqlResult2.next();
                carId = sqlResult2.getInt("car_id");
                String sqlCommand = "SELECT partName FROM WJ07LG4.outSourced_parts where part_id IN (SELECT part_id FROM associated_outSourced_car_parts WHERE car_id ="+ carId + ");";
                String sqlCommand3 = "SELECT partName FROM WJ07LG4.inHouse_parts where part_id IN (SELECT part_id FROM associated_inHouse_car_parts WHERE car_id ="+ carId + ");";
                ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);
                requiredAppointmentPartsComboBox.getItems().clear();

                while (sqlResult.next()) {
                    requiredAppointmentPartsComboBox.getItems().addAll(sqlResult.getString("partName"));
                }
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

