package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.MasqueradingDaoTestCase;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 19, 2007
 * Time: 2:34:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class CCTSRegistrationMessageTest extends MasqueradingDaoTestCase<StudySubjectDao> {

    XmlMarshaller marshaller;

    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
        marshaller = (XmlMarshaller) getApplicationContext().getBean("xmlUtility");
    }

    public void testSerialization() throws Exception {
        for (StudySubject subject : getDao().getAll()) {
            System.out.println(marshaller.toXML(subject));
        }
    }


    /**
     * What dao class is the test trying to Masquerade
     *
     * @return
     */
    public Class getMasqueradingDaoClassName() {
        return StudySubjectDao.class;
    }


}
 
