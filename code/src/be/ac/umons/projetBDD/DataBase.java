package be.ac.umons.projetBDD;

import java.io.Serializable;

public class DataBase implements Serializable
{
    private Dependance[] df;
    private String nameSql; /*name with .db*/

    public DataBase(String name)
    {
        nameSql = name + ".db";
    }
}
