package controller;
import DAO.customersInteraction;
import helper.JDBC;
import javafx.collections.FXCollections;
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
import model.Countries;
import model.firstLevelDivision;
import DAO.countryInteraction;
import DAO.divisionInteraction;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller class that controls the add customer page
  * @author Alekandra Laskowska
 */
public class addCustomerController implements Initializable {
    Stage stage;
    Parent scene;


    @FXML
    private TextField addAddressTxt;

    @FXML
    private Button addCancelButton;

    @FXML
    private ComboBox<Countries> addCountryComboBox;

    @FXML
    private AnchorPane addCustomerForm;

    @FXML
    private ComboBox<firstLevelDivision> addDivisionComboBox;

    @FXML
    private TextField addNameTxt;

    @FXML
    private TextField addPhoneNumberTxt;

    @FXML
    private TextField addPostalCodeTxt;

    @FXML
    private Button addSaveButton;

    /**
     * ActionEvent that occurs when cancel button is pressed
     * Takes the user to customer's page
     * @param event
     * @throws IOException
     */
    @FXML
    void addCancelButtonHandler(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * ActionEvent that occurs when the save button is pressed
     * Checks to make sure all the fields are filled out and are correct
     * Adds the customer to the database
     * Takes the user to the customer's page
     * @param event
     * @throws IOException
     */
    @FXML
    void addSaveButtonHandler(ActionEvent event) throws IOException {

        firstLevelDivision division = addDivisionComboBox.getValue();
        if(division== null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select the Division");
            alert.show();
            return;
        }
        int division_ID = division.getDivision_ID();
        String custName = addNameTxt.getText();
        String address = addAddressTxt.getText();
        String postal_Code = addPostalCodeTxt.getText();
        String phone = addPhoneNumberTxt.getText();

        if (custName.isBlank() || address.isBlank() || postal_Code.isBlank() || phone.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill out all the sections");
            alert.showAndWait();
            return;
        }

        customersInteraction.addCustomer(custName, address, postal_Code, phone, division_ID);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * ActionEvent that uses a lambda expression to filter the firstLevelDivision based on the country that is picked
     * @param event
     * @throws IOException
     */
    @FXML
    void countryComboBox(ActionEvent event) throws IOException {
        Countries c = addCountryComboBox.getValue();
        ObservableList<firstLevelDivision> filteredDivisions = divisionInteraction.getFilteredDivisions(c.getCountry_ID());
        ObservableList<firstLevelDivision> fDivisions = FXCollections.observableArrayList();
        //Lambda #3
        filteredDivisions.forEach(div -> {
            fDivisions.add(div);
        });
        addDivisionComboBox.setItems(fDivisions);
    }

    @FXML
    void divisionComboBox(ActionEvent event) throws IOException {

    }

    /**
     * Initializes the list of countries in the country combo box
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

                addCountryComboBox.setItems(countryInteraction.getCountries());

    }
}


