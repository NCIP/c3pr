package edu.duke.cabig.c3pr.web.study;

import static edu.duke.cabig.c3pr.C3PRUseCase.SEARCH_STUDY;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.web.ajax.StudyAjaxFacade;

/**
 * User: kherm
 * 
 * @author kherm manav.kher@semanticbits.com
 */
@C3PRUseCases( { SEARCH_STUDY })
public class StudyAjaxFacadeTest extends AbstractStudyControllerTest {

    StudyAjaxFacade facade;

    @Override
    protected void setUp() throws Exception {
        super.setUp(); // To change body of overridden methods use File | Settings | File
                        // Templates.

        facade = new StudyAjaxFacade();
        facade.setStudyDao(studyDao);

    }

}
