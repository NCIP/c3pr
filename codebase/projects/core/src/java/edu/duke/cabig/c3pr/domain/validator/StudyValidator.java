package edu.duke.cabig.c3pr.domain.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;

public class StudyValidator implements Validator {
	private StudySiteValidator studySiteValidator;
	private IdentifierValidator identifierValidator;
	private EpochValidator epochValidator;

	public boolean supports(Class clazz) {
		return Study.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		validateStudy(target, errors);
		validateStudyIdentifiers(target, errors);
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

	public void validateStudyIdentifiers(Object target, Errors errors) {
		Study study = (Study) target;
		List<OrganizationAssignedIdentifier> allOrganizationAssigedIdentitiers = study.getOrganizationAssignedIdentifiers();
		try {
			for (int orgIdentifierIndex=0;orgIdentifierIndex<allOrganizationAssigedIdentitiers.size(); orgIdentifierIndex++){
				errors.pushNestedPath("organizationAssignedIdentifiers["+orgIdentifierIndex+"]");
			//	ValidationUtils.invokeValidator(this.identifierValidator,
			//			allOrganizationAssigedIdentitiers.get(orgIdentifierIndex), errors);
				errors.popNestedPath();
			}
			
			Set<OrganizationAssignedIdentifier> uniqueOrgIdentifiers = new TreeSet<OrganizationAssignedIdentifier>();
			uniqueOrgIdentifiers.addAll(allOrganizationAssigedIdentitiers);
			if(allOrganizationAssigedIdentitiers.size()>uniqueOrgIdentifiers.size()){
				errors.reject("tempProperty","Organization Assigned Identifier already exists");
			}
		} finally {
			//TODO
		}
		List<SystemAssignedIdentifier> allSystemAssigedIdentitiers = study.getSystemAssignedIdentifiers();
		try {
			for (int sysIdentifierIndex=0;sysIdentifierIndex<allOrganizationAssigedIdentitiers.size(); sysIdentifierIndex++){
				errors.pushNestedPath("systemAssignedIdentifiers["+sysIdentifierIndex+"]");
			//	ValidationUtils.invokeValidator(this.identifierValidator,
			//			allSystemAssigedIdentitiers.get(sysIdentifierIndex), errors);
				errors.popNestedPath();
			}
			Set<SystemAssignedIdentifier> uniqueSysIdentifiers = new TreeSet<SystemAssignedIdentifier>();
			uniqueSysIdentifiers.addAll(allSystemAssigedIdentitiers);
			if(allSystemAssigedIdentitiers.size()>uniqueSysIdentifiers.size()){
				errors.reject("tempProperty","System Assigned Identifier already exists");
			}
			
		} finally {
			//TODO
		}
	}
	
	/*public void validateStudyOrganizations(Object target, Errors errors) {
		Study study = (Study) target;
		List<StudyOrganization> allStudyOrganizations = study.getStudyOrganizations();
		try {
			for (int studyOrganizationIndex =0; studyOrganizationIndex<allStudyOrganizations.size();studyOrganizationIndex++) {
				errors.pushNestedPath("studyOrganizations["+studyOrganizationIndex+"]");
				ValidationUtils.invokeValidator(this.studyOrganizationValidator,
						allStudyOrganizations.get(studyOrganizationIndex), errors);
			}
			Set<StudyOrganization> uniqueStudyOrganizations = new TreeSet<StudyOrganization>();
			uniqueStudyOrganizations.addAll(allStudyOrganizations);
			if(allStudyOrganizations.size()>uniqueStudyOrganizations.size()){
				errors.reject("Study Organization already exists");
			}
		} finally {
			errors.popNestedPath();
		}
	}*/

	public void validateStudySites(Object target, Errors errors) {
		Study study = (Study) target;
		List<StudySite> allStudySites = study.getStudySites();
		try {
			for (int studySiteIndex=0;studySiteIndex<allStudySites.size(); studySiteIndex++) {
				errors.pushNestedPath("studySites["+studySiteIndex+"]");
			//	ValidationUtils.invokeValidator(this.studySiteValidator,
			//			allStudySites.get(studySiteIndex), errors);
				errors.popNestedPath();
			}
			Set<StudySite> uniqueStudySites = new TreeSet<StudySite>();
			uniqueStudySites.addAll(allStudySites);
			if(allStudySites.size()>uniqueStudySites.size()){
				errors.reject("tempProperty","Study Site already exists");
			}
		} finally {
			//TODO
		}
	}
	
	public void validateStudyInvestigators(Object target, Errors errors) {
		Study study = (Study) target;
		List<StudyInvestigator> allStudyInvestigators = null;
		for(StudyOrganization studyOrganizaiton:study.getStudyOrganizations()){
			if (studyOrganizaiton.getStudyInvestigators().size()>0)
			allStudyInvestigators.addAll(studyOrganizaiton.getStudyInvestigators());
		}
		try {
			Set<StudyInvestigator> uniqueStudyInvestigators = new TreeSet<StudyInvestigator>();
			if (allStudyInvestigators.size()>0){
			uniqueStudyInvestigators.addAll(allStudyInvestigators);
			if(allStudyInvestigators.size()>uniqueStudyInvestigators.size()){
				errors.reject("tempProperty","Study Investigator already exists in the same role");
			}
			}
		} finally {
			//TODO
		}
	}


	public void validateStudyDesign(Object target, Errors errors) {
		Study study = (Study) target;
		List<Epoch> allEpochs = study.getEpochs();
		try {
			for (int epochIndex=0;epochIndex<allEpochs.size();epochIndex++) {
				errors.pushNestedPath("epochs["+epochIndex+"]");
				ValidationUtils.invokeValidator(this.epochValidator, allEpochs.get(epochIndex),
						errors);
				errors.popNestedPath();
			}
			Set<Epoch> uniqueEpochs = new TreeSet<Epoch>();
			uniqueEpochs.addAll(allEpochs);
			if (allEpochs.size() > uniqueEpochs.size()) {
				errors.reject("tempProperty","Epoch with same name already exists");
			}
			
		} finally {
			
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