package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Sql;

import java.util.*;

public class ListKey extends CommandDF {
    /**
     * This constructor allows define a command for a dependence.
     *
     * @param db   who is the database
     * @param args who is the tab with the arguments of the command
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
     * Code from https://stackoverflow.com/a/5162713 by superfav
     * @param values
     * @param size
     * @param <T>
     * @return
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
