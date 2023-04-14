package DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import helper.JDBC;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contact Interaction class
 * This is used to communicate data between the database and the application
 * @author Alekandra Laskowska
 */
public class contactInteraction {
    /**
     * Method that creates a list of all contacts from the database
     * @return contactsObservableList
     */
    public static ObservableList<Contacts> getAllContacts(){
        ObservableList<Contacts> contactsObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM CONTACTS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int contact_ID = rs.getInt("Contact_ID");
                String contact_Name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contacts contact = new Contacts(contact_ID, contact_Name, email);
                contactsObservableList.add(contact);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return contactsObservableList;
    }

}
