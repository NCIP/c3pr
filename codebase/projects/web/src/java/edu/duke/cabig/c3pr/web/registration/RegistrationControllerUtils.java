package edu.duke.cabig.c3pr.web.registration;

import java.util.HashMap;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.ajax.StudySubjectXMLFileImportAjaxFacade;

public class RegistrationControllerUtils {

	private StudySubjectService studySubjectService;
	private Configuration configuration;

	
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setStudySubjectService(StudySubjectService studySubjectService) {
		this.studySubjectService = studySubjectService;
	}

	public boolean isRegisterableOnPage(StudySubject studySubject) {
        return studySubject.isDataEntryComplete()
                        && !studySubject.getScheduledEpoch().getRequiresRandomization()
                        && !studySubjectService.requiresExternalApprovalForRegistration(studySubject)
                        && (studySubject.getScheduledEpoch().getEpoch().isEnrolling()?registrableStandalone(studySubject):true);
    }
    
    public static boolean registrableStandalone(StudySubject studySubject){
    	return studySubject.getParentStudySubject()==null && studySubject.getStudySite().getStudy().getCompanionStudyAssociations().size()==0;
    }
    public Map buildMap(StudySubject studySubject) {
		Map map = new HashMap();
		boolean reg_unregistered = false;
		boolean reg_registered = false;
		boolean reg_unapproved = false;
		boolean reg_pending = false;
		boolean reg_reserved = false;
		boolean reg_disapproved = false;
		boolean reg_nonenrolled = false;
		boolean reg_unrandomized = false;
		boolean epoch_unapproved = false;
		boolean epoch_pending = false;
		boolean epoch_approved = false;
		boolean epoch_nonenrolled = false;
		boolean epoch_unrandomized = false;
		boolean epoch_disapproved = false;
		boolean newRegistration = true;
		
		String armAssigned = "";
		String armAssignedLabel = "";
		if ((studySubject.getScheduledEpoch()).getScheduledArm() != null) {
			if (studySubject.getStudySite().getStudy().getBlindedIndicator()) {
				armAssigned = (studySubject.getScheduledEpoch())
						.getScheduledArm().getKitNumber();
				armAssignedLabel = "Kit Assigned";
			} else if ((studySubject.getScheduledEpoch()).getScheduledArm()
					.getArm() != null) {
				armAssigned = (studySubject.getScheduledEpoch())
						.getScheduledArm().getArm().getName();
				armAssignedLabel = "Arm Assigned";
			}

		}
		int count = 0;
		for (ScheduledEpoch scheduledEpoch : studySubject.getScheduledEpochs()) {
				count++;
		}
		if (studySubject.getScheduledEpoch().getEpoch().isEnrolling()) {
			count--;
		} else if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.APPROVED) {
			reg_nonenrolled = true;
			epoch_nonenrolled = true;
		}
		if (studySubject.getScheduledEpochs().size() > 1)
			newRegistration = false;
		if (studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.COMPLETE
				&& studySubject.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE
				&& studySubject.getScheduledEpoch().getRequiresRandomization()
				&& studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.UNAPPROVED) {
			reg_unrandomized = true;
			epoch_unrandomized = true;
		}
		switch (studySubject.getRegWorkflowStatus()) {
		case UNREGISTERED:
			reg_unregistered = true;
			break;
		case REGISTERED:
			reg_registered = true;
			break;
		case PENDING:
			reg_pending = true;
			break;
		case RESERVED:
			reg_reserved = true;
			break;
		case DISAPPROVED:
			reg_disapproved = true;
			break;
		}
		switch (studySubject.getScheduledEpoch().getScEpochWorkflowStatus()) {
		case UNAPPROVED:
			epoch_unapproved = true;
			break;
		case APPROVED:
			epoch_approved = true;
			break;
		case PENDING:
			epoch_pending = true;
			break;
		case DISAPPROVED:
			epoch_disapproved = true;
			break;
		}
		map.put("reg_unregistered", reg_unregistered);
		map.put("reg_registered", reg_registered);
		map.put("reg_unapproved", reg_unapproved);
		map.put("reg_pending", reg_pending);
		map.put("reg_reserved", reg_reserved);
		map.put("reg_disapproved", reg_disapproved);
		map.put("epoch_unapproved", epoch_unapproved);
		map.put("epoch_pending", epoch_pending);
		map.put("epoch_approved", epoch_approved);
		map.put("epoch_disapproved", epoch_disapproved);
		map.put("newRegistration", newRegistration);
		map.put("armAssigned", armAssigned);
		map.put("armAssignedLabel", armAssignedLabel);
		map.put("reg_nonenrolled", reg_nonenrolled);
		map.put("reg_unrandomized", reg_unrandomized);
		map.put("epoch_nonenrolled", epoch_nonenrolled);
		map.put("epoch_unrandomized", epoch_unrandomized);
		map.put("hasParent", studySubject.getParentStudySubject()!=null);
		map.put("hasCompanions", studySubject.getStudySite().getStudy().getCompanionStudyAssociations().size()>0);
		map.put("registerableWithCompanions", registerableAsorWithCompanion(studySubject));
		map.put("isDataEntryComplete", studySubject.isDataEntryComplete());
		return map;
	}

	public void addAppUrls(Map<String, Object> map) {
		if (this.configuration.get(this.configuration.AUTHENTICATION_MODEL)
				.equals("webSSO")) {
			map.put("hotlinkEnable", new Boolean(true));
			if (!StringUtils.getBlankIfNull(
					this.configuration.get(this.configuration.PSC_BASE_URL))
					.equalsIgnoreCase("")) {
				map.put("pscBaseUrl", this.configuration
						.get(this.configuration.PSC_BASE_URL));
				map.put("psc_window", this.configuration.get(this.configuration.PSC_WINDOW_NAME));
			}
			if (!StringUtils.getBlankIfNull(
					this.configuration.get(this.configuration.CAAERS_BASE_URL))
					.equalsIgnoreCase("")) {
				map.put("caaersBaseUrl", this.configuration
						.get(this.configuration.CAAERS_BASE_URL));
				map.put("caaers_window", this.configuration.get(this.configuration.CAAERS_WINDOW_NAME));
			}
			if (!StringUtils.getBlankIfNull(
					this.configuration.get(this.configuration.C3D_BASE_URL))
					.equalsIgnoreCase("")) {
				map.put("c3dBaseUrl", this.configuration
						.get(this.configuration.C3D_BASE_URL));
				map.put("c3d_window", this.configuration.get(this.configuration.C3D_WINDOW_NAME));
			}
		} else {
			map.put("hotlinkEnable", new Boolean(false));
		}
	}

	public boolean registerableAsorWithCompanion(StudySubject studySubject) {
		if(studySubject.getParentStudySubject()!=null)
			return false;
		if (studySubject.getStudySite().getStudy()
				.getCompanionStudyAssociations().size() > 0) {
			Map<Integer, Object> compIds = new HashMap<Integer, Object>();
			for (CompanionStudyAssociation companionStudyAssociation : studySubject
					.getStudySite().getStudy().getCompanionStudyAssociations()) {
				if (companionStudyAssociation.getMandatoryIndicator()) {
					compIds.put(companionStudyAssociation.getCompanionStudy()
							.getId(), new Object());
				}
			}

			for (StudySubject stSubject : studySubject.getChildStudySubjects()) {
				if (stSubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.READY_FOR_REGISTRATION) {
					return false;
				}
				compIds.remove(stSubject.getStudySite().getStudy().getId());
			}
			if (compIds.size() > 0)
				return false;
		}
		
		return true;

	}
	
	public void updateStatusForEmbeddedStudySubjet(StudySubject studySubject){
		if(studySubject.getParentStudySubject()!=null && studySubject.isDataEntryComplete()){
        	studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.READY_FOR_REGISTRATION);
        	studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
		}
	}

}
