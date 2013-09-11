package karyon1.data1;

import karyon1.exceptions1.DataMigrationFailedException;

/**
 * A Data Migration allows upgrading or downgrading of a database
 */
public abstract class DataMigration
    extends karyon.Object
{
    private float m_nVersion;

    /**
     * Creates a new instance of data migration
     * @param tnVersion the version of the database that this
     *                  data migration is for
     */
    public DataMigration(float tnVersion)
    {
        m_nVersion = tnVersion;
    }

    /**
     * Gets the version of the database this data migration is
     * intended for
     * @return the db version
     */
    public float getVersion()
    {
        return m_nVersion;
    }

    /**
     * Modifies the data store to bring it up to date with this
     * data migration
     * @return true if the migration was successful
     */
    public abstract boolean upgrade() throws DataMigrationFailedException;

    /**
     * Modifies the data store to revert this data migration
     * @return true if the migration was successfully reverted
     */
    public abstract boolean revert() throws DataMigrationFailedException;
}
