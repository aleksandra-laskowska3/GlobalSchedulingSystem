package model;

/**
 * Contact class
 */
public class Contacts {
    private int contact_ID;
    private String contact_Name;
    private String email;

    /**
     * Constructor for a new instance of Contact
     * @param contact_ID the contact's ID
     * @param contact_Name the contact's name
     * @param email the contact's email
     */
    public Contacts(int contact_ID, String contact_Name, String email){
        this.contact_ID = contact_ID;
        this.contact_Name = contact_Name;
        this.email = email;
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

    /**
     * Getter for contact_Name
     * @return contact_Name
     */
    public String getContact_Name(){
        return contact_Name;
    }

    /**
     * Setter for contact_Name
     * @param contact_Name
     */
    public void setContact_Name(String contact_Name){
        this.contact_Name = contact_Name;
    }
    /**
     * Getter for email
     * @return email
     */
    public String getEmail(){
        return email;
    }

    /**
     * Setter for email
     * @param email
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Method to change the format of contact_ID and contact_Name for the combobox lists
     * @return contact_ID - contact_Name
     */
    @Override
    public String toString() {
        return contact_ID
                + " - "
                + contact_Name;
    }
}
