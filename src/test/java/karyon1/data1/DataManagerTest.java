package karyon1.data1;

import karyon.applications.Application;
import karyon.Date;
import karyon.testing.KaryonTest;
import karyon.Version;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 5/29/13
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataManagerTest
        extends KaryonTest
{
    @BeforeClass
    public static void setUp()
    {
        if (Application.isCreated())
        {
            Application.clearApplication();
        }
        new TestDataApplication(new Version("TestDataApplication", 0, 0, 0, 0, new Date()));
        Application.getInstance().start();
    }

    @AfterClass
    public static void tearDown()
    {
        if (Application.isCreated())
        {
            Application.clearApplication();
        }
    }
    
    @Test
    public void testGetInstance() throws Exception
    {
        startMarker();
        DataManager loManager = DataManager.getInstance();
        assertSame(loManager, DataManager.getInstance());
    }

    @Test
    public void testRegisterConnector() throws Exception
    {
        startMarker();
        DataManager loManager = DataManager.getInstance();
        DataConnector loConnector = new TestDataConnector();
        loManager.registerConnector("test", loConnector);
        assertSame(loConnector, loManager.getConnector("test"));
    }

    @Test
    public void testGetConnector() throws Exception
    {
        startMarker();
        DataManager loManager = DataManager.getInstance();
        DataConnector loConnector = new TestDataConnector();
        loManager.registerConnector("test2", loConnector);
        assertSame(loConnector, loManager.getConnector("test2"));

    }

    @Test
    public void testUnregisterConnector() throws Exception
    {
        startMarker();
        DataManager loManager = DataManager.getInstance();
        DataConnector loConnector = new TestDataConnector();
        loManager.registerConnector("test3", loConnector);
        assertSame(loConnector, loManager.getConnector("test3"));

        loManager.unregisterConnector("test3");
        assertNull(loManager.getConnector("test3"));
    }

    @Test
    public void testMarkDefault() throws Exception
    {
        startMarker();
        DataManager loManager = DataManager.getInstance();
        DataConnector loConnector = new TestDataConnector();

        IDataConnector loDefault = loManager.getDefault();

        assertFalse(loManager.markDefault("test4"));

        loManager.registerConnector("test4", loConnector);
        assertSame(loConnector, loManager.getConnector("test4"));

        assertTrue(loManager.markDefault("test4"));

        assertTrue(loManager.markDefault(loDefault));

        assertSame(loManager.getDefault(), loDefault);
    }

    @Test
    public void testGetDefault() throws Exception
    {
        startMarker();
        DataManager loManager = DataManager.getInstance();
        DataConnector loConnector = new TestDataConnector();

        assertFalse(loManager.markDefault("test5"));

        loManager.registerConnector("test5", loConnector);
        assertSame(loConnector, loManager.getConnector("test5"));

        assertTrue(loManager.markDefault("test5"));

        assertSame(loManager.getDefault(), loConnector);
    }

    @Test
    public void testIsRegistered() throws Exception
    {
        startMarker();
        DataManager loManager = DataManager.getInstance();
        DataConnector loConnector = new TestDataConnector();
        loManager.registerConnector("test6", loConnector);
        assertSame(loConnector, loManager.getConnector("test6"));

        DataConnector loConnector1 = new TestDataConnector();
        assertEquals("test6", loManager.isRegistered(loConnector));
        assertNull(loManager.isRegistered(loConnector1));
    }

    @Test
    public void testMap() throws Exception
    {
        startMarker();
        DataManager loManager = DataManager.getInstance();
        DataConnector loConnector = new TestDataConnector();

        assertTrue(loManager.map(loConnector, DataSourceVersion.class));
        assertFalse(loManager.map(loConnector, DataSourceVersion.class));

        String lcKey = loManager.isRegistered(loConnector);
        assertSame(loConnector, loManager.getConnector(lcKey));
    }

}
