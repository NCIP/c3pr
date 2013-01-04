/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.constants.ConsentRequired;
import edu.duke.cabig.c3pr.domain.validator.ConsentValidator;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

public class StudyConsentTab extends StudyTab {
	public StudyConsentTab() {
        super("Consent", "Consent", "study/study_consents");
    }

	 private ConsentValidator consentValidator;

	    public ConsentValidator getConsentValidator() {
			return consentValidator;
		}

		public void setConsentValidator(ConsentValidator consentValidator) {
			this.consentValidator = consentValidator;
		}

	private StudyValidator studyValidator;

	public StudyValidator getStudyValidator() {
		return studyValidator;
	}
	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}

	@SuppressWarnings("unchecked")
	@Override
    public Map referenceDataForTab(HttpServletRequest request, StudyWrapper wrapper) {
		 Map<String, Object> refdata = super.referenceDataForTab(request,wrapper);
        addConfigMapToRefdata(refdata, "yesNo");
        
        List<Lov> consentRequiredList = new ArrayList<Lov>();
        Lov lov = new Lov();
        for(ConsentRequired consentRequired : ConsentRequired.values()){
        	lov = new Lov();
        	lov.setCode(consentRequired.getName());
        	lov.setDesc(consentRequired.getCode());
        	consentRequiredList.add(lov);
        }
        refdata.put("consentRequired",consentRequiredList);
        return refdata;
    }

	 @Override
	    public void validate(StudyWrapper wrapper, Errors errors) {
	       super.validate(wrapper, errors);
	        this.studyValidator.validateConsents(wrapper.getStudy(), errors);
	    }
}
