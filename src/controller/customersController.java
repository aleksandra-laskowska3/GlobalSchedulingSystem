package controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;
import DAO.customersInteraction;
import helper.JDBC;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.net.URL;
import DAO.appointmentsInteraction;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that controls the customer page
  * @author Alekandra Laskowska
 */
public class customersController implements Initializable {
    Stage stage;
    Parent scene;


    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Customers, String> addressColumn;

    @FXML
    private TableColumn<Customers, String> countryColumn;

    @FXML
    private AnchorPane customerRecordsForm;

    @FXML
    private Button customerSignOutButton;

    @FXML
    private TableView<Customers> customerTableView;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Customers, String> firstLevelDivisionColumn;

    @FXML
    private TableColumn<Customers, Integer> idColumn;

    @FXML
    private Button mainMenuButton;

    @FXML
    private TableColumn<Customers, String> nameColumn;

    @FXML
    private TableColumn<Customers, Spring> phoneNumerColumn;

    @FXML
    private TableColumn<Customers, Spring> postalCodeColumn;

    @FXML
    private Button updateButton;

    /**
     * ActionEvent that occurs when the add button is pressed
     * Takes the user to the add customer page
     * @param event
     * @throws IOException
     */
    @FXML
    void addButtonHandler(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));
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
    void customerSignOutButtonHandler(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * ActionEvent that deletes the selected customer
     * Checks if the customer has associated appointments that need to be deleted before the customer can be deleted
     * @param event
     * @throws SQLException
     */
    @FXML
    void deleteButtonHandler(ActionEvent event) throws SQLException {
        Customers selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        int selectedCustomerID = customerTableView.getSelectionModel().getSelectedItem().getCustomer_ID();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a customer to delete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to delete this customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (appointmentsInteraction.getAppByCustID(selectedCustomerID)) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setContentText("The selected customer has scheduled appointments which also will be deleted. Are you sure you want to delete this customer?");
                    Optional<ButtonType> newresult = alert.showAndWait();
                    if (newresult.isPresent() && newresult.get() == ButtonType.OK) {
                        appointmentsInteraction.deleteAppByCustID(selectedCustomerID);
                        customersInteraction.deleteCustomer(selectedCustomer.getCustomer_ID());
                        customerTableView.setItems(customersInteraction.getAllCustomers());
                    }

                }
            }
        }
    }
    /**
     * ActionEvent that occurs when the main menu button is pressed
     * Takes the user to the main menu page
     * @param event
     * @throws IOException
     */
    @FXML
    void mainMenuButtonHandler(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * ActionEvent that occurs when the update button is pressed
     * The selected customer's information is sent to the update customer's page
     * The user is taken to the update costumer's page
     * Error occurs if there is not a selected customer
     * @param event
     * @throws IOException
     */
    @FXML
    void updateButtonHandler(ActionEvent event) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/updateCustomer.fxml"));
            loader.load();

            updateCustomerController ucController = loader.getController();
            ucController.sendCustomer(customerTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a customer to update");
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
        customerTableView.setItems(customersInteraction.getAllCustomers());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("custName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postal_Code"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country_ID"));
        phoneNumerColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        firstLevelDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division_ID"));

    }
}

