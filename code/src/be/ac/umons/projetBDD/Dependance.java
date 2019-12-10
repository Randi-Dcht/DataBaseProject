package be.ac.umons.projetBDD;
import java.sql.*;

public class Dependance
{
    private String lhs;
    private String   rhs;
    private DataBase tableName;

    public Dependance(DataBase tableName, String lhs, String rhs) { // TODO : Check for multi-lhs;
        this.tableName = tableName;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public String getTableName() {
        return tableName.toString();
    }

    public String getLhs() {
        return lhs;
    }

    public String getRhs() {
        return rhs;
    }
}
