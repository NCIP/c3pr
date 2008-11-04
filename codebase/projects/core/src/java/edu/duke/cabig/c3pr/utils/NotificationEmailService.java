package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismBasedRecipient;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.impl.PersonnelServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;

/**
 * Author: vGangoli Date: Nov 30, 2007
 */

public class NotificationEmailService implements ApplicationContextAware {

    private MailSender mailSender;

    private SimpleMailMessage accountCreatedTemplateMessage;

    private Logger log = Logger.getLogger(NotificationEmailService.class);

    private HealthcareSiteDao healthcareSiteDao;
    
    private PersonnelServiceImpl personnelServiceImpl;
    
    private Configuration configuration;

    private ApplicationContext applicationContext;
    
    private String linkBack = null;
    
    public static final String STUDY_INVESTIGATOR = "SI";
    
    public static final String LINK_BACK_SUBJECT = "Notification from C3PR";
    
    public static final String LINK_BACK_TEXT = "To view this message click on https://localhost:8443/c3pr/pages/admin/viewInbox   You may be asked to login.";
    /**
     * This method is reponsible for figuring out the email address from notifications and sending
     * out the notification email using Javamail.
     * This is only used for REPORT BASED EMAILS
     * 
     * @param study*/
    
    public void sendReportEmail(RecipientScheduledNotification recipientScheduledNotification) throws MessagingException {
    	log.debug(this.getClass().getName() + ": Entering sendReportEmail()");
    	List<String> emailList = null;
        //composing the message to be sent out
        emailList = generateEmailList(recipientScheduledNotification);
        
        //logging the email details for testing purposes
        log.debug("********************  Email Address List *****************");
        for (String emailAddress : emailList) {
        	log.debug(emailAddress + "    ");
        }
        log.debug("*************** Email Title *****************");
        log.debug(recipientScheduledNotification.getScheduledNotification().getTitle());
        log.debug("*************** Email Content ***************");
        log.debug(recipientScheduledNotification.getScheduledNotification().getMessage());
        //logging the email details for testing purposes
        
        
        for (String emailAddress : emailList) {
        	
        	//TO DO: Must move this to be read in from an external file
        	Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.host", "smtp.gmail.com");
            props.setProperty("mail.port", "465");
            props.setProperty("mail.user", "c3prproject@gmail.com");
            props.setProperty("mail.password", "semanticbits");
            props.setProperty("mail.smtp.debug", "true");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");
            //TO DO: Must move this to be read in from an external file
            
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(true);
            Transport transport = mailSession.getTransport("smtp");

            MimeMessage message = new MimeMessage(mailSession);
            
            if(linkBack.equalsIgnoreCase("true")){
            	message.setSubject(LINK_BACK_SUBJECT);
                message.setFrom(new InternetAddress("c3prproject@gmail.com"));
                
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(LINK_BACK_TEXT, "text/html");

                Multipart multiPart = new MimeMultipart();
                multiPart.addBodyPart(mimeBodyPart);
                
                message.setContent(multiPart);
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            } else {
            	message.setSubject(recipientScheduledNotification.getScheduledNotification().getTitle());
                message.setFrom(new InternetAddress("c3prproject@gmail.com"));
                
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(recipientScheduledNotification.getScheduledNotification().getMessage(), "text/html");

                Multipart multiPart = new MimeMultipart();
                multiPart.addBodyPart(mimeBodyPart);
                
                message.setContent(multiPart);
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            }
            
            transport.connect("smtp.gmail.com", "c3prproject@gmail.com", "semanticbits");
            message.saveChanges();
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
        }
    	
        log.debug(this.getClass().getName() + ": Exiting sendReportEmail()");
    } 


    
    
    /**
     * This method is reponsible for figuring out the email address from notifications and sending
     * out the notification email using Javamail.
     * 
     * @param study
     */
    public void sendEmail(RecipientScheduledNotification recipientScheduledNotification) throws MailException {
    	log.debug(this.getClass().getName() + ": Entering sendEmail()");
        List<String> emailList = null;
        //composing the message to be sent out
        emailList = generateEmailList(recipientScheduledNotification);
        
        //logging the email details for testing purposes
        log.debug("********************  Email Address List *****************");
        for (String emailAddress : emailList) {
        	log.debug(emailAddress + "    ");
        }
        log.debug("*************** Email Title *****************");
        log.debug(recipientScheduledNotification.getScheduledNotification().getTitle());
        log.debug("*************** Email Content ***************");
        log.debug(recipientScheduledNotification.getScheduledNotification().getMessage());
        //logging the email details for testing purposes
        
        for (String emailAddress : emailList) {
        SimpleMailMessage msg = new SimpleMailMessage(this.accountCreatedTemplateMessage);
	        if(linkBack.equalsIgnoreCase("true")){
	        	msg.setSubject(LINK_BACK_SUBJECT);
                msg.setTo(emailAddress);
                msg.setText(LINK_BACK_TEXT);
	        } else {
                msg.setSubject(recipientScheduledNotification.getScheduledNotification().getTitle());
                msg.setTo(emailAddress);
                msg.setText(recipientScheduledNotification.getScheduledNotification().getMessage());
	        }
	        log.debug("Trying to send " + recipientScheduledNotification.getScheduledNotification().getTitle()+ " notification email");
	        this.mailSender.send(msg);
        }
        log.debug(this.getClass().getName() + ": Exiting sendEmail()");
    }

