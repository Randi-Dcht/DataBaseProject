package be.ac.umons.projetBDD.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class OptionGui
{
    public static final String url = "misc/ressource/";
    private BorderPane dp;
    private CodeToGui db;

    private VBox bcnf;
    private VBox nf;
    private VBox key;
    private VBox center;

    private VBox tbcnf = new VBox();
    private VBox tnf   = new VBox();
    private VBox tkey  = new VBox();

    public OptionGui(CodeToGui db)
    {
        this.db = db;
    }

    public BorderPane create()
    {
        dp = new BorderPane();
        bcnf = new VBox();
        nf= new VBox();
        key = new VBox();
        center = new VBox();

        bcnf.getChildren().addAll(createOptBC(),tbcnf);
        nf.getChildren().addAll(createOptNF(),tnf);
        key.getChildren().addAll(createOptKE(),tkey);

        center.getChildren().addAll(bcnf,nf,key);
        center.setSpacing(55);
        ScrollPane scp = new ScrollPane();
        scp.setContent(center);
        dp.setCenter(scp);

        return dp;
    }

    public HBox createOptBC()
    {
        HBox hb = new HBox();
        Text title = new Text(" DataBase BCNF ? ");
        Button bt = new Button(" Verified ");
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(30);
        hb.getChildren().addAll(title,bt);

        bt.setOnAction(e->
        {
            bcnf.getChildren().remove(tbcnf);
            tbcnf = new VBox();
            for(String str : db.checkBCNF(" "))
                tbcnf.getChildren().add(new Text(str));
            bcnf.getChildren().add(tbcnf);
        });

        return hb;
    }

    public HBox createOptNF()
    {
        HBox hb = new HBox();
        Text title = new Text(" DataBase 3NF ? ");
        Button bt = new Button(" Verified ");
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(30);
        hb.getChildren().addAll(title,bt);

        bt.setOnAction(e->
        {
            nf.getChildren().remove(tnf);
            tnf = new VBox();
            for(String str : db.check3NF(" "))
                tnf.getChildren().add(new Text(str));
            nf.getChildren().add(tnf);
        });

        return hb;
    }

    public HBox createOptKE()
    {
        HBox hb = new HBox();
        Text title = new Text(" Key of this DataBase");
        Button bt = new Button( " See the keys ");
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(30);
        hb.getChildren().addAll(title,bt);

        bt.setOnAction(e->
        {
            key.getChildren().remove(tkey);
            tkey = new VBox();
            for(String str : db.listKey())
                tkey.getChildren().add(new Text(str));
            key.getChildren().add(tkey);
        });

        return hb;
    }
}
