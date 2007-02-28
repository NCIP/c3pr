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
import edu.duke.cabig.c3pr.utils.ContextTools;
import edu.duke.cabig.c3pr.web.EditParticipantController.LOV;
import static org.easymock.classextension.EasyMock.expect;

/**
 * @author Ramakrishna
 */

public class EditParticipantControllerTest extends ControllerTestCase {

	private EditParticipantController controller = new EditParticipantController();

	private ParticipantDaoMock participantDao;

	private HealthCareSiteDaoMock healthcareSiteDao;

	private ApplicationContext context;

	protected void setUp() throws Exception {
		super.setUp();
		context = ContextTools.createDeployedApplicationContext();
		participantDao = registerMockFor(ParticipantDaoMock.class);
		controller.setParticipantDao(participantDao);
		healthcareSiteDao = registerMockFor(HealthCareSiteDaoMock.class);
		controller.setHealthcareSiteDao(healthcareSiteDao);
	}

	public void testReferenceData() throws Exception {
		Map<String, Object> refdata = controller.referenceData(request, 2);
		List<LOV> races = (List<LOV>) refdata.get("raceCode");
		Iterator<LOV> genderIter = races.iterator();
		LOV race;
		while (genderIter.hasNext()) {
			race = genderIter.next();
			if (race.getCode() == "White") {
				assertEquals("RaceCodes missing or wrong", "White", race
						.getDesc());

			}
		}
	}

	public class HealthCareSiteDaoMock extends HealthcareSiteDao {

		public List<HealthcareSite> getAll() {
			List list = new ArrayList();
			return list;
		}
	}

	public class ParticipantDaoMock extends ParticipantDao {

		public Participant getById(int id, boolean bool) {
			Participant participant = new Participant();
			return participant;
		}
	}
}
