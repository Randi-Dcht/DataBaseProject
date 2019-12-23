package be.ac.umons.projetBDD.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SgbdGui extends Application
{
    public static final int widthX  = 1700;
    public static final int heightY = 1200;
    public static final String css = "../../../../../../misc/ressource/designSGBD.css";

    private CodeToGui dataBase;

    private Stage stage;
    private BorderPane[] listBP;
    private BorderPane pane;
    private BorderPane actuel;

    public SgbdGui(CodeToGui dataBase)
    {
        this.dataBase = dataBase;
        dataBase.start();
    }

    @Override
    public void start(Stage stage)
    {
        pane = new BorderPane();
        Scene scene = new Scene(pane,widthX,heightY);
        scene.getStylesheets().add(getClass().getResource(css).toExternalForm());

        pane.setCenter(create());
        pane.setTop(createMenu());

        stage.setTitle("SGBD_Umons : misc/Other/" + dataBase.toString() + ".db");
        stage.setScene(scene);
        stage.show();
        this.stage = stage;
    }

    public BorderPane create()
    {
        listBP = new BorderPane[5];
        /*Dependence*/
        DependanceGui DG = new DependanceGui(dataBase);
        listBP[0] = DG.create();
        /*option*/
        listBP[1] = new BorderPane();
        /*DataBase*/
        listBP[2] = new BorderPane();

        actuel = listBP[0];
        return listBP[0];
    }

    public HBox createTab()
    {
        Button df   = new Button("Dependence");
        Button opt = new Button("Option");
        Button data = new Button("DataBase");

        HBox hb = new HBox();
        hb.getChildren().addAll(df,data,opt);
        hb.setSpacing(45);
        hb.setAlignment(Pos.CENTER);

        df.setOnAction(e->{changed(listBP[0]);});
        data.setOnAction(e->{changed(listBP[2]);});
        opt.setOnAction(e->{changed(listBP[1]);});

        return hb;
    }

    public void changed(BorderPane who)
    {
        pane.getChildren().remove(actuel);
        pane.setCenter(who);
    }

    public VBox createMenu()
    {
        VBox vb = new VBox();

        MenuBar menu   = new MenuBar();
        Menu quit      = new Menu("Quit");
        Menu cls       = new Menu("Close");
        Menu help      = new Menu("Help");
        Menu opti      = new Menu("Option");
        MenuItem safec = new MenuItem("Save & close");
        MenuItem doc   = new MenuItem("Documentary");
        MenuItem safeq = new MenuItem("Save & quit");
        MenuItem clos  = new MenuItem("Close");
        MenuItem clos2 = new MenuItem("quit");
        MenuItem ref   = new MenuItem("refresh");

        quit.getItems().addAll(safeq,clos2);
        cls.getItems().addAll(safec,clos);
        opti.getItems().add(ref);
        help.getItems().add(doc);
        menu.getMenus().addAll(quit,cls,help,opti);
        vb.getChildren().addAll(menu,createTab());

        safec.setOnAction(e->
        {
            dataBase.quit();
            stage.close();
            new MainGui().start(new Stage());
        });
        safeq.setOnAction(e->
        {
            dataBase.quit();
            stage.close();
        });
        clos2.setOnAction(e->
        {
            dataBase.quit();
            stage.close();
        });
        clos.setOnAction(e->
        {
            dataBase.quit();
            new MainGui().start(new Stage());
            stage.close();
        });
        ref.setOnAction(e->
        {
            dataBase.quit();
            new SgbdGui(dataBase).start(new Stage());
            stage.close();
        });
        doc.setOnAction(e->
        {

        });

        return vb;
    }

}