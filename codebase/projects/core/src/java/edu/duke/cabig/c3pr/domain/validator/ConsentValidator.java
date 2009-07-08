package edu.duke.cabig.c3pr.domain.validator;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentVersion;

public class ConsentValidator implements Validator{
	
	private ConsentVersionValidator consentVersionValidator;
	private StudyValidator studyValidator;
	private Logger log = Logger.getLogger(ConsentValidator.class);
	
	public ConsentVersionValidator getConsentVersionValidator() {
		return consentVersionValidator;
	}

	public void setConsentVersionValidator(
			ConsentVersionValidator consentVersionValidator) {
		this.consentVersionValidator = consentVersionValidator;
	}

	public StudyValidator getStudyValidator() {
		return studyValidator;
	}

	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}

	public boolean supports(Class clazz) {
        return Consent.class.isAssignableFrom(clazz);
    }
	
	public void validate(Object target, Errors errors) {
        validateConsentVersions(target, errors);
    }
	
    public void validateConsentVersions(Object target, Errors errors) {
        Consent consent = (Consent) target;
        List<ConsentVersion> versions = consent.getConsentVersions();
        try {
            for (int versionIndex = 0; versionIndex < versions.size(); versionIndex++) {
                errors.pushNestedPath("consentVersions[" + versionIndex + "]");
                ValidationUtils.invokeValidator(this.consentVersionValidator, versions.get(versionIndex), errors);
                errors.popNestedPath();
            }
            Set<ConsentVersion> uniqueVersions = new TreeSet<ConsentVersion>();
            uniqueVersions.addAll(versions);
            if (versions.size() > uniqueVersions.size()) {
                errors.rejectValue("consentVersions", 
                		new Integer(studyValidator.getCode("C3PR.STUDY.DUPLICATE.CONSENT.VERSION.ERROR")).toString(),
                                studyValidator.getMessageFromCode(studyValidator.getCode("C3PR.STUDY.DUPLICATE.CONSENT.VERSION.ERROR"), null, null));
            }

        }
        catch (Exception ex) {
            log.debug("error while validating consent versions");
        }
    }
  
}
