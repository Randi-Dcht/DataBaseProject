package be.ac.umons.projetBDD;
/*Compiler en linux avec .jar*/
/*javac -classpath sqlite-jdbc-3.27.2.1.jar BaseDonne.java*/
/*java -classpath ".:sqlite-jdbc-3.27.2.1.jar" BaseDonne*/
/*=>!utilise le jar : sqlite-jdbc-3.27.2.1.jar!<=*/

import java.sql.*;

public class BaseDonne
{

    final String url;
    private Connection connection;

    public BaseDonne(String url)
    {
        this.url = url;
    }

    public boolean connection(String fichier)
    {
        try
        {
            Class.forName("org.sqlite.JDBC"); /*<= permet de dire le fichier jar*/
            connection = DriverManager.getConnection(url + fichier);
            /*Supprimer =>*/ Saving.WRITE("Connection est bien réussite avec la Data Base");
            return true;
        }
        catch(Exception e)
        {
            /*Supprimer =>*/ Saving.WRITE("Erreur : " + e);
            return false;
        }
    }

    public boolean creerBaseDonnee()
    {
        if(connection == null)
            return false;
        try
        {
            DatabaseMetaData dmd = connection.getMetaData();
            /*Supprimer =>*/ Saving.WRITE("La création s'est bien passée : " + dmd.getDriverName());
            return true;
        }
        catch(Exception e)
        {
            /*Supprimer =>*/ Saving.WRITE("Erreur lors de la création de la base de donnée !!");
            return false;
        }
    }

//    public boolean creerTable(String createTable)
//    {
//        try
//        {
//            Statement statement = connection.createStatement();
//            statement.execute(createTable);
//            /*Supprimer =>*/ Saving.WRITE("La table a bien été créée dans la base de données");
//            return true;
//        }
//        catch(Exception e)
//        {
//            /*Supprimer =>*/ Saving.WRITE("Oups une erreur lors de la création table");
//            return false;
//        }
//    }

    public boolean creerTable(String nomTable, String contenuTable) {
        try {
            Statement stat = connection.createStatement();
            stat.execute(String.format("CREATE TABLE %s(%s)", nomTable, contenuTable));
            return true;
        } catch(Exception e)
        {
            /*Supprimer =>*/ Saving.WRITE("Oups une erreur lors de la création table");
            return false;
        }
    }

//    public boolean insererTable(String quoi)
//    {
//        try
//        {
//            PreparedStatement precmd = connection.prepareStatement(quoi);
//            precmd.executeUpdate();
//            /*Supprimer =>*/Saving.WRITE("Insertion dans la table est correctement placée :-)");
//            return true;
//        }
//        catch(Exception e)
//        {
//            /*Supprimer =>*/Saving.WRITE("Erreur lors de l'insertion dans la table : " + e);
//            return false;
//        }
//    }

    public boolean insererTable(String nomTable, String valeurs)
    {
        try
        {
            String comm = String.format("INSERT INTO %s VALUES(%s)", nomTable, valeurs);
            PreparedStatement precmd = connection.prepareStatement(comm);
            precmd.executeUpdate();
            /*Supprimer =>*/Saving.WRITE("Insertion dans la table est correctement placée :-)");
            return true;
        }
        catch(Exception e)
        {
            /*Supprimer =>*/Saving.WRITE("Erreur lors de l'insertion dans la table : " + e);
            return false;
        }
    }

    public boolean terminer()
    {
        try
        {
            if(connection == null)
                return false;
            connection.close();
            /*Supprimer =>*/Saving.WRITE("La base de donnée est bien fermée ;-) ");
            return true;
        }
        catch(Exception e)
        {
            /*Supprimer =>*/ Saving.WRITE("Erreur lors de la fermeture de la base de donnée");
            return false;
        }
    }
}
