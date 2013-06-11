package Karyon.Data;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 5/29/13
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestDataConnector
    extends JDBCConnector
{
    public TestDataConnector()
            throws ClassNotFoundException
    {
        super("org.apache.derby.jdbc.EmbeddedDriver");
    }
}
