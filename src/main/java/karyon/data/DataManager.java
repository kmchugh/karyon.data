package karyon.data;

import karyon.collections.HashMap;
import karyon.collections.List;
import karyon.Utilities;

/**
 * The DataManager controls all access to data and data operations
 */
public class DataManager
    extends karyon.Object
{
    private static DataManager g_oDataManager;

    /**
     * Gets the data manager for this Application
     * @return the data manager for the application
     */
    public static DataManager getInstance()
    {
        if (g_oDataManager == null)
        {
            g_oDataManager = new DataManager();
        }
        return g_oDataManager;
    }

    private HashMap<String, IDataConnector> m_oConnectors;
    private IDataConnector m_oDefault;
    private HashMap<IDataConnector, List<Class<? extends DataObject>>> m_oEntityMap;

    /**
     * Not publicly creatable
     */
    private DataManager()
    {
    }

    /**
     * Adds the specified connector to the DataManager.
     * @param tcKey the key for this connector
     * @param toConnector the connector to add
     */
    public final void registerConnector(String tcKey, IDataConnector toConnector)
    {
        if (m_oConnectors == null)
        {
            m_oConnectors = new HashMap<String, IDataConnector>();
        }
        m_oConnectors.put(tcKey.toLowerCase(), toConnector);

        // If there is no default connector, make this the default
        if (m_oDefault == null)
        {
            m_oDefault = toConnector;
        }
    }

    /**
     * Gets the specified data connector
     * @param tcKey the key for the data connector
     * @return the data connector or null if there is no matching connector
     */
    protected final IDataConnector getConnector(String tcKey)
    {
        return m_oConnectors != null ? m_oConnectors.get(tcKey.toLowerCase()) : null;
    }

    /**
     * Removes the connector specified
     * @param tcKey the key of the connector to remove
     * @return true if the connector has been removed, false if nothing has been removed
     */
    public final boolean unregisterConnector(String tcKey)
    {
        IDataConnector loConnector = m_oConnectors != null ? m_oConnectors.remove(tcKey.toLowerCase()) : null;
        // Also need to remove from entity mapping
        if (loConnector != null && m_oEntityMap != null)
        {
            m_oEntityMap.remove(loConnector);
        }
        return loConnector != null;
    }

    /**
     * Checks if this data connector is registered, and if it is returns the key
     * it is registered under
     * @param toConnector the connector to check
     * @return the key, or null if not registered
     */
    public final String isRegistered(IDataConnector toConnector)
    {
        if (m_oConnectors != null)
        {
            for (String lcKey : m_oConnectors.keySet())
            {
                if (m_oConnectors.get(lcKey) == toConnector)
                {
                    return lcKey;
                }
            }
        }
        return null;
    }

    /**
     * Marks the specified connector as the default connector.  The default connector
     * is the connector that will be used for data objects that are not specifically mapped.
     * If the specified connector does not exist then there is no change to the current default
     * @param tcKey the key of the connector to mark
     * @return true if the default connector changed, false otherwise
     */
    public final boolean markDefault(String tcKey)
    {
        return m_oConnectors != null && markDefault(m_oConnectors.get(tcKey.toLowerCase()));
    }

    /**
     * Marks the specified connector as the default connector.  This connector can only be set
     * as the default connector if it has previously been added to the manager using addConnector
     * @param toDefault the default connector
     * @return true if the default connector changed, false otherwise
     */
    public final boolean markDefault(IDataConnector toDefault)
    {
        if (toDefault != null && m_oConnectors != null && m_oConnectors.values().contains(toDefault))
        {
            m_oDefault = toDefault;
            return true;
        }
        return false;
    }

    /**
     * Gets the default connector, if no connector has been set as default this will
     * return null
     * @return the default connector
     */
    public final IDataConnector getDefault()
    {
        return m_oDefault;
    }


    // TODO: Implement registration for sharded objects and multi source classes
    /**
     * Directly maps the specified data object to the data connector.  This will forward any data manipulation
     * actions to the data connector
     * @param toConnector the connector that will handle data operations for the data object type
     * @param toDataObjectClass the type of data object being registered
     * @return true if the map is changed as a result of this call
     */
    public final boolean map(IDataConnector toConnector, Class<? extends DataObject> toDataObjectClass)
    {
        // Make sure the connector is registered
        if (m_oConnectors == null || !m_oConnectors.values().contains(toConnector))
        {
            registerConnector(Utilities.generateGUID(), toConnector);
        }

        if (m_oEntityMap == null)
        {
            m_oEntityMap = new HashMap<IDataConnector, List<Class<? extends DataObject>>>();
        }

        if (!m_oEntityMap.containsKey(toConnector))
        {
            m_oEntityMap.put(toConnector, new List<Class<? extends DataObject>>());
        }

        if (!m_oEntityMap.get(toConnector).contains(toDataObjectClass))
        {
            m_oEntityMap.get(toConnector).add(toDataObjectClass);
            return true;
        }
        return false;
    }

    /**
     * Gets all of the objects of type K from the data source
     * @param toClass the class to get the objects from
     * @param <K> the type of Data Object to retrieve
     * @return a cursor with the data objects, if no data objects were found, the cursor will be empty
     */
    public final <K extends DataObject> DataCursor<K> get(Class<K> toClass)
    {
        return null;
    }


/*













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

    public boolean add(DataObject toObject)
    {
        Utilities.checkParameterNotNull("toObject", toObject);
        return save(toObject.getClass(), Java.getValues(toObject));
    }
    */
}
