/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyFundingSponsor;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.StudyPersonnelRole;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

public class CompanionStudyTab extends StudyTab {

	public CompanionStudyTab() {
        super("Companion Studies", "Companion Studies", "study/study_companions");
    }

    @SuppressWarnings("unchecked")
	@Override
    public Map referenceDataForTab(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceDataForTab(request,wrapper);
        addConfigMapToRefdata(refdata, "yesNo");
        return refdata;
    }
    
    public ModelAndView createEmbeddedCompanionStudy(HttpServletRequest request, Object commandObj, Errors error) {
    	StudyWrapper wrapper =  ((StudyWrapper) commandObj) ; 
    	
    	Study study = wrapper.getStudy();
    	Study companionStudy = createCompanionStudyObject(study);
    	
    	int index = study.getCompanionStudyAssociations().size();
    	CompanionStudyAssociation companionStudyAssociation = study.getCompanionStudyAssociations().get(index);
    	companionStudyAssociation.setCompanionStudy(companionStudy);
    	companionStudyAssociation.setMandatoryIndicator(Boolean.FALSE);
    	// changed from save to merge because of a hibernate bug, https://hibernate.onjira.com/browse/HHH-5267
    	Study mergedStudy = studyDao.merge(study);
    	
    	study = studyDao.getById(mergedStudy.getId());
    	
    	wrapper.setStudy(study);
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("companionStudyId", study.getCompanionStudyAssociations().get(index).getCompanionStudy().getId());
        map.put("parentStudyFlow", request.getParameter("flowType"));
    	return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
    	
    	
    }

	private Study createCompanionStudyObject(Study parentStudy) {
		Study companionStudy = new LocalStudy();
		// Adding basic details
		companionStudy.setShortTitleText(parentStudy.getShortTitleText());
		companionStudy.setLongTitleText(parentStudy.getLongTitleText());
		companionStudy.setMultiInstitutionIndicator(parentStudy.getMultiInstitutionIndicator());
		companionStudy.setPhaseCode(parentStudy.getPhaseCode());
		companionStudy.setType(parentStudy.getType());

		// since it is embedded companion hence setting standalone indicator as false
		companionStudy.setCompanionIndicator(Boolean.TRUE);
		companionStudy.setStandaloneIndicator(Boolean.FALSE);
		
		//defaulting stratification and randomization indicator to false since companion studies are usually non stratified and non randomized CPR-1339
		companionStudy.setStratificationIndicator(Boolean.FALSE);
		companionStudy.setRandomizedIndicator(Boolean.FALSE);
		
		// Adding funding sponsor from parent study
		List<StudyFundingSponsor> parentSFS = parentStudy.getStudyFundingSponsors();
		int studyFSIndex = 0;
		for (StudyFundingSponsor studyFS : parentSFS) {
			StudyFundingSponsor sfs = companionStudy.getStudyFundingSponsors().get(studyFSIndex);
			sfs.setHealthcareSite(studyFS.getHealthcareSite());
			studyFSIndex++;
		}

		// Adding coordinating center from parent study
		List<StudyCoordinatingCenter> parentStudyCoordinatingCenter = parentStudy.getStudyCoordinatingCenters();
		int cSiteIndex = 0;
		for (StudyCoordinatingCenter studyCoordinatingCenter : parentStudyCoordinatingCenter) {
			StudyCoordinatingCenter scc = companionStudy.getStudyCoordinatingCenters().get(cSiteIndex);
			scc.setHealthcareSite(studyCoordinatingCenter.getHealthcareSite());
			//copying co-ordinating center identifier 
			OrganizationAssignedIdentifier identifier = companionStudy.getOrganizationAssignedIdentifiers().get(0);
			identifier.setHealthcareSite(studyCoordinatingCenter.getHealthcareSite());
			identifier.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
			identifier.setValue(parentStudy.getCoordinatingCenterAssignedIdentifier().getValue() + "-companion");
			identifier.setPrimaryIndicator(Boolean.TRUE);
			
			for (StudyInvestigator studyInvestigator : studyCoordinatingCenter.getStudyInvestigators()) {
				StudyInvestigator siteInvestigator = scc.getStudyInvestigators().get(0);
				siteInvestigator.setHealthcareSiteInvestigator(studyInvestigator.getHealthcareSiteInvestigator());
				siteInvestigator.setRoleCode(studyInvestigator.getRoleCode());
				siteInvestigator.setStatusCode(studyInvestigator.getStatusCode());
				scc.addStudyInvestigator(siteInvestigator);
			}
			cSiteIndex++;
		}

		// Adding coordinating center from parent study
		List<StudySite> parentStudySites = parentStudy.getStudySites();
		int siteIndex = 0;
		for (StudySite studySite : parentStudySites) {
			StudySite ss = new StudySite();
			ss.setHealthcareSite(studySite.getHealthcareSite());
			
			//copying study investigators and personnels for all studysites
			for (StudyInvestigator studyInvestigator : studySite.getStudyInvestigators()) {
				StudyInvestigator sInvestigator = new StudyInvestigator();
				sInvestigator.setHealthcareSiteInvestigator(studyInvestigator.getHealthcareSiteInvestigator());
				sInvestigator.setRoleCode(studyInvestigator.getRoleCode());
				ss.addStudyInvestigator(sInvestigator);
			}
			
			for (StudyPersonnel studyPersonnel : studySite.getStudyPersonnel()) {
				StudyPersonnel sPersonnel = new StudyPersonnel();
				sPersonnel.setPersonUser(studyPersonnel.getPersonUser());
				for(StudyPersonnelRole studyPersonnelRole : studyPersonnel.getStudyPersonnelRoles()){
					StudyPersonnelRole sPersonnelRole = new StudyPersonnelRole();
					sPersonnelRole.setRole(studyPersonnelRole.getRole());
					sPersonnel.getStudyPersonnelRoles().add(sPersonnelRole);
				}
				sPersonnel.setRoleCode(studyPersonnel.getRoleCode());
				sPersonnel.setStartDate(studyPersonnel.getStartDate());
				sPersonnel.setStatusCode(studyPersonnel.getStatusCode());
				ss.addStudyPersonnel(sPersonnel);
			}
			
			companionStudy.addStudySite(ss);
			siteIndex++;
		}
		return companionStudy;

	}

}
