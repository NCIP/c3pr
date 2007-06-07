package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.utils.ApplicationTestCase;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.exception.StudyValidationException;
import edu.duke.cabig.c3pr.dao.StudyDao;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 4, 2007
 * Time: 2:33:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudyXMLImporterTestCase extends StudyMarshallingTestCase {

    private StudyXMLImporter studyImporter =
            (StudyXMLImporter) getDeployedCoreApplicationContext().getBean("studyXMLImporter");
    private Study study;

    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
        studyImporter.setStudyDao(registerDaoMockFor(StudyDao.class));
        studyImporter.setStudyService(registerMockFor(StudyService.class));
        study = createDummyStudy(studyGridId);

    }

    public void testStudyValidation(){
        try {
            studyImporter.validate(study);
        } catch (StudyValidationException e) {
            fail(e.getMessage());
        }
    }


    public void testStudyImport(){
        try {
            studyImporter.importStudy(study);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }



}
