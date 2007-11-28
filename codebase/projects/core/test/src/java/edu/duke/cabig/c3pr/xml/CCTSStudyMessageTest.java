package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.MasqueradingDaoTestCase;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 7, 2007
 * Time: 12:54:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class CCTSStudyMessageTest extends MasqueradingDaoTestCase<StudyDao> {
    XmlMarshaller marshaller;

    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
        marshaller = new XmlMarshaller("c3pr-castor-mapping.xml");
    }


    public void testSerialization() throws Exception {
        for (Study study : getDao().getAll()) {
            assertNotNull(marshaller.toXML(study));
        }
    }

    /**
     * What dao class is the test trying to Masquerade
     *
     * @return
     */
    public Class getMasqueradingDaoClassName() {
        return StudyDao.class;
    }

}
