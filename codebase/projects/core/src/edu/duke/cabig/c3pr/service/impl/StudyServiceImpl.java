package edu.duke.cabig.c3pr.service.impl;

import java.util.List;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.service.StudyService;

/**
 * Services for Study related domain object
 * @see edu.duke.cabig.c3pr.service.StudyService
 * @author Priyatam
 */
public class StudyServiceImpl implements StudyService {
	
	StudyDao studyDao;
	//TODO hook esb call
	// ProtocolBroadcastService esbCreateProtocol;

	/**
	 * Search using a sample populate Study object
	 * @param study the study object
	 * @return List of Study objects based on the sample study object
	 * @throws Exception runtime exception object
	 */
	public List<Study> search(Study study) throws Exception {		
		return studyDao.searchByExample(study, true);
	}

	/**
	 * Saves a study object
	 * @param study the study object
  	 * @throws Exception runtime exception object
  	 */
	public void save(Study study) throws Exception {
		//TODO call esb to broadcast protocol, POC
		studyDao.save(study);		
	}
	
	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}


    /**
     * Assigns a Participant to a Study at a particular Site.
     * The Study and Site must already exist and be associated.
     *
     * @param study
     * @param participant
     * @param site
     * @return StudyParticipantAssignment for the Participant
     */
    public StudyParticipantAssignment assignParticipant(Study study, Participant participant, HealthcareSite site, String registrationGridId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
