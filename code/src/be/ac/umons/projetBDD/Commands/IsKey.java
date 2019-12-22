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
    private String simpleKey()//2
    {
        ArrayList<String> total = new ArrayList<>(attribut);
        ArrayList<Dependence> check = new ArrayList<>();
        for(Dependence dp: list)
        {
            for(String str : dp.getLhs())
            {
                if(total.contains(str))
                    check.add(dp);
            }
        }

        return null;
    }

    private boolean sizeMax(ArrayList<String> list, Dependence ... dp)
    {
        for(Dependence dpd : dp)
        {
            list.add(dpd.getRhs());
            list.addAll(dpd.getLhs());
        }
        return list.size() == maxAttribute;
    }

    private ArrayList<String> seeKey(Dependence dp,ArrayList<String> under)
    {
        under.addAll(dp.getLhs());
        under.add(dp.getRhs());
        if(under.size() == maxAttribute)
            return under;
        else
            return found(under);
    }

    private ArrayList<String> found(ArrayList<String> array)
    {
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

    private int noRhsAttributes() //1
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
    protected String doAction() //0
    {
        attribut=new ArrayList<>(); //TODO changer avec Sql
        maxAttribute = attribut.size();
        list = new ArrayList<>(); //TODO chaneger avec Sql
        if(noRhsAttributes() != 0)
            return simpleKey();
        return hardKey();

    }

    @Override
    public String getUsage()
    {
        return "<Attribute>";
    }
}
