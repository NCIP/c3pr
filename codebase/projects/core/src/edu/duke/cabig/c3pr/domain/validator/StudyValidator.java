package edu.duke.cabig.c3pr.domain.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;

public class StudyValidator implements Validator{	
	StudySiteValidator studySiteValidator;
	EpochValidator epochValidator;
	
	public boolean supports(Class clazz) {
		return Study.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		validatePage0(target, errors);
		validatePage2(target, errors);
		validatePage3(target, errors);
	}
	
	//Study Validation 
	public void validatePage0(Object target, Errors errors) {		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "longTitleText", "required",
			"required field");
		ValidationUtils.rejectIfEmpty(errors, "multiInstitutionIndicator", 
			"required", "required field");
	}
	
	//StudySite
	public void validatePage2(Object target, Errors errors) {
		 Study study = (Study) target;
	      try {
	          errors.pushNestedPath("studySite");
	          List<StudySite> studySites = study.getStudySites();
	          for (StudySite studySite : studySites) {
	        	 ValidationUtils.invokeValidator(this.studySiteValidator, 
	        	 studySite, errors);
			}	         
	      } finally {
	          errors.popNestedPath();
	      }	
	}
	
	//TODO Epoch Validator
	public void validatePage3(Object target, Errors errors) {
	
	}
	
	public EpochValidator getEpochValidator() {
		return epochValidator;
	}

	public void setEpochValidator(EpochValidator epochValidator) {
		this.epochValidator = epochValidator;
	}

	public StudySiteValidator getStudySiteValidator() {
		return studySiteValidator;
	}

	public void setStudySiteValidator(StudySiteValidator studySiteValidator) {
		this.studySiteValidator = studySiteValidator;
	}	
	
}