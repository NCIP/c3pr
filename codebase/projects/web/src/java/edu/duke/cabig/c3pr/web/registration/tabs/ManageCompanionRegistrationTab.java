package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.Companion;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
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
    	List<Identifier> identifiers=new ArrayList<Identifier>();
    	identifiers.add(studySubject.getSystemAssignedIdentifiers().get(0));
    	studySubject = studySubject=studySubjectRepository.getUniqueStudySubjects(identifiers);
		Map map = registrationControllerUtils.buildMap(studySubject);
		map.put("companions", getCompanionStudySubject(request));
		boolean actionRequired = false;
		String actionLabel = "";
		if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING && studySubject.isDataEntryComplete()) {
			actionRequired = true;
			if (studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.ENROLLED) {
				if (studySubject.getScheduledEpoch().getEpoch().isEnrolling())
					actionLabel = "Enroll";
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
		wrapper.setStudySubject(studySubject);
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
    			companion.setCompanionStudyId(companionStudy.getId());
    			companion.setMandatoryIndicator(companionStudyAssoc.getMandatoryIndicator());
				for (StudySubject cStudySubject : studySubject.getChildStudySubjects()) {
					if (companionStudy.getId() == cStudySubject.getStudySite().getStudy().getId()) {
						companion.setRegistrationId(cStudySubject.getId());
						companion.setCompanionRegistrationUrl(ControllerTools.createParameterString(cStudySubject.getSystemAssignedIdentifiers().get(0)));
						companion.setRegistrationStatus(cStudySubject.getRegWorkflowStatus().getDisplayName());
					}
				}
    			companions.add(companion);
    		}
    	}
    	return companions;
    }
	
}
