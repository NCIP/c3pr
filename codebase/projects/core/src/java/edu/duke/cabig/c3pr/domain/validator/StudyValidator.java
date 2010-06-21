package edu.duke.cabig.c3pr.domain.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.CommonUtils;
import edu.duke.cabig.c3pr.utils.DateUtil;

public class StudyValidator implements Validator {
    private StudySiteValidator studySiteValidator;

    private IdentifierValidator identifierValidator;

    private EpochValidator epochValidator;

    private StudyDao studyDao;

    private MessageSource c3prErrorMessages;

	private Logger log = Logger.getLogger(StudyValidator.class);
    

    public boolean supports(Class clazz) {
        return Study.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        validateStudy(target, errors);
        validateStudyIdentifiers(target, errors);
        validateStudySites(target, errors);
        validateStudyDesign(target, errors);
        validateConsents(target, errors);
        validateStudyVersion(target, errors);
    }

    public void validateStudyVersion(Object target, Errors errors) {
    	Study study = (Study) target;
        List<StudyVersion> allVersions = study.getStudyVersions();
        Set<StudyVersion> uniqueVersions = new HashSet<StudyVersion>();
        uniqueVersions.addAll(allVersions);
        try {
            if (allVersions.size() > uniqueVersions.size()) {
                errors.rejectValue("study.studyVersions", new Integer(
                                getCode("C3PR.STUDY.DUPLICATE.STUDY_VERSION.ERROR")).toString(),
                                getMessageFromCode(getCode("C3PR.STUDY.DUPLICATE.STUDY_VERSION.ERROR"),
                                                null, null));
            }

        }
        catch (Exception ex) {
            log.debug(ex.getMessage());
        }
		
	}

