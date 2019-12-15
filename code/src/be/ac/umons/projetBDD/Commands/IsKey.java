package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;

import java.util.ArrayList;

/**
 * This class allows to research the super key and the key of the DataBase with dependency.
 * This method calculated the key with the dependency
 */
public class IsKey
{
    private final String dataBase;
    private ArrayList<Dependence> list;

    public IsKey(String nameData)
    {
        dataBase = nameData;
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
}
