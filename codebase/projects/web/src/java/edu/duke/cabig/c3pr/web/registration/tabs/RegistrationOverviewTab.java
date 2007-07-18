package edu.duke.cabig.c3pr.web.registration.tabs;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class RegistrationOverviewTab extends RegistrationTab<StudySubject>{

	public RegistrationOverviewTab() {
		super("Overview", "Overview", "registration/reg_overview");
	}

	@Override
	public void postProcess(HttpServletRequest arg0, StudySubject arg1, Errors arg2) {
		
	}

	@Override
	protected String postProcessAsynchronous(HttpServletRequest request, StudySubject command, Errors error) {
		// TODO Auto-generated method stub
		return command.getInformedConsentSignedDateStr();
	}

	@Override
	protected void postProcessSynchronous(HttpServletRequest request, StudySubject command, Errors error) {
		// TODO Auto-generated method stub
		
	}
}
