package edu.duke.cabig.c3pr.annotations.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import edu.duke.cabig.c3pr.annotations.UniqueEmailAddressForResearchStaff;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.dao.query.ResearchStaffQuery;
import edu.duke.cabig.c3pr.domain.ResearchStaff;

public class UniqueEmailAddressForResearchStaffValidator implements
                Validator<UniqueEmailAddressForResearchStaff> {

    String message;

    private ResearchStaffDao researchStaffDao;

    public boolean validate(final Object value) {
        if (value instanceof String) {

            ResearchStaffQuery researchStaffQuery = new ResearchStaffQuery();
            researchStaffQuery.filterByEmailAddress((String) value);
            List<ResearchStaff> researchStaffList = researchStaffDao
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
    public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
        this.researchStaffDao = researchStaffDao;
    }
}