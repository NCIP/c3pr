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

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.ContactMechanismBasedRecipient;
import edu.duke.cabig.c3pr.domain.Correspondence;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.SiteStatusHistory;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.service.impl.RulesDelegationServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StringUtils;

public class NotificationInterceptor extends EmptyInterceptor implements ApplicationContextAware {

	private static final Log log = LogFactory.getLog(NotificationInterceptor.class);
	
	RulesDelegationServiceImpl rulesDelegationService;
	
	private ApplicationContext applicationContext;
	
	private Configuration configuration;
	
	private HealthcareSiteDao healthcareSiteDao;
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * Gets the planned notifications for the list of sites that are passed in.
	 * This method access the db using a new session from the hibernate session factory.
	 * 
	 * @param hcsList the hcs list
	 * @return the planned notifications
	 */
	public List<PlannedNotification> getPlannedNotifications(List<HealthcareSite> hcsList){
		List<PlannedNotification> result;
		List<String> nciCodeList = new ArrayList<String>();
		for(HealthcareSite hcs: hcsList){
			if(hcs != null){
				nciCodeList.add(hcs.getPrimaryIdentifier());
			}
		}
		
		SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean("sessionFactory");
		Session session = sessionFactory.openSession(sessionFactory.getCurrentSession().connection());
		session.setFlushMode(FlushMode.MANUAL);
		result = new ArrayList<PlannedNotification>();
        try {
          Query query =  session.createQuery("select p from PlannedNotification p, HealthcareSite o where p.id = o.plannedNotificationsInternal.id and " +
          "o.identifiersAssignedToOrganization.primaryIndicator = 'true' and " +
          "o.identifiersAssignedToOrganization.value in (:nciCodeList)").setParameterList("nciCodeList",nciCodeList);
          
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
	}
	
	/**
	 * Gets the planned notifications for the list of sites that are passed in.
	 * This method access the db using a new session from the hibernate session factory.
	 * 
	 * @param hcsList the hcs list
	 * @return the planned notifications
	 */
	public List<PlannedNotification> getPlannedNotificationsForUpdateMasterSubject(){
		List<PlannedNotification> result;
		
		SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean("sessionFactory");
		Session session = sessionFactory.openSession(sessionFactory.getCurrentSession().connection());
		session.setFlushMode(FlushMode.MANUAL);
		result = new ArrayList<PlannedNotification>();
        try {
          Query query =  session.createQuery("from PlannedNotification p where p.eventName = :var").setString("var", NotificationEventTypeEnum.MASTER_SUBJECT_UPDATED_EVENT.toString());
          
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
	}
	
	public List<PlannedNotification> getPlannedNotificationsForCorrespondence(){
		List<PlannedNotification> result;
		
		SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean("sessionFactory");
		Session session = sessionFactory.openSession(sessionFactory.getCurrentSession().connection());
		session.setFlushMode(FlushMode.MANUAL);
		result = new ArrayList<PlannedNotification>();
        try {
          Query query =  session.createQuery("from PlannedNotification p left join fetch p.userBasedRecipientInternal where p.eventName = :var").setString("var", NotificationEventTypeEnum.CORRESPONDENCE_CREATED_OR_UPDATED_EVENT.toString());
          
          result = query.list().subList(0, 1);
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
	}


	
	/*
	 *  Return true only if the state is modified in anyway.
	 */
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
		String[] propertyNames, Type[] types) {
		log.debug(this.getClass().getName() + ": Entering onSave()");
		
		//Every new registration is handled here.
		if(entity instanceof StudySubjectStudyVersion){			
			handleNewStudySubjectSaved(null,((StudySubjectStudyVersion)entity).getStudySubject().getRegWorkflowStatus(), entity);
		}
/*		if(entity instanceof StudySubject){			
			handleNewStudySubjectSaved(null,((StudySubject)entity).getRegWorkflowStatus(), entity);
		}*/
		//Every new study...notification is onyl sent if new stuayd status is Active
		if(entity instanceof Study){			
			handleStudyStatusChange(null,((Study)entity).getCoordinatingCenterStudyStatus(), entity);
		}
		//New Study Site .....currently not implemented
		if(entity instanceof SiteStatusHistory){
			StudySite studySite = ((SiteStatusHistory)entity).getStudySite();
			handleStudySiteStatusChange(null, studySite.getSiteStudyStatus(), studySite);
		}
		
		//saving correspondence activate rules only if follow_up is needed and either person_spoken_to or notified_study_personnel is present
		
		/*if(entity instanceof Correspondence && ((Correspondence)entity).getFollowUpNeeded() && 
				(((Correspondence)entity).getPersonSpokenTo()!=null || ((Correspondence)entity).getNotifiedStudyPersonnel().size()>0)){
			activateRulesForNewOrUpdatedCorrespondence((Correspondence)entity);
		}*/
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
		if(entity instanceof Study){
				for (int i = 0; i < propertyNames.length; i++) {
					if (propertyNames[i].equals("coordinatingCenterStudyStatus") && previousState != null && currentState != null ){		
							handleStudyStatusChange(previousState[i], currentState[i], entity);
					}
				}
		}
		
		//StudySite status changes are spotted here for activating rules.
		if(entity instanceof SiteStatusHistory){
			StudySite studySite = ((SiteStatusHistory)entity).getStudySite();
				for (int i = 0; i < propertyNames.length; i++) {
					if (propertyNames[i].equals("siteStudyStatus")){
						if(previousState != null && currentState != null ){
							handleStudySiteStatusChange(previousState[i], currentState[i], studySite);
						}
					}
			}
		}		
		
		//When Reg status changes to Reserved or Registered
		if(entity instanceof StudySubject){	
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
		// Update Subject
		
		if(entity instanceof Participant && ((Participant)entity).getVersion()>0){
				activateRulesForUpdatingMasterSubject((Participant)entity);
		}
		
		//update correspondence activate rules only if follow_up is needed and either person_spoken_to or notified_study_personnel is present
		
		/*if(entity instanceof Correspondence && ((Correspondence)entity).getFollowUpNeeded() && 
				(((Correspondence)entity).getPersonSpokenTo()!=null || ((Correspondence)entity).getNotifiedStudyPersonnel().size()>0)){
			activateRulesForNewOrUpdatedCorrespondence((Correspondence)entity);
		}*/
		log.debug(this.getClass().getName() + ": Exiting onFlushDirty()");
		return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
	}
	
	/**
	 * Handle new study subject saved. Activates rules for the NEW_REGISTRATION_EVENT & REGISTATION_STATUS_CHANGE
	 * 
	 * @param previousState the previous state
	 * @param currentState the current state
	 * @param studySubject the study subject
	 */
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
			if(currentRegStatus.equals(RegistrationWorkFlowStatus.ON_STUDY) &&
				(previousRegStatus == null || previousRegStatus.equals(RegistrationWorkFlowStatus.PENDING) ||
						previousRegStatus.equals(RegistrationWorkFlowStatus.PENDING_ON_STUDY) || previousRegStatus.equals(RegistrationWorkFlowStatus.RESERVED)) ){
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
				//activate rules if the event occured is a new Registration event and we have a stored planned notification for
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
	
	
	/**
	 * Handle study site status change. Actives rules for the STUDY_SITE_STATUS_CHANGED_EVENT
	 * 
	 * @param previousState the previous state
	 * @param currentState the current state
	 * @param entity the entity
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
		
		// don't do anything when the study site is newly created in which case it's previous state will be null
		if(previousSiteStudyStatus == null || (previousSiteStudyStatus != null && 
				previousSiteStudyStatus.getCode().equals(currentSiteStudyStatus.getCode()))){
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
	
	/**
	 * Handle study status change. Activates rules for the NEW_STUDY_SAVED_EVENT & STUDY_STATUS_CHANGED_EVENT
	 * 
	 * @param previousState the previous state
	 * @param currentState the current state
	 * @param entity the entity
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
			if(currentCoordinatingCenterStudyStatus.equals(CoordinatingCenterStudyStatus.OPEN) &&  
			   ( previousCoordinatingCenterStudyStatus == null ||
				 previousCoordinatingCenterStudyStatus.equals(CoordinatingCenterStudyStatus.READY_TO_OPEN) ||
				 previousCoordinatingCenterStudyStatus.equals(CoordinatingCenterStudyStatus.PENDING) )){ 
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

	
	/**
	 * Activate rules for accruals. Called for new registrations only. 
	 * Checks if accrual notifications are configured. Fires rules if thresholds are met.
	 * 
	 * @param studySubjectObj the study subject obj
	 */
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
					if(studyOrg.getHealthcareSite().getPrimaryIdentifier().equalsIgnoreCase(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE))){
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
				if(studySite.getHealthcareSite().getPrimaryIdentifier().equalsIgnoreCase(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE))){
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
	
	/**
	 * Gets the sites. Returns the list of sites associated with the study/studySite/studySubject(depending on the event).
	 * 
	 * @param entity the entity
	 * @return the sites
	 */
	private List<HealthcareSite> getSites(Object entity){
		
		List<HealthcareSite> hcsList = new ArrayList<HealthcareSite>();
		if(entity instanceof StudySubject){
//			Original code, commented out for the time-being.
			/*if(((StudySubject)entity).getStudySite() != null){
				hcsList.add(((StudySubject)entity).getStudySite().getHealthcareSite());
				hcsList.add(((StudySubject)entity).getStudySite().getStudy().getStudyCoordinatingCenter().getHealthcareSite());
				
			}*/
			
			//Try this if the above doesn't work
			StudySubject studySubject = (StudySubject)entity;
			if(studySubject.getStudySubjectStudyVersion() != null && studySubject.getStudySubjectStudyVersion().getStudySiteStudyVersion() != null){
				hcsList.add(studySubject.getStudySubjectStudyVersion().getStudySiteStudyVersion().getStudySite().getHealthcareSite());
				hcsList.add(studySubject.getStudySiteVersion().getStudyVersion().getStudy().getStudyCoordinatingCenter().getHealthcareSite());
			}
		}
		if(entity instanceof StudySubjectStudyVersion){
			StudySubjectStudyVersion studySubjectStudyVersion = (StudySubjectStudyVersion)entity;
			hcsList.add(studySubjectStudyVersion.getStudySiteStudyVersion().getStudySite().getHealthcareSite());
			hcsList.add(studySubjectStudyVersion.getStudySiteStudyVersion().getStudySite().getStudy().getStudyCoordinatingCenter().getHealthcareSite());
		}
		
		if(entity instanceof SiteStatusHistory){
			hcsList.add(((SiteStatusHistory)entity).getStudySite().getHealthcareSite());
			hcsList.add(((SiteStatusHistory)entity).getStudySite().getStudy().getStudyCoordinatingCenter().getHealthcareSite());
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
			hcsList.add(healthcareSiteDao.getByPrimaryIdentifierFromLocal(localNciCode));
		}
		removeDuplicates(hcsList);
		return hcsList;
	}
	
	
    /**
	 * Removes the duplicate occurrences of any sites that may result in emails being sent out twice.
	 * 
	 * @param hcsList the hcs list
	 */
	private void removeDuplicates(List<HealthcareSite> hcsList){
    	Set <HealthcareSite> set = new HashSet<HealthcareSite>();
    	set.addAll(hcsList);
    	hcsList.clear();
    	hcsList.addAll(set);
    }
	
	/**
	 * Calculate study accrual.
	 * 
	 * @param studySubject the study subject
	 * 
	 * @return the int
	 */
	private int calculateStudyAccrual(StudySubject studySubject){
		return studySubject.getStudySite().getStudy().getCurrentAccrualCount().intValue();
	}
	
	/**
	 * Calculate study site accrual.
	 * 
	 * @param studySubject the study subject
	 * 
	 * @return the int
	 */
	private int calculateStudySiteAccrual(StudySubject studySubject){
		return studySubject.getStudySite().getCurrentAccrualCount();
	}
	
	/* Called for updating master subjects only. * fires rules to all registrars on whose sites subject is registered */
	private void activateRulesForUpdatingMasterSubject(Participant participant){
		
		List<Object> objects = new ArrayList<Object>();
		objects.add(participant);
		List <HealthcareSite> hcsList = getRegisteredSitesForSubject(participant);
		for(HealthcareSite hcs: hcsList){
			PlannedNotification plannedNotification = getPlannedNotificationsForUpdateMasterSubject().get(0);
			objects.add(plannedNotification);
			rulesDelegationService.activateRules(NotificationEventTypeEnum.MASTER_SUBJECT_UPDATED_EVENT, objects);
			objects.remove(plannedNotification);
			}
	}
	
	/* Called for updating master subjects only. * fires rules to all registrars on whose sites subject is registered */
	private void activateRulesForNewOrUpdatedCorrespondence(Correspondence correspondence){
		
		List<Object> objects = new ArrayList<Object>();
		objects.add(correspondence);
			PlannedNotification plannedNotification = getPlannedNotificationsForCorrespondence().get(0);
			plannedNotification.getUserBasedRecipient().clear();
			if(correspondence.getPersonSpokenTo() != null){
				String email = correspondence.getPersonSpokenTo().getEmail();
				if(!StringUtils.isBlank(email)){
					UserBasedRecipient ubr = new UserBasedRecipient();
					ubr.setPersonUser(correspondence.getPersonSpokenTo());
					ubr.setEmailAddress(email);
					plannedNotification.getUserBasedRecipient().add(ubr);
				}
			}
			
			for(PersonUser notifiedPersonUser : correspondence.getNotifiedStudyPersonnel()){
				String email = notifiedPersonUser.getEmail();
				if(!StringUtils.isBlank(email)){
					UserBasedRecipient ubr = new UserBasedRecipient();
					ubr.setPersonUser(notifiedPersonUser);
					ubr.setEmailAddress(email);
					plannedNotification.getUserBasedRecipient().add(ubr);
				}
			}
			
			
			objects.add(plannedNotification);
			rulesDelegationService.activateRules(NotificationEventTypeEnum.CORRESPONDENCE_CREATED_OR_UPDATED_EVENT, objects);
			objects.remove(plannedNotification);
	}
	
	public List<HealthcareSite> getRegisteredSitesForSubject(Participant participant){
		List<HealthcareSite> registeredSites = new ArrayList<HealthcareSite>();
		
		for(StudySubjectDemographics studySubjectDemographics: participant.getStudySubjectDemographics()){
			for(StudySubject registration:studySubjectDemographics.getRegistrations()){
				registeredSites.add(registration.getStudySite().getHealthcareSite());
			}
		}
		return registeredSites;
	}

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
