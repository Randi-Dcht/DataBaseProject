package be.ac.umons.projetBDD.GUI;

import javafx.scene.layout.BorderPane;

public class DataGui
{
    public static final String url = "misc/ressource/";
    private BorderPane dp;
    private CodeToGui db;


    public DataGui(CodeToGui db)
    {
        this.db = db;
    }

    public BorderPane create()
    {
        dp = new BorderPane();
        return dp;
    }

}
