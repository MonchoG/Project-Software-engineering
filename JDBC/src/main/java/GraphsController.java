
import java.math.BigInteger;
import java.sql.Timestamp;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;


public class GraphsController{

    private Scene scene1;
    private AppMain main;
    @FXML
    private NumberAxis yAxis;
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

    Timestamp endDate ;
    Timestamp beginDate;
    private int day = 86400000;
    private long month = Long.parseUnsignedLong("2592000000");
    private long week = 604800000;
    //Creating connection without needing to open MySQL
    private DAO dbc = new DAO(DBmanager.getInstance());

    /**
     * Setting default graph on Temperature
     * Setting Temperature ToggleButton on selected and the others on not selected
     */

    public void initialize(){
        lineChart.getData().setAll(getTemp());
        //Temperature is default shown on graph
        Temp.setSelected(true);
        Light.setSelected(false);
        Pres.setSelected(false);
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

    public XYChart.Series<Integer, Double> getLight(){
        //Creating graph for Light
        XYChart.Series<Integer, Double> light = new XYChart.Series<>();

        if(rangeDay.isSelected()){
            for(int i = 0; i <= 24; i++){
                light.getData().add(new XYChart.Data<>(i, i+8.0));
            }
        }
        else if(rangeWeek.isSelected()){
            for(int i = 0; i <= 7; i++){
                light.getData().add(new XYChart.Data<>(i, i*10.0));
            }
        }
        else if(rangeMonth.isSelected()) {
            for (int i = 0; i <= 30; i++) {
                light.getData().add(new XYChart.Data<>(i, i + 5.0));
            }
        }else {
            light.getData().add(new XYChart.Data<>(0, 0.0));
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

    public XYChart.Series<Integer, Double> getTemp(){
        //Creating graph for Temp
        XYChart.Series<Integer, Double> temp = new XYChart.Series<>();
        if(rangeDay.isSelected()){
            for(int i = 0; i <= 24; i++){
                temp.getData().add(new XYChart.Data<>(i, i*2.0));
            }
        }
        else if(rangeWeek.isSelected()){
            for(int i = 0; i <= 7; i++){
                temp.getData().add(new XYChart.Data<>(i, i*5.0));
            }
        }
        else if(rangeMonth.isSelected()){
            for(int i = 0; i <= 30; i++){
                temp.getData().add(new XYChart.Data<>(i, 2*i+5.0));
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

    public XYChart.Series<Integer, Double> getPres(){
        //Creating graph for Pressure
        XYChart.Series<Integer, Double> pres = new XYChart.Series<>();

        if(rangeDay.isSelected()){
            for(int i = 0; i <= 24; i++){
                pres.getData().add(new XYChart.Data<>(i, i*4.0));
            }
        }
        else if(rangeWeek.isSelected()){
            for(int i = 0; i <= 7; i++){
                pres.getData().add(new XYChart.Data<>(i, i*8.0));
            }
        }
        else if(rangeMonth.isSelected()){
            for(int i = 0; i <= 30; i++){
                pres.getData().add(new XYChart.Data<>(i, 2*i+20.0));
            }

        }else {

            pres.getData().add(new XYChart.Data<>(0, 0.0));
            pres.getData().add(new XYChart.Data<>(1, 88.0));
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

    public XYChart.Series<Integer, Double> getHumid(){
        //Creating graph for Pressure
        XYChart.Series<Integer, Double> humid = new XYChart.Series<>();

        if(rangeDay.isSelected()){
            for(int i = 0; i <= 24; i++){
                humid.getData().add(new XYChart.Data<>(i, i*3.0));
            }
        }
        else if(rangeWeek.isSelected()){
            for(int i = 0; i <= 7; i++){
                humid.getData().add(new XYChart.Data<>(i, i*6.0));
            }
        }
        else if(rangeMonth.isSelected()){
            for(int i = 0; i <= 30; i++){
                humid.getData().add(new XYChart.Data<>(i, 3*i+10.0));
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
            endDate=new Timestamp(System.currentTimeMillis());
            beginDate = new Timestamp(System.currentTimeMillis()-day);
            System.out.println(endDate);
            System.out.println(beginDate);
            xAxis.setUpperBound(24);
            xAxis.setLabel("Hours");
            setLineChart();
            rangeDay.setDisable(true);
            rangeWeek.setDisable(false);
            rangeWeek.setSelected(false);
            rangeMonth.setDisable(false);
            rangeMonth.setSelected(false);

        };
        if(rangeWeek.isSelected()) {
            endDate=new Timestamp(System.currentTimeMillis());
            beginDate = new Timestamp(System.currentTimeMillis()-week);
            System.out.println(endDate);
            System.out.println(beginDate);
            xAxis.setUpperBound(7);
            xAxis.setLabel("Days");
            setLineChart();
            rangeDay.setDisable(false);
            rangeDay.setSelected(false);
            rangeWeek.setDisable(true);
            rangeMonth.setDisable(false);
            rangeMonth.setSelected(false);

        };
        if(rangeMonth.isSelected()){
            endDate=new Timestamp(System.currentTimeMillis());
            beginDate = new Timestamp(System.currentTimeMillis() - month);
            System.out.println(endDate);
            System.out.println(beginDate);
            xAxis.setUpperBound(30);
            xAxis.setLabel("Days");
            setLineChart();
            rangeDay.setDisable(false);
            rangeDay.setSelected(false);
            rangeWeek.setDisable(false);
            rangeWeek.setSelected(false);
            rangeMonth.setDisable(true);
        };

    }

    /**
     * customDate
     * initializes PopupController class and calls display method
     */

    @FXML
    private void customDate() {
        PopupController popupController=new PopupController();
        popupController.display();

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
