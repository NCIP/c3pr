/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
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
    	studySubject = studySubject=studySubjectRepository.getUniqueStudySubject(studySubject.getUniqueIdentifier());
		Map map = registrationControllerUtils.buildMap(studySubject);
		map.put("companions", getCompanionStudySubject(request));
		boolean actionRequired = false;
		String actionLabel = "";
		if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH && studySubject.isDataEntryComplete()) {
			actionRequired = true;
			if (studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.ON_STUDY) {
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
    		StudySubject studySubject=studySubjectRepository.getUniqueStudySubject(identifier);
    		for(CompanionStudyAssociation companionStudyAssoc : studySubject.getStudySite().getStudy().getStudyVersion().getCompanionStudyAssociations()){
    			Companion companion = new Companion();
    			Study companionStudy = companionStudyAssoc.getCompanionStudy();
    			companion.setCompanionStudyShortTitle(companionStudy.getShortTitleText());
    			companion.setCompanionStudyStatus(companionStudy.getCoordinatingCenterStudyStatus());
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
