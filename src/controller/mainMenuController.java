package controller;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import model.Appointments;
import DAO.appointmentsInteraction;

/**
 * Controller class that controls the main menu
 * @author Alekandra Laskowska
 */
public class mainMenuController implements Initializable{

    Stage stage;
    Parent scene;

        @FXML
        private Button appointmentsButton;

        @FXML
        private Button customersButton;

        @FXML
        private AnchorPane mainMenuForm;

        @FXML
        private Button reportsButton;

        @FXML
        private Button signOutButton;

    /**
     * ActionEvent that occurs when the appointments button is pressed
     * Takes you to the appointments page
     * @param event
     * @throws IOException
     */
        @FXML
        void appointmentsHandler(ActionEvent event) throws IOException {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    /**
     * ActionEvent that occurs when the customers button is pressed
     * Takes you to the customers page
     * @param event
     * @throws IOException
     */
        @FXML
        void customersHandler(ActionEvent event) throws IOException {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    /**
     * ActionEvent that occurs when the reports button is pressed
     * Takes you to the reports page
     * @param event
     * @throws IOException
     */
        @FXML
        void reportsHandler(ActionEvent event) throws IOException {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/reports.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    /**
     * ActionEvent that occurs when the sign out button is pressed
     * Takes you to the login page
     * @param event
     * @throws IOException
     */
        @FXML
        void signOutHandler(ActionEvent event) throws IOException {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

