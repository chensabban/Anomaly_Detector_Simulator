package view.open;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


import java.io.File;
import java.io.IOException;

public class OpenDisplay extends AnchorPane {

    public Button btn;
    public File file;

    public OpenDisplay() {

        FXMLLoader fxl = new FXMLLoader();
        try {
            AnchorPane open_btn = fxl.load(getClass().getResource("open_view.fxml").openStream());
            OpenController openController = fxl.getController();

            btn = openController.open;
            file = openController.file;
            if(file == null) {
                System.out.println("file is null in open display");
            }
            this.getChildren().add(open_btn);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}