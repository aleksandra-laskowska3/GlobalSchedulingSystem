package DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import helper.JDBC;
import model.firstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Division Interaction class
 * This is used to communicate data between the database and the application
 * @author Alekandra Laskowska
 */
public class divisionInteraction {
    /**
     * Method that makes a list of all the firstLevelDivisions from the database
     * @return Observable list of all the firstLevelDivisions
     */
    public static ObservableList<firstLevelDivision> getAllFirstLevelDivisions() {
        ObservableList<firstLevelDivision> divisionObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int division_ID = rs.getInt("Division_ID");
                String division_Name = rs.getString("Division_Name");
                int country_ID = rs.getInt("Country_ID");
                firstLevelDivision first_Division = new firstLevelDivision(division_ID, division_Name, country_ID);
                divisionObservableList.add(first_Division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionObservableList;
    }

    /**
     * Method that makes a filtered list of firstLevelDivisions from the database
     * @param country
     * @return filteredDivisions observable list
     */
    public static ObservableList<firstLevelDivision> getFilteredDivisions(int country) {
        ObservableList<firstLevelDivision> filteredDivisions = FXCollections.observableArrayList();
        try {
            String sql = "SELECT first_level_divisions.Division_ID, first_level_divisions.Division, first_level_divisions.Country_ID"
                    + " "
                    + "FROM first_level_divisions "
                    + " "
                    + "WHERE first_level_divisions.Country_ID = ?"
                    ;

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, country);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int division_ID = rs.getInt("Division_ID");
                String division_Name = rs.getString("Division");
                int country_ID = rs.getInt("Country_ID");

                firstLevelDivision first = new firstLevelDivision(division_ID, division_Name, country_ID);
                filteredDivisions.add(first);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredDivisions;
    }
}
