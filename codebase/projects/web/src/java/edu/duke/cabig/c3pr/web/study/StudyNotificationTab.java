package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismBasedRecipient;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.Study;

/**
 * Author: Vinay Gangoli Date: Nov 26, 2007
 */
public class StudyNotificationTab extends StudyTab {

    public StudyNotificationTab() {
        super("Notifications", "Notifications", "study/study_notification");
    }

    @Override
    public Map referenceData(HttpServletRequest request, Study study) {
        Map<String, Object> refdata = super.referenceData(study);
        addConfigMapToRefdata(refdata, "notificationPersonnelRoleRefData");
        boolean isAdmin = isAdmin();

        if ((request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow")
                        .toString().equals("true"))
                        || (request.getAttribute("editFlow") != null && request.getAttribute(
                                        "editFlow").toString().equals("true"))) {
            if (request.getSession().getAttribute(DISABLE_FORM_NOTIFICATION) != null && !isAdmin) {
                refdata.put("disableForm", request.getSession().getAttribute(
                                DISABLE_FORM_NOTIFICATION));
            }
            else {
                refdata.put("disableForm", new Boolean(false));
                refdata.put("mandatory", "true");
            }
        }
        return refdata;
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest httpServletRequest, Study study,
                     Errors errors) {
    	
    	for(PlannedNotification pn: study.getPlannedNotifications()){
    		for(ContactMechanismBasedRecipient cmbr: pn.getContactMechanismBasedRecipient()){
    			for(ContactMechanism cm: cmbr.getContactMechanisms()){
    				cm.setType(ContactMechanismType.EMAIL);
    			}
    		}
    	}
        super.postProcessOnValidation(httpServletRequest, study, errors);
    }

}