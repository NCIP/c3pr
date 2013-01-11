/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.validator;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.Consent;

public class ConsentValidator implements Validator{
	
	private StudyValidator studyValidator;
	private Logger log = Logger.getLogger(ConsentValidator.class);
	
	public StudyValidator getStudyValidator() {
		return studyValidator;
	}

	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}

	public boolean supports(Class clazz) {
        return Consent.class.isAssignableFrom(clazz);
    }
	
	public void validate(Object target, Errors errors) {
    }
  
}
