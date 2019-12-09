package be.ac.umons.projetBDD;

import java.io.Serializable;

public class DateBase implements Serializable
{
    private Dependance[] df;
    private String nameSql; /*name with .db*/

    public DateBase(String name)
    {
        nameSql = name + ".db";
    }
}
