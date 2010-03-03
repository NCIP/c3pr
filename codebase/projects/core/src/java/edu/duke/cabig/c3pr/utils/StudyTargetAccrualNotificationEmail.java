package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismBasedRecipient;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.impl.PersonnelServiceImpl;

/**
 * Author: vGangoli Date: Nov 30, 2007
 */
@Aspect
public class StudyTargetAccrualNotificationEmail {

    private MailSender mailSender;

    private SimpleMailMessage accountCreatedTemplateMessage;

    PersonnelServiceImpl personnelServiceImpl;

    private Logger log = Logger.getLogger(StudyTargetAccrualNotificationEmail.class);

    public static final String STUDY_INVESTIGATOR = "SI";
    /**
     * This method is responsible for figuring out the email address from notifications and sending
     * out the notification email using Javamail.
     *
     * @param studySubject
     */
    public void sendEmail(StudySubject studySubject) {

        Study study = studySubject.getStudySite().getStudy();
        int totalAccrual = calculateTotalAccrual(study);
        List<String> emailList = null;

        for (PlannedNotification nf : study.getPlannedNotifications()) {
            if (totalAccrual >= nf.getStudyThreshold()) {
                emailList = generateEmailList(study, nf);
                for (String emailAddress : emailList) {
                    try {
                    	if(emailAddress != null){
                    		SimpleMailMessage msg = new SimpleMailMessage(
                                    this.accountCreatedTemplateMessage);
		                    msg.setSubject("Study Threshold Notification.");
		                    msg.setTo(emailAddress);
		                    msg.setText("This is to notify you that the study accrual for "
										+ study.getShortTitleText() + " has now reached " + totalAccrual + ".");
							log.debug("Trying to send study target accrual notification email to "+emailAddress);
		
		                    this.mailSender.send(msg);
                    	}
                    }
                    catch (MailException e) {
                        log.error("Could not send email due to  " + e.getMessage());
                        // just log it for now
                    }
                }
            }
        }

    }

    /**
     * This method generates a list of emails by getting the emailaddresses from all the email based
     * Recepients and Role based Recepients for a given Notification. The role based recepient only
     * has the role String...the corresponding personnel in that role for that study have to be
     * retrieved and their email addresses have to be addded to the finalList.
     */
    public List<String> generateEmailList(Study study, PlannedNotification nf) {

        List<String> finalList = new ArrayList<String>();
        for (ContactMechanismBasedRecipient cmbr : nf.getContactMechanismBasedRecipient()) {
        	for (ContactMechanism cm : cmbr.getContactMechanisms()) {
        		if(cm.getType().getCode().equalsIgnoreCase(ContactMechanismType.EMAIL.getCode())){
        			finalList.add(cm.getValue());
        		}
            }
        }
        for (UserBasedRecipient er : nf.getUserBasedRecipient()) {
            finalList.add(er.getEmailAddress());
        }
        for (RoleBasedRecipient rr : nf.getRoleBasedRecipient()) {
            finalList.addAll(getEmailsFromRoleBasedRecipient(study, rr));
        }

        return finalList;
    }

    public List<String> getEmailsFromRoleBasedRecipient(Study study, RoleBasedRecipient rr) {
        List<StudyOrganization> studyOrgList = study.getStudyOrganizations();
        List<String> returnList = new ArrayList<String>();
        List<C3PRUserGroupType> groupList = null;
        List<ResearchStaff> rStaffList = null;
        for (StudyOrganization so : studyOrgList) {
            if (so instanceof StudyCoordinatingCenter) {
                rStaffList = so.getHealthcareSite().getResearchStaffs();

                for (ResearchStaff rs : rStaffList) {
                    try {
                        groupList = personnelServiceImpl.getGroups(rs);
                    }
                    catch (C3PRBaseException e) {
                        log
                                        .error("StudyTargetAccrualNotificationAspect - personnelServiceImpl.getGroups():FAILED");
                        log.error(e.getMessage());
                    }
                    /* Handle the investigator code seperately */
                    if (groupList != null) {
                        for (C3PRUserGroupType group : groupList) {
                            if (group.getCode().equalsIgnoreCase(rr.getRole())) {
                                returnList.addAll(getEmailAddressesFromResearchStaff(rs));
                            }
                            //Handling the investigators
							if(rr.getRole().equalsIgnoreCase(STUDY_INVESTIGATOR)){
								for(HealthcareSiteInvestigator hcsi: so.getHealthcareSite().getHealthcareSiteInvestigators()){
									returnList.addAll(getEmailAddressesFromInvestigator(hcsi.getInvestigator()));
								}
                            }
                        }
                    }
                }
            }

        }
        return returnList;
    }

    public List<String> getEmailAddressesFromResearchStaff(ResearchStaff rs) {

        List<String> returnList = new ArrayList<String>();
//        for (ContactMechanism cm : rs.getContactMechanisms()) {
//            if (cm.getType().equals(ContactMechanismType.EMAIL)) {
//                returnList.add(cm.getValue());
//            }
//        }
        returnList.add(rs.getEmail());
        return returnList;
    }


	public List<String> getEmailAddressesFromInvestigator(Investigator inv) {
		List<String> returnList = new ArrayList<String>();
//		for (ContactMechanism cm : inv.getContactMechanisms()) {
//			if (cm.getType().equals(ContactMechanismType.EMAIL)) {
//				returnList.add(cm.getValue());
//			}
//		}
		returnList.add(inv.getEmail());
		return returnList;
    }

    /**
     * This method calculates the total number of subjects assigned to a study by iterating through
     * all the sites that are in the study.
     */
    public int calculateTotalAccrual(Study study) {
        List<StudySite> studySitesList = study.getStudySites();
        int totalAccrual = 0;
        Iterator sslIter = studySitesList.iterator();
        StudySite studySite;
        while (sslIter.hasNext()) {
            studySite = (StudySite) sslIter.next();
            // replace this with a "count-calculating HQL method" call.
            totalAccrual += studySite.getStudySubjects().size();
        }
        return totalAccrual;
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

    public PersonnelServiceImpl getPersonnelServiceImpl() {
        return personnelServiceImpl;
    }

    public void setPersonnelServiceImpl(PersonnelServiceImpl personnelServiceImpl) {
        this.personnelServiceImpl = personnelServiceImpl;
    }
}
