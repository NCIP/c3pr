package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.utils.MasqueradingDaoTestCase;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Nov 19, 2007 Time: 4:13:58 PM To change this template
 * use File | Settings | File Templates.
 */
public class C3PRUserTest extends MasqueradingDaoTestCase<ResearchStaffDao> {

    /*the ResearchStaffDao.getAll has been removed. replace with something else.
     */ 
      public void testGetGroups() {
    	ResearchStaff staff  = null;
        for(int i=1000; i< 1005; i++){
        	staff = getDao().getById(i);
            assertNotNull(staff.getGroups());
        }
    }

    /**
     * What dao class is the test trying to Masquerade
     * 
     * @return
     */
    public Class<ResearchStaffDao> getMasqueradingDaoClassName() {
        return ResearchStaffDao.class;
    }
}
