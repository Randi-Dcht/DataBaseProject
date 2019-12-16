package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Sql;
import java.util.ArrayList;

/**
 * This class allows to research the super key and the key of the DataBase with dependency.
 * This method calculated the key with the dependency
 * @author Randy Dauchot (étudiant en sciences informatique Umons)
 */
public class IsKey extends CommandDF
{
    private ArrayList<Dependence> list;

    public IsKey(Sql sql, String[] args)
    {
        super(sql,args);
    }

    public String cheek()
    {
        return null;
    }

    private String simpleKey()
    {
        return null;
    }

    private String hardKey()
    {
        return null;
    }

    @Override
    protected void doAction() {

    }

    @Override
    public String getUsage() {
        return null;
    }
}
