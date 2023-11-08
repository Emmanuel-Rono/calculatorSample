package controller;


import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import DBAccess.DBUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class newAppointments implements Initializable {

    @FXML
    private Label appointmentIdLbl;

    @FXML
    private TextField appointmentIdTxt;

    @FXML
    private ComboBox<Contacts> contactCombo;

    @FXML
    private Label contactLbl;

    @FXML
    private ComboBox<Customers> customerCombo;

    @FXML
    private Label customerIdLbl;

    @FXML
    private Label dateLbl;

    @FXML
    private DatePicker dateSelect;

    @FXML
    private Label descriptionLbl;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private ComboBox<LocalTime> endCombo;

    @FXML
    private Label endTimeLbl;

    @FXML
    private Label locationLbl;

    @FXML
    private TextField locationTxt;

    @FXML
    private ComboBox<LocalTime> startCombo;

    @FXML
    private Label startTimeLbl;

    @FXML
    private Label titleLbl;

    @FXML
    private TextField titleTxt;

    @FXML
    private Label typeLbl;

    @FXML
    private TextField typeTxt;

    @FXML
    private ComboBox<Users> userCombo;

    @FXML
    private Label userIdLbl;

    Stage stage;
    Parent scene;


    @FXML
    public void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML
    public void onActionSaveAppointment(ActionEvent event) throws IOException {
        try {
            // Grabs user input from form
//            int id = Integer.parseInt(appointmentIdTxt.getText());
            String title = titleTxt.getText();
            String description = descriptionTxt.getText();
            String location = locationTxt.getText();
            String type = typeTxt.getText();

            // Get date picker date value, start time value and end time value
            LocalTime start = startCombo.getValue();
            LocalTime end = endCombo.getValue();
            LocalDate date = dateSelect.getValue();

            LocalTime beforeBusinessHours = LocalTime.of(7,59);
            LocalTime afterBusinessHours = LocalTime.of(22,0);

            Contacts contacts = contactCombo.getValue();
            Customers customers = customerCombo.getValue();
            Users users = userCombo.getValue();

            // Checking for field errors
            if(title.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Title cannot be left blank.");
                alert.showAndWait();
            return;
            }
            if (description.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Description cannot be left blank.");
                alert.showAndWait();
                return;
            }
            if (location.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Location cannot be left blank.");
                alert.showAndWait();
                return;
            }
            if (type.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Type cannot be left blank.");
                alert.showAndWait();
                return;
            }
            if (contacts == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Contacts cannot be left unselected.");
                alert.showAndWait();
                return;
            }
            if (date == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Date cannot be left unselected.");
                alert.showAndWait();
                return;
            }
            if (start == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Start time cannot be left unselected.");
                alert.showAndWait();
                return;
            }
            if (end == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "End time cannot be left unselected.");
                alert.showAndWait();
                return;
            }
            if (start.isAfter(end)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Start time cannot be placed after the end time.");
                alert.showAndWait();
                return;
            }
            if (start.equals(end)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Start time and End time cannot be the same.");
                alert.showAndWait();
                return;
            }
            if (start.isBefore(beforeBusinessHours) || start.isAfter(afterBusinessHours)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Hours need to be between \n 8AM - 10PM | 08:00 - " +
                        "22:00 ");
                alert.showAndWait();
                return;
            }
            if (end.isBefore(beforeBusinessHours) || end.isAfter(afterBusinessHours)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Hours need to be between \n 8AM - 10PM | 08:00 - " +
                        "22:00 ");
                alert.showAndWait();
                return;
            }
            if (customers == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Customer cannot be left unselected.");
                alert.showAndWait();
                return;
            }
            if (users == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Users cannot be left unselected.");
                alert.showAndWait();
                return;
            }

            LocalDateTime startDateTime = LocalDateTime.of(date, start);
            LocalDateTime endDateTime = LocalDateTime.of(date, end);
            if (DBAppointments.daysWithoutWeekends(startDateTime, endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointments cannot be scheduled on the weekend.");
                alert.showAndWait();
                return;
            }
            if (DBAppointments.validateApptTime(startDateTime, endDateTime, -1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointments cannot overlap");
                alert.showAndWait();
                return;
            }

            DBAppointments.addAppointment(title, description, location, type, startDateTime,
                        endDateTime, customers.getCustomerId(), users.getId(), contacts.getId());


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Appointment has been added");
            alert.showAndWait();

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("New Appointment not created. Please complete all fields.");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactCombo.setItems(DBContacts.getAllContacts());
        customerCombo.setItems(DBCustomers.getAllCustomers());
        userCombo.setItems(DBUsers.getAllUsers());


        LocalTime start = LocalTime.of(8,0);
        LocalTime end = LocalTime.of(22,0);

        while (start.isBefore(end.plusSeconds(1))){
            startCombo.getItems().add(start);
            endCombo.getItems().add(start);
            start = start.plusMinutes(15);
            end = start.plusMinutes(15);
        }
    }
}
