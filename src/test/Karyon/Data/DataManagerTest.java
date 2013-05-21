package Karyon.Data;

import Karyon.*;
import Karyon.Applications.Application;
import Karyon.Applications.IPropertyManager;
import Karyon.Collections.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataManagerTest
{
    public class FakeDataObject
        extends DataObject
    {
    }

    public class DataApplication
        extends Application
    {
        public DataApplication()
        {
            super(new Version("Data Test Application", 0,0,0,0,new Date()));
        }

        @Override
        public IDataManager createDataManager()
        {
            return null;
        }

        @Override
        protected boolean onInit()
        {
            return true;
        }

        @Override
        protected boolean onStart()
        {
            return true;
        }

        @Override
        public ISessionManager createSessionManager()
        {
            return new SessionManager();
        }

        @Override
        protected IPropertyManager createPropertyManager()
        {
            return null;
        }
    }

    @Before
    public void beforeTest()
    {
        TestApplication.getInstance();
    }

    @Test
    public void testGetInstance() throws Exception
    {
        IDataManager loDataManger = DataManager.getInstance();
        assertSame(loDataManger, DataManager.getInstance());
    }

    @Test
    public void testDetach() throws Exception
    {
        IDataManager loDataManger = DataManager.getInstance();
        loDataManger.detach();
        assertNotSame(loDataManger, DataManager.getInstance());
    }

    @Test
    public void testAttach() throws Exception
    {
        IDataManager loManager = new TestApplication.TestDataManager();
        IDataManager loOldManager = loManager.attach();
        assertSame(loManager, DataManager.getInstance());
        if (loOldManager != null)
        {
            loOldManager.attach();
            assertSame(loOldManager, DataManager.getInstance());
        }
    }

    @Test
    public void testHasDataManager() throws Exception
    {
        assertTrue(DataManager.hasDataManager());

        Application.clearApplication();
        Application loApp = new DataApplication();

        Application.clearApplication();
        TestApplication.getInstance();
        assertTrue(DataManager.hasDataManager());
    }

    @Test
    public void testInitialise() throws Exception
    {
        // Basic test, as only the instances will create a data store
        IDataManager loManager = DataManager.getInstance();
        if (!loManager.isInitialised())
        {
            loManager.initialise();
        }
        assertTrue(loManager.isInitialised());
    }

    @Test
    public void testGetMigrations() throws Exception
    {
        IDataManager loManager = DataManager.getInstance();
        List<Class<DataMigration>> loList = loManager.getMigrations();
        assertTrue(loList.size() > 0);
    }

    @Test
    public void testUpgrade() throws Exception
    {
        IDataManager loManager = DataManager.getInstance();
        loManager.revert(0);
        assertEquals(Float.floatToIntBits(0f), Float.floatToIntBits(loManager.getVersion()));

        loManager.upgrade(1);
        assertEquals(Float.floatToIntBits(1f), Float.floatToIntBits(loManager.getVersion()));
    }

    @Test
    public void testRevert() throws Exception
    {
        IDataManager loManager = DataManager.getInstance();
        loManager.revert(0);
        assertEquals(Float.floatToIntBits(0f), Float.floatToIntBits(loManager.getVersion()));

        loManager.upgrade(1);
        assertEquals(Float.floatToIntBits(1f), Float.floatToIntBits(loManager.getVersion()));
    }

    @Test
    public void testHasEntity() throws Exception
    {
        DataManager loManager = new DataManager() {
            @Override

            public float getCodedVersion()
            {
                return 0;
            }

            @Override
            public boolean isDataStoreCreated()
            {
                return false;
            }

            @Override
            public boolean createDataStore()
            {
                return false;
            }

            @Override
            public boolean deleteDataStore()
            {
                return false;
            }

            @Override
            public <K extends DataObject> boolean hasEntity(Class<K> toClass)
            {
                return toClass.equals(DataSourceVersion.class);
            }

            @Override
            public <K extends DataObject> boolean addEntity(Class<K> toEntityType)
            {
                return false;
            }

            @Override
            public <K extends DataObject> boolean removeEntity(Class<K> toEntityType)
            {
                return false;
            }
        };

        IDataManager loTmpManager = DataManager.getInstance();
        loManager.attach();

        assertFalse(loManager.hasEntity(FakeDataObject.class));
        assertTrue(loManager.hasEntity(DataSourceVersion.class));

        loTmpManager.attach();
    }

    // The following tests are empty because they are implemented in the

    @Test
    public void testGetVersion() throws Exception
    {
    }

    @Test
    public void testCreateDataStore() throws Exception
    {
    }

    @Test
    public void testDeleteDataStore() throws Exception
    {
    }

    @Test
    public void testBeforeSave() throws Exception
    {

    }

    @Test
    public void testAfterSave() throws Exception
    {

    }

    @Test
    public void testValidate() throws Exception
    {

    }

    @Test
    public void testOnSave() throws Exception
    {

    }

    @Test
    public void testAdd() throws Exception
    {

    }
}
