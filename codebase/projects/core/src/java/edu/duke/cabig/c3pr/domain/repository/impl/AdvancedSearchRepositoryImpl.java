/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
/**
 * 
 */
package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.List;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.repository.AdvancedSearchRepository;

/**
 * @author c3pr
 *
 */
public class AdvancedSearchRepositoryImpl implements AdvancedSearchRepository {
	
	private ParticipantDao participantDao;
	private StudyDao studyDao;
	private StudySubjectDao studySubjectDao;

	public List<Participant> searchSubjects(List<AdvancedSearchCriteriaParameter> searchCriteriaList) throws Exception {
		try{
			return participantDao.search(searchCriteriaList) ;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public List<Participant> searchSubjects(List<AdvancedSearchCriteriaParameter> searchCriteriaList, String fileLocation) throws Exception {
		try{
			return participantDao.search(searchCriteriaList, fileLocation) ;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public List<Study> searchStudy(List<AdvancedSearchCriteriaParameter> searchCriteriaList) throws Exception {
		try{
			return studyDao.search(searchCriteriaList) ;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public List<Study> searchStudy(List<AdvancedSearchCriteriaParameter> searchCriteriaList, String fileLocation) throws Exception {
		try{
			return studyDao.search(searchCriteriaList, fileLocation) ;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public List<StudySubject> searchRegistrations(List<AdvancedSearchCriteriaParameter> searchCriteriaList) throws Exception {
		try{
			return studySubjectDao.search(searchCriteriaList) ;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public List<StudySubject> searchRegistrations(List<AdvancedSearchCriteriaParameter> searchCriteriaList,String fileLocation) throws Exception {
		try{
			return studySubjectDao.search(searchCriteriaList, fileLocation) ;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * @return the participantDao
	 */
	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	/**
	 * @param participantDao the participantDao to set
	 */
	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	/**
	 * @return the studyDao
	 */
	public StudyDao getStudyDao() {
		return studyDao;
	}

	/**
	 * @param studyDao the studyDao to set
	 */
	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	/**
	 * @return the studySubjectDao
	 */
	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}

	/**
	 * @param studySubjectDao the studySubjectDao to set
	 */
	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}


}
