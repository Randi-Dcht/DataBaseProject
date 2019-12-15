package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Sql;
import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Main;

public class AddDF extends Command implements Confirmable {

    boolean confirmed;

    public AddDF(Sql db, String[] args) {
        super(db, args);
        possibleNumberOfArgs.add(3);
    }

    @Override
    protected void doAction() {
        addDF(args[1], args[2], args[3]);
    }

    @Override
    public String getUsage() {
        return "AddDF <tableName> <lhs> <rhs>";
    }

    private void addDF(String tableName, String lhs, String rhs) {
        if (! db.tableExists(tableName) && ! confirmed) {
            System.out.println(String.format("Warning : the table %s doesn't exist ! Execute \"ExecAnyway\" if you still want to add the dependency.", tableName));
            Main.commandToConfirm = this;
            return;
        }
        Dependence dep = new Dependence(tableName, lhs, rhs);
        if ((new RemoveRedundantDependencies(db, new String[] {"rdd", args[1]})).isRedundant(dep)) {
            System.out.println(String.format("Warning : The dependence (%s -> %s) is redundant in the table (%s). Execute \"ExecAnyway\" if you still want to add the dependency.", dep.getLhs(), dep.getRhs(), args[1]));
            Main.commandToConfirm = this;
        } else if (! (new CheckDF(db, new String[] {"CheckDF", args[1]})).checkDF(dep)) {
            System.out.println("Execute \"ExecAnyway\" if you still want to add the dependency.");
            Main.commandToConfirm = this;
        } else {
            if (db.addDependence(dep))
                System.out.println("DF has been added !");
        }
    }

    @Override
    public void runAnyway() {
        confirmed = true;
        doAction();
    }
}
