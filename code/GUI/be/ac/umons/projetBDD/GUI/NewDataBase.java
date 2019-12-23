package be.ac.umons.projetBDD.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;

public class NewDataBase extends Application
{
    private Stage stage;
    private Button add;
    private VBox center;
    private TextField title;
    private HashMap<String, ArrayList<String>> list;
    private String dat;
    static final String dep = " ... ";
    static final String tab = " Name of this table ";

    public NewDataBase(String dataBAse)
    {
        dat = dataBAse;
    }

    @Override
    public void start(Stage stage)
    {
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane,1500,1000);

        center = new VBox();
        center.setSpacing(5);
        addTable();
        title = new TextField(dat);

        ScrollPane scp = new ScrollPane();
        scp.setContent(center);

        pane.setTop(title);
        pane.setBottom(underPage());
        pane.setCenter(scp);

        stage.setTitle("Create a new DataBase");
        stage.setScene(scene);
        stage.show();

        list = new HashMap<>();
        this.stage = stage;
    }

    public HBox underPage()
    {
        HBox hb = new HBox();
        hb.setAlignment(Pos.BOTTOM_CENTER);
        hb.setSpacing(40);
        add = new Button("add Table");
        Button end = new Button("next");
        end.setOnAction(e->
        {
            finished();
        });
        add.setOnAction(e->
        {
            addTable();
        });
        hb.getChildren().addAll(add,end);
        return hb;
    }

    public void addTable()
    {
        VBox vvb = new VBox();
        vvb.setSpacing(5);
        TextField txt = new TextField(tab);
        Button more = new Button("add attribute");
        more.setOnAction(e->
        {
            String what = txt.getText();
            if(!what.equals(tab))
            {
                add(what);
                vvb.getChildren().add(addAttribute(more,what));
            }
        });
        vvb.getChildren().addAll(txt,more);
        center.getChildren().addAll(vvb);
    }

    private void add(String name)
    {
        if(!list.containsKey(name))
            list.put(name,new ArrayList<>());
    }

    private void add(String name, String lt)
    {
        if(list.containsKey(name))
            list.get(name).add(lt);
    }

    public HBox addAttribute(Button bt,String tab)
    {
        HBox hb = new HBox();
        hb.setSpacing(20);
        bt.setVisible(false);
        TextField att = new TextField(dep);
        Button ok = new Button("Ok");
        ok.setOnAction(e->
        {
            String what = att.getText();
            if(!what.equals(dep))
            {
                bt.setVisible(true);
                ok.setVisible(false);
                add(tab,what);
            }
        });
        hb.getChildren().addAll(new Text("Attribute : "),att,ok);
        return hb;
    }

    public void finished()
    {
        if(list.size() != 0)
        {
            CodeToGui sql = new CodeToGui(title.getText());
            sql.newData(list);
            new SgbdGui(sql).start(new Stage());
            stage.close();
        }
    }
}
