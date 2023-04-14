package DAO;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import helper.JDBC;
import model.Appointments;
import model.Report1;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Locale;

/**
 * Appointment Interaction class
 * This is used to communicate data between the database and the application
 * @author Alekandra Laskowska
 */
public class appointmentsInteraction {
    /**
     * Method that creates a list of all appointments from database
     * @return appointmentsObservableList
     */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM APPOINTMENTS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customer_ID = rs.getInt("Customer_ID");
                int user_ID = rs.getInt("User_ID");
                int contact_ID = rs.getInt("Contact_ID");
                Appointments appointments = new Appointments(appointment_ID, title, description, location, type, start, end, customer_ID, user_ID, contact_ID);
                appointmentsObservableList.add(appointments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsObservableList;
    }

    /**
     * Method that creates a list of appointments that occur this month
     * @return  monthObservableList
     */
    public static ObservableList<Appointments> getMonthApps() {
        ObservableList<Appointments> monthObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE MONTH(start) = month(curdate())";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customer_ID = rs.getInt("Customer_ID");
                int user_ID = rs.getInt("User_ID");
                int contact_ID = rs.getInt("Contact_ID");
                Appointments monthApps = new Appointments(appointment_ID, title, description, location, type, start, end, customer_ID, user_ID, contact_ID);
                monthObservableList.add(monthApps);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthObservableList;
    }

    /**
     * Method that creates a list of appointments that occur this week
     * @return weekObservableList
     */
    public static ObservableList<Appointments> getWeekApps() {
        ObservableList<Appointments> weekObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE start >= curdate() AND start <= DATE_ADD(curdate(), INTERVAL 7 DAY)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customer_ID = rs.getInt("Customer_ID");
                int user_ID = rs.getInt("User_ID");
                int contact_ID = rs.getInt("Contact_ID");
                Appointments weekApps = new Appointments(appointment_ID, title, description, location, type, start, end, customer_ID, user_ID, contact_ID);
                weekObservableList.add(weekApps);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weekObservableList;
    }

    /**
     * Method that adds a new appointment to the database
     * @param title Title of the appointment
     * @param description Description of the appointment
     * @param location Location of the appointment
     * @param type Type of the appointment
     * @param start Start of the appointment
     * @param end End of the appointment
     * @param customer_ID Customer ID associated with the appointment
     * @param user_ID User ID associated with the appointment
     * @param contact_ID Contact ID associated with the appointment
     * @return newAppointment
     */
    public static int addAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customer_ID, int user_ID, int contact_ID) {
        try {
            String sql = "INSERT INTO APPOINTMENTS ( Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customer_ID);
            ps.setInt(8, user_ID);
            ps.setInt(9, contact_ID);
            int newAppointment = ps.executeUpdate();

            return newAppointment;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Method that updates an existing appointment
     * @param title Title of the appointment
     * @param description Description of the appointment
     * @param location Location of the appointment
     * @param type Type of the appointment
     * @param start Start of the appointment
     * @param end End of the appointment
     * @param customer_ID Customer ID associated with the appointment
     * @param user_ID User ID associated with the appointment
     * @param contact_ID Contact ID associated with the appointment
     * @return updatedAppointment
     */
    public static int updateAppointment(int appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customer_ID, int user_ID, int contact_ID) {
        try {
            String sql = "UPDATE APPOINTMENTS SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customer_ID);
            ps.setInt(8, user_ID);
            ps.setInt(9, contact_ID);
            ps.setInt(10, appointment_ID);
            int updatedAppointment = ps.executeUpdate();

            return updatedAppointment;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Method that deletes the selected appointment based on the Appointment ID
     * @param appointment_ID
     * @return deleteAppointment
     */
    public static int deleteAppointment(int appointment_ID) {
        try {
            String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointment_ID);
            int deleteAppointment = ps.executeUpdate();

            return deleteAppointment;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    /**
     * Boolean method that checks for any appointments associated with the Customer ID
     * @param customer_ID Customer ID associated with the appointment
     * @return true or false
     */
    public static Boolean getAppByCustID(int customer_ID) {
        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE Customer_ID= " + customer_ID;
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method that makes a list of appointments associated with the selected Customer ID
     * @param customer_ID Customer ID associated with the appointment
     * @return appsByCustIDList
     */
    public static ObservableList<Appointments> getAppListByCustID(int customer_ID) {
        ObservableList<Appointments> appsByCustIDList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE Customer_ID= " + customer_ID;
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int user_ID = rs.getInt("User_ID");
                int contact_ID = rs.getInt("Contact_ID");
                Appointments newAppsList = new Appointments(appointment_ID, title, description, location, type, start, end, customer_ID, user_ID, contact_ID);
                appsByCustIDList.add(newAppsList);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appsByCustIDList;
    }

    /**
     * Method that deletes appointments associated with the customer ID
     * @param customer_ID Customer ID associated with the appointment
     * @throws SQLException
     */
    public static void deleteAppByCustID(int customer_ID) throws SQLException {
        String sql = "DELETE FROM client_schedule.appointments WHERE Customer_ID= " + customer_ID;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.executeUpdate();


    }

    /**
     * Boolean method that checks for any appointments associated with the Contact ID
     * @param contact_ID Contact ID associated with the appointment
     * @return true or false
     */
    public static Boolean getAppByContactID(int contact_ID) {
        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE Contact_ID= " + contact_ID;
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * Method that creates an appointment list based on contact ID
     * @param contact_ID Contact ID associated with the appointment
     * @return appsByContactIDList
     */
    public static ObservableList<Appointments> getAppListByContactID(int contact_ID) {
        ObservableList<Appointments> appsByContactIDList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE Contact_ID= " + contact_ID;
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customer_ID = rs.getInt("Customer_ID");
                int user_ID = rs.getInt("User_ID");
                Appointments newAppsList = new Appointments(appointment_ID, title, description, location, type, start, end, customer_ID, user_ID, contact_ID);
                appsByContactIDList.add(newAppsList);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appsByContactIDList;


    }

    /**
     * Method that creates a list of month names based on when the appointments are happening
     * @return monthList
     */
    public static ObservableList<Month> getMonthList() {
        ObservableList<Month> monthList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT MONTHNAME(Start) AS Month FROM APPOINTMENTS GROUP BY Month";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String month = rs.getString("Month");
                monthList.add(Month.valueOf(month.toUpperCase()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthList;
    }

    /**
     * Method that generates the types and totals of appointments based on the month they occur in
     * @param selectedMonth The month that is selected to filter appointments by
     * @return totalStrings
     */
    public static ObservableList<Report1> totalsByTypeAndMonth(Month selectedMonth) {
        ObservableList<Report1> totalStrings = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Type, COUNT(Type) AS totals, MONTHNAME(Start) AS month FROM APPOINTMENTS WHERE MONTHNAME(Start)=? GROUP BY Type;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(selectedMonth));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                Integer count = rs.getInt("totals");
                Report1 reports = new Report1(type, count);
                totalStrings.add(reports);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalStrings;

    }

    }
