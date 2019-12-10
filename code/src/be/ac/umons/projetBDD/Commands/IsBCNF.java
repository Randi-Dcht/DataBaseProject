package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependance;
import be.ac.umons.projetBDD.Sql;

import java.util.*;

public class IsBCNF extends Command {

    public IsBCNF(Sql db, String[] args) {
        super(db, args);
        possibleNumberOfArgs.add(1);
    }

    @Override
    protected void doAction() {
        if (! db.tableExists(args[1])) {
            System.err.println(String.format("ERROR : The given table (%s) wasn't found !", args[1]));
            return;
        }
        List<Dependance> depList = db.getDependenciesMap().get(args[1]);
        if (depList == null) {
            System.out.println(String.format("The table (%s) is in BCNF !", args[1]));
            return;
        }
        for (Dependance df : depList) {
            List<String> lhs = new ArrayList<>(df.getLhs());
            Set<String> determinedOne = new HashSet<>(lhs); // no duplicates
            List<Dependance> tempList = new LinkedList<>(depList); // copy the list
            boolean changed = true;
            while (changed && tempList.size() != 0) {
                changed = false;
                for (Iterator<Dependance> it = tempList.listIterator(); it.hasNext();) {
//                    if (df == df2)
//                        continue;
                    Dependance df2 = it.next();
                    if (lhs.containsAll(df2.getLhs())) {
                        determinedOne.add(df2.getRhs());
                        lhs.add(df2.getRhs());
                        it.remove();
                        changed = true;
                    }
                }
            }
            if (! determinedOne.containsAll(db.getTableContentName(args[1]))) {
                System.out.println(String.format("The table (%s) isn't in BCNF...", args[1]));
                return;
            }
        }
        System.out.println(String.format("The table (%s) is in BCNF !", args[1]));

    }



    @Override
    public String getUsage() {
        return "IsBCNF <table_name>";
    }
}
