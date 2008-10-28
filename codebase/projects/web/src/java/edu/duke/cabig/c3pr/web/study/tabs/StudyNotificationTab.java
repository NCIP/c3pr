package edu.duke.cabig.c3pr.web.study.tabs;

import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismBasedRecipient;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Author: Vinay Gangoli Date: Nov 26, 2007
 */
public class StudyNotificationTab extends StudyTab {

    public StudyNotificationTab() {
        super("Study Notifications", "Study Notifications", "study/study_notification");
    }

    @Override
    public Map referenceData(HttpServletRequest request, StudyWrapper command) {
        Map<String, Object> refdata = super.referenceData(command);
        addConfigMapToRefdata(refdata, "notificationPersonnelRoleRefData");
        boolean isAdmin = isAdmin();
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

}