    /**
     * This method generates a list of emails by getting the emailaddresses from all the email based
     * Recepients and Role based Recepients for a given Notification. The role based recepient only
     * has the role String...the corresponding personnel in that role for that study have to be
     * retrieved and their email addresses have to be addded to the finalList.
     */
    public List<String> generateEmailList(RecipientScheduledNotification recipientScheduledNotification) {
    	log.debug(this.getClass().getName() + ": Entering generateEmailList()");
        List<String> emailList = new ArrayList<String>();
    	emailList.addAll(getEmailsFromRoleBasedRecipient(recipientScheduledNotification));
    	emailList.addAll(getEmailsFromUserBasedRecipient(recipientScheduledNotification));
    	emailList.addAll(getEmailsFromContactMechanismBasedRecipient(recipientScheduledNotification));
    	removeDuplicates(emailList);
    	log.debug(this.getClass().getName() + ": Exiting generateEmailList()");
    	return emailList;
        
    }

    public List<String> getEmailsFromContactMechanismBasedRecipient(RecipientScheduledNotification recipientScheduledNotification){
    	log.debug(this.getClass().getName() + ": Entering getEmailsFromContactMechanismBasedRecipient()");
        List<String> returnList = new ArrayList<String>();
        
        if(recipientScheduledNotification.getRecipient() instanceof ContactMechanismBasedRecipient){
        	ContactMechanismBasedRecipient cmbr = (ContactMechanismBasedRecipient)recipientScheduledNotification.getRecipient();
    		if(cmbr.getContactMechanisms() != null){
    			for(ContactMechanism cm: cmbr.getContactMechanisms()){
    				if(cm.getType() != null && cm.getType().equals(ContactMechanismType.EMAIL)){
    					returnList.add(cm.getValue());
    				}
    			}
    		}
    	}
        log.debug(this.getClass().getName() + ": exiting getEmailsFromContactMechanismBasedRecipient()");
        return returnList;
    }
    
    
    public List<String> getEmailsFromRoleBasedRecipient(RecipientScheduledNotification recipientScheduledNotification) {
    	
    	log.debug(this.getClass().getName() + ": Entering getEmailsFromRoleBasedRecipient()");
    	List<String> returnList = new ArrayList<String>();
        
        /* this portion gets all the users for the related organization(defaults to the hosting org).
         * However this is replaced by the new logic which utilizes the studySite in the scheduledNotification
         * to retrieve the personnel/investigators assigned to the study.
        List<HealthcareSite> hcsList = getRelatedHelathcaresite(recipientScheduledNotification);
        if (hcsList == null  || hcsList.size() == 0){
        	//default to hosting Site if no related site is found
        	rStaffList = getHostingHealthcareSite().getResearchStaffs();
        } else {
        	//rStaff is not acccessible thru the hcsList as its throws lazyInitException...hence get the id and use the dao instead.
        	Integer hcsId = hcsList.get(0).getId();
        	HealthcareSite hcs = healthcareSiteDao.getById(hcsId);
        	rStaffList = hcs.getResearchStaffs();
        }*/
        
        if(recipientScheduledNotification.getRecipient() instanceof RoleBasedRecipient){
        	RoleBasedRecipient rbr = (RoleBasedRecipient)recipientScheduledNotification.getRecipient();
        	
            List<C3PRUserGroupType> groupList = null;
            List<ResearchStaff> rStaffList = new ArrayList<ResearchStaff>();
            
            //Incase the scheduledNotification does not have a studyOrg related to it(legacy data) then return empty list.
            //this means that roleBasedRecipients for pre-existing ScheduledNotifications(reports) will be ignored.
            if(recipientScheduledNotification.getScheduledNotification().getStudyOrganization() == null){
            	log.debug(this.getClass().getName() + ": ScheduledNotification has no studyOrgs associated with it. This must be an old ScheduledNotification.");
            	return returnList;
            }
            
            //Handle the researchStaff first
            for(StudyPersonnel sp: recipientScheduledNotification.getScheduledNotification().getStudyOrganization().getStudyPersonnel()){
            	rStaffList.add(sp.getResearchStaff());
            }
            
            for (ResearchStaff rs : rStaffList) {
                try {
                    groupList = personnelServiceImpl.getGroups(rs);
                }
                catch (C3PRBaseException e) {
                    log.error("NotificationEmailService - personnelServiceImpl.getGroups():FAILED" + e.getMessage());
                }
                if (groupList != null) {
                    for (C3PRUserGroupType group : groupList) {
                		if(rbr.getRole().equalsIgnoreCase(group.getCode())){
                                returnList.addAll(getEmailAddressesFromResearchStaff(rs));
                        }
                    }
                }
            }
            
            //Handle the investigators seperately
    		if(rbr.getRole().equalsIgnoreCase(STUDY_INVESTIGATOR)){
    			for(StudyInvestigator si: recipientScheduledNotification.getScheduledNotification().getStudyOrganization().getStudyInvestigators()){
                    returnList.addAll(getEmailAddressesFromInvestigator(si.getHealthcareSiteInvestigator().getInvestigator()));
    			}
            }
        }
        
        log.debug(this.getClass().getName() + ": exiting getEmailsFromRoleBasedRecipient()");
        return returnList;
    }

