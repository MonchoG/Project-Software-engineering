
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CancellationException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;


public class GraphsController{

    private Scene scene1;
    private AppMain main;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private LineChart<Integer, Double> lineChart;
    @FXML
    private ToggleButton Temp;
    @FXML
    private ToggleButton Light;
    @FXML
    private ToggleButton Pres;
    @FXML
    private ToggleButton Humid;
    @FXML
    private ToggleButton rangeDay;
    @FXML
    private ToggleButton rangeWeek;
    @FXML
    private ToggleButton rangeMonth;
    private int index;
    private PopupController pc = new PopupController();
    private DAO dbc = new DAO(DBmanager.getInstance());
    //Getting all arrayLists
    private ArrayList<Double> temperatureDay;
    private ArrayList<Double> temperatureWeek;
    private ArrayList<Double> temperatureMonth;
    private ArrayList<Double> temperatureCustom;
    private ArrayList<Double> lightDay;
    private ArrayList<Double> lightWeek;
    private ArrayList<Double> lightMonth;
    private ArrayList<Double> lightCustom;
    private ArrayList<Double> presDay;
    private ArrayList<Double> presWeek;
    private ArrayList<Double> presMonth;
    private ArrayList<Double> presCustom;
    private ArrayList<Double> humidDay;
    private ArrayList<Double> humidWeek;
    private ArrayList<Double> humidMonth;
    private ArrayList<Double> humidCustom;
    private boolean customDate;

    /**
     * Setting default graph on Temperature
     * Setting Temperature ToggleButton on selected and the others on not selected
     */

    public void initialize(){
        lineChart.getData().set(getTemp());
        //Temperature is default shown on graph

    }

    /**
     * Checking which variables are selected
     * Showing the selected values in a graph
     */

    public void setLineChart(){
        //Checking Temperature button
        if(Temp.isSelected()){
            //Checking Light button
            if(Light.isSelected()){
                //Checking Pressure button
                if(Pres.isSelected()){
                    //Checking Humidity button
                    if(Humid.isSelected()){
                        //All four variables are selected
                        lineChart.getData().setAll(getTemp(), getLight(), getPres(), getHumid());
                    }
                    else{
                        //Temperature, Light and Pressure are selected
                        lineChart.getData().setAll(getTemp(), getLight(), getPres());
                    }
                }
                //Checking Humidity button
                else if(Humid.isSelected()){
                    //Temperature, Light and Humidity are selected
                    lineChart.getData().setAll(getTemp(), getLight(), getHumid());
                }
                else{
                    //Temperature and Light are Selected
                    lineChart.getData().setAll(getTemp(), getLight());
                }
            }
            //Checking Pressure button
            else if(Pres.isSelected()){
                //Checking Humidity button
                if(Humid.isSelected()){
                    //Temperature, Pressure and Humidity are selected
                    lineChart.getData().setAll(getTemp(), getPres(), getHumid());
                }
                else{
                    //Temperature and Pressure are selected
                    lineChart.getData().setAll(getTemp(), getPres());
                }
            }
            else{
                //Checking Humidity button
                if(Humid.isSelected()){
                    //Temperature and Humidity are selected
                    lineChart.getData().setAll(getTemp(), getHumid());
                }
                else{
                    //Only Temperature is selected
                    lineChart.getData().setAll(getTemp());
                }
            }
        }

        //Checking Light button
        else if(Light.isSelected()){
            //Checking Pressure button
            if(Pres.isSelected()){
                //Checking Humidity button
                if(Humid.isSelected()){
                    //Light, Pressure and Humidity are selected
                    lineChart.getData().setAll(getLight(), getPres(), getHumid());
                }
                else{
                    //Light and Pressure are Selected
                    lineChart.getData().setAll(getLight(), getPres());
                }
            }
            else if(Humid.isSelected()){
                //Light and Humidity are selected
                lineChart.getData().setAll(getLight(), getHumid());
            }
            else{
                //Only light is selected
                lineChart.getData().setAll(getLight());
            }
        }

        //Checking Pressure button
        else if(Pres.isSelected()){
            //Checking Humidity button
            if(Humid.isSelected()){
                //Pressure and Humidity are selected
                lineChart.getData().setAll(getPres(), getHumid());
            }
            else{
                //Only Pressure is selected
                lineChart.getData().setAll(getPres());
            }

        }
        //Checking Humidity button
        else if(Humid.isSelected()){
            //Only Humidity is selected
            lineChart.getData().setAll(getHumid());
        }
        else{
            //None are selected
            lineChart.getData().setAll();
        }
    }

