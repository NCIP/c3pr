/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.scheduler.runtime.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionException;
import org.springframework.mail.MailException;

import edu.duke.cabig.c3pr.constants.EmailNotificationDeliveryStatusEnum;
import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.constants.NotificationFrequencyEnum;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.PlannedNotificationDao;
import edu.duke.cabig.c3pr.dao.ScheduledNotificationDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.dao.query.DataAuditEventQuery;
import edu.duke.cabig.c3pr.domain.ContactMechanismBasedRecipient;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.domain.repository.AuditHistoryRepository;
import edu.duke.cabig.c3pr.utils.NotificationEmailService;
import gov.nih.nci.cabig.ctms.audit.domain.DataAuditEvent;

/**
 * This class serves as the Email sending job class scheduled by <code>scheduler</code>
 * component. 
 * @author Vinay Gangoli
 */
public class ScheduledNotificationJob extends ScheduledJob {

    protected static final Log logger = LogFactory.getLog(ScheduledNotificationJob.class);
    
    private NotificationEmailService notificationEmailService;
    
    private AuditHistoryRepository auditHistoryRepository;
    
    private StudySubjectDao studySubjectDao;
    
    private PlannedNotificationDao plannedNotificationDao;
    
    private ScheduledNotificationDao scheduledNotificationDao;
    
    public final String NEW_REGISTATION_REPORT_MESSAGE = "This is the list of New Registrations from last ";
    
    public static final String WEEK = "Week";
    public static final String MONTH = "Month";
    public static final String YEAR = "Year";
    
    public ScheduledNotificationJob(){
    	super();
    }

