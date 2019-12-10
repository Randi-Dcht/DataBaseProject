package be.ac.umons.projetBDD.GUI;

import be.ac.umons.projetBDD.Dependance;

import java.io.Serializable;
import java.util.ArrayList;

public class DataBase implements Serializable
{
    private ArrayList<Dependance> df;
    private String nameSql; /*name with .db*/

    public DataBase(String name)
    {
        nameSql = name;
        df = new ArrayList<Dependance>();
    }

    public String toString()
    {
        return nameSql;
    }

    public ArrayList<Dependance> getDependance()
    {
        return df;
    }

    public void add(Dependance dd)
    {
        df.add(dd);
    }

    public void remove(Dependance dd)
    {
        df.remove(dd);
    }

        /*
  public boolean BCNF(String Table)
  {
  }

  public boolean 3NF(String Table)
  {
  }
*/
}
