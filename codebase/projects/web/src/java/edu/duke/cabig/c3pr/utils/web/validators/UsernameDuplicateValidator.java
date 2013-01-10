/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.validators;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.web.admin.PersonOrUserWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Oct 19, 2007 Time: 10:46:54 AM To change this
 * template use File | Settings | File Templates.
 */
public class UsernameDuplicateValidator implements Validator {

	private CSMUserRepository csmUserRepository;
	
	private Logger log = Logger.getLogger(UsernameDuplicateValidator.class);
	
    public boolean supports(Class aClass) {
    	return PersonOrUserWrapper.class.isAssignableFrom(aClass);
    }

    public void validate(Object object, Errors errors) {
//        PersonUserWrapper wrapper = (PersonUserWrapper) object;
//        PersonUser user = wrapper.getPersonUser();
//        
//        if(StringUtils.getBlankIfNull(user.getLoginId()).equals("")){
//        	errors.reject("submision.errors");
//        }
//        // do it for old and new users. The search should be against remote research staff too
//        //for now the search is against the db only as searching remote causes stale object exception on ORacle. see CPR-578
//        
//        	if(user instanceof RemotePersonUser){ } else {
//            	try {
//            		//using login id. Since the user name check should only happen in create flow and not in module flow
//            		//the login id check will work.
//					if(csmUserRepository.getUserByName(user.getLoginId()) != null){
//						errors.reject("duplicate.username.error");
//					}
//				} catch (C3PRNoSuchUserException e) {
//					//user not found in CSM, no action needed.
//				}
//        	}
    }
    
    @Required
	public void setCsmUserRepository(CSMUserRepository csmUserRepository) {
		this.csmUserRepository = csmUserRepository;
	}
}
