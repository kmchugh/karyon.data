package Karyon.Data;

import Karyon.SingletonManager;

/**
 * A DataCursor represents the results of a query to the data source.  The cursor can
 * contain 0 or more records.  If a query contains more records than retrievable by a cursor then
 * the DataCursor will page in and out data as required
 * @param <T> the type of DataObject in this data cursor
 */
public class DataCursor<T extends DataObject>
    extends Karyon.Object
{
    private T m_oDataFlyweight;
    private long m_nPointer;
    private long m_nOffset;
    private long m_nLength;
    private Object[] m_aValues;

    // TODO: Multiple Object arrays for collections larger than INT size
    // TODO: Implement iterable and navigation by pointer
    // TODO: Implement chained operators for filtering, getting modified records, and deleting records

    /**
     * Creates a new instance of the DataCursor
     * @param toClass the class that this cursor is representing
     * @param taValues the values inside this cursor
     */
    public DataCursor(Class<T> toClass, Object[] taValues)
    {
        this(toClass, taValues, 0, taValues.length);
    }

    /**
     * Creates a new instance of the DataCursor
     * @param toClass the class that this cursor is representing
     * @param taValues the values inside this cursor
     * @param tnOffset where the start of this cursor lies in comparison to the entire dataset that would have been retrieved if there were no limits on cursors
     * @param tnCount the count of the entire dataset that would have been retrieved if there were no limits on cursors
     */
    public DataCursor(Class<T> toClass, Object[] taValues, long tnOffset, long tnCount)
    {
        m_oDataFlyweight = SingletonManager.getInstance(toClass);
        m_aValues = taValues;
        m_nPointer = 0;
        m_nOffset = tnOffset;
        m_nLength = tnCount;
    }

    /**
     * Checks if the cursor is empty
     * @return true if there are no records in the cursor
     */
    public boolean isEmpty()
    {
        return m_aValues == null || m_aValues.length == 0;
    }



}
