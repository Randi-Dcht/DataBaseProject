package be.ac.umons.projetBDD.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainGui extends Application
{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane,1000,1000);

        primaryStage.setScene(scene);
        primaryStage.setTitle("MainGui SGBD");
        primaryStage.show();
    }
}
