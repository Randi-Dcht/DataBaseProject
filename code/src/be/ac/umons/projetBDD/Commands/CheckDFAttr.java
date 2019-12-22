package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Main;
import be.ac.umons.projetBDD.Sql;
import java.util.ArrayList;
import java.util.List;

public class CheckDFAttr extends CommandDF {

    List<String> names;
    private boolean errorInDF = false;

    public CheckDFAttr(Sql db, String[] args) {
        super(db, args);
        possibleNumberOfArgs.add(1);
        names = db.getTableContentName(args[1]);
    }

    @Override
    protected String doAction() {
        ArrayList<Dependence> deps = new ArrayList<>(db.getDependenciesMap().get(args[1]));

        for (Dependence dep : deps) {
            if (checkAttr(dep) || askForDeleting(dep)) {
                db.removeDependence(dep);
                errorInDF = true;
            }
        }

        if (! errorInDF)
            System.out.println(String.format("All the DF all the table (%s) are correct !", args[1]));

        return null;
    }

    public boolean checkAttr(Dependence dep) {
        boolean res = true;
        for (String s : dep.getLhs()) {
            if (! names.contains(s)) {
                if (res)
                    System.out.println(String.format("The DF \"%s -> %s\" isn't correct in table %s", dep.getLhs(), dep.getRhs(), args[1]));
                System.out.println(String.format("    The argument %s doesn't exist in table %s", s, args[1]));
                res =  false;
            }
        }
        if (! names.contains(dep.getRhs())) {
            if (res)
                System.out.println(String.format("The DF \"%s -> %s\" isn't correct in table %s", dep.getLhs(), dep.getRhs(), args[1]));
            System.out.println(String.format("    The argument %s doesn't exist in table %s", dep.getRhs(), args[1]));
            res = false;
        }
        return res;
    }

    private boolean askForDeleting(Dependence oneToDel) {
        System.out.println(String.format("Do you want to delete %s -> %s from the table (%s) ? (y/N)", oneToDel.getLhs(), oneToDel.getRhs(), args[1]));
        String ans = Main.input.nextLine().trim().toLowerCase();
        return ans.equals("y");
    }

    @Override
    public String getUsage() {
        return "(CheckDFAttr | cdfa) <table_name>";
    }
}
