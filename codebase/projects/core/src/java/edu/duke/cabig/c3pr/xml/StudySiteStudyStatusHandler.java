package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 25, 2007
 * Time: 10:35:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class StudySiteStudyStatusHandler implements FieldHandler {


    public Object getValue(Object object) throws IllegalStateException {
        StudySite studySite = (StudySite) object;
        try {
            return studySite.getSiteStudyStatus().toString();
        } catch (Exception e) {
            return "";
        }
    }

    public void setValue(Object object, Object value) throws IllegalStateException, IllegalArgumentException {
    	StudySite studySite = (StudySite) object;
    	studySite.setSiteStudyStatus(SiteStudyStatus.valueOf((String) value));
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
    	StudySite studySite = (StudySite) object;
    	studySite.setSiteStudyStatus(null);
    }

    /**
     * @deprecated
     */
    public void checkValidity(Object object) throws ValidityException, IllegalStateException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object newInstance(Object object) throws IllegalStateException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
