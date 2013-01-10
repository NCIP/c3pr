/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudyVersion;

public class RegistrationSiteFieldHandler implements FieldHandler{
	
	Logger log = Logger.getLogger(RegistrationSiteFieldHandler.class);

	@Deprecated
	public void checkValidity(Object object) throws ValidityException,
			IllegalStateException {
		
	}

	public Object getValue(Object object) throws IllegalStateException {
		StudySubject studySubejct = (StudySubject) object;
		try{
			return studySubejct.getStudySite().getHealthcareSite();
		}catch (Exception ex){
			log.warn("unable to get site code from registration");
			log.warn(ex);
		}
		return null;
	}

	public Object newInstance(Object parent) throws IllegalStateException {
		return null;
	}

	public void resetValue(Object object) throws IllegalStateException,
			IllegalArgumentException {
		// do nothing
	}

	public void setValue(Object object, Object value)
			throws IllegalStateException, IllegalArgumentException {
		StudySubject studySubject =(StudySubject) object;
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
		studySite.setHealthcareSite((HealthcareSite)value);
	}
}
