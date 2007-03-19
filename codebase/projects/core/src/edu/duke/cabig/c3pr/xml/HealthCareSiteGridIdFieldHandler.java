package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;
import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 19, 2007
 * Time: 12:21:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class HealthCareSiteGridIdFieldHandler implements FieldHandler {


    public Object getValue(Object object) throws IllegalStateException {
        StudyParticipantAssignment registration = (StudyParticipantAssignment) object;
        StudySite studySite = registration.getStudySite();
        if (studySite == null) return null;
        HealthcareSite site = studySite.getSite();
        if (site == null) return null;
        return site.getGridId();
    }

    public void setValue(Object object, Object value) throws IllegalStateException, IllegalArgumentException {
        StudyParticipantAssignment registration = (StudyParticipantAssignment) object;
        StudySite studySite = registration.getStudySite();
        if (studySite == null) {
            studySite = new StudySite();
            registration.setStudySite(studySite);
        }
        HealthcareSite site = studySite.getSite();
        if (site == null) {
            site = new HealthcareSite();
            studySite.setSite(site);
        }
        site.setGridId((String) value);
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
        StudyParticipantAssignment registration = (StudyParticipantAssignment) object;
        registration.getStudySite().getSite().setGridId(null);
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
