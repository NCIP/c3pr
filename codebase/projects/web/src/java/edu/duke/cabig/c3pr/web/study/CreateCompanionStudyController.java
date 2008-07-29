package edu.duke.cabig.c3pr.web.study;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyFundingSponsor;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

/**
 * Controller class to handle the work flow in the Creation of a Study Design This uses
 * AbstractWizardController to implement tabbed workflow
 * 
 * @author Himanshu
 */
public class CreateCompanionStudyController<C extends Study> extends CreateStudyController<C> {
	
    /**
     * Layout Tabs
     * 
     * @param request -
     *            flow the Flow object
     */
    protected void layoutTabs(Flow flow) {
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
        flow.addTab(new StudyOverviewTab("Overview", "Overview", "study/study_summary_create"));
    }
    
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        String embedded = request.getParameter("embeddedStudy");
        if(embedded != null){
        	Study parentStudy = (Study) request.getSession().getAttribute("studyObj");

        	Study companionStudy =  new Study() ;//parentStudy.getCompanionStudyAssociations().get(Integer.parseInt(row)).getCompanionStudy();
        	companionStudy.setShortTitleText(parentStudy.getShortTitleText());
        	companionStudy.setLongTitleText(parentStudy.getLongTitleText());
        	companionStudy.setMultiInstitutionIndicator(parentStudy.getMultiInstitutionIndicator());
        	
        	List<StudyFundingSponsor> parentSFS = parentStudy.getStudyFundingSponsors();
        	int studyFSIndex = 0 ;
        	for (StudyFundingSponsor studyFS : parentSFS) {
        		StudyFundingSponsor sfs = companionStudy.getStudyFundingSponsors().get(studyFSIndex);
        		sfs.setHealthcareSite(studyFS.getHealthcareSite());
        		studyFSIndex++ ;
    		}
        	
        	List<StudySite> parentStudySite = parentStudy.getStudySites();
        	int studySiteIndex = 0 ;
        	for (StudySite studySite : parentStudySite) {
        		StudySite ss = companionStudy.getStudySites().get(studySiteIndex);
        		ss.setHealthcareSite(studySite.getHealthcareSite());
        		ss.setIrbApprovalDate(studySite.getIrbApprovalDate());
	        	ss.setStartDate(studySite.getStartDate());
        		studySiteIndex++ ;
    		}
        	
        	List<StudyCoordinatingCenter> parentStudyCoordinatingCenter = parentStudy.getStudyCoordinatingCenters();
        	int cSiteIndex = 0 ;
        	for (StudyCoordinatingCenter studyCoordinatingCenter : parentStudyCoordinatingCenter) {
        		StudyCoordinatingCenter scc = companionStudy.getStudyCoordinatingCenters().get(cSiteIndex);
        		scc.setHealthcareSite(studyCoordinatingCenter.getHealthcareSite());
        		HealthcareSiteInvestigator hsci = parentStudy.getPrincipalInvestigator();
        		StudyInvestigator siteInvestigator = scc.getStudyInvestigators().get(0) ;
        		siteInvestigator.setHealthcareSiteInvestigator(hsci);
        		scc.addStudyInvestigator(siteInvestigator);
        		cSiteIndex++ ;
    		}
        	        	
        	List<SystemAssignedIdentifier> parentSystemAssignedIdentifiers = parentStudy.getSystemAssignedIdentifiers();
        	int index1 = 0 ;
        	for (SystemAssignedIdentifier systemIdentifier : parentSystemAssignedIdentifiers ) {
        		SystemAssignedIdentifier id = companionStudy.getSystemAssignedIdentifiers().get(index1);
        		id.setSystemName(systemIdentifier.getSystemName());
        		id.setType(systemIdentifier.getType());
        		id.setValue(systemIdentifier.getValue() + "comp");
        		index1++ ;
    		}
        	
        	int index2 = 0 ;
        	List<OrganizationAssignedIdentifier> parentOrganizationAssignedIdentifiers = parentStudy.getOrganizationAssignedIdentifiers();
        	for (OrganizationAssignedIdentifier orgIdentifier : parentOrganizationAssignedIdentifiers ) {
        		OrganizationAssignedIdentifier id = companionStudy.getOrganizationAssignedIdentifiers().get(index2);
        		id.setHealthcareSite(orgIdentifier.getHealthcareSite());
        		id.setType(orgIdentifier.getType());
        		id.setValue(orgIdentifier.getValue() + "comp");
        		index2++ ;
    		}
        	
        	companionStudy.setCompanionIndicator(companionIndicator);
        	if(!companionIndicator){
        		companionStudy.setStandaloneIndicator(true);
        	}else{
        		companionStudy.setStandaloneIndicator(false);
        	}
        	
        	return companionStudy;
        }else{
        	return createDefaultStudyWithDesign();
        }
        
    }
}