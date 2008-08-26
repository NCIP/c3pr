package edu.duke.cabig.c3pr.infrastructure.interceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.EmptyInterceptor;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessResourceFailureException;

import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.service.impl.RulesDelegationServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;

public class NotificationInterceptor extends EmptyInterceptor implements ApplicationContextAware {

	private static final Log log = LogFactory.getLog(NotificationInterceptor.class);
	
	RulesDelegationServiceImpl rulesDelegationService;
	
	private ApplicationContext applicationContext;
	
	private Configuration configuration;
	
	private String studyId; 
	private String studySiteId;
	private String studySubjectId;
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public List<PlannedNotification> getPlannedNotifications(){
		List<PlannedNotification> result;
		SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean("sessionFactory");
		Session session = sessionFactory.openSession(sessionFactory.getCurrentSession().connection());
		session.setFlushMode(FlushMode.MANUAL);
		result = new ArrayList<PlannedNotification>();
        try {
          Query query =  session.createQuery("select p from PlannedNotification p, HealthcareSite o where p.id = o.plannedNotificationsInternal.id and o.nciInstituteCode = ?");
          query.setString(0, configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE));
          result = query.list();
        }
        catch (DataAccessResourceFailureException e) {
            log.error(e.getMessage());
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
        }
        catch (HibernateException e) {
            log.error(e.getMessage());
        }
        finally{
        	session.close();
        }
        return result;
        //result = organizationDao.getByNciIdentifier(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).get(0).getPlannedNotifications();
        //return result;
	}

	
	/*
	 *  Return true only if the state is modified in anyway.
	 */
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
		String[] propertyNames, Type[] types) {
		log.debug(this.getClass().getName() + ": Entering onSave()");
		
		//Every new registration
		if(entity instanceof StudySubject){			
			handleNewStudySubjectSaved(null,((StudySubject)entity).getRegWorkflowStatus(), entity);
		}
		//Every new study...notification is onyl sent if new stuayd status is Active
		if(entity instanceof Study){			
			handleStudyStatusChange(null,((Study)entity).getCoordinatingCenterStudyStatus(), entity);
		}
		//New Study Site .....currently not implemented
		if(entity instanceof StudySite){
			handleStudySiteStatusChange(null, ((StudySite)entity).getSiteStudyStatus(), entity);
		}
		log.debug(this.getClass().getName() + ": Exiting onSave()");
		return false;
	}


	/*
	 * This call back intercepts all updates to the database.
	 * return true only if the current state is modified in anyway. Do NOT modify the previousState.
	 */
	@Override
	public boolean onFlushDirty(final Object entity, Serializable id, final Object[] currentState,
					            final Object[] previousState, String[] propertyNames, Type[] types) {
		
		log.debug(this.getClass().getName() + ": Entering onFlushDirty()");
		
		//Study status changes are spotted here for activating rules.
		if(entity instanceof Study ){
			if(String.valueOf(entity.hashCode()).equals(studyId)){
				//while saving the scheduled notifications, the interceptor is fired again
				//to prevent an infinite loop..we check to see if the study obj involved is the same
				//if so exit the interceptor immediately else continue processing
				log.debug("exiting to prevent looping");
				return false;
			}else{
				studyId = String.valueOf(entity.hashCode());
				for (int i = 0; i < propertyNames.length; i++) {
					if (propertyNames[i].equals("coordinatingCenterStudyStatus") && previousState != null && currentState != null ){		
							handleStudyStatusChange(previousState[i], currentState[i], entity);
					}
				}
			}
		}
		
		//StudySite status changes are spotted here for activating rules.
		if(entity instanceof StudySite){
			if(String.valueOf(entity.hashCode()).equals(studySiteId)){
				//while saving the scheduled notifications, the interceptor is fired again
				//to prevent an infinite loop..we check to see if the studySite obj involved is the same
				//if so exit the interceptor immediately else continue processing
				log.debug("exiting to prevent looping");
				return false;
			}else{
				studySiteId = String.valueOf(entity.hashCode());
				for (int i = 0; i < propertyNames.length; i++) {
					if (propertyNames[i].equals("siteStudyStatus")){
						if(previousState != null && currentState != null ){
							handleStudySiteStatusChange(previousState[i], currentState[i], entity);
						}
					}
				}
			}
		}		
		
		//When Reg status changes to Reserved or Registered
		if(entity instanceof StudySubject){	
			if(String.valueOf(entity.hashCode()).equals(studySubjectId)){
				//while saving the scheduled notifications, the interceptor is fired again
				//to prevent an infinite loop..we check to see if the studySite obj involved is the same
				//if so exit the interceptor immediately else continue processing
				log.debug("exiting to prevent looping");
				return false;
			}else{
				studySubjectId = String.valueOf(entity.hashCode());
				for (int i = 0; i < propertyNames.length; i++) {
					if (propertyNames[i].equals("regWorkflowStatus")){
						if( currentState != null ){
							if(previousState != null){
								handleNewStudySubjectSaved(previousState[i], currentState[i], entity);
							} else {
								handleNewStudySubjectSaved(null, currentState[i], entity);
							}
							
						}
					}
				}
			}
		}
		log.debug(this.getClass().getName() + ": Exiting onFlushDirty()");
		return false;
	}
	
	//activates rules for the NEW_REGISTRATION_EVENT & REGISTATION_STATUS_CHANGE
	public void handleNewStudySubjectSaved(final Object previousState, final Object currentState, final Object studySubject){
		RegistrationWorkFlowStatus previousRegStatus = null;
		RegistrationWorkFlowStatus currentRegStatus = null;
		NotificationEventTypeEnum event = null;
		
		List<Object> objects = new ArrayList<Object>();
		objects.add(studySubject);
		
		if(previousState != null && previousState instanceof RegistrationWorkFlowStatus){
			previousRegStatus = (RegistrationWorkFlowStatus)previousState;
		}
		if(currentState != null && currentState instanceof RegistrationWorkFlowStatus){
			currentRegStatus = (RegistrationWorkFlowStatus)currentState;
		}
		
		//figure out the event and then fire the rules
		if(previousRegStatus != null &&
				previousRegStatus.getCode().equals(currentRegStatus.getCode())){
			//no status change...do nothing
		} else {
			//if the prev status is null or pendign and current status is registered then its a new registration
			//else its a reg status change.
			if(currentRegStatus.equals(RegistrationWorkFlowStatus.REGISTERED) &&
				(previousRegStatus == null || previousRegStatus.equals(RegistrationWorkFlowStatus.PENDING) ||
				 previousRegStatus.equals(RegistrationWorkFlowStatus.UNREGISTERED) || previousRegStatus.equals(RegistrationWorkFlowStatus.RESERVED)) ){
				event = NotificationEventTypeEnum.NEW_REGISTRATION_EVENT;
			} else if(currentRegStatus.equals(RegistrationWorkFlowStatus.OFF_STUDY)){
				event = NotificationEventTypeEnum.SUBJECT_REMOVED_OFF_STUDY;
			} else {
				event = NotificationEventTypeEnum.REGISTATION_STATUS_CHANGE;
			}
			for(PlannedNotification pn: getPlannedNotifications()){
				if(pn.getEventName().equals(event)){
					objects.add(pn);
					rulesDelegationService.activateRules(event, objects);
					objects.remove(pn);
				}
			}
		}
		log.debug(this.getClass().getName() + ": exiting handleNewStudySubjectSaved()");
	}
	
	
	//actives rules for the STUDY_SITE_STATUS_CHANGED_EVENT
	public void handleStudySiteStatusChange(final Object previousState, final Object currentState, final Object entity){
		
		SiteStudyStatus previousSiteStudyStatus = null;
		SiteStudyStatus currentSiteStudyStatus = null;
		NotificationEventTypeEnum event = null;
		
		List<Object> objects = new ArrayList<Object>();
		objects.add(entity);
		
		if(previousState != null && previousState instanceof SiteStudyStatus){
			previousSiteStudyStatus = (SiteStudyStatus)previousState;
		}
		if(currentState != null && currentState instanceof SiteStudyStatus){
			currentSiteStudyStatus = (SiteStudyStatus)currentState;
		}
		
		
		if(previousSiteStudyStatus != null && 
				previousSiteStudyStatus.getCode().equals(currentSiteStudyStatus.getCode())){
			//no status change...do nothing
		} else {
			//there is some status change and event is configured in plannedNotifs
			//for study site we only have ths study site status changed event
			event = NotificationEventTypeEnum.STUDY_SITE_STATUS_CHANGED_EVENT;
			
			for(PlannedNotification pn: getPlannedNotifications()){
				if(pn.getEventName().equals(event)){
					objects.add(pn);
					rulesDelegationService.activateRules(event, objects);
					objects.remove(pn);
				}
			}
		}		
	}
	
	//	activates rules for the NEW_STUDY_SAVED_EVENT & STUDY_STATUS_CHANGED_EVENT
	public void handleStudyStatusChange(final Object previousState, final Object currentState, final Object entity){
		
		log.debug(this.getClass().getName() + ": Entering handleStudyStatusChange()");
		CoordinatingCenterStudyStatus previousCoordinatingCenterStudyStatus = null;
		CoordinatingCenterStudyStatus currentCoordinatingCenterStudyStatus = null;
		NotificationEventTypeEnum event = null;
		
		List<Object> objects = new ArrayList<Object>();
		objects.add(entity);
		
		if(previousState != null && previousState instanceof CoordinatingCenterStudyStatus){
			previousCoordinatingCenterStudyStatus = (CoordinatingCenterStudyStatus)previousState;
		}
		if(currentState != null && currentState instanceof CoordinatingCenterStudyStatus){
			currentCoordinatingCenterStudyStatus = (CoordinatingCenterStudyStatus)currentState;
		}
		
		//figure out the event and then fire the rules
		if(previousCoordinatingCenterStudyStatus != null &&
				previousCoordinatingCenterStudyStatus.equals(currentCoordinatingCenterStudyStatus)){
			//no status change...hence do nothing.
		}else{
			//if the prev status is null or pendign and current status is active then its a new study
			//else its a study status change.
			if(currentCoordinatingCenterStudyStatus.equals(CoordinatingCenterStudyStatus.ACTIVE) &&
				(previousCoordinatingCenterStudyStatus == null || previousCoordinatingCenterStudyStatus.equals(CoordinatingCenterStudyStatus.PENDING)) ){
				event = NotificationEventTypeEnum.NEW_STUDY_SAVED_EVENT;
			} else {
				event = NotificationEventTypeEnum.STUDY_STATUS_CHANGED_EVENT;
			}
			
			for(PlannedNotification pn: getPlannedNotifications()){
				//there is some status change and event is configured in plannedNotifs...activate RulesService
				if(pn.getEventName().equals(event)){
					objects.add(pn);
					rulesDelegationService.activateRules(event, objects);
					objects.remove(pn);
				}
			}
		}	
		log.debug(this.getClass().getName() + ": exiting handleStudyStatusChange()");
	}

	/*  public void handleNewStudySubjectSaved(StudySubject studySubject){
			log.debug(this.getClass().getName() + ": Entering handleNewStudySubSaved()");
			List<Object> objects = new ArrayList<Object>();
			objects.add(studySubject);
			
			for(PlannedNotification pn: getHostingOrganization()){
				if(pn.getEventName().equals(NotificationEventTypeEnum.NEW_REGISTRATION_EVENT)){
					objects.add(NotificationEventTypeEnum.NEW_REGISTRATION_EVENT);
					objects.add(pn);
					rulesDelegationService.activateRules(NotificationEventTypeEnum.NEW_REGISTRATION_EVENT, objects);
					objects.remove(pn);
				}
			}
			log.debug(this.getClass().getName() + ": exiting handleNewStudySubSaved()");
		}
		
		public void handleNewStudySaved(Study study){
			log.debug(this.getClass().getName() + ": Entering handleNewStudySaved()");
			List<Object> objects = new ArrayList<Object>();
			objects.add(study);
			
			for(PlannedNotification pn: getHostingOrganization()){
				if(pn.getEventName().equals(NotificationEventTypeEnum.NEW_STUDY_SAVED_EVENT)){
					objects.add(NotificationEventTypeEnum.NEW_STUDY_SAVED_EVENT);
					objects.add(pn);
					rulesDelegationService.activateRules(NotificationEventTypeEnum.NEW_STUDY_SAVED_EVENT, objects);
					objects.remove(pn);
				}
			}
			log.debug(this.getClass().getName() + ": exiting handleNewStudySaved()");
		}
		
		public void handleNewStudySiteSaved(StudySite studySite){
			log.debug(this.getClass().getName() + ": Entering handleNewStudySiteSaved()");
			List<Object> objects = new ArrayList<Object>();
			objects.add(studySite);
			
			for(PlannedNotification pn: getHostingOrganization()){
				if(pn.getEventName().equals(NotificationEventTypeEnum.NEW_STUDY_SITE_SAVED_EVENT)){
					objects.add(NotificationEventTypeEnum.NEW_STUDY_SITE_SAVED_EVENT);
					objects.add(pn);
					rulesDelegationService.activateRules(NotificationEventTypeEnum.NEW_STUDY_SITE_SAVED_EVENT, objects);
					objects.remove(pn);
				}
			}
			log.debug(this.getClass().getName() + ": exiting handleNewStudySiteSaved()");
		}*/

	public RulesDelegationServiceImpl getRulesDelegationService() {
		return rulesDelegationService;
	}

	public void setRulesDelegationService(
			RulesDelegationServiceImpl rulesDelegationService) {
		this.rulesDelegationService = rulesDelegationService;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

}
