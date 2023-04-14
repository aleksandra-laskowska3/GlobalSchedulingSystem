package model;

/**
 * First level division class
 */
public class firstLevelDivision {
    private int division_ID;
    private String division_Name;
    private int country_ID;

    /**
     * Constructor for a new instance of firstLevelDivision
     * @param division_ID the ID of the firstLevelDivision
     * @param division_Name the name of the firstLevelDivision
     * @param country_ID the country_ID associated with the firstLevelDivision
     */
    public firstLevelDivision(int division_ID, String division_Name, int country_ID){
        this.division_ID = division_ID;
        this.division_Name = division_Name;
        this.country_ID = country_ID;
    }

    /**
     * Getter for division_ID
     * @return division_ID
     */
    public int getDivision_ID(){
        return division_ID;
    }

    /**
     * Setter for division_ID
     * @param division_ID
     */
    public void setDivision_ID(int division_ID){
        this.division_ID = division_ID;
    }

    /**
     * Getter for division_Name
     * @return division_Name
     */
    public String getDivision_Name(){
        return division_Name;
    }

    /**
     * Setter for division_Name
     * @param division_Name
     */
    public void setDivision_Name(String division_Name){
        this.division_Name = division_Name;
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
     * Method that changes the format of division_ID and division_Name for combobox list
     * @return division_ID - division_Name
     */
    @Override
    public String toString() {
        return division_ID
        + " - "
        + division_Name;
    }
}
