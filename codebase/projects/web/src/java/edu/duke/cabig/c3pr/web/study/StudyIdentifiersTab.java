package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;

import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 12:51:05 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyIdentifiersTab extends StudyTab {
	
	private StudyValidator studyValidator;


    public StudyIdentifiersTab() {
        super("Study Identifier", "Identifiers", "study/study_identifiers");
    }

    @Override
    public Map<String, Object> referenceData(HttpServletRequest request, Study study) {
        Map<String, Object> refdata = super.referenceData();
        addConfigMapToRefdata(refdata, "identifiersTypeRefData");
        refdata.put("identifiersSourceRefData", getHealthcareSiteDao().getAll());
	    return refdata;
    }

	@Override
	public void validate(Study study, Errors errors) {
		// TODO Auto-generated method stub
		super.validate(study, errors);
		this.studyValidator.validateStudyIdentifiers(study, errors);
		
	}

	public StudyValidator getStudyValidator() {
		return studyValidator;
	}

	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}
}
