package InventorySide;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Utility.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 11/11/2020
 * ConfirmWindowController is the controller for the pop up window that confirms delete and remove of items.
 */
public class ConfirmWindowController {

    /**
     * par1 and product1 are where the going to be deleted objects are stored until confirmed deleted.
     */
    private static Part part1;
    private static Car product1;

    private static Stage confirmWindow = new Stage();

    /**
     *
     * @param part sends part to confirm box class for delete
     * @throws IOException
     * showConfirmBoxPart brings up a confirm delete part box
     */
    public static void showConfirmBoxPart(Part part) throws IOException {


        Parent confirmRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/ConfirmWindowPart.fxml"));
        Scene scene = new Scene(confirmRoot);
        confirmWindow.setScene(scene);
        confirmWindow.show();
        part1 = part;


    }

    /**
     *
     * @param part sends part to confirm box class for delete
     * @throws IOException
     * showConfirmBoxPart2 brings up a confirm delete part box
     */
    public static void showConfirmBoxPart2(Part part) throws IOException {



        Parent confirmRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/ConfirmWindowPart2.fxml"));
        Scene scene = new Scene(confirmRoot);
        confirmWindow.setScene(scene);
        confirmWindow.show();
        part1 = part;


    }

    /**
     *
     * @param product
     * @throws IOException
     * showConfirmBoxProduct brings up a confirm delete product box
     */
    public static void showConfirmBoxProduct(Car product) throws IOException {


        Parent confirmRoot = FXMLLoader.load(SceneLoader.class.getClassLoader().getResource("FXML/ConfirmWindowProd.fxml"));
        Scene scene = new Scene(confirmRoot);
        confirmWindow.setScene(scene);
        confirmWindow.show();
        product1 = product;



    }

    /**
     *
     * @param mouseEvent delete part from list
     */
    public void yesButClicked(MouseEvent mouseEvent) throws SQLException {

        Inventory.deletePart(part1);
        Inventory.deleteTempPart(part1); //have to delete temp part to in case trying to delete a part when searching other wise it does not show deleted until you type something
        Statement sqlStatement = LogInController.returnConnection().createStatement();
        String sqlCommand = "DELETE FROM `WJ07LG4`.`inHouse_parts`" +
                "WHERE part_id = "+part1.getId()+";";
        sqlStatement.execute(sqlCommand);
        sqlCommand = "DELETE FROM `WJ07LG4`.`outSourced_parts`" +
                "WHERE part_id = "+part1.getId()+";";
        sqlStatement.execute((sqlCommand));
        confirmWindow.close();
    }

    public void yesButClicked2(MouseEvent mouseEvent) throws SQLException {

        //Inventory.deletePart(part1);
        Inventory.deleteTempPart(part1); //have to delete temp part to in case trying to delete a part when searching other wise it does not show deleted until you type something
        confirmWindow.close();
    }

    /**
     *
     * @param mouseEvent closes confirm delete box without deleting
     */
    public void noButClicked(MouseEvent mouseEvent) {
        confirmWindow.close();
    }

    /**
     *
     * @param mouseEvent confirms delete of a product and does it
     */
    public void yesButClickedProd(MouseEvent mouseEvent) throws SQLException {



        Inventory.deleteCar(product1);
        Inventory.deleteTempCar(product1);
        Statement sqlStatement = LogInController.returnConnection().createStatement();
        String sqlCommand = "DELETE FROM `WJ07LG4`.`associated_inHouse_car_parts`" +               //deletes associated parts with product/car
                "WHERE car_id = "+product1.getId()+";";
        sqlStatement.execute(sqlCommand);
        sqlCommand = "DELETE FROM `WJ07LG4`.`associated_outSourced_car_parts`" +
                "WHERE car_id = "+product1.getId()+";";
        sqlStatement.execute((sqlCommand));

        Statement sqlStatement2 = LogInController.returnConnection().createStatement();
        String sqlCommand2 = "DELETE FROM WJ07LG4.Cars WHERE car_id = " + product1.getId()+";";
        sqlStatement2.execute(sqlCommand2);
        confirmWindow.close();
    }

    /**
     *
     * @param mouseEvent closes delete product confirm box without deleting
     */
    public void noButClickedProd(MouseEvent mouseEvent) {
        confirmWindow.close();
    }
}
