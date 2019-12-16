package be.ac.umons.projetBDD;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class allows the connect, read and write in the dataBase.
 * This class use the sqlLite dataBase.
 * @author : Randy Dauchot & Guillaume Cardoen (étudiants en sciences informatique Umons)
 */
public class Sql
{
    final String url;
    private Connection connection;
    private Map<String, List<Dependence>> dependenciesMap;

    /**
     * This contructor allows create a link with dataBase
     * @param url is the string with the road to the dataFile
     */
    public Sql(String url)
    {
        this.url = url;
        dependenciesMap = new HashMap<>();
    }

    /**
     * This method allows to connect to the dataBase SqlLite
     * @param file who is the name of dataBase (.db)
     * @return true if the connect is succesful and false
     */
    public boolean connect(String file)
    {
        try
        {
//            Class.forName("org.sqlite.JDBC"); /*<= permet de dire le fichier jar*/
            connection = DriverManager.getConnection(url + file);
            Saving.WRITE("Successfully connected with the database : " + file);
            return true;
        }
        catch(Exception e)
        {
            Saving.WRITE("Error : " + e);
            System.err.println("Error : " + e);
            return false;
        }
    }

    /**
     * This method allows to create a dataBase in the file
     * @return true if the dataBase is create and false
     */
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
            System.out.println(e);
            return false;
        }
    }

    /**
     * This method allows create a table in the dataBase with name of column
     * @param tableName who is the name of table
     * @param tableContent who is the name of the column
     * @return true if the table is create and false
     */
    public boolean createTable(String tableName, String tableContent) {
        try {
            Statement stat = connection.createStatement();
            stat.execute(String.format("CREATE TABLE %s(%s)", tableName, tableContent));
            Saving.WRITE("The Table of " + tableName + "create with : " + tableContent);
            return true;
        } catch(Exception e)
        {
            Saving.WRITE(String.format("An error has been raised when creating the table %s.", tableName));
            return false;
        }
    }

    /**
     * This method allows to insert in the specific table the value
     * @param tableName who is the name of the table in the data file
     * @param values who is the value to insert in the table
     */
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

    /**
     * This method allows to delete the value in the specific table
     * @param tableName who is the name of the table in the file
     * @param condition allows specify the value who is deleted in the table
     * @return true is the value is delete and false
     */
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

    /**
     * This method allows the see if the table exist in the dataBase
     * @param tableName who is the name of the table
     * @return true if the table exist and false
     */
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

    /**
     * This method allows the give a SQL request in the dataBAse
     * @param query is a request
     * @return result (ResultSet) of the request
     */
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
                Dependence dep = new Dependence(deps.getString(1), deps.getString(2), deps.getString(3));
                addIntoDependenciesMap(dep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method allows to add in map the couple name of Table and the dependence in the table
     * @param dep who is a dependence
     */
    public void addIntoDependenciesMap(Dependence dep) {
        if (! dependenciesMap.containsKey(dep.getTableName()))
            dependenciesMap.put(dep.getTableName(), new ArrayList<>());
        dependenciesMap.get(dep.getTableName()).add(dep);
    }

    /**
     * This method allows to put the new dependence in the dataBase and also in the map.
     * @param dep who is the dependence to add
     * @return true if the dependence is correctly add to table and false
     */
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

    /**
     * This method allow to delete a dependence in the dataBase and in map
     * @param dep who is the dependence who is remove
     * @return true if the dependence is remove and false
     */
    public boolean removeDependence(Dependence dep) {
        String lhs = dep.getLhs().toString();
        if (removeTuple("FuncDep", String.format("table_name='%s' AND lhs='%s' AND rhs='%s'", dep.getTableName(), lhs.substring(1, lhs.length() - 1), dep.getRhs()))) {
            dependenciesMap.get(dep.getTableName()).remove(dep);
            return true;
        }
        return false;
    }

    /**
     * This method allows to quit the dataBase correctly.
     * @return true if the dataBAse is correctly close and false
     */
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

    /**
     * this method allows to return the list of the dependence
     * @return map of table and dependence
     */
    public Map<String, List<Dependence>> getDependenciesMap() {
        return dependenciesMap;
    }

    /**
     * This method allows
     * @param tableName who is the name of the table in the dataBAse
     * @return list of the name [...]
     */
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
