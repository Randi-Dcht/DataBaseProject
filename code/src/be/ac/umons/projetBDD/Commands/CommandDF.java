package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Sql;
import java.util.List;

/**
 * This abstract class allows to define the command for the dependence in the dataBase.
 * @author Guillaume Cardoen (étudiant en sciences informatique Umons).
 */
public abstract class CommandDF extends Command {

    /**
     * This constructor allows define a command for a dependence.
     * @param  db who is the database
     * @param args who is the tab with the arguments of the command
     */
    public CommandDF(Sql db, String[] args) {
        super(db, args);
    }

    /**
     * This method is an overload of the parent method (run())
     * This method allows to launch the command (doAction)
     */
    @Override
    public void run() {
        if (checkArgsNumber() && doesTableExistAndHaveDF())
            doAction();
    }

    /**
     * This method allows to said if the table with dependence exist.
     * @return true if the table exist and false otherwise
     */
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
