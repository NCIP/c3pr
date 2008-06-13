package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.web.ControllerTestCase;

/**
 * Base class for all study controller tests User: kherm
 * 
 * @author kherm manav.kher@semanticbits.com
 */
public class AbstractStudyControllerTest extends ControllerTestCase {
    protected Study command;

    protected StudyDao studyDao;

    protected StudyRepository studyRepository;

    @Override
    protected void setUp() throws Exception {
        super.setUp(); // To change body of overridden methods use File | Settings | File
                        // Templates.
        command = registerMockFor(Study.class);
        studyDao = registerDaoMockFor(StudyDao.class);
        studyRepository = registerMockFor(StudyRepository.class);

    }

}
