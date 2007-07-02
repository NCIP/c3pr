package edu.duke.cabig.c3pr.web.study;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.dao.DiseaseTermDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 3:26:34 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyDiseasesTab extends StudyTab {

    private DiseaseTermDao diseaseTermDao;
    
    public StudyDiseasesTab() {
        super("Study Diseases", "Diseases", "study/study_diseases");
    }


    @Override
    public void postProcess(HttpServletRequest httpServletRequest, Study study, Errors errors) {

        String selected =httpServletRequest.getParameter("_selected");
        String action   = httpServletRequest.getParameter("_action");

        if ("addStudyDisease".equals(action))
		{
			String[] diseases = study.getDiseaseTermIds();
			log.debug("Study Diseases Size : " + study.getStudyDiseases().size());
			for (String diseaseId : diseases) {
				log.debug("Disease Id : " + diseaseId);
				StudyDisease studyDisease = new StudyDisease();
				studyDisease.setDiseaseTerm(diseaseTermDao.getById(Integer.parseInt(diseaseId)));
				study.addStudyDisease(studyDisease);
			}
		}
		else if ("removeStudyDisease".equals(action))
		{
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
