package view.clocks;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.GridPane;


import java.io.IOException;
import java.util.HashMap;

public class Clocks extends AnchorPane {

    public ClockController controller;
    public HashMap<String, FloatProperty> clocksMap;

    public Clocks() {
        FXMLLoader fxl = new FXMLLoader();
        GridPane gridPane = null;
        clocksMap = new HashMap<>();
        setClocksMap();

        try {
            gridPane = fxl.load(getClass().getResource("clocks.fxml").openStream());
            controller = fxl.getController();
            controller.createClocks();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            this.getChildren().add(gridPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setValues(String featureName, float newVal) {
        controller.gaugeMap.get(featureName).setValue((double)newVal);
    }
    private void setClocksMap() {

        clocksMap.put("airSpeed",new SimpleFloatProperty());
        clocksMap.put("altitude",new SimpleFloatProperty());
        clocksMap.put("heading",new SimpleFloatProperty());
        clocksMap.put("yaw",new SimpleFloatProperty());
        clocksMap.put("roll",new SimpleFloatProperty());
        clocksMap.put("pitch",new SimpleFloatProperty());
    }
}