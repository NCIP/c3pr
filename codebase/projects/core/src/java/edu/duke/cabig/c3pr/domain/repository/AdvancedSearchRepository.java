package edu.duke.cabig.c3pr.domain.repository;

import java.util.List;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;

public interface AdvancedSearchRepository {
	
	/* Search for Participants */
	public List<Participant> searchSubjects(List<AdvancedSearchCriteriaParameter> searchCriteriaList) throws Exception;
	public List<Participant> searchSubjects(List<AdvancedSearchCriteriaParameter> searchCriteriaList, String fileLocation)throws Exception;

	/* Search for Studies */
	public List<Study> searchStudy(List<AdvancedSearchCriteriaParameter> searchCriteriaList)throws Exception;
	public List<Study> searchStudy(List<AdvancedSearchCriteriaParameter> searchCriteriaList, String fileLocation)throws Exception;

	/* Search for Registrations */
	public List<StudySubject> searchRegistrations(List<AdvancedSearchCriteriaParameter> searchCriteriaList)throws Exception;
	public List<StudySubject> searchRegistrations(List<AdvancedSearchCriteriaParameter> searchCriteriaList, String fileLocation)throws Exception;

}
