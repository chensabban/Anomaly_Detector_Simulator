package view.f_list;

import PTM1.Helpclass.StatLib;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;

import java.util.Arrays;

public class F_listController {


    @FXML
    private NumberAxis x_f;
    @FXML
    private NumberAxis y_f;
    @FXML
    private NumberAxis x_c;
    @FXML
    private NumberAxis y_c;


    @FXML
    private LineChart FeatureLineChart;
    @FXML
    private LineChart CorrelatedFeatureLineChart;
    @FXML
    public ListView<String> fList;
    public StringProperty selected_feature;

    XYChart.Series selected_F_vals;
    XYChart.Series Best_C_F_vals;
    float[] f_vals, c_vals;
    float max_y_f, max_y_c;

    public F_listController() {
        selected_F_vals = new XYChart.Series();
        Best_C_F_vals = new XYChart.Series();
        selected_feature = new SimpleStringProperty();
    }

    public void add_series() {
        FeatureLineChart.getData().add(selected_F_vals);
        CorrelatedFeatureLineChart.getData().add(Best_C_F_vals);
    }

    //need cur time val
    public void set_setting(int curtime, float[] vals, float[] vals2,String name_f,String name_b_f) {
        clear();
        //set selected feature linechart setting
        f_vals = vals;
        float y_f_min = StatLib.min(vals);
        selected_F_vals.setName("Feature:" + name_f);

        x_f.setAutoRanging(false);
        x_f.setLowerBound(0);
        x_f.setUpperBound(curtime * (float) (1.1));
        y_f.setLowerBound(y_f_min);
        max_y_f = StatLib.max(Arrays.copyOfRange(f_vals, 0, curtime));
        y_f.setUpperBound(max_y_f * (float) (1.1));

        //set best cor to-selected feature linechart setting
        c_vals = vals2;
        if (c_vals != null) {
            float y_c_min = StatLib.min(vals2);
            Best_C_F_vals.setName("B_C Feature:"+name_b_f);
            x_c.setAutoRanging(false);
            x_c.setLowerBound(0);
            x_c.setUpperBound(curtime * (float) (1.1));
            y_c.setLowerBound(y_c_min);
            max_y_c = StatLib.max(Arrays.copyOfRange(c_vals, 0, curtime));
            y_c.setUpperBound(max_y_c * (float) (1.1));
        }
        if(curtime!=0)
          add_p_paint(0, curtime);
    }

    synchronized public void add_p_paint(int old_time, int new_time) {
        Platform.runLater(() -> {
            if (old_time < new_time) {
                x_f.setUpperBound(new_time * (float) (1.1));
                x_c.setUpperBound(new_time * (float) (1.1));

                for (int i = old_time + 1; i <= new_time; i++) {
                    selected_F_vals.getData().add(new XYChart.Data(i, f_vals[i]));
                    if (f_vals[i] > max_y_f) {
                        max_y_f = f_vals[i];
                        y_f.setUpperBound(max_y_f * (float) (1.1));
                    }

                    if (c_vals != null) {
                        Best_C_F_vals.getData().add(new XYChart.Data(i, c_vals[i]));
                        if (c_vals[i] > max_y_c) {
                            max_y_c = c_vals[i];
                            y_c.setUpperBound(max_y_c * (float) (1.1));
                        }
                    }

                }
            } else {
                for (int i = old_time; i != new_time; i--) {
                    selected_F_vals.getData().remove(i - 1);
                    if (c_vals != null)
                        Best_C_F_vals.getData().remove(i - 1);
                }
                max_y_f = StatLib.max(Arrays.copyOfRange(f_vals, 0, new_time));
                x_f.setUpperBound(new_time * (float) (1.1));
                if (c_vals != null) {
                    max_y_c = StatLib.max(Arrays.copyOfRange(c_vals, 0, new_time));
                    x_c.setUpperBound(new_time * (float) (1.1));
                }
            }
        });

    }

    public void clear() {
        selected_F_vals.getData().clear();
        Best_C_F_vals.getData().clear();
    }
}
