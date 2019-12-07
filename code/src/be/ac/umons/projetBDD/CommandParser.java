package be.ac.umons.projetBDD;

public class CommandParser {

    private Database db;

    public CommandParser(Database db) {
        this.db = db;
    }

    public void executeCommand(String comm) {
        String[] commTab = comm.split(" ");
        switch (commTab[0].toLowerCase()) {
            case "listdf":
                listDF();
                break;
            case "adddf":
                if (commTab.length != 4) {
                    System.err.println("The command adddf takes 3 arguments !\nUsage : adddf <tableName> <lhs> <rhs>");
                    break;
                }
                addDF(commTab[1], commTab[2], commTab[3]);
                break;
            default:
                System.err.println(String.format("Command \"%s\" isn't defined !", commTab[0]));

        }
    }

    private void listDF() {
        System.out.println("DF in DB : (<tableName> : <lhs> -> <rhs>)");
        for (String key : db.getDependenciesMap().keySet())
            for (Dependance dep: db.getDependenciesMap().get(key))
                System.out.println(String.format("  %s : %s -> %s", dep.getTableName(), dep.getLhs(), dep.getRhs()));
    }

    private void addDF(String tableName, String lhs, String rhs) {
        Dependance dep = new Dependance(tableName, lhs, rhs);
        if (db.addDependence(dep))
            System.out.println("DF has been added !");
    }

}
