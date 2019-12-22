package be.ac.umons.projetBDD;

import be.ac.umons.projetBDD.Commands.Confirmable;

import java.awt.Point;
import java.io.File;
import java.util.Scanner;
import java.util.Set;

/**
 * This class allows to interaction between the dataBase and the user through
 *    the command line in the terminal.
 * @author Randy Dauchot & Guillaume Cardoen (Student in computer science UMONS)
 */
public class Main {

    private static CommandParser cp;
    private static Sql db;
    public static Confirmable commandToConfirm;
    public static String contradictionsTableName;
    public static Set<Point> contradictionsIDs;
    public static Scanner input;

    /**
     * This method allows to launch the application in command line.
     * This is the entry point in the application.
     */
    public static void main(String[] args)
    {
        input = new Scanner(System.in);
        Saving.REOPEN("basedonnee");
        System.out.println("Database usage");
        System.out.println("@author: Randy Dauchot & Guillaume Cardoen");
        System.out.println("#Base de données I : Project (Bloc 2 Computer Sciences at UMons)");
        System.out.println("_____________________________________________________________________________________");

        db = new Sql("jdbc:sqlite:");
        if (! db.connect(askDatabasePath())) {
            System.err.println("An error was raised while trying to open the database !");
            return;
        }

        checkFuncDep();
        db.refreshDependenciesMap();

        cp = new CommandParser(db);

        while (true) {
            System.out.println("Command : ");
            String comm = input.nextLine();
            if (comm.toLowerCase().equals("exit"))
                break;
            cp.executeCommand(comm);
        }
        db.close();
    }

    /**
     * This method said to user, how does it create the dataBase of dependence
     */
    public static void checkFuncDep() {
        if (! db.tableExists("FuncDep")) {
            if (db.createTable("FuncDep", "table_name text, lhs text, rhs text"))
                System.out.println("The table FuncDep was automatically created !");
            else
                System.err.println("The table FuncDep wasn't found and an error was raised while trying to create it.");
        }
    }

    /**
     * This method allows to ask to enter the path of database in command line
     * @return name of path (string)
     */
    public static String askDatabasePath() {
        System.out.println("Please, enter the path to the database : ");
        String path = input.nextLine();
        if (! new File(path).exists()) {
            System.err.println("The given path is incorrect !");
            return askDatabasePath();
        }
        return path;
    }
}
