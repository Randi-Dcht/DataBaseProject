package be.ac.umons.projetBDD.GUI;

import be.ac.umons.projetBDD.Saving;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class MainGui extends Application
{

    public static void main(String[] args) {
        launch(args);
    }

    public static final int widthX  = 1000;
    public static final int heightY = 900;
    public static final String css = "../../../../../../misc/ressource/designMain.css";

    /*Les variables de l'application*/
    private Stage stage;
    private VBox entry;
    private BorderPane fondEcran;
    private String roadNew ="misc/Other/";

    @Override
    public void start(Stage primaryStage)
    {
        fondEcran = new BorderPane();
        Scene scene = new Scene(fondEcran,widthX,heightY);
        scene.getStylesheets().add(getClass().getResource(css).toExternalForm());
        fondEcran.getStyleClass().add("fondEcran");

        entry = choiseEntry();
        entry.setAlignment(Pos.CENTER);
        fondEcran.setCenter(entry);

        primaryStage.setTitle("Main of SGBD");
        primaryStage.setScene(scene);
        primaryStage.show();

        stage = primaryStage;
    }

    public VBox choiseEntry()
    {
        VBox hb = new VBox();

        Button okold = new Button("Find an old DataBase");
        HBox hb1 = new HBox();
        hb1.getChildren().addAll(okold);
        hb1.setSpacing(50);
        hb1.setAlignment(Pos.CENTER);

        Button oknew = new Button("Created a  DataBase");
        HBox hb2 = new HBox();
        hb2.getChildren().add(oknew);
        hb2.setSpacing(50);
        hb2.setAlignment(Pos.CENTER);

        Button quit = new Button("quit Application");

        hb.setSpacing(50);
        hb.getChildren().addAll(hb1,hb2,quit);

        oknew.setOnAction(e->{dnew(hb2,oknew);});
        okold.setOnAction(e->{dold(hb1,okold);});
        quit.setOnAction(e->{stage.close();});

        return hb;
    }

    public void dold(HBox hb,Button bt)
    {
        ComboBox<String> oldBase = new ComboBox<String>(); oldBase.getStyleClass().add("text");
        oldBase.getItems().addAll(find(roadNew));
        oldBase.setPrefWidth(350);
        oldBase.setPrefHeight(30);
        Button ok1 = new Button("ok");
        hb.getChildren().addAll(oldBase,ok1);
        hb.getChildren().remove(bt);

        ok1.setOnAction(e->{changedOld(oldBase.getValue());});
    }

    public void dnew(HBox hb,Button bt)
    {
        TextField texte = new TextField(); texte.getStyleClass().add("text");
        texte.setText("NameOfDatabase");
        texte.setPrefWidth(350);
        texte.setPrefHeight(30);
        Button ok2 = new Button("ok");
        hb.getChildren().addAll(texte,ok2);
        hb.getChildren().remove(bt);

        ok2.setOnAction(e->{changedNew(texte.getText());});
    }

    public void changedOld(String name)
    {
        new SgbdGui(new CodeToGui(name)).start(new Stage());
        stage.close();
    }

    public void changedNew(String name)
    {
        new NewDataBase(name).start(new Stage());
        stage.close();
    }

    public ArrayList<String> find(String rd)
    {
        File repert = new File(rd);
        String[] lst = repert.list();
        ArrayList<String> lt = new ArrayList<String>();
        assert lst != null;
        for(String str : lst)
        {
            String[] cut = str.split("[/.]");
            if(cut.length ==2 && cut[1].equals("db"))
                lt.add(cut[0]);
        }
        return lt;
    }
}
