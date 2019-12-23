package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Sql;

import java.util.*;

/**
 * This class allows to list all the key of a table.
 */
public class ListKey extends CommandDF {
    /**
     * Construct a new instance of this class.
     *
     * @param db   The database
     * @param args The arguments
     */
    public ListKey(Sql db, String[] args) {
        super(db, args);
        possibleNumberOfArgs.add(1);
    }

    @Override
    protected void doAction() {
        List<List<Integer>> res = new ArrayList<>();
        List<String> dbAttr = db.getTableContentName(args[1]);
        int tableSize = dbAttr.size();
        for (List<Integer> is: getAllPossiblesCombinations(0, tableSize)) {
            List<String> lhs = new ArrayList<>();
            for (int i : is)
                lhs.add(dbAttr.get(i));
            boolean redundant = false;
            for (List<Integer> is2: res) {
                if (is.containsAll(is2)) {
                    redundant = true;
                    break;
                }
            }
            if (! redundant && isKey(db.getDependenciesMap().get(args[1]), lhs)) {
                res.add(is);
                System.out.println(String.format("%s is a key", Arrays.toString(lhs.toArray())));
            }
        }
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
     * Return all possibles combinations of <code>values</code> with the size <code>size</code>.
     * Code from https://stackoverflow.com/a/5162713 by superfav
     * @param values The values to combine.
     * @param size The size of the combination.
     * @param <T>
     * @return All possibles combinations of <code>values</code> with the size <code>size</code>.
     */
    public static <T> List<List<T>> combination(List<T> values, int size) { // TODO : Code redondant avec ProposeDF, à règler

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

    /**
     * Check if the given attributes represents a key or not for the given dependencies.
     * @param deps The dependencies of the table.
     * @param lhs The attributes.
     * @return If the given attributes represents a key or not for the given dependencies.
     */
    private boolean isKey(List<Dependence> deps, List<String> lhs) {
        List<Dependence> tempList = new LinkedList<>(deps); // copy the list
        boolean changed = true;
        Set<String> determinedOne = new HashSet<>(lhs); // no duplicates
        while (changed && tempList.size() != 0) {
            changed = false;
            for (Iterator<Dependence> it = tempList.listIterator(); it.hasNext();) {
                Dependence df2 = it.next();
                if (determinedOne.containsAll(df2.getLhs())) {
                    determinedOne.add(df2.getRhs());
                    it.remove();
                    changed = true;
                }
            }
        }
        return determinedOne.containsAll(db.getTableContentName(args[1]));
    }

    @Override
    public String getUsage() {
        return "ListKey <table_name>";
    }
}