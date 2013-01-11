/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.duke.cabig.c3pr.web.study.tabs.StudyAmendmentTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyConsentTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDesignTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDetailsTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDiseasesTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyEligibilityChecklistTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyOverviewTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyRandomizationTab;
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
     *                flow the Flow object
     */
    protected void layoutTabs(Flow flow) {
        boolean editMode = false;
        flow.addTab(new StudyAmendmentTab());
        flow.addTab(new StudyDetailsTab());
        flow.addTab(new StudyConsentTab());
        flow.addTab(new StudyDesignTab());
        flow.addTab(new StudyEligibilityChecklistTab());
        flow.addTab(new StudyStratificationTab());
        flow.addTab(new StudyRandomizationTab());
        flow.addTab(new StudyDiseasesTab());
        flow.addTab(new StudyOverviewTab("Summary", "Summary", "study/study_summary_view"));
    }

    @Override
    protected Map referenceData(HttpServletRequest request, int arg1)
            throws Exception {
        request.setAttribute(FLOW_TYPE, AMEND_COMPANION_STUDY);
        return super.referenceData(request, arg1);
    }

}
