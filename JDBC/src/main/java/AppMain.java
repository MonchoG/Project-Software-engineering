

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AppMain extends Application {
    Stage window;
    Scene scene1, scene2;
    private Pane pane1;
    private Pane pane2;
    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        window = stage;
        loader.setLocation(AppMain.class.getResource("screen_current_weather.fxml"));
        pane1 = loader.load();
        CurrentWeatherController currentWeatherController = loader.getController();

        loader = new FXMLLoader();
        loader.setLocation(AppMain.class.getResource("screen_graphs.fxml"));
        pane2 = loader.load();
        GraphsController graphsController = loader.getController();

        scene1 = new Scene(pane1, 400, 400);
        scene2 = new Scene(pane2, 600, 400);

        scene1.getStylesheets().add(getClass().getResource("screen_current_weather_css.css").toExternalForm());
        scene2.getStylesheets().add(getClass().getResource("screen_current_weather_css.css").toExternalForm());

        currentWeatherController.setScene2(scene2);
        currentWeatherController.setMain(AppMain.this);
        graphsController.setScene1(scene1);
        graphsController.setMain(AppMain.this);

        stage.setTitle("Weather App");
        stage.setScene(scene1);
        stage.show();

    }
    public void setScene(Scene scene) {
        window.setScene(scene);
    }

}