package be.ac.umons.projetBDD.GUI;

import be.ac.umons.projetBDD.Commands.*;
import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Saving;
import be.ac.umons.projetBDD.Sql;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class allows to do bridge between the code of DataBase and Gui application
 * @author Randy Dauchot (étudiant en sciences informatique Umons)
 */
public class CodeToGui
{
    /*the list of dependence to read in the dataBase for only GUI*/
    private ArrayList<Dependence> df;
    private ArrayList<Dependence> proposal;
    /*The name of this dataBase*/
    private Sql sql;
    private String name;

    public CodeToGui(String name)
    {
        Saving.REOPEN(name);
        this.name = name;
        initialize();
    }

    private void initialize()
    {
        sql = new Sql("jdbc:sqlite:");
        sql.connect("misc/Other/"+ name +".db");
    }

    public void start()
    {
        df = new ArrayList<>();
        if(sql.tableExists("FuncDep"))
        {
            sql.refreshDependenciesMap();
            for(String table : sql.getDependenciesMap().keySet())
                df.addAll(sql.getDependenciesMap().get(table));
        }
    }

    public void before()
    {
        sql.createDatabase();
    }

    public void newData(HashMap<String,ArrayList<String>> list)
    {
        for(String table : list.keySet())
        {
            String data = "";
            for(int i = 0 ; i < (list.get(table).size()-1);i++)
                data = data + list.get(table).get(i) + " text, ";
            data = data + list.get(table).get(list.get(table).size()-1) + " text";
            sql.createTable(table,data);
        }

        if (! sql.tableExists("FuncDep"))
            sql.createTable("FuncDep", "table_name text, lhs text, rhs text");
    }

    public void quit()
    {
        sql.close();
        Saving.CLOSE();
    }

    public ArrayList<Dependence> getDependance()
    {
        return df;
    }


    public void add(Dependence dd)
    {
        if(sql.addDependence(dd))
            df.add(dd);
    }

    public HashMap<String,ArrayList<String>> getAttribute()
    {
        HashMap<String,ArrayList<String>> list = new HashMap<>();
        ArrayList<String> under;
        for(String str : getTable(true))
        {
            under = new ArrayList<>();
            for(String what : sql.getTableContentName(str))
                under.add(what);
            list.put(str,under);
        }
        return list;
    }

    public ArrayList<String> getTable(boolean cd)
    {
        ArrayList<String> table = new ArrayList<>();
        ResultSet result = sql.executeQuery("SELECT name FROM sqlite_master where type = 'table' AND name NOT LIKE 'sqlite_%';");
        try
        {
            while (result.next())
            {
                if(result.getString(1).equals("FuncDep") == cd || !result.getString(1).equals("FuncDep"))
                    table.add(result.getString(1));
            }
        }
        catch (Exception e)
        {
            System.err.println(e);
        }

        return table;
    }

    public void remove(Dependence dd)
    {
        if(sql.removeDependence(dd))
            df.remove(dd);
    }

    public ArrayList<String> checkBCNF(String table)
    {
        String[] lt = {"isbcnf",table};
        Command cmd = new IsBCNF(sql,lt);
        cmd.run();
        return cmd.getMemory();
    }

    public ArrayList<String> check3NF(String table)
    {
        String[] lt = {"is3NF",table};
        Command cmd = new Is3NF(sql,lt);
        cmd.run();
        return cmd.getMemory();
    }

    public ArrayList<String> listKey(String table)
    {
        String[] lt = {"decomposition",table};
        Command cmd = new ListKey(sql,lt);
        cmd.run();
        return cmd.getMemory();
    }

    public String toString()
    {
        return name;
    }

}
