package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import junit.framework.TestCase;

/**
 * The Class ScheduledEpochDaoTest.
 */
public class ScheduledEpochDaoTest extends TestCase {

	/** The scheduled epoch dao. */
	ScheduledEpochDao scheduledEpochDao=new ScheduledEpochDao();
	
	/**
	 * Test domain class.
	 */
	public void testDomainClass(){
		assertEquals("Wrong Domain Class", ScheduledEpoch.class, scheduledEpochDao.domainClass());
	}
}