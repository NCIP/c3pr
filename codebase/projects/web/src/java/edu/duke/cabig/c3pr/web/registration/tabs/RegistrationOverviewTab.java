package edu.duke.cabig.c3pr.web.registration.tabs;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class RegistrationOverviewTab<C extends StudySubject> extends InPlaceEditableTab<C>{

	public RegistrationOverviewTab() {
		super("Overview", "Overview", "registration/reg_overview");
	}
}
