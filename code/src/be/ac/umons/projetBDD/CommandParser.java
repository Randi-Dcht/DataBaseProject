package be.ac.umons.projetBDD;

public class CommandParser {

    private Database db;

    public CommandParser(Database db) {
        this.db = db;
    }

    public void executeCommand(String comm) {
        String[] commTab = comm.split(" ");
        switch (commTab[0]) {
            case "listdf":
                listDF();
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

}
