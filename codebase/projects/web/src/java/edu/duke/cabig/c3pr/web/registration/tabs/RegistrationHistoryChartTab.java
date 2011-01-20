package edu.duke.cabig.c3pr.web.registration.tabs;

import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class RegistrationHistoryChartTab<C extends StudySubjectWrapper> extends
		RegistrationTab<C> {
	
	public RegistrationHistoryChartTab() {
		super("Registration Timeline", "Registration Timeline",
				"registration/reg_history_timeline");
	}
	
}
