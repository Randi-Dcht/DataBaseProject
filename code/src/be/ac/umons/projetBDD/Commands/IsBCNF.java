package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Sql;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * The class allows to check if a database is in BCNF.
 * @author Cardoen Guillaume (Student in computer sciences UMONS)
 */
public class IsBCNF extends CommandDF {

    /**
     * Construct a new instance.
     * @param db The database.
     * @param args The arguments given with the command.
     */
    public IsBCNF(Sql db, String[] args) {
        super(db, args);
        possibleNumberOfArgs.add(1);
    }

    @Override
    protected void doAction() {
        List<Dependence> depList = db.getDependenciesMap().get(args[1]);
        for (Dependence df : depList) {
            if (isTrivial(df))
                continue;
            Set<String> determinedOne = new HashSet<>(df.getLhs()); // no duplicates
            List<Dependence> tempList = new LinkedList<>(depList); // copy the list
            boolean changed = true;
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
            if (! determinedOne.containsAll(db.getTableContentName(args[1]))) {
                System.out.println(String.format("The table (%s) isn't in BCNF...", args[1]));
                memory.add(String.format("The table (%s) isn't in BCNF...", args[1]));
                return;
            }
        }
        System.out.println(String.format("The table (%s) is in BCNF !", args[1]));
        memory.add(String.format("The table (%s) is in BCNF !", args[1]));

    }

    /**
     * Check if a DF is trivial or not.
     * @param df The DF.
     * @return If a DF is trivial or not.
     */
    private boolean isTrivial(Dependence df) {
        return df.getLhs().contains(df.getRhs());
    }

    @Override
    public String getUsage() {
        return "IsBCNF <table_name>";
    }
}