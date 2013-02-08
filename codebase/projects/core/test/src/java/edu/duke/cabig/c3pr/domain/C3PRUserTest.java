/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.utils.MasqueradingDaoTestCase;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Nov 19, 2007 Time: 4:13:58 PM To change this template
 * use File | Settings | File Templates.
 */
public class C3PRUserTest extends MasqueradingDaoTestCase<PersonUserDao> {

    /*the PersonUserDao.getAll has been removed. replace with something else.
     */ 
      public void testGetGroups() {
    	PersonUser staff  = null;
        for(int i=1000; i< 1005; i++){
        	staff = getDao().getById(i);
        	// FIXME : Vinay Gangoli
//            assertNotNull(staff.getGroups());
        }
    }

    /**
     * What dao class is the test trying to Masquerade
     * 
     * @return
     */
    public Class<PersonUserDao> getMasqueradingDaoClassName() {
        return PersonUserDao.class;
    }
}
