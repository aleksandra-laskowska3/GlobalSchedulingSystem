package model;

/**
 * User Class
 */
public class User {
    private int user_ID;
    private String user_Name;
    private String password;

    /**
     * Constructor for a new instance of User
     * @param user_ID The user's ID
     * @param user_Name The username of the user
     * @param password The users password
     */
    public User(int user_ID, String user_Name, String password) {
        this.user_ID = user_ID;
        this.user_Name = user_Name;
        this.password = password;
    }

    /**
     * Getter for user_ID
     * @return user_ID the user's ID
     */
    public int getUser_ID() {
        return user_ID;
    }

    /**
     * Setter for user_ID
     * @param user_ID the user's ID
     */
    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    /**
     * Getter for user_Name
     * @return user_Namw
     */
    public String getUser_Name() {
        return user_Name;
    }

    /**
     * Setter for user_Name
     * @param user_Name the user's user_Name
     */
    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    /**
     * Getter for password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the user's password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method that changes the format of user_ID and user_Name for combobox lists
     * @return user_ID - user_Name as a string
     */
    @Override
    public String toString() {
        return user_ID
                + " - "
                + user_Name;
    }
}
