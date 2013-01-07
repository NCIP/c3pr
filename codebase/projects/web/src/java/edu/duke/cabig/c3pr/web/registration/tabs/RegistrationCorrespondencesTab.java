package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.constants.AMPMEnum;
import edu.duke.cabig.c3pr.constants.CorrespondencePurpose;
import edu.duke.cabig.c3pr.constants.CorrespondenceType;
import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.constants.TimeZoneEnum;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.dao.PlannedNotificationDao;
import edu.duke.cabig.c3pr.domain.Correspondence;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.service.impl.RulesDelegationServiceImpl;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.ajax.StudyAjaxFacade;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;


/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 12:51:05 PM To change this
 * template use File | Settings | File Templates.
 */
public class RegistrationCorrespondencesTab<C extends StudySubjectWrapper> extends RegistrationTab<C> {

	
	private StudyAjaxFacade studyAjaxFacade;
	
	private PersonUserDao personUserDao;
	
	private RulesDelegationServiceImpl rulesDelegationService;
	
	public void setRulesDelegationService(
			RulesDelegationServiceImpl rulesDelegationService) {
		this.rulesDelegationService = rulesDelegationService;
	}

	public void setPlannedNotificationDao(
			PlannedNotificationDao plannedNotificationDao) {
		this.plannedNotificationDao = plannedNotificationDao;
	}

	private PlannedNotificationDao plannedNotificationDao;
	
    public void setPersonUserDao(PersonUserDao personUserDao) {
		this.personUserDao = personUserDao;
	}

	public void setStudyAjaxFacade(StudyAjaxFacade studyAjaxFacade) {
		this.studyAjaxFacade = studyAjaxFacade;
	}

	public RegistrationCorrespondencesTab() {
        super("Correspondence", "Correspondence", "registration/reg_correspondences");
        
    }
	
	
    public void postProcess(HttpServletRequest request, C command, Errors errors) {
    	StudySubject studySubject = ((StudySubjectWrapper) command).getStudySubject();
    	
    	super.postProcess(request, command, errors);
    	
    	Correspondence correspondence = null;
    	if(!StringUtils.isBlank(request.getParameter("notifiedCorrespondenceId"))){
    		int correspondenceId = Integer.parseInt(request.getParameter("notifiedCorrespondenceId"));
    		correspondence = studySubject.getByCorrespondenceId(correspondenceId);
    	}
    	// activate rules on the correspondence
    	if(correspondence!=null){

			List<Object> objects = new ArrayList<Object>();
			objects.add(correspondence);
				PlannedNotification plannedNotification = plannedNotificationDao.getByEventName(NotificationEventTypeEnum.
						CORRESPONDENCE_CREATED_OR_UPDATED_EVENT);
				// delete all existing recipients so that notification is not sent to them
				plannedNotification.getUserBasedRecipient().clear();
				plannedNotification.getContactMechanismBasedRecipient().clear();
				plannedNotification.getRoleBasedRecipient().clear();
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
    }
    
    @Override
	public Map<String, Object> referenceData(C command) {
    	StudySubjectWrapper studySubjectWrapper = (StudySubjectWrapper)command;
    	StudySubject studySubject = studySubjectWrapper.getStudySubject();
    	
        Map<String, Object> refdata = super.referenceData();
        
        Map<String,Object> correspondenceTypesMap = new HashMap<String,Object>();
        for(CorrespondenceType correspondenceType : CorrespondenceType.values()){
        	correspondenceTypesMap.put(correspondenceType.getName(), correspondenceType.getCode());
        }
        refdata.put("correspondenceTypes",correspondenceTypesMap);
        
        Map<String,Object> correspondencePurposesMap = new HashMap<String,Object>();
        for(CorrespondencePurpose correspondencePurpose : CorrespondencePurpose.values()){
        	correspondencePurposesMap.put(correspondencePurpose.getName(), correspondencePurpose.getCode());
        }
        refdata.put("correspondencePurposes",correspondencePurposesMap);
        
        Map<String,Object> amPmTypeMap = new HashMap<String,Object>();
        for(AMPMEnum ampm :AMPMEnum.values()){
        	amPmTypeMap.put(ampm.getName(), ampm.getCode());
        }
        refdata.put("amPmRefData",amPmTypeMap);
        
        
        Map<String,Object> timeZonesMap = new HashMap<String,Object>();
        for(TimeZoneEnum timeZone : TimeZoneEnum.values()){
        	timeZonesMap.put(timeZone.getName(), timeZone.getCode());
        }
        refdata.put("timeZonesRefData",timeZonesMap);
        
        Map<Integer,String> minsRefData = new LinkedHashMap<Integer,String>();
        for(int i =0; i< 60; i++){
        	if(i<10){
        		minsRefData.put(new Integer(i),"0"+i);
        	}else{
        		minsRefData.put(new Integer(i),String.valueOf(i));
        	}
        }
        refdata.put("minsRefData",minsRefData);
        
       Map<Integer,String> hoursRefData = new LinkedHashMap<Integer,String>();
        for(int i =1; i<= 12; i++){
        	if(i<10){
        		hoursRefData.put(new Integer(i),"0"+i);
        	}else{
        		hoursRefData.put(new Integer(i),String.valueOf(i));
        	}
        }
        refdata.put("hoursRefData",hoursRefData);
        addConfigMapToRefdata(refdata, "yesNo");
        
        if(studyAjaxFacade.getRegistrationId() != studySubject.getId().intValue()){
        	
        	studyAjaxFacade.setRegistrationId(studySubject.getId());
	        Study study =((StudySubjectWrapper)command).getStudySubject().getStudySite().getStudy();
	    	
	        Set<PersonUser> staffWithAccess = new HashSet<PersonUser>();
	    	List<PersonUser> hcsRSList = new ArrayList<PersonUser>();
	    	for(StudyOrganization studyOrganization : study.getStudyOrganizations()){
	    		// add all the study personnel at all the study organizations
	    		for(StudyPersonnel studyPerson: studyOrganization.getStudyPersonnel()){
	    			staffWithAccess.add(studyPerson.getPersonUser());
	    		}
	    		
	    		int hcsId = studyOrganization.getHealthcareSite().getId();
	    		HealthcareSite hcs = healthcareSiteDao.getById(hcsId);
	
	    		//get all staff belonging to the org in question
	    		hcsRSList = personUserDao.getResearchStaffByOrganizationCtepCodeFromLocal(hcs, true);
	    		//get the sub list of staff (from the above list) who are scoped by study
	    		List<PersonUser> allStudyScopedRSAtSite = personUserDao.getAllStudyScopedStaff(hcsRSList, hcs);
	    		staffWithAccess.addAll(allStudyScopedRSAtSite);
	    	}
	    	
	    	studyAjaxFacade.getNotifiedPersonUsers().clear();
	    	studyAjaxFacade.getNotifiedPersonUsers().addAll(staffWithAccess);
	    	studySubjectWrapper.getNotifiedPersonUsers().clear();
	    	studySubjectWrapper.getNotifiedPersonUsers().addAll(staffWithAccess);
        } else{
        	studyAjaxFacade.getNotifiedPersonUsers().clear();
        	studyAjaxFacade.getNotifiedPersonUsers().addAll(studySubjectWrapper.getNotifiedPersonUsers());
        }
		
        
        return refdata;
    }
}
