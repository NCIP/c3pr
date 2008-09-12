package edu.duke.cabig.c3pr.web.registration.tabs;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;
import edu.duke.cabig.c3pr.web.registration.RegistrationControllerUtils;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public abstract class RegistrationTab<C extends StudySubject> extends InPlaceEditableTab<C> {

    protected ConfigurationProperty configurationProperty;

    protected StudySubjectService studySubjectService;
    
    protected StudySubjectRepository studySubjectRepository;
    
    protected RegistrationControllerUtils registrationControllerUtils;
    

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

    public ConfigurationProperty getConfigurationProperty() {
        return configurationProperty;
    }

    public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
        this.configurationProperty = configurationProperty;
    }
}
