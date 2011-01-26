package edu.duke.cabig.c3pr.web.registration.tabs;

import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

public class RegistrationHistoryChartTab<C extends StudySubjectWrapper> extends
		RegistrationTab<C> {
	
	public RegistrationHistoryChartTab() {
		super("Study Subject Timeline", "Study Subject Timeline",
				"registration/reg_history_timeline");
	}
	
}
