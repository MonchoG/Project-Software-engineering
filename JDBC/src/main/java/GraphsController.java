
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CancellationException;
import java.util.zip.Inflater;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import static jdk.nashorn.internal.objects.Global.Infinity;


public class GraphsController{

    private Scene scene1;
    private AppMain main;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private LineChart<Integer, Double> lineChart;
    @FXML
    private ToggleButton temp;
    @FXML
    private ToggleButton light;
    @FXML
    private ToggleButton pres;
    @FXML
    private ToggleButton humid;
    @FXML
    private ToggleButton rangeDay;
    @FXML
    private ToggleButton rangeWeek;
    @FXML
    private ToggleButton rangeMonth;
//    @FXML
//    private Label week_0;
//    @FXML
//    private Label week_1;
//    @FXML
//    private Label week_2;
//    @FXML
//    private Label week_3;
//    @FXML
//    private Label week_4;
//    @FXML
//    private Label week_5;
//    @FXML
//    private Label week_6;
//    @FXML
//    private Label week_7;

    private int index;
    private boolean week_enabled = false;
    private PopupController pc = new PopupController();
    private DAO dbc = new DAO(DBmanager.getInstance());
    //Getting all arrayLists
    private double[] temperatureDay = new double[25];
    private double[] temperatureWeek = new double[8];
    private double[] temperatureMonth = new double[32];
    private ArrayList<Double> temperatureCustom = new ArrayList<>();
    private double[] lightDay = new double[25];
    private double[] lightWeek = new double[8];
    private double[] lightMonth = new double[32];
    private ArrayList<Double> lightCustom = new ArrayList<>();
    private double[] presDay = new double[25];
    private double[] presWeek = new double[8];
    private double[] presMonth = new double[32];
    private ArrayList<Double> presCustom = new ArrayList<>();
    private double[] humidDay = new double[25];
    private double[] humidWeek = new double[8];
    private double[] humidMonth = new double[32];
    private ArrayList<Double> humidCustom = new ArrayList<>();
    private boolean customDate;

    /**
     * Setting default graph on Temperature
     * Setting Temperature ToggleButton on selected and the others on not selected
     */

    public void initialize(){
        rangeDay.setSelected(true);
        rangeDay.setDisable(true);

        //Temperature is default shown on graph
        temp.setSelected(true);
        light.setSelected(false);
        pres.setSelected(false);
        humid.setSelected(false);
        dataRange();


    }

    /**
     * Checking which variables are selected
     * Showing the selected values in a graph
     */

