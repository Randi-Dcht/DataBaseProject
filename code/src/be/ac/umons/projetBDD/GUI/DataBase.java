package be.ac.umons.projetBDD.GUI;

import be.ac.umons.projetBDD.Dependence;

import java.io.Serializable;
import java.util.ArrayList;

public class DataBase implements Serializable
{
    private ArrayList<Dependence> df;
    private String nameSql; /*name with .db*/

    public DataBase(String name)
    {
        nameSql = name;
        df = new ArrayList<Dependence>();
    }

    public String toString()
    {
        return nameSql;
    }

    public ArrayList<Dependence> getDependance()
    {
        return df;
    }

    public void add(Dependence dd)
    {
        df.add(dd);
    }

    public void remove(Dependence dd)
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
