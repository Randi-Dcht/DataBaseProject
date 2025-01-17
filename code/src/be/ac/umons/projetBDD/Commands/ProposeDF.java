package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 */
public class ProposeDF extends Command {
    /**
     * The constructor define a command
     *
     * @param db   who is a sql for dataBase
     * @param args who are the arguments of the command
     */
    public ProposeDF(Sql db, String[] args) {
        super(db, args);
        possibleNumberOfArgs.add(1);
    }

    /**
     * Search and propose the possibles DF in a specific table.
     */
    @Override
    protected void doAction() {
        if (! db.tableExists(args[1])) {
            System.err.println(String.format("ERROR : The table (%s) doesn't exist !", args[1]));
            return;
        }

        List<String> dbAttr = db.getTableContentName(args[1]);
        int tableSize = dbAttr.size();
        ResultSet rs = db.executeQuery(String.format("SELECT * FROM %s", args[1]));
        List<String[]> results = new ArrayList<>();
        try {
            while (rs.next()) {
                String[] row = new String[tableSize];
                for (int i = 1; i <= tableSize; i++) {
                    row[i - 1] = rs.getString(i);
                }
                results.add(row);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        List<IntDependence> deps = new ArrayList<>();
        for (List<Integer> is: getAllPossiblesCombinations(0, tableSize)) {
            for (int j = 0; j < tableSize; j++) {
                if (is.size() > 0 && ! is.contains(j) && isADependence(results, is, j) && ! isRepetitive(deps, is, j)) {
                    String lhs = "";
                    for (int i : is)
                        lhs += dbAttr.get(i) + ",";
                    System.out.println(String.format("%s -> %s is a possible DF", lhs.substring(0, lhs.length() - 1), dbAttr.get(j)));
                    deps.add(new IntDependence(is, j));
                }
            }
        }
        System.out.println(getAllPossiblesCombinations(1,3));
    }

    /**
     * Check if a DF is repetitive with the previous dependencies given by <code>deps</code>.
     * @param deps The previous dependencies.
     * @param is The index of the attribute on the left of the DF.
     * @param j The index of the attribute of the right of the DF.
     * @return If a DF is repetitive.
     */
    protected boolean isRepetitive(List<IntDependence> deps, List<Integer> is, int j) {
        for (IntDependence dep : deps)
            if (dep.rhs == j && is.containsAll(dep.lhs))
                return true;
        return false;
    }

    /**
     * Return all possibles combinations starting at <code>from</code> and ending at <code>to</code>.
     * For example, from = 1 and to = 2 will return [[], [1], [2], [1,2]]
     * @param from The beginning.
     * @param to The ending.
     * @return All possibles combinations starting at <code>from</code> and ending at <code>to</code>.
     */
    protected List<List<Integer>> getAllPossiblesCombinations(int from, int to) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int i = from; i < to; i++)
            list.add(i);

        for (int i = 0; i <= list.size(); i++)
            res.addAll(combination(list, i));
        return res;
    }

    /**
     * Check if a DF is a dependence from a specific table.
     * @param table The table.
     * @param is The index of the attribute on the left of the DF.
     * @param j The index of the attribute of the right of the DF.
     * @return If a DF is a dependence from a specific table.
     */
    protected boolean isADependence(List<String[]> table, List<Integer> is, int j) {
        Map<String, String> tmp = new HashMap<>();
        for (String[] row : table) {
            String lhs = "";
            for (int i : is)
                lhs += row[i] + ",";
            if (tmp.containsKey(lhs) && ! tmp.get(lhs).equals(row[j]))
                return false;
            tmp.put(lhs, row[j]);
        }
        return true;
    }

    /**
     * Return all possibles combinations of <code>values</code> with the size <code>size</code>.
     * Code from https://stackoverflow.com/a/5162713 by superfav
     * @param values The values to combine.
     * @param size The size of the combination.
     * @param <T>
     * @return All possibles combinations of <code>values</code> with the size <code>size</code>.
     */
    public static <T> List<List<T>> combination(List<T> values, int size) {

        if (0 == size) {
            return Collections.singletonList(Collections.<T> emptyList());
        }

        if (values.isEmpty()) {
            return Collections.emptyList();
        }

        List<List<T>> combination = new LinkedList<List<T>>();

        T actual = values.iterator().next();

        List<T> subSet = new LinkedList<T>(values);
        subSet.remove(actual);

        List<List<T>> subSetCombination = combination(subSet, size - 1);

        for (List<T> set : subSetCombination) {
            List<T> newSet = new LinkedList<T>(set);
            newSet.add(0, actual);
            combination.add(newSet);
        }

        combination.addAll(combination(subSet, size));

        return combination;
    }

    @Override
    public String getUsage() {
        return "(ProposeDF | pdf) <table_name>";
    }

    private class IntDependence {
        List<Integer> lhs;
        int rhs;

        public IntDependence(List<Integer> lhs, int rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }
    }
}