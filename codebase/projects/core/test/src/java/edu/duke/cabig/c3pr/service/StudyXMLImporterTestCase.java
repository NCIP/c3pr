package edu.duke.cabig.c3pr.service;

import static edu.duke.cabig.c3pr.C3PRUseCase.IMPORT_STUDY;
import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.impl.StudyXMLImporterServiceImpl;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;
import edu.duke.cabig.c3pr.utils.SecurityContextTestUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import org.acegisecurity.AccessDeniedException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 4, 2007
 * Time: 2:33:16 PM
 * To change this template use File | Settings | File Templates.
 */
@C3PRUseCases({IMPORT_STUDY})
public class StudyXMLImporterTestCase extends ContextDaoTestCase<StudyDao> {


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
        Study study = getDao().getById(1000);
        String xmlStudy = marshaller.toXML(study);
        System.out.println(xmlStudy);

        SecurityContextTestUtils.switchToNobody();

        try {
            studyImporter.importStudies(StringUtils.getInputStream(xmlStudy));
            fail("Should not be able to import studies. User not authorized");
        } catch (C3PRBaseRuntimeException e) {
            if (e.getRootCause() instanceof AccessDeniedException) {

                //expected

            } else
                fail("Could not import Study");
        }


        SecurityContextTestUtils.switchToSuperuser();
        List<Study> studies = studyImporter.importStudies(StringUtils.getInputStream(xmlStudy));

        assertNotNull(studies);
        assertTrue(studies.size() > 0);

        for (Study loadedStudy : studies) {
            assertNotNull(loadedStudy.getGridId());
            assertEquals(loadedStudy.getStudyOrganizations().size(), 3);

            for (StudyOrganization organization : loadedStudy.getStudyOrganizations()) {
                assertNotNull(organization.getHealthcareSite());
            }
        }

    }


    protected String getDaoBeanName() {
        return "studyDao";
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

    @Override
    protected String getClassNameWithoutPackage() {
        return "StudyImportDataset";
    }
}
