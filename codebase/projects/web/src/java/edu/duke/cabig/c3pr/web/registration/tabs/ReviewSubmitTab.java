/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.web.registration.RegistrationControllerUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class ReviewSubmitTab extends RegistrationTab<StudySubjectWrapper> {

	private RegistrationControllerUtils registrationControllerUtils;
    public void setRegistrationControllerUtils(
			RegistrationControllerUtils registrationControllerUtils) {
		this.registrationControllerUtils = registrationControllerUtils;
	}

	public ReviewSubmitTab() {
        super("Review & Submit", "Review & Submit", "registration/reg_submit");
        setShowSummary("false");
    }

    @Override
    public Map referenceData(StudySubjectWrapper command) {
		Map map = new HashMap();
		StudySubject studySubject = command.getStudySubject();
		String armAssigned = "";
		String armAssignedLabel = "";
		if (studySubject.getScheduledEpoch().getScheduledArm() != null) {
			if (studySubject.getStudySite().getStudy().getBlindedIndicator() && studySubject.getScheduledEpoch().getRequiresRandomization()) {
				armAssigned = (studySubject.getScheduledEpoch())
						.getScheduledArm().getKitNumber();
				armAssignedLabel = "Kit assigned";
			} else if (studySubject.getScheduledEpoch().getScheduledArm()
					.getArm() != null) {
				armAssigned = (studySubject.getScheduledEpoch())
						.getScheduledArm().getArm().getName();
				armAssignedLabel = "Arm assigned";
			} 
		}
		
		map.put("companions", registrationControllerUtils.getCompanionStudySubject(studySubject.getSystemAssignedIdentifiers().get(0), studySubject));
		map.put("actionLabel", registrationControllerUtils.getActionButtonLabel(command));
		map.put("tabTitle", registrationControllerUtils.getTabTitle(command));
		map.put("registerable", registrationControllerUtils.isRegisterableOnPage(command.getStudySubject()));
		map.put("armAssigned", armAssigned);
		map.put("armAssignedLabel", armAssignedLabel);
		map.put("requiresRandomization", requiresRandomization(studySubject));
        return map;
    }
    
    private boolean requiresRandomization(StudySubject studySubject){
    	if(studySubject.getScheduledEpoch().getRequiresRandomization()){
    		return true ;
    	}
    	for(StudySubject childStudySubject : studySubject.getChildStudySubjects()){
    		if(childStudySubject.getScheduledEpoch().getRequiresRandomization()){
        		return true ;
        	}
    	}
    	return false ;
    }
    
}
