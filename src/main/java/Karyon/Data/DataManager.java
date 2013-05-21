package Karyon.Data;

import Karyon.*;
import Karyon.Applications.Application;
import Karyon.Collections.List;
import Karyon.DyanmicCode.Java;
import Karyon.Exceptions.DataMigrationFailedException;

import java.lang.Object;
import java.util.Map;

/**
 * The data manager controls data access between that application and data store.
 */
public abstract class DataManager
    extends Karyon.Object
    implements IDataManager
{
    // TODO: Make this IDataManager
    private static IDataManager g_oDataManager;
    private static Boolean g_lHasDataManager;
    private static String DATAMANAGER_VERSION_KEY = "Application.DataStore.Version";

    /**
     * Gets the data manager for this Application.  The call to getInstance will
     * create and initialise the data manager if it did not exist
     * @return the data manager for the application or null if there is no data manager
     */
    public static IDataManager getInstance()
    {
        if (g_oDataManager == null)
        {
            if (g_lHasDataManager == null)
            {
                // TODO: Create the data manager
                //g_oDataManager = Application.getInstance().createDataManager();
                g_lHasDataManager = g_oDataManager != null;
                if (g_oDataManager != null)
                {
                    g_oDataManager.initialise();
                }
            }
        }
        return g_oDataManager;
    }

    /**
     * Checks if this application has a data manager.  If the application
     * is not instantiated at this point it will be for this call.
     * @return true if there is one, false otherwise.
     */
    public static boolean hasDataManager()
    {
        if (g_lHasDataManager == null)
        {
            getInstance();
        }
        return g_lHasDataManager != null && g_lHasDataManager;
    }

    private Float m_nVersion;
    private boolean m_lInitialised;

    @Override
    public final IDataManager attach()
    {
        IDataManager loReturn = g_oDataManager == this ? null : g_oDataManager;
        g_oDataManager = this;
        return loReturn;
    }

    @Override
    public final void detach()
    {
        g_oDataManager = null;
        g_lHasDataManager = null;
    }

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

    /**
     * Sets the Current Version of the data store
     * @param tnVersion the version to set to
     */
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











    public <K extends DataObject> boolean beforeSave(Class<K> toClass, Map<String, Object> toValues)
    {
        return false;
    }

    public <K extends DataObject> boolean afterSave(Class<K> toClass, Map<String, Object> toValues)
    {
        return false;
    }

    public <K extends DataObject> boolean onValidate(Class<K> toClass, Map<String, Object> toValues)
    {
        return false;
    }

    public <K extends DataObject> boolean onSave(Class<K> toClass, Map<String, Object> toValues)
    {
        return false;
    }





    /**
     * Saves the Data Object to the data store through the data manager
     * @param toClass the class to save
     * @param toValues the property value pairs of the values to save
     * @param <K> the type of DataObject
     * @return true if the record was stored successfully
     */
    private <K extends DataObject> boolean save(Class<K> toClass, Map<String, Object> toValues)
    {
        if (hasDataManager())
        {
            if (g_oDataManager.hasEntity(toClass))
            {
                if (g_oDataManager.onValidate(toClass, toValues))
                {
                    if (g_oDataManager.beforeSave(toClass, toValues))
                    {
                        if (g_oDataManager.onSave(toClass, toValues))
                        {
                            g_oDataManager.afterSave(toClass, toValues);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Stores the data object to the data store
     * @param toObject The data object to store
     * @return true if the object was added successfully
     */
    public boolean add(DataObject toObject)
    {
        Utilities.checkParameterNotNull("toObject", toObject);
        return save(toObject.getClass(), Java.getValues(toObject));
    }
}
