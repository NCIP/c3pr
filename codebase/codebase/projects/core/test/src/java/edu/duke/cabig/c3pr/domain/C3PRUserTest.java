package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.utils.MasqueradingDaoTestCase;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Nov 19, 2007 Time: 4:13:58 PM To change this template
 * use File | Settings | File Templates.
 */
public class C3PRUserTest extends MasqueradingDaoTestCase<ResearchStaffDao> {

    public void testGetGroups() {
        for (ResearchStaff staff : getDao().getAll()) {
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
