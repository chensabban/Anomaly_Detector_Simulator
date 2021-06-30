package app;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import view.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import view_model.ViewModel;

import javafx.scene.layout.AnchorPane;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        System.out.println("Application started");
        FXMLLoader fxl = new FXMLLoader();
        AnchorPane root = fxl.load(getClass().getResource("sample.fxml").openStream());
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toString());
        View view = fxl.getController();

        Model model = new Model("prop.txt");

        ViewModel vm = new ViewModel(model);
        primaryStage.setTitle("Flight GUI");

        //full screen
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        primaryStage.setScene(scene);
        primaryStage.show();
        view.init(vm);
    }


    public static void main(String[] args) {

        launch(args);
    }
}