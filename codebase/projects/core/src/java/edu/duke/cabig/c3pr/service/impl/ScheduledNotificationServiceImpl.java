package edu.duke.cabig.c3pr.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import edu.duke.cabig.c3pr.constants.EmailNotificationDeliveryStatusEnum;
import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.dao.ScheduledNotificationDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.ContactMechanismBasedRecipient;
import edu.duke.cabig.c3pr.domain.Correspondence;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.service.ScheduledNotificationService;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.IdentifierGenerator;
import edu.duke.cabig.c3pr.utils.StringUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Author: vGangoli Date: Nov 30, 2007
 */

public class ScheduledNotificationServiceImpl implements ScheduledNotificationService, ApplicationContextAware {

    private Logger log = Logger.getLogger(ScheduledNotificationServiceImpl.class);
    
    private IdentifierGenerator identifierGenerator;
    
    private ScheduledNotificationDao scheduledNotificationDao;
    
    private StudySubjectDao studySubjectDao;
    
    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	public void setScheduledNotificationDao(
			ScheduledNotificationDao scheduledNotificationDao) {
		this.scheduledNotificationDao = scheduledNotificationDao;
	}

	private String c3prURL = null;

    public void setIdentifierGenerator(IdentifierGenerator identifierGenerator) {
		this.identifierGenerator = identifierGenerator;
	}
    
    @Required
	public void setC3prURL(String c3prURL) {
		this.c3prURL = c3prURL;
	}

	ApplicationContext applicationContext;
    
    /* Study Status Changed case     */
    public Integer saveScheduledNotification(PlannedNotification plannedNotification, Study study) {
    	String composedMessage = applyRuntimeReplacementsForEmailMessage(plannedNotification.getMessage(), study.buildMapForNotification());
    	return saveScheduledNotification(plannedNotification, composedMessage, study.getStudyOrganizations(),null);
    }
    
    /* StudySite Status Changed case     */
    public Integer saveScheduledNotification(PlannedNotification plannedNotification, StudySite studySite) {
    	String composedMessage = applyRuntimeReplacementsForEmailMessage(plannedNotification.getMessage(), studySite.buildMapForNotification());
    	List<StudyOrganization> soList = new ArrayList<StudyOrganization>();
    	soList.add(studySite);
    	return saveScheduledNotification(plannedNotification, composedMessage, soList,null);
    }   
    
    /* New Registration case     */
    public Integer saveScheduledNotification(PlannedNotification plannedNotification, StudySubject studySubject) {
    	String composedMessage = applyRuntimeReplacementsForEmailMessage(plannedNotification.getMessage(), studySubject.buildMapForNotification());
    	List<StudyOrganization> soList = new ArrayList<StudyOrganization>();
    	soList.add(studySubject.getStudySite());
    	return saveScheduledNotification(plannedNotification, composedMessage, soList,null);
    } 
    
    /* Update Master Subject case  */
    public Integer saveScheduledNotification(PlannedNotification plannedNotification, Participant participant) {
    	List<ScheduledNotification> scheduledNotificationsForThisSubject = new ArrayList<ScheduledNotification>();
    	Boolean isANotificationAlreadyScheduledForToday = false;
    	scheduledNotificationsForThisSubject = scheduledNotificationDao.getByEventId(participant.getC3PRSystemSubjectIdentifier().getValue());
    	for(ScheduledNotification scheduledNotification: scheduledNotificationsForThisSubject){
    		Date dateScheduled = DateUtil.getUtilDateFromString(DateUtil.formatDate(scheduledNotification.getDateSent(), "MM/dd/yyyy"), "MM/dd/yyyy");
    		Date currentDate = DateUtil.getUtilDateFromString(DateUtil.formatDate(new Date(), "MM/dd/yyyy"), "MM/dd/yyyy");
    		if (!dateScheduled.before(currentDate) && !dateScheduled.after(currentDate)){
    			isANotificationAlreadyScheduledForToday = true;
    			break;
    		}
    	}
    	if (isANotificationAlreadyScheduledForToday){	
    		return null;
    	}
    	String composedMessage =  "To view this subject click on " + c3prURL + "/pages/personAndOrganization/participant/viewParticipant?" +identifierGenerator.createParameterString(participant.getC3PRSystemSubjectIdentifier());
    	List<StudyOrganization> soList = new ArrayList<StudyOrganization>();
    	for(StudySubjectDemographics studySubjectDemographics:participant.getStudySubjectDemographics()){
    		for(StudySubject registration: studySubjectDemographics.getRegistrations()){
    			soList.add(registration.getStudySite());
    		}
    	}
    	return saveScheduledNotification(plannedNotification, composedMessage, soList,participant.getC3PRSystemSubjectIdentifier().getValue());
    } 
    
