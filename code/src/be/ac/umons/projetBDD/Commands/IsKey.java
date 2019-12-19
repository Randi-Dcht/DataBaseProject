package be.ac.umons.projetBDD.Commands;

import be.ac.umons.projetBDD.Dependence;
import be.ac.umons.projetBDD.Sql;
import java.util.ArrayList;

/**
 * This class allows to research the super key and the key of the DataBase with dependency.
 * This method calculated the key with the dependency
 * @author Randy Dauchot (étudiant en sciences informatique Umons)
 */
public class IsKey extends CommandDF
{
    private ArrayList<Dependence> list;
    private ArrayList<String> key;
    private ArrayList<String> attribut;
    private int maxAttribute = 0;

    public IsKey(Sql sql, String[] args)
    {
        super(sql,args);
    }

    public String cheek()
    {
        return null;
    }

    /**
     * This method allows to see the key with an under algorithm of key
     * @return string who is the key of the argument
     */
    private String simpleKey()
    {
        if(noRhsAttributes() == 0)
            return hardKey();
        ArrayList<String> total = new ArrayList<>();
        total.addAll(attribut);
        for(Dependence dp: list)
        {
            seeKey(dp,total);
        }
        return null;
    }

    private String seeKey(Dependence dp,ArrayList<String> under)
    {
        under.addAll(dp.getLhs());
        if(under.size() == maxAttribute)
            return null;
        return null;
    }

    private ArrayList<String> found(ArrayList<String> array)
    {
        Boolean var = true;
        ArrayList<Dependence> dpList = list;
        for(Dependence dp : list)
        {
            for(String str : dp.getLhs())
            {
                if(!array.contains(str))
                    dpList.remove(dp);
            }
        }
        for(Dependence dp : dpList)
        {
            array.addAll(dp.getLhs());array.add(dp.getRhs());
        }

        return array;
    }

    private int noRhsAttributes()
    {
        for(Dependence dp : list)
        {
            attribut.remove(dp.getRhs());
        }
        return attribut.size();
    }

    private String hardKey()
    {
        return null;
    }

    @Override
    protected void doAction()
    {
        key = new ArrayList<String>();
        attribut=new ArrayList<>(); //TODO changer avec Sql
        maxAttribute = attribut.size();
        list = new ArrayList<>(); //TODO chaneger avec Sql
        simpleKey();
    }

    @Override
    public String getUsage() {
        return null;
    }
}
