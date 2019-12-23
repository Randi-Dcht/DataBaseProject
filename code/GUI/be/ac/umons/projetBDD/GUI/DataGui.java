package be.ac.umons.projetBDD.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.HashMap;

public class DataGui
{
    public static final String url = "misc/ressource/";
    private BorderPane dp;
    private CodeToGui db;

    private VBox center;
    private Button valide;
    private Button attribut;
    private Button table;

    private HashMap<String,ArrayList<String>> list;
    private String actuel;


    public DataGui(CodeToGui db)
    {
        this.db = db;
    }

    public BorderPane create()
    {
        dp = new BorderPane();
        init();


        ScrollPane scp = new ScrollPane();
        scp.setContent(center);

        dp.setCenter(scp);
        dp.setBottom(createBt());

        return dp;
    }

    public void init()
    {
        list = new HashMap<>();
        center = new VBox();
        center.setSpacing(20);
        HashMap<String, ArrayList<String>> list = db.getAttribute();

        for (String table : list.keySet())
        {
            Text tab = new Text("-------------  " + table + "  -------------");
            tab.getStyleClass().add("textMd");
            center.getChildren().add(tab);
            for(String att : list.get(table))
            {
                Text at = new Text("Attribute : " + att);
                at.getStyleClass().add("textMd");
                center.getChildren().add(at);
            }
        }
    }

    public HBox createBt()
    {
        valide = new Button("Valid");
        valide.setVisible(false);
        table = new Button("Add table");
        table.setVisible(true);
        attribut = new Button("Add attribute");
        attribut.setVisible(false);

        valide.setOnAction(e->
        {
            db.newData(list);
            list = new HashMap<>();
            actuel = " ?? ";
            valide.setVisible(false);
            attribut.setVisible(false);
        });
        table.setOnAction(e->
        {
            table.setVisible(false);
            add();
        });
        attribut.setOnAction(e->
        {
            attribut.setVisible(false);
            addAttri();
        });

        HBox hb = new HBox();
        hb.setAlignment(Pos.BOTTOM_CENTER);
        hb.setSpacing(25);
        hb.getChildren().addAll(table,valide,attribut);

        return hb;
    }

    public void add()
    {
        Text bef = new Text(" ---------- ");
        bef.getStyleClass().add("textMd");
        Text aft = new Text(" ---------- ");
        aft.getStyleClass().add("textMd");
        TextField txt = new TextField(" ? ");
        txt.getStyleClass().add("textMd");
        Button bt = new Button("ok");
        HBox hb = new HBox();
        hb.getChildren().addAll(bef,txt,bt,aft);
        hb.setSpacing(5);
        center.getChildren().add(hb);

        bt.setOnAction(e->
        {
            if(!txt.getText().equals(" ? "))
            {
                bt.setVisible(false);
                attribut.setVisible(true);
                actuel = txt.getText();
                list.put(actuel,new ArrayList<>());
            }
        });
    }

    public void addAttri()
    {
        Text tx = new Text("Attribute : ");
        tx.getStyleClass().add("textMd");
        TextField txt = new TextField(" ? ");
        txt.getStyleClass().add("textMd");
        Button bt = new Button("OK");
        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.getChildren().addAll(tx,txt,bt);

        bt.setOnAction(e->
        {
            if(!txt.getText().equals(" ? "))
            {
                bt.setVisible(false);
                attribut.setVisible(true);
                valide.setVisible(true);
                table.setVisible(true);
                list.get(actuel).add(txt.getText());
            }
        });

        center.getChildren().add(hb);
    }

}
