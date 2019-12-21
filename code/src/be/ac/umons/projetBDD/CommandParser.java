package be.ac.umons.projetBDD;

import be.ac.umons.projetBDD.Commands.*;

public class CommandParser {

    private Sql db;

    public CommandParser(Sql db) {
        this.db = db;
    }

    public void executeCommand(String comm) {
        String[] commTab = comm.split(" ");
        Command command = null;
        switch (commTab[0].toLowerCase()) {
            case "listdf":
                command = new ListDF(db, commTab);
                break;
            case "adddf":
                command = new AddDF(db, commTab);
                break;
            case "execanyway":
                if (commTab.length > 1)
                    System.out.println("Warning : The command ExecAnyway takes no arguments : the arguments has been ignored !");
                execAnyway();
                break;
            case "isbcnf":
                command = new IsBCNF(db, commTab);
                break;
            case "checkdf":
                command = new CheckDF(db, commTab);
                break;
            case "removeconflictstuples":
            case "rct":
                command = new RemoveConflictsTuples(db, commTab);
                break;
            case "removeredundantdependencies":
            case "rdd":
                command = new RemoveRedundantDependencies(db, commTab);
                break;
            case "checkdfattr":
            case "cdfa":
                command = new CheckDFAttr(db, commTab);
                break;
            case "proposedf":
            case "pdf":
                command = new ProposeDF(db, commTab);
                break;
            case "listkey":
                command = new ListKey(db, commTab);
                break;
            default:
                System.err.println(String.format("Command \"%s\" isn't defined !", commTab[0]));
                return;
        }
        if (command != null)
            command.run();
    }

    private void execAnyway() {
        if (Main.commandToConfirm != null) {
            Main.commandToConfirm.runAnyway();
            Main.commandToConfirm = null;
        }
        else
            System.err.println("No command to run was found !");
    }

}
