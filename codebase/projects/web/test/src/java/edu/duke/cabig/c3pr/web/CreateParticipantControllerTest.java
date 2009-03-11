package edu.duke.cabig.c3pr.web;

import static org.easymock.EasyMock.expect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.ContextTools;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.web.participant.ParticipantTab;

/**
 * @author Ramakrishna
 */

public class CreateParticipantControllerTest extends ControllerTestCase {

    private CreateParticipantController controller = new CreateParticipantController();

    private ParticipantDao participantDao;

    private HealthCareSiteDaoMock healthcareSiteDao;

    private ParticipantValidator participantValidator;

    private ConfigurationProperty configurationProperty;

    private ApplicationContext context;

    private Participant participant;

    protected void setUp() throws Exception {
        super.setUp();
        context = ContextTools.createDeployedApplicationContext();
        participant = registerMockFor(Participant.class);
        participantDao = registerMockFor(ParticipantDao.class);
        controller.setParticipantDao(participantDao);
        healthcareSiteDao = registerMockFor(HealthCareSiteDaoMock.class);
        controller.setHealthcareSiteDao(healthcareSiteDao);
        participantValidator = registerMockFor(ParticipantValidator.class);
        configurationProperty = registerMockFor(ConfigurationProperty.class);
        controller.setConfigurationProperty(new ConfigurationProperty());
        configurationProperty = (ConfigurationProperty) context.getBean("configurationProperty");
        controller.setConfigurationProperty(configurationProperty);
        ((ParticipantTab)controller.getFlow().getTab(0)).setConfigurationProperty(configurationProperty);
        ((ParticipantTab)controller.getFlow().getTab(0)).setHealthcareSiteDao(healthcareSiteDao);

    }

    public void testReferenceData() throws Exception {

      
        Map<String, Object> refdata = controller.getFlow().getTab(0).referenceData(participant);
        List<Lov> genders = (List<Lov>) refdata.get("administrativeGenderCode");
        System.out.println(" Size of ref data : " + refdata.size());
        Iterator<Lov> genderIter = genders.iterator();
        Lov gender;
        while (genderIter.hasNext()) {
            gender = genderIter.next();
            if (gender.getCode() == "Male") {
                assertEquals("Genders missing or wrong", "Male", gender.getDesc());

            }
        }

    }

    public void testViewOnGet() throws Exception {
    	
    	//expect(healthcareSiteDao.getAll()).andReturn(null).times(2);
        replayMocks();
        ModelAndView mv = controller.handleRequest(request, response);
        assertNotNull("Command not present in model: ", mv);
        assertEquals("participant/participant", mv.getViewName());
        request.setMethod("GET");
        verifyMocks();
    }

    public void testViewOnGoodSubmit() throws Exception {
    	
    	request.addParameter("firstName", "John");
        request.addParameter("lastName", "Doe");
        request.addParameter("birthDate", "02/11/1967");
        request.addParameter("administrativeGenderCode", "Male");
        request.addParameter("ethnicGroupCode", "Non Hispanic or Latino");
        request.addParameter("raceCode", "Not Reported");
        request.setParameter("_target1", "");
    	
    	//expect(healthcareSiteDao.getAll()).andReturn(null).times(2);
        replayMocks();
        ModelAndView mv = controller.handleRequest(request, response);
        assertNotNull("Command not present in model: ", mv);
        assertEquals("participant/participant", mv.getViewName());
        request.setMethod("GET");
        verifyMocks();
    }

    public class HealthCareSiteDaoMock extends HealthcareSiteDao {

        public List<HealthcareSite> getAll() {
            List list = new ArrayList();
            list = healthcareSiteDao.getAll();
            return list;
        }
    }
}
