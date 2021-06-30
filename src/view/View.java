package view;

import PTM1.AnomalyDetector.TimeSeriesAnomalyDetector;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import sample.anomaly_errors.AnomalyErrors;
import view.algoGraph.MyAlgoGraph;
import view.clocks.Clocks;
import view.f_list.F_list;
import view.pannel.Pannel;
import view.joystick.MyJoystick;
import view_model.ViewModel;

import java.io.BufferedReader;
import java.io.File;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

public class View {

        @FXML
        MyJoystick myJoystick;
        @FXML
        Clocks clocks;
        @FXML
        Pannel pannel;
        @FXML
        F_list flist;
        @FXML
        MyAlgoGraph myAlgoGraph;
        @FXML
        Button open, settings;
        @FXML
        Button fly;


        ViewModel vm;
        BooleanProperty check_settings;
        AnomalyErrors anomalyErrors;
        BooleanProperty display_bool_features;
        public View() {
            check_settings = new SimpleBooleanProperty();
            anomalyErrors = new AnomalyErrors(this);
            display_bool_features = new SimpleBooleanProperty();
        }


        public void init(ViewModel vm) {
            this.vm = vm;
            this.settings.setText("upload\nsettings");
            this.open.setText("upload\n  csv");



            this.display_bool_features.bind(vm.display_bool_features);
            myJoystick.joystickMap.forEach((f,p)-> {
                myJoystick.joystickMap.get(f).bind(vm.getDisplayVariables().get(f));
                myJoystick.joystickMap.get(f).addListener((ob,ov,nv)->myJoystick.myJoystickController.paint());
            });
           clocks.clocksMap.forEach((f,p) -> {
               clocks.clocksMap.get(f).bind(vm.getDisplayVariables().get(f));
               clocks.clocksMap.get(f).addListener((ob,ov,nv)->clocks.setValues(f,(float)nv));
           });

            pannel.controller.time_speed.bind(vm.time_speed);

            vm.time.addListener((ob,ov,nv) ->
                    pannel.controller.text_speed.setText("x" + nv.toString()));

            pannel.controller.time_step.bindBidirectional(vm.time_step);
            pannel.controller.time_step.addListener((ob,ov,nv) -> {
                Platform.runLater(() -> {
                    pannel.changeTimeStep();
                    if(flist.myLineChartController.selected_feature.getValue()!=null) {
                        flist.myLineChartController.add_p_paint((int) ov, (int) nv);
                        for(int time=(int) ov+1;time<=(int) nv;time++)
                            myAlgoGraph.myAlgoGraphController.add_p_paint(vm.get_detect_point(time));
                    }
                });
            });

            vm.selected_feature.bind(flist.myLineChartController.selected_feature);

            vm.check_for_settings.addListener((o,ov,nv)->popupSettings());
            vm.settings_ok.addListener((o,ov,nv)-> popupToOpenFile());

            pannel.controller.onPlay = vm.play;
            pannel.controller.onPause = vm.pause;
            pannel.controller.onStop = vm.stop;
            pannel.controller.runForward = vm.forward;
            pannel.controller.runBackward = vm.backward;
            initFeature();
        }

    public ViewModel getVm() {
        return vm;
    }

//     the function checks if the flightgear is running
//     and send notification to the view-model to connect
    public void connectFg() {
        if(checkFlightGearProcess()==true){
            vm.connect2fg();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Please Open FlightGear App!");
            alert.showAndWait();
        }
    }

