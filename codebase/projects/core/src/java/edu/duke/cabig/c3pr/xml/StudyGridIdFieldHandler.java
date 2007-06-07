package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;
import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 19, 2007
 * Time: 12:29:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class StudyGridIdFieldHandler implements FieldHandler {


    public Object getValue(Object object) throws IllegalStateException {
        StudyParticipantAssignment registration = (StudyParticipantAssignment) object;
        StudySite studySite = registration.getStudySite();
        if (studySite == null) return null;
        Study study = studySite.getStudy();
        if (study == null) return null;
        return study.getGridId();
    }

    public void setValue(Object object, Object value) throws IllegalStateException, IllegalArgumentException {
       StudyParticipantAssignment registration = (StudyParticipantAssignment) object;
        StudySite studySite = registration.getStudySite();
        if (studySite == null) {
            studySite = new StudySite();
            registration.setStudySite(studySite);
        }
        Study study = studySite.getStudy();
        if (study == null) {
            study = new Study();
            studySite.setStudy(study);
        }
        study.setGridId((String) value);
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
        StudyParticipantAssignment registration = (StudyParticipantAssignment) object;
        registration.getStudySite().getStudy().setGridId(null);
    }

    /**
     * @deprecated
     */
    public void checkValidity(Object object) throws ValidityException, IllegalStateException {

    }

    public Object newInstance(Object object) throws IllegalStateException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
