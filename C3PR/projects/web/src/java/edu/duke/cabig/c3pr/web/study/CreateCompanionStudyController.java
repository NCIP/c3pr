package edu.duke.cabig.c3pr.web.study;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyFundingSponsor;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
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
public class CreateCompanionStudyController<C extends StudyWrapper> extends CreateStudyController<C> {

    protected void layoutTabs(Flow flow) {
        flow.addTab(new StudyDetailsTab());
        flow.addTab(new StudyConsentTab());
        flow.addTab(new StudyDesignTab());
        flow.addTab(new StudyEligibilityChecklistTab());
        flow.addTab(new StudyStratificationTab());
        flow.addTab(new StudyRandomizationTab());
        flow.addTab(new StudyDiseasesTab());
        flow.addTab(new StudyOverviewTab("Overview", "Overview", "study/study_summary_create"));
    }

    @Override
    protected Map referenceData(HttpServletRequest request, int arg1)
            throws Exception {
        request.setAttribute(FLOW_TYPE, CREATE_COMPANION_STUDY);
        return super.referenceData(request, arg1);
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        StudyWrapper wrapper = new StudyWrapper();
        String embedded = request.getParameter("embeddedStudy");
        if (embedded != null) {
            Study parentStudy = (Study) request.getSession().getAttribute("studyObj");

            Study companionStudy = new LocalStudy();
            companionStudy.setShortTitleText(parentStudy.getShortTitleText());
            companionStudy.setLongTitleText(parentStudy.getLongTitleText());
            companionStudy.setMultiInstitutionIndicator(parentStudy.getMultiInstitutionIndicator());

            List<StudyFundingSponsor> parentSFS = parentStudy.getStudyFundingSponsors();
            int studyFSIndex = 0;
            for (StudyFundingSponsor studyFS : parentSFS) {
                StudyFundingSponsor sfs = companionStudy.getStudyFundingSponsors().get(studyFSIndex);
                sfs.setHealthcareSite(studyFS.getHealthcareSite());
                studyFSIndex++;
            }

            List<StudySite> parentStudySite = parentStudy.getStudySites();
            int studySiteIndex = 0;
            for (StudySite studySite : parentStudySite) {
                StudySite ss = companionStudy.getStudySites().get(studySiteIndex);
                ss.setHealthcareSite(studySite.getHealthcareSite());
                ss.setIrbApprovalDate(studySite.getIrbApprovalDate());
              //TODO fix it later
//                ss.setStartDate(studySite.getStartDate());
                studySiteIndex++;
            }

            List<StudyCoordinatingCenter> parentStudyCoordinatingCenter = parentStudy.getStudyCoordinatingCenters();
            int cSiteIndex = 0;
            for (StudyCoordinatingCenter studyCoordinatingCenter : parentStudyCoordinatingCenter) {
                StudyCoordinatingCenter scc = companionStudy.getStudyCoordinatingCenters().get(cSiteIndex);
                scc.setHealthcareSite(studyCoordinatingCenter.getHealthcareSite());
                HealthcareSiteInvestigator hsci = parentStudy.getPrincipalInvestigator();
                StudyInvestigator siteInvestigator = scc.getStudyInvestigators().get(0);
                siteInvestigator.setHealthcareSiteInvestigator(hsci);
                scc.addStudyInvestigator(siteInvestigator);
                cSiteIndex++;
            }

            List<SystemAssignedIdentifier> parentSystemAssignedIdentifiers = parentStudy.getSystemAssignedIdentifiers();
            int index1 = 0;
            for (SystemAssignedIdentifier systemIdentifier : parentSystemAssignedIdentifiers) {
                SystemAssignedIdentifier id = companionStudy.getSystemAssignedIdentifiers().get(index1);
                id.setSystemName(systemIdentifier.getSystemName());
                id.setType(systemIdentifier.getType());
                id.setValue(systemIdentifier.getValue() + "<Parent Study Identifier>");
                index1++;
            }

            int index2 = 0;
            List<OrganizationAssignedIdentifier> parentOrganizationAssignedIdentifiers = parentStudy.getOrganizationAssignedIdentifiers();
            for (OrganizationAssignedIdentifier orgIdentifier : parentOrganizationAssignedIdentifiers) {
                OrganizationAssignedIdentifier id = companionStudy.getOrganizationAssignedIdentifiers().get(index2);
                id.setHealthcareSite(orgIdentifier.getHealthcareSite());
                id.setType(orgIdentifier.getType());
                id.setValue(orgIdentifier.getValue() + "<Parent Study Identifier>");
                index2++;
            }

            companionStudy.setCompanionIndicator(companionIndicator);
            if (!companionIndicator) {
                companionStudy.setStandaloneIndicator(true);
            } else {
                companionStudy.setStandaloneIndicator(false);
            }
            wrapper.setStudy(companionStudy);
            return wrapper;
        } else {
            wrapper.setStudy(createDefaultStudyWithDesign());
            return wrapper;
        }

    }
}