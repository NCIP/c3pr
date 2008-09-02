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
import edu.duke.cabig.c3pr.dao.PlannedNotificationDao;
import edu.duke.cabig.c3pr.dao.ScheduledNotificationDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.dao.query.DataAuditEventQuery;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
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
    
    public final String spacer = "     ";
    public final String seperator = " | ";
    public final String dashedLine = "-----------------------------------------------------------------------------------";

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
    	//recipientScheduledNotification can be null for report generation cases
    	//assert recipientScheduledNotification != null: "recipientScheduledNotification cannot be null";
    	assert plannedNotification != null: "plannedNotification cant be null";
    	
        try {
            // init the member variables
        	notificationEmailService = (NotificationEmailService) applicationContext.getBean("notificationEmailService");
        	if(recipientScheduledNotification == null){
        		if(plannedNotification.getFrequency() != NotificationFrequencyEnum.IMMEDIATE){
        			//Generate the REPORT NOTIFICATION and send it
        			ScheduledNotification scheduledNotification = handleReportGeneration(plannedNotification);
        			if(scheduledNotification != null){
        				for(RecipientScheduledNotification rsn: scheduledNotification.getRecipientScheduledNotification()){
        					notificationEmailService.sendEmail(rsn);
        				}
        			} else {
        				logger.error("Error during report generation job: scheduledNotification is null. " +
        						"Note: only registration event can have frequencies other than IMMEDIATE.");
        			}
        		}
        	} else if(recipientScheduledNotification.getDeliveryStatus().equals(EmailNotificationDeliveryStatusEnum.PENDING) ||
        			recipientScheduledNotification.getDeliveryStatus().equals(EmailNotificationDeliveryStatusEnum.RETRY) ){
        		
        		if(plannedNotification.getFrequency().equals(NotificationFrequencyEnum.IMMEDIATE)){
        			notificationEmailService.sendEmail(recipientScheduledNotification);
        		}
                recipientScheduledNotification.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.COMPLETE);
        	}
        } catch(MailException me){
        	logger.error("execution of sendMail failed", me);
        	recipientScheduledNotification.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.RETRY);
        }catch (Exception e){
            logger.error("execution of job  failed", e);
            recipientScheduledNotification.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.ERROR);
        }
        logger.debug("Exiting ScheduledNotification Job");
    }
    
    
    public ScheduledNotification handleReportGeneration(PlannedNotification plannedNotification){
    	
    	plannedNotificationDao = (PlannedNotificationDao) applicationContext.getBean("plannedNotificationDao");
    	scheduledNotificationDao = (ScheduledNotificationDao) applicationContext.getBean("scheduledNotificationDao");
    	
    	//if recipientScheduledNotification is null...create and save both scheduledNotification and recipientScheduledNotification.
    	if(plannedNotification.getEventName().equals(NotificationEventTypeEnum.NEW_REGISTRATION_EVENT)){
    		//gov.nih.nci.cabig.ctms.audit.DataAuditInfo.setLocal(new gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo(
            		//userName, request.getRemoteAddr(), new Date(),httpReq.getRequestURI()));
    		gov.nih.nci.cabig.ctms.audit.DataAuditInfo.setLocal(new gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo(
            		"C3PR Admin", "C3PR Scheduled Notification Job", new Date(), "C3PR Scheduled Notification Job"));
    		
    		
			String reportText = generateReportFromHistory(plannedNotification);
			ScheduledNotification scheduledNotification = addScheduledNotification(plannedNotification, reportText);
			plannedNotificationDao.saveOrUpdate(plannedNotification);
			return scheduledNotificationDao.getById(scheduledNotification.getId());
    	}
    	return null;
    }
    
    
    public ScheduledNotification addScheduledNotification(PlannedNotification plannedNotification, String reportText){
    	
    	ScheduledNotification scheduledNotification = new ScheduledNotification();
    	plannedNotification.getScheduledNotification().add(scheduledNotification);
    	scheduledNotification.setDateSent(new Date());
		scheduledNotification.setMessage(reportText);
		scheduledNotification.setTitle(plannedNotification.getFrequency().getDisplayName() + " Report.");
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
    	return composeReport(ssList); 
    }
    
    /*
     *  create the message format with the values to be reported
     */
    private String composeReport(List<StudySubject> ssList){
    	StringBuffer msgBody = new StringBuffer();
    	msgBody.append("Subject MRN" + this.spacer + this.seperator + "Registration  Status" + this.spacer + this.seperator + "Subject Name");
    	msgBody.append("\n");
    	msgBody.append(dashedLine);
    	msgBody.append("\n");
    	StudySubject ss = null;
    	Iterator iter = ssList.iterator();
    	while(iter.hasNext()){
    		ss = (StudySubject)iter.next();
    		msgBody.append(ss.getParticipant().getMRN().getValue());
    		msgBody.append(spacer + spacer);
    		msgBody.append(seperator);
    		msgBody.append(ss.getRegWorkflowStatus().getDisplayName());
    		msgBody.append(spacer + spacer);
    		msgBody.append(seperator);
    		msgBody.append(ss.getParticipant().getFirstName() + " " + ss.getParticipant().getLastName());
    		msgBody.append("\n");
    	}
    	
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
        dataAuditEventQuery.filterByValue("regWorkflowStatus", null, RegistrationWorkFlowStatus.REGISTERED.toString());
        
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