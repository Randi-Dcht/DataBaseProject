package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Sql;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    protected Sql db;
    protected String[] args;
    protected List<Integer> possibleNumberOfArgs;

    public Command(Sql db, String[] args) {
        this.db = db;
        this.args = args;
        possibleNumberOfArgs = new ArrayList<>();
    }

    public void run() {
        if (checkArgsNumber())
            doAction();
    }

    protected abstract void doAction();

    public boolean checkArgsNumber() {
        if (possibleNumberOfArgs.contains(-1))
            return true;
        if (! possibleNumberOfArgs.contains(args.length - 1)) {
            System.err.println(String.format("Command %s takes %s arguments.\nUsage : %s", args[0], possibleNumberOfArgs, getUsage()));
            return false;
        }
        return true;
    }

    public abstract String getUsage();
}
