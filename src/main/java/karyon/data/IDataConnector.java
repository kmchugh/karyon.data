package karyon.data;

/**
 * IDataConnectors control the flow of data as well as the algorithms for distributing data
 */
public interface IDataConnector
{
    /**
     * Registers this data object with this connector.  This will imply that any data
     * manipulations of toDataObjectClass types should be directed to this data connector.
     * If the data connector has not been registered with the data manager, then calling
     * this method will register automatically.
     * @param toDataObjectClass the data object type to register
     */
    void registerDataObject(Class<? extends DataObject> toDataObjectClass);

    // TODO: Implement registration for sharded objects and multi source classes
}
