package edu.duke.cabig.c3pr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StudyTargetAccrualNotificationEmail;

/**
 * @author Kruttik
 * @version 1.0
 * 
 */
public class StudySubjectServiceImpl extends WorkflowServiceImpl implements StudySubjectService {

    private static final Logger logger = Logger.getLogger(StudySubjectServiceImpl.class);
    
    private boolean hostedMode = true;

    public String getLocalNCIInstituteCode() {
		return this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);
	}
    
    public boolean isHostedMode() {
        return hostedMode;
    }

    public void setHostedMode(boolean hostedMode) {
        this.hostedMode = hostedMode;
    }

    public String getLocalInstanceNCICode() {
        return getConfiguration().get(Configuration.LOCAL_NCI_INSTITUTE_CODE);
    }

    public boolean requiresExternalApprovalForRegistration(StudySubject studySubject) {
        return studySubject.requiresCoordinatingCenterApproval()
        && !studySubject.isCoOrdinatingCenter(getLocalInstanceNCICode()) && !isHostedMode();
    }

    public List<StudySubject> getIncompleteRegistrations(int maxResults) {
    	return ((StudySubjectDao)dao).getIncompleteRegistrations(maxResults) ;
    }

}
