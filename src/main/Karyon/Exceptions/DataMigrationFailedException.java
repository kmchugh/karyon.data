package Karyon.Exceptions;

import Karyon.Data.DataMigration;

/**
 * Data Migration Exceptions occur when a data store could not be upgraded or downgraded
 */
public class DataMigrationFailedException
        extends Exception
{
    /**
     * Creates a new instance of DataMigrationException
     * @param toMigration the migration that caused the error
     * @param toReason the error that occurred
     */
    public DataMigrationFailedException(DataMigration toMigration, Throwable toReason)
    {
        this(toMigration.getClass(), toReason);
    }

    /**
     * Creates a new instance of DataMigrationException
     * @param toMigrationClass the migration that caused the error
     * @param toReason the error that occurred
     * @param <K> the type of the data migration
     */
    public <K extends DataMigration> DataMigrationFailedException(Class<K> toMigrationClass, Throwable toReason)
    {
        super(toMigrationClass.getName() + " failed.", toReason);
    }
}
