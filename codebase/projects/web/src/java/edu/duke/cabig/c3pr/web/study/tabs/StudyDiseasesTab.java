package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.DiseaseCategoryDao;
import edu.duke.cabig.c3pr.dao.DiseaseTermDao;
import edu.duke.cabig.c3pr.domain.DiseaseCategory;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;
import freemarker.template.utility.StringUtil;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:26:34 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyDiseasesTab extends StudyTab {

    private DiseaseTermDao diseaseTermDao;
    private DiseaseCategoryDao diseaseCategoryDao;
    
    public DiseaseCategoryDao getDiseaseCategoryDao() {
		return diseaseCategoryDao;
	}

	public void setDiseaseCategoryDao(DiseaseCategoryDao diseaseCategoryDao) {
		this.diseaseCategoryDao = diseaseCategoryDao;
	}

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
        refdata.put("diseaseCategories", getDiseaseCategories());
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

    private List<DiseaseCategory> getDiseaseCategories() {
    	List<DiseaseCategory> diseaseCategories = diseaseCategoryDao.getAll();
    	List<DiseaseCategory> diseaseCategoryList = new ArrayList<DiseaseCategory>();
    	for(DiseaseCategory diseaseCategory : diseaseCategories){
    		if(diseaseCategory.getParentCategory() == null ){
    			diseaseCategoryList.add(diseaseCategory);
    		}
    	}
    	return diseaseCategoryList;
	}

    public DiseaseTermDao getDiseaseTermDao() {
        return diseaseTermDao;
    }

    public void setDiseaseTermDao(DiseaseTermDao diseaseTermDao) {
        this.diseaseTermDao = diseaseTermDao;
    }
    
    @SuppressWarnings("unchecked")
	public ModelAndView addStudyDiseases(HttpServletRequest request, Object obj,Errors errors) {
    	StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();
		String selectedDiseaseTerms = request.getParameter("selectedDiseaseTerms");
		StringTokenizer st = new StringTokenizer(selectedDiseaseTerms, ",");
		while (st.hasMoreTokens()) {
			String token = st.nextToken() ;
			DiseaseTerm diseaseTerm = diseaseTermDao.getById(Integer.parseInt(token));
		   	StudyDisease studyDisease = new StudyDisease();
		   	studyDisease.setDiseaseTerm(diseaseTerm);
		   	studyDisease.setStudy(study);
			study.addStudyDisease(studyDisease);
		}
		Study modifiedStudy = studyDao.merge(study);
		wrapper.setStudy(modifiedStudy);
		Map map = new HashMap();
		map.put("command", wrapper); 
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}
    
    @SuppressWarnings("unchecked")
	public ModelAndView deleteStudyDiseases(HttpServletRequest request, Object obj,Errors errors) {
		StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();
		String diseaseTermId = request.getParameter("diseaseTermId");
		List<StudyDisease> studyDiseases = study.getStudyDiseases();
		for(StudyDisease studyDisease : studyDiseases){
			if(!StringUtils.isBlank(diseaseTermId)){ 
				if(studyDisease.getDiseaseTerm().getId() == Integer.parseInt(diseaseTermId)){
					study.removeStudyDisease(studyDisease);
					break;
				}
			}else{
				study.removeStudyDisease(studyDisease);
			}
		}
		Study modifiedStudy = studyDao.merge(study);
		wrapper.setStudy(modifiedStudy);
		Map map = new HashMap();
		map.put("command", wrapper); 
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}
}
