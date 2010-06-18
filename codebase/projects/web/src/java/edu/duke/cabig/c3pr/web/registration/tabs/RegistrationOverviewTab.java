package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.constants.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import edu.duke.cabig.c3pr.utils.web.WebUtils;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.registration.RegistrationControllerUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class RegistrationOverviewTab<C extends StudySubjectWrapper> extends
		RegistrationTab<C> {
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger
			.getLogger(RegistrationOverviewTab.class);

	private StudySubjectService studySubjectService;

	private RegistrationControllerUtils registrationControllerUtils;

	private EpochDao epochDao;
	
	private StudyDao studyDao ;
	
	private CSMUserRepository csmUserRepository;

	public void setCsmUserRepository(CSMUserRepository csmUserRepository) {
		this.csmUserRepository = csmUserRepository;
	}

	public EpochDao getEpochDao() {
		return epochDao;
	}

	public void setEpochDao(EpochDao epochDao) {
		this.epochDao = epochDao;
	}

	public void setRegistrationControllerUtils(
			RegistrationControllerUtils registrationControllerUtils) {
		this.registrationControllerUtils = registrationControllerUtils;
	}

	public RegistrationOverviewTab() {
		super("Overview", "Overview", "registration/reg_overview");
	}
	
	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}
	
	public StudySubjectService getStudySubjectService() {
		return studySubjectService;
	}

	public void setStudySubjectService(StudySubjectService studySubjectService) {
		this.studySubjectService = studySubjectService;
	}

	@Override
	public Map<String, Object> referenceData(C command) {
		StudySubjectWrapper wrapper = (StudySubjectWrapper) command;
		StudySubject studySubject = wrapper.getStudySubject();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, List<Lov>> configMap = configurationProperty.getMap();
		boolean actionRequired = false;
		boolean newRegistration = false;
		String actionLabel = "";
		String armAssigned = "";
		String armAssignedLabel = "";
		
		if ((studySubject.getScheduledEpoch()).getScheduledArm() != null) {
			if (studySubject.getStudySite().getStudy().getBlindedIndicator()  && studySubject.getScheduledEpoch().getRequiresRandomization()) {
				armAssigned = (studySubject.getScheduledEpoch())
						.getScheduledArm().getKitNumber();
				armAssignedLabel = "Kit";
			} else if ((studySubject.getScheduledEpoch()).getScheduledArm()
					.getArm() != null) {
				armAssigned = (studySubject.getScheduledEpoch())
						.getScheduledArm().getArm().getName();
				armAssignedLabel = "Arm";
			}

		}
		
		if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING
				&& studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.COMPLETE
				&& studySubject.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE) {
			actionRequired = true;
			if (studySubject.getScheduledEpochs().size() > 1) {
				actionLabel = "Transfer subject";
			} else if (studySubject.getScheduledEpoch().getEpoch()
					.isEnrolling()) {
				actionLabel = "Register";
			} else {
				actionLabel = "Save";
			}
			if (studySubject.getScheduledEpoch().getRequiresRandomization()) {
				actionLabel += " & Randomize";
			}
		}
		if (studySubject.getScheduledEpochs().size() == 1) {
			newRegistration = true;
		}
		map.put("hasCompanions", studySubject.getStudySite().getStudy().getStudyVersion()
				.getCompanionStudyAssociations().size() > 0);
		map.put("actionRequired", actionRequired);
		map.put("actionLabel", actionLabel);
		map.put("newRegistration", newRegistration);
		map.put("armAssigned", armAssigned);
		map.put("armAssignedLabel", armAssignedLabel);
		map.put("takeSubjectOffStudy", studySubject.canTakeSubjectOffStudy());
		map.put("canFailScreening", studySubject.canFailScreening());
		map.put("isCompleteRegistration", studySubject.isCurrentEpochWorkflowComplete());
		map.put("registerableWithCompanions", registrationControllerUtils
				.registerableAsorWithCompanion(studySubject));
		map.put("requiresMultiSite", studySubjectService
				.requiresExternalApprovalForRegistration(studySubject));
		map.put("hasEndpointMessages", new Boolean(studySubject.getStudySite()
				.getStudy().getStudyCoordinatingCenter()
				.getRegistrationEndpoints().size() > 0));
		registrationControllerUtils.addAppUrls(map);
    	map.put("paymentMethods", configMap.get("paymentMethods"));
    	map.put("canChangeEpoch", studySubject.canChangeEpoch());
		map.put("companions", registrationControllerUtils.getCompanionStudySubject(studySubject.getSystemAssignedIdentifiers().get(0), studySubject));
		if(configuration.get(Configuration.ESB_ENABLE).equals("true")){
			map.put("canBroadcast", "true");
        }
		map.put("isAdmin", WebUtils.isAdmin());
		map.put("isStudyCoordinator", WebUtils.isStudyCoordinator());
		map.put("canEditRegistrationRecord", canEditRegistrationRecord(studySubject));
		map.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
        map.put("ethnicGroupCodes", configMap.get("ethnicGroupCode"));
        map.put("raceCodes", configMap.get("raceCode"));
		return map;
	}
	
	private Boolean canEditRegistrationRecord(StudySubject studySubject) {
		return WebUtils.isAdmin() && (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.REGISTERED) ;
	}

	public ModelAndView getMessageBroadcastStatus(HttpServletRequest request, Object commandObj,
            Errors error) {
		StudySubject command = ((StudySubjectWrapper) commandObj).getStudySubject();
		command = studySubjectDao.getById(command.getId());
		((StudySubjectWrapper) commandObj).setStudySubject(command);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
	}

    public ModelAndView sendMessageToESB(HttpServletRequest request, Object commandObj, Errors error) {
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) commandObj;
		StudySubject command = wrapper.getStudySubject();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isAdmin", WebUtils.isAdmin());
    	try {
            log.debug("Sending message to CCTS esb");
            studySubjectService.broadcastMessage(command);
            return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
        }
        catch (C3PRCodedException e) {
        	log.error(e);
            map.put("codedError", e);
            return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
        }
        catch (Exception e) {
        	log.error(e);
            map.put("generalError", e);
            return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
        }finally{
        	command = studySubjectDao.getById(command.getId());
            ((StudySubjectWrapper) commandObj).setStudySubject(command);
        }
    }

	public ModelAndView showEndpointMessage(HttpServletRequest request,
			Object obj, Errors errors) {
		StudySubjectWrapper wrapper = ((StudySubjectWrapper) obj);
		StudySubject studySubject = wrapper.getStudySubject();
		StudyOrganization studyOrganization = studySubject.getStudySite()
				.getStudy().getStudyCoordinatingCenter();
		Map map = new HashMap();
		map.put("site", studyOrganization);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}

	public ModelAndView refreshEnrollmentSection(HttpServletRequest request,
			Object obj, Errors errors) {
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
	}

	public ModelAndView getEpochSection(HttpServletRequest request,
			Object commandObj, Errors error) {
		C command = (C) commandObj;
		StudySubject studySubject = command.getStudySubject();
		int id = -1;
		Map<String, Object> map = new HashMap<String, Object>();
		id = Integer.parseInt(request.getParameter("epochId"));
		Epoch epoch = epochDao.getById(id);
		map.put("epoch", epoch);
		map.put("alreadyRegistered", isAlreadyRegistered(studySubject, epoch));
		map.put("notRegistrable", isNotRegisterable(studySubject, epoch));
		map.put("requiresAdditionalInfo", isRequiresAdditionalInfo(
				studySubject, epoch));
		map.put("isCurrentScheduledEpoch", isCurrentScheduledEpoch(
				studySubject, epoch));
		map.put("acrrualCeilingReached", isAccrualCeilingReached(id));
		map.put("additionalInformation", getAdditionalInformation(studySubject, epoch));
		map.put("transferToStatus", getPossibleStatus(studySubject, epoch));

		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}

	private String getPossibleStatus(StudySubject studySubject, Epoch epoch) {
		if(!isRequiresAdditionalInfo(studySubject, epoch)){
			if(epoch.getType() == EpochType.RESERVING){
				return "RESERVED" ;
			}else if(epoch.getEnrollmentIndicator()) {
				return "ENROLLED" ;
			}else {
				return "REGISTERED_BUT_NOT_ENROLLED" ;
			}
		}
		return "flow";
	}

	private boolean isNotRegisterable(StudySubject studySubject, Epoch epoch) {
		return (epoch.getEpochOrder() <= studySubject.getScheduledEpoch().getEpoch().getEpochOrder());
	}

	private boolean isCurrentScheduledEpoch(StudySubject studySubject, Epoch epoch) {
		if (studySubject.getScheduledEpoch().getEpoch().getId().intValue() == epoch.getId().intValue()) {
			return true;
		}
		return false;
	}

	private boolean isRequiresAdditionalInfo(StudySubject studySubject,Epoch epoch) {
		
		if ((epoch.getEnrollmentIndicator() && studySubject.getStartDate()== null ) || epoch.getEligibilityCriteria().size() > 0 || epoch.getStratificationCriteria().size() > 0 || epoch.getRandomizedIndicator() || epoch.getArms().size() > 0 || isWorkPendingOnMandatoryCompanion(studySubject, epoch)) {
			return true;
		}
		return false;
	}

	private boolean isWorkPendingOnMandatoryCompanion(StudySubject studySubject, Epoch epoch) {
		if(!epoch.getEnrollmentIndicator()){
			return false ; 
		}
		for(CompanionStudyAssociation companionStudyAssociation : studySubject.getStudySite().getStudy().getStudyVersion().getCompanionStudyAssociations()){
			if (companionStudyAssociation.getMandatoryIndicator()) {
				boolean hasCorrespondingStudySubject = false;
				for (StudySubject childStudySubject : studySubject.getChildStudySubjects()) {
					if (childStudySubject.getStudySite().getStudy().equals(
							companionStudyAssociation.getCompanionStudy())) {
						hasCorrespondingStudySubject = true;
					}
				}
				if (!hasCorrespondingStudySubject)
					return true;
			}
		}	
		for (StudySubject childStudySubject : studySubject.getChildStudySubjects()) {
			if(!childStudySubject.getDataEntryStatus()){
				return true ;
			}
			if(childStudySubject.getRegWorkflowStatus()!=RegistrationWorkFlowStatus.ENROLLED){
				CompanionStudyAssociation studyAssociation = studySubject.getMatchingCompanionStudyAssociation(childStudySubject);
				if (studyAssociation != null) {
					if (studyAssociation.getMandatoryIndicator()) {
						if (!childStudySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator()) {
							return true;
						}
						if (childStudySubject.getRegWorkflowStatus()!=RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED || childStudySubject.getScheduledEpoch().getScEpochWorkflowStatus()==ScheduledEpochWorkFlowStatus.PENDING){
							return true;
						}
						
					}
				}
			}
		}
		return false;
	}

	private boolean isAccrualCeilingReached(int id) {
		return studySubjectRepository.isEpochAccrualCeilingReached(id) ;
	}
	
	private boolean isAlreadyRegistered(StudySubject studySubject, Epoch epoch) {
		for (ScheduledEpoch scheduledEpoch : studySubject.getScheduledEpochs()) {
			if (scheduledEpoch.getEpoch().getId() == epoch.getId()) {
				return true;
			}
		}
		return false;
	}
	
	private String getAdditionalInformation(StudySubject studySubject, Epoch epoch) {
		if(isAlreadyRegistered(studySubject, epoch)){
			//return "Cannot assign subject to this epoch since he has already been registered once on the epoch." ;
			return "";
		}else if(isAccrualCeilingReached(epoch.getId())){
			//return "Accrual Ceiling for this Epoch alreay reached. Cannot enroll any more subjects on this epoch." ;
			return "" ;
		}else if(isNotRegisterable(studySubject, epoch)){
			//return "Cannot assign subject to this epoch." ;
			return "" ;
		}else if(isCurrentScheduledEpoch(studySubject, epoch)){
			return "Current Registration" ;
		}else if(isRequiresAdditionalInfo(studySubject, epoch)){
			return "Additional information is required to register on this epoch" ;
		}else{
			return "Additional information is not required to register on this epoch" ;
		}
	}
	

	
    public ModelAndView editRegistration(HttpServletRequest request, Object command , Errors error) {
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
	}
    
    public ModelAndView invalidateRegistration(HttpServletRequest request, Object obj, Errors errors) {
		StudySubjectWrapper wrapper = ((StudySubjectWrapper) obj);
		StudySubject studySubject = wrapper.getStudySubject();
		studySubject = studySubjectRepository.invalidateRegistration(studySubject);
		wrapper.setStudySubject(studySubject);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
	}
    
    public ModelAndView updateEligibility(HttpServletRequest request, Object obj, Errors errors) {
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
	}
    
    public ModelAndView updateStratumGroupNumber(HttpServletRequest request, Object obj, Errors errors) {
		StudySubjectWrapper wrapper = ((StudySubjectWrapper) obj);
		StudySubject studySubject = wrapper.getStudySubject();
		Integer stratumGroupNumber;
		try {
			stratumGroupNumber = studySubject.getScheduledEpoch().getStratumGroup().getStratumGroupNumber();
			studySubject.getScheduledEpoch().setStratumGroupNumber(stratumGroupNumber);
			Map<String, String> map = new HashMap<String, String>();
			if(stratumGroupNumber != null){
				map.put(AjaxableUtils.getFreeTextModelName(), stratumGroupNumber.toString());
			}
		    return new ModelAndView("", map);
		} catch (C3PRBaseException e) {
			e.printStackTrace();
		} 
		return null;
	}
    
    @Override
    public void postProcess(HttpServletRequest request, C command, Errors errors) {
    	List<EligibilityCriteria> eligibilityCriteria = new ArrayList<EligibilityCriteria>();
    	if(WebUtils.hasSubmitParameter(request, "allowWaiver")){
    		for(Integer subjectAnswerId : command.getWaiveEligibilityCrieteria()){
    			for(SubjectEligibilityAnswer subjectEligibilityAnswer : command.getStudySubject().getScheduledEpoch().getWaivableEligibilityAnswers()){
    				if(subjectEligibilityAnswer.getId().equals(subjectAnswerId)){
    					eligibilityCriteria.add(subjectEligibilityAnswer.getEligibilityCriteria());
    				}
    			}
    		}
    	}
    	SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
    	String userName = SecurityUtils.getUserName(authentication);
		ResearchStaff researchStaff = (ResearchStaff)csmUserRepository.getUserByName(userName);
    	StudySubject studySubject = studySubjectRepository.allowEligibilityWaiver(command.getStudySubject().getUniqueIdentifier(), eligibilityCriteria, researchStaff.getAssignedIdentifier());
    	command.setStudySubject(studySubject);
    	request.setAttribute("displayAllowWaiverSuccessMessage", "true");
    }
}
