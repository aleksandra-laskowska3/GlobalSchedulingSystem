package model;

/**
 * Report1 class
 */
public class Report1 {
    private String type;
    private int totals;

    /**
     * Constructor for new instance of Report1
     * @param type the appointment type
     * @param totals the appointment totals by month and type
     */
    public Report1(String type, int totals){
        this.type = type;
        this.totals = totals;
    }

    /**
     * Getter for type
     * @return type
     */
    public String getType(){
        return type;
    }

    /**
     * Setter for type
     * @param type of appointment
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Getter for totals
     * @return totals
     */
    public int getTotals(){
        return totals;
    }

    /**
     * Setter for totals
     * @param totals of appointments
     */
    public void setTotals(int totals){
        this.totals = totals;
    }
}
