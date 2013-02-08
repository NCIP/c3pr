/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:31:43 AM To change this
 * template use File | Settings | File Templates.
 */
public class IdentifiersHolderFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
        if (object instanceof StudyVersion) {
			StudyVersion studyVersion = (StudyVersion) object;
			return new IdentifiersHolder(studyVersion.getStudy().getIdentifiers());
		}else if (object instanceof StudySubject) {
			StudySubject studySubject = (StudySubject) object;
			return new IdentifiersHolder(studySubject.getStudySite().getStudy().getIdentifiers());
		}
        throw new C3PRBaseRuntimeException("Illegal object instance.");
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
    	if(value==null) return;
    	IdentifiersHolder identifiersHolder= (IdentifiersHolder) value;
    	Study study= new LocalStudy();
    	study.getIdentifiers().addAll(identifiersHolder.getIdentifiers());
    	if (object instanceof StudyVersion) {
			StudyVersion studyVersion = (StudyVersion) object;
			studyVersion.setStudy(study);
		}else if (object instanceof StudySubject) {
			StudySubject studySubject = (StudySubject) object;
			StudySite studySite = null;
			if(studySubject.getStudySubjectStudyVersion().getStudySiteStudyVersion()==null){
				studySite = new StudySite();
				StudySiteStudyVersion studySiteStudyVersion= new StudySiteStudyVersion();
				studySiteStudyVersion.setStudySite(studySite);
				studySubject.getStudySubjectStudyVersion().setStudySiteStudyVersion(studySiteStudyVersion);			
			}else if(studySubject.getStudySubjectStudyVersion().getStudySiteStudyVersion().getStudySite()==null){
				studySite = new StudySite();
				studySubject.getStudySubjectStudyVersion().getStudySiteStudyVersion().setStudySite(studySite);
			}else{
				studySite= studySubject.getStudySubjectStudyVersion().getStudySiteStudyVersion().getStudySite();
			}
			studySite.setStudy(study);
		}else{
			throw new C3PRBaseRuntimeException("Illegal object instance.");
		}
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
        return new IdentifiersHolder();
    }
}
