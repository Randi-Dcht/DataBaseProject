package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Sql;

import java.util.ArrayList;

public class Decomposition extends CommandDF
{

    /**
     * This constructor allows define a command for a dependence.
     * @param db   who is the database
     * @param args who is the tab with the arguments of the command
     */
    public Decomposition(Sql db, String[] args)
    {
        super(db, args);
        possibleNumberOfArgs.add(1);
    }

    @Override
    protected void doAction()
    {
        decomposit();
    }

    private void decomposit()
    {
        ArrayList<Dependence> list = new ArrayList<>();
        if(db.tableExists(args[1]))
        {
            System.out.println("Error the table doesn't exist in the dataBase");
            memory.add("Error the table doesn't exist in the dataBase");
            return;
        }
        db.refreshDependenciesMap();
        list.addAll(db.getDependenciesMap().get(args[1]));
        if(list.size()==0)
        {
            System.out.println("There isn't dependence of this base");
            memory.add("There isn't dependence of this base");
            return;
        }
        for(Dependence dp : list)
        {
            String request = " ";
            for(int i = 0; i < dp.getLhs().size()-1;i++)
            {
                request = request + dp.getLhs().get(i) + " text, ";
            }
            request = request + dp.getLhs().get(dp.getLhs().size()-1) + " text";
            db.createTable(dp.getRhs(),request);
            System.out.println("Decomposition : " +  dp.getRhs() + " with " + request);
            memory.add("Decomposition : " +  dp.getRhs() + " with " + request);
        }
    }

    @Override
    public String getUsage()
    {
        return "Decomposition <table>";
    }
}
