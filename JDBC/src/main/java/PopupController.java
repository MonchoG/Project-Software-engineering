

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
    private DAO dao;
    private DatePicker beginDate;
    private DatePicker endDate;
    public PopupController(){
        this.dao = new DAO(DBmanager.getInstance());
    }

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
        DatePicker beginDate = new DatePicker();
        this.beginDate = beginDate;

        Label label2 = new Label("Enter end date");
        DatePicker endDate = new DatePicker();

        VBox layout= new VBox(10);

        layout.getChildren().addAll(button1, label1, beginDate, label2, endDate);

        layout.setAlignment(Pos.CENTER);

        Scene popupScene= new Scene(layout, 300, 250);

        popupScene.getStylesheets().add(getClass().getResource("graph_screen.css").toExternalForm());

        popupWindow.setScene(popupScene);

        popupWindow.showAndWait();

    }
    public DatePicker getBeginDate(){
        return beginDate;
    }
    public DatePicker getEndDate(){
        return endDate;
    }

}