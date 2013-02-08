/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.MasqueradingDaoTestCase;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Nov 7, 2007 Time: 12:54:39 PM To change this template
 * use File | Settings | File Templates.
 */
public class CCTSStudyMessageTest extends MasqueradingDaoTestCase<StudyDao> {
    XmlMarshaller marshaller;
    
    public XmlMarshaller getMarshaller() {
        return new XmlMarshaller("c3pr-study-xml-castor-mapping.xml");
    }
    
    protected void setUp() throws Exception {
        super.setUp(); // To change body of overridden methods use File | Settings | File
                        // Templates.
        marshaller = getMarshaller();
    }

    public void testSerialization() throws Exception {
        Study study = getDao().getById(1000);
        getDao().initialize(study);
        interruptSession();
        String marshalledStudy = marshaller.toXML(study);
        assertNotNull(marshalledStudy);
        System.out.println(marshalledStudy);
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
