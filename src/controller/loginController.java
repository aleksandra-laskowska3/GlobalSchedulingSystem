package controller;

import DAO.appointmentsInteraction;
import DAO.userInteraction;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Appointments;
import model.User;

/**
 * Controller class that controls the login page
 *  @author Alekandra Laskowska
 */
public class loginController implements Initializable {

        Stage stage;
        Parent scene;
        private ResourceBundle langdictionary = ResourceBundle.getBundle("language/lang");

        @FXML
        private Label currentLocationLabel;

        @FXML
        private Label locationLabel;

        @FXML
        private AnchorPane loginForm;

        @FXML
        private Label loginLabel;

        @FXML
        private TextField loginPassword;

        @FXML
        private TextField loginUserID;

        @FXML
        private Label passwordLabel;

        @FXML
        private Button signInButton;

        @FXML
        private Label userIDLabel;

        private String alertTitle;
        private String alertContext;

        /**
         * ActionEvent occurs when the sign in button is pressed
         * Check if the correct username and password is entered
         * Logs every sign in attempt
         * If correct login credentials are entered there will be a pop-up to notify the user if there are any appointments
         * in the next 15 min, then will take user to the main menu
         * @param event
         * @throws IOException
         */
        @FXML
        void signInHandler(ActionEvent event) throws IOException {
                String userName = loginUserID.getText();
                String passWord = loginPassword.getText();
                int user_ID = userInteraction.checkUser(userName, passWord);

                FileWriter writer = new FileWriter("login_activity.txt", true);
                PrintWriter outputFile = new PrintWriter(writer);

                if (user_ID > 0) {
                        ObservableList<Appointments> getAllAppointments = appointmentsInteraction.getAllAppointments();
                        LocalDateTime currentTimePlus15Min = LocalDateTime.now().plusMinutes(15);
                        LocalDateTime now = LocalDateTime.now();
                        LocalDateTime startTime;
                        int appointmentID = 0;
                        boolean found = false;

                        for(Appointments appointments : getAllAppointments){
                                if(appointments.getUser_ID()!=user_ID){
                                       continue;
                                }
                                startTime = appointments.getStart();
                                if(startTime.isEqual(now) || (startTime.isAfter(now) && startTime.isBefore(currentTimePlus15Min)) || startTime.isEqual(currentTimePlus15Min)){
                                        appointmentID = appointments.getAppointment_ID();
                                        found = true;
                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                        alert.setTitle("Warning");
                                        alert.setContentText("Appointment within 15min, Appointment ID: " + appointmentID +" at: " + startTime);
                                        alert.showAndWait();
                                }

                        }
                        if(!found) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Confirmation");
                                alert.setContentText("There are no appointments in the next 15 minutes");
                                alert.showAndWait();
                        }
                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();

                        outputFile.print("Username: " + userName + ", login successful at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
                }
                else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Wrong Username or Password");
                        alert.showAndWait();

                        outputFile.print("Username: " + userName + ", login unsuccessful at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
                }
                outputFile.close();
        }

        /**
         * Initializes as the application starts and gets the locale information
         * Sets the current location on the screen
         * @param url
         * @param rb
         */
        @Override
        public void initialize(URL url, ResourceBundle rb) {
                Locale locale = Locale.getDefault();
                Locale.setDefault(locale);

                ZoneId zone = ZoneId.systemDefault();

                currentLocationLabel.setText(ZoneId.systemDefault().toString());
                rb = ResourceBundle.getBundle("language/lang", Locale.getDefault());
                loginLabel.setText(rb.getString("Login"));
                userIDLabel.setText(rb.getString("User_ID"));
                passwordLabel.setText(rb.getString("Password"));
                locationLabel.setText(rb.getString("Location"));
                signInButton.setText(rb.getString("Sign_In"));
                alertTitle = rb.getString("Error");
                alertContext = rb.getString("Wrong");

                }
        }


