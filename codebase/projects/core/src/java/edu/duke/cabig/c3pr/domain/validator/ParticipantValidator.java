package edu.duke.cabig.c3pr.domain.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDemographicsDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.repository.ParticipantRepository;

public class ParticipantValidator implements Validator {

    private ParticipantDao participantDao;
    
    private StudySubjectDemographicsDao studySubjectDemographicsDao;

    private MessageSource c3prErrorMessages;
    
    public void setStudySubjectDemographicsDao(
			StudySubjectDemographicsDao studySubjectDemographicsDao) {
		this.studySubjectDemographicsDao = studySubjectDemographicsDao;
	}

	private Logger log = Logger.getLogger(ParticipantValidator.class);
    
    private ParticipantRepository participantRepository;

    public boolean supports(Class clazz) {
        return Participant.class.equals(clazz);
    }

    public void setParticipantRepository(ParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}

	public void validate(Object arg0, Errors errors) {
        validateParticipantDetails(arg0, errors);
        validateParticipantAddress(arg0, errors);
    }

    public void validateParticipantDetails(Object arg0, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "firstName", "required", "required field");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "required", "required field");
        ValidationUtils.rejectIfEmpty(errors, "birthDate", "required", "required field");
    }

    public void validateParticipantAddress(Object arg0, Errors errors) {

        ValidationUtils
                        .rejectIfEmpty(errors, "address.streetAddress", "required",
                                        "required field");
        ValidationUtils.rejectIfEmpty(errors, "address.city", "required", "required field");
        ValidationUtils.rejectIfEmpty(errors, "address.stateCode", "required", "required field");
        ValidationUtils.rejectIfEmpty(errors, "address.countryCode", "required", "required field");

    }
    
    public void validateIdentifiers(Object target, Errors errors) {
        Participant participant = (Participant) target;
        List<OrganizationAssignedIdentifier> allOrganizationAssigedIdentitiers = participant
                        .getOrganizationAssignedIdentifiers();
        
        List<Identifier> commonIdentifiers = new ArrayList<Identifier>();
        for(Identifier identifier: participant.getIdentifiers()){
        	List<Participant> existingParticipants = new ArrayList<Participant>();
        	existingParticipants = participantDao.searchByIdentifier(identifier,Participant.class);
        	
        	List<StudySubjectDemographics> existingSubjectSnapShots = new ArrayList<StudySubjectDemographics>();
        	existingSubjectSnapShots = studySubjectDemographicsDao.searchByIdentifier(identifier,StudySubjectDemographics.class);
        	
        	// there cannot be more than 1 subject with the same identifier
        	if(existingParticipants.size() > 1){
        		commonIdentifiers.add(identifier);
        		break;
        	}
        	
        	if(existingParticipants.size() > 0 || existingSubjectSnapShots.size() > 0){
        		// when subject is first time created, there cannot be another subject or subject snapshot with same identifiers.
	        	if(participant.getId() == null){
	        		commonIdentifiers.add(identifier);
	        		break;
	        		
	        		// when a subject already exists with same identifier, the existing subject should be same as current subject 
	        		// otherwise throw error
	        	}else if((existingParticipants.size()== 1) && (!existingParticipants.get(0).getId().equals(participant.getId()))){
	        		commonIdentifiers.add(identifier);
	        		break;
	        	} else {
	        		for(StudySubjectDemographics snapshot: existingSubjectSnapShots){
	        			if (!snapshot.getMasterSubject().getId().equals(participant.getId())){
	        				commonIdentifiers.add(identifier);
	                		break;
	        			}
	        		}
	        	}
        	}
        }
        
        for(Identifier identifier: commonIdentifiers){
        		 errors.reject("tempProperty", getMessageFromCode( getCode("C3PR.COMMON.DUPLICATE.IDENTIFIER.ERROR"),
 	                    new Object[]{identifier.getValue(), "Subject"},null));
        	}
        
        try {
            for (int orgIdentifierIndex = 0; orgIdentifierIndex < allOrganizationAssigedIdentitiers
                            .size(); orgIdentifierIndex++) {
                errors
                                .pushNestedPath("organizationAssignedIdentifiers["
                                                + orgIdentifierIndex + "]");
                errors.popNestedPath();
            }

            Set<OrganizationAssignedIdentifier> uniqueOrgIdentifiers = new HashSet<OrganizationAssignedIdentifier>();
            uniqueOrgIdentifiers.addAll(allOrganizationAssigedIdentitiers);
            if (allOrganizationAssigedIdentitiers.size() > uniqueOrgIdentifiers.size()) {
                errors
                                .rejectValue(
                                                "organizationAssignedIdentifiers",
                                                new Integer(
                                                                getCode("C3PR.SUBJECT.DUPLICATE.ORGANIZATION_ASSIGNED_IDENTIFIER.ERROR"))
                                                                .toString(),
                                                getMessageFromCode(
                                                                getCode("C3PR.SUBJECT.DUPLICATE.ORGANIZATION_ASSIGNED_IDENTIFIER.ERROR"),
                                                                null, null));
            }
        }
        catch (Exception ex) {
            log.debug(ex.getMessage());
        }
        List<SystemAssignedIdentifier> allSystemAssigedIdentitiers = participant
                        .getSystemAssignedIdentifiers();
        try {
            for (int sysIdentifierIndex = 0; sysIdentifierIndex < allOrganizationAssigedIdentitiers
                            .size(); sysIdentifierIndex++) {
                errors.pushNestedPath("systemAssignedIdentifiers[" + sysIdentifierIndex + "]");
                errors.popNestedPath();
            }
            Set<SystemAssignedIdentifier> uniqueSysIdentifiers = new HashSet<SystemAssignedIdentifier>();
            uniqueSysIdentifiers.addAll(allSystemAssigedIdentitiers);
            if (allSystemAssigedIdentitiers.size() > uniqueSysIdentifiers.size()) {
                errors.rejectValue("systemAssignedIdentifiers", new Integer(
                                getCode("C3PR.SUBJECT.DUPLICATE.SYSTEM_ASSIGNED_IDENTIFIER.ERROR"))
                                .toString(), getMessageFromCode(
                                getCode("C3PR.SUBJECT.DUPLICATE.SYSTEM_ASSIGNED_IDENTIFIER.ERROR"),
                                null, null));
            }

        }
        catch (Exception ex) {
            log.debug(ex.getMessage());
        }
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


    public ParticipantDao getParticipantDao() {
        return participantDao;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

}
