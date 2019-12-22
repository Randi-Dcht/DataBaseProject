package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Main;
import be.ac.umons.projetBDD.Sql;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class allows to remove a dependence in the dataBase
 * @author Guillaume Cardoen (Student in computer science UMONS)
 */
public class RemoveRedundantDependencies extends CommandDF {

    /**
     * This constructor allows to give a dataBAse and the arguments of this function
     * @param db who is the dataBase (sql)
     * @param  args who are the arguments of this command
     */
    public RemoveRedundantDependencies(Sql db, String[] args) {
        super(db, args);
        possibleNumberOfArgs.add(1);
        possibleNumberOfArgs.add(2);
    }

    /**
     * This method allows to launch the remove redundant dependence in the dataBase
     */
    @Override
    protected void doAction() {
        List<Dependence> dependenceList = db.getDependenciesMap().get(args[1]);
        List<Dependence> redundantDeps = new ArrayList<>();
        for (Dependence dependence : dependenceList) {
            if (isRedundant(dependence))
                redundantDeps.add(dependence);
        }
        if (redundantDeps.size() == 0)
            System.out.println("No logical dependence has been found !");
        else
            System.out.println(String.format("These logical dependencies has been found in the table (%s):", args[1]));
        for (Dependence dep : redundantDeps)
            System.out.println(String.format("    %s -> %s", dep.getLhs(), dep.getRhs()));
        if (! args[2].equals("list")) {
            if (! args[2].equals(""))
                System.err.println(String.format("Warning : %s has been ignored !", args[2]));
            for (Dependence dep: redundantDeps) {
                if (askForDeleting(dep))
                    db.removeDependence(dep);
            }
        }
    }

    /**
     * This method allows to check the redundancy dependence of this dataBase
     * @param dep who is the dependence for check if redundancies
     * @return true if the dependence true if exist and false otherwise
     */
    public boolean isRedundant(Dependence dep) {
        List<Dependence> dependenceList = db.getDependenciesMap().get(args[1]);
        if (dep.getLhs().contains(dep.getRhs())) {
            return true;
        }
        List<String> foundOne = new ArrayList<>(dep.getLhs());
        List<Dependence> tmpDeps = new LinkedList<>(dependenceList);
        boolean modified = true;
        while (modified && tmpDeps.size() > 0) {
            modified = false;
            for (Iterator<Dependence> it = tmpDeps.listIterator(); it.hasNext();) {
                Dependence d = it.next();
                if (dep == d)
                    continue;
                if (foundOne.containsAll(dep.getLhs())) {
                    foundOne.add(dep.getRhs());
                    it.remove();
                    modified = true;
                }
            }
        }
        return foundOne.contains(dep.getRhs());
    }

    /**
     * This method allows to ask to user if he want to delete dependence in the dataBase
     * @param oneToDel who is the dependence to remove
     * @return true if the user choose yes and false otherwise
     */
    private boolean askForDeleting(Dependence oneToDel) {
        System.out.println(String.format("Are you sure you want to delete %s -> %s from the table (%s) ? (y/N)", oneToDel.getLhs(), oneToDel.getRhs(), args[1]));
        String ans = Main.input.nextLine().trim().toLowerCase();
        return ans.equals("y");
    }

    /**
     * This method allows to give the function of this command
     * @return function of this command
     */
    @Override
    public String getUsage() {
        return "(RemoveLogicalDependencies | rdd) <table_name>";
    }
}
