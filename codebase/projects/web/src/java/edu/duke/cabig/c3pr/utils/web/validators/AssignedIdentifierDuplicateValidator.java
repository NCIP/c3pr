package edu.duke.cabig.c3pr.utils.web.validators;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.web.admin.PersonOrUserWrapper;
import edu.duke.cabig.c3pr.web.admin.ResearchStaffWrapper;


public class AssignedIdentifierDuplicateValidator implements Validator {

	protected PersonUserDao personUserDao;
	
    public boolean supports(Class aClass) {
    	return PersonOrUserWrapper.class.isAssignableFrom(aClass)  || 
				ResearchStaffWrapper.class.isAssignableFrom(aClass);
    }

    public void validate(Object object, Errors errors) {
    	PersonUser researchStaff = null;
    	if(object instanceof ResearchStaffWrapper){
    		ResearchStaffWrapper wrapper = (ResearchStaffWrapper) object;
    		researchStaff = wrapper.getResearchStaff();
    	} else if(object instanceof PersonOrUserWrapper){
    		PersonOrUserWrapper wrapper = (PersonOrUserWrapper) object;
    		researchStaff = wrapper.getPersonUser();
    	}
    	
    	//Since assigned identifier can be empty during user creation
    	if(StringUtils.isBlank(researchStaff.getAssignedIdentifier())){
    		return;
    	}
        PersonUser rStaffFromDB = personUserDao.getByAssignedIdentifierFromLocal(researchStaff.getAssignedIdentifier());
		if (rStaffFromDB != null && !rStaffFromDB.getId().equals(researchStaff.getId())) {
			errors.reject("RSTAFF_EXISTS","Research Staff with identifier " +researchStaff.getAssignedIdentifier()+ " already exists");
			return;
		}
    }

    @Required
	public void setPersonUserDao(PersonUserDao personUserDao) {
		this.personUserDao = personUserDao;
	}
}