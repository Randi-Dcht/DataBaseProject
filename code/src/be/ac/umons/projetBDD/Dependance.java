package be.ac.umons.projetBDD;
import java.sql.*;

public class Dependance
{
    private String lhs;
    private String   rhs;
    private String tableName;

    public Dependance(String tableName, String lhs, String rhs) { // TODO : Check for multi-lhs;
        this.tableName = tableName;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public String getTableName() {
        return tableName;
    }

    public String getLhs() {
        return lhs;
    }

    public String getRhs() {
        return rhs;
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
