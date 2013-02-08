/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.validators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import edu.duke.cabig.c3pr.web.admin.PersonOrUserWrapper;


public class CreatePersonUserValidator implements Validator {

    public boolean supports(Class aClass) {
    	return PersonOrUserWrapper.class.isAssignableFrom(aClass);
    }

    public void validate(Object object, Errors errors) {
    	PersonUser personUser = null;
    	PersonOrUserWrapper wrapper = null;
    	if(object instanceof PersonOrUserWrapper){
    		wrapper = (PersonOrUserWrapper) object;
    		personUser = wrapper.getPersonUser();
    	}
    	List<UserPrivilegeType> staffPrivilegesList = new ArrayList<UserPrivilegeType>();
    	staffPrivilegesList.add(UserPrivilegeType.UI_RESEARCHSTAFF_CREATE);
    	staffPrivilegesList.add(UserPrivilegeType.UI_RESEARCHSTAFF_UPDATE);
    	
    	List<UserPrivilegeType> userPrivilegesList = new ArrayList<UserPrivilegeType>();
    	userPrivilegesList.add(UserPrivilegeType.USER_CREATE);
    	userPrivilegesList.add(UserPrivilegeType.USER_UPDATE);
    	
    	if(SecurityUtils.hasAnyPrivilege(staffPrivilegesList) && wrapper.getCreateAsStaff() && StringUtils.isBlank(personUser.getAssignedIdentifier())){
			errors.reject("ASSIGNED_ID_NULL","If you wish to create a staff, enter an Assigned Identifier");
		}
    	if(SecurityUtils.hasAnyPrivilege(userPrivilegesList) && wrapper.getCreateAsUser() && StringUtils.isBlank(wrapper.getUserName())){
			errors.reject("USERNAME_NULL","If you wish to create a user, enter a Username");
		} 
    	if(SecurityUtils.hasAnyPrivilege(staffPrivilegesList) && StringUtils.isNotBlank(personUser.getAssignedIdentifier()) && !wrapper.getCreateAsStaff()){
    		personUser.setAssignedIdentifier(null);
    		//errors.reject("ASSIGNED_ID_NOT_NULL","If you wish to create a staff, select Create as Research Staff");
		}
    	if(SecurityUtils.hasAnyPrivilege(userPrivilegesList) && StringUtils.isNotBlank(wrapper.getUserName()) && !wrapper.getCreateAsUser()){
    		wrapper.setUserName(null);
    		//errors.reject("USERNAME_NOT_NULL","If you wish to create a user, select Create as User");
		}
    	
    	if(!SecurityUtils.hasAnyPrivilege(userPrivilegesList)){
    		wrapper.setCreateAsUser(false);
    	}
    	if(!SecurityUtils.hasAnyPrivilege(staffPrivilegesList)){
    		wrapper.setCreateAsStaff(false);
    	}
        
    }

}
