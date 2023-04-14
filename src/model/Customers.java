package model;

/**
 * Customers class
 */
public class Customers {
    private int customer_ID;
    private String custName;
    private String address;
    private String postal_Code;
    private String phone;
    private int division_ID;
    private int country_ID;

    /**
     * Constructor for a new instance of Customers
     * @param customer_ID the ID of the customer
     * @param custName the name of the customer
     * @param address the address of the customer
     * @param postal_Code the postal code of the customer
     * @param phone the phone number of the customer
     * @param division_ID the division ID of the customer
     * @param country_ID the country ID of the customer
     */
    public Customers(int customer_ID, String custName, String address, String postal_Code, String phone, int division_ID, int country_ID) {
        this.customer_ID = customer_ID;
        this.custName = custName;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;
        this.division_ID = division_ID;
        this.country_ID = country_ID;
    }

    /**
     * Getter for customer_ID
     * @return customer_ID
     */
    public int getCustomer_ID() {
        return customer_ID;
    }

    /**
     * Setter for customer_ID
     * @param customer_ID
     */
    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    /**
     * Getter for custName
     * @return custName
     */
    public String getCustName() {
        return custName;
    }

    /**
     * Setter for custName
     * @param custName
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * Getter for address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for postal_Code
     * @return postal_Code
     */
    public String getPostal_Code() {
        return postal_Code;
    }

    /**
     * Setter for postal_Code
     * @param postal_Code
     */
    public void setPostal_Code(String postal_Code) {
        this.postal_Code = postal_Code;
    }

    /**
     * Getter for phone
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for phone
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for division_ID
     * @return division_ID
     */
    public int getDivision_ID() {
        return division_ID;
    }

    /**
     * Setter for division_ID
     * @param division_ID
     */
    public void setDivision_ID(int division_ID) {
        this.division_ID = division_ID;
    }

    /**
     * Getter for country_ID
     * @return country_ID
     */
    public int getCountry_ID() {
        return country_ID;
    }

    /**
     * Setter for country_ID
     * @param country_ID
     */
    public void setCountry_ID(int country_ID) {
        this.country_ID = country_ID;
    }

    /**
     * Method to change the format of customer_ID and custName for the combobox lists
     * @return customer_ID - custName
     */
    @Override
    public String toString() {
        return customer_ID
                + " - "
                + custName;
    }
}

