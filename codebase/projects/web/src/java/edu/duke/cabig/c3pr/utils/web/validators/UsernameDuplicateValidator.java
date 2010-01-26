package edu.duke.cabig.c3pr.utils.web.validators;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Oct 19, 2007 Time: 10:46:54 AM To change this
 * template use File | Settings | File Templates.
 */
public class UsernameDuplicateValidator implements Validator {

	private CSMUserRepository csmUserRepository;
	
    public boolean supports(Class aClass) {
        //return aClass.isAssignableFrom(ResearchStaff.class);
    	return ResearchStaff.class.isAssignableFrom(aClass);
    }

    public void validate(Object object, Errors errors) {
        ResearchStaff user = (ResearchStaff) object;

        // do it for old and new users. The search should be against remote research staff too
        //for now the search is against the db only as searching remote causes stale object exception on ORacle. see CPR-578
        
        	if(object instanceof RemoteResearchStaff){ } else {
        		ResearchStaff researchStaffByEmail = null;
            	try {
            		//using login id. Since the username check should only happen in create flow and not in module flow
            		//the login id check will work.
					if(csmUserRepository.getUserByName(user.getLoginId()) != null){
						errors.reject("duplicate.username.error");
					}
				} catch (RuntimeException e) {
					// this means user does not exist
				}
        	}

        if (user.getGroups() != null && user.getGroups().size() < 1) {
            errors.reject("Please select atleast 1 group for user");
        }
    }

    @Required
	public void setCsmUserRepository(CSMUserRepository csmUserRepository) {
		this.csmUserRepository = csmUserRepository;
	}
}