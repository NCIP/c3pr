/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.constants.NCIRecognizedProgramName;
import edu.duke.cabig.c3pr.constants.StudyCategory;
import edu.duke.cabig.c3pr.constants.StudySponsorType;
import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
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
        setShowSummary("false");
    }

    /*
    * This method sets the study.randomizationIndicator, study.RandomizationType and
    * epoch.randomization nased on teh values selected. This can be called from both the details
    * and the design tab.
    */
    public void updateRandomization(Study study) {
        super.updateRandomization(study);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> referenceDataForTab(HttpServletRequest request, StudyWrapper wrapper) {
    	 Map<String, Object> refdata = super.referenceDataForTab(request,wrapper);
        addConfigMapToRefdata(refdata, "phaseCodeRefData");
        addConfigMapToRefdata(refdata, "typeRefData");
        addConfigMapToRefdata(refdata, "yesNo");
        
        Map<String,Object> studySponsorTypeMap = new HashMap<String,Object>();
        for(StudySponsorType sponsorType : StudySponsorType.values()){
        	studySponsorTypeMap.put(sponsorType.getName(), sponsorType.getCode());
        }
        refdata.put("studySponsorRefData",studySponsorTypeMap);
        
        Map<String,Object> nciRecognizedProgramNamesMap = new HashMap<String,Object>();
        for(NCIRecognizedProgramName nciRecognizedProgramName : NCIRecognizedProgramName.values()){
        	nciRecognizedProgramNamesMap.put(nciRecognizedProgramName.getName(), nciRecognizedProgramName.getCode());
        }
        refdata.put("nciRecognizedProgramNames",nciRecognizedProgramNamesMap);
        
        Map<String,Object> studyCategoryMap = new HashMap<String,Object>();
        for(StudyCategory studyCategory : StudyCategory.values()){
        	studyCategoryMap.put(studyCategory.getName(), studyCategory.getCode());
        }
        refdata.put("studyCategoryRefData",studyCategoryMap);
        
        if(wrapper.getStudy().getId() == null || SecurityUtils.hasAnyPrivilege(Arrays.asList(new UserPrivilegeType[]{UserPrivilegeType.STUDY_DEFINITION_DETAILS_UPDATE}))){
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
        // CPR-1771 Start
        if(!StringUtils.isBlank(request.getParameter("piCoCenter-hidden")) && !StringUtils.isBlank(request.getParameter("hcsInvestigator-hidden")))  {	
         	
         	HealthcareSiteInvestigator healthcareSiteInvestigator = healthcareSiteInvestigatorDao.getById(Integer.parseInt(request.getParameter("hcsInvestigator-hidden")));
 			boolean invExists = false;
         	
         	if (study.getStudyCoordinatingCenters().get(0).getHealthcareSite().getId().equals(Integer.parseInt(request.getParameter("piCoCenter-hidden")))){
         		if(!study.getStudyCoordinatingCenters().get(0).ifStudyInvestigatorExistsAsPrincipalInvestigator(healthcareSiteInvestigator)){
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
         				// Same logic as coordinating center
         				if (!studySite.ifStudyInvestigatorExistsAsPrincipalInvestigator(healthcareSiteInvestigator)) {
         					if(study.getPrincipalInvestigatorStudyOrganization()!=null){
         						study.getPrincipalInvestigatorStudyOrganization().getStudyInvestigators().remove(study.getPrincipalStudyInvestigator());
         					}
         					StudyInvestigator studyInvestigator = buildPrincipalInvestigator();
 							healthcareSiteInvestigator.addStudyInvestigator(studyInvestigator);
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
     // CPR-1771 End
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
