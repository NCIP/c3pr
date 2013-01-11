/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easymock.EasyMock;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.ContextTools;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.web.participant.ParticipantDetailsTab;
import edu.duke.cabig.c3pr.web.participant.ParticipantTab;
import edu.duke.cabig.c3pr.web.participant.ParticipantWrapper;

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
    
    private ParticipantWrapper participantWrapper = new ParticipantWrapper();

    protected void setUp() throws Exception {
        super.setUp();
        context = ContextTools.createConfigPropertiesApplicationContext();
        participant = registerMockFor(Participant.class);
        participantDao = registerMockFor(ParticipantDao.class);
        controller.setParticipantDao(participantDao);
        healthcareSiteDao = registerMockFor(HealthCareSiteDaoMock.class);
        controller.setHealthcareSiteDao(healthcareSiteDao);
        participantValidator = registerMockFor(ParticipantValidator.class);
        controller.setParticipantValidator(participantValidator);
        configurationProperty = registerMockFor(ConfigurationProperty.class);
        controller.setConfigurationProperty(new ConfigurationProperty());
        configurationProperty = (ConfigurationProperty) context.getBean("configurationProperty");
        controller.setConfigurationProperty(configurationProperty);
        ((ParticipantTab)controller.getFlow().getTab(0)).setConfigurationProperty(configurationProperty);
        ((ParticipantTab)controller.getFlow().getTab(0)).setHealthcareSiteDao(healthcareSiteDao);

    }

    public void testReferenceData() throws Exception {
    	List<RaceCodeEnum> raceCodes = new ArrayList<RaceCodeEnum>();
    	Set<Address> addresses = new HashSet<Address>();
    	EasyMock.expect(participant.getAddresses()).andReturn(addresses);
    	EasyMock.expect(participant.getRaceCodes()).andReturn(raceCodes).times(7);
    	replayMocks();
    	participantWrapper.setParticipant(participant);
        Map<String, Object> refdata = ((ParticipantDetailsTab)controller.getFlow().getTab(0)).referenceData(request,participantWrapper);
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
        
        verifyMocks();
    }

    public void testViewOnGet() throws Exception {
    	
    	Set<Address> addresses = new HashSet<Address>();
    	EasyMock.expect(participant.getAddresses()).andReturn(addresses);
        replayMocks();
        participantWrapper.setParticipant(participant);
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
