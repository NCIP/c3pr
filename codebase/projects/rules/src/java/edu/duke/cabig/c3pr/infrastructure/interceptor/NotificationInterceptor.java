package edu.duke.cabig.c3pr.infrastructure.interceptor;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import edu.duke.cabig.c3pr.constants.NotificationEventType;
import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.service.impl.RulesDelegationServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;

public class NotificationInterceptor extends EmptyInterceptor implements ApplicationContextAware {

	private static final Log log = LogFactory.getLog(NotificationInterceptor.class);
	
	RulesDelegationServiceImpl rulesDelegationService;
	
	private ApplicationContext applicationContext;
	
	private OrganizationDao organizationDao;
	
	private Configuration configuration; 
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public Organization getHostingOrganization(){
		Organization org = organizationDao.getByNciIdentifier(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).get(0);
		return org;
	}
	
	
	/*
	 *  Return true only if the state is modified in anyway.
	 */
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
		String[] propertyNames, Type[] types) {
		log.debug(this.getClass().getName() + ": Entering onSave()");
		//SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean("sessionFactory");
		//Session session  = sessionFactory.openSession();	
		//Serializable persistedObjectId = getObjectId(entity);
		//Object preUpdateState = session.get(entity.getClass(),  persistedObjectId);
		if(entity instanceof Study){			
			handleNewStudySaved((Study)entity);
		}
		if(entity instanceof StudySite){
			handleNewStudySiteSaved((StudySite)entity);
		}
		log.debug(this.getClass().getName() + ": Exiting onSave()");
		return false;
	}

/*	private Serializable getObjectId(Object obj) {
        
        Class objectClass = obj.getClass();
        Method[] methods = objectClass.getMethods();

        Serializable persistedObjectId = null;
        for (int ii = 0; ii < methods.length; ii++) {
            // If the method name equals 'getId' then invoke it to get the id of the object.
            if (methods[ii].getName().equals("getId")) {
                try {
                    persistedObjectId = (Serializable)methods[ii].invoke(obj, null);
                    break;      
                } catch (Exception e) {
                    log.warn("Audit Log Failed - Could not get persisted object id: " + e.getMessage());
                }
            }
        }
        return persistedObjectId;
    }*/

	/*
	 * This call back intercepts all updates to the database.
	 * return true only if the current state is modified in anyway. Do NOT modify the previousState.
	 */
	@Override
	public boolean onFlushDirty(final Object entity, Serializable id, final Object[] currentState,
					            final Object[] previousState, String[] propertyNames, Type[] types) {
		
		log.debug(this.getClass().getName() + ": Entering onFlushDirty()");
		//Study related property updates are spotted here for activating the corresponding rules.
		if(entity instanceof Study){
			for (int i = 0; i < propertyNames.length; i++) {
				if (propertyNames[i].equals("coordinatingCenterStudyStatus")){					
					handleStudyStatusChange(previousState[i], currentState[i], entity);										
				}
			}
		}
		
		//Study Site related property updates are spotted here for activating the corresponding rules.
		if(entity instanceof StudySite){
			for (int i = 0; i < propertyNames.length; i++) {
				if (propertyNames[i].equals("siteStudyStatus")){
					handleStudySiteStatusChange(previousState[i], currentState[i], entity);
				}
			}
		}		
		log.debug(this.getClass().getName() + ": Exiting onFlushDirty()");
		return false;
	}
	
	
	public void handleNewStudySaved(Study study){
		log.debug(this.getClass().getName() + ": Entering handleNewStudySaved()");
		rulesDelegationService.activateRules(RulesDelegationServiceImpl.NEW_STUDY_SAVED_EVENT, study, 
				null, study.getCoordinatingCenterStudyStatus().getCode());
		log.debug(this.getClass().getName() + ": exiting handleNewStudySaved()");
	}
	
	public void handleNewStudySiteSaved(StudySite studySite){
		log.debug(this.getClass().getName() + ": Entering handleNewStudySiteSaved()");
		rulesDelegationService.activateRules(RulesDelegationServiceImpl.NEW_STUDY_SITE_SAVED_EVENT, studySite,
				null, studySite.getSiteStudyStatus().getCode());
		log.debug(this.getClass().getName() + ": exiting handleNewStudySiteSaved()");
	}
	
	
	public void handleStudySiteStatusChange(final Object previousState, final Object currentState, final Object entity){
		
		SiteStudyStatus previousSiteStudyStatus = null;
		SiteStudyStatus currentSiteStudyStatus = null;
		
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
			for(PlannedNotification pn: getHostingOrganization().getPlannedNotifications()){
				if(pn.getEventName().equals(NotificationEventType.STUDY_STATUS_CHANGED_EVENT)){
					rulesDelegationService.activateRules(RulesDelegationServiceImpl.STUDY_SITE_STATUS_CHANGE_EVENT, pn,
							previousSiteStudyStatus.getCode(), currentSiteStudyStatus.getCode());
					break;
				}
			}
		}		
	}
	
	
	public void handleStudyStatusChange(final Object previousState, final Object currentState, final Object entity){
		
		log.debug(this.getClass().getName() + ": Entering handleStudyStatusChange()");
		CoordinatingCenterStudyStatus previousCoordinatingCenterStudyStatus = null;
		CoordinatingCenterStudyStatus currentCoordinatingCenterStudyStatus = null;
		
		if(previousState != null && previousState instanceof CoordinatingCenterStudyStatus){
			previousCoordinatingCenterStudyStatus = (CoordinatingCenterStudyStatus)previousState;
		}
		if(currentState != null && currentState instanceof CoordinatingCenterStudyStatus){
			currentCoordinatingCenterStudyStatus = (CoordinatingCenterStudyStatus)currentState;
		}
		
		if(previousCoordinatingCenterStudyStatus.getCode().equals(currentCoordinatingCenterStudyStatus.getCode())){
			//no status change...hence do nothing.
		}else{
			//there is some status change and event is configured in plannedNotifs...activate RulesService
			for(PlannedNotification pn: getHostingOrganization().getPlannedNotifications()){
				if(pn.getEventName().equals(NotificationEventType.STUDY_STATUS_CHANGED_EVENT)){
					rulesDelegationService.activateRules(RulesDelegationServiceImpl.STUDY_STATUS_CHANGE_EVENT, pn,
							previousCoordinatingCenterStudyStatus.getCode(), currentCoordinatingCenterStudyStatus.getCode());
					break;
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
	
}
