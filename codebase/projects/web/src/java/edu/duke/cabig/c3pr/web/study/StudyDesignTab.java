package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.validator.EpochValidator;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;


/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class StudyDesignTab extends StudyTab {
	
	private EpochValidator epochValidator;
	private StudyValidator studyValidator;

	public StudyDesignTab() {
		super("Epochs and Arms", "Epochs & Arms", "study/study_design");
	}


	@Override
	public Map referenceData(HttpServletRequest request, Study study) {
		Map<String, Object> refdata = super.referenceData(study);
		refdata.put("currentOperation", getConfigurationProperty().getMap().get("inclusion"));
		addConfigMapToRefdata(refdata, "yesNo");
		if( (request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow").toString().equals("true")) ||
		    (request.getAttribute("editFlow") != null && request.getAttribute("editFlow").toString().equals("true")) ) 
		{
			if(request.getSession().getAttribute(DISABLE_FORM_EPOCH_AND_ARMS) != null){
				refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_EPOCH_AND_ARMS));
			} else {
				refdata.put("disableForm", new Boolean(false));
			}
		}
		return refdata;
	}
	
	@Override
	public void postProcess(HttpServletRequest httpServletRequest, Study study,
			Errors errors) {
		updateRandomization(study);
	}


	@Override
	public void validate(Study study, Errors errors) {
		// TODO Auto-generated method stub
		super.validate(study, errors);
		this.studyValidator.validateStudyDesign(study, errors);
	}

	public EpochValidator getEpochValidator() {
		return epochValidator;
	}


	public void setEpochValidator(EpochValidator epochValidator) {
		this.epochValidator = epochValidator;
	}


	public StudyValidator getStudyValidator() {
		return studyValidator;
	}


	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}
}