    // TODO to be fixed
    /* Update Correspondence case  */
    public Integer saveScheduledNotification(PlannedNotification plannedNotification, Correspondence correspondence) {
    	StudySubject studySubject = studySubjectDao.getByCorrespondenceId(correspondence.getId().intValue());
    	
    	StringBuilder sb = new StringBuilder("Follow up needed for subject ");
       	if(studySubject != null){
       		
       		plannedNotification.setTitle("Follow up needed for subject " + studySubject.getStudySubjectDemographics().getFullName()+ " on study " +
       				studySubject.getStudySite().getStudy().getShortTitleText());
	    	sb.append(studySubject.getStudySubjectDemographics().getFullName());
	    	sb.append(" (");
	    	sb.append(studySubject.getStudySubjectDemographics().getPrimaryIdentifierValue());
	    	sb.append(" )");
	    	sb.append("\n on study ");
	    	sb.append(studySubject.getStudySite().getStudy().getShortTitleText());
	    	sb.append(" at site ");
	    	sb.append(studySubject.getStudySite().getHealthcareSite().getName());
       	}
    	sb.append("\n\n Correspondence Details"); 
    	sb.append("\n Type: ");
    	sb.append( correspondence.getType().getCode());
    	sb.append( "\n Date: ");
    	sb.append(correspondence.getTimeStr());
    	sb.append("\n Start time: ");
    	sb.append(correspondence.getStartTimeStr());
    	sb.append("\n End time: ");
    	sb.append(correspondence.getEndTimeStr());
    	sb.append("\n Purpose: ");
    	sb.append(correspondence.getPurpose().getCode());
    	sb.append("\n Action: ");
    	sb.append(correspondence.getAction());
    	sb.append("\n Resolved: ");
    	if(correspondence.getResolved()){
    		sb.append("Yes");
    	}else{
    		sb.append("No");
    	}
    	sb.append("\n Follow up needed: ");
    	if(correspondence.getFollowUpNeeded()){
    		sb.append("Yes");
    	}else{
    		sb.append("No");
    	}
    	sb.append("\n Description: ");
    	sb.append(correspondence.getText());
    	
    	List<StudyOrganization> soList = new ArrayList<StudyOrganization>();
    	ScheduledNotification scheduledNotification = null;
    	scheduledNotification = addScheduledNotification(plannedNotification, sb.toString(), soList,null);
    	log.debug(this.getClass().getName() + ": Exiting saveScheduledNotification()");
    	if(scheduledNotification != null){
	    	scheduledNotificationDao.save(scheduledNotification);
	    	return scheduledNotification.getId();
    	}
    	
    	log.error( this.getClass().getName() +"saveScheduledNotification(): ScheduledNotification was not saved successfully");
		return 0;
    } 
    
    
    /* Generic save method called by all the above methods to save the ScheduledNotifications    */
    public synchronized Integer saveScheduledNotification(PlannedNotification plannedNotification, String composedMessage, List<StudyOrganization> ssList,String eventId){
    	log.debug(this.getClass().getName() + ": Entering saveScheduledNotification()");
    	ScheduledNotification scheduledNotification = null;
    	
    	//Creating a new session to save the scheduled notifications to avoid conflicts with the
    	//CurrentSession (whose flush initiated this interceptor call in the first place).
    	SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean("notificationSessionFactory");
    	Session session = sessionFactory.openSession();
		session.setFlushMode(FlushMode.COMMIT);
    	try{
    			session.update(plannedNotification);
    		
    		// for updating master subject notification event, planned notification is not associated to a healthcare site
    		if(plannedNotification.getHealthcareSite()!=null){
    			session.update(plannedNotification.getHealthcareSite());
    		}
        	//generating and saving the ScheduledNotification
        	scheduledNotification = addScheduledNotification(plannedNotification, composedMessage, ssList,eventId);
        	session.saveOrUpdate(plannedNotification);
        	session.flush();
    	}catch(Exception e){
    		log.error(e.getMessage());
    	}finally{
    		session.close();
    	}
		log.debug(this.getClass().getName() + ": Exiting saveScheduledNotification()");
		if(scheduledNotification != null){
		    return scheduledNotification.getId();
		}else{
			log.error( this.getClass().getName() +"saveScheduledNotification(): ScheduledNotification was not saved successfully");
			return 0;
		}
    }
    
