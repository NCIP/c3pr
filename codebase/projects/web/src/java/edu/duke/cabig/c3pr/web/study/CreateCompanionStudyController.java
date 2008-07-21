package edu.duke.cabig.c3pr.web.study;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.duke.cabig.c3pr.domain.Study;
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
//        String row  = request.getParameter("row");	
        if(embedded != null){
        	Study parentStudy = (Study) request.getSession().getAttribute("studyObj");
        	Study companionStudy =  new Study() ;//parentStudy.getCompanionStudyAssociations().get(Integer.parseInt(row)).getCompanionStudy();
        	companionStudy.setShortTitleText(parentStudy.getShortTitleText());
        	companionStudy.setLongTitleText(parentStudy.getLongTitleText());
        	companionStudy.setStudySites(parentStudy.getStudySites());
        	companionStudy.setStudyCoordinatingCenters(parentStudy.getStudyCoordinatingCenters());
        	companionStudy.setOrganizationAssignedIdentifiers(parentStudy.getOrganizationAssignedIdentifiers());
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