    /**
     * Creating XYChart
     * Adding all values in it
     * @return Light XYChart with all data in it
     */

    private XYChart.Series<Integer, Double> getLight(){
        //Creating graph for Light
        XYChart.Series<Integer, Double> light = new XYChart.Series<>();
        if(customDate){
            for(int i = 0; i < lightCustom.size(); i++){
                index = dbc.getBeginDay();
                light.getData().add(new XYChart.Data<>(index+i, lightCustom.get(i)));
            }
        }
        else if(rangeDay.isSelected()){
            for(int i = 0; i < lightDay.size(); i++){
              light.getData().add(new XYChart.Data<>(i, lightDay.get(i)));
            }

        }
        else if(rangeWeek.isSelected()){
            for(int i = 0; i < lightWeek.size(); i++){
                light.getData().add(new XYChart.Data<>(i, lightWeek.get(i)));
            }
        }
        else if(rangeMonth.isSelected()) {
            for (int i = 0; i < lightMonth.size(); i++) {
                light.getData().add(new XYChart.Data<>(i, lightMonth.get(i)));
            }
        }else {
            light.getData().add(new XYChart.Data<>(0, dbc.getLight()));
            light.getData().add(new XYChart.Data<>(1, 44.0));
            light.getData().add(new XYChart.Data<>(2, 18.0));
            light.getData().add(new XYChart.Data<>(3, 33.0));
            light.getData().add(new XYChart.Data<>(4, 88.0));
            light.getData().add(new XYChart.Data<>(5, 55.0));
            light.getData().add(new XYChart.Data<>(6, 30.0));
            light.getData().add(new XYChart.Data<>(7, 88.0));
            light.getData().add(new XYChart.Data<>(8, 36.0));
            light.getData().add(new XYChart.Data<>(9, 55.0));
        }
        //Setting Legend name
        light.setName("Light (lx)");

        return light;
    }

    /**
     * Creating a XYChart
     * Adding all values in it
     * @return Temperature XYChart with all data in it
     */

    private XYChart.Series<Integer, Double> getTemp(){
        //Creating graph for Temp
        XYChart.Series<Integer, Double> temp = new XYChart.Series<>();
        if(customDate){
            for(int i = 0; i < temperatureCustom.size(); i++){
                index = dbc.getBeginDay();
                temp.getData().add(new XYChart.Data<>(index+i, temperatureCustom.get(i)));
            }
        }
        else if(rangeDay.isSelected()){
            for(int i = 0; i < temperatureDay.size(); i++){
                temp.getData().add(new XYChart.Data<>(i, temperatureDay.get(i)));
            }
        }
        else if(rangeWeek.isSelected()){
            for(int i = 0; i < temperatureWeek.size(); i++){
                temp.getData().add(new XYChart.Data<>(i, temperatureWeek.get(i)));
            }
        }
        else if(rangeMonth.isSelected()){
            for(int i = 0; i < temperatureMonth.size(); i++){
                temp.getData().add(new XYChart.Data<>(i, temperatureMonth.get(i)));
            }
        }else{

            temp.getData().add(new XYChart.Data<>(0, 0.0));
            temp.getData().add(new XYChart.Data<>(1, dbc.getTemp()));
            temp.getData().add(new XYChart.Data<>(2, 3.0));
            temp.getData().add(new XYChart.Data<>(3, 29.0));
            temp.getData().add(new XYChart.Data<>(4, 15.0));
            temp.getData().add(new XYChart.Data<>(5, 18.0));
            temp.getData().add(new XYChart.Data<>(6, 25.0));
            temp.getData().add(new XYChart.Data<>(7, 26.0));
            temp.getData().add(new XYChart.Data<>(8, 18.0));
            temp.getData().add(new XYChart.Data<>(9, 24.0));
        }

        //Setting Legend name
        temp.setName("Temp (°C)");
        return temp;
    }

