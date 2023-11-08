package controller;

import DBAccess.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Edit Appointment controller.
 */
public class EditAppointmentController implements Initializable {
    @FXML
    private Label appointmentIdLbl;

    @FXML
    private TextField appointmentIdTxt;

    @FXML
    private ComboBox<Contacts> contactCombo;

    @FXML
    private TextField contactTxt;

    @FXML
    private Label customerIdLbl;

    @FXML
    private ComboBox<Customers> customerCombo;

    @FXML
    private Label dateLbl;

    @FXML
    private DatePicker dateSelect;

    @FXML
    private Label descriptionLbl;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private Label endTimeLbl;

    @FXML
    private ComboBox<LocalTime> endCombo;

    @FXML
    private Label locationLbl;

    @FXML
    private TextField locationTxt;

    @FXML
    private Label startTimeLbl;

    @FXML
    private ComboBox<LocalTime> startCombo;

    @FXML
    private Label titleLbl;

    @FXML
    private TextField titleTxt;

    @FXML
    private Label typeLbl;

    @FXML
    private TextField typeTxt;

    @FXML
    private Label userIdLbl;

    @FXML
    private ComboBox<Users> userCombo;

    Stage stage;
    Parent scene;

    /**
     * This method directs the user to the main menu view.
     *
     * @param event button.
     */
    // Returns user back to main menu.
    @FXML
    public void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }



    /**
     * This method grabs user input, error checks and saves any changes to the database.
     *
     * @param event button.
     */
    // Saves any changed data.
    @FXML
    public void onActionSaveAppointment(ActionEvent event) {
        try {
            // Get date picker date value, start time value and end time value.
            LocalTime start = startCombo.getValue();
            LocalTime end = endCombo.getValue();
            LocalDate date = dateSelect.getValue();

            LocalTime beforeBusinessHours = LocalTime.of(7,59);
            LocalTime afterBusinessHours = LocalTime.of(22,0);

            // Grabs user input from form.
            int id = Integer.parseInt(appointmentIdTxt.getText());
            String title = titleTxt.getText();
            String description = descriptionTxt.getText();
            String location = locationTxt.getText();
            String type = typeTxt.getText();
            Contacts contacts = contactCombo.getValue();
            Customers customers = customerCombo.getValue();
            Users users = userCombo.getValue();

            // Checking for field errors.
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

            // Sets the local date time of the start and end of the appointments using local time and local date.
            LocalDateTime startDateTime = LocalDateTime.of(date, start);
            LocalDateTime endDateTime = LocalDateTime.of(date, end);

            // Prevent user from scheduling on weekends.
            if (DBAppointments.daysWithoutWeekends(startDateTime, endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointments cannot be scheduled on the weekend.");
                alert.showAndWait();
                return;
            }
            // Prevents user from scheduling overlapping appointments.
            if (DBAppointments.validateApptTime(startDateTime, endDateTime, id)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointments cannot overlap");
                alert.showAndWait();
                return;
            }

            // Adds data to the DB which in return populate to our appointment table.
            DBAppointments.updateAppointments(id, title, description, location, type, startDateTime,
                    endDateTime, customers.getCustomerId(), users.getId(), contacts.getId());

            // Confirms if Appointment has been added to the database.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Appointment has been updated");
            alert.showAndWait();

            // Redirects user to the customer main screen.
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NumberFormatException | IOException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("New Appointment not updated. Please complete all fields.");
            alert.showAndWait();
        }
    }

    /**
     * This method transfers data from the main menu table to the edit appointment controller form.
     *
     * @param appointments Passes appointments.
     */
    // Used to transfer appointments from the appointment table view into the edit customer view.
    public void transferAppointments(Appointments appointments){

        Contacts contacts = DBContacts.getContactById(appointments.getContactId());
        Users users = DBUsers.getUserById(appointments.getUserId());
        Customers customers = DBCustomers.getCustomerById(appointments.getCustomerId());

        appointmentIdTxt.setText(String.valueOf(appointments.getAppointmentId()));
        titleTxt.setText(String.valueOf(appointments.getTitle()));
        descriptionTxt.setText(appointments.getDescription());
        locationTxt.setText(appointments.getLocation());
        contactCombo.setValue(contacts);
        typeTxt.setText(appointments.getType());

        contactCombo.getSelectionModel().select(contacts);
        customerCombo.setValue(customers);
        userCombo.setValue(users);

        startCombo.setValue(LocalTime.from(appointments.getStartDateTime()));
        endCombo.setValue(LocalTime.from(appointments.getEndDateTime()));
        dateSelect.setValue(LocalDate.from(appointments.getStartDateTime()));

    }

    /**
     * This method sets the columns, combo boxes and tableview with the appointment data.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets the combo boxes with data.
        contactCombo.setItems(DBContacts.getAllContacts());
        customerCombo.setItems(DBCustomers.getAllCustomers());
        userCombo.setItems(DBUsers.getAllUsers());

        // Sets the start and end time -- 8AM - 10PM.
        LocalTime start = LocalTime.of(8,0);
        LocalTime end = LocalTime.of(8,0);

        // Sets the time combo boxes with time incrementing by 30 minutes.
        while (start.isBefore(end.plusSeconds(1))){
            startCombo.getItems().add(start);
            endCombo.getItems().add(start);
            start = start.plusMinutes(15);
            end = start.plusMinutes(15);
        }
    }
}