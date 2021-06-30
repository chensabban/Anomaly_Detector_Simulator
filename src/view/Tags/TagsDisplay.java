package view.Tags;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;


public class TagsDisplay extends AnchorPane {

    //StringProperty aileron,elevator,altitude,airSpeed,heading;
    //public ObservableList features;
    //public ListView<String> list;
    TagsController controller;

    public TagsDisplay() {

        FXMLLoader fxl = new FXMLLoader();
        try {
            AnchorPane root = fxl.load(getClass().getResource("tags.fxml").openStream());
            controller = fxl.getController();

            this.getChildren().add(root);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
