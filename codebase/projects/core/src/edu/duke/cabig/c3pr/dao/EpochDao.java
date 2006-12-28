package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.domain.Epoch;

/**
 *  Interface defining methods by which the C3PR Application will access the 
 *  data store.  All data pertaiing to the Epoch data aggregate should be 
 *  accessed via an implementation of this interface.
 *
 *  These methods throw org.springframework.dao.DataAccessException which is 
 *  a Run-time Exception that should be handled in one of the calling classes.  
 *  See the Spring Framework API for a hierarchy of the DataAccessException.
 *  
 *  @author Priyatam
 */
public interface EpochDao extends BaseDao {

	public List<Epoch> getAll() throws DataAccessException; 
}