	public void validateStudy(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "study.longTitleText", "required",
                        "required field");
        ValidationUtils.rejectIfEmpty(errors, "study.coordinatingCenterStudyStatus", "required",
                        "required field");
        ValidationUtils.rejectIfEmpty(errors, "study.phaseCode", "required", "required field");
        ValidationUtils.rejectIfEmpty(errors, "study.type", "required", "required field");
    }

    public void validateStudyIdentifiers(Object target, Errors errors) {
        Study study = (Study) target;
        
		List<Identifier> commonIdentifiers = new ArrayList<Identifier>();
        for(Identifier identifier: study.getIdentifiers()){
        	List<Study> existingStudies = new ArrayList<Study>();
        	existingStudies = studyDao.searchByIdentifier(identifier,Study.class);
        	
        	//  there cannot be more than 1 study with the same identifier
        	if(existingStudies.size() > 1){
        		commonIdentifiers.add(identifier);
        		break;
        	}
        	
        	if(existingStudies.size() > 0){
        		// when study is first time created, there cannot be another study with same identifier(s).
	        	if(study.getId() == null){
	        		commonIdentifiers.add(identifier);
	        		break;
	        		
	        		// when a study already exists with same identifier, the existing study should be same as current study 
	        		// otherwise it is already assigned to the other study
	        	}else if(existingStudies.size()== 1 && !existingStudies.get(0).getId().equals(study.getId())){
	        		commonIdentifiers.add(identifier);
	        		break;
	        	} 
        	}
        }
        for(Identifier identifier: commonIdentifiers){
        		 errors.reject("tempProperty", getMessageFromCode( getCode("C3PR.COMMON.DUPLICATE.IDENTIFIER.ERROR"),
 	                    new Object[]{identifier.getValue(), "Study"},null));
        	}
        List<OrganizationAssignedIdentifier> allOrganizationAssigedIdentitiers = study
                        .getOrganizationAssignedIdentifiers();
        try {
            for (int orgIdentifierIndex = 0; orgIdentifierIndex < allOrganizationAssigedIdentitiers
                            .size(); orgIdentifierIndex++) {
                errors
                                .pushNestedPath("study.organizationAssignedIdentifiers["
                                                + orgIdentifierIndex + "]");
                errors.popNestedPath();
            }

            Set<OrganizationAssignedIdentifier> uniqueOrgIdentifiers = new HashSet<OrganizationAssignedIdentifier>();
            uniqueOrgIdentifiers.addAll(allOrganizationAssigedIdentitiers);
            if (allOrganizationAssigedIdentitiers.size() > uniqueOrgIdentifiers.size()) {
                errors
                                .rejectValue(
                                                "study.organizationAssignedIdentifiers",
                                                new Integer(
                                                                getCode("C3PR.STUDY.DUPLICATE.ORGANIZATION_ASSIGNED_IDENTIFIER.ERROR"))
                                                                .toString(),
                                                getMessageFromCode(
                                                                getCode("C3PR.STUDY.DUPLICATE.ORGANIZATION_ASSIGNED_IDENTIFIER.ERROR"),
                                                                null, null));
            }
        }
        catch (Exception ex) {
            log.debug(ex.getMessage());
        }
        List<SystemAssignedIdentifier> allSystemAssigedIdentitiers = study
                        .getSystemAssignedIdentifiers();
        try {
            for (int sysIdentifierIndex = 0; sysIdentifierIndex < allOrganizationAssigedIdentitiers
                            .size(); sysIdentifierIndex++) {
                errors.pushNestedPath("study.systemAssignedIdentifiers[" + sysIdentifierIndex + "]");
                errors.popNestedPath();
            }
            Set<SystemAssignedIdentifier> uniqueSysIdentifiers = new HashSet<SystemAssignedIdentifier>();
            uniqueSysIdentifiers.addAll(allSystemAssigedIdentitiers);
            if (allSystemAssigedIdentitiers.size() > uniqueSysIdentifiers.size()) {
                errors.rejectValue("study.systemAssignedIdentifiers", new Integer(
                                getCode("C3PR.STUDY.DUPLICATE.SYSTEM_ASSIGNED_IDENTIFIER.ERROR"))
                                .toString(), getMessageFromCode(
                                getCode("C3PR.STUDY.DUPLICATE.SYSTEM_ASSIGNED_IDENTIFIER.ERROR"),
                                null, null));
            }

        }
        catch (Exception ex) {
            log.debug(ex.getMessage());
        }
    }

    public void validateStudySites(Object target, Errors errors) {
        Study study = (Study) target;
        List<StudySite> allStudySites = study.getStudySites();
        try {
            for (int studySiteIndex = 0; studySiteIndex < allStudySites.size(); studySiteIndex++) {
                errors.pushNestedPath("study.studySites[" + studySiteIndex + "]");
                errors.popNestedPath();
            }
            Set<StudySite> uniqueStudySites = new HashSet<StudySite>();
            uniqueStudySites.addAll(allStudySites);
            if (allStudySites.size() > uniqueStudySites.size()) {
                errors.rejectValue("study.studySites", new Integer(
                                getCode("C3PR.STUDY.DUPLICATE.STUDY_SITE.ERROR")).toString(),
                                getMessageFromCode(
                                                getCode("C3PR.STUDY.DUPLICATE.STUDY_SITE.ERROR"),
                                                null, null));
            }
        }
        catch (Exception ex) {
            log.debug(ex.getMessage());
        }
    }

    public void validateStudyInvestigators(Object target, Errors errors) {
        Study study = (Study) target;
        List<StudyInvestigator> allStudyInvestigators = new ArrayList<StudyInvestigator>();
        try {
            for (StudySite studySite : study.getStudySites()) {
                allStudyInvestigators.addAll(studySite.getStudyInvestigators());
            }
            Set<StudyInvestigator> uniqueStudyInvestigators = new HashSet<StudyInvestigator>();
            List<StudyInvestigator> notNullInvestigatorsList = new ArrayList<StudyInvestigator>();
            if (notNullInvestigatorsList.contains(null)) {
                notNullInvestigatorsList.remove(null);
            }

            for (StudyInvestigator studyInv : allStudyInvestigators) {
                if (studyInv != null) notNullInvestigatorsList.add(studyInv);
            }
            uniqueStudyInvestigators.addAll(notNullInvestigatorsList);
            if (notNullInvestigatorsList.size() > uniqueStudyInvestigators.size()) {
                errors.rejectValue("study.studySites[0].studyInvestigators", new Integer(
                                getCode("C3PR.STUDY.DUPLICATE.STUDY.INVESTIGATOR.ROLE.ERROR"))
                                .toString(), getMessageFromCode(
                                getCode("C3PR.STUDY.DUPLICATE.STUDY.INVESTIGATOR.ROLE.ERROR"),
                                null, null));
            }
        }
        catch (Exception ex) {
            log.debug(ex.getMessage());
        }
    }

    public void validateStudyDesign(Object target, Errors errors) {
        Study study = (Study) target;
        List<Epoch> allEpochs = study.getEpochs();
        try {
            Set<Epoch> uniqueEpochs = new HashSet<Epoch>();
            uniqueEpochs.addAll(allEpochs);
            if (allEpochs.size() > uniqueEpochs.size()) {
                errors.rejectValue("study.epochs", new Integer(
                                getCode("C3PR.STUDY.DUPLICATE.EPOCH.ERROR")).toString(),
                                getMessageFromCode(getCode("C3PR.STUDY.DUPLICATE.EPOCH.ERROR"),
                                                null, null));
            }

        }
        catch (Exception ex) {
            log.debug(ex.getMessage());
        }
    }

    public void validateEpochs(Object target, Errors errors) {
        Study study = (Study) target;
        List<Epoch> allEpochs = study.getEpochs();
        try {
            for (int epochIndex = 0; epochIndex < allEpochs.size(); epochIndex++) {
                errors.pushNestedPath("study.epochs[" + epochIndex + "]");
                ValidationUtils.invokeValidator(this.epochValidator, allEpochs
                                .get(epochIndex), errors);
                errors.popNestedPath();
            }
        }
        catch (Exception ex) {
            log.debug(ex.getMessage());
        }
    }

    public void validateStudyDiseases(Object target, Errors errors) {
        Study study = (Study) target;
        List<StudyDisease> allDiseases = study.getStudyDiseases();
        Set<StudyDisease> uniqueDiseases = new HashSet<StudyDisease>();
        uniqueDiseases.addAll(allDiseases);
        try {
            if (allDiseases.size() > uniqueDiseases.size()) {
                errors.rejectValue("study.studyDiseases", new Integer(
                                getCode("C3PR.STUDY.DUPLICATE.DISEASE.ERROR")).toString(),
                                getMessageFromCode(getCode("C3PR.STUDY.DUPLICATE.DISEASE.ERROR"),
                                                null, null));
            }

        }
        catch (Exception ex) {
            log.debug(ex.getMessage());
        }
    }

    public void validateStudyCoordinatingCetnterIdentifier(Object target, Errors errors) {

        Study study = (Study) target;
        OrganizationAssignedIdentifier coCenterIdentifier = study
                        .getCoordinatingCenterAssignedIdentifier();

        try {
            if ((coCenterIdentifier != null) && (coCenterIdentifier.getHealthcareSite() != null)) {
                List<OrganizationAssignedIdentifier> coCenterIdentifiers = studyDao
                                .getCoordinatingCenterIdentifiersWithValue(coCenterIdentifier
                                                .getValue(), coCenterIdentifier.getHealthcareSite());
                if (coCenterIdentifiers.size() > 0) {
                    if ((study.getId() == null) || (coCenterIdentifiers.size() > 1) || (coCenterIdentifiers.size() == 1 && study.getId() != studyIdOfExistingRecord(coCenterIdentifiers.get(0)))) {
                        errors
                                        .rejectValue(
                                                        "study.coordinatingCenterAssignedIdentifier",
                                                        "C3PR.STUDY.DUPLICATE.STUDY.COORDINATING.CENTER.IDENTIFIER.ERROR",
                                                        getMessageFromCode(
                                                                        getCode("C3PR.STUDY.DUPLICATE.STUDY.COORDINATING.CENTER.IDENTIFIER.ERROR"),
                                                                        null, null));
                    }
                }
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }

    }

    private int studyIdOfExistingRecord(OrganizationAssignedIdentifier organizationAssignedIdentifier){
    	List<Study> studies = studyDao.searchByOrgIdentifier(organizationAssignedIdentifier);
    	if(studies.size() == 1){
    		return studies.get(0).getId() ;
    	}else{
    		return 0;
    	}
    }

    public void validateStudyFundingSponsorIdentifier(Object target, Errors errors) {

        Study study = (Study) target;
        OrganizationAssignedIdentifier funSponIdentifier = study
                        .getFundingSponsorAssignedIdentifier();

        try {
            if ((funSponIdentifier != null) && (funSponIdentifier.getHealthcareSite() != null)) {
                List<OrganizationAssignedIdentifier> funSponIdentifiers = studyDao
                                .getFundingSponsorIdentifiersWithValue(
                                                funSponIdentifier.getValue(), funSponIdentifier
                                                                .getHealthcareSite());
                if (funSponIdentifiers.size() > 0) {
                    if ((study.getId() == null) || (funSponIdentifiers.size() > 1 || (funSponIdentifiers.size() == 1 && study.getId() != studyIdOfExistingRecord(funSponIdentifiers.get(0))))) {
                        errors
                                        .rejectValue(
                                                        "study.fundingSponsorAssignedIdentifier",
                                                        new Integer(
                                                                        getCode("C3PR.STUDY.DUPLICATE.STUDY.FUNDING.SPONSOR.IDENTIFIER.ERROR"))
                                                                        .toString(),
                                                        getMessageFromCode(
                                                                        getCode("C3PR.STUDY.DUPLICATE.STUDY.FUNDING.SPONSOR.IDENTIFIER.ERROR"),
                                                                        null, null));
                    }
                }
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();
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

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    public MessageSource getC3prErrorMessages() {
        return c3prErrorMessages;
    }

    public void setC3prErrorMessages(MessageSource errorMessages) {
        c3prErrorMessages = errorMessages;
    }

    public int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }

    public String getMessageFromCode(int code, Object[] params, Locale locale) {
        String msg = "";
        try {
            msg = c3prErrorMessages.getMessage(code + "", params, locale);
        }
        catch (NoSuchMessageException e) {
            try {
                msg = c3prErrorMessages.getMessage(-1 + "", null, null);
            }
            catch (NoSuchMessageException e1) {
                msg = "Exception Code property file missing";
            }
        }
        return msg;
    }

    public void validateConsents(Object target, Errors errors) {
    	  Study study = (Study) target;
          List<Consent> consents = study.getConsents();
          try {
              Set<Consent> uniqueConsents = new HashSet<Consent>();
              uniqueConsents.addAll(consents);
              if (consents.size() > uniqueConsents.size()) {
                  errors.rejectValue("study.consents", new Integer(getCode("C3PR.STUDY.DUPLICATE.CONSENT.ERROR")).toString(), getMessageFromCode(getCode("C3PR.STUDY.DUPLICATE.CONSENT.ERROR"), null, null));
              }

          }
          catch (Exception ex) {
              log.debug(ex.getMessage());
          }
          
          Boolean mandatoryConsentExists = false;
          for(Consent consent:consents){
        	  if(consent.getMandatoryIndicator()){
        		  mandatoryConsentExists = true;
        		  break;
        	  }
          }
          
          if (!mandatoryConsentExists){
        	  errors.reject("consentsMandatoryIndicator","There should be at least one mandatory consent");
          }
    }

    private ConsentValidator consentValidator;

    public ConsentValidator getConsentValidator() {
		return consentValidator;
	}

	public void setConsentValidator(ConsentValidator consentValidator) {
		this.consentValidator = consentValidator;
	}

	public void validateAmendment(Study study, Errors errors, String versionName) {
		StudyVersion currentVersion = study.getStudyVersion(versionName);
		validateStudyVersion(study, errors);
		validateAmendmentDate(study, errors, versionName);
		if(!errors.hasErrors()  && (currentVersion.getAmendmentReasons() == null || currentVersion.getAmendmentReasons().size() == 0)){
			errors.rejectValue("study.studyVersions", new Integer(
                    getCode("C3PR.EXCEPTION.STUDY.AMENDMENT.MISSING.AMENDED_ITEMS.CODE")).toString(),
                    getMessageFromCode(getCode("C3PR.EXCEPTION.STUDY.AMENDMENT.MISSING.AMENDED_ITEMS.CODE"),
                                    null, null));
		}
	}

	private void validateAmendmentDate(Study study, Errors errors, String versionName) {
		StudyVersion currentVersion = study.getStudyVersion(versionName);
		if(currentVersion.getVersionDate().after(new Date())){
			errors.rejectValue("study.studyVersions", new Integer(getCode("C3PR.EXCEPTION.STUDY.AMENDMENT.FUTURE.AMENDMENT_DATE.CODE")).toString(),
					getMessageFromCode(getCode("C3PR.EXCEPTION.STUDY.AMENDMENT.FUTURE.AMENDMENT_DATE.CODE"), null, null));
		}
		List<StudyVersion> studyVersions = study.getSortedStudyVersions();
		for(StudyVersion studyVersion : studyVersions){
			if(studyVersion != currentVersion &&  DateUtil.daysEqual(studyVersion.getVersionDate(), currentVersion.getVersionDate())){
				errors.rejectValue("study.studyVersions", new Integer(
	                    getCode("C3PR.EXCEPTION.STUDY.AMENDMENT.NO_AMENDMENT_ON_SAME_DATE.CODE")).toString(),
	                    getMessageFromCode(getCode("C3PR.EXCEPTION.STUDY.AMENDMENT.NO_AMENDMENT_ON_SAME_DATE.CODE"),
	                                    null, null));
			}
		}
		if(studyVersions.size() > 1){
			StudyVersion previousVersion = study.getReverseSortedStudyVersions().get(studyVersions.size() - 2); 
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(previousVersion.getVersionDate());
			cal.add(cal.DATE, 1);
			String previousVersionDateStr = CommonUtils.getDateString(cal.getTime());
			if(currentVersion.getVersionDate().before(previousVersion.getVersionDate())){
				errors.rejectValue("study.studyVersions", new Integer(
	                    getCode("C3PR.EXCEPTION.STUDY.AMENDMENT.DATE.OLDER_THAN_PREVIOUD_VERSION.CODE")).toString(),
	                    getMessageFromCode(getCode("C3PR.EXCEPTION.STUDY.AMENDMENT.DATE.OLDER_THAN_PREVIOUD_VERSION.CODE"),
	                                    new String[]{previousVersionDateStr}, null));
			}
		}
	}

}