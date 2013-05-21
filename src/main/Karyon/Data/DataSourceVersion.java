package Karyon.Data;

import Karyon.Date;

/**
 * The DataSourceVersion holds information about migrations
 * that have been applied
 */
public class DataSourceVersion
    extends DataObject
{
    private Date m_dInstallDate;
    private String m_cName;
    private float m_nVersion;

    public float getVersion()
    {
        return m_nVersion;
    }

    public void setVersion(float tnVersion)
    {
        m_nVersion = tnVersion;
    }

    public String getName()
    {
        return m_cName;
    }

    public void setName(String tcName)
    {
        m_cName = tcName;
    }

    public Date getInstallDate()
    {
        return m_dInstallDate;
    }

    public void setInstallDate(Date tdInstallDate)
    {
        m_dInstallDate = tdInstallDate;
    }
}
