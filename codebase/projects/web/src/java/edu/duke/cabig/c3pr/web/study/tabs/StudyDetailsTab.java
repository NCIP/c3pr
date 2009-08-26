package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 13, 2007 Time: 7:27:09 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyDetailsTab extends StudyTab {

    private StudyValidator studyValidator;

    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

	public void setHealthcareSiteInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
		this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
	}

	public StudyDetailsTab() {
        super("Details", "Details", "study/study_details");
    }

    /*
    * This method sets the study.randomizationIndicator, study.RandomizationType and
    * epoch.randomization nased on teh values selected. This can be called from both the details
    * and the design tab.
    */
    public void updateRandomization(Study study) {
        super.updateRandomization(study);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, Object> referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData();
        addConfigMapToRefdata(refdata, "phaseCodeRefData");
        addConfigMapToRefdata(refdata, "statusRefData");
        addConfigMapToRefdata(refdata, "typeRefData");
        addConfigMapToRefdata(refdata, "yesNo");

        boolean isAdmin = isAdmin();

        if (request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow").toString().equalsIgnoreCase("true")) {
            // amend-flow: set the disableForm refData for the amend flow.
            if (request.getSession().getAttribute(DISABLE_FORM_DETAILS) != null && !isAdmin) {
                refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_DETAILS));
            } else {
                refdata.put("disableForm", new Boolean(false));
                refdata.put("mandatory", "true");
            }
        } else if (request.getAttribute("editFlow") != null && request.getAttribute("editFlow").toString().equalsIgnoreCase("true")) {
            // edit-flow: disable all unless in PENDING STATE. 
            if (!(wrapper.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING) && !isAdmin) {
                disableAll(request);
            } else {
                // all states other than pending
                enableAll(request);
                refdata.put("mandatory", "true");
            }
            // set the disableForm refData for the edit flow.
            if (request.getSession().getAttribute(DISABLE_FORM_DETAILS) != null && !isAdmin) {
                refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_DETAILS));
            } else {
                refdata.put("disableForm", new Boolean(false));
                refdata.put("mandatory", "true");
            }
        } else {
            // this must be the create flow
            enableAll(request);
            refdata.put("mandatory", "true");
        }
        return refdata;
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper, Errors errors) {
    	Study study = wrapper.getStudy() ;
        super.postProcessOnValidation(request, wrapper, errors);
        if (request.getParameter("deletedSponsor") != null && request.getParameter("deletedSponsor").equals("delete")) {
            if (study.getFundingSponsorIdentifierIndex() != -1) {
                study.getOrganizationAssignedIdentifiers().remove(
                        study.getFundingSponsorIdentifierIndex());
            }
            if ((study.getStudyFundingSponsors().size() > 0)) {
                study.getStudyFundingSponsors().remove(0);
            }
        } else
        if (request.getParameter("deletedSponsorIdentifier") != null && request.getParameter("deletedSponsorIdentifier").equals("delete")) {
            if (study.getFundingSponsorIdentifierIndex() != -1) {
                study.getOrganizationAssignedIdentifiers().remove(
                        study.getFundingSponsorIdentifierIndex());
            }

        }
        if(!StringUtils.isBlank(request.getParameter("piCoCenter-hidden")) && !StringUtils.isBlank(request.getParameter("hcsInvestigator-hidden")))  {	
        	
        	HealthcareSiteInvestigator healthcareSiteInvestigator = healthcareSiteInvestigatorDao.getById(Integer.parseInt(request.getParameter("hcsInvestigator-hidden")));
			boolean invExists = false;
        	
        	if (study.getStudyCoordinatingCenters().get(0).getHealthcareSite().getId().equals(Integer.parseInt(request.getParameter("piCoCenter-hidden")))){
        		if(!study.getStudyCoordinatingCenters().get(0).ifStudyInvestigatorExists(healthcareSiteInvestigator)){
        			if(study.getPrincipalInvestigatorStudyOrganization()!=null){
						study.getPrincipalInvestigatorStudyOrganization().getStudyInvestigatorsInternal().remove(study.getPrincipalStudyInvestigator());
					}
        			StudyInvestigator studyInvestigator = buildPrincipalInvestigator();
	        		healthcareSiteInvestigator.addStudyInvestigator(studyInvestigator);
					study.getStudyCoordinatingCenters().get(0).addStudyInvestigator(studyInvestigator);
        		}
        	} else { 
        		boolean siteExists=false;
        		for(StudySite studySite:study.getStudySites()){
        			if(studySite.getHealthcareSite().getId().equals(Integer.parseInt(request.getParameter("piCoCenter-hidden")))){
        				if (!studySite.ifStudyInvestigatorExists(healthcareSiteInvestigator)) {
        					if(study.getPrincipalInvestigatorStudyOrganization()!=null){
        						study.getPrincipalInvestigatorStudyOrganization().getStudyInvestigators().remove(study.getPrincipalStudyInvestigator());
        					}
        					StudyInvestigator studyInvestigator = buildPrincipalInvestigator();
							healthcareSiteInvestigator
									.addStudyInvestigator(studyInvestigator);
							studySite.addStudyInvestigator(studyInvestigator);
							study.addStudySite(studySite);
						}
						siteExists=true;
        			} 
        		}
        		
        		if(!siteExists){
        			StudySite studySite = new StudySite();
        			studySite.setHealthcareSite(healthcareSiteInvestigator.getHealthcareSite());
        			if(study.getPrincipalInvestigatorStudyOrganization()!=null){
						study.getPrincipalInvestigatorStudyOrganization().getStudyInvestigators().remove(study.getPrincipalStudyInvestigator());
					}
        			StudyInvestigator studyInvestigator = buildPrincipalInvestigator();
	        		healthcareSiteInvestigator.addStudyInvestigator(studyInvestigator);
	        		studySite.addStudyInvestigator(studyInvestigator);
	        		
					study.addStudySite(studySite);
        		} 
        	} 
        }
        updateRandomization(study);
        updateStratification(study);
        wrapper.setStudy(study);
    }
    
    
    public StudyInvestigator buildPrincipalInvestigator(){
    	StudyInvestigator studyInvestigator = new StudyInvestigator();
		studyInvestigator.setRoleCode("Principal Investigator");
		studyInvestigator.setStatusCode(InvestigatorStatusCodeEnum.AC);
		return studyInvestigator;
    }

    @Override
    public void validate(StudyWrapper wrapper, Errors errors) {
        super.validate(wrapper, errors);
        studyValidator.validateStudyCoordinatingCetnterIdentifier(wrapper.getStudy(), errors);
        studyValidator.validateStudyFundingSponsorIdentifier(wrapper.getStudy(), errors);
        studyValidator.validateStudyInvestigators(wrapper.getStudy(), errors);

    }
    

    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

}
