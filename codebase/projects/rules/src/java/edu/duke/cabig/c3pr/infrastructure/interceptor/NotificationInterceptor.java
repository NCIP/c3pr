package edu.duke.cabig.c3pr.infrastructure.interceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.service.impl.RulesDelegationServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;

public class NotificationInterceptor extends EmptyInterceptor implements ApplicationContextAware {

	private static final Log log = LogFactory.getLog(NotificationInterceptor.class);
	
	RulesDelegationServiceImpl rulesDelegationService;
	
	private ApplicationContext applicationContext;
	
	private Configuration configuration;
	
	private HealthcareSiteDao healthcareSiteDao;
	
	private String studyId; 
	private String studySiteId;
	private String studySubjectId;
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public List<PlannedNotification> getPlannedNotifications(List<HealthcareSite> hcsList){
		List<PlannedNotification> result;
		List<String> nciCodeList = new ArrayList<String>();
		for(HealthcareSite hcs: hcsList){
			if(hcs != null){
				nciCodeList.add(hcs.getNciInstituteCode());
			}
		}
		
		SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean("sessionFactory");
		Session session = sessionFactory.openSession(sessionFactory.getCurrentSession().connection());
		session.setFlushMode(FlushMode.MANUAL);
		result = new ArrayList<PlannedNotification>();
        try {
          //Query query =  session.createQuery("select p from PlannedNotification p, HealthcareSite o where p.id = o.plannedNotificationsInternal.id and o.nciInstituteCode = ?");
          Query query =  session.createQuery("select p from PlannedNotification p, HealthcareSite o where p.id = o.plannedNotificationsInternal.id and o.nciInstituteCode in (:nciCodeList)").setParameterList("nciCodeList",nciCodeList);
//          Query query = session.createQuery("Select p from PlannedNotification as p, o from HealthcareSite as o where p.id = o.plannedNotificationsInternal.id and" +
//          		"o.nci_institute_code in (:nciCodeList)").setParameterList("nciCodeList",nciCodeList);
//          query.setEntity(0, nciCodeList);
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
        }catch(Exception e){
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
			if(String.valueOf(((Study)entity).getVersion()).equals(studyId)){
				//while saving the scheduled notifications, the interceptor is fired again
				//to prevent an infinite loop..we check to see if the study obj involved is the same
				//if so exit the interceptor immediately else continue processing
				log.error("********  NotificationInterceptor.onFlushDirty(): same hashcode - ");
				log.error(" entity.hashCode() ="+ entity.hashCode() + "studyId" +studyId);
				log.debug("exiting to prevent looping");
				return false;
			}else{
				studyId = String.valueOf(((Study)entity).getVersion());
				for (int i = 0; i < propertyNames.length; i++) {
					if (propertyNames[i].equals("coordinatingCenterStudyStatusInternal") && previousState != null && currentState != null ){		
							handleStudyStatusChange(previousState[i], currentState[i], entity);
					}
				}
			}
		}
		
		//StudySite status changes are spotted here for activating rules.
		if(entity instanceof StudySite){
			if(String.valueOf(((StudySite)entity).getVersion()).equals(studySiteId)){
				//while saving the scheduled notifications, the interceptor is fired again
				//to prevent an infinite loop..we check to see if the studySite obj involved is the same
				//if so exit the interceptor immediately else continue processing
				log.debug("exiting to prevent looping");
				return false;
			}else{
				studySiteId = String.valueOf(((StudySite)entity).getVersion());
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
			if(String.valueOf(((StudySubject)entity).getVersion()).equals(studySubjectId)){
				//while saving the scheduled notifications, the interceptor is fired again
				//to prevent an infinite loop..we check to see if the studySite obj involved is the same
				//if so exit the interceptor immediately else continue processing
				log.debug("exiting to prevent looping");
				return false;
			}else{
				studySubjectId = String.valueOf(((StudySubject)entity).getVersion());
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
			if(currentRegStatus.equals(RegistrationWorkFlowStatus.ENROLLED) &&
				(previousRegStatus == null || previousRegStatus.equals(RegistrationWorkFlowStatus.PENDING) ||
						previousRegStatus.equals(RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED) || previousRegStatus.equals(RegistrationWorkFlowStatus.RESERVED)) ){
				event = NotificationEventTypeEnum.NEW_REGISTRATION_EVENT;
				//run rules seperately for the accruals use case
				activateRulesForAccruals(studySubject);
			} else if(currentRegStatus.equals(RegistrationWorkFlowStatus.OFF_STUDY)){
				event = NotificationEventTypeEnum.SUBJECT_REMOVED_OFF_STUDY;
			} else {
				event = NotificationEventTypeEnum.REGISTATION_STATUS_CHANGE;
			}
			List <HealthcareSite> hcsList = getSites(studySubject); 
				
			for(PlannedNotification pn: getPlannedNotifications(hcsList)){
				if(pn.getEventName().equals(event)){
					objects.add(pn);
					rulesDelegationService.activateRules(event, objects);
					objects.remove(pn);
				}
				//activate rules if the event occured is a new Reg event and we have a stored planned notification for
				//registration status changes. In other words...new Reg event shud send out Reg status changed notification.
				if(pn.getEventName().equals(NotificationEventTypeEnum.REGISTATION_STATUS_CHANGE) &&
						event.equals(NotificationEventTypeEnum.NEW_REGISTRATION_EVENT)){
					objects.add(pn);
					rulesDelegationService.activateRules(NotificationEventTypeEnum.REGISTATION_STATUS_CHANGE, objects);
					objects.remove(pn);
				}
			}
		}
		log.debug(this.getClass().getName() + ": exiting handleNewStudySubjectSaved()");
	}
	
	
	/* Actives rules for the STUDY_SITE_STATUS_CHANGED_EVENT
	 */
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
			List <HealthcareSite> hcsList = getSites(entity);
			for(PlannedNotification pn: getPlannedNotifications(hcsList)){
				if(pn.getEventName().equals(event)){
					objects.add(pn);
					rulesDelegationService.activateRules(event, objects);
					objects.remove(pn);
				}
			}
		}		
	}
	
