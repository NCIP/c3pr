package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;

/**
 * 
 * @author Ramakrishna
 * @version 1.0
 */

public interface HealthcareSiteDao extends BaseDao{

	/** From the BaseDao
	 * Add a new HealthcareSite to the data source 
	 * @param HealthcareSite
	 * 	 * @throws Exception
	 */
	
	
	/**
	 * Lists all HealthcareSite objects
	 */
	
	public List<HealthcareSite> getAll(); 

}
