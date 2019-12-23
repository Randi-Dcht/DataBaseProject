package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Main;
import be.ac.umons.projetBDD.Sql;
import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class allows to remove the tuple which is in contradiction with the dependencies of the table.
 * @author Guillaume Cardoen (Student in computer science UMONS)
 */
public class RemoveConflictsTuples extends Command {
    public RemoveConflictsTuples(Sql db, String[] args) {
        super(db, args);
        possibleNumberOfArgs.add(-1);
    }

    @Override
    protected void doAction() {
        if (Main.contradictionsTableName == null) {
            System.err.println("You have to use 'checkdf' before using this command !");
            return;
        }
        ResultSet rs = db.executeQuery(String.format("SELECT * FROM %s", Main.contradictionsTableName));
        String[] columnNames;
        try {
            columnNames = new String[rs.getMetaData().getColumnCount()];
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++)
                columnNames[i] = rs.getMetaData().getColumnLabel(i + 1);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("The following tuples were deleted :");
        List<Integer> toRemove = getListToRemove();
        try {
            int actualOne = 0;
            for (Iterator<Integer> it = toRemove.listIterator(); it.hasNext();) {
                int nextOne = it.next();
                for (; actualOne < nextOne; actualOne++) {
                    rs.next();
                }
                if (rs.isClosed()) {
                    System.err.println(String.format("The following argument was ignored : %d", actualOne));
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                StringBuilder conditionBuilder = new StringBuilder();
                sb.append("    (");
                for (int i = 0; i < columnNames.length; i++) {
                    sb.append(String.format("%s='%s'", columnNames[i], rs.getString(i + 1)));
                    conditionBuilder.append(String.format("%s='%s'", columnNames[i], rs.getString(i + 1)));
                    if (i != columnNames.length - 1) {
                        sb.append(", ");
                        conditionBuilder.append(" AND ");
                    }
                }
                sb.append(")");
                if (db.removeTuple(Main.contradictionsTableName, conditionBuilder.toString()))
                    System.out.println(sb.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Main.contradictionsIDs = null;
        Main.contradictionsTableName = null;
    }

    /**
     * Return a list of all the indexes of the tuples to remove.
     * @return A list of all the indexes of the tuples to remove.
     */
    private List<Integer> getListToRemove() {
        List<Integer> toRemove = new ArrayList<>();
        if (args[1].equals("auto")) {
            if (args.length > 2)
                System.err.println("Command \"rct\" takes 0 argument with argument auto (All the given one were ignored).");
            for (Point p : Main.contradictionsIDs)
                toRemove.add(p.x);
            Collections.sort(toRemove);
        } else {
            for (int i = 1; i < args.length; i++) {
                try {
                    toRemove.add(Integer.parseInt(args[i]));
                } catch (NumberFormatException e) {
                    System.err.println(String.format("The given argument was ignored because it wasn't an integer : %s", args[i]));
                }
            }
        }
        return toRemove;
    }

    @Override
    public String getUsage() {
        return "RemoveConflictsTuples <'auto', id1[, id2, ...]>\n rct <'auto', id1[, id2, ...]>";
    }
}