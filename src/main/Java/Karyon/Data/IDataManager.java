package Karyon.Data;

import Karyon.Collections.List;
import Karyon.Exceptions.DataMigrationFailedException;

import java.util.Map;

/**
 * Public interface for a data manager
 */
public interface IDataManager
{
    /**
     * Gets the version of the data store this data manager is managing
     * @return the db version
     */
    public float getVersion();

    /**
     * Initialises the data manager.
     * @return true if the data manager was initialise successfully
     */
    public boolean initialise();

    /**
     * Checks if the DataManager has been initialised
     * @return true if initialised, false otherwise
     */
    public boolean isInitialised();

    /**
     * Checks if the data store has been created already
     * @return true if created, false if not
     */
    public boolean isDataStoreCreated();

    /**
     * Creates the data store for this DataManager
     * @return true if created, false if not
     */
    public boolean createDataStore();

    /**
     * Deletes and completely removes the data store for this
     * DataManager
     * @return true if created, false if no
     */
    public boolean deleteDataStore();

    /**
     * Upgrades the DataStore from the current version to tnUpdateTo
     * @param tnUpdateTo the last version of the data store recorded
     * @return true if the upgrade was successful
     */
    public boolean upgrade(float tnUpdateTo) throws DataMigrationFailedException;

    /**
     * Reverts the DataStore from current version to tnRevertTo
     * @param tnRevertTo the last version of the data store recorded
     * @return true if the reversion was successful
     */
    public boolean revert(float tnRevertTo) throws DataMigrationFailedException;

    /**
     * Checks if the data store has the specified entity
     * @param toClass the entity class for ORM
     * @param <K> the type of the entity class
     * @return true if the data store can store an entity of type toClass
     */
    public <K extends DataObject> boolean hasEntity(Class<K> toClass);

    /**
     * Hook that occurs before the entity attempts to validate
     * @param toClass the entity that we are saving
     * @param toValues the property value map of values to store
     * @param <K> the type of the property
     * @return true if the beforeSave action was okay and we should proceed with saving
     */
    public <K extends DataObject> boolean onValidate(Class<K> toClass, Map<String, Object> toValues);

    /**
     * Hook that occurs before the entity attempts to save, this occurs after validation
     * @param toClass the entity that we are saving
     * @param toValues the property value map of values to store
     * @param <K> the type of the property
     * @return true if the beforeSave action was okay and we should proceed with saving
     */
    public <K extends DataObject> boolean beforeSave(Class<K> toClass, Map<String, Object> toValues);

    /**
     * Hook that occurs when the entity attempts to save, this occurs after beforeSave and should
     * store the entity to the data store.
     * @param toClass the entity that we are saving
     * @param toValues the property value map of values to store
     * @param <K> the type of the property
     * @return true if the entity was stored to the data store
     */
    public <K extends DataObject> boolean onSave(Class<K> toClass, Map<String, Object> toValues);

    /**
     * Hook that occurs after the entity has saved
     * @param toClass the entity that we are saving
     * @param toValues the property value map of values that were stored
     * @param <K> the type of the property
     * @return true if the afterSave action was okay and we should proceed with saving
     */
    public <K extends DataObject> boolean afterSave(Class<K> toClass, Map<String, Object> toValues);

    /**
     * Attaches this data manager to the application, detaches any
     * existing data manager and returns that detached manager.  If the existing manager
     * is the same as this one then null will be returned, if there is no existing manager
     * then null will be returned
     * @return The detached data manager or null if there was none
     */
    public IDataManager attach();

    /**
     * Detaches this data manager from the application
     */
    public void detach();

    /**
     * Gets the list of migrations for this DataStore
     * @return the list of data migrations
     */
    public <K extends DataMigration> List<Class<K>> getMigrations();

    /**
     * Creates the data entity in the data store
     * @param toEntityType the data entity to create
     * @param <K> the type of the data entity
     * @return true if the entity has been successfully created
     */
    public <K extends DataObject> boolean addEntity(Class<K> toEntityType);

    /**
     * Removes the data entity in the data store
     * @param toEntityType the data entity to remove
     * @param <K> the type of the data entity
     * @return true if the entity has been successfully removed
     */
    public <K extends DataObject> boolean removeEntity(Class<K> toEntityType);

    /**
     * Gets the version that the data store needs to be upgraded/reverted to
     * @return the version of intent
     */
    public float getCodedVersion();

}
