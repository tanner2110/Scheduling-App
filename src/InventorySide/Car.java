package InventorySide;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Tanner
 * @version 1.0
 * Product, frame for product object
 */


public class Car {

    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    public Car(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }


    /**
     * @param id of product
     */
    public void setId(int id) {

      this.id = id;

    }

    /**
     * @param name of product
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * @param price set amount
     */
    public void setPrice(double price) {

        this.price = price;

    }

    /**
     * @param stock set amount
     */
    public void setStock(int stock) {

        this.stock = stock;

    }

    /**
     * @param min product number
     */
    public void setMin(int min) {

        this.min = min;

    }

    /**
     * @param max product number
     */
    public void setMax(int max) {

        this.max = max;

    }



    /**
     * @return id of product
     */
    public int getId() {

       return id;

    }

    /**
     * @return name of product
     */
    public String getName() {

        return name;

    }

    /**
     * @return price of product
     */
    public double getPrice() {


        return price;

    }

    /**
     * @return number of in stock product
     */
    public int getStock() {

        return stock;

    }

    /**
     * @return min product number
     */
    public int getMin() {


        return min;

    }

    /**
     * @return max product number
     */
    public int getMax() {


        return max;

    }

    /**
     * @param parts adds associated parts to the products list
     */
    public void addAssociatedParts(ObservableList<Part> parts) {

        for (Part part : parts) {
            associatedParts.add(part);
        }
    }



    public void clearAssociatedParts(){
        associatedParts.clear();
    }


    /**
     *
     * @return list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts (){


    return associatedParts;

}

    /**
     *
     * @return true if product has assocated parts
     */
    public boolean checkForParts(){

            if(associatedParts.size() > 0){
                System.out.println(associatedParts.size());
                 return true;
        }//end if
        else return false;
}


}