package MainClass;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Utility.*;

import java.io.IOException;
import java.sql.Connection;

/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 12/9/2020
 * MainClass.Main page
 */

public class Main extends Application {



    private static Stage primaryStage;
    private static Connection dbConnection;


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent mainRoot = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/LogInForm.fxml"));
        Scene mainPage = new Scene(mainRoot);
        primaryStage.setTitle("Gnome Auto");
        primaryStage.setScene(mainPage);
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }



    /**
     *
     * @return primary stage to methods in controllers to allow changing of scene.
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }


    /**
     * returns the database connection created to other pages that need it.
     * @return
     */
    public static Connection getDbConnection() {
        return dbConnection;
    }














}
