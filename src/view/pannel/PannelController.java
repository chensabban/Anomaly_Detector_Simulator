package view.pannel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class PannelController {

    public IntegerProperty time_step;
    public DoubleProperty time_speed;
    public Runnable onPlay, onPause, onStop,runForward,runBackward;
    @FXML
    public Slider slider;
    @FXML
    public TextField text_speed;
    @FXML
    public Text runtimeDisplay;

    public PannelController() {

        time_step = new SimpleIntegerProperty();
        time_speed = new SimpleDoubleProperty();
        text_speed = new TextField();
        runtimeDisplay = new Text();
    }


    public void play(){
        if(onPlay != null)
            onPlay.run();
    }

    public void pause(){
        if(onPause != null)
            onPause.run();
    }

    public void stop(){
        if(onStop != null)
            onStop.run();
    }

    public void forward() {
        if(runForward != null)
            runForward.run();
    }

    public void backward() {
        if(runBackward != null)
            runBackward.run();
    }

    public void changeTimeStep(MouseEvent mouseEvent) {
        pause();
        System.out.println("time step has changed by dragging slider");
        this.time_step.set((int) slider.getValue());
        System.out.println(this.time_step);
        play();
    }

}
