package be.ac.umons.projetBDD;

import be.ac.umons.projetBDD.GUI.MainGui;
import javafx.stage.Stage;

import java.util.Scanner;

public class ChooseMain
{
    public static String user;
    public static Scanner read;

    public static void main(String[] args)
    {
        read = new Scanner(System.in);
        choose();
    }

    public static boolean check(String who)
    {
        if(who.equals("gui")|| who.equals("cmd") || who.equals("quit"))
            return true;
        return false;
    }

    public static void choose()
    {
        getMessage();
        user = read.nextLine();
        if(check(user))
            start();
        else
            choose();
    }

    public static void getMessage()
    {
        System.out.println("Choose between line command or graphics for use this application");
        System.out.println("quit the application --> quit");
        System.out.println("Graphics interface   --> gui");
        System.out.println("Line command         --> cmd");
    }

    public static void start()
    {
        if(user.equals("gui"))
            MainGui.main(null);
        if(user.equals("cmd"))
            Main.main(null);
    }
}
