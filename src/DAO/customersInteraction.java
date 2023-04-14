package DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import helper.JDBC;
import model.Customers;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Customers Interaction class
 * This is used to communicate data between the database and the application
 * @author Alekandra Laskowska
 */
public class customersInteraction {
    /**
     * Method to get a list of all the customers from the database
     * @return customersObservableList
     */
    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> customersObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, Country_ID FROM customers, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customer_ID = rs.getInt("Customer_ID");
                String custName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal_Code = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int division_ID = rs.getInt("Division_ID");
                int country_ID = rs.getInt("Country_ID");
                customersObservableList.add(new Customers(customer_ID, custName, address, postal_Code, phone, division_ID, country_ID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersObservableList;
    }

    /**
     * Method that adds a new customer to the database
     * @param custName Customer name
     * @param address Customer address
     * @param postal_Code Customer postal code
     * @param phone Customer phone
     * @param division_ID Customer division ID
     * @return newCustomer
     */
    public static int addCustomer(String custName, String address, String postal_Code, String phone, int division_ID) {
        try {
            String sql = "INSERT INTO Customers ( Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES ( ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, custName);
            ps.setString(2, address);
            ps.setString(3, postal_Code);
            ps.setString(4, phone);
            ps.setInt(5, division_ID);
            int newCustomer = ps.executeUpdate();

            return newCustomer;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Method that updates an existing customer to the database
     * @param custName Customer name
     * @param address Customer address
     * @param postal_Code Customer postal code
     * @param phone Customer phone
     * @param division_ID Customer division ID
     * @return updatedCustomer
     */
    public static int updateCustomer(String custName, String address, String postal_Code, String phone, int division_ID, int customer_ID) {
        try {
            String sql = "UPDATE CUSTOMERS SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, custName);
            ps.setString(2, address);
            ps.setString(3, postal_Code);
            ps.setString(4, phone);
            ps.setInt(5, division_ID);
            ps.setInt(6, customer_ID);
            int updatedCustomer = ps.executeUpdate();

            return updatedCustomer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Method that deletes an existing customer
     * @param customer_ID Customer ID
     * @return deletedCustomer
     */
    public static int deleteCustomer(int customer_ID) {
        try {
            String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customer_ID);
            int deletedCustomer = ps.executeUpdate();

            return deletedCustomer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

}
