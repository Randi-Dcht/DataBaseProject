package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Sql;
import be.ac.umons.projetBDD.Dependence;

/**
 * The class allows to list the DF of a given table.
 */
public class ListDF extends CommandDF {

    /**
     * Construct a new instance of the class.
     * @param db The database.
     * @param args The arguments.
     */
    public ListDF(Sql db, String[] args) {
        super(db, args);
        possibleNumberOfArgs.add(0);
        possibleNumberOfArgs.add(1);
    }

    @Override
    protected void doAction() {
        if (args.length > 1)
            listDF(args[1]);
        else
            listDF();
    }

    @Override
    public String getUsage() {
        return "ListDF [table_name]";
    }

    /**
     * List the DF of all tables.
     */
    private void listDF() {
        for (String key : db.getDependenciesMap().keySet())
            listDF(key);
    }

    /**
     * List the DF of a specific table.
     * @param tableName The table.
     */
    private void listDF(String tableName) {
        System.out.println(String.format("DF in %s : (<lhs> -> <rhs>)", tableName));
        for (Dependence dep: db.getDependenciesMap().get(tableName))
            System.out.println(String.format("  %s -> %s", dep.getLhs(), dep.getRhs()));
    }
}
