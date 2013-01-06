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

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.utils.web.WebUtils;

public class SubjectAdvancedSearchController extends AdvancedSearchController{
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) {
        Map<String, Object> configMap = getConfigurationProperty().getMap();
        Map<String, Object> refdata = new HashMap<String, Object>();
        refdata.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
        refdata.put("ethnicGroupCodes", configMap.get("ethnicGroupCode"));
        refdata.put("raceCodes", configMap.get("raceCode"));
        refdata.put("identifiersType", configMap.get("participantIdentifiersType"));
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
		List<Participant> subjects = getAdvancedSearchRepository().searchSubjects(criteriaList);
		Map map = errors.getModel();
		map.put("subjects", subjects);
		
		request.getSession().setAttribute("resultsViewColumnList", getSubjectSearchColumns());
		request.getSession().setAttribute("searchResultsRowList", getAdvancedSearchSubjectRows(subjects));
		
		ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
		return modelAndView;
	}
	
	private List<ViewColumn> getSubjectSearchColumns(){
		
		List<ViewColumn> subjectSearchColumns = new ArrayList<ViewColumn>();
		ViewColumn viewColumn1 = new ViewColumn();
		viewColumn1.setColumnTitle("Full Name");
		subjectSearchColumns.add(viewColumn1);
		ViewColumn viewColumn2 = new ViewColumn();
		viewColumn2.setColumnTitle("Primary Identifier");
		subjectSearchColumns.add(viewColumn2);
		ViewColumn viewColumn3 = new ViewColumn();
		viewColumn3.setColumnTitle("Gender");
		subjectSearchColumns.add(viewColumn3);
		ViewColumn viewColumn4 = new ViewColumn();
		viewColumn4.setColumnTitle("Ethnicity");
		subjectSearchColumns.add(viewColumn4);
		ViewColumn viewColumn5 = new ViewColumn();
		viewColumn5.setColumnTitle("Birthdate");
		subjectSearchColumns.add(viewColumn5);
		
		return subjectSearchColumns;
	}
	
	private List<AdvancedSearchRow> getAdvancedSearchSubjectRows(List<Participant> subjects){
		List<AdvancedSearchRow> advancedSearchSubjects = new ArrayList<AdvancedSearchRow>();
		
		for(Participant subject:subjects){
			AdvancedSearchRow advancedSearchRow = new AdvancedSearchRow();
			
			List<AdvancedSearchColumn> columnList = new ArrayList<AdvancedSearchColumn>();
			
			AdvancedSearchColumn advancedSearchColumn1 = new AdvancedSearchColumn();
			advancedSearchColumn1.setColumnHeader("Full Name");
			advancedSearchColumn1.setValue(subject.getFullName());
			columnList.add(advancedSearchColumn1);
			
			AdvancedSearchColumn advancedSearchColumn2 = new AdvancedSearchColumn();
			advancedSearchColumn2.setColumnHeader("Primary Identifier");
			advancedSearchColumn2.setValue(subject.getPrimaryIdentifier());
			columnList.add(advancedSearchColumn2);
			
			AdvancedSearchColumn advancedSearchColumn3 = new AdvancedSearchColumn();
			advancedSearchColumn3.setColumnHeader("Gender");
			advancedSearchColumn3.setValue(subject.getAdministrativeGenderCode());
			columnList.add(advancedSearchColumn3);
			
			AdvancedSearchColumn advancedSearchColumn4 = new AdvancedSearchColumn();
			advancedSearchColumn4.setColumnHeader("Ethnicity");
			advancedSearchColumn4.setValue(subject.getEthnicGroupCode());
			columnList.add(advancedSearchColumn4);
			
			AdvancedSearchColumn advancedSearchColumn5 = new AdvancedSearchColumn();
			advancedSearchColumn5.setColumnHeader("Birthdate");
			advancedSearchColumn5.setValue(subject.getBirthDateStr());
			columnList.add(advancedSearchColumn5);
			
			advancedSearchRow.setColumnList(columnList);
			advancedSearchSubjects.add(advancedSearchRow);
		}
		
		return advancedSearchSubjects;
	}
	
}
