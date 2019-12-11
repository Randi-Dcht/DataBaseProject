package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Sql;

import java.util.*;

public class Is3NF extends Command
{
    public Is3NF(Sql db, String[] args)
    {
        super(db, args);
    }

    @Override
    protected void doAction()
    {
    }


    @Override
    public String getUsage()
    {
        return " ";
    }
}
