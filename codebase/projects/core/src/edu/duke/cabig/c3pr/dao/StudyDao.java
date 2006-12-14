package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.Study;

/**
 * @author Priyatam
 *
 *  Interface defining methods by which the C3PR Application will access the data store.  All data
 *  pertaiing to the Study data aggregate should be accessed via an implementation of this
 *  interface.
 *
 *  These methods throw org.springframework.dao.DataAccessException which is a Run-time Exception
 *  that should be handled in one of the calling classes.  See the Spring Framework API for a
 *  hierarchy of the DataAccessException.

 */
public interface StudyDao extends BaseDao{


	/**
	 * Add a new Study to the data source 
	 * @param study
	 * @throws Exception
	 */
	public void saveStudy(Study study) throws Exception;
	
}
