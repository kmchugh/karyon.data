package karyon.data;

import karyon.applications.Application;
import karyon.ISessionManager;
import karyon.Version;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 5/30/13
 * Time: 9:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestDataApplication
    extends Application
{
    public TestDataApplication(String tcName, Version toVersion)
    {
        super(tcName, toVersion);
    }

    public TestDataApplication(Version toVersion)
    {
        super(toVersion);
    }

    @Override
    protected boolean onInit()
    {
        // Set up some data connections
        try
        {
            IDataConnector loConnector = new TestDataConnector();
            loConnector.registerDataObject(DataSourceVersion.class);
        }
        catch (ClassNotFoundException ex)
        {
            Application.log(ex);
            return false;
        }

        return true;
    }

    @Override
    protected boolean onStart()
    {
        return false;
    }
}
