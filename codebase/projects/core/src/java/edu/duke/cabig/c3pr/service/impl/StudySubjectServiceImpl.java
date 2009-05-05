package edu.duke.cabig.c3pr.service.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.WorkFlowStatusType;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.factory.StudySubjectFactory;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.MessageBroadcastService;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StudyTargetAccrualNotificationEmail;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * @author Kruttik
 * @version 1.0
 * 
 */
public class StudySubjectServiceImpl extends WorkflowServiceImpl implements StudySubjectService {

    private static final Logger logger = Logger.getLogger(StudySubjectServiceImpl.class);
    private StudySubjectDao studySubjectDao;
    
    private boolean hostedMode = true;

    private StudyTargetAccrualNotificationEmail notificationEmailer;
    
    public void setNotificationEmailer(
                    StudyTargetAccrualNotificationEmail studyTargetAccrualNotificationEmail) {
        this.notificationEmailer = studyTargetAccrualNotificationEmail;
    }

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

    public List<StudySubject> searchByExample(StudySubject ss, int maxResults) {
        return studySubjectDao.searchByExample(ss, maxResults);
    }
    
    public List<StudySubject> getIncompleteRegistrations(StudySubject registration, int maxResults) {
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    	for(StudySubject studySubject : studySubjectDao.getIncompleteRegistrations(registration, maxResults)){
    		if(studySubject.getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.REGISTERED){
    			studySubjects.add(studySubject);
    		}
    	}
        return studySubjects;
    }

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}
    
}
