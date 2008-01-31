package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import edu.duke.cabig.c3pr.dao.DiseaseTermDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;

import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 3:26:34 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyDiseasesTab extends StudyTab {

    private DiseaseTermDao diseaseTermDao;
    private StudyValidator studyValidator;

    public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}

	public StudyDiseasesTab() {
        super("Diseases", "Diseases", "study/study_diseases");
    }

    @Override
	public Map referenceData(HttpServletRequest request, Study study) {
		Map<String, Object> refdata = super.referenceData(study);
		boolean isAdmin = isAdmin();
		
		if( (request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow").toString().equals("true")) ||
			    (request.getAttribute("editFlow") != null && request.getAttribute("editFlow").toString().equals("true")) )  
    	{
			if(request.getSession().getAttribute(DISABLE_FORM_DISEASES) != null || !isAdmin){
				refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_DISEASES));
			} else {
				refdata.put("disableForm", new Boolean(false));
			}
    	}
		return refdata;
	}
    
    
    @Override
	public void validate(Study study, Errors errors) {
		// TODO Auto-generated method stub
		super.validate(study, errors);
		this.studyValidator.validateStudyDiseases(study, errors);
	}

    @Override
    public void postProcess(HttpServletRequest httpServletRequest, Study study, Errors errors) {

        String selected = httpServletRequest.getParameter("_selected");
        String action = httpServletRequest.getParameter("_actionx");

        if ("addStudyDisease".equals(action)) {
            String[] diseases = study.getDiseaseTermIds();
            log.debug("Study Diseases Size : " + study.getStudyDiseases().size());
            for (String diseaseId : diseases) {
                log.debug("Disease Id : " + diseaseId);
                StudyDisease studyDisease = new StudyDisease();
                studyDisease.setDiseaseTerm(diseaseTermDao.getById(Integer.parseInt(diseaseId)));
                study.addStudyDisease(studyDisease);
            }
        } else if ("removeStudyDisease".equals(action)) {
            study.getStudyDiseases().remove(Integer.parseInt(selected));
        }
    }

    public DiseaseTermDao getDiseaseTermDao() {
        return diseaseTermDao;
    }

    public void setDiseaseTermDao(DiseaseTermDao diseaseTermDao) {
        this.diseaseTermDao = diseaseTermDao;
    }
}
