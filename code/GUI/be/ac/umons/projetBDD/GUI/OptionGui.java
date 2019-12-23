package be.ac.umons.projetBDD.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private VBox deco;

    private VBox tbcnf = new VBox();
    private VBox tnf   = new VBox();
    private VBox tkey  = new VBox();
    private VBox tdeco = new VBox();

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
        deco = new VBox();

        bcnf.getChildren().addAll(createOptBC(),tbcnf);
        nf.getChildren().addAll(createOptNF(),tnf);
        key.getChildren().addAll(createOptKE(),tkey);
        deco.getChildren().addAll(createOptDeco(),tdeco);

        center.getChildren().addAll(bcnf,nf,key,deco);
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
        title.getStyleClass().add("textMd");
        ComboBox<String> bcnfC = new ComboBox<String>();
        bcnfC.getItems().addAll(db.getTable(false));
        Button bt = new Button(" Verified ");
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(30);
        hb.getChildren().addAll(title,bcnfC,bt);

        bt.setOnAction(e->
        {
            if(bcnfC.getValue() != null)
            {
                bcnf.getChildren().remove(tbcnf);
                tbcnf = new VBox();
                for(String str : db.checkBCNF(bcnfC.getValue()))
                    tbcnf.getChildren().add(new Text(str));
                bcnf.getChildren().add(tbcnf);
            }
        });

        return hb;
    }

    public HBox createOptNF()
    {
        HBox hb = new HBox();
        Text title = new Text(" DataBase 3NF ? ");
        title.getStyleClass().add("textMd");
        ComboBox<String> nfC = new ComboBox<String>();
        nfC.getItems().addAll(db.getTable(false));
        Button bt = new Button(" Verified ");
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(30);
        hb.getChildren().addAll(title,nfC,bt);

        bt.setOnAction(e->
        {
            if(nfC.getValue() != null)
            {
                nf.getChildren().remove(tnf);
                tnf = new VBox();
                for(String str : db.check3NF(nfC.getValue()))
                    tnf.getChildren().add(new Text(str));
                nf.getChildren().add(tnf);
            }
        });

        return hb;
    }

    public HBox createOptKE()
    {
        HBox hb = new HBox();
        Text title = new Text(" Key of this DataBase");
        title.getStyleClass().add("textMd");
        ComboBox<String> keyC = new ComboBox<String>();
        keyC.getItems().addAll(db.getTable(false));
        Button bt = new Button( " See the keys ");
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(30);
        hb.getChildren().addAll(title,keyC,bt);

        bt.setOnAction(e->
        {
            if(keyC.getValue() != null)
            {
                key.getChildren().remove(tkey);
                tkey = new VBox();
                for(String str : db.listKey(keyC.getValue()))
                    tkey.getChildren().add(new Text(str));
                key.getChildren().add(tkey);
            }
        });

        return hb;
    }

    public HBox createOptDeco()
    {
        HBox hb = new HBox();
        Text title = new Text(" Decomposition of DataBase");
        title.getStyleClass().add("textMd");
        ComboBox<String> decoC = new ComboBox<String>();
        decoC.getItems().addAll(db.getTable(false));
        Button bt = new Button( " See the decomposition ");
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(30);
        hb.getChildren().addAll(title,decoC,bt);

        bt.setOnAction(e->
        {
            if(decoC.getValue() != null)
            {
                deco.getChildren().remove(tdeco);
                tdeco = new VBox();
                for(String str : db.listKey(decoC.getValue()))
                    tdeco.getChildren().add(new Text(str));
                deco.getChildren().add(tdeco);
            }
        });

        return hb;
    }
}
