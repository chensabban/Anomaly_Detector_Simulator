package view.pannel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class Pannel extends AnchorPane {
    public PannelController controller;


    public Pannel(){
        FXMLLoader fxl = new FXMLLoader();
        AnchorPane ap = null;
        try {
            ap = fxl.load(getClass().getResource("pannel.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ap!=null){
            controller = fxl.getController();
            this.getChildren().add(ap);
        }
        else
            controller=null;
    }

    public void changeTimeStep() {
        controller.slider.setValue(controller.time_step.get());
        int minutes = controller.time_step.get() / 60;
        int seconds =(int) (controller.time_step.get() - (minutes * 60));
        controller.runtimeDisplay.setText(minutes+":"+seconds);

    }
}
