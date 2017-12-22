

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.util.Date;

/**
 * PopupController class
 * creates and shows popup window to custom date range settings
 */
public class PopupController {

    private String beginDate;
    private String endDate;
    private int beginDay;


    /**display
     * initializes popUp window and adds all elements to it and creates events
     * when popup is shown user can pick the date range manually using two datePickers, which stand for
     * beginning and end date.
     */
    public  void display()
    {
        Stage popupWindow=new Stage();
 //example

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Custom Date");


        Button button1= new Button("Apply settings");

        button1.setOnAction(e -> popupWindow.close());

        Label label1 = new Label("Enter begin date");
        DatePicker begin = new DatePicker();

        Label label2 = new Label("Enter end date");
        DatePicker end = new DatePicker();



        VBox layout= new VBox(10);

        layout.getChildren().addAll(button1, label1, begin, label2, end);

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
        if(begin.getValue() != null) {
            this.beginDay = begin.getValue().getDayOfMonth();
        }
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