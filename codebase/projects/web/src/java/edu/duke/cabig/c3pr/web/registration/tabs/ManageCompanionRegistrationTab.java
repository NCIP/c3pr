package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Companion;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

public class ManageCompanionRegistrationTab<C extends StudySubjectWrapper> extends RegistrationTab<C> {
	
	public ManageCompanionRegistrationTab() {
		super("Manage Companion Registration", "Companion Registration", "registration/reg_companion_reg");
	}
	
	@Override
	public Map<String,Object> referenceData(HttpServletRequest request,
    		StudySubjectWrapper wrapper) {
    	StudySubject studySubject = wrapper.getStudySubject();
		Map map = registrationControllerUtils.buildMap(studySubject);
		map.put("companions", getCompanionStudySubject(request));
		boolean actionRequired = false;
		String actionLabel = "";
		if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.UNAPPROVED && studySubject.isDataEntryComplete()) {
			actionRequired = true;
			if (studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.REGISTERED) {
				if (studySubject.getScheduledEpoch().getEpoch().isEnrolling())
					actionLabel = "Register";
				else
					actionLabel = "Save";
			} else
				actionLabel = "Transfer Subject";
			if (studySubject.getScheduledEpoch().getRequiresRandomization()) {
				actionLabel += " & Randomize";
			}
		}
		map.put("actionRequired", actionRequired);
		map.put("actionLabel", actionLabel);
		map.put("requiresMultiSite", studySubjectService.requiresExternalApprovalForRegistration(studySubject));
		return map;
	}
	
	private List<Companion> getCompanionStudySubject(HttpServletRequest request){
    	List<Companion> companions = new ArrayList<Companion>();
    	Identifier identifier=ControllerTools.getIdentifierInRequest(request);
    	if(identifier != null){
    		List<Identifier> identifiers=new ArrayList<Identifier>();
    		identifiers.add(identifier);
    		StudySubject studySubject=studySubjectRepository.getUniqueStudySubjects(identifiers);
    		for(CompanionStudyAssociation companionStudyAssoc : studySubject.getStudySite().getStudy().getCompanionStudyAssociations()){
    			Companion companion = new Companion();
    			Study companionStudy = companionStudyAssoc.getCompanionStudy();
    			companion.setCompanionStudyShortTitle(companionStudy.getShortTitleText());
    			companion.setCompanionStudyPrimaryIdentifier(companionStudy.getPrimaryIdentifier());
    			companion.setMandatoryIndicator(companionStudyAssoc.getMandatoryIndicator());
    			for(StudySite studySite : companionStudy.getStudySites()){
    				if(studySite.getHealthcareSite() == studySubject.getStudySite().getHealthcareSite()){
    					companion.setStudySiteId(studySite.getId());
    					for(StudySubject cStudySubject : studySubject.getChildStudySubjects()){
    						if(studySite == cStudySubject.getStudySite()){
    							companion.setRegistrationId(cStudySubject.getId());
    							companion.setRegistrationStatus(cStudySubject.getRegWorkflowStatus().getDisplayName());
    						}
    					}
    				}
    			}
    			companions.add(companion);
    		}
    	}
    	return companions;
    }
	
}
