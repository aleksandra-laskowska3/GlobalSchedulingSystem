package model;
import java.time.LocalDateTime;

/**
 * Appointments class
 */
public class Appointments {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customer_ID;
    private int user_ID;
    private int contact_ID;

    /**
     * Constructor for a new instance of Appointments
     * @param appointment_ID the appointment ID
     * @param title the title of appointment
     * @param description the description of appointment
     * @param location the location of appointment
     * @param type the type of appointment
     * @param start the start of appointment
     * @param end the end of appointment
     * @param customer_ID the customer ID associated with the appointment
     * @param user_ID the user ID associated with the appointment
     * @param contact_ID the contact ID associated with the appointment
     */
    public Appointments(int appointment_ID, String title, String description, String location, String type, LocalDateTime start,
                        LocalDateTime end, int customer_ID, int user_ID, int contact_ID){
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_ID = customer_ID;
        this.user_ID = user_ID;
        this.contact_ID = contact_ID;

    }

    /**
     * Getter for appointment_ID
     * @return appointment_ID
     */
    public int getAppointment_ID(){
        return appointment_ID;
    }

    /**
     * Setter for appointment_ID
     * @param appointment_ID
     */
    public void setAppointment_ID(int appointment_ID){
        this.appointment_ID = appointment_ID;
    }

    /**
     * Getter for title
     * @return title
     */
    public String getTitle(){
        return title;
    }

    /**
     * Setter for title
     * @param title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Getter for description
     * @return description
     */
    public String getDescription(){
        return description;
    }

    /**
     * Setter for description
     * @param description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Getter for location
     * @return location
     */
    public String getLocation(){
        return location;
    }

    /**
     * Setter for location
     * @param location
     */
    public void setLocation(String location){
        this.location = location;
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
     * @param type
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Getter for start
     * @return start
     */
    public LocalDateTime getStart(){
        return start;
    }

    /**
     * Setter for start
     * @param start
     */
    public void setStart(LocalDateTime start){
        this.start = start;
    }

    /**
     * Getter for end
     * @return end
     */
    public LocalDateTime getEnd(){
        return end;
    }

    /**
     * Setter for end
     * @param end
     */
    public void setEnd(LocalDateTime end){
        this.end = end;
    }

    /**
     * Getter for customer_ID
     * @return customer_ID
     */
    public int getCustomer_ID(){
        return customer_ID;
    }

    /**
     * Setter for customer_ID
     * @param customer_ID
     */
    public void setCustomer_ID(int customer_ID){
        this.customer_ID = customer_ID;
    }

    /**
     * Getter user_ID
     * @return user_ID
     */
    public int getUser_ID(){
        return user_ID;
    }

    /**
     * Setter for user_ID
     * @param user_ID
     */
    public void setUser_ID(int user_ID){
        this.user_ID = user_ID;
    }

    /**
     * Getter for contact_ID
     * @return contact_ID
     */
    public int getContact_ID(){
        return contact_ID;
    }

    /**
     * Setter for contact_ID
     * @param contact_ID
     */
    public void setContact_ID(int contact_ID){
        this.contact_ID = contact_ID;
    }

}
