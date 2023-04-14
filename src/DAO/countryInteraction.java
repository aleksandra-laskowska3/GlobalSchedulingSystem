package DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import helper.JDBC;
import model.Countries;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Country Interaction class
 * This is used to communicate data between the database and the application
 * @author Alekandra Laskowska
 */
public class countryInteraction {
    /**
     * Method that makes a list of all countries from database
     * @return countriesObservableList
     */
    public static ObservableList<Countries> getCountries() {
        ObservableList<Countries> countriesObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM COUNTRIES";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int country_ID = rs.getInt("Country_ID");
                String country_Name = rs.getString("Country");
                Countries country = new Countries(country_ID, country_Name);
                countriesObservableList.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countriesObservableList;
    }


}
