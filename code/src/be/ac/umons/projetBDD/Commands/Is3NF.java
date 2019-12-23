package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Sql;

import java.util.ArrayList;

/**
 * This class allows to check if the table follow the schema of 3NF
 * @author Randy Dauchot (etudiant en sciences informatique Umons)
 */
public class Is3NF extends CommandDF
{
    private ArrayList<String> attributes;
    private ArrayList<String> key;

    /**
     * This constructor allows to give a database and arguments of this command
     * @param  db who is a dataBase (sql)
     * @param  args who are the arguments of this commands
     */
    public Is3NF(Sql db, String[] args)
    {
        super(db, args);
    }

    @Override
    protected void doAction()
    {
        attributes = new ArrayList<>(); //TODO raccorder a Sql
        key = new ArrayList<>(); // TODO raccorder isKey
        data3nfEasy();
    }

    private int firstAttribute()
    {
        for(String str : key)
        {
            attributes.remove(str);
        }
        return attributes.size();
    }

    private void data3nfEasy()
    {
        if(/*ajouter ici bcnf*/ 0==1)
        {
            System.out.println("This DataBase is BNCF so it is also 3NF");
            memory.add("This DataBase is BNCF so it is also 3NF");
        }
        else if(firstAttribute() == 0)
        {
            System.out.println("This DataBase is in 3NF because all attributes is first");
            memory.add("This DataBase is in 3NF because all attributes is first");
        }
        else if(data3NF())
        {
            System.out.println("this dataBase is in 3NF");
            memory.add("this dataBase is in 3NF");
        }
        else
        {
            System.out.println("this dataBase isn't in 3NF");
            memory.add("this dataBase isn't in 3NF");
        }

    }

    private boolean data3NF()
    {
        return false;
    }


    @Override
    public String getUsage()
    {
        return "Is3NF <table_name>";
    }
}
