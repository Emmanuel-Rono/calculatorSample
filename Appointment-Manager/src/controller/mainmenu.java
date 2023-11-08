package controller;

import DBAccess.DBAppointments;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;


public class mainmenu implements Initializable {

    @FXML
    private TableColumn<Appointments, Integer> appointmentCol;

    @FXML
    private TableView<Appointments> appointmentTable;

    @FXML
    private TableColumn<Appointments, Integer> contactCol;

    @FXML
    private TableColumn<Appointments, Integer> customerIdCol;

    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    @FXML
    private TableColumn<Appointments, Timestamp> endTimeCol;

    @FXML
    private TableColumn<Appointments, String> locationCol;

    @FXML
    private TableColumn<Appointments, Timestamp> startTimeCol;

    @FXML
    private TableColumn<Appointments, String> titleCol;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private TableColumn<Appointments, Integer> userIdCol;

    @FXML
    private RadioButton allAppointments;

    @FXML
    private RadioButton byMonth;

    @FXML
    private RadioButton byWeek;


    private static ObservableList<Appointments> filterByWeeklyApts = FXCollections.observableArrayList();
    // List that shows appointments by month
    private static ObservableList<Appointments> filterByMonthApts = FXCollections.observableArrayList();

    LocalDate currentDate = LocalDate.now();
    Month currentMonth = currentDate.getMonth();
    int currentYear = currentDate.getYear();


    Stage stage;
    Parent scene;


    @FXML
    public void onActionAllAppointments(ActionEvent event) {
        // Get Appointments from DB
        appointmentTable.getItems().clear();
        appointmentTable.setItems(DBAppointments.getAllAppointments());
        System.out.println("All appointments");
    }


    @FXML
    public void onActionByWeek(ActionEvent event) {

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);

        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        filterByWeeklyApts.clear();


        DBAppointments.getAllAppointments().forEach(appointments -> {
            if(appointments.getStartDateTime().get(WeekFields.ISO.weekOfYear()) == weekOfYear && appointments.getStartDateTime().getYear() == currentYear){
                filterByWeeklyApts.add(appointments);

                if(!filterByWeeklyApts.isEmpty()) {
                    appointmentTable.setItems(filterByWeeklyApts);
                }
            }
        });
        if (filterByWeeklyApts.isEmpty()){
            appointmentTable.getItems().clear();
            Alert alert = new Alert(Alert.AlertType.WARNING, "No Weekly Appointments");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    @FXML
    public void onActionByMonth(ActionEvent event) {

        filterByMonthApts.clear();
        for(Appointments appointment : DBAppointments.getAllAppointments()) {
            if(appointment.getStartDateTime().getMonth() == currentMonth && appointment.getStartDateTime().getYear() == currentYear){
                filterByMonthApts.add(appointment);

                if(!filterByMonthApts.isEmpty()) {
                    appointmentTable.setItems(filterByMonthApts);
                }
            }
        }
        if(filterByMonthApts.isEmpty()){
            appointmentTable.getItems().clear();
            Alert alert = new Alert(Alert.AlertType.WARNING, "No Monthly Appointments");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    @FXML
    public void onActionDeleteAppointment(ActionEvent event) {

        Appointments appointments = appointmentTable.getSelectionModel().getSelectedItem();
        int appointmentId = appointments.getAppointmentId();
        String appointmentType = appointments.getType();

        if(appointments == null ){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an appointment to delete.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){

                DBAppointments.deleteAppointment(appointments.getAppointmentId());
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + appointmentId + "\n" +
                        "Appointment Type: " + appointmentType + "\n has been cancelled.");
                Optional<ButtonType> result1 = alert1.showAndWait();

                filterByMonthApts.clear();
                filterByWeeklyApts.clear();

                // Refreshes the table for each selection.
                if(allAppointments.isSelected()){
                    appointmentTable.getItems().clear();
                    appointmentTable.getItems().addAll(DBAppointments.getAllAppointments());
                } else if (byMonth.isSelected()) {
                    appointmentTable.getItems().clear();
                    onActionByMonth(event);
                } else if (byWeek.isSelected()) {
                    appointmentTable.getItems().clear();
                    onActionByWeek(event);
                }
            }
        }
    }

    @FXML
    public void onActionDisplayCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML
    public void onActionDisplayEditAppointment(ActionEvent event) throws IOException {
        Appointments appointments = appointmentTable.getSelectionModel().getSelectedItem();

        if(appointments == null ){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an appointment to edit.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EditAppointment.fxml"));
            loader.load();

            EditAppointmentController controller = loader.getController();
            controller.transferAppointments(appointments);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    public void onActionDisplayNewAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/NewAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML
    public void onActionDisplayReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void onActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        appointmentTable.setItems(DBAppointments.getAllAppointments());


        appointmentCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("user"));
    }
}
