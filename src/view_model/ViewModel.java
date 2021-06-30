package view_model;


import PTM1.AnomalyDetector.AnomalyReport;
import PTM1.AnomalyDetector.TimeSeriesAnomalyDetector;
import PTM1.Helpclass.Point;
import PTM1.Helpclass.StatLib;
import PTM1.Helpclass.TimeSeries;

import javafx.application.Platform;
import javafx.beans.property.*;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.scene.chart.XYChart;
import model.Model;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;


public class ViewModel implements Observer {

    Model model;

    public final Runnable play,pause,stop,forward,backward;

    public IntegerProperty time_step;
    public LongProperty time_speed;
    public DoubleProperty time;

    private HashMap<String,SimpleFloatProperty> displayVariables;
    public StringProperty selected_feature;

    private ExecutorService scheduledExecutorService;

    public float[] selected_feature_vector;
    public float[] Best_c_feature_vector;

    public BooleanProperty check_for_settings = new SimpleBooleanProperty(true);
    public BooleanProperty settings_ok = new SimpleBooleanProperty(false);
    public BooleanProperty display_bool_features;

    public HashMap<String, ArrayList<Integer>> setting_map;


    public ViewModel(Model m) {
        model = m;
        model.addObserver(this);

        scheduledExecutorService = Executors.newSingleThreadExecutor();

        selected_feature = new SimpleStringProperty();
        display_bool_features = new SimpleBooleanProperty(false);
        time = new SimpleDoubleProperty();
        time_step = new SimpleIntegerProperty(0);
        time_speed = new SimpleLongProperty((long)model.time_default);
        time.setValue(1);
        model.timestep =time_step;
        model.time_speed =time_speed;
        model.setSettings(model.settings);
        displayVariables = this.model.showFields();
        time_step.addListener((o, ov, nv) -> {
     //       this.model.timestep.set((int)nv);
            Platform.runLater(() -> setTimeStep((int) nv));
        });

        play = () -> scheduledExecutorService.execute(()->model.play());
        stop = ()-> scheduledExecutorService.execute(()->model.stop());
        pause = ()-> scheduledExecutorService.execute(()->model.pause());
        forward = ()-> scheduledExecutorService.execute(()->this.changeTimeSpeed(0.25));
        backward = ()-> scheduledExecutorService.execute(()->this.changeTimeSpeed(-0.25));

    }

    @Override
    public void update(Observable o, Object arg) {
          this.display_bool_features.setValue(true);
   }

    synchronized public void changeTimeSpeed(double time) {
        model.pause();
        if(time > 0) {
            if (this.time.get() >= 0.25 && this.time.get() < 2) {
                this.time.setValue(this.time.get() + time);
                time_speed.set((long) (200 / this.time.get()));
            }
        }
        else if(time < 0) {
            if (this.time.get() > 0.25 && this.time.get() <= 2) {
                this.time.setValue(this.time.get() + time);
                time_speed.set((long) (200 / this.time.get()));
            }
        }
        model.play();
    }

    public void connect2fg(){
        model.csvToFg();
    }

    public boolean set_detect_TimeSeries(File f) {
        boolean answer = true;
        if(this.model.checkCSVfile(f)){
            model.set_detect_TimeSeries(f);
        }
        else
            answer=false;
        return answer;
    }

    //change
    public void setTimeStep(int time_step) {
        if(model.timeSeries !=null){
            for (String feature : this.displayVariables.keySet()) {
                displayVariables.get(feature).setValue(model.timeSeries.valueAtIndex(time_step,this.model.setting_map.get(feature).get(0)));
            }
        }
    }

    public HashMap<String, SimpleFloatProperty> getDisplayVariables() {
        return displayVariables;
    }

    public TimeSeries getTimeSeries(){
        return model.timeSeries;
    }

    public TimeSeries getDetect_timeSeries(){
        return model.detect_timeSeries;
    }

    public void setAnomalyDetector(TimeSeriesAnomalyDetector anomalyDetector) {
        this.model.setAnomalyDetevtor(anomalyDetector);
    }

    public void sendSettingsToModel(File f){
        if(this.model.CheckSettings(f)){
            model.setSettings(f.getPath());
            setting_map=model.setting_map;
            settings_ok.set(!settings_ok.get());
        }
        else{
            check_for_settings.set(!check_for_settings.get());
        }
    }
    public void set_selected_vectors(){
        selected_feature_vector = model.getSelected_vector(selected_feature.getValue());
        Best_c_feature_vector = model.getBest_cor_Selected_vector(selected_feature.getValue());
    }

    public List<XYChart.Series> getpaintFunc(){
        return model.paintAlgo(selected_feature.getValue());
    }

    public float[] get_detect_point(int time){
       return model.anomalyDetector.detect_P(model.detect_timeSeries,selected_feature.getValue(),time);
    }
}