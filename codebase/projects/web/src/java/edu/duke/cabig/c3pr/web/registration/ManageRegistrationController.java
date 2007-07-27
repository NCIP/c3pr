package edu.duke.cabig.c3pr.web.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.web.registration.tabs.ManageEpochTab;
import edu.duke.cabig.c3pr.web.registration.tabs.RegistrationOverviewTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

public class ManageRegistrationController<C extends StudySubject> extends RegistrationController<C> {

    public ManageRegistrationController() {
		super("Manage Registration");
	}

	@Override
	protected void intializeFlows(Flow flow) {
		flow.addTab(new RegistrationOverviewTab<StudySubject>());
		flow.addTab(new ManageEpochTab<StudySubject>());
		setFlow(flow);
	}

	@Override
	protected boolean isFormSubmission(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		return super.isFormSubmission(arg0);
	}
	@Override
	protected ModelAndView processFinish(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, BindException arg3) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void onBindOnNewForm(HttpServletRequest request, Object command) throws Exception {
		// TODO Auto-generated method stub
		super.onBindOnNewForm(request, command);
	}
}