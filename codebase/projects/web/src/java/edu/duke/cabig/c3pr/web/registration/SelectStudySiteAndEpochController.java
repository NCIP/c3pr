/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.registration;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;

public class SelectStudySiteAndEpochController extends AbstractController{

	private StudyDao studyDao ;
	private ParticipantDao participantDao ;
	private StudySubjectDao studySubjectDao ;
	
	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	
	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}
	
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HashMap map = new HashMap() ;
		
		String studyId = request.getParameter("study") ;
		String participantId = request.getParameter("participant");
		String parentRegistrationId = request.getParameter("parentRegistrationId");
		
		Participant participant = participantDao.getById(Integer.parseInt(participantId));
		Study companionStudy = studyDao.getById(Integer.parseInt(studyId));
		StudySubject parentRegistration = studySubjectDao.getById(Integer.parseInt(parentRegistrationId));
        List<Epoch> epochs = companionStudy.getEpochs() ;
        List<StudySite> studySites = getStudySites(parentRegistration.getStudySite().getStudy(), companionStudy) ;
		
		map.put("participant", participant);
		map.put("studySites", studySites);
		map.put("companionStudy", companionStudy);
		map.put("epochs", epochs);
		map.put("parentRegistrationId", request.getParameter("parentRegistrationId"));
		
		ModelAndView mav = new ModelAndView("registration/select_studysite_and _epoch", map);
		return mav;
	}

	private List<StudySite> getStudySites(Study study, Study companionStudy) {
		CompanionStudyAssociation companionStudyAssociation = companionStudy.getParentStudyAssociation(study.getId());
		return companionStudyAssociation.getStudySites();
	}

	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}



}