    public void setLineChart(){
        //Checking Temperature button
        if(temp.isSelected()){
            //Checking Light button
            if(light.isSelected()){
                //Checking Pressure button
                if(pres.isSelected()){
                    //Checking Humidity button
                    if(humid.isSelected()){
                        //All four variables are selected
                        lineChart.getData().setAll(getTemp(), getLight(), getPres(), getHumid());
                    }
                    else{
                        //Temperature, Light and Pressure are selected
                        lineChart.getData().setAll(getTemp(), getLight(), getPres());
                    }
                }
                //Checking Humidity button
                else if(humid.isSelected()){
                    //Temperature, Light and Humidity are selected
                    lineChart.getData().setAll(getTemp(), getLight(), getHumid());
                }
                else{
                    //Temperature and Light are Selected
                    lineChart.getData().setAll(getTemp(), getLight());
                }
            }
            //Checking Pressure button
            else if(pres.isSelected()){
                //Checking Humidity button
                if(humid.isSelected()){
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
                if(humid.isSelected()){
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
        else if(light.isSelected()){
            //Checking Pressure button
            if(pres.isSelected()){
                //Checking Humidity button
                if(humid.isSelected()){
                    //Light, Pressure and Humidity are selected
                    lineChart.getData().setAll(getLight(), getPres(), getHumid());
                }
                else{
                    //Light and Pressure are Selected
                    lineChart.getData().setAll(getLight(), getPres());
                }
            }
            else if(humid.isSelected()){
                //Light and Humidity are selected
                lineChart.getData().setAll(getLight(), getHumid());
            }
            else{
                //Only light is selected
                lineChart.getData().setAll(getLight());
            }
        }

        //Checking Pressure button
        else if(pres.isSelected()){
            //Checking Humidity button
            if(humid.isSelected()){
                //Pressure and Humidity are selected
                lineChart.getData().setAll(getPres(), getHumid());
            }
            else{
                //Only Pressure is selected
                lineChart.getData().setAll(getPres());
            }

        }
        //Checking Humidity button
        else if(humid.isSelected()){
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

    private XYChart.Series<Integer, Double> getLight() {
        //Creating graph for Light
        XYChart.Series<Integer, Double> light = new XYChart.Series<>();
        if (customDate) {
            index = dbc.getBeginDay();
            for (int i = 0; i < lightCustom.size(); i++) {
                if(lightCustom.get(i) == -Infinity){
                    index++;
                }else {
                    light.getData().add(new XYChart.Data<>(index + i, lightCustom.get(i)));
                }
            }
        } else if (rangeDay.isSelected()) {
            int count = 0;
            for (int i = lightDay.length - 1; i >= 0; i--) {
                if(lightDay[i] == -Infinity){
                    count++;
                }else {
                    light.getData().add(new XYChart.Data<>(count, lightDay[i]));
                    count++;
                }
            }

        } else if (rangeWeek.isSelected()) {
            int count = 0;
            for (int i = lightWeek.length - 1; i >= 0; i--) {
                if(lightWeek[i] == -Infinity){
                    count++;
                }else {
                    light.getData().add(new XYChart.Data<>(count, lightWeek[i]));
                    count++;
                }
            }
        } else if (rangeMonth.isSelected()) {
            int count = 0;
            for (int i = lightMonth.length - 1; i >= 0; i--) {
                if(lightMonth[i] == -Infinity){
                    count++;
                }else {
                    light.getData().add(new XYChart.Data<>(count, lightMonth[i]));
                    count++;
                }
            }
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

    private XYChart.Series<Integer, Double> getTemp() {
        //Creating graph for Temp
        XYChart.Series<Integer, Double> temp = new XYChart.Series<>();
        if (customDate) {
            index = dbc.getBeginDay();
            for (int i = 0; i < temperatureCustom.size(); i++) {
                if(temperatureCustom.get(i) == -Infinity){
                    index++;
                }else {
                    temp.getData().add(new XYChart.Data<>(index + i, temperatureCustom.get(i)));
                }
            }
        } else if (rangeDay.isSelected()) {
            int count = 0;
            for (int i = (temperatureDay.length-1); i >= 0; i--) {
                int j = i;
                //int k = i;
                if(temperatureDay[j] == -Infinity){
                        count++;

                    //double temp1;
                    /*if(j == -1){
                        temp1 = 0;
                    }else {
                        temp1 = temperatureDay[j];
                    }
                    double temp2;
                    while(temperatureDay[k] == -Infinity){
                        if(k == 25){
                            break;
                        }
                        k++;
                    }
                    /*if(k == 25) {
                        temp2 = 0;
                    }else {
                        temp2 = temperatureDay[k];
                    }
                    System.out.println("temp1 " + temp1 + "\ntemp2 " + temp2);
                    if(temp1 == 0){
                        temp.getData().add(new XYChart.Data<>(count, temp2));
                    }else if(temp2 == 0) {
                        temp.getData().add(new XYChart.Data<>(count, temp1));
                    }else{
                        temp.getData().add(new XYChart.Data<>(count, (temp1+temp2)/2));
                    }

                    //count++;*/
                }else {
                    temp.getData().add(new XYChart.Data<>(count, temperatureDay[i]));
                    count++;
                }

            }

        } else if (rangeWeek.isSelected()) {
            int count = 0;
            for (int i = temperatureWeek.length - 1; i >= 0; i--) {
                if(temperatureWeek[i] == -Infinity){
                    count++;
                }else {
                    temp.getData().add(new XYChart.Data<>(count, temperatureWeek[i]));
                    count++;
                }
            }
        } else if (rangeMonth.isSelected()) {
            int count = 0;
            for (int i = temperatureMonth.length - 1; i >= 0; i--) {
                if(temperatureMonth[i] == -Infinity){
                    count++;
                }else {
                    temp.getData().add(new XYChart.Data<>(count, temperatureMonth[i]));
                    count++;
                }
            }
        }
            //Setting Legend name
            temp.setName("Temp (°C)");
            return temp;
        /**
         * Creating a XYChart
         * Adding all values into the graph
         * @return Pressure XYChart with all data in it
         */
    }
    private XYChart.Series<Integer, Double> getPres() {
            //Creating graph for Pressure
            XYChart.Series<Integer, Double> pres = new XYChart.Series<>();
            if (customDate) {
                index = dbc.getBeginDay();
                for (int i = 0; i < presCustom.size(); i++) {
                    if(presCustom.get(i) == -Infinity){
                        index++;
                    }else {
                        pres.getData().add(new XYChart.Data<>(index + i, presCustom.get(i)));
                    }
                }
            } else if (rangeDay.isSelected()) {
                int count = 0;
                for (int i = presDay.length - 1; i >= 0; i--) {
                    if(presDay[i] == -Infinity){
                        count++;
                    }
                    else{
                        pres.getData().add(new XYChart.Data<>(count++, presDay[i]));
                    }
                }
            } else if (rangeWeek.isSelected()) {
                int count = 0;
                for (int i = presWeek.length - 1; i >= 0; i--) {
                    if(presWeek[i] == -Infinity){
                        count++;
                    }else {
                        pres.getData().add(new XYChart.Data<>(count, presWeek[i]));
                        count++;
                    }
                }
            } else if (rangeMonth.isSelected()) {
                int count = 0;
                for (int i = presMonth.length - 1; i >= 0; i--) {
                    if(presMonth[i] == -Infinity){
                        count++;
                    }else {
                        pres.getData().add(new XYChart.Data<>(count, presMonth[i]));
                        count++;
                    }
                }
            }
            //Setting Legend name
            pres.setName("Pressure / 10 (bar)");

            return pres;
    }

    /**
     * Creating a XYChart
     * Adding all values into the graph
     * @return Humidity XYChart with all data in it
     */

    private XYChart.Series<Integer, Double> getHumid() {
            //Creating graph for Pressure


            XYChart.Series<Integer, Double> humid = new XYChart.Series<>();
            if (customDate) {
                index = dbc.getBeginDay();
                for (int i = 0; i < humidCustom.size(); i++) {
                    if(humidCustom.get(i) == -Infinity){
                        index++;
                    }else {
                        humid.getData().add(new XYChart.Data<>(index + i, humidCustom.get(i)));
                    }
                }
            } else if (rangeDay.isSelected()) {
                int count = 0;
                for (int i = humidDay.length - 1; i >= 0; i--) {
                    if(humidDay[i] == -Infinity){
                       count++;
                    }else {
                        humid.getData().add(new XYChart.Data<>(count, humidDay[i]));
                    }
                    count++;
                }
            } else if (rangeWeek.isSelected()) {
                int count = 0;
                for (int i = humidWeek.length - 1; i >= 0; i--) {
                    if(humidWeek[i] == -Infinity){
                        count++;
                    }else {
                        humid.getData().add(new XYChart.Data<>(count, humidWeek[i]));
                        count++;
                    }
                }
            } else if (rangeMonth.isSelected()) {
                int count = 0;
                for (int i = humidMonth.length - 1; i >= 0; i--) {
                    if(humidMonth[i] == -Infinity){
                        count++;
                    }else {
                        humid.getData().add(new XYChart.Data<>(count, humidMonth[i]));
                        count++;
                    }
                }
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
    private void dataRange(){
        ToggleGroup group=new ToggleGroup();
        rangeDay.setToggleGroup(group);
        rangeWeek.setToggleGroup(group);
        rangeMonth.setToggleGroup(group);
        xAxis.setTickLabelsVisible(false);
        if(rangeDay.isSelected()){
            //setWeekDisable();
            temperatureDay = dbc.getDay("temperature");
            lightDay = dbc.getDay("light");
            presDay = dbc.getDay("pressure");
            humidDay = dbc.getDay("humidity");
            xAxis.setLowerBound(0);
            xAxis.setUpperBound(24);
            xAxis.setLabel("Hours ago");
            rangeDay.setDisable(true);
            rangeWeek.setDisable(false);
            rangeWeek.setSelected(false);
            rangeMonth.setDisable(false);
            rangeMonth.setSelected(false);
            customDate = false;
            setLineChart();
        };
        if(rangeWeek.isSelected()){
            //setWeekEnable();
            temperatureWeek = dbc.getWeek("temperature");
            lightWeek = dbc.getWeek("light");
            presWeek = dbc.getWeek("pressure");
            humidWeek = dbc.getWeek("humidity");
            xAxis.setLowerBound(0);
            xAxis.setUpperBound(7);
            xAxis.setLabel("Days ago");
            rangeDay.setDisable(false);
            rangeDay.setSelected(false);
            rangeWeek.setDisable(true);
            rangeMonth.setDisable(false);
            rangeMonth.setSelected(false);
            customDate=false;
            setLineChart();
        };
        if(rangeMonth.isSelected()){
            //setWeekDisable();
            temperatureMonth = dbc.getMonth("temperature");
            lightMonth = dbc.getMonth("light");
            presMonth = dbc.getMonth("pressure");
            humidMonth = dbc.getMonth("humidity");
            xAxis.setLowerBound(0);
            xAxis.setUpperBound(30);
            xAxis.setLabel("Days ago");
            rangeDay.setDisable(false);
            rangeDay.setSelected(false);
            rangeWeek.setDisable(false);
            rangeWeek.setSelected(false);
            rangeMonth.setDisable(true);
            customDate=false;
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
        //setWeekDisable();
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
            int month = Integer.parseInt(dbc.getBeginDate().substring(5, 7));
            xAxis.setLabel(getMonth(month));
            xAxis.setTickLabelsVisible(true);
            setLineChart();
        }else{
            System.out.println("Invalid value has been entered");
        }

    }
//    private void setWeekEnable(){
//            week_0.setVisible(true);
//            week_1.setVisible(true);
//            week_2.setVisible(true);
//            week_3.setVisible(true);
//            week_4.setVisible(true);
//            week_5.setVisible(true);
//            week_6.setVisible(true);
//            week_7.setVisible(true);
//    }
//    private void setWeekDisable(){
//            week_0.setVisible(false);
//            week_1.setVisible(false);
//            week_2.setVisible(false);
//            week_3.setVisible(false);
//            week_4.setVisible(false);
//            week_5.setVisible(false);
//            week_6.setVisible(false);
//            week_7.setVisible(false);
//    }
    private String getMonth(int month){
        String monthString;
        switch (month){
            case 1: monthString = "January";
                break;
            case 2: monthString = "February";
                break;
            case 3: monthString = "March";
                break;
            case 4: monthString = "April";
                break;
            case 5: monthString = "May";
                break;
            case 6: monthString = "June";
                break;
            case 7: monthString = "July";
                break;
            case 8: monthString = "August";
                break;
            case 9: monthString = "September";
                break;
            case 10: monthString = "October";
                break;
            case 11: monthString = "November";
                break;
            case 12: monthString = "December";
                break;
            default: monthString = "Days";
                break;
        }
        return monthString;
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
