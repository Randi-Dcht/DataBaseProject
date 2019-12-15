package be.ac.umons.projetBDD;

import be.ac.umons.projetBDD.GUI.DataGui;

import java.io.*;
import java.util.*;
import java.text.*;

public class Saving
{
    private static PrintWriter ecrire;

    public static void REOPEN(String fichier)
    {
        try
        {
            ecrire = new PrintWriter(new BufferedWriter(new FileWriter("./misc/"+ fichier +".saving",true)));
            ecrire.println("Open the new session -- @auteur: Randy Dauchot & Guillaume Cardoen -- #projet Bachelier Base de donnée 2019");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public static void WRITE(String phrase)
    {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        ecrire.print(format.format(date) + " : " + System.getProperty("os.name") + " : ");
        ecrire.println(phrase);
    }


    public static void CLOSE()
    {
        ecrire.println("Close the session of dataBase");
        ecrire.println("___________________________________________________");
        ecrire.close();
    }

}
