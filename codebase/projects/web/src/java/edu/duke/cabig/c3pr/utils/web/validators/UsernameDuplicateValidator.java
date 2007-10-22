package edu.duke.cabig.c3pr.utils.web.validators;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import gov.nih.nci.security.UserProvisioningManager;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Oct 19, 2007
 * Time: 10:46:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class UsernameDuplicateValidator implements Validator {

    ResearchStaffDao dao;
    private UserProvisioningManager userProvisioningManager;


    public boolean supports(Class aClass) {
        return aClass.isAssignableFrom(ResearchStaff.class);
    }

    public void validate(Object object, Errors errors) {
        ResearchStaff user = (ResearchStaff)object;

        //only do it for new users
        if(user.getLoginId()==null){

            if(dao.getByNciIdentifier(user.getNciIdentifier())!=null){
                errors.reject("duplicate.nci.id.error");
            }

            for (ContactMechanism cm : user.getContactMechanisms()) {
                if (cm.getType().equals(ContactMechanismType.EMAIL)) {
                    if(userProvisioningManager.getUser(cm.getValue())!=null){
                        errors.reject("duplicate.username.error");
                    }
                }
            }
        }

        if(user.getGroups().size()<1){
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
