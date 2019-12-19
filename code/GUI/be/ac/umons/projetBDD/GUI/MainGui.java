package be.ac.umons.projetBDD.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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

        pane.setCenter(createChoose());

        Button bt = new Button("ok");
        bt.setOnAction(e->{System.out.println("ok");});

        pane.setBottom(bt);

        primaryStage.setScene(scene);
        primaryStage.setTitle("MainGui SGBD");
        primaryStage.show();
    }

    public VBox createChoose()
    {
        return null;
    }

    public VBox useOld()
    {
        return null;
    }

    public VBox useNew()
    {
        return null;
    }
}
