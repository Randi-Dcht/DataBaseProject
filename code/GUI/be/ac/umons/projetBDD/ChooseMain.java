package be.ac.umons.projetBDD;

import be.ac.umons.projetBDD.GUI.MainGui;

import java.util.Scanner;
/**
 * This class allows to choose between graphic and line command
 * @author Randy Dauchot (étudiant en sciences informatiques Umons)
 */
public class ChooseMain
{
    /*choose of the user in line command*/
    public static String user;
    /*scanner for choose*/
    public static Scanner read;

    /**
     * This method is an entry point of this application
     */
    public static void main(String[] args)
    {
        System.out.println("Project Umons 2019 of Dependence and dataBase");
        read = new Scanner(System.in);
        choose();
    }

    /**
     * This method allows to verifie the choose of the user
     * @param who who is the word to check
     */
    public static boolean check(String who)
    {
        if(who.equals("gui")|| who.equals("cmd") || who.equals("quit"))
            return true;
        return false;
    }

    /**
     * This method allows to ask a question to the user
     * This method is recursive method if the answer is bad.
     */
    public static void choose()
    {
        getMessage();
        user = read.nextLine();
        if(check(user))
            start();
        else
            choose();
    }

    /**
     * This method allows to print in line command the choose of the user
     */
    public static void getMessage()
    {
        System.out.println("Choose between line command or graphics for use this application");
        System.out.println("quit the application --> quit");
        System.out.println("Graphics interface   --> gui");
        System.out.println("Line command         --> cmd");
    }

    /**
     * This method allows to launch the line command or graphic application
     */
    public static void start()
    {
        if(user.equals("gui"))
            MainGui.main(null);
        if(user.equals("cmd"))
            Main.main(null);
    }
}
