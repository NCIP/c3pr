/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import junit.framework.TestCase;


/**
 * The Class ScheduledNotificationDaoTest.
 */
public class ScheduledNotificationDaoTest extends TestCase {

	/** The scheduled notification dao. */
	private ScheduledNotificationDao scheduledNotificationDao= new ScheduledNotificationDao();
	
	/**
	 * Test domain class.
	 */
	public void testDomainClass() {
		assertEquals("Wrong Domain Class", ScheduledNotification.class, scheduledNotificationDao.domainClass());
	}
	
}
