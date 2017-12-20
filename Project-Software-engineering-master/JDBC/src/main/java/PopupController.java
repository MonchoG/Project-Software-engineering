

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Callback;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

/**
 * PopupController class
 * creates and shows popup window to custom date range settings
 */
public class PopupController {
    private DAO dao;
    @FXML
    private DatePicker beginDate;
    @FXML
    private DatePicker endDate;

    public PopupController() {
        this.dao = new DAO(DBmanager.getInstance());
    }

    /**
     * display
     * initializes popUp window and adds all elements to it and creates events
     * when popup is shown user can pick the date range manually using two datePickers, which stand for
     * beginning and end date.
     */
    public void display() {
        Stage popupWindow = new Stage();
        popupWindow.setResizable(false);

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Custom Date");

        Button apply_settings = new Button("Apply settings");
        apply_settings.getStyleClass().add("set");
        apply_settings.setOnAction(e -> popupWindow.close());

        Label label1 = new Label("Enter begin date");
        DatePicker beginDate = new DatePicker();
        this.beginDate = beginDate;

        Label label2 = new Label("Enter end date");
        DatePicker endDate = new DatePicker();

       // Create a day cell factory
        //here we create a day cell factory, which disables future dates
        //TODO we need to disable all dates for which we don't have measurements
        Callback<DatePicker, DateCell> dayCellFactory_begin = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        // Must call super
                        super.updateItem(item, empty);

                        // Disable all future date cells
                        if (item.isAfter(LocalDate.now())) {
                            this.setDisable(true);
                        }
                    }
                };
            }
        };

        // Set the day cell factory to the DatePicker
        beginDate.setDayCellFactory(dayCellFactory_begin);


        VBox layout = new VBox(10);

        layout.getChildren().addAll(apply_settings, label1, beginDate, label2, endDate);

        layout.setAlignment(Pos.CENTER);

        Scene popupScene = new Scene(layout, 300, 250);
        popupScene.getStylesheets().add(getClass().getResource("popup.css").toExternalForm());
        beginDate.getStyleClass().add("beginDate");
        endDate.getStyleClass().add("endDate");
        popupWindow.setScene(popupScene);

        popupWindow.showAndWait();

    }

}