    /**
     * Creating a XYChart
     * Adding all values into the graph
     * @return Pressure XYChart with all data in it
     */

    private XYChart.Series<Integer, Double> getPres(){
        //Creating graph for Pressure
        XYChart.Series<Integer, Double> pres = new XYChart.Series<>();
        if(customDate){
            for(int i = 0; i < presCustom.size(); i++){
                index = dbc.getBeginDay();
                pres.getData().add(new XYChart.Data<>(index+i, presCustom.get(i)));
            }
        }
        else if(rangeDay.isSelected()){
            for(int i = 0; i < presDay.size(); i++){
                pres.getData().add(new XYChart.Data<>(i, presDay.get(i)));
            }
        }
        else if(rangeWeek.isSelected()){
            for(int i = 0; i < presWeek.size(); i++){
                pres.getData().add(new XYChart.Data<>(i, presWeek.get(i)));
            }
        }
        else if(rangeMonth.isSelected()) {
            for (int i = 0; i < presMonth.size(); i++) {
                pres.getData().add(new XYChart.Data<>(i, presMonth.get(i)));
            }
        }else {

            pres.getData().add(new XYChart.Data<>(0, 0.0));
            pres.getData().add(new XYChart.Data<>(1, dbc.getPressure()));
            pres.getData().add(new XYChart.Data<>(2, 30.0));
            pres.getData().add(new XYChart.Data<>(3, 40.0));
            pres.getData().add(new XYChart.Data<>(4, 18.0));
            pres.getData().add(new XYChart.Data<>(5, 33.0));
            pres.getData().add(new XYChart.Data<>(6, 80.0));
            pres.getData().add(new XYChart.Data<>(7, 44.0));
            pres.getData().add(new XYChart.Data<>(8, 99.0));
            pres.getData().add(new XYChart.Data<>(9, 25.0));
        }
        //Setting Legend name
        pres.setName("Pressure (atm)");

        return pres;
    }

    /**
     * Creating a XYChart
     * Adding all values into the graph
     * @return Humidity XYChart with all data in it
     */

    private XYChart.Series<Integer, Double> getHumid(){
        //Creating graph for Pressure
        XYChart.Series<Integer, Double> humid = new XYChart.Series<>();
        if(customDate){
            for(int i = 0; i < humidCustom.size(); i++){
                index = dbc.getBeginDay();
                humid.getData().add(new XYChart.Data<>(index+i, humidCustom.get(i)));
            }
        }
        else if(rangeDay.isSelected()){
            for(int i = 0; i <= 24; i++){
                humid.getData().add(new XYChart.Data<>(i, humidDay.get(i)));
            }
        }
        else if(rangeWeek.isSelected()){
            for(int i = 0; i <= 7; i++){
                humid.getData().add(new XYChart.Data<>(i, humidWeek.get(i)));
            }
        }
        else if(rangeMonth.isSelected()){
            for(int i = 0; i <= 30; i++){
                humid.getData().add(new XYChart.Data<>(i, humidMonth.get(i)));
            }

        }else {
            humid.getData().add(new XYChart.Data<>(0, -50.0));
            humid.getData().add(new XYChart.Data<>(1, 25.0));
            humid.getData().add(new XYChart.Data<>(2, 44.0));
            humid.getData().add(new XYChart.Data<>(3, 53.0));
            humid.getData().add(new XYChart.Data<>(4, 28.0));
            humid.getData().add(new XYChart.Data<>(5, 33.0));
            humid.getData().add(new XYChart.Data<>(6, 39.0));
            humid.getData().add(new XYChart.Data<>(7, 42.0));
            humid.getData().add(new XYChart.Data<>(8, 28.0));
            humid.getData().add(new XYChart.Data<>(9, 88.0));
        }
        //Setting Legend name
        humid.setName("Humidity (%)");

        return humid;
    }