	/* Activates rules for the NEW_STUDY_SAVED_EVENT & STUDY_STATUS_CHANGED_EVENT
	 */
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
		//removed......currentCoordinatingCenterStudyStatus.equals(CoordinatingCenterStudyStatus.PENDING) ||
		if(currentCoordinatingCenterStudyStatus != null && currentCoordinatingCenterStudyStatus.equals(previousCoordinatingCenterStudyStatus)){
			log.debug("There is no change in the study's status");
			//do nothing if the final status is same as the previous status.
		} else {
			//if the prev status is ready to open and current status is active then its a new study
			//else its a study status change.
			//removed ....(previousCoordinatingCenterStudyStatus == null || previousCoordinatingCenterStudyStatus == PENDING)
			if(currentCoordinatingCenterStudyStatus.equals(CoordinatingCenterStudyStatus.OPEN) &&   
				previousCoordinatingCenterStudyStatus.equals(CoordinatingCenterStudyStatus.READY_TO_OPEN) ||
				previousCoordinatingCenterStudyStatus.equals(CoordinatingCenterStudyStatus.PENDING) ){ 
				event = NotificationEventTypeEnum.NEW_STUDY_SAVED_EVENT;
				log.debug("Creating a new study event in the interceptor");
			} else {
				event = NotificationEventTypeEnum.STUDY_STATUS_CHANGED_EVENT;
				log.debug("Creating a study status changed event in the interceptor");
			}
			List <HealthcareSite> hcsList = getSites(entity);
			for(PlannedNotification pn: getPlannedNotifications(hcsList)){
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

	
	/* Called for new registrations only. Checks if accrual notifications are configured.
	 * fires rules if thresholds are met	 */
	private void activateRulesForAccruals(Object studySubjectObj){
		StudySubject studySubject = null;
		StudyOrganization studyOrg = null;
		StudySite studySite = null;
		
		if(studySubjectObj instanceof StudySubject){
			studySubject = (StudySubject)studySubjectObj;
		}else{
			return;
		}
		
		List<Object> objects = new ArrayList<Object>();
		objects.add(studySubject);
		int studyAccruals = 0;
		int threshold = 0;
		List <HealthcareSite> hcsList = getSites(studySubject);
		for(PlannedNotification pn: getPlannedNotifications(hcsList)){
			if(pn.getEventName().equals(NotificationEventTypeEnum.STUDY_ACCRUAL_EVENT)){
				Iterator <StudyOrganization>iter = studySubject.getStudySite().getStudy().getStudyOrganizations().iterator();
				while(iter.hasNext()){
					studyOrg = iter.next();
					//ensure that the host org is in the studyOrg list for the study
					if(studyOrg.getHealthcareSite().getNciInstituteCode().equalsIgnoreCase(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE))){
						studyAccruals = calculateStudyAccrual(studySubject);
						threshold = studySubject.getStudySite().getStudy().getTargetAccrualNumber().intValue();
						//if accruals exceed specified threshold value then send out email
						if(studyAccruals*100/threshold >= pn.getStudyThreshold()){
							objects.add(pn);
							rulesDelegationService.activateRules(NotificationEventTypeEnum.STUDY_ACCRUAL_EVENT, objects);
						}
					}
				}
			}
			if(pn.getEventName().equals(NotificationEventTypeEnum.STUDY_SITE_ACCRUAL_EVENT)){
				studySite = studySubject.getStudySite();
				//ensure that the host org is in the studySite list for the study
				if(studySite.getHealthcareSite().getNciInstituteCode().equalsIgnoreCase(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE))){
					studyAccruals = calculateStudySiteAccrual(studySubject);
					threshold = studySubject.getStudySite().getTargetAccrualNumber().intValue();
					//if accruals exceed specified threshold value then send out email
					if((studyAccruals/threshold)*100 >= pn.getStudySiteThreshold()){
						objects.add(pn);
						rulesDelegationService.activateRules(NotificationEventTypeEnum.STUDY_SITE_ACCRUAL_EVENT, objects);
					}
				}
			}
		}
	}
	
