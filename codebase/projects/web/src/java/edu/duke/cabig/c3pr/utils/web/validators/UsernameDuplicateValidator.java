package edu.duke.cabig.c3pr.utils.web.validators;

import java.util.ArrayList;
import java.util.List;

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

        // do it for old and new users. The search should be against remote research staff too
        //for now the search is against the db only as searching remote causes stale object exception on ORacle. see CPR-578
        
        	if(object instanceof RemoteResearchStaff){ } else {
        		ResearchStaff researchStaff = dao.getByNciIdentifierFromLocal(user.getNciIdentifier());
        		if (researchStaff != null) {
					if (user.getId() == null) {
							errors.reject("duplicate.nci.id.error");

					} else if (!user.getId().equals(researchStaff.getId())) {
						errors.reject("duplicate.nci.id.error");
					}
				}
				for (ContactMechanism cm : user.getContactMechanisms()) {
                    if (cm.getType().equals(ContactMechanismType.EMAIL)) {
                    	List<ResearchStaff> researchStaffByEmail = new ArrayList<ResearchStaff>();
                    	researchStaffByEmail = dao.getByEmailAddressFromLocal(cm.getValue());
                    	if (researchStaffByEmail.size()>1){
                    		errors.reject("duplicate.username.error");
                    	} else if (researchStaffByEmail.size()>0 && researchStaffByEmail.get(0)!=null){
                    		if(user.getId()== null){
                    			errors.reject("duplicate.username.error");
                    		} else if (!user.getId().equals(researchStaffByEmail.get(0).getId())) {
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
