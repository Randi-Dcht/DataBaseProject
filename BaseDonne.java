//package be.ac.umons.SGBD;

                 /*Compiler en linux avec .jar*/
/*javac -classpath sqlite-jdbc-3.27.2.1.jar BaseDonne.java*/
/*java -classpath ".:sqlite-jdbc-3.27.2.1.jar" BaseDonne*/
   /*=>!utilise le jar : sqlite-jdbc-3.27.2.1.jar!<=*/

import java.sql.*;

public class BaseDonne
{

  /*>----------------------------Test de cette classe-------------------------*/
  public static void main(String[] args)
  {
    BaseDonne bd = new BaseDonne("jdbc:sqlite:");
    bd.connection("TestRandy.db");
    bd.creerBaseDonnee();
    bd.creerTable("CREATE TABLE etudiant(Matricule integer PRIMARY KEY, Nom text, Faculte text)");
    bd.insererTable("INSERT INTO etudiant(Matricule,Nom,Faculte) VALUES(1234,'rnd','sciences')");
    bd.terminer();
  }
  /*-----------------------------Test de cette classe------------------------<*/

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
      /*Supprimer =>*/ System.out.println("Connection est bien réussite avec la Data Base");
      return true;
    }
    catch(Exception e)
    {
      /*Supprimer =>*/ System.out.println("Erreur : " + e);
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
      /*Supprimer =>*/ System.out.println("La création s'est bien passée : " + dmd.getDriverName());
      return true;
    }
    catch(Exception e)
    {
      /*Supprimer =>*/ System.out.println("Erreur lors de la création de la base de donnée !!");
      return false;
    }
  }

  public boolean creerTable(String createTable)
  {
    try
    {
      Statement statement = connection.createStatement();
      statement.execute(createTable);
      /*Supprimer =>*/ System.out.println("La table a bien été créée dans la base de données");
      return true;
    }
    catch(Exception e)
    {
      /*Supprimer =>*/ System.out.println("Oups une erreur lors de la création table");
      return false;
    }
  }

  public boolean insererTable(String quoi)
  {
    try
    {
      PreparedStatement precmd = connection.prepareStatement(quoi);
      precmd.executeUpdate();
      /*Supprimer =>*/System.out.println("Insertion dans la table est correctement placée :-)");
      return true;
    }
    catch(Exception e)
    {
      /*Supprimer =>*/System.out.println("Erreur lors de l'insertion dans la table : " + e);
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
      /*Supprimer =>*/System.out.println("La base de donnée est bien fermée ;-) ");
      return true;
    }
    catch(Exception e)
    {
      /*Supprimer =>*/ System.out.println("Erreur lors de la fermeture de la base de donnée");
      return false;
    }
  }
}
