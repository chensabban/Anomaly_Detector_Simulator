package view.algoGraph;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.algorithms.Helpclass.Line;

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

    XYChart.Series good_points;
    XYChart.Series v_points;
    Queue<Float> queue;
    int count;
    public MyAlgoGraphController() {
        queue = new ArrayDeque<>(30);
        count=0;
    }


    public void clear() {
        Algo_line_chart.setAnimated(false);
        Algo_line_chart.getData().clear();
        good_points = new XYChart.Series();
        v_points = new XYChart.Series();
        good_points.setName("Good Points");
        v_points.setName("Bad Points");
        queue.clear();
    }

    public void set_algo_setting(List<XYChart.Series> data) {
        Algo_line_chart.setCreateSymbols(true);
        if (data == null) {
         //   Algo_line_chart.setTitle("No-Algo");
        }else {
               Platform.runLater(()->{
                   //Algo_line_chart.setTitle(data.get(1).getName());
            Algo_line_chart.getData().add(data.get(1));
            Algo_line_chart.getData().add(data.get(0));
            Algo_line_chart.getData().add(good_points);
            Algo_line_chart.getData().add(v_points);
            x.setAutoRanging(false);
            y.setAutoRanging(false);
            float max_x, max_y, min_x, min_y, size;
            min_x = Float.parseFloat(((XYChart.Data<?, ?>) data.get(2).getData().get(0)).getXValue().toString());
            max_x = Float.parseFloat(((XYChart.Data<?, ?>) data.get(2).getData().get(0)).getYValue().toString());
            min_y = Float.parseFloat(((XYChart.Data<?, ?>) data.get(2).getData().get(1)).getXValue().toString());
            max_y = Float.parseFloat(((XYChart.Data<?, ?>) data.get(2).getData().get(1)).getYValue().toString());
               size = Float.parseFloat(((XYChart.Data<?, ?>) data.get(2).getData().get(2)).getYValue().toString());
               count = 0 - (int) size;
               System.out.print(size);
            x.setLowerBound(min_x * 1.2);
            x.setUpperBound(max_x * 1.2);
            y.setLowerBound(min_y * 1.2);
            y.setUpperBound(max_y * 1.2);
            x.setAutoRanging(true);
            y.setAutoRanging(true);
            Algo_line_chart.setAnimated(true);
               });
          }

    }

     public void add_p_paint(float[] point){
        if(point==null){return;}
        Platform.runLater(()->{
            float step;
        if(count==0){
            count--;
            step =queue.poll();
            if(step==1)
                v_points.getData().remove(0);
            else
                good_points.getData().remove(0);
        }
        if (point[2] == 1) {
            v_points.getData().add(new XYChart.Data(point[0], point[1]));
        }else
            good_points.getData().add(new XYChart.Data(point[0], point[1]));

            count++;
            queue.add(point[2]);
         });
    }

}