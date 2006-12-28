package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Study;

/**
 *  Interface defining methods by which the C3PR Application will access the 
 *  data store.  All data pertaiing to the Study data aggregate should be 
 *  accessed via an implementation of this interface.
 *
 *  These methods throw org.springframework.dao.DataAccessException which is 
 *  a Run-time Exception that should be handled in one of the calling classes.  
 *  See the Spring Framework API for a hierarchy of the DataAccessException.
 *  
 *  @author Priyatam
 */
public interface StudyDao extends BaseDao{

	/**
	 * Add a new Study to the data source 
	 * @param study
	 * @throws DataAccessException
	 */	
	public List<Study> getAll() throws DataAccessException; 
	 
	/**
	 * Searches based on an example object.	
	 * @param study
	 * @return 
	 * @throws DataAccessException
	 */
	public List<Study> searchByExample(Study study) throws DataAccessException;
	
	/**
	 * 
	 * @param studyId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Epoch> getEpochsForStudy(Integer studyId) throws DataAccessException;
	
	/**
	 * 
	 * @param studyId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Arm> getArmsForStudy(Integer studyId) throws DataAccessException;
	
	
}
