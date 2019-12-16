package be.ac.umons.projetBDD;

import org.junit.*;
import static org.junit.Assert.*;

/**
*This class allows the test for the project of DataBase with dependence.
*@author Randy Dauchot & Guillaume Cardoen (étudiants en Sciences informatique Umons)
*/

public class TestJunit
{
  private Sql sql;

  @Before
  public void  launch()
  {
    Saving.REOPEN("../misc/test");
    sql = new Sql("jdbc:sqlite:");
  }

  @After
  public void quit()
  {
    Saving.CLOSE();
  }

  @Test
  public void testSqlConnect()
  {
    assertTrue("Connect to the exist dataBAse",sql.connect("test.db"));
  }

  @Test
  public void testSqlCreate()
  {
    assertFalse("don't connect to dataBase",sql.createDatabase());
    sql.connect("test.db");
    assertTrue("after connect, create dataBase", sql.createDatabase());
  }

  @Test
  public void createTable()
  {
    sql.connect("test.db");
    sql.createDatabase();
    assertTrue(sql.createTable("testJunit","lapin chasseur mort lol"));
  }
}
