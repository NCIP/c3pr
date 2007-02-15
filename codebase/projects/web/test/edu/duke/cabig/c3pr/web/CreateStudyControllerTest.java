package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;

/**
 * @author Priyatam
 */
public class CreateStudyControllerTest extends WebTestCase {

    private CreateStudyController controller = new CreateStudyController();
    private StudyDao studyDao;
    private HealthCareSiteDaoMock healthcareSiteDao;	
    private StudyService studyService;
    private StudyValidator studyValidator;
    private ConfigurationProperty configurationProperty;
	
    protected void setUp() throws Exception {
        super.setUp();
        studyDao = registerMockFor(StudyDao.class);
        controller.setStudyDao(studyDao);
        healthcareSiteDao = registerMockFor(HealthCareSiteDaoMock.class);
        controller.setHealthcareSiteDao(healthcareSiteDao);   
        studyService = registerMockFor(StudyService.class);
        controller.setStudyService(studyService); 
        studyValidator = registerMockFor(StudyValidator.class);
        controller.setStudyValidator(studyValidator); 
        configurationProperty = registerMockFor(ConfigurationProperty.class);
        controller.setConfigurationProperty(configurationProperty); 
	}
    
    public void testViewOnGet() throws Exception {
//        request.setMethod("GET");
//        ModelAndView mv = controller.handleRequest(request, response);
//        assertEquals("createStudy", mv.getViewName());
    }

    public void testViewOnGoodSubmit() throws Exception {
//        request.addParameter("multiInstitutionIndicator", "true");
//        request.addParameter("shortTitle", "Scott");
//        request.addParameter("longTitle", "Male");
//        request.addParameter("description", "Description");
//        request.addParameter("primarySponsorCode", "Primary Sponsor Code");
//        request.addParameter("phaseCode", "PhaseCode");
//        request.setParameter("_target1", "");        
//        
//        ModelAndView mv = controller.handleRequest(request, response);
//        assertEquals("createStudy", mv.getViewName());
    }    
    
    //private Study postAndReturnCommand() throws Exception {
//        request.setMethod("POST");
//        studyDao.save((Study) notNull());  
//        expectLastCall().atLeastOnce().asStub();
//
//        replayMocks();
//        ModelAndView mv = controller.handleRequest(request, response);
//        verifyMocks();
//
//        Object command = mv.getModel().get("command");
//        assertNotNull("Command not present in model: " + mv.getModel(), command);
//        return (Study) command;
   // }

    public class HealthCareSiteDaoMock extends HealthcareSiteDao{
    	
    	public List<HealthcareSite> getAll()
    	{
    		List list = new ArrayList();
    		return list;
    	}
    }
}
