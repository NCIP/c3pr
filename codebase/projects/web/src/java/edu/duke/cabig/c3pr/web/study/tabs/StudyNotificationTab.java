/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismBasedRecipient;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Author: Vinay Gangoli Date: Nov 26, 2007
 */
public class StudyNotificationTab extends StudyTab {

	private MessageSource c3prErrorMessages;
	
    public StudyNotificationTab() {
        super("Notifications", "Notifications", "study/study_notification");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map referenceDataForTab(HttpServletRequest request, StudyWrapper command) {
    	 Map<String, Object> refdata = super.referenceDataForTab(request,command);
        addConfigMapToRefdata(refdata, "notificationPersonnelRoleRefData");
        return refdata;
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest httpServletRequest, StudyWrapper wrapper,
                                        Errors errors) {

        for (PlannedNotification pn : wrapper.getStudy().getPlannedNotifications()) {
            for (ContactMechanismBasedRecipient cmbr : pn.getContactMechanismBasedRecipient()) {
                for (ContactMechanism cm : cmbr.getContactMechanisms()) {
                    cm.setType(ContactMechanismType.EMAIL);
                }
            }
        }
        super.postProcessOnValidation(httpServletRequest, wrapper, errors);
    }

    
	/* (non-Javadoc)
	 * @see gov.nih.nci.cabig.ctms.web.tabs.Tab#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(StudyWrapper wrapper, Errors errors) {
        List<PlannedNotification> plannedNotificationsList = wrapper.getStudy().getPlannedNotifications();
        PlannedNotification plannedNotification = null;
        try {
        	List<String> emailList = new ArrayList<String>();
        	List<String> roleList = new ArrayList<String>();
            for (int plannedNotificationsIndex = 0; plannedNotificationsIndex < plannedNotificationsList.size(); plannedNotificationsIndex++) {
            	plannedNotification = plannedNotificationsList.get(plannedNotificationsIndex);
            	emailList.clear();
            	roleList.clear();
            	//build list of emails for every plannedNotification
            	for(ContactMechanismBasedRecipient cmbr : plannedNotification.getContactMechanismBasedRecipient()){
            		emailList.add(cmbr.getContactMechanisms().get(0).getValue());
            	}	
            	for(String email:emailList){
            		if(emailList.indexOf(email) != emailList.lastIndexOf(email)){
            			//same email entered twice
            			//errors.reject("C3PR.STUDY.DUPLICATE.NOTIFICATION.EMAIL");
            			errors.rejectValue("study.plannedNotifications", new Integer(
        	                    getCode("C3PR.STUDY.DUPLICATE.NOTIFICATION.EMAIL")).toString(),
        	                    getMessageFromCode(getCode("C3PR.STUDY.DUPLICATE.NOTIFICATION.EMAIL"), null , null));
            			break;
            		}
            	}
        		//build list of roles for every plannedNotification
            	for(RoleBasedRecipient rbr : plannedNotification.getRoleBasedRecipient()){
            		roleList.add(rbr.getRole());
            	}
            	for(String role : roleList){
            		if(roleList.indexOf(role) != roleList.lastIndexOf(role)){
            			//same role entered twice
            			//errors.reject("C3PR.STUDY.DUPLICATE.NOTIFICATION.ROLE");
            			errors.rejectValue("study.plannedNotifications", new Integer(
        	                    getCode("C3PR.STUDY.DUPLICATE.NOTIFICATION.ROLE")).toString(),
        	                    getMessageFromCode(getCode("C3PR.STUDY.DUPLICATE.NOTIFICATION.ROLE"), null , null));
            			break;
            		}
            	}
            }
        }
        catch (Exception ex) {
            log.debug(ex.getMessage());
        }
	}

    public MessageSource getC3prErrorMessages() {
        return c3prErrorMessages;
    }

    public void setC3prErrorMessages(MessageSource errorMessages) {
        c3prErrorMessages = errorMessages;
    }

    public int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }

    public String getMessageFromCode(int code, Object[] params, Locale locale) {
        String msg = "";
        try {
            msg = c3prErrorMessages.getMessage(code + "", params, locale);
        }
        catch (NoSuchMessageException e) {
            try {
                msg = c3prErrorMessages.getMessage(-1 + "", null, null);
            }
            catch (NoSuchMessageException e1) {
                msg = "Exception Code property file missing";
            }
        }
        return msg;
    }
}
