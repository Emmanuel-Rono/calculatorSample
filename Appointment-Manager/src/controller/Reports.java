package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Reports controller.
 */
public class Reports {
    Stage stage;
    Parent scene;

    /**
     * This method directs the user to the main menu.
     *
     * @param event button.
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method directs the user to the appointment by month report view.
     *
     * @param event button.
     */
    @FXML
    void onActionToAppointmentsReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsByMonthReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method directs the user to the contact report view.
     *
     * @param event button.
     */
    @FXML
    void onActionToContactReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ContactsReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method directs the user to the Division report view.
     *
     * @param event button.
     */
    @FXML
    void onActionToDivisionsReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/DivisionsReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}