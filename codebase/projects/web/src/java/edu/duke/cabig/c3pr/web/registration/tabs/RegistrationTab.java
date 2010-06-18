package edu.duke.cabig.c3pr.web.registration.tabs;

import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;
import edu.duke.cabig.c3pr.web.registration.RegistrationControllerUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public abstract class RegistrationTab<C extends StudySubjectWrapper> extends InPlaceEditableTab<C> {

    protected ConfigurationProperty configurationProperty;

    protected StudySubjectService studySubjectService;
    
    protected StudySubjectRepository studySubjectRepository;
    
    protected RegistrationControllerUtils registrationControllerUtils;
    
    protected EpochDao epochDao;
    
    protected StudyDao studyDao;
    
    protected ParticipantDao participantDao;

    protected StudySubjectDao studySubjectDao;

    protected HealthcareSiteDao healthcareSiteDao;
    
    protected StudySiteDao studySiteDao;
    
    protected Configuration configuration;
    
    protected static final String OPERATION_TYPE_PARAM_NAME="operationType";
	
    protected static final String OFF_STUDY_OPERATION="takeSubjectOffStudy";
	
    protected static final String FAIL_SCREENING_OPERATION="failScreening";
	
    protected static final String CHANGE_EPOCH_OPERATION="changeEpochAssignment";
	
    protected static final String ACTION_PARAM_NAME="action";

    public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public RegistrationControllerUtils getRegistrationControllerUtils() {
		return registrationControllerUtils;
	}

	public void setRegistrationControllerUtils(
			RegistrationControllerUtils registrationControllerUtils) {
		this.registrationControllerUtils = registrationControllerUtils;
	}

	public void setStudySubjectRepository(StudySubjectRepository studySubjectRepository) {
        this.studySubjectRepository = studySubjectRepository;
    }

    public void setStudySubjectService(StudySubjectService studySubjectService) {
        this.studySubjectService = studySubjectService;
    }

    public RegistrationTab(String longTitle, String shortTitle, String viewName) {
        super(longTitle, shortTitle, viewName);
    }

    public RegistrationTab(String longTitle, String shortTitle) {
        super(longTitle, shortTitle, "");
    }

    public RegistrationTab(String longTitle, String shortTitle, String viewName, String display) {
        super(longTitle, shortTitle, viewName);
        setDisplay(display);
    }

    public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
        this.configurationProperty = configurationProperty;
    }

	public void setEpochDao(EpochDao epochDao) {
		this.epochDao = epochDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public void setStudySiteDao(StudySiteDao studySiteDao) {
		this.studySiteDao = studySiteDao;
	}
}
