package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Main;
import be.ac.umons.projetBDD.Sql;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class CheckDF extends Command {

    private boolean errorInDF = false;

    public CheckDF(Sql db, String[] args) {
        super(db, args);
        possibleNumberOfArgs.add(1);
    }

    @Override
    protected void doAction() {
        if (! db.tableExists(args[1])) {
            System.err.println(String.format("ERROR : The table (%s) doesn't exist !", args[1]));
            return;

        }
        List<Dependence> deps = db.getDependenciesMap().get(args[1]);
        if (deps == null) {
            System.out.println("This table has no DF !");
            return;
        }

        for (Dependence dep : deps) {
            checkDF(dep);
        }

        if (! errorInDF)
            System.out.println(String.format("All the DF all the table (%s) are respected !", args[1]));
    }

    public boolean checkDF(Dependence dep) {
        Main.contradictionsIDs = new HashSet<>();
        List<Point> idContradictionList = new ArrayList<>();
        boolean messageAlreadyShowed = false;
        Map<String, IntString> verifMap = new HashMap<>();
        List<String> lhsList = dep.getLhs();
        String commandPart = "";
        String lhs = lhsList.toString().substring(1, lhsList.toString().length() - 1);
        String rhs = dep.getRhs();
        for (String s : lhsList)
            commandPart += s + ",";
        commandPart += dep.getRhs();
        ResultSet rs = db.executeQuery(String.format("SELECT %s FROM %s;", commandPart, args[1]));
        if (rs == null) {
            System.err.println("An error has occurred while checking if the DF was respected !");
            System.exit(1);
        }
        try {
            while (rs.next()) {
                String lhsValue = "";
                for (int i = 0; i < lhsList.size(); i++) {
                    lhsValue += rs.getString(i + 1);
                    if (i != lhsList.size() - 1)
                        lhsValue += ",";
                }
                String rhsValue = rs.getString(rhs);
                if (verifMap.containsKey(lhsValue)) {
                    if (! verifMap.get(lhsValue).s.equals(rhsValue)) {
                        if (! messageAlreadyShowed) {
                            System.out.println(String.format("The DF \"%s -> %s\" isn't respected in table %s", lhs, rhs, args[1]));
                            messageAlreadyShowed = true;
                        }
                        System.out.println(String.format("    Entry %d : %s -> %s", rs.getRow(), lhsValue, rhsValue));
                        System.out.println(String.format("    Contradiction with entry %d : %s -> %s\n", verifMap.get(lhsValue).x, lhsValue, verifMap.get(lhsValue).s));
                        idContradictionList.add(new Point(rs.getRow(), 0));
                        errorInDF = true;
                    }
                } else {
                    verifMap.put(lhsValue, new IntString(rhsValue, rs.getRow()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (idContradictionList.size() != 0) {
            Main.contradictionsIDs.addAll(idContradictionList);
            Main.contradictionsTableName = args[1];
            return false;
        }
        return true;
    }

    @Override
    public String getUsage() {
        return null;
    }

    public class IntString {
        int x;
        String s;
        public IntString(String s, int x) {
            this.s = s;
            this.x = x;
        }
    }
}
