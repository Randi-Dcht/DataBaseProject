package be.ac.umons.projetBDD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class allows to define a dependence associate with a table.
 * Every dependence has a lhs who are attributes (list) define rhs.
 * Every dependence has a singular rhs who is a attributes define by lhs.
 * @author Randy Dauchot & Guillaume Cardoen (étudiants en sciences informatique Umons)
 */
public class Dependence implements Serializable
{
    private List<String> lhs;
    private String rhs;
    private String tableName;

    /**
     * This constrictor allows to define a dependence
     * @param tableName who is name of the table for dependence
     * @param lhs who are the attributes who define rhs
     * @param rhs who is an attributes
     */
    public Dependence(String tableName, String lhs, String rhs)// TODO : Check for multi-lhs;
    {
        this.tableName = tableName;
        this.lhs = new ArrayList<>();
        this.lhs.addAll(Arrays.asList(lhs.split(","))); /*the words separate by space*/
        this.rhs = rhs;
    }

    /**
     * This methods return the name of the Table
     * @return name of the table
     */
    public String getTableName()
    {
        return tableName.toString();
    }

    /**
     * This method return a list of lhs
     * @return list of string lhs
     */
    public List<String> getLhs()
    {
        return lhs;
    }

    /**
     * This method return a string of rhs
     * @return rhs who is a string
     */
    public String getRhs() {
        return rhs;
    }
}
