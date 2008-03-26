package edu.duke.cabig.c3pr.domain.repository;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.StudyValidationException;

public interface StudyRepository {

	public void buildAndSave(Study study) throws Exception;
	
	public void validate(Study study) throws StudyValidationException;
}
