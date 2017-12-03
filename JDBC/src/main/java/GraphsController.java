

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GraphsController {

    private Scene scene1;
    private AppMain main;


//    private BlockingQueue<Number> dataQ1 = new ArrayBlockingQueue<Number>(1024);
//    private BlockingQueue<Number> dataQ2 = new ArrayBlockingQueue<Number>(1024);
//
//
//    @FXML private CategoryAxis xAxis;
//    @FXML private NumberAxis yAxis;
//    @FXML final LineChart<String, Number> linechart = new LineChart<String, Number>(xAxis, yAxis);
//


    public void setMain(AppMain main){
        this.main = main;
    }
    public void setScene1(Scene scene1){
        this.scene1 = scene1;
    }
    @FXML
    public void pressButton2() throws IOException {
        main.setScene(scene1);
    }
    @FXML
    public void parameters(){

    }
//here I tried to make graphs work, but haven't figured it out yet :(
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//xAxis.setAutoRanging(false);
//
//    xAxis.setTickLabelsVisible(false);
//    xAxis.setTickMarkVisible(false);
//
//    NumberAxis yAxis = new NumberAxis();
//    yAxis.setAutoRanging(true);

//    //Graph
//    final LineChart<String, Number> lc = new LineChart<String, Number>(xAxis, yAxis){
//        @Override
//        protected void dataItemAdded(Series<String, Number> series, int itemIndex, Data<String, Number> item){}
//    };
//    lc.setAnimated(false);
//    lc.setId("liveLineChart");
//    lc.setTitle("Animated Line Chart");
//
//    //Graph Series
//    series1 = new XYChart.Series<Number, Number>();
//    series2 = new XYChart.Series<Number, Number>();
//    linechart.getData().addAll(series1, series2);
//
//    series1.setName("T1");
//    series2.setName("T2");
//
//    fluidT.setText("0000");
//    gasT.setText("0000");
//
//    prepareTimeline();
//
//    Runnable con = new Consumer(this);
//    Thread c = new Thread(con);
//    c.start();


}
