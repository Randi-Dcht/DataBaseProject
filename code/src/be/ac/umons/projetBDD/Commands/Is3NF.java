package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Sql;

/**
 * This class allows to check if the table follow the schema of 3NF
 * @author Randy Dauchot (etudiant en sciences informatique Umons)
 */
public class Is3NF extends CommandDF
{
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
    }

    private boolean data3nf()
    {
        return true;
    }


    @Override
    public String getUsage()
    {
        return " ";
    }
}
