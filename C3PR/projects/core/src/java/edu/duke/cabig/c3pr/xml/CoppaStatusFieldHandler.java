package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.constants.CoppaStatusCodeEnum;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:31:43 AM To change this
 * template use File | Settings | File Templates.
 */
public class CoppaStatusFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
        RemoteHealthcareSite remoteHealthcareSite = (RemoteHealthcareSite) object;
        return remoteHealthcareSite.getCoppaStatusCode()==null?null:remoteHealthcareSite.getCoppaStatusCode().toString();
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
    	if(StringUtils.getBlankIfNull((String)value).equals("")) return;
    	RemoteHealthcareSite remoteHealthcareSite = (RemoteHealthcareSite) object;
    	remoteHealthcareSite.setCoppaStatusCode(CoppaStatusCodeEnum.valueOf((String) value));

    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
    }

    /**
     * @deprecated
     */
    public void checkValidity(Object object) throws ValidityException, IllegalStateException {
        // To change body of implemented methods use File | Settings | File Templates.
    }

    public Object newInstance(Object object) throws IllegalStateException {
        return null; // To change body of implemented methods use File | Settings | File
                        // Templates.
    }
}
