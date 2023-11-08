package controller;

import DBAccess.DBCustomers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Customer menu
 */
public class CustomerMenuController implements Initializable {

    @FXML
    private TableColumn<Customers, String> addressCol;

    @FXML
    private TableColumn<Customers, Integer> customerIdCol;

    @FXML
    private TableColumn<Customers, String> customerNameCol;

    @FXML
    private TableView<Customers> customerTable;
    @FXML
    private TableColumn<Customers, Integer> countryCol;
    @FXML
    private TableColumn<Customers, Integer> divisionCol;

    @FXML
    private TableColumn<Customers, String> phoneNumberCol;

    @FXML
    private TableColumn<Customers, String> zipCodeCol;

    Stage stage;
    Parent scene;

    @FXML
    public void onActionBackToMainMenu(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void onActionDeleteCustomer(ActionEvent event) {

        Customers selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a customer to delete.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Customer? All " +
                    "appointments associated with this customer will also be deleted.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                DBCustomers.deleteCustomerAppointments(selectedCustomer.getCustomerId());
                DBCustomers.deleteCustomer(selectedCustomer.getCustomerId());
                // Refreshes the table.
                customerTable.getItems().clear();
                customerTable.getItems().addAll(DBCustomers.getAllCustomers());
            }
        }
    }

    @FXML
    public void onActionDisplayEditCustomer(ActionEvent event) throws IOException {

        Customers customer = customerTable.getSelectionModel().getSelectedItem();

        if(customer == null ){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a customer to edit.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EditCustomer.fxml"));
            loader.load();

            customerEdit controller = loader.getController();
            controller.transferCustomer(customer);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    public void onActionDisplayNewCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/NewCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets all customers into the table view.
        customerTable.setItems(DBCustomers.getAllCustomers());

        // Sets each column with the data.
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        zipCodeCol.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));


    }
}
