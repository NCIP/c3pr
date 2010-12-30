package edu.duke.cabig.c3pr.utils.web.validators;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.web.admin.PersonOrUserWrapper;
import edu.duke.cabig.c3pr.web.admin.ResearchStaffWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Oct 19, 2007 Time: 10:46:54 AM To change this
 * template use File | Settings | File Templates.
 */
public class UsernameDuplicateValidator implements Validator {

	private CSMUserRepository csmUserRepository;
	
	private Logger log = Logger.getLogger(UsernameDuplicateValidator.class);
	
    public boolean supports(Class aClass) {
        //return aClass.isAssignableFrom(ResearchStaff.class);
    	return PersonOrUserWrapper.class.isAssignableFrom(aClass) || 
				ResearchStaffWrapper.class.isAssignableFrom(aClass);
    }

    public void validate(Object object, Errors errors) {
//        ResearchStaffWrapper wrapper = (ResearchStaffWrapper) object;
//        PersonUser user = wrapper.getResearchStaff();
//        
//        if(StringUtils.getBlankIfNull(user.getLoginId()).equals("")){
//        	errors.reject("submision.errors");
//        }
//        // do it for old and new users. The search should be against remote research staff too
//        //for now the search is against the db only as searching remote causes stale object exception on ORacle. see CPR-578
//        
//        	if(user instanceof RemoteResearchStaff){ } else {
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