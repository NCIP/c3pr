package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class RegistrationOverviewTab<C extends StudySubject> extends RegistrationTab<C>{

	private StudySubjectService studySubjectService;
	public void setStudySubjectService(StudySubjectService studySubjectService) {
		this.studySubjectService = studySubjectService;
	}

	public RegistrationOverviewTab() {
		super("Overview", "Overview", "registration/reg_overview");
	}

	@Override
	public Map<String, Object> referenceData(C command){
		StudySubject studySubject=(StudySubject)command;
		Map<String, Object> map= new HashMap<String, Object>();
		boolean actionRequired=false;
		boolean newRegistration=false;
		String actionLabel="";
		String armAssigned="";
		String armAssignedLabel="";
		if(studySubject.getIfTreatmentScheduledEpoch() && ((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getScheduledArm()!=null
				&& ((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getScheduledArm().getArm()!=null){
			armAssigned=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getScheduledArm().getArm().getName();
			armAssignedLabel="Arm Assigned";
		}
		if(studySubject.getScheduledEpoch().getScEpochWorkflowStatus()==ScheduledEpochWorkFlowStatus.UNAPPROVED
				&& studySubject.getRegDataEntryStatus()==RegistrationDataEntryStatus.COMPLETE
				&& studySubject.getScheduledEpoch().getScEpochDataEntryStatus()==ScheduledEpochDataEntryStatus.COMPLETE){
			actionRequired=true;
			if(studySubject.getScheduledEpochs().size()>1){
				actionLabel="Transfer Subject";
			}else if(studySubject.getScheduledEpoch().getEpoch().isEnrolling()){
				actionLabel="Register";
			}else{
				actionLabel="Save";
			}
			if(studySubject.getScheduledEpoch().getRequiresRandomization()){
				actionLabel+=" & Randomize";
			}
		}
		if(studySubject.getScheduledEpochs().size()==1){
			newRegistration=true;
		}
		map.put("actionRequired", actionRequired);
		map.put("actionLabel", actionLabel);
		map.put("newRegistration", newRegistration);
		map.put("armAssigned", armAssigned);
		map.put("armAssignedLabel", armAssignedLabel);
		map.put("requiresMultiSite", studySubjectService.requiresCoordinatingCenterApproval(studySubject));
		return map;
	}
	
	public ModelAndView broadcastRegistration(HttpServletRequest request, Object commandObj, Errors error){
		C command=(C)commandObj;
		String responseMessage="Registration message successfully broadcasted.";
		try {
			this.studySubjectService.sendRegistrationEvent(command);
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			responseMessage=e.getCodedExceptionMesssage();
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("responseMessage", responseMessage);
		return new ModelAndView(getAjaxViewName(request),map);
	}
}
