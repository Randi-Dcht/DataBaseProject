package be.ac.umons.projetBDD;

import java.io.*;
import java.util.*;
import java.text.*;

/**
 * This class allows to create a file with the history of this application
 * The error is write to be recover after
 * The action is save also to see after
 * @author Randy Dauchot (etudiant en sciences Informatique Umons)
 */
public class Saving
{
    private static PrintWriter ecrire;

    /**
     * This method allows to open or create a file for the save
     * @param fichier who is the name of the file
     */
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

    /**
     * This method allows ro write in the file and add the date of this write
     * @param phrase who is the string who is writte in the file
     */
    public static void WRITE(String phrase)
    {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        ecrire.print(format.format(date) + " : " + System.getProperty("os.name") + " : ");
        ecrire.println(phrase);
    }

    /**
     * This methods allows to close the file of the saving.
     */
    public static void CLOSE()
    {
        ecrire.println("Close the session of dataBase");
        ecrire.println("___________________________________________________");
        ecrire.close();
    }

}
