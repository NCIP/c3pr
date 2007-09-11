package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class RegistrationOverviewTab<C extends StudySubject> extends RegistrationTab<C>{

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
		return map;
	}
}
