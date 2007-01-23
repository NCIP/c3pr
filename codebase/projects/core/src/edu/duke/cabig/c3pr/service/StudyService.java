package edu.duke.cabig.c3pr.service;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Study;

/**
 * Interface for Services on Study related domain object
 * @author priyatam
 */
public interface StudyService {

	/**
	 * Saves a study object
	 * @param study the study object
  	 * @throws Exception runtime exception object
  	 */
	public void save(Study study) throws Exception;	
}
