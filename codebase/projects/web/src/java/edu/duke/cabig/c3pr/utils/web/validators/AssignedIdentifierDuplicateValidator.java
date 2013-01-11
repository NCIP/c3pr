/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.validators;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.web.admin.PersonOrUserWrapper;


public class AssignedIdentifierDuplicateValidator implements Validator {

	protected PersonUserDao personUserDao;
	
    public boolean supports(Class aClass) {
    	return PersonOrUserWrapper.class.isAssignableFrom(aClass);
    }

    public void validate(Object object, Errors errors) {
    	PersonUser personUser = null;
    	if(object instanceof PersonOrUserWrapper){
    		PersonOrUserWrapper wrapper = (PersonOrUserWrapper) object;
    		personUser = wrapper.getPersonUser();
    	}
    	
    	//Since assigned identifier can be empty during user creation
    	if(StringUtils.isBlank(personUser.getAssignedIdentifier())){
    		return;
    	}
        PersonUser rStaffFromDB = personUserDao.getByAssignedIdentifierFromLocal(personUser.getAssignedIdentifier());
		if (rStaffFromDB != null && !rStaffFromDB.getId().equals(personUser.getId())) {
			errors.reject("RSTAFF_EXISTS","Research Staff with identifier " +personUser.getAssignedIdentifier()+ " already exists");
			return;
		}
    }

    @Required
	public void setPersonUserDao(PersonUserDao personUserDao) {
		this.personUserDao = personUserDao;
	}
}
