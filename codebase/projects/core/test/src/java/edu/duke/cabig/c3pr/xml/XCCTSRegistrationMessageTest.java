/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.MasqueradingDaoTestCase;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 19, 2007 Time: 2:34:51 PM To change this template
 * use File | Settings | File Templates.
 */
public class XCCTSRegistrationMessageTest extends MasqueradingDaoTestCase<StudySubjectDao> {

    XmlMarshaller marshaller;

    protected void setUp() throws Exception {
        super.setUp(); // To change body of overridden methods use File | Settings | File
                        // Templates.
        marshaller = (XmlMarshaller) getApplicationContext().getBean("xmlUtility");
        marshaller.setMappingFile((String) getApplicationContext().getBean(
                        "c3pr-study-xml-castorMapping"));
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
