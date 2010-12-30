package edu.duke.cabig.c3pr.annotations.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import edu.duke.cabig.c3pr.annotations.UniqueEmailAddressForResearchStaff;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.dao.query.ResearchStaffQuery;
import edu.duke.cabig.c3pr.domain.PersonUser;

public class UniqueEmailAddressForResearchStaffValidator implements
                Validator<UniqueEmailAddressForResearchStaff> {

    String message;

    private PersonUserDao personUserDao;

    public boolean validate(final Object value) {
        if (value instanceof String) {

            ResearchStaffQuery researchStaffQuery = new ResearchStaffQuery();
            researchStaffQuery.filterByEmailAddress((String) value);
            List<PersonUser> researchStaffList = personUserDao
                            .searchResearchStaff(researchStaffQuery);
            return (researchStaffList == null || researchStaffList.isEmpty()) ? true : false;
        }
        return true;
    }

    public void initialize(UniqueEmailAddressForResearchStaff parameters) {
        message = parameters.message();

    }

    public String message() {
        return message;
    }

    @Required
    public void setPersonUserDao(PersonUserDao personUserDao) {
        this.personUserDao = personUserDao;
    }
}