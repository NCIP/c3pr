package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.dao.DiseaseTermDao;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:26:34 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyDiseasesTab extends StudyTab {

    private DiseaseTermDao diseaseTermDao;

    private StudyValidator studyValidator;

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

    public StudyDiseasesTab() {
        super("Diseases", "Diseases", "study/study_diseases");
    }

    @Override
    public Map referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        boolean isAdmin = isAdmin();

        if ((request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow")
                .toString().equals("true"))
                || (request.getAttribute("editFlow") != null && request.getAttribute(
                "editFlow").toString().equals("true"))) {
            if (request.getSession().getAttribute(DISABLE_FORM_DISEASES) != null && !isAdmin) {
                refdata
                        .put("disableForm", request.getSession().getAttribute(
                                DISABLE_FORM_DISEASES));
            } else {
                refdata.put("disableForm", new Boolean(false));
            }
        }
        return refdata;
    }

    @Override
    public void validate(StudyWrapper wrapper, Errors errors) {
        super.validate(wrapper, errors);
        this.studyValidator.validateStudyDiseases(wrapper.getStudy(), errors);
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper,
                                        Errors errors) {

        String selected = request.getParameter("_selected");
        String action = request.getParameter("_actionx");

        if (!errors.hasErrors()) {

            if ("addStudyDisease".equals(action)) {
                String[] diseases = wrapper.getStudy().getDiseaseTermIds();
                log.debug("Study Diseases Size : " + wrapper.getStudy().getStudyDiseases().size());
                for (String diseaseId : diseases) {
                    log.debug("Disease Id : " + diseaseId);
                    DiseaseTerm diseaseTerm = diseaseTermDao.getById(Integer
                            .parseInt(diseaseId));
                    boolean diseasePresent = false;
                    for(int i=0; i< wrapper.getStudy().getStudyDiseases().size();i++){
                    	if (wrapper.getStudy().getStudyDiseases().get(i).getDiseaseTerm().getId().intValue() ==(diseaseTerm.getId().intValue())){
                    		diseasePresent = true;
                    		errors.rejectValue("study.studyDiseases","Disease already exists in study");
                    	}
                    }
                    if (!diseasePresent){
	                    StudyDisease studyDisease = new StudyDisease();
	                    studyDisease
	                            .setDiseaseTerm(diseaseTermDao.getById(Integer
	                                    .parseInt(diseaseId)));
	                    wrapper.getStudy().addStudyDisease(studyDisease);
	                    
	                    studyValidator.validateStudyDiseases(wrapper.getStudy(), errors);
	                    if (errors.hasErrors()) {
	                        wrapper.getStudy().getStudyDiseases().remove(studyDisease);
	                    }
                    }
                }
            }
        }
        if ("removeStudyDisease".equals(action)) {
            wrapper.getStudy().getStudyDiseases().remove(Integer.parseInt(selected));
        }
    }

    public DiseaseTermDao getDiseaseTermDao() {
        return diseaseTermDao;
    }

    public void setDiseaseTermDao(DiseaseTermDao diseaseTermDao) {
        this.diseaseTermDao = diseaseTermDao;
    }
}
