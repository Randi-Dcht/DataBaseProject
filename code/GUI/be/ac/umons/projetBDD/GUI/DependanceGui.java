package be.ac.umons.projetBDD.GUI;


import be.ac.umons.projetBDD.Dependence;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class DependanceGui
{
    public static final String url = "misc/ressource/";
    public static int value = 0;
    private BorderPane dp;
    private CodeToGui db;
    private Button add;
    private VBox vb;
    private VBox listVbox;
    private ArrayList<Dependence> list = new ArrayList<>();

    public DependanceGui(CodeToGui db)
    {
        this.db = db;
    }

    public BorderPane create()
    {
        dp = new BorderPane();
        HBox right = new HBox();
        right.setSpacing(120);
        right.setAlignment(Pos.CENTER);
        Button his = new Button("History");
        add = new Button("+");
        right.getChildren().addAll(add,his);
        vb = readDepandance();
        vb.getStyleClass().add("background");
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(vb);
        scroll.getStyleClass().add("background");
        dp.setCenter(scroll);
        dp.setBottom(right);
        add.setOnAction(e->{createDF();});

        return dp;
    }

    public VBox readDepandance()
    {
        listVbox = new VBox();
        listVbox.setSpacing(15);
        for(Dependence dd : db.getDependance())
        {
            listVbox.getChildren().add(createDep(dd));
            list.add(dd);
        }
        return listVbox;
    }

    public HBox createDep(Dependence dd)
    {
        HBox hb = new HBox();
        String data= "";
        Text accoD = new Text("{");
        accoD.getStyleClass().add("textMd");
        for(String str : dd.getLhs())
            data = data + " " + str;
        TextField txt1 = new TextField(data);
        txt1.getStyleClass().add("textMd");
        Text accoF = new Text("}");
        accoF.getStyleClass().add("textMd");
        Text accoD2 = new Text("{");
        accoD2.getStyleClass().add("textMd");
        Text accoF2 = new Text("}");
        accoF2.getStyleClass().add("textMd");
        Text fle = new Text("  --->  ");
        fle.getStyleClass().add("textMd");
        TextField txt2 = new TextField(dd.getRhs());
        txt2.getStyleClass().add("textMd");
        Button delete = createButton("croix.png",value);
        delete.getStyleClass().add("transpRed");
        Button modif  = createButton("ecrire.png",value);
        modif.getStyleClass().add("transpGreen");
        hb.getChildren().addAll(accoD,txt1,accoF,fle,accoD2,txt2,accoF2,modif,delete);
        hb.setAlignment(Pos.CENTER);
        value++;

        return hb;
    }

    public Button createButton(String picture,int valeur)
    {
        Button bt = new Button("",picture(picture,30,30));
        bt.setOnAction(e->{int i = valeur; db.remove(list.get(i)); dp.getChildren().remove(vb); vb = readDepandance(); dp.setCenter(vb);});
        return bt;
    }

    public ImageView picture(String file, int x, int y)
    {
        ImageView img = new ImageView(url+file);
        img.setFitHeight(y);
        img.setFitWidth(x);
        return img;
    }

    public void createDF()
    {
        HBox hb = new HBox();
        Text accoD = new Text("{");
        accoD.getStyleClass().add("textMd");
        TextField txt1 = new TextField("?");
        txt1.getStyleClass().add("textMd");
        Text accoF = new Text("}");
        accoF.getStyleClass().add("textMd");
        Text accoD2 = new Text("{");
        accoD2.getStyleClass().add("textMd");
        Text accoF2 = new Text("}");
        accoF2.getStyleClass().add("textMd");
        Text fle = new Text("  --->  ");
        fle.getStyleClass().add("textMd");
        TextField txt2 = new TextField("?");
        txt2.getStyleClass().add("textMd");
        Button delete = createButton("croix.png",value);
        delete.getStyleClass().add("transpRed");
        delete.setVisible(false);
        Button modif  = createAccept("ecrire.png",txt1,txt2,delete);
        modif.getStyleClass().add("transpGreen");
        hb.getChildren().addAll(accoD,txt1,accoF,fle,accoD2,txt2,accoF2,modif,delete);
        hb.setAlignment(Pos.CENTER);
        listVbox.getChildren().add(hb);
        value++;
        add.setVisible(false);
    }

    public Button createAccept(String picture,TextField txt1, TextField txt2,Button btt)
    {
        Button bt = new Button("",picture(picture,30,30));
        bt.setOnAction(e->{Dependence data = new Dependence("db",txt1.getText(),txt2.getText());list.add(data);db.add(data);btt.setVisible(true);add.setVisible(true);});
        return bt;
    }

}