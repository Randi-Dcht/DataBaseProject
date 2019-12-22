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
        /*Database*/
        listBP[0] = new BorderPane();
        /*Dependance*/
        DependanceGui DG = new DependanceGui(dataBase);
        listBP[1] = DG.create();
        /*History*/
        HistoryGui HG = new HistoryGui("misc/Other/"+dataBase.toString()+".saving");
        listBP[2] = HG.create();
        /*BCNF-3NF*/
        listBP[3] = new BorderPane();
        /*key*/
        listBP[4] = new BorderPane();

        actuel = listBP[0];
        return listBP[0];
    }

    public HBox createTab()
    {
        Button df   = new Button("Dependance");
        Button base = new Button("History");
        Button BCNF = new Button("BCNF-3NF");
        Button key  = new Button("Key");
        Button data = new Button("DataBase");

        HBox hb = new HBox();
        hb.getChildren().addAll(data,df,base,BCNF,key);
        hb.setSpacing(25);
        hb.setAlignment(Pos.CENTER);

        data.setOnAction(e->{changed(listBP[0]);});
        base.setOnAction(e->{changed(listBP[2]);});
        BCNF.setOnAction(e->{changed(listBP[3]);});
        key.setOnAction(e->{changed(listBP[4]);});
        df.setOnAction(e->{changed(listBP[1]);});

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
            MainGui mg = new MainGui();
            stage.close();
            mg.start(new Stage());
        });
        clos2.setOnAction(e->
        {
            stage.close();
        });
        clos.setOnAction(e->{
            MainGui mg = new MainGui();
            stage.close();
            mg.start(new Stage());
        });
        ref.setOnAction(e->
        {
            SgbdGui neww = new SgbdGui(dataBase);
            neww.start(new Stage());
            stage.close();
        });

        return vb;
    }

}