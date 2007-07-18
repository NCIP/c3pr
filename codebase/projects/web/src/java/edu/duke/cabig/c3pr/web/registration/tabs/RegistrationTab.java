package edu.duke.cabig.c3pr.web.registration.tabs;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
abstract class RegistrationTab<C extends StudySubject> extends SubFlowTab<C>{

	protected ConfigurationProperty configurationProperty;

	public RegistrationTab() {
		super();
	}
	public RegistrationTab(String longTitle, String shortTitle, String viewName) {
		super(longTitle, shortTitle, viewName);
    }
    public RegistrationTab(String longTitle, String shortTitle) {
    	super(longTitle, shortTitle, "");
    }
	public RegistrationTab(String longTitle, String shortTitle, String viewName,String display) {
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
