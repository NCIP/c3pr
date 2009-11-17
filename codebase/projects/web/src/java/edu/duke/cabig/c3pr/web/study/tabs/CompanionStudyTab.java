package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
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
        refdata.put("dataFromParent", dataForCompanionStudies(wrapper.getStudy()));
        refdata = canDisableTab(request, refdata, DISABLE_FORM_COMPANION);
        return refdata;
    }
    
    @SuppressWarnings("unchecked")
	private Map dataForCompanionStudies(Study study) {
		Map map = new HashMap();
		map.put("shortTitle", study.getShortTitleText());
		map.put("longTitle", study.getLongTitleText());
		map.put("fundingSponsorsList", study.getStudyFundingSponsors());
		map.put("coordinatingCenterList", study.getStudyCoordinatingCenters());
    	return map;
	}
    
    @Override
    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper, Errors errors) {
    	Study study = wrapper.getStudy();
    	for(CompanionStudyAssociation companionStudyAssociation : study.getStudyVersion().getCompanionStudyAssociations()){
    		if(companionStudyAssociation.getId() == null ){
    			Study companionStudy = companionStudyAssociation.getCompanionStudy() ; 
    			updateBlindedRandomization(companionStudy);
//    			for(StudySite stuSite : companionStudyAssociation.getParentStudy().getStudySites()){
//    				if(!stuSite.getIsCoordinatingCenter()){
//	    				StudySite newStudySite = new StudySite();
//	    				newStudySite.setHealthcareSite(stuSite.getHealthcareSite());
//	    				companionStudy.addStudySite(newStudySite);
//    				}
//    			}
    		}
    	}
    	super.postProcessOnValidation(request, wrapper, errors);
    }
}
