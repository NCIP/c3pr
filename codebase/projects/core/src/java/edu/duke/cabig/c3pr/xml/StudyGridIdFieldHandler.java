/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Mar 19, 2007 Time: 12:29:31 AM To change this
 * template use File | Settings | File Templates.
 */
public class StudyGridIdFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
        StudySubject registration = (StudySubject) object;
        StudySite studySite = registration.getStudySite();
        if (studySite == null) return null;
        Study study = studySite.getStudy();
        if (study == null) return null;
        return study.getGridId();
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
        StudySubject registration = (StudySubject) object;
        StudySite studySite = registration.getStudySite();
        if (studySite == null) {
            studySite = new StudySite();
            registration.setStudySite(studySite);
        }
        Study study = studySite.getStudy();
        if (study == null) {
            study = new LocalStudy();
            studySite.setStudy(study);
        }
        study.setGridId((String) value);
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
        StudySubject registration = (StudySubject) object;
        registration.getStudySite().getStudy().setGridId(null);
    }

    /**
     * @deprecated
     */
    public void checkValidity(Object object) throws ValidityException, IllegalStateException {

    }

    public Object newInstance(Object object) throws IllegalStateException {
        return null; // To change body of implemented methods use File | Settings | File
                        // Templates.
    }
}
