package be.ac.umons.projetBDD;

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

    public static void saveData(String file,DataBase db)
    {
        try
        {
            ObjectOutputStream exit = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("./misc/"+file+".sgbd"))));
            exit.writeObject(db);
            exit.close();
        }
        catch (IOException ignored){System.out.println(ignored);} //TODO : gére cela pour pas vide !
    }

    public static DataBase giveData(String file)
    {
        DataBase db = null;
        try
        {
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("./misc/"+file+".sgbd"))));
            db = (DataBase) in.readObject();
            in.close();
        }
        catch (IOException | ClassNotFoundException ignored){}
        return db;
    }
}
