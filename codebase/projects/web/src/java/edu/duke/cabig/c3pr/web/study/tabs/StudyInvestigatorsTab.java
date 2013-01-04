/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:02:39 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyInvestigatorsTab extends StudyTab {

    private StudyValidator studyValidator;

    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

    StudySiteDao studySiteDao = null;

    public StudyInvestigatorsTab() {
        this(false);
    }

    public StudyInvestigatorsTab(boolean editMode) {
        super("Investigators", "Investigators", "study/study_investigators");
    }

    @Override
    public Map referenceDataForTab(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        addConfigMapToRefdata(refdata, "studyInvestigatorStatusRefData");
        return refdata;
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper,
                                        Errors errors) {
    	if(!WebUtils.hasSubmitParameter(request, "_actionx")){
    		return;
    	}
    	Integer selectedStudyOrganizationId = Integer.parseInt(request.getParameter("_selectedStudyOrganization"));
    	StudyOrganization selectedStudyOrganization = null;
    	Integer selectedSiteIndex=null;
    	for(int i=0 ; i<wrapper.getStudy().getStudyOrganizations().size() ; i++ ){
    		StudyOrganization studyOrganization = wrapper.getStudy().getStudyOrganizations().get(i);
    		if(studyOrganization.getId().equals(selectedStudyOrganizationId)){
    			selectedStudyOrganization = studyOrganization;
    			selectedSiteIndex = i;
    			break;
    		}
    	}
    	if(selectedStudyOrganization == null){
    		throw new C3PRBaseRuntimeException("Invalid submit.");
    	}
    	String action = request.getParameter("_actionx");
    	if(action.equals("addStudyInvestigators")){
    		String[] studyInvestigatorIds = wrapper.getStudyInvestigatorIds();
            if (studyInvestigatorIds.length > 0) {
                HealthcareSiteInvestigator healthcareSiteInvestigator = null;
                log.debug("Study InvestigatorIds Size : " + studyInvestigatorIds.length);
                for (String studyInvestigatorId : studyInvestigatorIds) {
                    log.debug(" Study Investigator Id : " + studyInvestigatorId);
                    int healthcareSiteInvestigatorId = Integer.parseInt(studyInvestigatorId);
                    if(exists(healthcareSiteInvestigatorId, selectedStudyOrganization.getStudyInvestigators())){
                    	continue;
                    }
                    StudyInvestigator studyInvestigator = new StudyInvestigator();
                    healthcareSiteInvestigator = healthcareSiteInvestigatorDao.getById(healthcareSiteInvestigatorId);
                    if (healthcareSiteInvestigator != null) {
                        healthcareSiteInvestigator.getStudyInvestigators().add(studyInvestigator);
                        studyInvestigator.setHealthcareSiteInvestigator(healthcareSiteInvestigator);
                        studyInvestigator.setRoleCode("Site Investigator");
                        studyInvestigator.setStatusCode(InvestigatorStatusCodeEnum.AC);
                        studyInvestigator.setStudyOrganization(selectedStudyOrganization);
                        selectedStudyOrganization.getStudyInvestigators().add(studyInvestigator);
                    } else {
                        log.error("StudyInvestigatorTab - postProcessOnValidation(): healthcareSiteInvestigatorDao.getById() returned null");
                    }
                }
            }
    	}else if (action.equals("removeStudyInvestigator")) {
    		for(StudyInvestigator studyInvestigator : selectedStudyOrganization.getStudyInvestigators()){
    			if(studyInvestigator.getHealthcareSiteInvestigator().getInvestigator().getAssignedIdentifier().equals(request.getParameter("_selectedInvAssignedId"))){
    				selectedStudyOrganization.getStudyInvestigators().remove(studyInvestigator);
    				break;
    			}
    		}
        }
    	request.setAttribute("selectedStudyOrganization", selectedStudyOrganization);
    	request.setAttribute("selected_site_index", selectedSiteIndex);
    }

    private boolean exists(int id , List<StudyInvestigator> studyInvestigators){
    	for(StudyInvestigator studyInvestigator : studyInvestigators){
    		if(studyInvestigator.getHealthcareSiteInvestigator().getId().equals(id))
    			return true;
    	}
    	return false;
    }
    
    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

    public StudySiteDao getStudySiteDao() {
        return studySiteDao;
    }

    public void setStudySiteDao(StudySiteDao studySiteDao) {
        this.studySiteDao = studySiteDao;
    }

    public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
        return healthcareSiteInvestigatorDao;
    }

    public void setHealthcareSiteInvestigatorDao(
            HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
        this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
    }

}
