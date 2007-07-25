package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySite;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 19, 2007
 * Time: 12:21:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class HealthCareSiteGridIdFieldHandler implements FieldHandler {


    public Object getValue(Object object) throws IllegalStateException {
        StudySubject registration = (StudySubject) object;
        StudySite studySite = registration.getStudySite();
        if (studySite == null) return null;
        HealthcareSite site = studySite.getHealthcareSite();
        if (site == null) return null;
        return site.getGridId();
    }

    public void setValue(Object object, Object value) throws IllegalStateException, IllegalArgumentException {
        StudySubject registration = (StudySubject) object;
        StudySite studySite = registration.getStudySite();
        if (studySite == null) {
            studySite = new StudySite();
            registration.setStudySite(studySite);
        }
        HealthcareSite site = studySite.getHealthcareSite();
        if (site == null) {
            site = new HealthcareSite();
            studySite.setHealthcareSite(site);
        }
        site.setGridId((String) value);
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
        StudySubject registration = (StudySubject) object;
        registration.getStudySite().getHealthcareSite().setGridId(null);
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
