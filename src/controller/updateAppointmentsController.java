package controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import DAO.contactInteraction;
import DAO.appointmentsInteraction;
import DAO.customersInteraction;
import DAO.userInteraction;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

/**
 * Controller class that controls updating appointments
 * @author Alekandra Laskowska
 */
public class updateAppointmentsController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private ComboBox<Contacts> updateAppContactCombobox;

    @FXML
    private ComboBox<Customers> updateAppCustomerCombobox;

    @FXML
    private DatePicker updateAppDatePicker;

    @FXML
    private TextField updateAppEnd;

    @FXML
    private TextField updateAppStart;

    @FXML
    private ComboBox<User> updateAppUserCombobox;

    @FXML
    private Button updateAppCancelButton;

    @FXML
    private TextField updateAppDescription;

    @FXML
    private TextField updateAppID;

    @FXML
    private TextField updateAppLocation;

    @FXML
    private Button updateAppSaveButton;


    @FXML
    private TextField updateAppTitle;

    @FXML
    private TextField updateAppType;


    @FXML
    private AnchorPane updateAppointmentForm;

    /**
     * ActionEvent that occurs when the cancel button is pressed
     * Takes you back to the appointments form
     * @param event
     * @throws IOException
     */
    @FXML
    void updateAppCancelButtonHandler(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * ActionEvent that occurs when the save button is pressed
     * Checks to make sure that all fields have been filled out correctly
     * Updates the appointment and saves it to the database
     * Takes you back to the appointments form
     * @param event
     * @throws IOException
     */
    @FXML
    void updateAppSaveButtonHandler(ActionEvent event) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        ObservableList<Appointments> getAllAppointments = appointmentsInteraction.getAllAppointments();

        Contacts c = updateAppContactCombobox.getValue();
        if (c == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select the Contact");
            alert.show();
            return;
        }
        Customers custID = updateAppCustomerCombobox.getValue();
        if (custID == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a Customer ");
            alert.show();
            return;
        }

        User u = updateAppUserCombobox.getValue();
        if (u == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a User ");
            alert.show();
            return;
        }


        int appointment_ID = Integer.parseInt(updateAppID.getText());
        String title = updateAppTitle.getText();
        String description = updateAppDescription.getText();
        String location = updateAppLocation.getText();
        String type = updateAppType.getText();
        LocalDateTime start = null;
        LocalDateTime end = null;
        LocalDate appDate = updateAppDatePicker.getValue();
        int customer_ID = custID.getCustomer_ID();
        int user_ID = u.getUser_ID();
        int contact_ID = c.getContact_ID();

        if (title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill out all the sections");
            alert.showAndWait();
            return;
        }
        if (appDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please pick a date");
            alert.showAndWait();
            return;
        }
        try {
            start = LocalDateTime.of(updateAppDatePicker.getValue(), LocalTime.parse(updateAppStart.getText(), formatter));


        } catch (DateTimeParseException error) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid start time, check for correct format");
            alert.showAndWait();
            return;

        }
        try {
            end = LocalDateTime.of(updateAppDatePicker.getValue(), LocalTime.parse(updateAppEnd.getText(), formatter));
        } catch (DateTimeParseException error) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid end time, check for correct format");
            alert.showAndWait();
            return;
        }
        if (!checkHours(start, end, appDate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Hours outside of business hours");
            alert.showAndWait();
            return;
        }

        for (Appointments appointment : getAllAppointments) {
            LocalDateTime checkOverlapStart = appointment.getStart();
            LocalDateTime checkOverlapEnd = appointment.getEnd();


            if ((customer_ID != appointment.getCustomer_ID()) || (appointment_ID == appointment.getAppointment_ID())) {
                continue;
            }
            if ((start.isBefore(checkOverlapStart)) && (end.isAfter(checkOverlapEnd))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Appointment overlaps another existing appointment");
                alert.showAndWait();
                return;
            }
            if ((start.isAfter(checkOverlapStart)) && (start.isBefore(checkOverlapEnd))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Appointment overlaps another existing appointment");
                alert.showAndWait();
                return;
            }
            if ((end.isAfter(checkOverlapStart)) && (end.isBefore(checkOverlapEnd))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Appointment overlaps another existing appointment");
                alert.showAndWait();
                return;
            }
            if ((start.isEqual(checkOverlapStart)) || (end.isEqual(checkOverlapEnd))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Appointment overlaps another existing appointment");
                alert.showAndWait();
                return;
            }

        }


        appointmentsInteraction.updateAppointment(appointment_ID, title, description, location, type, start, end, customer_ID, user_ID, contact_ID);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Boolean method that checks to see if the start and end of the appointment are within business hours
     * @param start start of the appointment
     * @param end end of the appointment
     * @param appDate the date of the appointment
     * @return true or false
     */
        public Boolean checkHours (LocalDateTime start, LocalDateTime end, LocalDate appDate){

            ZonedDateTime startBusinessHours = ZonedDateTime.of(appDate, LocalTime.of(8, 0), ZoneId.of("America/New_York"));
            ZonedDateTime endBusinessHours = ZonedDateTime.of(appDate, LocalTime.of(22, 0), ZoneId.of("America/New_York"));
            LocalDateTime startBH = startBusinessHours.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime endBH = endBusinessHours.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();


            if (start.isBefore(startBH) || start.isAfter(endBH) || end.isBefore(startBH) ||
                    end.isAfter(endBH) || start.isAfter(end)) {
                return false;
            } else {
                return true;
            }
        }

    /**
     * Method that take the selected appointment's data and sets it into the fields on the update page
     * @param selectedAppointment the selected appointment
     */
        public void sendAppointment (Appointments selectedAppointment){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            updateAppDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
            updateAppID.setText(String.valueOf(selectedAppointment.getAppointment_ID()));
            updateAppTitle.setText(selectedAppointment.getTitle());
            updateAppDescription.setText(selectedAppointment.getDescription());
            updateAppLocation.setText(selectedAppointment.getLocation());
            updateAppType.setText(selectedAppointment.getType());
            updateAppStart.setText(String.valueOf(selectedAppointment.getStart().toLocalTime()));
            updateAppEnd.setText(String.valueOf(selectedAppointment.getEnd().toLocalTime()));
            updateAppContactCombobox.setItems(contactInteraction.getAllContacts());
            for (Contacts c : updateAppContactCombobox.getItems()) {
                if (c.getContact_ID() == selectedAppointment.getContact_ID()) {
                    updateAppContactCombobox.getSelectionModel().select(c);
                    break;
                }
            }
            updateAppCustomerCombobox.setItems(customersInteraction.getAllCustomers());
            for (Customers cust : updateAppCustomerCombobox.getItems()) {
                if (cust.getCustomer_ID() == selectedAppointment.getCustomer_ID()) {
                    updateAppCustomerCombobox.getSelectionModel().select(cust);
                    break;
                }
            }
            updateAppUserCombobox.setItems(userInteraction.getAllUsers());
            for (User u : updateAppUserCombobox.getItems()) {
                if (u.getUser_ID() == selectedAppointment.getUser_ID()) {
                    updateAppUserCombobox.getSelectionModel().select(u);
                    break;
                }
            }
        }

        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){

        }
    }

