import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class WeatherStationGUI extends BorderPane {

    private TextField time = new TextField();
    private TextField temperature = new TextField();
    private TextField pressure = new TextField();
    private TextField light = new TextField();


    private Button refresh = new Button("refresh...");

    DAO dbc = new DAO(DBmanager.getInstance());

    public WeatherStationGUI() {
        setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        setCenter(initFields());
        setFieldData(dbc);
        setBottom(initButtons());
    }


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
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(20);
        grid.setVgap(2);
        grid.add(new Label("Time"), 1, 0);
        grid.add(time, 2, 0);
        time.setEditable(false);
        grid.add(new Label("Temp"), 1, 1);
        grid.add(temperature, 2, 1);
        temperature.setEditable(false);
        grid.add(new Label("Pressure"), 1, 2);
        grid.add(pressure, 2, 2);
        pressure.setEditable(false);
        grid.add(new Label("Light"), 1, 3);
        grid.add(light, 2, 3);
        light.setEditable(false);
        return grid;
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