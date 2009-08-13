package edu.duke.cabig.c3pr.xml;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.StudySubject;

public class RegistrationSiteFieldHandler implements FieldHandler{
	
	Logger log = Logger.getLogger(RegistrationSiteFieldHandler.class);

	@Deprecated
	public void checkValidity(Object object) throws ValidityException,
			IllegalStateException {
		
	}

	public Object getValue(Object object) throws IllegalStateException {
		StudySubject studySubejct = (StudySubject) object;
		try{
			return studySubejct.getStudySite().getHealthcareSite().getCtepCode();
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
		StudySubject studySubject = (StudySubject) object;
		String ctepCode = (String) value;
		try{
			HealthcareSite healthcareSite = new LocalHealthcareSite();
			OrganizationAssignedIdentifier ctepAssignedIdentifier = new OrganizationAssignedIdentifier();
			ctepAssignedIdentifier.setType(OrganizationIdentifierTypeEnum.CTEP);
			ctepAssignedIdentifier.setValue(ctepCode);
			studySubject.getStudySite().setHealthcareSite(healthcareSite);
		}catch(Exception ex){
			log.warn("unable to set site in registration");
			log.warn(ex);
		}
	}

}
