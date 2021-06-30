package view.Tags;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;


public class TagsController implements Initializable {

    public ObservableList features;
    @FXML
    private ListView<String> selectedFeature;

    public TagsController() {}

    public void chooseFeature() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("feature has been selected");
        features = FXCollections.observableArrayList();
        features.removeAll(features);


        features.add("aileron");
        features.add("elevator");
        features.add("altitude");
        features.add("airSpeed");
        features.add("heading");

        selectedFeature.getItems().addAll(features);
    }
}
