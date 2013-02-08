/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudyVersion;

public class RegistrationStudyVersionFieldHandler implements FieldHandler{
	
	private Logger log = Logger.getLogger(RegistrationStudyVersionFieldHandler.class);

	@Deprecated
	public void checkValidity(Object object) throws ValidityException,
			IllegalStateException {
		
	}

	public Object getValue(Object object) throws IllegalStateException {
		StudySubject studySubject =(StudySubject) object;
		try{
			return studySubject.getStudySubjectStudyVersion().getStudySiteStudyVersion().getStudyVersion().getName();
		}catch (Exception ex){
			log.warn("unable to get value of study version from registration");
			log.warn(ex);
		}
		
		return null;
	}

	public Object newInstance(Object parent) throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	public void resetValue(Object object) throws IllegalStateException,
			IllegalArgumentException {
		// do nothing
	}

	public void setValue(Object object, Object value)
			throws IllegalStateException, IllegalArgumentException {
		StudySubject studySubject =(StudySubject) object;
		StudyVersion studyVersion = new StudyVersion();
		studyVersion.setName((String)value);
		if(studySubject.getStudySubjectStudyVersion().getStudySiteStudyVersion()==null){
			StudySiteStudyVersion studySiteStudyVersion= new StudySiteStudyVersion();
			studySiteStudyVersion.setStudyVersion(studyVersion);
			studySubject.getStudySubjectStudyVersion().setStudySiteStudyVersion(studySiteStudyVersion);			
		}else{
			studySubject.getStudySubjectStudyVersion().getStudySiteStudyVersion().setStudyVersion(studyVersion);
		}
	}

}
