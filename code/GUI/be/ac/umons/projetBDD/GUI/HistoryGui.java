package be.ac.umons.projetBDD.GUI;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class HistoryGui
{
    final String file;
    private BorderPane dp;

    public HistoryGui(String file)
    {
        this.file = file;
    }

    public BorderPane create()
    {
        dp = new BorderPane();

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(readHistory());
        dp.setCenter(scroll);

        return dp;
    }


    public VBox readHistory()
    {
        VBox list = new VBox();
        String vertical;
        try
        {
            BufferedReader ligne = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while ((vertical = ligne.readLine()) != null)
            {
                Text text = new Text(vertical);
                text.getStyleClass().add("history");
                list.getChildren().add(text);
            }
            ligne.close();
        }
        catch (Exception e){System.err.println(e);}

        list.getStyleClass().add("history");
        return list;
    }
}