	/*
	 * Returns the list of sites associated with the study/studySite/studySubject(depending on the event).
	 */
	private List<HealthcareSite> getSites(Object entity){
		
		List<HealthcareSite> hcsList = new ArrayList<HealthcareSite>();
		if(entity instanceof StudySubject){
			hcsList.add(((StudySubject)entity).getStudySite().getHealthcareSite());
			hcsList.add(((StudySubject)entity).getStudySite().getStudy().getStudyCoordinatingCenter().getHealthcareSite());
		}
		if(entity instanceof StudySite){
			hcsList.add(((StudySite)entity).getHealthcareSite());
			hcsList.add(((StudySite)entity).getStudy().getStudyCoordinatingCenter().getHealthcareSite());
		}
		if(entity instanceof Study){
			for(StudyOrganization so: ((Study)entity).getStudyOrganizations()){
				hcsList.add(so.getHealthcareSite());
			}
		}
		//defaulting to the hosting site if nothing is found
		if(hcsList.size() == 0){
			String localNciCode = this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);
			hcsList.add(healthcareSiteDao.getByNciInstituteCode(localNciCode));
		}
		
		removeDuplicates(hcsList);
		return hcsList;
	}
	
	
	/*
     * removes multiple occurrences of any sites that may result in emails being sent out twice
     */
    private void removeDuplicates(List<HealthcareSite> hcsList){
    	Set <HealthcareSite> set = new HashSet<HealthcareSite>();
    	set.addAll(hcsList);
    	hcsList.clear();
    	hcsList.addAll(set);
    }
    
	
	private int calculateStudyAccrual(StudySubject studySubject){
		return studySubject.getStudySite().getStudy().getCurrentAccrualCount().intValue();
	}
	
	private int calculateStudySiteAccrual(StudySubject studySubject){
		return studySubject.getStudySite().getCurrentAccrualCount();
	}
	
	/* public void handleNewStudySubjectSaved(StudySubject studySubject){
			List<Object> objects = new ArrayList<Object>();
			objects.add(studySubject);
			
			for(PlannedNotification pn: getHostingOrganization()){
				if(pn.getEventName().equals(NotificationEventTypeEnum.NEW_REGISTRATION_EVENT)){
					objects.add(pn);
					rulesDelegationService.activateRules(NotificationEventTypeEnum.NEW_REGISTRATION_EVENT, objects);
					objects.remove(pn);
				}
			}
		}
		
		public void handleNewStudySaved(Study study){
			List<Object> objects = new ArrayList<Object>();
			objects.add(study);
			
			for(PlannedNotification pn: getHostingOrganization()){
				if(pn.getEventName().equals(NotificationEventTypeEnum.NEW_STUDY_SAVED_EVENT)){
					objects.add(pn);
					rulesDelegationService.activateRules(NotificationEventTypeEnum.NEW_STUDY_SAVED_EVENT, objects);
					objects.remove(pn);
				}
			}
		}
		
		public void handleNewStudySiteSaved(StudySite studySite){
			List<Object> objects = new ArrayList<Object>();
			objects.add(studySite);
			
			for(PlannedNotification pn: getHostingOrganization()){
				if(pn.getEventName().equals(NotificationEventTypeEnum.NEW_STUDY_SITE_SAVED_EVENT)){
					objects.add(pn);
					rulesDelegationService.activateRules(NotificationEventTypeEnum.NEW_STUDY_SITE_SAVED_EVENT, objects);
					objects.remove(pn);
				}
			}
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

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

}
