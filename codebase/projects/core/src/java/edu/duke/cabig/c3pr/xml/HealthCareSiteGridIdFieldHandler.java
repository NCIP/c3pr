/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Mar 19, 2007 Time: 12:21:07 AM To change this
 * template use File | Settings | File Templates.
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

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
        StudySubject registration = (StudySubject) object;
        StudySite studySite = registration.getStudySite();
        if (studySite == null) {
            studySite = new StudySite();
            registration.setStudySite(studySite);
        }
        HealthcareSite site = studySite.getHealthcareSite();
        if (site == null) {
            site = new LocalHealthcareSite();
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
        // To change body of implemented methods use File | Settings | File Templates.
    }

    public Object newInstance(Object object) throws IllegalStateException {
        return null; // To change body of implemented methods use File | Settings | File
                        // Templates.
    }
}
