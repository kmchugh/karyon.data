package Karyon.Data;

import Karyon.Applications.Application;

import java.sql.Driver;

/**
 * JDBC is at the heart of Java data.  Because of this, the JDBCConnector is build in to the Data package.
 */
public abstract class JDBCConnector
    extends DataConnector
{
    private Class<? extends Driver> m_oDriverClass;

    protected JDBCConnector(String tcDriverClass)
            throws ClassNotFoundException
    {
        m_oDriverClass = (Class<? extends Driver>)Class.forName(tcDriverClass, true, Application.class.getClassLoader());
    }
}
