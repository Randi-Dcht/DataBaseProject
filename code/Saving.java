package umons;

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
      ecrire = new PrintWriter(new BufferedWriter(new FileWriter("./"+ fichier +".saving",true)));
      ecrire.println("Utilisation de la base de donnée");
      ecrire.println("@auteur: Randy Dauchot & Guillaume Cardoen");
      ecrire.println("#projet Bachelier Base de donnée 2019");
      ecrire.println("_____________________________________________________________________________________");
    }
    catch(Exception e)
    {}
  }

  /*public static void OPEN(String fichier)
  {
    try
    {
      ecrire = new PrintWriter(new BufferedWriter(new FileWriter("./misc/FichierResumer/"+ fichier +".saving")));
    }
    catch(IOException e)
    {
      ERROR(e,"open");
    }
    ecrire.println("Une nouvelle partie démarre ...");
  }*/


  public static void WRITE(String phrase)
  {
    DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    ecrire.print(format.format(date) + " : " + System.getProperty("os.name") + " : ");
    ecrire.println(phrase);
  }


  public static void CLOSE()
  {
    ecrire.println("___________________________________________________");
    ecrire.close();
  }
}
