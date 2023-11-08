package controller;

import DBAccess.DBAppointments;
import DBAccess.DBUsers;
import util.CustomMessages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Login controller.
 */
public class LoginController implements Initializable {
    @FXML
    private Label titleLbl;
    @FXML
    private Label idLbl;

    @FXML
    private TextField idTxt;

    @FXML
    private Label passwordLbl;

    @FXML
    private TextField passwordTxt;

    @FXML
    private Button resetLbl;

    @FXML
    private Button loginLbl;

    @FXML
    private Label timezoneLbl;

    @FXML
    private Label zoneIdLbl;

    Stage stage;
    Parent scene;

    ResourceBundle rb = ResourceBundle.getBundle("lang", Locale.getDefault());

    /**
     * This method grabs the users input, validates, and directs the user to the main menu if validation is successful.
     *
     * @param event button.
     */
    // Log in button -- Checks for empty text fields, and correct credentials. If correct, fires into the main menu
    // else returns an incorrect credential error.
    @FXML
    public void onActionLogin(ActionEvent event) throws IOException, SQLException {

            String username = idTxt.getText();
            String password = passwordTxt.getText();

            if(username.isEmpty()){
                CustomMessages.errorMessages("umessage");
            }
            else if(password.isEmpty()){
                CustomMessages.errorMessages("pmessage");
            }

            // Validates the user log in.
            else if(DBUsers.vLogin(username, password)) {

                // Stores the user by username.
                DBUsers.userLogIn.add(DBUsers.getUsername(username).toString());
                // Writes to the log activity file
                FileIO(username);

                // Loops through and Checks for any upcoming appointments within 15minutes. If none, error message is
                // displayed.
                if(DBAppointments.loginAppointmentCheck() == true) {
                } else {
                    CustomMessages.informMessages("noaptmessage");
                }
                // Sends user to the main menu if successful.
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                // Writes to the log activity file
                FileIO(username);
                CustomMessages.errorMessages("cmessage");
            }
    }

    /**
     * This method writes any log in activity to the login_activity.txt.
     *
     * @param username Passes the username.
     */
    // Writes log in activity to the login_activity.txt file.
    public static void FileIO(String username) throws IOException {

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true));
            if(DBUsers.holdUsername(username)) {
                writer.write("User Log in attempt on from " + username + " on " + date + " at " + time + " was " +
                        "successful.\n");
                writer.close();
            } else {
                writer.write("User Log in attempt on from " + username + " on " + date + " at " + time + " was " +
                        "unsuccessful.\n");
                writer.close();
            }
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * This method resets the input fields.
     *
     * @param event button.
     */
    // Clears the username and password fields when clicked on.
    @FXML
    public void onActionResetFields(ActionEvent event) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(rb.getString("rmessage"));
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                idTxt.setText("");
                passwordTxt.setText("");
            }
    }
    /**
     * This method sets the text to english or french and sets the zone id.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Sets text to in either English or French based on the computer language settings.
        titleLbl.setText(rb.getString("title"));
        resetLbl.setText(rb.getString("reset"));
        loginLbl.setText(rb.getString("login"));
        idLbl.setText(rb.getString("username"));
        passwordLbl.setText(rb.getString("password"));
        timezoneLbl.setText(rb.getString("timezone"));

        // Sets the users location and displays it in a label.
        zoneIdLbl.setText(String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
    }
}
