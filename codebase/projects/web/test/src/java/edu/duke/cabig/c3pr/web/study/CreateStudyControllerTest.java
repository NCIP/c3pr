package edu.duke.cabig.c3pr.web.study;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_STUDY;
import static org.easymock.EasyMock.expect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMock;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.web.participant.ParticipantTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDetailsTab;
import gov.nih.nci.cabig.ctms.web.tabs.StaticTabConfigurer;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@C3PRUseCases( { CREATE_STUDY })
public class CreateStudyControllerTest extends AbstractStudyControllerTest {

    private edu.duke.cabig.c3pr.web.study.CreateStudyController controller;

    private HealthCareSiteDaoMock healthcareSiteDao;

    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

    private PersonUserDao personUserDao;

    private StudyValidator studyValidator;

    private ConfigurationProperty configurationProperty;
    
    private StudyDetailsTab studyDetailsTab;
    
    private StudyRepository studyRepository;
    
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

        studyRepository=registerMockFor(StudyRepository.class);
        controller.setStudyRepository(studyRepository);
        // setup controller
        healthcareSiteDao = registerMockFor(HealthCareSiteDaoMock.class);
        controller.setHealthcareSiteDao(healthcareSiteDao);
        healthcareSiteInvestigatorDao = registerDaoMockFor(HealthcareSiteInvestigatorDao.class);
        controller.setHealthcareSiteInvestigatorDao(healthcareSiteInvestigatorDao);
        personUserDao = registerDaoMockFor(PersonUserDao.class);
        controller.setPersonUserDao(personUserDao);
        controller.setStudyRepository(studyRepository);
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
    	request.setParameter("_action", "open");
    	List list=new ArrayList();
    	expect(study.getIdentifiers()).andReturn(list);
    	expect(studyRepository.openStudy(list)).andReturn(study);
    	expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
    	expect(command.getStudy().getId()).andReturn(1);
        request.setMethod("POST");
        replayMocks();
        ModelAndView mv = controller.processFinish(request, response, command, errors);
        assertNull("Command not present in model: ", mv);
        verifyMocks();

    }
    
    public void testPostProcessFinishPendingStudy() throws Exception {
    	request.setParameter("_action", "open");
    	List list=new ArrayList();
    	expect(study.getIdentifiers()).andReturn(list);
    	expect(studyRepository.openStudy(list)).andReturn(study);
    	expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.PENDING);
        request.setMethod("POST");
    	expect(command.getStudy().getId()).andReturn(1);
    	expect(command.getStudy().getId()).andReturn(1);
    	expect(study.getStudyVersion()).andReturn(studyVersion).times(2);
    	expect(studyDao.getById(1)).andReturn(study);
    	Date date= new Date();
    	expect(studyVersion.getVersionDate()).andReturn(date);
    	studyVersion.setVersionDate(date);
        expect(studyRepository.merge(command.getStudy())).andReturn((Study) command.getStudy());
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
