package edu.duke.cabig.c3pr.domain.validator;

import java.util.ArrayList;
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
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;

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
        List<OrganizationAssignedIdentifier> allOrganizationAssigedIdentitiers = study
                        .getOrganizationAssignedIdentifiers();
        try {
            for (int orgIdentifierIndex = 0; orgIdentifierIndex < allOrganizationAssigedIdentitiers
                            .size(); orgIdentifierIndex++) {
                errors
                                .pushNestedPath("study.organizationAssignedIdentifiers["
                                                + orgIdentifierIndex + "]");
                // ValidationUtils.invokeValidator(this.identifierValidator,
                // allOrganizationAssigedIdentitiers.get(orgIdentifierIndex), errors);
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
                // ValidationUtils.invokeValidator(this.studySiteValidator,
                // allStudySites.get(studySiteIndex), errors);
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

    public void validateStudyPersonnel(Object target, Errors errors) {
        Study study = (Study) target;
        List<StudyPersonnel> allStudyPersonnel = new ArrayList<StudyPersonnel>();

        try {
            for (StudySite studySite : study.getStudySites()) {
                allStudyPersonnel.addAll(studySite.getStudyPersonnel());
            }
            Set<StudyPersonnel> uniqueStudyPersonnel = new HashSet<StudyPersonnel>();
            List<StudyPersonnel> notNullPersonnelList = new ArrayList<StudyPersonnel>();
            if (notNullPersonnelList.contains(null)) {
                notNullPersonnelList.remove(null);
            }
            for (StudyPersonnel studyPerson : allStudyPersonnel) {
                if (studyPerson != null) notNullPersonnelList.add(studyPerson);
            }
            uniqueStudyPersonnel.addAll(notNullPersonnelList);
            if (notNullPersonnelList.size() > uniqueStudyPersonnel.size()) {
                errors
                                .rejectValue(
                                                "study.studySites[0].studyPersonnel",
                                                new Integer(
                                                                getCode("C3PR.STUDY.DUPLICATE.STUDY.PERSON.ROLE.ERROR"))
                                                                .toString(),
                                                getMessageFromCode(
                                                                getCode("C3PR.STUDY.DUPLICATE.STUDY.PERSON.ROLE.ERROR"),
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

}