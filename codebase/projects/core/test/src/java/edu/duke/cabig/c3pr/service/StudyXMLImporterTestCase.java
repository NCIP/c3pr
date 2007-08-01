package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.StudyValidationException;
import edu.duke.cabig.c3pr.service.impl.StudyXMLImporterService;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.xml.AbstractXMLMarshalling;
import static org.easymock.EasyMock.*;


import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 4, 2007
 * Time: 2:33:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudyXMLImporterTestCase extends AbstractXMLMarshalling {

    private StudyXMLImporterService studyImporter =
            (StudyXMLImporterService) getDeployedCoreApplicationContext().getBean("studyXMLImporterService");
    private Study study;
    private StudyDao studyDao;

    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.

        studyDao = registerMockFor(StudyDao.class);

        studyImporter.setStudyDao(studyDao);
        study = createDummyStudy(studyGridId);
    }

    public void testStudyValidation() {
        try {
            studyImporter.validate(study);
        } catch (StudyValidationException e) {
            fail(e.getMessage());
        }
    }


    public void testGetStudies() throws Exception {
        Study study2  = new Study();
        study2.setId(2);
        expect(studyDao.getByGridId(isA(String.class))).andReturn(null);
        expectLastCall().times(2);

        studyDao.save(isA(Study.class));

        replayMocks();

        String xmlStudy = marshaller.toXML(study);
        studyDao.save(study.getStudySites().get(0));
        List<Study> studies = studyImporter.importStudies(StringUtils.getInputStream(xmlStudy));
        assertNotNull(studies);
        assertTrue(studies.size() > 0);

        verifyMocks();
    }

}
