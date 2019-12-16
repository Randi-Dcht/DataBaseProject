package be.ac.umons.projetBDD;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *This class allows the test for the project of DataBase with dependence.
 *This test in write with the package Junit 4 and java 8.
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
        sql.close();
    }

    @Test
    public void testSqlConnect()
    {
        assertTrue("Connect to the exist dataBAse",sql.connect("compileTest/testconnect.db"));
        assertTrue("Close the dataBase", sql.close());
    }

    @Test
    public void testSqlCreate()
    {
        assertFalse("don't connect to dataBase",sql.createDatabase());
        sql.connect("compileTest/testcreateD.db");
        assertTrue("after connect, create dataBase", sql.createDatabase());
    }

    @Test
    public void createTable()
    {
        sql.connect("compileTest/testcreateT.db");
        sql.createDatabase();
        assertTrue(sql.createTable("testJunit","Umons student matricule Java Python"));
    }

    @Test
    public void testDependence()
    {
        String test   = "randy,guillaume,basedonnee,umons,est";
        String[] list = test.split(",");
        Dependence dp = new Dependence("test",test,"sql");
        assertEquals("The size of lhs", list.length, dp.getLhs().size());
        for(int i = 0 ; i < list.length ; i++)
            assertEquals("The same word after the cut string", dp.getLhs().get(i),list[i]);
    }
}
