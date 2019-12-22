package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Sql;
import be.ac.umons.projetBDD.Dependence;

public class ListDF extends CommandDF {

    public ListDF(Sql db, String[] args) {
        super(db, args);
        possibleNumberOfArgs.add(0);
        possibleNumberOfArgs.add(1);
    }

    @Override
    protected String doAction() {
        if (args.length > 1)
            listDF(args[1]);
        else
            listDF();

        return null;
    }

    @Override
    public String getUsage() {
        return "ListDF [table_name]";
    }

    private void listDF() {
        for (String key : db.getDependenciesMap().keySet())
            listDF(key);
    }

    private void listDF(String tableName) {
        System.out.println(String.format("DF in %s : (<lhs> -> <rhs>)", tableName));
        for (Dependence dep: db.getDependenciesMap().get(tableName))
            System.out.println(String.format("  %s -> %s", dep.getLhs(), dep.getRhs()));
    }
}
