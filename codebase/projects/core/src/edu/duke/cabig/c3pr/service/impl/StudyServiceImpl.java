package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.StudyService;

/**
 * @author Priyatam
 *
 */
public class StudyServiceImpl implements StudyService {

	StudyDao studyDao;
	//ProtocolBroadcastService esbCreateProtocol;


	/**
	  Saves a Study	
	*/
	public void saveStudy(Study study) throws Exception {
		// call esb to broadcast protocol, POC
		//esbCreateProtocol.broadcast(study);
		studyDao.saveStudy(study);		
	}


	public StudyDao getStudyDao() {
		return studyDao;
	}


	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}
}
