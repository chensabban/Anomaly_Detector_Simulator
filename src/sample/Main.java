package sample;

import view.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import view_model.ViewModel;

import javafx.scene.layout.AnchorPane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        System.out.println("Application started");
        FXMLLoader fxl = new FXMLLoader();
        AnchorPane root = fxl.load(getClass().getResource("sample.fxml").openStream());
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toString());
        View view = fxl.getController();
        Model model = new Model("propS.txt");
        ViewModel vm = new ViewModel(model);
        primaryStage.setTitle("Flight GUI");

        primaryStage.setScene(scene);
        primaryStage.show();
        view.init(vm);
    }


    public static void main(String[] args) {

        launch(args);
    }
}