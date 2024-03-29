

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AppMain extends Application {
    private Stage window;

    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene1, scene2;
        Pane pane1;
        Pane pane2;
        FXMLLoader loader = new FXMLLoader();
        window = stage;
        window.setResizable(false );
        loader.setLocation(AppMain.class.getResource("screen_current_weather.fxml"));
        pane1 = loader.load();
        CurrentWeatherController currentWeatherController = loader.getController();

        loader = new FXMLLoader();
        loader.setLocation(AppMain.class.getResource("screen_graphs.fxml"));
        pane2 = loader.load();
        GraphsController graphsController = loader.getController();

        scene1 = new Scene(pane1, 400, 400);
        scene2 = new Scene(pane2, 600, 500);

        scene1.getStylesheets().add(getClass().getResource("screen_current_weather_css.css").toExternalForm());
        scene2.getStylesheets().add(getClass().getResource("graph_screen.css").toExternalForm());


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