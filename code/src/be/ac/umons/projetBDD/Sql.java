package be.ac.umons.projetBDD;
/*Compiler en linux avec .jar*/
/*javac -classpath sqlite-jdbc-3.27.2.1.jar BaseDonne.java*/
/*java -classpath ".:sqlite-jdbc-3.27.2.1.jar" BaseDonne*/
/*=>!utilise le jar : sqlite-jdbc-3.27.2.1.jar!<=*/

import be.ac.umons.projetBDD.GUI.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sql
{
    final String url;
    private Connection connection;
    private Map<String, List<Dependence>> dependenciesMap;

    public Sql(String url)
    {
        this.url = url;
        dependenciesMap = new HashMap<>();
    }

    public boolean connect(String fichier)
    {
        try
        {
//            Class.forName("org.sqlite.JDBC"); /*<= permet de dire le fichier jar*/
            connection = DriverManager.getConnection(url + fichier);
            /*Supprimer =>*/ Saving.WRITE("Successfully connected with the database");
            return true;
        }
        catch(Exception e)
        {
            Saving.WRITE("Error : " + e);
            System.err.println("Error : " + e);
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
            Saving.WRITE("Successfully created : " + dmd.getDriverName());
            return true;
        }
        catch(Exception e)
        {
            Saving.WRITE("Error while creating the database !!");
            return false;
        }
    }

    public boolean createTable(String tableName, String tableContent) {
        try {
            Statement stat = connection.createStatement();
            stat.execute(String.format("CREATE TABLE %s(%s)", tableName, tableContent));
            return true;
        } catch(Exception e)
        {
            Saving.WRITE(String.format("An error has been raised when creating the table %s.", tableName));
            return false;
        }
    }

    public boolean insertIntoTable(String tableName, String values)
    {
        try
        {
            String comm = String.format("INSERT INTO %s VALUES(%s)", tableName, values);
            PreparedStatement precmd = connection.prepareStatement(comm);
            precmd.executeUpdate();
            Saving.WRITE(String.format("Successfully inserted (%s) into %s", values, tableName));
            return true;
        }
        catch(Exception e)
        {
            System.err.println(String.format("Error while inserting into %s : %s", tableName, e.getMessage()));
            Saving.WRITE(String.format("Error while inserting into %s : %s", tableName, e.getMessage()));
            return false;
        }
    }

    public boolean removeTuple(String tableName, String condition) {
        try
        {
            String comm = String.format("DELETE FROM %s WHERE %s", tableName, condition);
            PreparedStatement precmd = connection.prepareStatement(comm);
            precmd.executeUpdate();
            Saving.WRITE(String.format("Successfully deleted tuple where %s from %s", condition, tableName));
            return true;
        }
        catch(Exception e)
        {
            System.err.println(String.format("Error while deleting from %s : %s", tableName, e.getMessage()));
            Saving.WRITE(String.format("Error while deleting from %s : %s", tableName, e.getMessage()));
            return false;
        }
    }

    public boolean tableExists(String tableName) {
        try
        {
            String comm = String.format("SELECT name FROM sqlite_master WHERE type='table' AND name='%s';", tableName);
            ResultSet rs = executeQuery(comm);
            rs.next();
            return rs.getRow() == 1;
        }
        catch(Exception e)
        {
            Saving.WRITE(String.format("Error while inserting into %s : %s", tableName, e.getMessage()));
            return false;
        }
    }

    public ResultSet executeQuery(String query) {
        try
        {
            PreparedStatement precmd = connection.prepareStatement(query);
            return precmd.executeQuery();
        }
        catch(Exception e)
        {
            Saving.WRITE(String.format("Error while querying : %s", e.getMessage()));
            return null;
        }

    }

    public void refreshDependenciesMap() {
        ResultSet deps = executeQuery("SELECT * FROM FuncDep;");
        try {
            while (deps.next()) {
                Dependence dep = new Dependence(new DataBase(deps.getString(1)), deps.getString(2), deps.getString(3));
                addIntoDependenciesMap(dep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addIntoDependenciesMap(Dependence dep) {
        if (! dependenciesMap.containsKey(dep.getTableName()))
            dependenciesMap.put(dep.getTableName(), new ArrayList<>());
        dependenciesMap.get(dep.getTableName()).add(dep);
    }

    public boolean addDependence(Dependence dep) {
        String lhs = "";
        for (int i = 0; i < dep.getLhs().size(); i++) {
            lhs += dep.getLhs().get(i);
            if (i != dep.getLhs().size() - 1)
                lhs += ",";
        }
        if (insertIntoTable("FuncDep", String.format("\"%s\", \"%s\", \"%s\"", dep.getTableName(), lhs, dep.getRhs()))) {
            addIntoDependenciesMap(dep);
            return true;
        }
        return false;
    }

    public boolean removeDependence(Dependence dep) {
        String lhs = dep.getLhs().toString();
        if (removeTuple("FuncDep", String.format("table_name='%s' AND lhs='%s' AND rhs='%s'", dep.getTableName(), lhs.substring(1, lhs.length() - 1), dep.getRhs()))) {
            dependenciesMap.get(dep.getTableName()).remove(dep);
            return true;
        }
        return false;
    }

    public boolean close()
    {
        try
        {
            if(connection == null)
                return false;
            connection.close();
            Saving.WRITE("La base de donnée est bien fermée ;-) ");
            return true;
        }
        catch(Exception e)
        {
            Saving.WRITE("Erreur lors de la fermeture de la base de donnée");
            return false;
        }
    }

    public Map<String, List<Dependence>> getDependenciesMap() {
        return dependenciesMap;
    }

    public List<String> getTableContentName(String tableName) {
        String comm = String.format("PRAGMA table_info('%s')", tableName);
        ResultSet rs = executeQuery(comm);
        List<String> names = new ArrayList<>();
        try {
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return names;
    }
}
