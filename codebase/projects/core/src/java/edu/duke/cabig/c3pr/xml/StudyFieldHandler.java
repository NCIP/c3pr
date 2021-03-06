/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * To handle the studysite field in StudySubject object <p/> Created by IntelliJ IDEA. User: kherm
 * Date: Mar 18, 2007 Time: 7:32:20 PM To change this template use File | Settings | File Templates.
 */
public class StudyFieldHandler implements FieldHandler {

    public StudyFieldHandler() {
        super();
    }

    public Object getValue(Object object) throws IllegalStateException {
        StudySubject registration = (StudySubject) object;
        StudySite site = registration.getStudySite();
        if (site == null) return null;
        Study study = site.getStudy();
        if (study == null) return null;
        return study;
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
        StudySubject registration = (StudySubject) object;
        StudySite site = registration.getStudySite();
        if (site == null) {
            site = new StudySite();
            registration.setStudySite(site);
        }
        // if already assigned study
        if (site.getStudy() != null) {
            ((Study) value).setGridId(site.getStudy().getGridId());
        }
        site.setStudy((Study) value);
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
        StudySubject registration = (StudySubject) object;
        registration.getStudySite().setStudy(null);
    }

    public Object newInstance(Object object) throws IllegalStateException {
        return null; // To change body of implemented methods use File | Settings | File
                        // Templates.
    }

    /**
     * @deprecated
     */
    public void checkValidity(Object object) throws ValidityException, IllegalStateException {
        // do nothing
    }
}
