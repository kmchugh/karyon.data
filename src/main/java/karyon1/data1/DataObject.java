package karyon1.data1;

import karyon.dynamicCode.Java;
import karyon.exceptions.PropertyNotSetException;

import java.util.Map;

/**
 * A DataObject is intended to represent objects
 * which can be passed back and forth through means
 * such as JSON, binary, or sql statements
 */
public abstract class DataObject
    extends karyon.Object
{
    /**
     * Creates a data object from the data provided in the map specified
     */
    protected DataObject(Map<String, java.lang.Object> toPropertyMap)
        throws PropertyNotSetException
    {
        Java.setValues(this, toPropertyMap);
    }

    protected DataObject()
    {
    }

    // TODO: Specify the type of data cursor that should be used with this object
}
