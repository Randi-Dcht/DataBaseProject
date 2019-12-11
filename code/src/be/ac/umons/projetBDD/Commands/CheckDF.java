package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckDF extends Command {

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

        boolean errorInDF = false;

        for (Dependence dep : deps) {
            boolean messageAlreadyShowed = false;
            Map<String, String> verifMap = new HashMap<>();
            List<String> lhsList = dep.getLhs();
            String commandPart = "";
            String lhs = lhsList.toString().substring(1, lhsList.toString().length() - 1);
            String rhs = dep.getRhs();
            for (String s : lhsList)
                commandPart += s + ",";
            commandPart += dep.getRhs();
            ResultSet rs = db.executeQuery(String.format("SELECT %s FROM %s;", commandPart, args[1]));
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
                        if (verifMap.get(lhsValue) != rhsValue) {
                            if (! messageAlreadyShowed) {
                                System.out.println(String.format("The DF \"%s -> %s\" isn't respected in table %s", lhs, rhs, args[1]));
                                messageAlreadyShowed = true;
                            }
                            System.out.println(String.format("    Entry %d : %s -> %s", rs.getRow(), lhsValue, rhsValue));
                            System.out.println(String.format("    Contradiction with : %s -> %s\n", lhsValue, verifMap.get(lhsValue)));
                            errorInDF = true;
                        }
                    } else {
                        verifMap.put(lhsValue, rhsValue);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (! errorInDF)
            System.out.println(String.format("All the DF all the table (%s) are respected !", args[1]));
    }

    @Override
    public String getUsage() {
        return null;
    }
}
