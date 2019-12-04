package be.ac.umons.projetBDD;

import java.util.ArrayList;
import java.util.Arrays;

public class MethodRandy
{
    public class Dependance
    {
        public static final String what = " "; /*<= permet de dire comment sont séparer les éléments*/
        private String[] lhs;
        private String   rhs;
        private String nameTable;

        public Dependance(String nameTable,String left,String right)
        {
            this.nameTable = nameTable;
            rhs = right;
            lhs = analyse(left);
        }
        private String[] analyse(String words)
        {
            return words.split(what);
        }
        public String[] getLeft()
        {
            return lhs;
        }
        public String getRigth()
        {
            return rhs;
        }
        private String getTable()
        {
            return nameTable;
        }

        @Override
        public String toString()
        {
            String add = lhs[0];
            for(int i=1;i<lhs.length;i++)
            {
                add = add + " , " + lhs[i];
            }
            return add + " --> " + rhs;
        }
    }

    private String[] attributs;
    private Dependance[] liste;
    private String[] key;

    public void addAttributs(String ... attri)
    {
        attributs = attri; //TODO: ajouter à ceux présent (à voir)
    }

    public void giveDependance(Dependance ... dpd)
    {
        liste = dpd;
    }

    public void keySimpleAlgorithm()
    {
        ArrayList<String> lt = new ArrayList<String>(Arrays.asList(attributs));
        for(Dependance dp : liste)
        {
            lt.remove(dp.getRigth());
        }
        //TODO: continuer ici (04-12)!
    }

    public void KeySimpleAlgorithm()
    {}
}

