package Karyon.Data;

import Karyon.Exceptions.DataMigrationFailedException;

/**
 * Creates the storage for tracking the Data Source Version
 */
public class DataSourceVersionMigration
    extends DataMigration
{
    public DataSourceVersionMigration()
    {
        super(.1f);
    }

    @Override
    public boolean upgrade() throws DataMigrationFailedException
    {
        return DataManager.getInstance().addEntity(DataSourceVersion.class);
    }

    @Override
    public boolean revert() throws DataMigrationFailedException
    {
        return DataManager.getInstance().removeEntity(DataSourceVersion.class);
    }
}
