package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.DataBase;
import be.ac.umons.projetBDD.Sql;
import be.ac.umons.projetBDD.Dependance;
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
        if (! db.doesTableExists(tableName) && ! confirmed) {
            System.out.println(String.format("Warning : the table %s doesn't exist ! Execute \"ExecAnyway\" if you still want to add the dependency.", tableName));
            Main.commandToConfirm = this;
            return;
        }
        Dependance dep = new Dependance(new DataBase(tableName), lhs, rhs);
        if (db.addDependence(dep))
            System.out.println("DF has been added !");
    }

    @Override
    public void runAnyway() {
        confirmed = true;
        doAction();
    }
}
