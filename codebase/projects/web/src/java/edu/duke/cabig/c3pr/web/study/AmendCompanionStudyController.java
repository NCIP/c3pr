package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.web.study.tabs.EditStudyOverviewTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyAmendmentTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDesignTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDetailsTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDiseasesTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyEligibilityChecklistTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyIdentifiersTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyInvestigatorsTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyNotificationTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyPersonnelTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyRandomizationTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudySitesTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyStratificationTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

/**
 * Controller class to handle the work flow in the Creation of a Study Design This uses
 * AbstractWizardController to implement tabbed workflow
 * 
 * @author Himanshu
 */
public class AmendCompanionStudyController extends AmendStudyController {
	
    /**
     * Layout Tabs
     * 
     * @param request -
     *            flow the Flow object
     */
    protected void layoutTabs(Flow flow) {
    	boolean editMode = false;
    	flow.addTab(new StudyAmendmentTab());
        flow.addTab(new StudyDetailsTab());
        flow.addTab(new StudyDesignTab());
        flow.addTab(new StudyEligibilityChecklistTab());
        flow.addTab(new StudyStratificationTab());
        flow.addTab(new StudyRandomizationTab());
        flow.addTab(new StudyDiseasesTab());
        flow.addTab(new StudySitesTab());
        flow.addTab(new StudyIdentifiersTab());
        flow.addTab(new StudyInvestigatorsTab());
        flow.addTab(new StudyPersonnelTab());
        flow.addTab(new StudyNotificationTab());
        flow.addTab(new EditStudyOverviewTab("Summary", "Summary", "study/study_summary_view"));
    }
   
}