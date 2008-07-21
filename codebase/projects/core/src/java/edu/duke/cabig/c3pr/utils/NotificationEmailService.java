package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyOrganization;

/**
 * Author: vGangoli Date: Nov 30, 2007
 */

public class NotificationEmailService {

    private MailSender mailSender;

    private SimpleMailMessage accountCreatedTemplateMessage;

//    PersonnelServiceImpl personnelServiceImpl;

    private Logger log = Logger.getLogger(StudyTargetAccrualNotificationEmail.class);

    
    /**
     * This method is reponsible for figuring out the email address from notifications and sending
     * out the notification email using Javamail.
     * 
     * @param study
     */
    public void sendEmail(Study study) {

        List<String> emailList = null;

        emailList = generateEmailList(study);
        for (String emailAddress : emailList) {
            try {
                SimpleMailMessage msg = new SimpleMailMessage(
                                this.accountCreatedTemplateMessage);
                msg.setSubject("Study Status Notification.");
                msg.setTo(emailAddress);
                msg.setText("This is to notify you that the study status for "
                                + study.getShortTitleText() + " has now changed to "
                                + study.getCoordinatingCenterStudyStatus().getDisplayName() + ".");
                log.debug("Trying to send study status change notification email");
                this.mailSender.send(msg);
            }
            catch (MailException e) {
                log.error("Could not send email due to  " + e.getMessage());
                // just log it for now
            }
        }
    }

    /**
     * This method generates a list of emails by getting the emailaddresses from all the email based
     * Recepients and Role based Recepients for a given Notification. The role based recepient only
     * has the role String...the corresponding personnel in that role for that study have to be
     * retrieved and their email addresses have to be addded to the finalList.
     */
    public List<String> generateEmailList(Study study) {
        return getEmailsFromRoleBasedRecipient(study);
    }

    public List<String> getEmailsFromRoleBasedRecipient(Study study) {
        List<StudyOrganization> studyOrgList = study.getStudyOrganizations();
        List<String> returnList = new ArrayList<String>();
//        List<C3PRUserGroupType> groupList = null;
        List<ResearchStaff> rStaffList = null;
        for (StudyOrganization so : studyOrgList) {
            if (so instanceof StudyCoordinatingCenter) {
                rStaffList = so.getHealthcareSite().getResearchStaffs();

                /*for (ResearchStaff rs : rStaffList) {
                    try {
                        groupList = personnelServiceImpl.getGroups(rs);
                    }
                    catch (C3PRBaseException e) {
                        log.error("StudyTargetAccrualNotificationAspect - personnelServiceImpl.getGroups():FAILED");
                        log.error(e.getMessage());
                    }
                     Handle the investigator code seperately 
                    if (groupList != null) {
                        for (C3PRUserGroupType group : groupList) {
//                            if (group.getCode().equalsIgnoreCase(rr.getRole())) {
                                returnList.addAll(getEmailAddressesFromResearchStaff(rs));
//                            }
                        }
                    }
                }*/
                
                for (ResearchStaff rs : rStaffList) {
                	returnList.addAll(getEmailAddressesFromResearchStaff(rs));
                }
            }
        }
        return returnList;
    }

    public List<String> getEmailAddressesFromResearchStaff(ResearchStaff rs) {

        List<String> returnList = new ArrayList<String>();
        for (ContactMechanism cm : rs.getContactMechanisms()) {
            if (cm.getType().equals(ContactMechanismType.EMAIL)) {
                returnList.add(cm.getValue());
            }
        }
        return returnList;
    }

    
    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public SimpleMailMessage getAccountCreatedTemplateMessage() {
        return accountCreatedTemplateMessage;
    }

    public void setAccountCreatedTemplateMessage(SimpleMailMessage accountCreatedTemplateMessage) {
        this.accountCreatedTemplateMessage = accountCreatedTemplateMessage;
    }

/*    public PersonnelServiceImpl getPersonnelServiceImpl() {
        return personnelServiceImpl;
    }

    public void setPersonnelServiceImpl(PersonnelServiceImpl personnelServiceImpl) {
        this.personnelServiceImpl = personnelServiceImpl;
    }*/
}
