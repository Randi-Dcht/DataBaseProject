package be.ac.umons.projetBDD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dependance
{
    private List<String> lhs;
    private String rhs;
    private DataBase tableName;

    public Dependance(DataBase tableName, String lhs, String rhs) { // TODO : Check for multi-lhs;
        this.tableName = tableName;
        this.lhs = new ArrayList<>();
        this.lhs.addAll(Arrays.asList(lhs.split(",")));
        this.rhs = rhs;
    }

    public String getTableName() {
        return tableName.toString();
    }

    public List<String> getLhs() {
        return lhs;
    }

    public String getRhs() {
        return rhs;
    }
}
