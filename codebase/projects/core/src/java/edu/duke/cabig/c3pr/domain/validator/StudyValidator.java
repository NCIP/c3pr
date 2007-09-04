package edu.duke.cabig.c3pr.domain.validator;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;

public class StudyValidator implements Validator {
	StudySiteValidator studySiteValidator;

	IdentifierValidator identifierValidator;

	EpochValidator epochValidator;

	public boolean supports(Class clazz) {
		return Study.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		validateStudy(target, errors);
		validateIdentifiers(target, errors);
		validateStudySites(target, errors);
		validateStudyDesign(target, errors);
	}

	public void validateStudy(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "longTitleText",
				"required", "required field");
		ValidationUtils.rejectIfEmpty(errors, "status", "required",
				"required field");
		ValidationUtils.rejectIfEmpty(errors, "sponsorCode", "required",
				"required field");
		ValidationUtils.rejectIfEmpty(errors, "phaseCode", "required",
				"required field");
		ValidationUtils.rejectIfEmpty(errors, "type", "required",
				"required field");
	}

	public void validateIdentifiers(Object target, Errors errors) {
		Study study = (Study) target;
		try {
			errors.pushNestedPath("identifiers");
			for (Identifier identifier : study.getIdentifiers()) {
				ValidationUtils.invokeValidator(this.identifierValidator,
						identifier, errors);
			}
		} finally {
			errors.popNestedPath();
		}
	}

	public void validateStudySites(Object target, Errors errors) {
		Study study = (Study) target;
		List<StudySite> allStudySites = study.getStudySites();
		try {
			errors.pushNestedPath("studySites");
			for (StudySite studySite : allStudySites) {
				ValidationUtils.invokeValidator(this.studySiteValidator,
						studySite, errors);
			}
			Set<StudySite> uniqueStudySites = new TreeSet<StudySite>();
			uniqueStudySites.addAll(allStudySites);
			if(allStudySites.size()>uniqueStudySites.size()){
				errors.reject("Study Site already exists");
			}
		} finally {
			errors.popNestedPath();
		}
	}

	public void validateStudyDesign(Object target, Errors errors) {
		Study study = (Study) target;
		try {
			errors.pushNestedPath("epochs");
			List<Epoch> allEpochs = study.getEpochs();
			for (Epoch epoch : allEpochs) {
				ValidationUtils.invokeValidator(this.epochValidator, epoch,
						errors);
			}
			Set<Epoch> uniqueEpochs = new TreeSet<Epoch>();
			uniqueEpochs.addAll(allEpochs);
			if (allEpochs.size() > uniqueEpochs.size()) {
				errors.reject("Epoch already exists");
			}
		} finally {
			errors.popNestedPath();
		}
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

	public IdentifierValidator getIdentifierValidator() {
		return identifierValidator;
	}

	public void setIdentifierValidator(IdentifierValidator identifierValidator) {
		this.identifierValidator = identifierValidator;
	}

}