    /*
     * @see edu.duke.cabig.c3pr.domain.scheduler.runtime.job.ScheduledJob#processJob(org.quartz.JobDataMap, org.springframework.context.ApplicationContext,
     * 									 edu.duke.cabig.c3pr.domain.RecipientScheduledNotification)
     * This is the job for the Study status change notification email delivery. 
     */
    @Override
    public void processJob(JobDataMap jobDataMap,
    						RecipientScheduledNotification recipientScheduledNotification, 
    						PlannedNotification plannedNotification)
    						throws JobExecutionException {
       	logger.debug("Executing ScheduledNotification Job");
       	
    	assert applicationContext != null: "applicationContext cannot be null";
    	assert plannedNotification != null: "plannedNotification cant be null";
    	
    	synchronized (this) {
    		try {
                // init the member variables
            	notificationEmailService = (NotificationEmailService) applicationContext.getBean("notificationEmailService");
            	setAuditInfo();
            	if(recipientScheduledNotification == null){
            		if(plannedNotification.getFrequency() != NotificationFrequencyEnum.IMMEDIATE){
            			//Generate the REPORT NOTIFICATION and send it
            			ScheduledNotification scheduledNotification = handleReportGeneration(plannedNotification);
            			if(scheduledNotification != null){
            				for(RecipientScheduledNotification rsn: scheduledNotification.getRecipientScheduledNotification()){
            					notificationEmailService.sendReportEmail(rsn);
            				}
            			} else {
            				logger.error("Error during report generation job: scheduledNotification is null. " +
            						"Note: only events can have frequencies other than IMMEDIATE.");
            			}
            		}
            	} else if(recipientScheduledNotification.getDeliveryStatus().equals(EmailNotificationDeliveryStatusEnum.PENDING) ||
            			recipientScheduledNotification.getDeliveryStatus().equals(EmailNotificationDeliveryStatusEnum.RETRY) ){
            		
            		if(plannedNotification.getFrequency().equals(NotificationFrequencyEnum.IMMEDIATE) || plannedNotification.getFrequency().equals(NotificationFrequencyEnum.END_OF_THE_DAY)){
            			if(recipientScheduledNotification.getRecipient() instanceof UserBasedRecipient){
            				if(((UserBasedRecipient)recipientScheduledNotification.getRecipient()).getPersonUser() != null){
            					logger.error("$$$$$$$$$$ Threads id is :" + Thread.currentThread().getId());
            					logger.error("$$$$$$$$$$ RBN's id is :" + recipientScheduledNotification.getId());
            					logger.error("$$$$$$$$$$ ResearchStaff's version is :" + ((UserBasedRecipient)recipientScheduledNotification.getRecipient()).getPersonUser().getVersion() );
            				}
            			}
            			notificationEmailService.sendEmail(recipientScheduledNotification);
            		}
                    recipientScheduledNotification.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.COMPLETE);
            	}
            } catch(MailException me){
            	logger.error("execution of sendMail failed", me);
            	recipientScheduledNotification.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.RETRY);
            }catch (Exception e){
                logger.error("execution of job failed", e);
                recipientScheduledNotification.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.ERROR);
            } finally {
            	if(recipientScheduledNotification.getRecipient() instanceof UserBasedRecipient){
    				if(((UserBasedRecipient)recipientScheduledNotification.getRecipient()).getPersonUser() != null){
    					logger.error("$$$$$$$$$$ FINALLY  Threads id is :" + Thread.currentThread().getId());
    					logger.error("$$$$$$$$$$ FINALLY  RBN's id is :" + recipientScheduledNotification.getId());
    					logger.error("$$$$$$$$$$ FINALLY  ResearchStaff's version is :" + ((UserBasedRecipient)recipientScheduledNotification.getRecipient()).getPersonUser().getVersion() );
    				}
    			}
            	recipientScheduledNotificationDao.saveOrUpdate(recipientScheduledNotification);
            }
		}
    	
        
        logger.debug("Exiting ScheduledNotification Job");
    }
    
    public ScheduledNotification handleReportGeneration(PlannedNotification plannedNotification){
    	
    	plannedNotificationDao = (PlannedNotificationDao) applicationContext.getBean("plannedNotificationDao");
    	scheduledNotificationDao = (ScheduledNotificationDao) applicationContext.getBean("scheduledNotificationDao");
    	
    	//if recipientScheduledNotification is null...create and save both scheduledNotification and recipientScheduledNotification.
    	if(plannedNotification.getEventName().equals(NotificationEventTypeEnum.NEW_REGISTRATION_EVENT_REPORT)){
    		//gov.nih.nci.cabig.ctms.audit.DataAuditInfo.setLocal(new gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo(
    		//userName, request.getRemoteAddr(), new Date(),httpReq.getRequestURI()));
    		//gov.nih.nci.cabig.ctms.audit.DataAuditInfo.setLocal(new gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo(
    		//"C3PR Admin", "C3PR Scheduled Notification Job", new Date(), "C3PR Scheduled Notification Job"));
    		
			String reportText = generateReportFromHistory(plannedNotification);
			ScheduledNotification scheduledNotification = addScheduledNotification(plannedNotification, reportText);
			plannedNotificationDao.saveOrUpdate(plannedNotification);
			return scheduledNotificationDao.getById(scheduledNotification.getId());
    	}
    	
    	if(plannedNotification.getEventName().equals(NotificationEventTypeEnum.MASTER_SUBJECT_UPDATED_EVENT)){
			String reportText = generateReportFromHistory(plannedNotification);
			ScheduledNotification scheduledNotification = addScheduledNotification(plannedNotification, reportText);
			plannedNotificationDao.saveOrUpdate(plannedNotification);
			return scheduledNotificationDao.getById(scheduledNotification.getId());
    	}
    	return null;
    }
    
    
    public ScheduledNotification addScheduledNotification(PlannedNotification plannedNotification, String reportText){
    	
    	ScheduledNotification scheduledNotification = new ScheduledNotification();
    	plannedNotification.getScheduledNotifications().add(scheduledNotification);
    	scheduledNotification.setDateSent(new Date());
		scheduledNotification.setMessage(reportText);
		scheduledNotification.setTitle(plannedNotification.getEventName().getDisplayName() + " - " + plannedNotification.getFrequency().getDisplayName() + " Report.");
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
    	return scheduledNotification;
    }

    /*
     * generate the new registrations report and assign it to the rsn.
     */
    private String generateReportFromHistory(PlannedNotification plannedNotification){
    	
    	auditHistoryRepository = (AuditHistoryRepository)applicationContext.getBean("auditHistoryRepository");
    	studySubjectDao = (StudySubjectDao)applicationContext.getBean("studySubjectDao");
    	
		List<DataAuditEvent> daeList = getAuditHistory(auditHistoryRepository, plannedNotification);
		
    	Iterator iter = daeList.iterator();
    	List <Integer>idList = new ArrayList<Integer>();
    	DataAuditEvent auditEvent =  null;
    	while(iter.hasNext()){
    		auditEvent = (DataAuditEvent)iter.next();
    		idList.add(auditEvent.getReference().getId());
    	}
    	List<StudySubject> ssList = new ArrayList<StudySubject>();
    	for(int i = 0; i < idList.size(); i++){
    		ssList.add(studySubjectDao.getById(idList.get(i)));
    	}
    	return composeReport(ssList, plannedNotification); 
    }
    
    /*
     *  create the message format with the values to be reported
     */
    private String composeReport(List<StudySubject> ssList, PlannedNotification plannedNotification){
    	StringBuffer msgBody = new StringBuffer();
    	String messageText = " ";
    	String messageTerm = " ";
    	
    	switch(plannedNotification.getEventName()){
			case NEW_REGISTRATION_EVENT:    messageText = NEW_REGISTATION_REPORT_MESSAGE;
						   					break;
						   					
			default: 						break;
		}
    	
    	switch(plannedNotification.getFrequency()){
    		case WEEKLY:   messageTerm = WEEK; 
    					   break;
    		case MONTHLY:  messageTerm = MONTH;
    			 		   break;
    		case ANNUAL:   messageTerm = YEAR;
    					   break;
    		default: break;
    	}
    	
    	msgBody.append("<html><body><br/>");
    	msgBody.append( messageText + messageTerm + "<br/><br/>");
    	msgBody.append("<table border ='1'>");
    	msgBody.append("<tr>");
    	msgBody.append("<th>" + "Subject MRN" + "</th>");
    	msgBody.append("<th>" + "Registration  Status" + "</th>");
    	msgBody.append("<th>" + "Subject Name" + "</th>");
    	msgBody.append("</tr>");
    	StudySubject ss = null;
    	Iterator iter = ssList.iterator();
    	while(iter.hasNext()){
    		ss = (StudySubject)iter.next();
    		msgBody.append("<tr>");
    		msgBody.append("<td>" + ss.getStudySubjectDemographics().getMasterSubject().getMRN().getValue()+ "</td>");
    		msgBody.append("<td>" + ss.getRegWorkflowStatus().getDisplayName()+ "</td>");
    		msgBody.append("<td>" + ss.getStudySubjectDemographics().getMasterSubject().getFirstName() + " " + ss.getStudySubjectDemographics().getMasterSubject().getLastName() + "</td>");
    		msgBody.append("</tr>");
    	}
    	msgBody.append("</table>");
    	msgBody.append("</body></html>");
    	
    	return msgBody.toString();
    }
    
    /*
     * generate the query and run it against the audit repo to get results for report.
     */
    private List<DataAuditEvent> getAuditHistory(AuditHistoryRepository auditHistoryRepository, 
    		PlannedNotification plannedNotification){
    	
    	DataAuditEventQuery dataAuditEventQuery = new DataAuditEventQuery();
    	Calendar now = Calendar.getInstance();
    	Calendar prev = (Calendar)now.clone();
        
        if(plannedNotification.getFrequency().equals(NotificationFrequencyEnum.WEEKLY)){
        	prev.add(Calendar.DATE, -7);
        }
        if(plannedNotification.getFrequency().equals(NotificationFrequencyEnum.MONTHLY)){
        	prev.add(Calendar.MONTH, -1);
        }
        if(plannedNotification.getFrequency().equals(NotificationFrequencyEnum.ANNUAL)){
        	prev.add(Calendar.YEAR, -1);
        }
        
        dataAuditEventQuery.filterByClassName(StudySubject.class.getName());
        dataAuditEventQuery.filterByEndDateBefore(now.getTime());
        dataAuditEventQuery.filterByStartDateAfter(prev.getTime());
        //where the status has changed to REGISTERED
        dataAuditEventQuery.filterByValue("regWorkflowStatus", null, RegistrationWorkFlowStatus.ON_STUDY.toString());
        
        final List<DataAuditEvent> dataAuditEvents = auditHistoryRepository.findDataAuditEvents(dataAuditEventQuery);
        return dataAuditEvents;
    }
    
    
    
	public NotificationEmailService getNotificationEmailService() {
		return notificationEmailService;
	}

	public void setNotificationEmailService(NotificationEmailService notificationEmailService) {
		this.notificationEmailService = notificationEmailService;
	}

}
