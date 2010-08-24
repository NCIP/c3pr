/**
 * 
 */
package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.RaceCode;

/**
 * @author c3pr
 *
 */
public class RaceCodeDao extends GridIdentifiableDao {

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
	 */
	@Override
	 public Class<RaceCode> domainClass() {
        return RaceCode.class;
    }

}
