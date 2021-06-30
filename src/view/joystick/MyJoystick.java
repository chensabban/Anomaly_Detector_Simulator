package view.joystick;

import eu.hansolo.medusa.skins.PlainClockSkin;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.util.HashMap;

public class MyJoystick extends AnchorPane {

    public MyJoystickController myJoystickController;
    public HashMap<String,FloatProperty> joystickMap;
    public MyJoystick(){
        super();
        try{
            FXMLLoader fxl = new FXMLLoader();
            AnchorPane joy = (AnchorPane) fxl.load(getClass().getResource("MyJoystick.fxml").openStream());
            myJoystickController=fxl.getController();
            joystickMap = new HashMap<>();


            joystickMap.put("aileron", myJoystickController.aileron);
            joystickMap.put("elevator", myJoystickController.elevator);
            joystickMap.put("rudder", myJoystickController.rudd);
            joystickMap.put("throttle", myJoystickController.throt);



            Platform.runLater(() -> myJoystickController.paint());

            this.getChildren().add(joy);
            ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}