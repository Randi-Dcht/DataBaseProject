package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Sql;
import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class define the command to be used in line command.
 * @author Guillaume Cardoen (étudiant en sciences informatique Umons)
 */
public abstract class Command {
    protected Sql db;
    protected String[] args;
    protected List<Integer> possibleNumberOfArgs;
    protected ArrayList<String> memory;

    /**
     * The constructor allows to define a command
     * @param db who is a sql for dataBase
     * @param args who are the arguments of the command
     */
    public Command(Sql db, String[] args) {
        this.db = db;
        this.args = args;
        possibleNumberOfArgs = new ArrayList<>();
        memory = new ArrayList<>();
    }

    /**
     * This method allows to launch the command (doAction()) if the number of Args are sufficient
     */
    public void run() {
        if (checkArgsNumber())
            doAction();
    }

    /**
     * This method return the result of the command who call
     * @return memory (String)
     */
    public ArrayList<String> getMemory()
    {
        return memory;
    }

    /**
     * Do the action determined by the given arguments.
     */
    protected abstract void doAction();

    /**
     * This method allows to check the number of the arguments in the list.
     * @return true if the length of arguments list is sufficient and false otherwise
     */
    public boolean checkArgsNumber() {
        if (possibleNumberOfArgs.contains(-1))
            return true;
        if (! possibleNumberOfArgs.contains(args.length - 1)) {
            System.err.println(String.format("Command %s takes %s arguments.\nUsage : %s", args[0], possibleNumberOfArgs, getUsage()));
            memory.add(String.format("Command %s takes %s arguments.\nUsage : %s", args[0], possibleNumberOfArgs, getUsage()));
            return false;
        }
        return true;
    }
    /**
     * Return how the command should be used.
     * @return How the command should be used.
     */
    public abstract String getUsage();
}