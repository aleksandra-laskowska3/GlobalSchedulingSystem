package controller;
import DAO.divisionInteraction;
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
import javafx.util.Callback;
import model.*;
import DAO.contactInteraction;
import DAO.customersInteraction;
import DAO.appointmentsInteraction;

import java.time.Month;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/**
 *  Controller class that controls reports
 * @author Alekandra Laskowska
 */
public class reportsController implements Initializable {
        Stage stage;
        Parent scene;



        @FXML
        private ComboBox<Customers> appCustomerDropBox;

        @FXML
        private TableView<Report1> typeCountTableView;

        @FXML
        private ComboBox<Month> monthComboBox;

        @FXML
        private TableColumn<Appointments, Integer> report1Count;

        @FXML
        private TableColumn<Appointments, String> report1Type;

        @FXML
        private TableView<Appointments> appCustomersTableView;

        @FXML
        private ComboBox<Contacts> contactDropBox;

        @FXML
        private TableView<Appointments> contactScheduleTableView;


        @FXML
        private TableColumn<Appointments, Integer> report2CustomerID;

        @FXML
        private TableColumn<Appointments, ZonedDateTime> report2End;

        @FXML
        private TableColumn<Appointments, Integer> report2ID;

        @FXML
        private TableColumn<Appointments, ZonedDateTime> report2Start;

        @FXML
        private TableColumn<Appointments, String> report2Title;

        @FXML
        private TableColumn<Appointments, String> report2Type;

        @FXML
        private TableColumn<Appointments, Integer> report3ContactID;

        @FXML
        private TableColumn<Appointments, String> report3Description;

        @FXML
        private TableColumn<Appointments, String> report2Description;


        @FXML
        private TableColumn<Appointments, ZonedDateTime> report3End;

        @FXML
        private TableColumn<Appointments, Integer> report3ID;

        @FXML
        private TableColumn<Appointments, ZonedDateTime> report3Start;

        @FXML
        private TableColumn<Appointments, String> report3Title;

        @FXML
        private TableColumn<Appointments, String> report3Type;

        @FXML
        private Button reportMainMenuButton;

        @FXML
        private AnchorPane reportsForm;

        /**
         * ActionEvent that occurs when a customer is selected
         * Lambda expression is used to generate the customers list in the combo box
         * Second Lambda expression is used to populate an appointment list based on the selected customer
         * @param event
         */
        @FXML
        void customerComboBoxHandler(ActionEvent event) {
                //Lambda #1
                Callback<ListView<Customers>, ListCell<Customers>> custList = lv -> new ListCell<Customers>() {
                        @Override
                        protected void updateItem(Customers item, boolean empty){
                                super.updateItem(item, empty);
                                setText(empty ? "" : (item.getCustomer_ID() + "- " + item.getCustName()));
                        }
                };
                Callback<ListView<Customers>, ListCell<Customers>> custPicked = lv -> new ListCell<Customers>() {
                        @Override
                        protected void updateItem(Customers item, boolean empty){
                                super.updateItem(item, empty);
                                setText(empty ? "" : (item.getCustomer_ID() + "- " + item.getCustName()));
                        }
                };
                appCustomerDropBox.setCellFactory(custList);
                appCustomerDropBox.setButtonCell(custPicked.call(null));

                Customers c = appCustomerDropBox.getValue();
                ObservableList<Appointments> filteredAppByCust = appointmentsInteraction.getAppListByCustID(c.getCustomer_ID());
                ObservableList<Appointments> appList = FXCollections.observableArrayList();
                //Lambda #2
                filteredAppByCust.forEach(app -> {
                        appList.add(app);
        });
                appCustomersTableView.setItems(appList);
        }

        /**
         * ActionEvent that occurs when a contact is selected
         * Displays a tableview of the appointments associated with the contact ID
         * @param event
         */
        @FXML
        void contactDropBoxHandler(ActionEvent event) {
                int contact_ID = contactDropBox.getSelectionModel().getSelectedItem().getContact_ID();
                if(appointmentsInteraction.getAppByContactID(contact_ID)){
                        contactScheduleTableView.setItems(appointmentsInteraction.getAppListByContactID(contact_ID));
                }
        }

        /**
         * ActionEvent that occurs when the main menu is pressed
         * Takes you back to the main menu page
         * @param event
         * @throws IOException
         */
        @FXML
        void reportMainMenuButtonHandler(ActionEvent event) throws IOException {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

        /**
         * ActionEvent that occurs when a month is selected
         * Tableview displays types and totals of appointments based on the month selected
         * @param event
         */
        @FXML
        void monthPickedHandler(ActionEvent event) {
                Month selectedMonth = monthComboBox.getSelectionModel().getSelectedItem();
               if(selectedMonth!=null){
                       typeCountTableView.setItems(appointmentsInteraction.totalsByTypeAndMonth(selectedMonth));
               }





        }

        /**
         *  Method that initializes and fills columns with the correct data
         *  Generates a list of contacts, customers, and months to the combo boxes
         * @param url
         * @param resourceBundle
         */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            report1Type.setCellValueFactory(new PropertyValueFactory<>("type"));
            report1Count.setCellValueFactory(new PropertyValueFactory<>("totals"));

            report2ID.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
            report2Title.setCellValueFactory(new PropertyValueFactory<>("title"));
            report2Description.setCellValueFactory(new PropertyValueFactory<>("description"));
            report2Type.setCellValueFactory(new PropertyValueFactory<>("type"));
            report2Start.setCellValueFactory(new PropertyValueFactory<>("start"));
            report2End.setCellValueFactory(new PropertyValueFactory<>("end"));
            report2CustomerID.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));

            report3ID.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
            report3Title.setCellValueFactory(new PropertyValueFactory<>("title"));
            report3Description.setCellValueFactory(new PropertyValueFactory<>("description"));
            report3Type.setCellValueFactory(new PropertyValueFactory<>("type"));
            report3Start.setCellValueFactory(new PropertyValueFactory<>("start"));
            report3End.setCellValueFactory(new PropertyValueFactory<>("end"));
            report3ContactID.setCellValueFactory(new PropertyValueFactory<>("contact_ID"));

                contactDropBox.setItems(contactInteraction.getAllContacts());
                appCustomerDropBox.setItems(customersInteraction.getAllCustomers());
                monthComboBox.setItems(appointmentsInteraction.getMonthList());
    }
}

