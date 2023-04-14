package controller;
import helper.JDBC;
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
import model.Customers;
import DAO.customersInteraction;
import DAO.countryInteraction;
import DAO.divisionInteraction;
import model.firstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller class that controls updating a customer
 * @author Alekandra Laskowska
 */
public class updateCustomerController implements Initializable {
        Stage stage;
        Parent scene;


        @FXML
        private TextField updateAddressTxt;

        @FXML
        private Button updateCancelButton;

        @FXML
        private ComboBox<Countries> updateCountryComboBox;

        @FXML
        private AnchorPane updateCustomerForm;

        @FXML
        private ComboBox<firstLevelDivision> updateDivisionComboBox;

        @FXML
        private TextField updateIDTxt;

        @FXML
        private TextField updateNameTxt;

        @FXML
        private TextField updatePhoneNumberTxt;

        @FXML
        private TextField updatePostalCodeTxt;

        @FXML
        private Button updateSaveButton;

    /**
     * ActionEvent that occurs when the cancel button is pressed, takes you back to the Customers form
     * @param event
     * @throws IOException
     */
        @FXML
        void updateCancelButtonHandler(ActionEvent event) throws IOException {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    /**
     * ActionEvent that occurs when a country is picked from the combobox
     * It sets the divisions list that are associated with the country
     * @param event
     * @throws IOException
     */
        @FXML
        void countryHandler(ActionEvent event) throws IOException{
            Countries c = updateCountryComboBox.getValue();
            updateDivisionComboBox.setItems(divisionInteraction.getFilteredDivisions(c.getCountry_ID()));
        }

    /**
     * ActionEvent that occurs when the save button is pressed
     * Checks if all the sections have been filled out
     * Updates the customer and takes you back to the customer form
     * @param event
     * @throws IOException
     */
        @FXML
        void updateSaveButtonHandler(ActionEvent event) throws IOException {

            firstLevelDivision division = updateDivisionComboBox.getValue();
            if(division== null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please select the Division");
                alert.show();
                return;
            }
            int division_ID = division.getDivision_ID();
            String custName = updateNameTxt.getText();
            String address = updateAddressTxt.getText();
            String postal_Code = updatePostalCodeTxt.getText();
            String phone = updatePhoneNumberTxt.getText();
            int customer_ID = Integer.parseInt(updateIDTxt.getText());


            if (custName.isBlank() || address.isBlank() || postal_Code.isBlank() || phone.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please fill out all the sections");
                alert.showAndWait();
                return;
            }

            customersInteraction.updateCustomer(custName, address, postal_Code, phone, division_ID, customer_ID);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    /**
     * Method that takes the selected customer data and sets it in text and combo boxes
     * @param selectedCustomer The selected customer from the tableview
     */
    public void sendCustomer(Customers selectedCustomer){

                updateIDTxt.setText(String.valueOf(selectedCustomer.getCustomer_ID()));
                updateNameTxt.setText(selectedCustomer.getCustName());
                updateAddressTxt.setText(selectedCustomer.getAddress());
                updatePostalCodeTxt.setText(selectedCustomer.getPostal_Code());
                updatePhoneNumberTxt.setText(selectedCustomer.getPhone());

                updateCountryComboBox.setItems(countryInteraction.getCountries());
                for(Countries c: updateCountryComboBox.getItems()){
                    if(c.getCountry_ID()==selectedCustomer.getCountry_ID()){
                        updateCountryComboBox.getSelectionModel().select(c);
                        break;
                    }
                }
                updateDivisionComboBox.setItems(divisionInteraction.getFilteredDivisions(selectedCustomer.getCountry_ID()));
            for(firstLevelDivision d: updateDivisionComboBox.getItems()){
                if(d.getDivision_ID()==selectedCustomer.getDivision_ID()){
                    updateDivisionComboBox.getSelectionModel().select(d);
                    break;
                }
            }



        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}

