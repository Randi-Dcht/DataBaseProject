package be.ac.umons.projetBDD;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Main {

    private static Map<String, List<Dependance>> dependanciesMap;

    public static void main(String[] args)
    {
        dependanciesMap = new HashMap<>();
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

        Database db = new Database("jdbc:sqlite:");
        if (! db.connect(askDatabasePath())) {
            System.err.println("An error was raised while trying to open the database !");
            return;
        }

        if (! db.doesTableExists("FuncDep")) {
            if (db.createTable("FuncDep", "table_name text, lhs text, rhs text"))
                System.out.println("The table FuncDep was automatically created !");
            else
                System.err.println("The table FuncDep wasn't found and an error was raised while trying to create it.");
        }

        ResultSet deps = db.executeQuery("SELECT * FROM FuncDep;");
        try {
            while (deps.next()) {
                Dependance dep = new Dependance(deps.getString(1), deps.getString(2), deps.getString(3));
                if (! dependanciesMap.containsKey(dep.getTableName()))
                    dependanciesMap.put(dep.getTableName(), new ArrayList<>());
                dependanciesMap.get(dep.getTableName()).add(dep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Command : ");
            String comm = input.nextLine().toLowerCase();
            if (comm.equals("listdf")) {
                System.out.println("DF in DB : (<tableName> : <lhs> -> <rhs>)");
                for (String key :  dependanciesMap.keySet())
                    for (Dependance dep: dependanciesMap.get(key))
                        System.out.println(String.format("  %s : %s -> %s", dep.getTableName(), dep.getLhs(), dep.getRhs()));
            }
            else if (comm.equals("exit"))
                break;
        }
        db.close();
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
