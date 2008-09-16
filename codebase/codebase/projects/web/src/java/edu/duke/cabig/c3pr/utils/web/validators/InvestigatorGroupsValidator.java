package edu.duke.cabig.c3pr.utils.web.validators;

import java.util.Date;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.dao.InvestigatorGroupDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.web.admin.InvestigatorGroupsCommand;

public class InvestigatorGroupsValidator implements Validator {

    private InvestigatorGroupDao investigatorGroupDao;

    public boolean supports(Class clazz) {
        return InvestigatorGroupsCommand.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        validateGroup((InvestigatorGroupsCommand) target, errors);
    }

    public void validateGroup(InvestigatorGroupsCommand investigatorGroupsCommand, Errors errors) {
        InvestigatorGroup investigatorGroup = investigatorGroupsCommand.getHealthcareSite()
                        .getInvestigatorGroups().get(0);
        if ((investigatorGroup.getStartDate() != null && investigatorGroup.getStartDate().after(
                        new Date()))) {
            errors.reject("tempProperty", "Start Date cannot be later than today's Date");
        }
    }

    public void setInvestigatorGroupDao(InvestigatorGroupDao investigatorGroupDao) {
        this.investigatorGroupDao = investigatorGroupDao;
    }

}
