package karyon.data;

/**
 * A data connector is an adapter to manipulate data in a specific type
 * of data store.  All data connectors should extend from this class
 */
public abstract class DataConnector
    extends karyon.Object
    implements IDataConnector
{
    @Override
    public final void registerDataObject(Class<? extends DataObject> toDataObjectClass)
    {
        DataManager.getInstance().map(this, toDataObjectClass);
    }

    /*
    private static String DATAMANAGER_VERSION_KEY = "Application.DataStore.Version";




    private Float m_nVersion;
    private boolean m_lInitialised;


    @Override
    public final boolean isInitialised()
    {
        return m_lInitialised;
    }

    @Override
    public final boolean initialise()
    {
        if (!isInitialised() && hasDataManager())
        {
            m_lInitialised = true;
            IDataManager loDataManager = getInstance();
            if(!loDataManager.isDataStoreCreated())
            {
                loDataManager.createDataStore();
            }
            float lnLastVersion = loDataManager.getVersion();
            float lnCurrentVersion = loDataManager.getCodedVersion();

            try
            {
                // Do we need to upgrade/downgrade?
                if (lnCurrentVersion != lnLastVersion)
                {
                    if (lnLastVersion < lnCurrentVersion)
                    {
                        loDataManager.upgrade(lnCurrentVersion);
                    }
                    else
                    {
                        loDataManager.revert(lnCurrentVersion);
                    }
                }
                Application.getInstance().getPropertyManager().setProperty(DATAMANAGER_VERSION_KEY, lnCurrentVersion);
            }
            catch (DataMigrationFailedException ex)
            {
                return false;
            }
        }
        return false;
    }


    @Override
    public final float getVersion()
    {
        if (m_nVersion == null)
        {
            m_nVersion = Application.getInstance().getPropertyManager().getProperty(DATAMANAGER_VERSION_KEY, 0f);
        }
        return m_nVersion;
    }

    @Override
    public abstract float getCodedVersion();

    private void setVersion(float tnVersion)
    {
        Application.getInstance().getPropertyManager().setProperty(DATAMANAGER_VERSION_KEY, tnVersion);
        m_nVersion = tnVersion;
    }

    @Override
    public boolean upgrade(float tnUpgradeTo)
    {
        float lnCurrentVersion = getVersion();
        try
        {
            for (Class<DataMigration> loMigrationClass : getMigrations())
            {
                DataMigration loMigration = null;
                try
                {
                    loMigration = loMigrationClass.newInstance();
                }
                catch (Throwable ex)
                {
                    throw new DataMigrationFailedException(loMigrationClass, ex);
                }
                if (loMigration != null)
                {
                    if (loMigration.getVersion() > lnCurrentVersion &&
                            loMigration.getVersion() <= tnUpgradeTo)
                    {
                        loMigration.upgrade();
                    }
                }
            }
            setVersion(tnUpgradeTo);
        }
        catch (DataMigrationFailedException ex)
        {
            Application.log(ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean revert(float tnRevertTo)
    {
        float lnCurrentVersion = getVersion();
        try
        {
            for (Class<DataMigration> loMigrationClass : getMigrations())
            {
                DataMigration loMigration = null;
                try
                {
                    loMigration = loMigrationClass.newInstance();
                }
                catch (Throwable ex)
                {
                    throw new DataMigrationFailedException(loMigrationClass, ex);
                }
                if (loMigration != null)
                {
                    if (loMigration.getVersion() < lnCurrentVersion &&
                            loMigration.getVersion() >= tnRevertTo)
                    {
                        loMigration.upgrade();
                    }
                }
            }
            setVersion(tnRevertTo);
        }
        catch (DataMigrationFailedException ex)
        {
            Application.log(ex);
            return false;
        }
        return true;
    }

    @Override
    public final <K extends DataMigration> List<Class<K>> getMigrations()
    {
        return new List<Class<K>>(new Class[]{DataSourceVersionMigration.class});
    }

    @Override
    public <K extends DataObject> boolean hasEntity(Class<K> toClass)
    {
        return false;
    }



    @Override
    public abstract boolean isDataStoreCreated();

    @Override
    public abstract boolean createDataStore();

    @Override
    public abstract boolean deleteDataStore();

*/
}
