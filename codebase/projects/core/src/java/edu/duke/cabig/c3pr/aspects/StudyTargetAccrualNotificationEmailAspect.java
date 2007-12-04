package edu.duke.cabig.c3pr.aspects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.EmailBasedRecepient;
import edu.duke.cabig.c3pr.domain.Notification;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RoleBasedRecepient;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.impl.PersonnelServiceImpl;

/**
 * Author: vGangoli
 * Date: Nov 30, 2007
 */
@Aspect
public class StudyTargetAccrualNotificationEmailAspect {

    private MailSender mailSender;
    private SimpleMailMessage accountCreatedTemplateMessage;
    PersonnelServiceImpl personnelServiceImpl;

    private Logger log = Logger.getLogger(StudyTargetAccrualNotificationEmailAspect.class);

    @AfterReturning("execution(* edu.duke.cabig.c3pr.service.impl.StudySubjectServiceImpl.registerSubject(..))" +
                    " && args(studySubject)")
    public void sendEmail(StudySubject studySubject){
    	
    	Study study = studySubject.getStudySite().getStudy();
    	int totalAccrual = calculateTotalAccrual(study);
    	List<String> emailList = null;  
    	
    	for (Notification nf : study.getNotifications()){
	    	if(totalAccrual == nf.getThreshold()){
	    		emailList = generateEmailList(study, nf);	    		
	    		for (String emailAddress : emailList){	        		
	    			try{
	    				SimpleMailMessage msg = new SimpleMailMessage(this.accountCreatedTemplateMessage);
	    				msg.setSubject("Study Threshold Notification.");
	    				msg.setTo(emailAddress);
	    				msg.setText("This is to notify you that the study accrual for " + study.getShortTitleText() +
	    							" has now reached " + totalAccrual + ".");
	    				log.debug("Trying to send study target accrual notification email");
	    				this.mailSender.send(msg);
	    			} catch (MailException e) {
	    				log.error("Could not send email due to  " + e.getMessage());
	    				//just log it for now
	    			}	    			
	    		}	    		
	    	}
    	}

    }
    
    //This method generates a list of emails by getting the emailaddresses from all the
    //email based Recepients and Role based Recepients for a given Notification.
    //The role based recepient only has the role String...the corresponding personnel in that role for that study 
    //have to be retrieved and their email addresses have to be addded to the finalList.
    public List<String> generateEmailList(Study study, Notification nf){
    	
    	List<String> finalList = new ArrayList<String>();
    	
    	for (EmailBasedRecepient er : nf.getEmailBasedRecepient()){
    		finalList.add(er.getEmailAddress());
    	}    	
    	for (RoleBasedRecepient rr : nf.getRoleBasedRecepient()){
    		finalList.addAll(getEmailsFromRoleBasedRecepient(study, rr));
    	}
    	
    	return finalList;
    }
    
    public List<String> getEmailsFromRoleBasedRecepient(Study study, RoleBasedRecepient rr){
    	List <StudySite> studySiteList = study.getStudySites();
    	List <String> returnList = new ArrayList<String>();
    	List <C3PRUserGroupType> groupList = null;
    	
    	for (StudySite ss : studySiteList){
    		for(StudyPersonnel sp : ss.getStudyPersonnel()){
    			try {
    				groupList = personnelServiceImpl.getGroups(sp.getResearchStaff());
    			} catch(C3PRBaseException e){
    				log.error(e.getMessage());
    			}
	    		if(groupList != null){
    				for(C3PRUserGroupType group : groupList){
	    				if(group.getCode().equalsIgnoreCase(rr.getRole())){
	    					returnList.addAll(getEmailAddressesFromResearchStaff(sp.getResearchStaff()));
	    				}
	    			}
	    		}
    		}    		
    	}    	
    	return returnList;
     }
    
    public List<String> getEmailAddressesFromResearchStaff(ResearchStaff rs){
    	
    	List <String>returnList = new ArrayList<String>();
    	for(ContactMechanism cm: rs.getContactMechanisms()){
    		if(cm.getType().equals(ContactMechanismType.EMAIL)){
    			returnList.add(cm.getValue());
    		}    		
    	}    	
    	return returnList;
    }
    

    //This method calculates the total number of subjects assigned to a study
    //by iterating through all the sites that are in the study.
    public int calculateTotalAccrual(Study study){
    	List<StudySite> studySitesList = study.getStudySites();
    	int totalAccrual = 0;
    	Iterator sslIter = studySitesList.iterator();
    	StudySite studySite;
    	while(sslIter.hasNext()){
    		studySite = (StudySite)sslIter.next();
    		//replace this with a "count-calculating HQL method" call.
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

