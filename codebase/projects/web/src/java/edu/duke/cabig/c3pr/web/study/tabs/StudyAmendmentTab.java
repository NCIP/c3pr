/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.constants.AmendmentType;
import edu.duke.cabig.c3pr.constants.StudyPart;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.utils.web.WebUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

public class StudyAmendmentTab extends StudyTab {

    private StudyValidator studyValidator;

    public StudyValidator getStudyValidator() {
		return studyValidator;
	}

	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}

	public StudyAmendmentTab() {
        super("Amendment details", "Amendments", "study/study_amendments");
        setShowSummary("false");
    }

	@SuppressWarnings("unchecked")
    @Override
    public Map referenceDataForTab(HttpServletRequest request, StudyWrapper wrapper) {
    	 Map<String, Object> refdata = super.referenceDataForTab(request,wrapper);
        refdata.put("disableForm", new Boolean(false));
        refdata.put("mandatory", "true");
        refdata.put("amendmentTypeOptions", WebUtils.collectOptions(AmendmentType.values(), "Please Select"));
        return refdata;
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper, Errors errors) {
    	if(!errors.hasErrors()){
	    	StudyVersion sAmendment = (StudyVersion) wrapper.getStudy().getCurrentStudyAmendment();
	        if (sAmendment != null) {
	        	List<StudyPart> studyParts = sAmendment.getAmendmentReasons();
	        	if (studyParts.contains(StudyPart.DETAIL)) {
	                request.getSession().setAttribute(DISABLE_FORM_DETAILS, new Boolean(false));
	            } else {
	                request.getSession().setAttribute(DISABLE_FORM_DETAILS, new Boolean(true));
	            }
	        	
	        	if (studyParts.contains(StudyPart.DESIGN)) {
	                request.getSession().setAttribute(DISABLE_FORM_EPOCH_AND_ARMS, new Boolean(false));
	            } else {
	                request.getSession().setAttribute(DISABLE_FORM_EPOCH_AND_ARMS, new Boolean(true));
	            }
	        	
	        	if (studyParts.contains(StudyPart.CONSENT)) {
	                request.getSession().setAttribute(DISABLE_FORM_CONSENT, new Boolean(false));
	            } else {
	                request.getSession().setAttribute(DISABLE_FORM_CONSENT, new Boolean(true));
	            }
	        	
	        	if (studyParts.contains(StudyPart.ELIGIBILITY)) {
	                request.getSession().setAttribute(DISABLE_FORM_ELIGIBILITY, new Boolean(false));
	            } else {
	                request.getSession().setAttribute(DISABLE_FORM_ELIGIBILITY, new Boolean(true));
	            }
	
	        	if (studyParts.contains(StudyPart.STRATIFICATION)) {
	                request.getSession().setAttribute(DISABLE_FORM_STRATIFICATION, new Boolean(false));
	            } else {
	                request.getSession().setAttribute(DISABLE_FORM_STRATIFICATION, new Boolean(true));
	            }
	
	        	if (studyParts.contains(StudyPart.DISEASE)) {
	                request.getSession().setAttribute(DISABLE_FORM_DISEASES, new Boolean(false));
	            } else {
	                request.getSession().setAttribute(DISABLE_FORM_DISEASES, new Boolean(true));
	            }
	
	        	if (studyParts.contains(StudyPart.RANDOMIZATION)) {
	                request.getSession().setAttribute(DISABLE_FORM_RANDOMIZATION, new Boolean(false));
	            } else {
	                request.getSession().setAttribute(DISABLE_FORM_RANDOMIZATION, new Boolean(true));
	            }
	
	        	if (studyParts.contains(StudyPart.COMPANION)) {
	                request.getSession().setAttribute(DISABLE_FORM_COMPANION, new Boolean(false));
	            } else {
	                request.getSession().setAttribute(DISABLE_FORM_COMPANION, new Boolean(true));
	            }
	        }
	    }
    }

    @Override
    public void validate(StudyWrapper wrapper, Errors errors) {
    	super.validate(wrapper, errors);
	    this.studyValidator.validateAmendment(wrapper.getStudy(), errors,  wrapper.getStudy().getCurrentStudyAmendment().getName());
    }

}
