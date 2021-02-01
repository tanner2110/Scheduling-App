package InventorySide;


/**
 *
 * @author Tanner
 * @version 1.0
 * OutSourced is the frame for the outsourced part object
 */
public class OutSourced extends Part {

    private String companyName;
    private int id;
    private  String name;
    private  double price;
    private  int stock;
    private  int min;
    private  int max;


    public OutSourced(int id, String name,
                      double price, int stock, int min, int max, String companyName){

        super(id, name, price, stock, min, max);


        this.id = id;
        this.name = name;
        this.price= price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.companyName = companyName;


    }

    /**
     *
     * @return name of class for modify part screen
     */
    public String checkClass(){
        String name = "OutSourced";
        return name;
    }



    /**
     *
     * @param companyName is set
     */
    public void setCompanyName(String companyName){

        this.companyName = companyName;

    }

    /**
     *
     * @return company name
     */
    public  String getCompanyName(){

        return companyName;

    }

}