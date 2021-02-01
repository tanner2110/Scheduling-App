package CustomerAppointmentSide;

/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 12/9/2020
 *
 */


public class Customer {



    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerZipCode;
    private String customerPhone;
    private String customerDivision;




    public Customer(int customerId, String customerName, String customerAddress, String customerZipCode, String customerPhone,
                    String customerDivision) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerZipCode = customerZipCode;
        this.customerPhone = customerPhone;
        this.customerDivision = customerDivision;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerZipCode() {
        return customerZipCode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }


    public String getCustomerDivision() {
        return customerDivision;
    }

    /////////////////////////////////////////////////


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setCustomerZipCode(String customerZipCode) {
        this.customerZipCode = customerZipCode;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }


    public void setCustomerDivision(String customerDivision) {
        this.customerDivision = customerDivision;
    }







}
