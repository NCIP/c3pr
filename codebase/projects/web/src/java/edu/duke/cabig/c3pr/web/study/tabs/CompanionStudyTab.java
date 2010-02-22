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
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

public class CompanionStudyTab extends StudyTab {

	public CompanionStudyTab() {
        super("Companion Studies", "Companion Studies", "study/study_companions");
    }

    @SuppressWarnings("unchecked")
	@Override
    public Map referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        request.getSession().setAttribute("studyObj", wrapper.getStudy());
        
        Map<String, Object> refdata = super.referenceData(wrapper);
        addConfigMapToRefdata(refdata, "phaseCodeRefData");
        addConfigMapToRefdata(refdata, "statusRefData");
        addConfigMapToRefdata(refdata, "typeRefData");
        addConfigMapToRefdata(refdata, "yesNo");
        refdata = canDisableTab(request, refdata, DISABLE_FORM_COMPANION);
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
    	study = studyDao.merge(study);
    	
    	wrapper.setStudy(study);
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("companionStudyId", study.getCompanionStudyAssociations().get(index).getCompanionStudy().getId());
        map.put("parentStudyFlow", getFlow().getName());
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
		companionStudy.setCompanionIndicator(true);
		companionStudy.setStandaloneIndicator(false);

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
			
			for (StudyInvestigator studyInvestigator : studyCoordinatingCenter.getStudyInvestigators()) {
				StudyInvestigator siteInvestigator = scc.getStudyInvestigators().get(0);
				siteInvestigator.setHealthcareSiteInvestigator(studyInvestigator.getHealthcareSiteInvestigator());
				siteInvestigator.setRoleCode(studyInvestigator.getRoleCode());
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
			for (StudyInvestigator studyInvestigator : studySite.getStudyInvestigators()) {
				StudyInvestigator siteInvestigator = ss.getStudyInvestigators().get(0);
				siteInvestigator.setHealthcareSiteInvestigator(studyInvestigator.getHealthcareSiteInvestigator());
				siteInvestigator.setRoleCode(studyInvestigator.getRoleCode());
				ss.addStudyInvestigator(siteInvestigator);

			}
			companionStudy.addStudySite(ss);
			siteIndex++;
		}
		return companionStudy;

	}



}
