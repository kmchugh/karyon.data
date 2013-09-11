package karyon1.data1;

import karyon1.exceptions1.DataMigrationFailedException;

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
        //return DataManager.getInstance().addEntity(DataSourceVersion.class);
        return false;
    }

    @Override
    public boolean revert() throws DataMigrationFailedException
    {
        //return DataManager.getInstance().removeEntity(DataSourceVersion.class);
        return false;
    }
}
