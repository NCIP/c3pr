package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;
import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

/**
 * To handle the studysite field in StudyParticipantAssignment
 * object
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 18, 2007
 * Time: 7:32:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudyFieldHandler implements FieldHandler {


    public StudyFieldHandler() {
        super();
    }


    public Object getValue(Object object) throws IllegalStateException {
        StudyParticipantAssignment registration = (StudyParticipantAssignment) object;
        StudySite site = registration.getStudySite();
        if (site == null) return null;
        Study study = site.getStudy();
        if (study == null) return null;
        return study;
    }

    public void setValue(Object object, Object value) throws IllegalStateException, IllegalArgumentException {
        StudyParticipantAssignment registration = (StudyParticipantAssignment) object;
        StudySite site = registration.getStudySite();
        if (site == null) {
            site = new StudySite();
            registration.setStudySite(site);
        }
        //if already assigned study
        if(site.getStudy()!=null){
            ((Study)value).setGridId(site.getStudy().getGridId());
        }
        site.setStudy((Study) value);
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
        StudyParticipantAssignment registration = (StudyParticipantAssignment) object;
        registration.getStudySite().setStudy(null);
    }

    public Object newInstance(Object object) throws IllegalStateException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     * @deprecated
     */
    public void checkValidity(Object object) throws ValidityException, IllegalStateException {
        //do nothing
    }
}
