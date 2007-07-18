package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import gov.nih.nci.cabig.ctms.web.tabs.StaticTabConfigurer;
import org.easymock.EasyMock;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author kherm manav.kher@semanticbits.com
 */

public class CreateStudyControllerTest extends StudyControllerTest {

    private edu.duke.cabig.c3pr.web.study.CreateStudyController controller;
    private HealthCareSiteDaoMock healthcareSiteDao;
    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
    private ResearchStaffDao researchStaffDao;
    private StudyValidator studyValidator;
    private ConfigurationProperty configProperty;


    protected void setUp() throws Exception {
        super.setUp();


        configProperty = registerMockFor(ConfigurationProperty.class);
        EasyMock.expect(configProperty.getMap()).andReturn(Collections.emptyMap()).anyTimes();

        controller = new CreateStudyController() {
            @Override
            protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
                //do nothing
            }
        };

        controller.setStudyDao(studyDao);

        //setup controller
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
        tabConfigurer.addBean("configurationProperty", configProperty);

        controller.setTabConfigurer(tabConfigurer);
    }


    public void testViewOnGoodSubmit() throws Exception {
        replayMocks();
        ModelAndView mv = controller.handleRequest(request, response);
        verifyMocks();
        assertEquals("study/study_details", mv.getViewName());
    }

    public void testPostAndReturnCommand() throws Exception {
        request.setMethod("POST");
        studyService.save(command);
        EasyMock.expectLastCall().atLeastOnce().asStub();

        replayMocks();
        ModelAndView mv = controller.processFinish(request, response, command, errors);
        verifyMocks();

        Object command = mv.getModel().get("command");
        assertNotNull("Command not present in model: " + mv.getModel(), command);

    }

    public class HealthCareSiteDaoMock extends HealthcareSiteDao {

        public List<HealthcareSite> getAll() {
            List list = new ArrayList();
            return list;
        }
    }

}
