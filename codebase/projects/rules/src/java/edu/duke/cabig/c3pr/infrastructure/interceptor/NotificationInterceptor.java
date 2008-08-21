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
import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
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
	
	private OrganizationDao organizationDao;
	
	private Configuration configuration; 
	
	private Study study;
	
	private StudySubject studySubject;
	
	private String studyId; 
	private String studySiteId;
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public List<PlannedNotification> getHostingOrganization(){
		List<PlannedNotification> result;
		SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean("sessionFactory");
		//sessionFactory.getCurrentSession().setFlushMode(FlushMode.AUTO);
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
//		result = organizationDao.getByNciIdentifier(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).get(0).getPlannedNotifications();
//		return result;
	}

	
	/*
	 *  Return true only if the state is modified in anyway.
	 */
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
		String[] propertyNames, Type[] types) {
		log.debug(this.getClass().getName() + ": Entering onSave()");
		
		if(entity instanceof Study){			
			handleNewStudySaved((Study)entity);
		}
		if(entity instanceof StudySite){
			handleNewStudySiteSaved((StudySite)entity);
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
		
		//Study related property updates are spotted here for activating the corresponding rules.
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
		
		//Study Site related property updates are spotted here for activating the corresponding rules.
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
		log.debug(this.getClass().getName() + ": Exiting onFlushDirty()");
		return false;
	}
	
	
	public void handleNewStudySaved(Study study){
		List<Object> objects = new ArrayList<Object>();
		objects.add(study);
		
		log.debug(this.getClass().getName() + ": Entering handleNewStudySaved()");
		rulesDelegationService.activateRules(NotificationEventTypeEnum.NEW_STUDY_SAVED_EVENT, objects);
		log.debug(this.getClass().getName() + ": exiting handleNewStudySaved()");
	}
	
	public void handleNewStudySiteSaved(StudySite studySite){
		List<Object> objects = new ArrayList<Object>();
		objects.add(studySite);
		
		log.debug(this.getClass().getName() + ": Entering handleNewStudySiteSaved()");
		rulesDelegationService.activateRules(NotificationEventTypeEnum.NEW_STUDY_SITE_SAVED_EVENT, objects);
		log.debug(this.getClass().getName() + ": exiting handleNewStudySiteSaved()");
	}
	
	
	public void handleStudySiteStatusChange(final Object previousState, final Object currentState, final Object entity){
		
		SiteStudyStatus previousSiteStudyStatus = null;
		SiteStudyStatus currentSiteStudyStatus = null;
		List<Object> objects = new ArrayList<Object>();
		objects.add(entity);
		
		if(previousState != null && previousState instanceof SiteStudyStatus){
			previousSiteStudyStatus = (SiteStudyStatus)previousState;
		}
		if(currentState != null && currentState instanceof SiteStudyStatus){
			currentSiteStudyStatus = (SiteStudyStatus)currentState;
		}
		
		if(previousSiteStudyStatus.getCode().equals(currentSiteStudyStatus.getCode())){
			//no status change...do nothing
		} else {
			//there is some status change and event is configured in plannedNotifs...activate RulesService
			for(PlannedNotification pn: getHostingOrganization()){
				if(pn.getEventName().equals(NotificationEventTypeEnum.STUDY_SITE_STATUS_CHANGED_EVENT)){
					objects.add(NotificationEventTypeEnum.STUDY_SITE_STATUS_CHANGED_EVENT);
					objects.add(pn);
					rulesDelegationService.activateRules(NotificationEventTypeEnum.STUDY_SITE_STATUS_CHANGED_EVENT, objects);
					objects.remove(pn);
					//break;
				}
			}
		}		
	}
	
	
	public void handleStudyStatusChange(final Object previousState, final Object currentState, final Object entity){
		
		log.debug(this.getClass().getName() + ": Entering handleStudyStatusChange()");
		CoordinatingCenterStudyStatus previousCoordinatingCenterStudyStatus = null;
		CoordinatingCenterStudyStatus currentCoordinatingCenterStudyStatus = null;
		List<Object> objects = new ArrayList<Object>();
		objects.add(entity);
		
		if(previousState != null && previousState instanceof CoordinatingCenterStudyStatus){
			previousCoordinatingCenterStudyStatus = (CoordinatingCenterStudyStatus)previousState;
		}
		if(currentState != null && currentState instanceof CoordinatingCenterStudyStatus){
			currentCoordinatingCenterStudyStatus = (CoordinatingCenterStudyStatus)currentState;
		}
		
		if(previousCoordinatingCenterStudyStatus.getCode().equals(currentCoordinatingCenterStudyStatus.getCode())){
			//no status change...hence do nothing.
		}else{
			
			for(PlannedNotification pn: getHostingOrganization()){
				//there is some status change and event is configured in plannedNotifs...activate RulesService
				if(pn.getEventName().equals(NotificationEventTypeEnum.STUDY_STATUS_CHANGED_EVENT)){
					objects.add(NotificationEventTypeEnum.STUDY_STATUS_CHANGED_EVENT);
					objects.add(pn);
					rulesDelegationService.activateRules(NotificationEventTypeEnum.STUDY_STATUS_CHANGED_EVENT, objects);
					objects.remove(pn);
					//break;
				}
			}
		}	
		log.debug(this.getClass().getName() + ": exiting handleStudyStatusChange()");
	}


	public RulesDelegationServiceImpl getRulesDelegationService() {
		return rulesDelegationService;
	}

	public void setRulesDelegationService(
			RulesDelegationServiceImpl rulesDelegationService) {
		this.rulesDelegationService = rulesDelegationService;
	}

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	public StudySubject getStudySubject() {
		return studySubject;
	}

	public void setStudySubject(StudySubject studySubject) {
		this.studySubject = studySubject;
	}
	
}
