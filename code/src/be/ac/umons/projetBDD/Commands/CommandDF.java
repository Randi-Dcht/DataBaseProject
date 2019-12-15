package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Sql;

import java.util.List;

public abstract class CommandDF extends Command {
    public CommandDF(Sql db, String[] args) {
        super(db, args);
    }

    @Override
    public void run() {
        if (checkArgsNumber() && doesTableExistAndHaveDF())
            doAction();
    }

    protected boolean doesTableExistAndHaveDF() {
        if (! db.tableExists(args[1])) {
            System.err.println(String.format("ERROR : The table (%s) doesn't exist !", args[1]));
            return false;

        }
        List<Dependence> tmp = db.getDependenciesMap().get(args[1]);
        if (tmp == null) {
            System.out.println("This table has no DF !");
            return false;
        }
        return true;
    }
}
