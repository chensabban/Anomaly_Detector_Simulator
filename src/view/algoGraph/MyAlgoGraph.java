package view.algoGraph;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MyAlgoGraph extends Pane {
    public MyAlgoGraphController myAlgoGraphController;
    public MyAlgoGraph() {
        try {
            FXMLLoader fxl = new FXMLLoader();
            Pane algo = fxl.load(getClass().getResource("MyAlgoGraph.fxml").openStream());
            algo.getStylesheets().add(getClass().getResource("AlgoStyle.css").toExternalForm());
            myAlgoGraphController = fxl.getController();
            myAlgoGraphController.add_series();
            this.getChildren().add(algo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
