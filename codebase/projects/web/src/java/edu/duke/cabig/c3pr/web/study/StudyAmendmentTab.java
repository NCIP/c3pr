package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyAmendment;
import edu.duke.cabig.c3pr.service.impl.StudyServiceImpl;

public class StudyAmendmentTab extends StudyTab {


	public StudyAmendmentTab() {
		super("Amendment details", "Amendments", "study/study_amendments");
	}

	@Override
	public Map referenceData(HttpServletRequest request, Study study) {
		Map<String, Object> refdata = super.referenceData(study);
//		refdata.put("currentOperation", getConfigurationProperty().getMap().get("inclusion"));
		//this will ensure the amendment form isnt disabled.
		refdata.put("disableForm", new Boolean(false));
		//this will ensure all other tabs/forms are disabled
		refdata.put("mandatory", "true");
		disableAll(request);
//		study.getStudyAmendments().size();
		return refdata;
	}
	
	@Override
	public void postProcess(HttpServletRequest request, Study study,
			Errors errors) {
		
		int size = study.getStudyAmendments().size();
		StudyAmendment sAmendment = (StudyAmendment)study.getStudyAmendments().get(size - 1);
		
		if(sAmendment != null){
			//Note: the epochsIndicator will be null if the checkbox is not checked.
			//In case the epochsIndicator is not null and its value is true.....set the disable for that tab to false.
			//Else(epochsIndicator is null which corresponds to false or not checking the checkbox) set the disable for that tab to true and
			//explicitly set the epochsIndicator in the studyAmendment instance to false.
			if(sAmendment.getEpochAndArmsChangedIndicator() != null && sAmendment.getEpochAndArmsChangedIndicator()){				
				request.getSession().setAttribute(DISABLE_FORM_EPOCH_AND_ARMS, new Boolean(false));
			}else {
				request.getSession().setAttribute(DISABLE_FORM_EPOCH_AND_ARMS, new Boolean(true));
				sAmendment.setEpochAndArmsChangedIndicator(false);
			}			

			if(sAmendment.getEligibilityChangedIndicator() != null && sAmendment.getEligibilityChangedIndicator()){
				request.getSession().setAttribute(DISABLE_FORM_ELIGIBILITY, new Boolean(false));
			}else {
				request.getSession().setAttribute(DISABLE_FORM_ELIGIBILITY, new Boolean(true));
				sAmendment.setEligibilityChangedIndicator(false);
			}		
			
			if(sAmendment.getStratificationChangedIndicator() != null && sAmendment.getStratificationChangedIndicator()){
				request.getSession().setAttribute(DISABLE_FORM_STRATIFICATION, new Boolean(false));
			}else {
				request.getSession().setAttribute(DISABLE_FORM_STRATIFICATION, new Boolean(true));
				sAmendment.setStratificationChangedIndicator(false);
			}						
			
			if(sAmendment.getDiseasesChangedIndicator() != null && sAmendment.getDiseasesChangedIndicator()){
				request.getSession().setAttribute(DISABLE_FORM_DISEASES, new Boolean(false));
			}else {
				request.getSession().setAttribute(DISABLE_FORM_DISEASES, new Boolean(true));
				sAmendment.setDiseasesChangedIndicator(false);
			}
			
			if(sAmendment.getRandomizationChangedIndicator() != null && sAmendment.getRandomizationChangedIndicator()){
				request.getSession().setAttribute(DISABLE_FORM_RANDOMIZATION, new Boolean(false));
			}else {
				request.getSession().setAttribute(DISABLE_FORM_RANDOMIZATION, new Boolean(true));
				sAmendment.setRandomizationChangedIndicator(false); 
			}	
			
			if(sAmendment.getConsentChangedIndicator() != null && sAmendment.getConsentChangedIndicator()){
		//		request.getSession().setAttribute(DISABLE_FORM_EPOCH_AND_ARMS, new Boolean(false));
//			}else {
//				request.getSession().setAttribute(DISABLE_FORM_EPOCH_AND_ARMS, new Boolean(true));
//				sAmendment.setConsentChangedIndicator(false);
			}

			//Change the status from Active to Pending_amendment.
			StudyServiceImpl ssImpl = new StudyServiceImpl(); 
			try{
				//Changing the status only if ti is currently Active.
				if(study.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.ACTIVE){
					study.setCoordinatingCenterStudyStatus(ssImpl.evaluateCoordinatingCenterStudyStatus(study));
				}
				
			}catch(Exception e){
				log.error("Unable to eval status" +e.getMessage());
			}
			
		}
				
	}

}
