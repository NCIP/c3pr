package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.ContextTools;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.web.participant.ParticipantTab;

/**
 * @author Ramakrishna
 */

public class EditParticipantControllerTest extends ControllerTestCase {

    private EditParticipantController controller = new EditParticipantController();

    private ParticipantDaoMock participantDao;

    private HealthCareSiteDaoMock healthcareSiteDao;

    private ConfigurationProperty configurationProperty;

    private ApplicationContext context;

    private Participant participant;

    protected void setUp() throws Exception {
        super.setUp();
        context = ContextTools.createDeployedApplicationContext();
        participant = registerMockFor(Participant.class);
        participantDao = registerMockFor(ParticipantDaoMock.class);
        controller.setParticipantDao(participantDao);
        healthcareSiteDao = registerMockFor(HealthCareSiteDaoMock.class);
        controller.setHealthcareSiteDao(healthcareSiteDao);
        configurationProperty = registerMockFor(ConfigurationProperty.class);
        controller.setConfigurationProperty(new ConfigurationProperty());
        configurationProperty = (ConfigurationProperty) context.getBean("configurationProperty");
        ((ParticipantTab)controller.getFlow().getTab(0)).setConfigurationProperty(configurationProperty);
        ((ParticipantTab)controller.getFlow().getTab(0)).setHealthcareSiteDao(healthcareSiteDao);
    }

    public void testReferenceData() throws Exception {
        controller.setConfigurationProperty(configurationProperty);
        Map<String, Object> refdata = controller.getFlow().getTab(0).referenceData(participant);
        List<Lov> races = (List<Lov>) refdata.get("raceCode");
        if (races.size() > 0) {
            Iterator<Lov> racesIter = races.iterator();
            Lov race;
            while (racesIter.hasNext()) {
                race = racesIter.next();
                if (race.getCode() == "White") {
                    assertEquals("RaceCodes missing or wrong", "White", race.getDesc());

                }
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