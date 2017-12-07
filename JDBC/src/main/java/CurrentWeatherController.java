
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
    @FXML
    private Slider delay;
    @FXML
    private Label delay2;
    private double cel, fahr;
    private static Thread thread;
    int lastValue;

    public CurrentWeatherController() {
        this.db = new DAO(DBmanager.getInstance());
        cel = db.getTemp();
        fahr = cel + 32;
    }

    @FXML
    private void initialize() {
        try {
            lastValue = (int) delay.getValue();
            cel = db.getTemp();
            fahr = cel + 32;
            time.setText(db.getTime());
            temp.setText(String.valueOf(cel) + "°");
            delay2.setText(String.valueOf(delay.getValue() / 60000));
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
        } catch (Exception Exc) {
            System.out.println();
        }
    }

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
    public void slider_drag() {
        DecimalFormat df = new DecimalFormat("#.00");
        delay2.setText(String.valueOf(df.format(delay.getValue() / 60000)));
    }

    public void slider_release() throws InterruptedException {

        Timer timer = new Timer();
        int delay1 = (int) delay.getValue() / 60;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (lastValue != delay.getValue()) {
                            try {
                                timer.cancel();
                                timer.purge();
                                slider_release();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        initialize();
                    }
                });
            }
        }, delay1, delay1);


    }

    public void refresh() {
        initialize();
    }
}
