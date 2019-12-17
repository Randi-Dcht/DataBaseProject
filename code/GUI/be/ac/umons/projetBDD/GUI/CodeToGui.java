package be.ac.umons.projetBDD.GUI;

import be.ac.umons.projetBDD.Dependence;
import java.util.ArrayList;

/**
 * This class allows to do bridge between the code of DataBase and Gui application
 * @author Randy Dauchot (étudiant en sciences informatique Umons)
 */
public class CodeToGui
{
    /*the list of dependence to read in the dataBase*/
    private ArrayList<Dependence> df;
    /*The name of this dataBase*/
    private String nameSql; /*Note: name without .db*/

    public CodeToGui(String name)
    {
        nameSql = name;
        initialize();
    }

    private void initialize()
    {

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


    public boolean checkBCNF()
    {
        return true;
    }

    public boolean check3NF(String Table)
    {
        return true;
    }

}
