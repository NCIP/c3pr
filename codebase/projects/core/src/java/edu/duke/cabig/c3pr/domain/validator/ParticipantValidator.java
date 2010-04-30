package edu.duke.cabig.c3pr.domain.validator;

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
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;

public class ParticipantValidator implements Validator {

    private ParticipantDao participantDao;

    private MessageSource c3prErrorMessages;
    
    private Logger log = Logger.getLogger(ParticipantValidator.class);

    public boolean supports(Class clazz) {
        return Participant.class.equals(clazz);
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

    public void validateParticipantMRN(Object target, Errors errors) {

        Participant participant = (Participant) target;
        OrganizationAssignedIdentifier mrn = participant.getMRN();

        if ((mrn != null) && (mrn.getHealthcareSite() != null)) {
            List<OrganizationAssignedIdentifier> participantsWithMRN = participantDao
                            .getSubjectIdentifiersWithMRN(mrn.getValue(), mrn.getHealthcareSite());
            if (participantsWithMRN.size() > 0) {
                if ((participant.getId() == null) || (participantsWithMRN.size() > 1)) {
                    errors
                                    .rejectValue("primaryIdentifierValue", new Integer(
                                            getCode("C3PR.SUBJECT.DUPLICATE.MRN.ERROR"))
                                    .toString(),
                    getMessageFromCode(
                                    getCode("C3PR.SUBJECT.DUPLICATE.MRN.ERROR"),
                                    null, null));
                }
            }
        }

    }
    
    
    public void validateIdentifiers(Object target, Errors errors) {
        Participant participant = (Participant) target;
        List<OrganizationAssignedIdentifier> allOrganizationAssigedIdentitiers = participant
                        .getOrganizationAssignedIdentifiers();
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
