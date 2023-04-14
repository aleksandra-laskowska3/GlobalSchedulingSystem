package controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointments;
import DAO.appointmentsInteraction;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that controls the appointment page
  * @author Alekandra Laskowska
 */
public class appointmentsController implements Initializable {

        Stage stage;
        Parent scene;



        @FXML
        private Button addAppointmentButton;

        @FXML
        private RadioButton allAppRadioButton;

        @FXML
        private ToggleGroup appButtons;

        @FXML
        private Button appMainMenuButton;

        @FXML
        private RadioButton appMonthRadioButton;

        @FXML
        private Button appSignOutButton;

        @FXML
        private RadioButton appWeekRadioButton;

        @FXML
        private TableColumn<Appointments, Integer> appointmentIDColumn;

        @FXML
        private TableView<Appointments> appointmentTableView;

        @FXML
        private AnchorPane appointmentsForm;

        @FXML
        private TableColumn<Appointments, String> contactColumn;

        @FXML
        private TableColumn<Appointments, Integer> customerIDColumn;

        @FXML
        private Button deleteAppointmentButton;

        @FXML
        private TableColumn<Appointments, String> descriptionColumn;

        @FXML
        private TableColumn<Appointments, ZonedDateTime> endColumn;

        @FXML
        private TableColumn<Appointments, String> locationColumn;

        @FXML
        private TableColumn<Appointments, ZonedDateTime> startColumn;

        @FXML
        private TableColumn<Appointments, String> titleColumn;

        @FXML
        private TableColumn<Appointments, String> typeColumn;

        @FXML
        private Button updateAppointmentButton;

        @FXML
        private TableColumn<Appointments, Integer> userIDColumn;

    /**
     * ActionEvent that occurs when the add button is pressed
     * Takes the user to the add appointment page
     * @param event
     * @throws IOException
     */
        @FXML
        void addAppointmentButtonHandler(ActionEvent event) throws IOException {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/addAppointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    /**
     * ActionEvent that occurs when the main menu button is pressed
     * Takes the user to the main menu page
     * @param event
     * @throws IOException
     */
        @FXML
        void appMainMenuButtonHandler(ActionEvent event) throws IOException {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    /**
     * ActionEvent that occurs when the sign out button is pressed
     * Takes the user to the login page
     * @param event
     * @throws IOException
     */
        @FXML
        void appSignOutButtonHandler(ActionEvent event) throws IOException {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    /**
     * ActionEvent that occurs when the delete button is pressed
     * Checks if an appointment has been selected for deletion
     * Deletes the selected appointment
     * @param event
     */
        @FXML
        void deleteAppointmentButtonHandler(ActionEvent event) {
            Appointments selectedApp = appointmentTableView.getSelectionModel().getSelectedItem();
            int deletedAppID = appointmentTableView.getSelectionModel().getSelectedItem().getAppointment_ID();
            String deletedAppType = appointmentTableView.getSelectionModel().getSelectedItem().getType();
            if(selectedApp==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No appointment has been selected for deletion");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Are you sure you want to delete this appointment?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    appointmentsInteraction.deleteAppointment(selectedApp.getAppointment_ID());
                    Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    newAlert.setTitle("Confirmation");
                    newAlert.setContentText("You have successfully deleted the appointment with ID: " + deletedAppID + " and type: " + deletedAppType);
                    newAlert.showAndWait();
                    appointmentTableView.setItems(appointmentsInteraction.getAllAppointments());
                }

            }
        }
    /**
     * ActionEvent that occurs when all appointments radio button is pressed
     * Sets the tableview to display all the appointments
     * @param event
     */
        @FXML
        void switchToAll(ActionEvent event) {
                appointmentTableView.setItems(appointmentsInteraction.getAllAppointments());
        }
    /**
     * ActionEvent that occurs when month appointments radio button is pressed
     * Sets the tableview to display the appointments that occur this month
     * @param event
     */
        @FXML
        void switchToMonth(ActionEvent event) {
                appointmentTableView.setItems(appointmentsInteraction.getMonthApps());
        }
    /**
     * ActionEvent that occurs when week radio button is pressed
     * Sets the tableview to display the appointments that occur this week
     * @param event
     */
        @FXML
        void switchToWeek(ActionEvent event) {
            appointmentTableView.setItems(appointmentsInteraction.getWeekApps());
        }

    /**
     * ActionEvent that occurs when update button is pressed
     * Sends the selected appointment to the update appointment page
     * Takes the user to the update appointment page
     * @param event
     * @throws IOException
     */
        @FXML
        void updateAppointmentButtonHandler(ActionEvent event) throws IOException {
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/updateAppointments.fxml"));
                loader.load();

                updateAppointmentsController uaController = loader.getController();
                uaController.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();

            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Select an appointment to update");
                alert.showAndWait();
            }
        }

    /**
     * Initializes data to the correct columns
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentTableView.setItems(appointmentsInteraction.getAllAppointments());
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact_ID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
    }
}


