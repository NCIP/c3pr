package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.ContextTools;
import edu.duke.cabig.c3pr.utils.Lov;

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
		controller.setParticipantValidator(participantValidator);
		configurationProperty = registerMockFor(ConfigurationProperty.class);
		controller.setConfigurationProperty(new ConfigurationProperty());
		configurationProperty = (ConfigurationProperty) context
				.getBean("configurationProperty");

	}

	public void testReferenceData() throws Exception {
		
		controller.setConfigurationProperty(configurationProperty);
		Map<String, Object> refdata = controller.getFlow().getTab(0).referenceData(participant);
		List<Lov> genders = (List<Lov>) refdata.get("administrativeGenderCode");
		System.out.println(" Size of ref data : " + refdata.size());
		Iterator<Lov> genderIter = genders.iterator();
		Lov gender;
		while (genderIter.hasNext()) {
			gender = genderIter.next();
			if (gender.getCode() == "Male") {
				assertEquals("Genders missing or wrong", "Male", gender
						.getDesc());

			}
		}

	}

	public void testViewOnGet() throws Exception {
		request.setMethod("GET");
		ModelAndView mv = controller.handleRequest(request, response);
		assertEquals("participant/participant", mv.getViewName());
	}

	public void testViewOnGoodSubmit() throws Exception {
		request.addParameter("firstName", "John");
		request.addParameter("lastName", "Doe");
		request.addParameter("birthDate", "02/11/1967");
		request.addParameter("administrativeGenderCode", "Male");
		request.addParameter("ethnicGroupCode", "Non Hispanic or Latino");
		request.addParameter("raceCode", "Not Reported");
		request.setParameter("_target1", "");

		ModelAndView mv = controller.handleRequest(request, response);
		assertEquals("participant/participant", mv.getViewName());

	}

	/*
	 * private Participant postAndReturnCommand() throws Exception {
	 * request.setMethod("POST"); participantDao.save((new Participant())
	 * notNull()); expectLastCall().atLeastOnce().asStub();
	 * 
	 * replayMocks(); ModelAndView mv = controller.handleRequest(request,
	 * response); verifyMocks();
	 * 
	 * Object command = mv.getModel().get("command"); assertNotNull("Command not
	 * present in model: " + mv.getModel(), command); return (Participant)
	 * command; }
	 */

	public class HealthCareSiteDaoMock extends HealthcareSiteDao {

		public List<HealthcareSite> getAll() {
			List list = new ArrayList();
			list = healthcareSiteDao.getAll();
			return list;
		}
	}
}
