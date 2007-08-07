package edu.duke.cabig.c3pr.service;

import static edu.duke.cabig.c3pr.ant.C3PRUseCase.CREATE_EXPEDITED_REPORT;
import static edu.duke.cabig.c3pr.ant.C3PRUseCase.CREATE_ROUTINE_REPORT;
import edu.duke.cabig.c3pr.ant.C3PRUseCases;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.impl.StudyXMLImporterService;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 4, 2007
 * Time: 2:33:16 PM
 * To change this template use File | Settings | File Templates.
 */
@C3PRUseCases({ STUDY_EXPORT, STUDY_IMPORT })
public class StudyXMLImporterTestCase extends DaoTestCase {


    private StudyXMLImporterService studyImporter;
    private StudyDao dao;

    XmlMarshaller marshaller;

    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
        marshaller = new XmlMarshaller();
        dao = (StudyDao) getApplicationContext().getBean("studyDao");
        studyImporter = (StudyXMLImporterService) getApplicationContext().getBean("studyXMLImporterService");
    }


    public void testStudyValidation() throws Exception{
        Study study = dao.getById(1000);

        studyImporter.validate(study);
    }


    public void testGetStudies() throws Exception {
        Study study = dao.getById(1000);
        String xmlStudy = marshaller.toXML(study);
        List<Study> studies = studyImporter.importStudies(StringUtils.getInputStream(xmlStudy));
        assertNotNull(studies);
        assertTrue(studies.size() > 0);

    }


    public StudyXMLImporterService getStudyImporter() {
        return studyImporter;
    }

    public void setStudyImporter(StudyXMLImporterService studyImporter) {
        this.studyImporter = studyImporter;
    }

    public StudyDao getDao() {
        return dao;
    }

    public void setDao(StudyDao dao) {
        this.dao = dao;
    }


    public XmlMarshaller getMarshaller() {
        return marshaller;
    }

    public void setMarshaller(XmlMarshaller marshaller) {
        this.marshaller = marshaller;
    }

    @Override
    protected String getClassNameWithoutPackage() {
        return "StudyImportDataset";
    }
}
