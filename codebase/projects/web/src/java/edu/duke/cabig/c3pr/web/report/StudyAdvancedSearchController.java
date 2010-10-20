package edu.duke.cabig.c3pr.web.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.web.WebUtils;

public class StudyAdvancedSearchController extends AdvancedSearchController{
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) {
        Map<String, Object> configMap = getConfigurationProperty().getMap();
        Map<String, Object> refdata = new HashMap<String, Object>();
        refdata.put("phaseCodeRefData", configMap.get("phaseCodeRefData"));
        refdata.put("typeRefData", configMap.get("typeRefData"));
        refdata.put("yesNo", configMap.get("yesNo"));
        Map<String,Object> studyStatusMap = new HashMap<String,Object>();
        for(CoordinatingCenterStudyStatus studyStatus : CoordinatingCenterStudyStatus.values()){
        	studyStatusMap.put(studyStatus.getName(), studyStatus.getCode());
        }
        
        refdata.put("statusRefDate",studyStatusMap);
        return refdata;
    }
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		AdvancedSearchWrapper wrapper  = (AdvancedSearchWrapper) command ;
		List<AdvancedSearchCriteriaParameter> criteriaList = new ArrayList<AdvancedSearchCriteriaParameter>();
		for(AdvancedSearchCriteriaParameter searchCriteria : wrapper.getSearchCriteriaList()){
			searchCriteria.setValues(WebUtils.removeEmptyStrings(searchCriteria.getValues()));
			if(searchCriteria.getValues().size() != 0 ){
				criteriaList.add(searchCriteria);
			}
		}
		List<Study> studies = getAdvancedSearchRepository().searchStudy(criteriaList);
		Map map = errors.getModel();
		map.put("studies", studies);
		ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
		return modelAndView;
	}
}
