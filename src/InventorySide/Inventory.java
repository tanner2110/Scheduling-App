package InventorySide;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 11/11/2020
 * Inventory class is where data on inventory is held and also has methods that modify it.
 */
public class Inventory {

public static ObservableList <Part> partList = FXCollections.observableArrayList();
public static ObservableList<Car> carList = FXCollections.observableArrayList();
public static ObservableList <Part> tempPartList = FXCollections.observableArrayList();
public static ObservableList <Part> tempPartList2 = FXCollections.observableArrayList();
public static ObservableList<Car> tempCarList = FXCollections.observableArrayList();
private static Car updatingCar;
private static Part updatingPart;





    /**
     *
     * @param product can be stored with this method
     */
    public static void storeUpdatingCar(Car product) {

        updatingCar = product;
    }

    /**
     *
     * @return returns stored product.
     */
    public static Car getUpdatingCar(){

        return updatingCar;
    }

    /**
     *
     * @return returns stored part object.
     */
    public static Part getUpdatingPart(){

        return updatingPart;
    }

    /**
     *
     * @param part part object is passed here with this method
     */
    public static void storeUpdatingPart(Part part) {

        updatingPart = part;
    }


    /**
     *
     * @param part is added to list
     */
    public static void addPart(Part part){

        partList.add(part);


    }


    /**
     *
     * @param selectedPart
     */
    public static void deletePart(Part selectedPart){

        partList.remove(selectedPart);

    }

    /**
     *
     * @param part, temp part is added to temp list
     */
    public static void addTempPart(Part part){

        tempPartList.add(part);


    }
    public static void addTempPart2(Part part){

        tempPartList2.add(part);


    }

    /**
     *
     * @param selectedPart, temp part deleted from temp list
     */
    public static void deleteTempPart(Part selectedPart){

        tempPartList.remove(selectedPart);

    }

    public static void deleteTempPart2(Part selectedPart){

        tempPartList2.remove(selectedPart);

    }

    /**
     * clearTempPartList ysed to clear temp part list after uses.
     */
    public static void clearTempPartList() {

        tempPartList.clear();
    }

    public static void clearTempPartList2() {

        tempPartList2.clear();
    }

    public static void clearPartList() {

        partList.clear();
    }

    public static void clearCarList() {

        carList.clear();
    }

    /**
     *
     * @param product added to list
     */
    public static void addCar(Car product){

        carList.add(product);

    }

    /**
     *
     * @param selectedProduct removed from list
     *
     */
    public static void deleteCar(Car selectedProduct){

        carList.remove((selectedProduct));

    }

    /**
     *
     * @param product, temp product added to list
     */
    public static void addTempCar(Car product){

        tempCarList.add(product);

    }

    /**
     *
     * @param selectedProduct, temp product removed from temp list
     *
     */
    public static void deleteTempCar(Car selectedProduct){

        tempCarList.remove((selectedProduct));

    }

    /**
     * clearTempProdList clears temp product list after use.
     */
    public static void clearTempProdList() {
        tempCarList.clear();
    }

    /**
     *
     * @return part list
     */
    public static ObservableList<Part> getAllParts(){
        return partList;

    }

    /**
     *
     * @return temp part list
     */
    public static ObservableList<Part> getAllTempParts(){
        return tempPartList;

    }

    public static ObservableList<Part> getAllTempParts2(){
        return tempPartList2;

    }




    /**
     *
     * @return product list
     */
    public static ObservableList<Car> getAllCars(){

        return carList;

    }

    /**
     *
     * @return temp product list
     */
    public static ObservableList<Car> getAllTempCars(){

        return tempCarList;

    }




}










