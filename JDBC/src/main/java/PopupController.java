

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Callback;
import sun.plugin2.jvm.RemoteJVMLauncher;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

/**
 * PopupController class
 * creates and shows popup window to custom date range settings
 */
public class PopupController {
    
    private String beginDate;
    private String endDate;
    private int beginDay;
    @FXML
    private DatePicker begin;
    @FXML
    private DatePicker end;




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


        Label label1 = new Label("Enter begin date");
        begin = new DatePicker();
        Label label2 = new Label("Enter end date");
        end = new DatePicker();
        end.setDisable(true);
        apply_settings.setOnAction(e -> popupWindow.close());


        // Create a day cell factory
        //here we create a day cell factory, which disables future dates
        Callback<DatePicker, DateCell> dayCellFactory_begin = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        // Must call super
                        super.updateItem(item, empty);
                        //Disable all dates before the 1st of the month
                        int day = LocalDate.now().getDayOfMonth();
                        if(item.isBefore(LocalDate.now().minusDays(day-1))){
                            this.setDisable(true);
                        }
                        // Disable all future date cells
                        if (item.isAfter(LocalDate.now().minusDays(1))) {
                            this.setDisable(true);
                        }

                    }
                };
            }
        };


        // Set the day cell factory to the DatePicker
        begin.setDayCellFactory(dayCellFactory_begin);
        // Set day cell end when begin date has been picked
        begin.setOnAction(event -> setEnd());


        VBox layout= new VBox(10);

        layout.getChildren().addAll(apply_settings, label1, begin, label2, end);

        layout.setAlignment(Pos.CENTER);

        Scene popupScene= new Scene(layout, 300, 250);

        popupScene.getStylesheets().add(getClass().getResource("graph_screen.css").toExternalForm());

        popupWindow.setScene(popupScene);

        popupWindow.showAndWait();
        Date date = new Date();
        String currentTime = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        String beginDate = begin.getValue() + " " + currentTime;
        String endDate = end.getValue() + " " + currentTime;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }
    @FXML
    private void setEnd(){
        end.setDisable(false);
        Callback<DatePicker, DateCell> dayCellFactory_end = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        // Must call super
                        super.updateItem(item, empty);

                        // Disable all dates before and equal to begin
                        if(item.isBefore(begin.getValue().plusDays(1))) {
                            this.setDisable(true);
                        }
                        // Disable all future date cells
                        if(item.isAfter(LocalDate.now())){
                            this.setDisable(true);
                        }
                    }
                };
            }
        };
        end.setDayCellFactory(dayCellFactory_end);

    }
    public String getBeginDate(){
        return beginDate;
    }
    public String getEndDate(){
        return endDate;
    }
    public int getBeginDay(){
        return beginDay;
    }
}
