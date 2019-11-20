package be.ac.umons.projetBDD;
/*Compiler en linux avec .jar*/
/*javac -classpath sqlite-jdbc-3.27.2.1.jar BaseDonne.java*/
/*java -classpath ".:sqlite-jdbc-3.27.2.1.jar" BaseDonne*/
/*=>!utilise le jar : sqlite-jdbc-3.27.2.1.jar!<=*/

import java.sql.*;

public class Database
{

    final String url;
    private Connection connection;

    public Database(String url)
    {
        this.url = url;
    }

    public boolean connect(String fichier)
    {
        try
        {
            Class.forName("org.sqlite.JDBC"); /*<= permet de dire le fichier jar*/
            connection = DriverManager.getConnection(url + fichier);
            /*Supprimer =>*/ Saving.WRITE("Successfully connected with the database");
            return true;
        }
        catch(Exception e)
        {
            /*Supprimer =>*/ Saving.WRITE("Error : " + e);
            return false;
        }
    }

    public boolean createDatabase()
    {
        if(connection == null)
            return false;
        try
        {
            DatabaseMetaData dmd = connection.getMetaData();
            /*Supprimer =>*/ Saving.WRITE("Successfully created : " + dmd.getDriverName());
            return true;
        }
        catch(Exception e)
        {
            /*Supprimer =>*/ Saving.WRITE("Error while creating the database !!");
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

    public boolean createTable(String tableName, String tableContent) {
        try {
            Statement stat = connection.createStatement();
            stat.execute(String.format("CREATE TABLE %s(%s)", tableName, tableContent));
            return true;
        } catch(Exception e)
        {
            /*Supprimer =>*/ Saving.WRITE("An error has been raised when creating the table.");
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

    public boolean insertIntoTable(String tableName, String values)
    {
        try
        {
            String comm = String.format("INSERT INTO %s VALUES(%s)", tableName, values);
            PreparedStatement precmd = connection.prepareStatement(comm);
            precmd.executeUpdate();
            /*Supprimer =>*/Saving.WRITE(String.format("Successfully inserted (%s) into %s", values, tableName));
            return true;
        }
        catch(Exception e)
        {
            /*Supprimer =>*/Saving.WRITE(String.format("Error while inserting into %s : %s", tableName, e.getMessage()));
            return false;
        }
    }

    public boolean close()
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
