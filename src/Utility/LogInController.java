package Utility;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Utility.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;



/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 12/9/2020
 *
 */

public class LogInController implements Initializable {


    /**
     * dbConnection is used to store the connection that is created for the rest of the program.
     */
    private static Connection dbConnection;

    @FXML
    private TextField loginUserNameTB;
    @FXML
    private TextField loginPasswordTB;
    @FXML
    private Label passwordWrongLabel;
    @FXML
    private Label userZoneLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Label headerLabel;

    /**
     * userName and userId are used to store the logged in user for use in other parts
     * frenhLanguage is a boolean to mark true if user has french language enabled.
     */
    private static String userName;
    private static int userId;
    private  boolean frenchLanguage = false;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(Locale.getDefault().getLanguage().equals("fr")){
            frenchLanguage = true;
            userNameLabel.setText("Nom d'utilisateur");        //translations done via google translate.
            passwordLabel.setText("mot de passe");
            loginButton.setText("entrer");
            headerLabel.setText("GlobalWide Consulting écran de connexion ");
        }

        dbConnection = DBconnection.startConnection();      //starts database connection on login page load
        TimeZone zone = TimeZone.getDefault();
        userZoneLabel.setText(zone.getID() + " time zone.");




    }





    /**
     *
     * @param mouseEvent compares login credentials to database to see if match and loads next page if are.
     * @throws SQLException
     * @throws IOException
     */
    public void loginButtonClicked(MouseEvent mouseEvent) throws SQLException, IOException {


        boolean loginSuccessful = false;
        if(loginUserNameTB.getText().equals("")){
            passwordWrongLabel.setText("Enter a username and password.");
        }


        Statement sqlStatement = dbConnection.createStatement();
        Statement sqlStatement2 = dbConnection.createStatement();
        String sqlCommand = "SELECT User_Name, User_ID FROM WJ07LG4.users;";
        String sqlCommand2 = "SELECT Password FROM WJ07LG4.users;";

        ResultSet sqlResult = sqlStatement.executeQuery(sqlCommand);
        ResultSet sqlResult2 = sqlStatement2.executeQuery(sqlCommand2);
        while(sqlResult.next()){
            if (sqlResult.getString("User_name").equals(loginUserNameTB.getText())) {     //compares username text box to user names in db
                while (sqlResult2.next()){
                    if(sqlResult2.getString("Password").equals(loginPasswordTB.getText())){   //checks if password matches
                        userName = sqlResult.getString("User_name");  //stores userName
                        userId = sqlResult.getInt("User_ID");          //stores user_id that logged in
                        Utility.SceneLoader.loadMainPage();
                        UserLogWriteClass.writeToUserLog("User " + userName + " SUCCESSFULLY logged in " + ZonedDateTime.now());
                        loginSuccessful = true;


                    }

                }

            }


        }

        if (frenchLanguage) { passwordWrongLabel.setText("L'identifiant ou le mot de passe est incorrect.");}
        else{passwordWrongLabel.setText("Username or Password is incorrect.");}

        if(loginSuccessful != true) {
            UserLogWriteClass.writeToUserLog("User " + loginUserNameTB.getText() + " FAILED to log in " + ZonedDateTime.now());
        }

        sqlStatement.close();
        sqlStatement2.close();


    }


    /**
     * returnUserName returns the user name to other parts of the program that need to know who the user is
     * @return username
     */
    public static String returnUserName(){
        return userName;
    }

    /**
     * returnConnection returns the connection
     * @return
     */
    public static Connection returnConnection(){
        return dbConnection;
    }

    /**
     * returnUserId returns the user id when it is needed to update a sql entry.
     * @return
     */
    public static int returnUserId () {
        return  userId;
    }


}
