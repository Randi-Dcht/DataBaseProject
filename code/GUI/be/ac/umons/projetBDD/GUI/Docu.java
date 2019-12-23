package be.ac.umons.projetBDD.GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;

public class Docu extends Application
{
    @Override
    public void start(Stage stage)
    {
        Group root = new Group();
        Scene scene = new Scene(root,800,600);
        /*permet de de voir une page web*/
        WebView web = new WebView();
        WebEngine we = web.getEngine();

        try
        {
            URL url = this.getClass().getResource("../../../../../../misc/ressource/Doc.html");
            we.load(url.toString());
        }
        catch (Exception e)
        {
            System.err.println(e);
        }

        root.getChildren().addAll(web);

        stage.setScene(scene);
        stage.setTitle("Documentary");
        stage.show();
    }
}
