package view.f_list;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class F_list extends Pane {
    public F_listController myLineChartController;
    public F_list(){
        try {
            FXMLLoader fxl = new FXMLLoader();
            Pane ap = fxl.load(getClass().getResource("F_list.fxml").openStream());
            ap.getStylesheets().add(getClass().getResource("F_listStyle.css").toExternalForm());

            myLineChartController=fxl.getController();
            myLineChartController.add_series();
            this.getChildren().add(ap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}