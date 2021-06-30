package view.open;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class OpenController  {

    @FXML
    Button open;

    public File file;

    public OpenController() {}


    public void openBtnPreesed() {
        System.out.println("12btn pressed");
        Stage stage = new Stage();
        stage.setTitle("File chooser sample");

        final FileChooser fileChooser = new FileChooser();

        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                System.out.println(file.getPath());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}