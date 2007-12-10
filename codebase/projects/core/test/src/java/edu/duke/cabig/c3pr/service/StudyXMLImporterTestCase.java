package edu.duke.cabig.c3pr.service;

import static edu.duke.cabig.c3pr.C3PRUseCase.IMPORT_STUDY;
import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.service.impl.StudyXMLImporterServiceImpl;
import edu.duke.cabig.c3pr.utils.MasqueradingDaoTestCase;
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
@C3PRUseCases({IMPORT_STUDY})
public class StudyXMLImporterTestCase extends MasqueradingDaoTestCase<StudyDao> {


    private StudyXMLImporterServiceImpl studyImporter;

    XmlMarshaller marshaller;

    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
        marshaller = new XmlMarshaller();
        studyImporter = (StudyXMLImporterServiceImpl) getApplicationContext().getBean("studyXMLImporterService");
    }


    public void testStudyValidation() throws Exception {
        Study study = getDao().getById(1000);

        studyImporter.validate(study);
    }


    public void testGetStudies() throws Exception {
        for (int i = 1000; i < 1003; i++) {
            Study study = getDao().getById(i);
            String xmlStudy = marshaller.toXML(study);

            System.out.println(xmlStudy);
            studyImporter.importStudies(StringUtils.getInputStream(xmlStudy));

            List<Study> studies = studyImporter.importStudies(StringUtils.getInputStream(xmlStudy));

            assertNotNull(studies);
            assertTrue(studies.size() > 0);

            for (Study loadedStudy : studies) {
                assertNotNull(loadedStudy);
                assertEquals(loadedStudy.getStudyOrganizations().size(), study.getStudyOrganizations().size());

                for (StudyOrganization organization : loadedStudy.getStudyOrganizations()) {
                    assertNotNull(organization.getHealthcareSite());
                }
            }
        }
    }


    /**
     * What dao class is the test trying to Masquerade
     *
     * @return
     */
    public Class<StudyDao> getMasqueradingDaoClassName() {
        return StudyDao.class;
    }

    public StudyXMLImporterServiceImpl getStudyImporter() {
        return studyImporter;
    }

    public void setStudyImporter(StudyXMLImporterServiceImpl studyImporter) {
        this.studyImporter = studyImporter;
    }


    public XmlMarshaller getMarshaller() {
        return marshaller;
    }

    public void setMarshaller(XmlMarshaller marshaller) {
        this.marshaller = marshaller;
    }


}
