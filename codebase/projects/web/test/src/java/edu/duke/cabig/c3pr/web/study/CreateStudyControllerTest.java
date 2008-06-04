package edu.duke.cabig.c3pr.web.study;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_STUDY;
import static org.easymock.EasyMock.expect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMock;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.web.participant.ParticipantTab;
import gov.nih.nci.cabig.ctms.web.tabs.StaticTabConfigurer;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@C3PRUseCases( { CREATE_STUDY })
public class CreateStudyControllerTest extends AbstractStudyControllerTest {

    private edu.duke.cabig.c3pr.web.study.CreateStudyController controller;

    private HealthCareSiteDaoMock healthcareSiteDao;

    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

    private ResearchStaffDao researchStaffDao;

    private StudyValidator studyValidator;

    private ConfigurationProperty configurationProperty;
    
    private StudyDetailsTab studyDetailsTab;
    
    protected void setUp() throws Exception {
        super.setUp();

        configurationProperty = registerMockFor(ConfigurationProperty.class);
        EasyMock.expect(configurationProperty.getMap()).andReturn(Collections.emptyMap()).anyTimes();

        controller = new CreateStudyController() {
            @Override
            protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
                            throws Exception {
                // do nothing
            }
        };

        controller.setStudyDao(studyDao);

        // setup controller
        healthcareSiteDao = registerMockFor(HealthCareSiteDaoMock.class);
        controller.setHealthcareSiteDao(healthcareSiteDao);
        healthcareSiteInvestigatorDao = registerDaoMockFor(HealthcareSiteInvestigatorDao.class);
        controller.setHealthcareSiteInvestigatorDao(healthcareSiteInvestigatorDao);
        researchStaffDao = registerDaoMockFor(ResearchStaffDao.class);
        controller.setResearchStaffDao(researchStaffDao);
        controller.setStudyService(studyService);
        studyValidator = registerMockFor(StudyValidator.class);
        controller.setStudyValidator(studyValidator);
        StaticTabConfigurer tabConfigurer = new StaticTabConfigurer(healthcareSiteDao);
        tabConfigurer.addBean("configurationProperty", configurationProperty);

        controller.setTabConfigurer(tabConfigurer);
        
    }

    public void testViewOnGoodSubmit() throws Exception {
        replayMocks();
        ModelAndView mv = controller.handleRequest(request, response);
        verifyMocks();
        assertEquals("study/study_details", mv.getViewName());
    }

    public void testPostProcessFinishStudy() throws Exception {
        request.setMethod("POST");
    	expect(command.getTrimmedShortTitleText()).andReturn("Short Title");
    	expect(command.getPrimaryIdentifier()).andReturn("PrimaryId-121");
    	expect(command.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.ACTIVE);
        expect(studyService.merge(command)).andReturn(null);
        replayMocks();
        ModelAndView mv = controller.processFinish(request, response, command, errors);
        assertNull("Command not present in model: ", mv);
        verifyMocks();

    }

    public class HealthCareSiteDaoMock extends HealthcareSiteDao {

        public List<HealthcareSite> getAll() {
            List list = new ArrayList();
            return list;
        }
    }

	public StudyDetailsTab getStudyDetailsTab() {
		return studyDetailsTab;
	}

	public void setStudyDetailsTab(StudyDetailsTab studyDetailsTab) {
		this.studyDetailsTab = studyDetailsTab;
	}
}
