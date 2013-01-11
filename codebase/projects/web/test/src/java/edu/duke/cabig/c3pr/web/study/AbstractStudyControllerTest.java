/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.web.ControllerTestCase;

/**
 * Base class for all study controller tests User: kherm
 * 
 * @author kherm manav.kher@semanticbits.com
 */
public class AbstractStudyControllerTest extends ControllerTestCase {
    protected StudyWrapper command;
    
    protected LocalStudy study;

    protected StudyDao studyDao;

    protected StudyRepository studyRepository;
    
    protected StudyVersion studyVersion;

    @Override
    protected void setUp() throws Exception {
        super.setUp(); // To change body of overridden methods use File | Settings | File
                        // Templates.
        command = new StudyWrapper();
        study = registerMockFor(LocalStudy.class);
        command.setStudy(study);
        studyVersion= registerMockFor(StudyVersion.class);
        studyDao = registerDaoMockFor(StudyDao.class);
        studyRepository = registerMockFor(StudyRepository.class);

    }

}
