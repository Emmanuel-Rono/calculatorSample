package controller;

import DBAccess.DBReports;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Reports;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Report class controller for the appointments by division, country and total.
 */
public class DivisionsReportController implements Initializable {
    @FXML
    private TableColumn<Reports, Integer> totalCol;

    @FXML
    private TableColumn<Reports, Integer> countryCol;

    @FXML
    private TableColumn<Reports, Integer> divisionCol;

    @FXML
    private TableView<Reports> divisionsTable;
    Parent scene;
    Stage stage;

    /**
     * This method directs the user to the reports view.
     *
     * @param event button;
     */
    // Returns user back to the reports view.
    @FXML
    public void onActionBack(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * This method sets the columns and tableview with the report data.
     *
     * @param url;
     * @param resourceBundle;
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets the table view with the report data.
        divisionsTable.setItems(DBReports.getDivisionReport());

        // Sets each column with the data.
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totals"));
    }
}
