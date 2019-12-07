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
                if (commTab.length > 2) {
                    System.err.println("The command ListDF takes 1 or 2 argument(s) !\nUsage : ListDF [table_name]");
                    break;
                }
                else if (commTab.length == 2)
                    listDF(commTab[1]);
                else
                    listDF();
                break;
            case "adddf":
                if (commTab.length != 4) {
                    System.err.println("The command AddDF takes 3 arguments !\nUsage : AddDF <tableName> <lhs> <rhs>");
                    break;
                }
                addDF(commTab[1], commTab[2], commTab[3]);
                break;
            default:
                System.err.println(String.format("Command \"%s\" isn't defined !", commTab[0]));
        }
    }

    private void listDF() {
        for (String key : db.getDependenciesMap().keySet())
            listDF(key);
    }

    private void listDF(String tableName) {
        System.out.println(String.format("DF in %s : (<tableName> : <lhs> -> <rhs>)", tableName));
        if (! db.getDependenciesMap().containsKey(tableName))
            System.err.println(String.format("The table %s doesn't exist !", tableName));
        for (Dependance dep: db.getDependenciesMap().get(tableName))
            System.out.println(String.format("  %s : %s -> %s", dep.getTableName(), dep.getLhs(), dep.getRhs()));

    }

    private void addDF(String tableName, String lhs, String rhs) {
        Dependance dep = new Dependance(tableName, lhs, rhs);
        if (db.addDependence(dep))
            System.out.println("DF has been added !");
    }

}
