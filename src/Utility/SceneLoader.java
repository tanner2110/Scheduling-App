package Utility;

import MainClass.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneLoader {





    /**
     *
     * @throws IOException method to load main page
     */
    public static void loadMainPage() throws IOException {
        Parent modifyPartRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/MainForm.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        MainClass.Main.getPrimaryStage().setScene(modifyPartButClick);
        MainClass.Main.getPrimaryStage().show();
    }

    /**
     * loads login page
     * @throws IOException
     */
    public static void loadLogIn() throws IOException {
        Parent modifyPartRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/LogInForm.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        MainClass.Main.getPrimaryStage().setScene(modifyPartButClick);
        MainClass.Main.getPrimaryStage().show();
    }

    /**
     * loads customer page
     * @throws IOException
     */
    public static void loadCustomerPage() throws IOException {
        Parent modifyPartRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/CustomerForm.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        MainClass.Main.getPrimaryStage().setScene(modifyPartButClick);
        MainClass.Main.getPrimaryStage().show();
    }

    /**
     * loads appointment page
     * @throws IOException
     */
    public static void loadAppointmentPage() throws IOException {
        Parent modifyPartRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/AppointmentForm.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        MainClass.Main.getPrimaryStage().setScene(modifyPartButClick);
        MainClass.Main. getPrimaryStage().show();
    }


    /**
     * loads appointment modify page
     * @throws IOException
     */
    public static void loadAppointmentModifyPage() throws IOException {

        Parent modifyPartRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/AppointmentModifyForm.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        MainClass.Main.getPrimaryStage().setScene(modifyPartButClick);
        MainClass.Main.getPrimaryStage().show();


    }




    public static void loadInventoryMainScreen() throws IOException {

        Parent modifyPartRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/InventoryMainForm.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        MainClass.Main.getPrimaryStage().setScene(modifyPartButClick);
        MainClass.Main.getPrimaryStage().show();


    }

    public static void loadModifyPart() throws IOException {

        Parent modifyPartRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/MainForm.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        MainClass.Main.getPrimaryStage().setScene(modifyPartButClick);
        MainClass.Main.getPrimaryStage().show();


    }

    public static void loadinHousePart() throws IOException {

        Parent modifyPartRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/AddPartInHouse.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        MainClass.Main.getPrimaryStage().setScene(modifyPartButClick);
        MainClass.Main.getPrimaryStage().show();


    }

    public static void loadOutSourcedPart() throws IOException {

        Parent modifyPartRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/AddPartOutSourced.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        MainClass.Main.getPrimaryStage().setScene(modifyPartButClick);
        MainClass.Main.getPrimaryStage().show();


    }

    public static void loadinHousePartModify() throws IOException {

        Parent modifyPartRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/ModifyPartInHouse.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        MainClass.Main.getPrimaryStage().setScene(modifyPartButClick);
        MainClass.Main.getPrimaryStage().show();


    }

    public static void loadOutSourcedPartModify() throws IOException {

        Parent modifyPartRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/ModifyPartOut.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        MainClass.Main.getPrimaryStage().setScene(modifyPartButClick);
        MainClass.Main.getPrimaryStage().show();


    }


    public static void loadAddCar() throws IOException {

        Parent modifyPartRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/AddCar.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        MainClass.Main.getPrimaryStage().setScene(modifyPartButClick);
        MainClass.Main.getPrimaryStage().show();


    }

    public static void loadModifyCar() throws IOException {

        Parent modifyPartRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/ModifyCar.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        MainClass.Main.getPrimaryStage().setScene(modifyPartButClick);
        MainClass.Main.getPrimaryStage().show();


    }









}