    /**dataRange
     * initializes ToggleGroup and adds rangeDay, rangeWeek, rangeMonth to it,
     * sets action listeners for these buttons
     */
    @FXML
    private void dataRange() {
        ToggleGroup group = new ToggleGroup();
        rangeDay.setToggleGroup(group);
        rangeWeek.setToggleGroup(group);
        rangeMonth.setToggleGroup(group);
        if(rangeDay.isSelected()) {
            temperatureDay = dbc.getDay("temperature");
            lightDay = dbc.getDay("light");
            presDay = dbc.getDay("pressure");
            humidDay = dbc.getDay("humidity");

            xAxis.setLowerBound(0);
            xAxis.setUpperBound(24);
            xAxis.setLabel("Hours");
            rangeDay.setDisable(true);
            rangeWeek.setDisable(false);
            rangeWeek.setSelected(false);
            rangeMonth.setDisable(false);
            rangeMonth.setSelected(false);
            customDate = false;
            setLineChart();
        };
        if(rangeWeek.isSelected()) {
            temperatureWeek = dbc.getWeek("temperature");
            lightWeek = dbc.getWeek("light");
            presWeek = dbc.getWeek("pressure");
            humidWeek = dbc.getWeek("humidity");
            xAxis.setLowerBound(0);
            xAxis.setUpperBound(7);
            xAxis.setLabel("Days");
            rangeDay.setDisable(false);
            rangeDay.setSelected(false);
            rangeWeek.setDisable(true);
            rangeMonth.setDisable(false);
            rangeMonth.setSelected(false);
            customDate = false;
            setLineChart();

        };
        if(rangeMonth.isSelected()){
            temperatureMonth = dbc.getMonth("temperature");
            lightMonth = dbc.getMonth("light");
            presMonth = dbc.getMonth("pressure");
            humidMonth = dbc.getWeek("humidity");
            xAxis.setLowerBound(0);
            xAxis.setUpperBound(30);
            xAxis.setLabel("Days");
            rangeDay.setDisable(false);
            rangeDay.setSelected(false);
            rangeWeek.setDisable(false);
            rangeWeek.setSelected(false);
            rangeMonth.setDisable(true);
            customDate = false;
            setLineChart();
        };
    }

    /**
     * customDate
     * initializes PopupController class and calls display method
     */

    @FXML
    private void customDate() {
        dbc.custom();
        if(dbc.getBeginDate().charAt(0) != 'n' && dbc.getEndDate().charAt(0) != 'n' ) {
            index = pc.getBeginDay();
            customDate = true;

            temperatureCustom = dbc.getCustom("temperature");
            presCustom = dbc.getCustom("pressure");
            lightCustom = dbc.getCustom("light");
            humidCustom = dbc.getCustom("humidity");
            rangeDay.setDisable(false);
            rangeDay.setSelected(false);
            rangeWeek.setDisable(false);
            rangeWeek.setSelected(false);
            rangeMonth.setDisable(false);
            rangeMonth.setSelected(false);
            double begin = Double.parseDouble(dbc.getBeginDate().substring(8, 10));
            double end = Double.parseDouble(dbc.getEndDate().substring(8, 10));
            xAxis.setLowerBound(begin);
            xAxis.setUpperBound(end);
            setLineChart();
        }else{
            System.out.println("Invalid value has been entered");
        }


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
