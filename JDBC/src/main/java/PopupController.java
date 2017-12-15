

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * PopupController class
 * creates and shows popup window to custom date range settings
 */
public class PopupController {
    private DAO dao;

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
        popupWindow.setTitle("This is a pop up window");

        Label label1= new Label("Pop up window now displayed");

        Button button1= new Button("Close this pop up window");

        button1.setOnAction(e -> popupWindow.close());

        VBox layout= new VBox(10);

        layout.getChildren().addAll(label1, button1);

        layout.setAlignment(Pos.CENTER);

        Scene popupScene= new Scene(layout, 300, 250);

        popupScene.getStylesheets().add(getClass().getResource("graph_screen.css").toExternalForm());

        popupWindow.setScene(popupScene);

        popupWindow.showAndWait();

    }

}