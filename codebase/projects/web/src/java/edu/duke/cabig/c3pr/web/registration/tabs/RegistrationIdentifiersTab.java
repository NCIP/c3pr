package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.Map;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.validator.StudySubjectValidator;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 12:51:05 PM To change this
 * template use File | Settings | File Templates.
 */
public class RegistrationIdentifiersTab<C extends StudySubjectWrapper> extends RegistrationTab<C> {

    public RegistrationIdentifiersTab() {
        super("Identifier", "Identifiers", "registration/reg_identifiers");
        
    }
    
    @Override
	public Map<String, Object> referenceData(C command) {
        Map<String, Object> refdata = super.referenceData();
        refdata.put("orgIdentifiersTypeRefData", configurationProperty.getMap().get("orgIdentifiersTypeRefData"));
        return refdata;
    }
    
    private StudySubjectValidator studySubjectValidator;

    public StudySubjectValidator getStudySubjectValidator() {
		return studySubjectValidator;
	}

	public void setStudySubjectValidator(StudySubjectValidator studySubjectValidator) {
		this.studySubjectValidator = studySubjectValidator;
	}

	@Override
    public void validate(StudySubjectWrapper wrapper, Errors errors) {
        this.studySubjectValidator.validateIdentifiers(wrapper.getStudySubject(), errors);

    }

   
    
}
