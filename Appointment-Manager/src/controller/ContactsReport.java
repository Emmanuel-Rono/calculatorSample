package controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
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
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Report class controller for the appointments by contact.
 */
public class ContactsReportController implements Initializable {
    @FXML
    private TableColumn<Appointments, Integer> appointmentCol;

    @FXML
    private TableView<Appointments> appointmentTable;

    @FXML
    private TableColumn<Contacts, String> contactCol;

    @FXML
    private TableColumn<Appointments, Integer> customerIdCol;

    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> endTimeCol;

    @FXML
    private TableColumn<Appointments, String> locationCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> startTimeCol;

    @FXML
    private TableColumn<Appointments, String> titleCol;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private ComboBox<Contacts> contactCombo;

    Parent scene;
    Stage stage;

    // Holds the contact filtered list after all the appointments are filtered.
    private static ObservableList<Appointments> contactFilterList = FXCollections.observableArrayList();

    /**
     * This method directs the user to the reports view.
     *
     * @param event button;
     */
    // Returns user back to the Report view.
    @FXML
    public void onActionBack(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    // Filters appointments based off the user's contact selection within the combo box.
    /** This method grabs the appointment data for the contact selected.
     *
     * Lambda expression #2 -- Filters the table view appointments by comparing the user's selected contact id chosen
     * in the combo box and the contact id stored in the appointments. The lambda expression provides flexible code referencing
     * and cleaner code.
     *
     * @param event button.
     */
    @FXML
    public void onActionByContact(ActionEvent event) {

        /** Lambda expression #2 -- Filters the table view appointments by comparing the user's selected contact id chosen
         * in the combo box and the contact id stored in the appointments.
         */
        contactFilterList.clear();
        appointmentTable.setItems(DBAppointments.getAllAppointments().filtered(
                appts -> appts.getContact().getId() == contactCombo.getValue().getId()
        ));
        if(contactFilterList.isEmpty()){
        appointmentTable.getItems().clear();
        Alert alert = new Alert(Alert.AlertType.WARNING, "No Appointments for this contact");
        alert.showAndWait();
        }
    }

    /**
     * This method sets the columns, contact combo box and tableview with the report data.
     *
     * @param url;
     * @param resourceBundle;
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Sets all contact names into the contact combo box.
        contactCombo.setItems(DBContacts.getAllContacts());
        // Sets all appointments into the table view.
        appointmentTable.setItems(DBAppointments.getAllAppointments());

        // Sets each column with the data.
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appointmentCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customer"));

    }
}
