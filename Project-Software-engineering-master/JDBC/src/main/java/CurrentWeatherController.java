import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
//TODO something is wrong up here, probably with timer, because when you close program, it's still running
public class CurrentWeatherController {
    private DAO db;
    private Scene scene2;
    private AppMain main;
    @FXML
    Button graph;
    @FXML
    private Label time;
    public Label temp;
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
    boolean last_was_fahr = false;
    DecimalFormat df = new DecimalFormat("#.00");

    public CurrentWeatherController() {
        this.db = new DAO(DBmanager.getInstance());
        cel = db.getTemp();
        fahr = cel + 32;
    }

    @FXML
    /**initialize method
     * sets all components in the GUI to their corresponding data
     *
     *
     */
    private void initialize() {
        try {
            System.out.println(Font.getFamilies());

            f.setVisible(true);
            //getting stylesheets for each button
            c.getStyleClass().add("c");
            f.getStyleClass().add("f");
            graph.getStyleClass().add("graph");
            temp.getStyleClass().add("temp");

            lastValue = (int) delay.getValue();
            cel = db.getTemp();
            fahr = cel + 32;
            time.setText(db.getTime());

            delay2.setText(df.format(delay.getValue() / 60000) + " sec");

            if (!last_was_fahr) {
                c.setVisible(false);
                f.setVisible(true);
                if (cel > 0) temp.setText("+" + String.valueOf(cel) + "°");
                else temp.setText(String.valueOf(cel) + "°");

            } else {
                c.setVisible(true);
                f.setVisible(false);
                if (fahr > 0) temp.setText("+" + String.valueOf(fahr) + "°");
                else temp.setText(String.valueOf(fahr) + "°");
            }


            c.setOnAction(event -> {
                c.setVisible(false);
                f.setVisible(true);
                if (cel > 0) temp.setText("+" + String.valueOf(cel) + "°");
                else temp.setText(String.valueOf(cel) + "°");
               last_was_fahr=false;
            });
            f.setOnAction(event -> {
                c.setVisible(true);
                f.setVisible(false);
                if (fahr > 0) temp.setText("+" + String.valueOf(fahr) + "°");
                else temp.setText(String.valueOf(fahr) + "°");
               last_was_fahr=true;
            });
            pres.setText(String.valueOf(db.getPressure() + " atm"));
            lux.setText(String.valueOf(db.getLight() + " lx"));
        } catch (Exception Exc) {
            System.out.println(Exc);
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

        delay2.setText(df.format(delay.getValue() / 60000) + " sec");
    }

    public void slider_release() throws InterruptedException {

        Timer timer = new Timer();
        int delay1 = (int) delay.getValue() / 60;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
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
                });
            }
        }, delay1, delay1);
    }

}
