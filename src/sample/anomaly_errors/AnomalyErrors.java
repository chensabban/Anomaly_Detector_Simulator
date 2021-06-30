package sample.anomaly_errors;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class AnomalyErrors implements AnomalyAppError {

    View view;

    public AnomalyErrors(View view) {

        this.view = view;
    }

    @Override
    public void connectFg() {
        if(checkFlightGearProcess()==true){
            view.getVm().connect2fg();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Please Open FlightGear App!");
            alert.showAndWait();
        }
    }

    @Override
    public boolean checkFlightGearProcess() {
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

    @Override
    public void uploadSettings() {
        Stage stage = new Stage();
        stage.setTitle("File chooser sample");

        final FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(stage);

        if(file!=null){
            if (file.getPath().endsWith(".txt")){
                System.out.println("accepting the props file");
                view.getVm().sendSettingsToModel(file);
            }
            else{
                popupSettings();
            }
        }
    }

    @Override
    public void popupSettings() {
        Stage stage = new Stage();
        stage.setTitle("File chooser sample");

        final FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(stage);

        if(file!=null){
            if (file.getPath().endsWith(".txt")){
                System.out.println("accepting the props file");
                 view.getVm().sendSettingsToModel(file);
            }
            else{
                popupSettings();
            }
        }
    }

    @Override
    public void popupToOpenFile() {

    }
}
