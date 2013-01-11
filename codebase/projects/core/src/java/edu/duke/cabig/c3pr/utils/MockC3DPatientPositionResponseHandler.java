/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.Date;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.esb.CaXchangeMessageResponseHandler;
import gov.nih.nci.caxchange.Response;

/**
 * Handles c3d patient position <p/> Created by IntelliJ IDEA. User: kherm Date: Nov 27, 2007 Time:
 * 10:11:56 AM To change this template use File | Settings | File Templates.
 */
public class MockC3DPatientPositionResponseHandler implements CaXchangeMessageResponseHandler {
	/**
	 * Logger for this class
	 */
	
	 
	private boolean shouldProcess=false;
	
	private static final Logger log = Logger
			.getLogger(MockC3DPatientPositionResponseHandler.class);
	
	public void handleMessageResponse(String objectId, Response response) {
        gov.nih.nci.cabig.ctms.audit.DataAuditInfo.setLocal(new gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo(
                        "C3PR Admin", "CCTS Callback", new Date(), "CCTS Callback"));
        processResponse(objectId, response);
    }

    private StudySubjectRepository studySubjectRepository;
    private StudySubjectDao studySubjectDao;

    public void processResponse(String objectId, Response response) {
        log.debug("Will look for c3d identifier in response message");
        log.debug("Found c3d response. Processing...");
        if(shouldProcess){
	        log.debug("Found Registration element in c3d response. Processing....");
	        StudySubject c3dSubject = studySubjectDao.getByGridId(objectId);
	        c3dSubject.setGridId(objectId);
	        studySubjectRepository.assignC3DIdentifier(c3dSubject, "c3d identifier value");
        }
    }

    public void setStudySubjectRepositoryNew(StudySubjectRepository studySubjectRepository) {
        this.studySubjectRepository = studySubjectRepository;
    }

	public void setShouldProcess(boolean shouldProcess) {
		this.shouldProcess = shouldProcess;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

}
