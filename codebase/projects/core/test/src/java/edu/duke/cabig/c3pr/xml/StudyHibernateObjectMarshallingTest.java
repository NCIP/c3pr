package edu.duke.cabig.c3pr.xml;

import static edu.duke.cabig.c3pr.C3PRUseCase.EXPORT_STUDY;
import static edu.duke.cabig.c3pr.C3PRUseCase.IMPORT_STUDY;
import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.dao.InvestigatorDaoTest;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudyDaoTest;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.nwu.bioinformatics.commons.ResourceRetriever;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Will test marshalling of a hibernate object.
 *
 * @testType unit
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 8, 2007
 * Time: 12:49:24 AM
 * To change this template use File | Settings | File Templates.
 */

@C3PRUseCases({IMPORT_STUDY, EXPORT_STUDY})
public class StudyHibernateObjectMarshallingTest extends DaoTestCase {
    private StudyDao dao;
    XmlMarshaller marshaller;


    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
        dao = (StudyDao) getApplicationContext().getBean("studyDao");
    }

    public void testMarshallStudy()  throws Exception{
            Study study = dao.getById(1000);

            marshaller = new XmlMarshaller();

            String studyXML = marshaller.toXML(study);
            System.out.println(studyXML);
            assertNotNull(studyXML);

            // make sure no hibernate proxy (garbage) is being serialized           
            assertFalse(studyXML.indexOf("proxy") > -1);
        
    }


    //Mock to be some other test

    protected InputStream handleTestDataFileNotFound() throws FileNotFoundException {
        return ResourceRetriever.getResource(InvestigatorDaoTest.class.getPackage(),getTestDataFileName());
    }


    protected String getClassNameWithoutPackage() {
        return StudyDaoTest.class.getSimpleName();
    }
}
