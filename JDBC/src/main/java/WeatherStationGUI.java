import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class WeatherStationGUI extends BorderPane {

    private javafx.scene.control.TextField time = new javafx.scene.control.TextField();
    private javafx.scene.control.TextField temperature = new javafx.scene.control.TextField();
    private javafx.scene.control.TextField pressure = new javafx.scene.control.TextField();
    private javafx.scene.control.TextField light = new javafx.scene.control.TextField();


    private javafx.scene.control.Button refresh = new javafx.scene.control.Button("refresh...");


    public WeatherStationGUI() {
        setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        setCenter(initFields());
        setBottom(initButtons());
    }

    //
    private Pane initButtons() {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(5);
        box.getChildren().add(refresh);
        refresh.setOnAction(new ButtonHandler());

        return box;
    }

    //
    private Pane initFields() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        grid.setHgap(20);
        grid.setVgap(2);
        grid.add(new javafx.scene.control.Label("Time"), 1, 0);
        grid.add(time, 2, 0);
        time.setEditable(false);
        grid.add(new javafx.scene.control.Label("Temp"), 1, 1);
        grid.add(temperature, 2, 1);
        temperature.setEditable(false);
        grid.add(new javafx.scene.control.Label("Pressure"), 1, 2);
        grid.add(pressure, 2, 2);
        pressure.setEditable(false);
        grid.add(new javafx.scene.control.Label("Light"), 1, 3);
        grid.add(light, 2, 3);
        light.setEditable(false);
        return grid;
    }

    private DAO getFieldData() {
        DAO p = new DAO(DBmanager.getInstance());
        p.getTime();
        p.getTemp();
        p.getPressure();
        p.getLight();

        return p;
    }


    private void setFieldData(DAO db) {
        time.setText(String.valueOf(db.getTime()));
        temperature.setText(String.valueOf(db.getTemp()));
        pressure.setText(String.valueOf(db.getPressure()));
        light.setText(String.valueOf(db.getLight()));
    }


    private class ButtonHandler implements EventHandler {

        public void handle(Event event) {
            DAO dbc = new DAO(DBmanager.getInstance());
            if (event.getSource().equals(refresh)) {
                setFieldData(dbc);
            }
        }
    }
}