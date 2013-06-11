package Karyon.Data;

import Karyon.Testing.KaryonTest;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 5/30/13
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataCursorTest
        extends KaryonTest
{
    @Test
    public void testIsEmpty() throws Exception
    {
        startMarker();
        DataCursor<DataSourceVersion> loCursor = new DataCursor<DataSourceVersion>(DataSourceVersion.class, new Object[0]);
        assertTrue(loCursor.isEmpty());

        loCursor = new DataCursor<DataSourceVersion>(DataSourceVersion.class, new Object[]{1,2,3,4,5,6,7,8,9,10});


    }
}
