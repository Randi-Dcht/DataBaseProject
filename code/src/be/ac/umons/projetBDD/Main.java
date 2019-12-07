package be.ac.umons.projetBDD;

import java.io.File;
import java.util.*;

public class Main {

    private static CommandParser cp;
    private static Database db;

    public static void main(String[] args)
    {
        Saving.REOPEN("basedonnee");
//        BaseDonne bd = new BaseDonne("jdbc:sqlite:");
//        bd.connect("./misc/TestGui.db");
//        bd.createDataBase();
////        bd.creerTable("CREATE TABLE etudiant(Matricule integer PRIMARY KEY, Nom text, Faculte text)");
//        bd.createTable("etudiant", "Matricule integer PRIMARY KEY, Nom text, Faculte text");
////        bd.insererTable("INSERT INTO etudiant(Matricule,Nom,Faculte) VALUES(1234,'rnd','sciences')");
//        bd.insertIntoTable("etudiant", "1234,'rnd','sciences'");
//        bd.close();
//        Saving.CLOSE();

        System.out.println("Database usage");
        System.out.println("@author: Randy Dauchot & Guillaume Cardoen");
        System.out.println("#Base de données I : Project (Bloc 2 Computer Sciences at UMons)");
        System.out.println("_____________________________________________________________________________________");

        db = new Database("jdbc:sqlite:");
        if (! db.connect(askDatabasePath())) {
            System.err.println("An error was raised while trying to open the database !");
            return;
        }

        checkFuncDep();

        cp = new CommandParser(db);

        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Command : ");
            String comm = input.nextLine().toLowerCase();
            if (comm.equals("exit"))
                break;
            cp.executeCommand(comm);
        }
        db.close();
    }

    public static void checkFuncDep() {
        if (! db.doesTableExists("FuncDep")) {
            if (db.createTable("FuncDep", "table_name text, lhs text, rhs text"))
                System.out.println("The table FuncDep was automatically created !");
            else
                System.err.println("The table FuncDep wasn't found and an error was raised while trying to create it.");
        }
    }

    public static String askDatabasePath() {
        System.out.println("Please, enter the path to the database : ");
        Scanner input = new Scanner(System.in);
        String path = input.nextLine();
        if (! new File(path).exists()) {
            System.err.println("The given path is incorrect !");
            return askDatabasePath();
        }
        input.close();
        return path;
    }

}
