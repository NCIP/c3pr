package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.registration.RegistrationControllerUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class RegistrationOverviewTab<C extends StudySubjectWrapper> extends
		RegistrationTab<C> {

	private StudySubjectService studySubjectService;

	private RegistrationControllerUtils registrationControllerUtils;

	private EpochDao epochDao;
	
	private StudyDao studyDao ;

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

	@Override
	public Map<String, Object> referenceData(C command) {
		StudySubjectWrapper wrapper = (StudySubjectWrapper) command;
		StudySubject studySubject = wrapper.getStudySubject();
		Map<String, Object> map = new HashMap<String, Object>();
		boolean actionRequired = false;
		boolean newRegistration = false;
		String actionLabel = "";
		String armAssigned = "";
		String armAssignedLabel = "";
		if (studySubject.getScheduledEpoch() != null
				&& (studySubject.getScheduledEpoch()).getScheduledArm() != null
				&& (studySubject.getScheduledEpoch()).getScheduledArm()
						.getArm() != null) {
			armAssigned = (studySubject.getScheduledEpoch()).getScheduledArm()
					.getArm().getName();
			armAssignedLabel = "Arm";
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
		map.put("hasCompanions", studySubject.getStudySite().getStudy()
				.getCompanionStudyAssociations().size() > 0);
		map.put("actionRequired", actionRequired);
		map.put("actionLabel", actionLabel);
		map.put("newRegistration", newRegistration);
		map.put("armAssigned", armAssigned);
		map.put("armAssignedLabel", armAssignedLabel);
		map.put("takeSubjectOffStudy", canTakeSubjectOffStudy(studySubject));
		map.put("canEdit", canEditRegistration(studySubject));
		map.put("reconsentRequired", reconsentRequired(studySubject));
		map.put("registerableWithCompanions", registrationControllerUtils
				.registerableAsorWithCompanion(studySubject));
		map.put("requiresMultiSite", studySubjectService
				.requiresExternalApprovalForRegistration(studySubject));
		map.put("hasEndpointMessages", new Boolean(studySubject.getStudySite()
				.getStudy().getStudyCoordinatingCenter()
				.getRegistrationEndpoints().size() > 0));
		registrationControllerUtils.addAppUrls(map);
		return map;
	}

	public ModelAndView getMessageBroadcastStatus(HttpServletRequest request,
			Object commandObj, Errors error) {
		StudySubjectWrapper wrapper = (StudySubjectWrapper) commandObj;
		StudySubject command = wrapper.getStudySubject();
		String responseMessage = null;
		try {
			responseMessage = studySubjectService
					.getCCTSWofkflowStatus(command).getDisplayName();
		} catch (Exception e) {
			responseMessage = "error";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("responseMessage", responseMessage);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}

	public ModelAndView broadcastRegistration(HttpServletRequest request,
			Object commandObj, Errors error) {
		StudySubjectWrapper wrapper = (StudySubjectWrapper) commandObj;
		StudySubject command = wrapper.getStudySubject();
		try {
			this.studySubjectService.broadcastMessage(command);
			return getMessageBroadcastStatus(request, commandObj, error);
		} catch (C3PRCodedException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("responseMessage", e.getMessage());
			return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
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

	public StudySubjectService getStudySubjectService() {
		return studySubjectService;
	}

	public void setStudySubjectService(StudySubjectService studySubjectService) {
		this.studySubjectService = studySubjectService;
	}

	public ModelAndView refreshEnrollmentSection(HttpServletRequest request,
			Object obj, Errors errors) {
		StudySubjectWrapper wrapper = (StudySubjectWrapper) obj;
		String regId = request.getParameter("registrationId");
		StudySubject studySubject = studySubjectDao.getById(Integer
				.parseInt(regId));
		studySubjectDao.initialize(studySubject);
		wrapper.setStudySubject(studySubject);
		HashMap map = new HashMap();
		map.put("command", wrapper);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}

	private boolean canEditRegistration(StudySubject studySubject) {
		if (studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.OFF_STUDY) {
			return true;
		}
		return false;
	}

	private boolean reconsentRequired(StudySubject studySubject) {
		if (studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.OFF_STUDY
				&& studySubject.getInformedConsentVersion() != studySubject
						.getStudySite().getStudy().getConsentVersion()) {
			return true;
		}
		return false;
	}

	private boolean canTakeSubjectOffStudy(StudySubject studySubject) {
		if (studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.ENROLLED
				&& studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.REGISTERED) {
			return true;
		}
		return false;
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
			if(epoch.getReservationIndicator()){
				return "RESERVED" ;
			}else {
				return "ENROLLED" ;
			}
		}
		return "flow";
	}

	private boolean isNotRegisterable(StudySubject studySubject, Epoch epoch) {
		if (epoch.getEpochOrder() == studySubject.getCurrentScheduledEpoch().getEpoch().getEpochOrder() && studySubject.getCurrentScheduledEpoch().getEpoch().getId() != epoch.getId()) {
			return true;
		} else if (epoch.getEpochOrder() < studySubject.getCurrentScheduledEpoch().getEpoch().getEpochOrder()) {
			return true;
		}
		return false;
	}

	private boolean isCurrentScheduledEpoch(StudySubject studySubject, Epoch epoch) {
		if (studySubject.getCurrentScheduledEpoch().getEpoch().getId().intValue() == epoch.getId().intValue()) {
			return true;
		}
		return false;
	}

	private boolean isRequiresAdditionalInfo(StudySubject studySubject,Epoch epoch) {
		if (epoch.getEligibilityCriteria().size() > 0 || epoch.getStratificationCriteria().size() > 0 || epoch.getRandomizedIndicator() || epoch.getArms().size() > 0) {
			return true;
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
			return "Cannot assign subject to this epoch since he has already been registered once on the epoch." ;
		}else if(isAccrualCeilingReached(epoch.getId())){
			return "Accrual Ceiling for this Epoch alreay reached. Cannot enroll any more subjects on this epoch." ;
		}else if(isNotRegisterable(studySubject, epoch)){
			return "Cannot assign subject to this epoch." ;
		}else if(isCurrentScheduledEpoch(studySubject, epoch)){
			return "Current Registration" ;
		}else if(isRequiresAdditionalInfo(studySubject, epoch)){
			return "Required" ;
		}else{
			return "Not Required" ;
		}
	}

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}
}
