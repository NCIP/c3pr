package edu.duke.cabig.c3pr.xml;

import static edu.duke.cabig.c3pr.C3PRUseCase.EXPORT_STUDY;
import static edu.duke.cabig.c3pr.C3PRUseCase.IMPORT_STUDY;
import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.StudyXMLImporterService;
import edu.duke.cabig.c3pr.utils.MasqueradingDaoTestCase;
import gov.nih.nci.cagrid.common.Utils;

import java.io.InputStream;
import java.io.StringReader;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Dec 3, 2007
 * Time: 9:18:10 AM
 * To change this template use File | Settings | File Templates.
 */
@C3PRUseCases({IMPORT_STUDY, EXPORT_STUDY})
public class StudyDeserializationFromFileTest extends MasqueradingDaoTestCase<StudyDao> {
    XmlMarshaller marshaller;
    StudyDao dao;
    StudyXMLImporterService importService;

    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
        marshaller = (XmlMarshaller) getApplicationContext().getBean("xmlUtility");
        importService = (StudyXMLImporterService) getApplicationContext().getBean("studyXMLImporterService");

    }

    public void testStudyImport() throws Exception {
        InputStream studyXMLin = getClass().getResourceAsStream("SampleStudy.xml");
        StringBuffer buf = Utils.inputStreamToStringBuffer(studyXMLin);
        Study study = (Study) marshaller.fromXML(new StringReader(buf.toString()));
        importService.importStudy(study);


    }

//      public void testRegistrationImport() throws Exception{
//        InputStream registrationIn = getClass().getResourceAsStream("SampleRegistration.xml");
//        StringBuffer buf = Utils.inputStreamToStringBuffer(registrationIn);
//        marshaller.fromXML(new StringReader(buf.toString()));
//    }

    /**
     * What dao class is the test trying to Masquerade
     *
     * @return
     */
    public Class getMasqueradingDaoClassName() {
        return StudyDao.class;
    }

}
