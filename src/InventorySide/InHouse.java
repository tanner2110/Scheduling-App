package InventorySide;

/**
 *
 * @author Tanner
 * @version 1.0
 * InHouse class is the frame for the inhouse part objects
 */
public class InHouse extends Part {


    private int id;
    private  String machineId;
    private  String name;
    private  double price;
    private  int stock;
    private  int min;
    private  int max;



    public InHouse(int id, String name, double price, int stock, int min, int max, String machineId){

        super(id, name, price, stock, min, max);

        this.id = id;
        this.name = name;
        this.price= price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.machineId = machineId;

    }


    /**
     *
     * @param machineId is set with
     */
    public void setMachineId(String machineId){
        this.machineId = machineId;

    }


    /**
     *
     * @return name of object for modify part loadscreen
     */
    public String checkClass(){
        String name = "InHouse";
        return name;
    }


    /**
     *
     * @return machine id
     */
    public String getMachineId(){

        return this.machineId;

    }



}
