package edu.duke.cabig.c3pr.utils.web.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import gov.nih.nci.security.UserProvisioningManager;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Oct 19, 2007 Time: 10:46:54 AM To change this
 * template use File | Settings | File Templates.
 */
public class UsernameDuplicateValidator implements Validator {

    ResearchStaffDao dao;

    private UserProvisioningManager userProvisioningManager;

    public boolean supports(Class aClass) {
        //return aClass.isAssignableFrom(ResearchStaff.class);
    	return ResearchStaff.class.isAssignableFrom(aClass);
    }

    public void validate(Object object, Errors errors) {
        ResearchStaff user = (ResearchStaff) object;

        // only do it for new users
        if (user.getLoginId() == null) {
        	if(object instanceof RemoteResearchStaff){
        		
        	} else {
        		if (dao.getByNciIdentifier(user.getNciIdentifier()) != null) {
                    errors.reject("duplicate.nci.id.error");
                }

                for (ContactMechanism cm : user.getContactMechanisms()) {
                    if (cm.getType().equals(ContactMechanismType.EMAIL)) {
                        if (userProvisioningManager.getUser(cm.getValue()) != null) {
                            errors.reject("duplicate.username.error");
                        }
                    }
                }
        	}
        }

        if (user.getGroups() != null && user.getGroups().size() < 1) {
            errors.reject("Please select atleast 1 group for user");
        }
    }

    public UserProvisioningManager getUserProvisioningManager() {
        return userProvisioningManager;
    }

    public void setUserProvisioningManager(UserProvisioningManager userProvisioningManager) {
        this.userProvisioningManager = userProvisioningManager;
    }

    public ResearchStaffDao getDao() {
        return dao;
    }

    public void setDao(ResearchStaffDao dao) {
        this.dao = dao;
    }
}