    public void OpenCsvButton() {

        Stage stage = new Stage();
        stage.setTitle("File chooser sample");

        final FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                if (file.getPath().endsWith(".csv")) {
                    System.out.println("File ends with csv");
                    if(vm.set_detect_TimeSeries(file)){
                        this.fly.setDisable(false);
                        this.fly.setStyle("-fx-background-color: #b0d2e5");

                    }
                    else popupCSV();
                }
                else popupCSV();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void popupCSV(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("There is a problem with the CSV file");
        alert.setContentText("Please try again!");
        alert.showAndWait();
    }

//    / the function checks if the flightGear process is running in the background
    public static boolean checkFlightGearProcess() {
        String line;
        String pidInfo ="";
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
            BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                pidInfo+=line;
            }
            input.close();

            if(pidInfo.contains("fgfs")) {
                System.out.println("Found FlightGear process.");
                return true;
            } else {
                System.out.println("Couldn't find FlightGear process");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /// the function open a dialog with the user to upload settinngs file and send it to the view-model
    public void uploadSettings(){
        Stage stage = new Stage();
        stage.setTitle("File chooser sample");
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if(file!=null){
            if (file.getPath().endsWith(".txt") || file.getPath().endsWith(".xml")){
                this.settings.setStyle("-fx-background-color: #f6cba5;");
                System.out.println("accepting the props file");
                vm.sendSettingsToModel(file);
            }
            else{
                popupSettings();
            }
        }
    }

    /// the function alert that there is a problem with the settings file that uplaoded
    public void popupSettings(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("There is a problem with the settings file");
        alert.setContentText("Using default settings");
        alert.showAndWait();
    }

    /// the function responsible to alert the user to upload csv file after uploaded the settings file
    public void popupToOpenFile(){
        this.open.setStyle("-fx-background-color: #9fe9a2");
        System.out.println(this.vm.setting_map);
        clocks.controller.updateMinMax(this.vm.setting_map);
    }

    public void LoadAlgo() {
        pannel.controller.pause();
        Stage stage = new Stage();
        stage.setTitle("Select Algo Class File");
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null&&file.getPath().contains(".java")) {
            try {
                String str = file.getPath();
                String[] path_parts = str.split("bin");
                StringBuffer path_b = new StringBuffer();
                StringBuffer class_b = new StringBuffer();
                path_b.append("file://");
                for (char x : path_parts[0].toCharArray()) {
                    if(x == '\\'){
                        path_b.append('/');
                    }else{
                        path_b.append(x);
                    }
                }
                path_b.append("bin/");
                String algo_path = path_b.toString();
                String[] name1 = path_parts[1].split(".java");
                Boolean check = false;
                for (char x : name1[0].toCharArray()) {
                    if(x == '\\'){
                        if(check) {
                            class_b.append('.');
                        }else{
                            check =true;
                        }
                    }else{
                        class_b.append(x);
                    }
                }
               String classname = class_b.toString();
                URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{
                        new URL(algo_path)
                });
                Class<?> c = urlClassLoader.loadClass(classname);
                TimeSeriesAnomalyDetector Ts = (TimeSeriesAnomalyDetector) c.newInstance();
                Platform.runLater(()->{
                    vm.setAnomalyDetector(Ts);
               if(flist.myLineChartController.selected_feature.get()!=null){
                   myAlgoGraph.myAlgoGraphController.set_algo_setting(vm.getpaintFunc());
                   for(int i=0;i<this.pannel.controller.time_step.getValue();i++)
                        myAlgoGraph.myAlgoGraphController.add_p_paint(vm.get_detect_point(i));
               }

            });
                this.flist.myLineChartController.fList.setDisable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void initFeature() {
            List<String> features = new LinkedList<>();
            if(vm.display_bool_features.get()) { ;
                for (String feature : this.vm.getTimeSeries().FeaturesList) {
                    features.add(feature);
                }
                flist.myLineChartController.fList.getItems().addAll(features);
            }
            flist.myLineChartController.fList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    pannel.controller.pause();
                    flist.myLineChartController.selected_feature.setValue(flist.myLineChartController.fList.getSelectionModel().getSelectedItem());
                    vm.set_selected_vectors();
                    myAlgoGraph.myAlgoGraphController.set_algo_setting(vm.getpaintFunc());
                    String selected = flist.myLineChartController.selected_feature.get();
                    String b_s_name = vm.getDetect_timeSeries().getbest_c_feature(selected);
                    flist.myLineChartController.set_setting(pannel.controller.time_step.getValue(),vm.selected_feature_vector, vm.Best_c_feature_vector,selected,b_s_name);
                    System.out.println(flist.myLineChartController.fList.getSelectionModel().getSelectedItem() + " was selected");
               }
            });
        }

}


