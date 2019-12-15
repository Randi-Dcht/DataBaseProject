package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Main;
import be.ac.umons.projetBDD.Sql;

import java.util.*;

public class RemoveRedundantDependencies extends CommandDF {
    public RemoveRedundantDependencies(Sql db, String[] args) {
        super(db, args);
        possibleNumberOfArgs.add(1);
        possibleNumberOfArgs.add(2);
    }

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

    private boolean askForDeleting(Dependence oneToDel) {
        System.out.println(String.format("Are you sure you want to delete %s -> %s from the table (%s) ? (y/N)", oneToDel.getLhs(), oneToDel.getRhs(), args[1]));
        String ans = Main.input.nextLine().trim().toLowerCase();
        return ans.equals("y");
    }

    @Override
    public String getUsage() {
        return "(RemoveLogicalDependencies | rdd) <table_name>";
    }
}