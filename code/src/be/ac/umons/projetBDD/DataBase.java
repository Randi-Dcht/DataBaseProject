package be.ac.umons.projetBDD;

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

        /*
  public void funcDept(String table,String lhs, String rhs)
  {
    String[] lhs2 = lhs.split(" ");
  }

  public boolean BCNF(String Table)
  {
  }

  public boolean 3NF(String Table)
  {
  }

  public void addDependency()
  {
  }

  public String[] removeDependency()
  {
  }*/
}