    //merge this with getEmailAddressesFromInvestigator
    public List<String> getEmailAddressesFromResearchStaff(ResearchStaff rs) {
        List<String> returnList = new ArrayList<String>();
        for (ContactMechanism cm : rs.getContactMechanisms()) {
            if (cm.getType().equals(ContactMechanismType.EMAIL)) {
                returnList.add(cm.getValue());
            }
        }
        return returnList;
    }
    
    public List<String> getEmailAddressesFromInvestigator(Investigator inv) {
        List<String> returnList = new ArrayList<String>();
        for (ContactMechanism cm : inv.getContactMechanisms()) {
            if (cm.getType().equals(ContactMechanismType.EMAIL)) {
                returnList.add(cm.getValue());
            }
        }
        return returnList;
    }

    
    public List<String> getEmailsFromUserBasedRecipient(RecipientScheduledNotification recipientScheduledNotification){
    	log.debug(this.getClass().getName() + ": Entering getEmailsFromUserBasedRecipient()");
    	List<String> returnList = new ArrayList<String>();
    	
    	if(recipientScheduledNotification.getRecipient() instanceof UserBasedRecipient){
    		UserBasedRecipient ubr = (UserBasedRecipient)recipientScheduledNotification.getRecipient();
    		if(ubr.getResearchStaff() != null){
    			returnList.addAll(getEmailAddressesFromResearchStaff(ubr.getResearchStaff()));
    		}
            if(ubr.getInvestigator() != null){
            	returnList.addAll(getEmailAddressesFromInvestigator(ubr.getInvestigator()));
            }
    	}
	    	
    	log.debug(this.getClass().getName() + ": exiting getEmailsFromUserBasedRecipient()");
    	return returnList;
    }
    
    /*
     * removes the muktiple occurrences of any emails addresses that may result in emails being sent out twice
     * to the same recipient.
     */
    private void removeDuplicates(List<String> emailList){
    	Set <String> set = new HashSet<String>();
    	set.addAll(emailList);
    	emailList.clear();
    	emailList.addAll(set);
    }
    
    
    /* Commented out ...was previously used by getEmailsFromRoleBasedRecipient();
    private List<HealthcareSite> getRelatedHelathcaresite(RecipientScheduledNotification rsn){
    	SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean("sessionFactory");
		org.hibernate.Session session = sessionFactory.openSession(sessionFactory.getCurrentSession().connection());
		session.setFlushMode(FlushMode.MANUAL);
		
		List<HealthcareSite> result = null; 
        try {
          //Query query =  session.createQuery("select p from PlannedNotification p, HealthcareSite o where p.id = o.plannedNotificationsInternal.id and o.nciInstituteCode = ?");
          Query query =  session.createQuery("select o from HealthcareSite o, PlannedNotification p where " +
          									 "p.scheduledNotificationsInternal.id = ? and o.plannedNotificationsInternal.id = p.id");
          query.setInteger(0, rsn.getScheduledNotification().getId());
          result = query.list();
        }catch(Exception e){
        	log.error(e.getMessage());
        }
        finally{
        	session.close();
        }
        return result;
    }*/
    
    
    public HealthcareSite getHostingHealthcareSite(){
    	return healthcareSiteDao.getByNciInstituteCode(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE));
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

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public PersonnelServiceImpl getPersonnelServiceImpl() {
		return personnelServiceImpl;
	}

	public void setPersonnelServiceImpl(PersonnelServiceImpl personnelServiceImpl) {
		this.personnelServiceImpl = personnelServiceImpl;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}




	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}




	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}




	public String getLinkBack() {
		return linkBack;
	}




	public void setLinkBack(String linkBack) {
		this.linkBack = linkBack;
	}

}
