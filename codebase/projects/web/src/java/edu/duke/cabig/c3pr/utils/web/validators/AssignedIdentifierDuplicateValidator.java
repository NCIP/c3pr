package edu.duke.cabig.c3pr.utils.web.validators;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.ResearchStaff;


public class AssignedIdentifierDuplicateValidator implements Validator {

	protected ResearchStaffDao researchStaffDao;
	
    public boolean supports(Class aClass) {
    	return ResearchStaff.class.isAssignableFrom(aClass);
    }

    public void validate(Object object, Errors errors) {
        ResearchStaff researchStaff = (ResearchStaff) object;
        ResearchStaff rStaffFromDB = researchStaffDao.getByAssignedIdentifierFromLocal(researchStaff
													.getAssignedIdentifier());
		if (rStaffFromDB != null && !rStaffFromDB.getId().equals(researchStaff.getId())) {
			errors.reject("RSTAFF_EXISTS","Research Staff with identifier " +researchStaff.getAssignedIdentifier()+ " already exists");
			return;
		}
    }

    @Required
	public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
		this.researchStaffDao = researchStaffDao;
	}
}