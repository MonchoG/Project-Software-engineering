
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


import java.io.IOException;

public class CurrentWeatherController {
    private DAO db;
    private Scene scene2;
    private AppMain main;

    @FXML
    private Label time;
    @FXML
    private Label temp;
    @FXML
    private Button c;
    @FXML
    private Button f;
    @FXML
    private Label pres;
    @FXML
    private Label lux;
    private double cel, fahr;

    public CurrentWeatherController() {
        this.db = new DAO(DBmanager.getInstance());
        cel = db.getTemp();
        fahr = cel + 32;
    }

    @FXML
    private void initialize() {
        cel = db.getTemp();
        fahr = cel + 32;
        time.setText(db.getTime());
        temp.setText(String.valueOf(cel) + "°");
        c.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                temp.setText(String.valueOf(cel) + "°");
            }
        });
        f.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                temp.setText(String.valueOf(fahr) + "°");

            }
        });
        pres.setText(String.valueOf(db.getPressure() + " atm"));
        lux.setText(String.valueOf(db.getLight() + " lx"));
    }

// here's Miro's code:

    //    private TextField time = new TextField();
//    private TextField temperature = new TextField();
//    private TextField pressure = new TextField();
//    private TextField light = new TextField();
//
//    private Button toGraphView = new Button("To graph view");
//    private Button refresh = new Button("refresh...");
//
//    DAO dbc = new DAO(DBmanager.getInstance());
//
//    public CurrentWeatherController() {
//        setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
//        setCenter(initFields());
//        setFieldData(dbc);
//        setBottom(initButtons());
//    }
//
//
//    private Pane initButtons() {
//        HBox box = new HBox();
//        box.setAlignment(Pos.CENTER);
//        box.setSpacing(5);
//        box.getChildren().add(refresh);
//        refresh.setOnAction(new CurrentWeatherController.ButtonHandler());
//
//        return box;
//    }
//
//    private Pane initFields() {
//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setPadding(new Insets(10, 10, 10, 10));
//        grid.setHgap(20);
//        grid.setVgap(2);
//
//        grid.add(toGraphView,3,3);
//        grid.add(new Label("Time"), 1, 0);
//        grid.add(time, 2, 0);
//        time.setEditable(false);
//        grid.add(new Label("Temp"), 1, 1);
//        grid.add(temperature, 2, 1);
//        temperature.setEditable(false);
//        grid.add(new Label("Pressure"), 1, 2);
//        grid.add(pressure, 2, 2);
//        pressure.setEditable(false);
//        grid.add(new Label("Light"), 1, 3);
//        grid.add(light, 2, 3);
//        light.setEditable(false);
//        return grid;
//    }
//
//    private void setFieldData(DAO db) {
//        time.setText(String.valueOf(db.getTime()));
//        temperature.setText(String.valueOf(db.getTemp()));
//        pressure.setText(String.valueOf(db.getPressure()));
//        light.setText(String.valueOf(db.getLight()));
//    }
//
//
//
//    private class ButtonHandler implements EventHandler {
//
//        public void handle(Event event) {
//            DAO dbc = new DAO(DBmanager.getInstance());
//            if (event.getSource().equals(refresh)) {
//                setFieldData(dbc);
//            }
//        }
//    }
//
    public void setMain(AppMain main) {
        this.main = main;
    }

    public void setScene2(Scene scene2) {
        this.scene2 = scene2;
    }

    @FXML
    public void graph() throws IOException {
        main.setScene(scene2);
    }

    @FXML
    public void refresh() throws IOException {
        initialize();
    }
}