    /**
     * Adds the scheduled notification.
     * 
     * @param plannedNotification the planned notification
     * @param composedMessage the composed message
     * @param soList the so list
     * 
     * @return the scheduled notification
     */
    public ScheduledNotification addScheduledNotification(PlannedNotification plannedNotification, String composedMessage, List<StudyOrganization> soList,String eventId){
    	
    	ScheduledNotification scheduledNotification = new ScheduledNotification();
    	scheduledNotification.setEventId(eventId);
    	plannedNotification.getScheduledNotifications().add(scheduledNotification);
    	scheduledNotification.setDateSent(new Date());
		scheduledNotification.setMessage(composedMessage);
		scheduledNotification.setTitle(plannedNotification.getTitle());
    	RecipientScheduledNotification rsn; 
    	for(RoleBasedRecipient rbr: plannedNotification.getRoleBasedRecipient()){
    		rsn = new RecipientScheduledNotification();
    		rsn.setRecipient(rbr);
    		rsn.setIsRead(Boolean.FALSE);
    		rsn.setScheduledNotification(scheduledNotification);
    		rsn.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.PENDING);
    		scheduledNotification.getRecipientScheduledNotification().add(rsn);
    	}
    	
    	for(UserBasedRecipient ubr: plannedNotification.getUserBasedRecipient()){
    		rsn = new RecipientScheduledNotification();
    		rsn.setRecipient(ubr);
    		rsn.setIsRead(Boolean.FALSE);
    		rsn.setScheduledNotification(scheduledNotification);
    		rsn.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.PENDING);
    		scheduledNotification.getRecipientScheduledNotification().add(rsn);
    	}
    	
    	for(ContactMechanismBasedRecipient cmbr: plannedNotification.getContactMechanismBasedRecipient()){
    		rsn = new RecipientScheduledNotification();
    		rsn.setRecipient(cmbr);
    		rsn.setIsRead(Boolean.FALSE);
    		rsn.setScheduledNotification(scheduledNotification);
    		rsn.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.PENDING);
    		scheduledNotification.getRecipientScheduledNotification().add(rsn);
    	}
    	
		
		
		if (plannedNotification.getEventName() == NotificationEventTypeEnum.MASTER_SUBJECT_UPDATED_EVENT ){
			scheduledNotification.setStudyOrganization(soList.get(0));
		}else{
			//Add the studyOrganization containing the site which is linked to by PlannedNotifications
			for(StudyOrganization ss: soList){
				if(ss.getHealthcareSite().getPrimaryIdentifier().equals(plannedNotification.getHealthcareSite().getPrimaryIdentifier())){
					scheduledNotification.setStudyOrganization(ss);
				}
			}
		}
    	
    	return scheduledNotification;
    }
    
    
    /* Using freemarker to compose the email message and replace the substitution vars
     */
    private String applyRuntimeReplacementsForEmailMessage(String rawText, Map<Object, Object> map) {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration();
        rawText = StringUtils.getBlankIfNull(rawText);
        try {
            Template t = new Template("message", new StringReader(rawText), cfg);
            StringWriter writer = new StringWriter();
            t.process(map, writer);
            return writer.toString();
        } catch (TemplateException e) {
            log.error("Error while applying freemarker template ", e);
        } catch (IOException e) {
            log.error("Error while applying freemarker template ", e);
        }
        return "";
    }
    

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
