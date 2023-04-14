package model;

/**
 * Countries class
 */
public class Countries {
    private int country_ID;
    private String country_Name;

    /**
     * Constructor for a new instance of Countries
     * @param country_ID the country's ID
     * @param country_Name the country's name
     */
    public Countries(int country_ID, String country_Name){
        this.country_ID = country_ID;
        this.country_Name = country_Name;
    }

    /**
     * Getter for country_ID
     * @return country_ID
     */
    public int getCountry_ID(){
        return country_ID;
    }

    /**
     * Setter for country_ID
     * @param country_ID
     */
    public void setCountry_ID(int country_ID){
        this.country_ID = country_ID;
    }

    /**
     * Getter for country_Name
     * @return country_Name
     */
    public String getCountry_Name(){
        return country_Name;
    }

    /**
     * Setter for country_Name
     * @param country_Name
     */
    public void setCountry_Name(String country_Name){
        this.country_Name = country_Name;
    }

    /**
     * Method that changes the format of country_ID and country_Name for the combobox lists
     * @return country_ID - country_Name
     */
    @Override
    public String toString() {
        return  country_ID +
                " - " + country_Name;
    }
}
