

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ToggleButton;
import java.net.URL;
import java.util.ResourceBundle;


public class GraphsController implements Initializable{

    private Scene scene1;
    private AppMain main;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private NumberAxis yAxis2;
    @FXML
    private LineChart<Integer, Double> lineChart;
    @FXML
    private ToggleButton Temp;
    @FXML
    private ToggleButton Light;
    @FXML
    private ToggleButton Pres;


    private DAO dbc = new DAO(DBmanager.getInstance());
    @Override
    public void initialize(URL location, ResourceBundle resources){
        //Create new XY Chart
         XYChart.Series<Integer, Double> default_graph= new XYChart.Series<>();
        //Adding Data to Graph when started up
        default_graph.setName("Temp");
        default_graph.getData().add(new XYChart.Data<>(1, dbc.getTemp()));
        default_graph.getData().add(new XYChart.Data<>(2, 3.0));
        default_graph.getData().add(new XYChart.Data<>(3, 29.0));
        default_graph.getData().add(new XYChart.Data<>(4, 15.0));
        default_graph.getData().add(new XYChart.Data<>(5, 18.0));
        default_graph.getData().add(new XYChart.Data<>(6, 25.0));
        default_graph.getData().add(new XYChart.Data<>(7, 26.0));
        default_graph.getData().add(new XYChart.Data<>(8, 18.0));
        default_graph.getData().add(new XYChart.Data<>(9, 24.0));
        default_graph.getData().add(new XYChart.Data<>(10, 16.0));
        lineChart.getData().setAll(default_graph);
        Temp.setSelected(true);
        Light.setSelected(false);
        Pres.setSelected(false);

        System.out.println(lineChart.getYAxis());
    }
    public void setLineChart(){

        //Checking which are selected and showing only the selected ones
        if(Temp.isSelected()){
            yAxis.setLabel("Temperature");
            yAxis.setTickLabelsVisible(true);

            if(Light.isSelected()){
                if(Pres.isSelected()){

                    lineChart.getData().setAll(getTemp(), getLight(), getPres());

                }
                else{
                    lineChart.getData().setAll(getTemp(), getLight());
                }
            }
            else if(Pres.isSelected()){
                lineChart.getData().setAll(getTemp(), getPres());
            }
            else{
                lineChart.getData().setAll(getTemp());
            }
        }
        else if(Light.isSelected()){
            if(Pres.isSelected()){
                lineChart.getData().setAll(getLight(), getPres());
            }
            else{
                yAxis.setLabel("Light");
                yAxis.setTickLabelsVisible(true);
                lineChart.getData().setAll(getLight());
            }
        }
        else if(Pres.isSelected()){
            yAxis.setLabel("Pressure");
            yAxis.setTickLabelsVisible(true);
            lineChart.getData().setAll(getPres());
        }
        else{
            yAxis.setLabel("");
            yAxis2.setLabel("Testing");

            yAxis.setTickLabelsVisible(false);
            lineChart.getData().setAll();
        }

    }
    public XYChart.Series<Integer, Double> getLight(){

        //Creating graph for Light

        XYChart.Series<Integer, Double> light = new XYChart.Series<>();
        light.getData().add(new XYChart.Data<>(1, 200.0));
        light.getData().add(new XYChart.Data<>(2, 400.0));
        light.getData().add(new XYChart.Data<>(3, 180.0));
        light.getData().add(new XYChart.Data<>(4, 200.0));
        light.getData().add(new XYChart.Data<>(5, 302.0));
        light.getData().add(new XYChart.Data<>(6, 205.0));
        light.getData().add(new XYChart.Data<>(7, 105.0));
        light.getData().add(new XYChart.Data<>(8, 250.0));
        light.getData().add(new XYChart.Data<>(9, 383.0));
        //Setting Legend name
        light.setName("Light");
        return light;
    }
    public XYChart.Series<Integer, Double> getTemp(){

        //Creating graph for Temp

        XYChart.Series<Integer, Double> temp = new XYChart.Series<>();
        temp.getData().add(new XYChart.Data<>(1, dbc.getTemp()));
        temp.getData().add(new XYChart.Data<>(2, 3.0));
        temp.getData().add(new XYChart.Data<>(3, 29.0));
        temp.getData().add(new XYChart.Data<>(4, 15.0));
        temp.getData().add(new XYChart.Data<>(5, 18.0));
        temp.getData().add(new XYChart.Data<>(6, 25.0));
        temp.getData().add(new XYChart.Data<>(7, 26.0));
        temp.getData().add(new XYChart.Data<>(8, 18.0));
        temp.getData().add(new XYChart.Data<>(9, 24.0));
        temp.getData().add(new XYChart.Data<>(10, 16.0));
        //Setting Legend name
        temp.setName("Temp");
        return temp;
    }
    public XYChart.Series<Integer, Double> getPres(){
        //Creating graph for Pressure
        XYChart.Series<Integer, Double> pres = new XYChart.Series<>();
        pres.getData().add(new XYChart.Data<>(1, 431.4));
        pres.getData().add(new XYChart.Data<>(2, 1253.4));
        pres.getData().add(new XYChart.Data<>(3, 834.4));
        pres.getData().add(new XYChart.Data<>(4, 535.5));
        pres.getData().add(new XYChart.Data<>(5, 2345.45));
        pres.getData().add(new XYChart.Data<>(6, 345.5));
        pres.getData().add(new XYChart.Data<>(7, 3455.55));
        pres.getData().add(new XYChart.Data<>(8, 3453.5));
        pres.getData().add(new XYChart.Data<>(9, 285.5));
        //Setting Legend name
        pres.setName("Pressure");
        return pres;
    }

    public void setMain(AppMain main){
        this.main = main;
    }
    public void setScene1(Scene scene1){
        this.scene1 = scene1;
    }
    @FXML
    public void pressButton2() {
        main.setScene(scene1);
    }

}
