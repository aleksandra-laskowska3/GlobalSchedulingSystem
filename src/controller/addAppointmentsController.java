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
import DAO.appointmentsInteraction;
import DAO.contactInteraction;
import model.*;
import DAO.customersInteraction;
import DAO.userInteraction;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

/**
 * Controller class that controls the add appointment page
 * @author Alekandra Laskowska
 */
public class addAppointmentsController implements Initializable {
        Stage stage;
        Parent scene;

        @FXML
        private TextField addAppStart;

        @FXML
        private Button addAppCancelButton;

        @FXML
        private ComboBox<Customers> addAppCustIDComboBox;

        @FXML
        private DatePicker addAppDatePicker;

        @FXML
        private ComboBox<User> addAppUserIDComboBox;

        @FXML
        private ComboBox<Contacts> addAppContactComboBox;


        @FXML
        private TextField addAppDescription;

        @FXML
        private TextField addAppEnd;

        @FXML
        private TextField addAppID;

        @FXML
        private TextField addAppLocation;

        @FXML
        private Button addAppSaveButton;

        @FXML
        private TextField addAppTitle;

        @FXML
        private TextField addAppType;

        @FXML
        private AnchorPane addAppointmentForm;
        /**
         * ActionEvent that occurs when cancel button is pressed
         * Takes the user to the appointments page
         * @param event * @author Alekandra Laskowska
         * @throws IOException
         */
        @FXML
        void addAppCancelButtonHandler(ActionEvent event) throws IOException {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        /**
         * ActionEvent that occurs when save button is pressed
         * Checks if all fields are filled out
         * Adds the new appointment to the database
         * Checks to make sure there is no overlap of appointments
         * Takes the user to the appointments page
         * @param event
         * @throws IOException
         */
        @FXML
        void addAppSaveButtonHandler(ActionEvent event) throws IOException {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                ObservableList<Appointments> getAllAppointments = appointmentsInteraction.getAllAppointments();

                Contacts c = addAppContactComboBox.getValue();
                if (c == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Please select a Contact");
                        alert.show();
                        return;
                }
                Customers custID = addAppCustIDComboBox.getValue();
                if (custID==null){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Please select a Customer ");
                        alert.show();
                        return;
                  }

                User u = addAppUserIDComboBox.getValue();
                if(u==null){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Please select a User ");
                        alert.show();
                        return;
                }

                String title = addAppTitle.getText();
                String description = addAppDescription.getText();
                String location = addAppLocation.getText();
                String type = addAppType.getText();
                LocalDateTime start = null;
                LocalDateTime end = null;
                LocalDate appDate = addAppDatePicker.getValue();
                int customer_ID = custID.getCustomer_ID();
                int user_ID = u.getUser_ID();
                int contact_ID = c.getContact_ID();

                if(appDate==null){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Please pick a date");
                        alert.showAndWait();
                        return;
                }
                if( title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Please fill out all the sections");
                        alert.showAndWait();
                        return;
                }
                try{
                        start = LocalDateTime.of(addAppDatePicker.getValue(), LocalTime.parse(addAppStart.getText(),formatter));


                } catch (DateTimeParseException error){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Invalid start time, check for correct format");
                        alert.showAndWait();
                        return;

                }
                try{
                        end = LocalDateTime.of(addAppDatePicker.getValue(), LocalTime.parse(addAppEnd.getText(), formatter));
                } catch (DateTimeParseException error){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Invalid end time, check for correct format");
                        alert.showAndWait();
                        return;
                }
                if(!checkHours(start, end, appDate)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Hours outside of business hours");
                        alert.showAndWait();
                        return;
                }

                int appointment_ID = -1; //Integer.parseInt(addAppID.getText()); // -1 when adding appointment

                for (Appointments appointment: getAllAppointments){
                        LocalDateTime checkOverlapStart = appointment.getStart();
                        LocalDateTime checkOverlapEnd = appointment.getEnd();


                        if((customer_ID != appointment.getCustomer_ID()) || (appointment_ID == appointment.getAppointment_ID())) {
                               continue;
                        }
                        if((start.isBefore(checkOverlapStart)) && (end.isAfter(checkOverlapEnd))) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Appointment overlaps another existing appointment");
                                alert.showAndWait();
                                return;
                        }
                        if( (start.isAfter(checkOverlapStart)) && (start.isBefore(checkOverlapEnd))){
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Appointment overlaps another existing appointment");
                                alert.showAndWait();
                                return;
                        }
                        if( (end.isAfter(checkOverlapStart)) && (end.isBefore(checkOverlapEnd))){
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Appointment overlaps another existing appointment");
                                alert.showAndWait();
                                return;
                        }
                        if((start.isEqual(checkOverlapStart)) || (end.isEqual(checkOverlapEnd))) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Appointment overlaps another existing appointment");
                                alert.showAndWait();
                                return;
                        }

                }

                appointmentsInteraction.addAppointment( title, description, location, type, start, end, customer_ID, user_ID, contact_ID);

                          stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                          scene = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
                          stage.setScene(new Scene(scene));
                          stage.show();

        }

        /**
         * Boolean method to check if start and end are within business hours
         * @param start start of app
         * @param end end of app
         * @param appDate date of app
         * @return true or false
         */
           public Boolean checkHours(LocalDateTime start, LocalDateTime end, LocalDate appDate){

                ZonedDateTime startBusinessHours = ZonedDateTime.of(appDate, LocalTime.of(8,0), ZoneId.of("America/New_York"));
                ZonedDateTime endBusinessHours = ZonedDateTime.of(appDate, LocalTime.of(22,0), ZoneId.of("America/New_York"));
                LocalDateTime startBH = startBusinessHours.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime endBH = endBusinessHours.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();



                if(start.isBefore(startBH) || start.isAfter(endBH) || end.isBefore(startBH) ||
                        end.isAfter(endBH) || start.isAfter(end)) {
                        return false;
                }
                else {
                        return true;
                }
           }

        /**
         * Initializes the contact, customer, and users list to the combo boxes
         * @param url
         * @param resourceBundle
         */
        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
                addAppContactComboBox.setItems(contactInteraction.getAllContacts());
                addAppCustIDComboBox.setItems(customersInteraction.getAllCustomers());
                addAppUserIDComboBox.setItems(userInteraction.getAllUsers());

    }
}

