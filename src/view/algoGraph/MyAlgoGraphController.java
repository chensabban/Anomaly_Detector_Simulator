package view.algoGraph;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class MyAlgoGraphController {

    @FXML
    private NumberAxis x ;
    @FXML
    private NumberAxis y ;

    @FXML
    LineChart Algo_line_chart;

    XYChart.Series learning_points;
    XYChart.Series algo_points;
    XYChart.Series good_points;
    XYChart.Series v_points;
    Queue<Float> queue;
    int count;
    public MyAlgoGraphController() {
        learning_points = new XYChart.Series();
        algo_points = new XYChart.Series();
        good_points = new XYChart.Series();
        v_points = new XYChart.Series();
        queue = new ArrayDeque<>(30);
        count=0;

    }

    public void add_series(){
//        Algo_line_chart.setAnimated(false);
//        learning_points.getNode().setStyle("-fx-stroke: transparent;-fx-background-color: #bcbebd;");
//        algo_points.getNode().setStyle("-fx-stroke: #9676a2;-fx-background-color: transparent, transparent;");
//        good_points.getNode().setStyle("-fx-stroke: transparent;-fx-background-color: green;");
//        v_points.getNode().setStyle("-fx-stroke: transparent;-fx-background-color: red;");
          Algo_line_chart.getData().addAll(algo_points,learning_points,good_points,v_points);

    }

    public void clear() {
        Algo_line_chart.setAnimated(false);
        learning_points.getData().clear();
        algo_points.getData().clear();
        good_points.getData().clear();
        v_points.getData().clear();
        queue.clear();
        count=0;
        Algo_line_chart.setAnimated(true);
    }

    public void set_algo_setting(List<XYChart.Series> data) {
        clear();
        if(data==null){
            System.out.println("not hava enough coraltion to use this algo_detect");
            algo_points.setName("This feature doesn't have coraltion");
            Algo_line_chart.setTitle("No-Algo");
//            return;
        }
        //changes
        else {

            for (int i = 0; i < data.get(0).getData().size(); i++) {
                Object x = data.get(0).getData().get(i);
                if (!learning_points.getData().contains(x))
                    learning_points.getData().add(x);
            }
            for (Object x : data.get(1).getData()) {
                algo_points.getData().add(x);
            }
            Algo_line_chart.setCreateSymbols(true);
            learning_points.setName(data.get(0).getName());
            algo_points.setName(data.get(1).getName());
            good_points.setName("Normal points");
            v_points.setName("Exceptional points");
            Algo_line_chart.setTitle(algo_points.getName());
            x.setAutoRanging(false);
            y.setAutoRanging(false);
            float max_x, max_y, min_x, min_y, size;
            min_x = Float.parseFloat(((XYChart.Data<?, ?>) data.get(2).getData().get(0)).getXValue().toString());
            max_x = Float.parseFloat(((XYChart.Data<?, ?>) data.get(2).getData().get(0)).getYValue().toString());
            min_y = Float.parseFloat(((XYChart.Data<?, ?>) data.get(2).getData().get(1)).getXValue().toString());
            max_y = Float.parseFloat(((XYChart.Data<?, ?>) data.get(2).getData().get(1)).getYValue().toString());
            size = Float.parseFloat(((XYChart.Data<?, ?>) data.get(2).getData().get(2)).getYValue().toString());
            count -= size;
            x.setLowerBound(min_x * (float) (1.2));
            x.setUpperBound(max_x * (float) (1.2));
            y.setLowerBound(min_y * (float) (1.2));
            y.setUpperBound(max_y * (float) (1.2));
        }
    }

    synchronized public void add_p_paint(float[] point){
        if(point==null){return;}
        float step;
        if(count==0){
            count++;
            step =queue.poll();
            if(step==(float)1)
                v_points.getData().remove(0);
            else
                good_points.getData().remove(0);
        }
        Platform.runLater(() -> {
        if (point[2] == (float) 1)
            v_points.getData().add(new XYChart.Data(point[0], point[1]));
        else
            good_points.getData().add(new XYChart.Data(point[0], point[1]));

        });
            count++;
            queue.add(point[2]);
    }

    public void clear_detect() {
        good_points.getData().clear();
        v_points.getData().clear();
        queue.clear();
        count=0;
    }
}