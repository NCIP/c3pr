package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * Will test marshalling of a hibernate object.
 *
 * @testType unit
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 8, 2007
 * Time: 12:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class StudyHibernateObjectMarshallingTest extends DaoTestCase {
    private StudyDao dao = (StudyDao) getApplicationContext().getBean("studyDao");
    XmlMarshaller marshaller;




    public void testMarshallStudy(){
        try {
            Study study = dao.getById(1000);
            marshaller = new XmlMarshaller();

            String studyXML = marshaller.toXML(study);
            assertNotNull(studyXML);

            // make sure no hibernate proxy (garbage) is being serialized           
            assertFalse(studyXML.indexOf("proxy")>-1);

        } catch (XMLUtilityException e) {
            fail(e.getMessage());
        }


    }

    // override default dataset filename
    protected String getClassNameWithoutPackage() {
        return "StudyDataset";
    }
}
