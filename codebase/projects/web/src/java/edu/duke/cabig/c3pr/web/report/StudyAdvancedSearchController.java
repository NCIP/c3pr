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
		
		request.getSession().setAttribute("resultsViewColumnList", getAdvancedStudySearchResultColumns());
		request.getSession().setAttribute("searchResultsRowList", getAdvancedStudySearchResultRows(studies));
		
		ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
		return modelAndView;
	}
	
	private List<ViewColumn> getAdvancedStudySearchResultColumns(){
		
		List<ViewColumn> studySearchColumns = new ArrayList<ViewColumn>();
		
		ViewColumn viewColumn1 = new ViewColumn();
		viewColumn1.setColumnTitle("Short Title Text");
		studySearchColumns.add(viewColumn1);
		ViewColumn viewColumn2 = new ViewColumn();
		viewColumn2.setColumnTitle("Primary Identifier");
		studySearchColumns.add(viewColumn2);
		ViewColumn viewColumn3 = new ViewColumn();
		viewColumn3.setColumnTitle("Status");
		studySearchColumns.add(viewColumn3);
		ViewColumn viewColumn4 = new ViewColumn();
		viewColumn4.setColumnTitle("Phase");
		studySearchColumns.add(viewColumn4);
		ViewColumn viewColumn5 = new ViewColumn();
		viewColumn5.setColumnTitle("Type");
		studySearchColumns.add(viewColumn5);
		ViewColumn viewColumn6 = new ViewColumn();
		viewColumn6.setColumnTitle("Target Accrual");
		studySearchColumns.add(viewColumn6);
		ViewColumn viewColumn7 = new ViewColumn();
		viewColumn7.setColumnTitle("Companion");
		studySearchColumns.add(viewColumn7);
		
		return studySearchColumns;
	}
	
	private List<AdvancedSearchRow> getAdvancedStudySearchResultRows(List<Study> studies){
		
		List<AdvancedSearchRow> advancedSearchStudies = new ArrayList<AdvancedSearchRow>();
		
		for(Study study:studies){
			AdvancedSearchRow advancedSearchRow = new AdvancedSearchRow();
			
			List<AdvancedSearchColumn> columnList = new ArrayList<AdvancedSearchColumn>();
			
			AdvancedSearchColumn advancedSearchColumn1 = new AdvancedSearchColumn();
			advancedSearchColumn1.setColumnHeader("Short Title Text");
			advancedSearchColumn1.setValue(study.getShortTitleText());
			columnList.add(advancedSearchColumn1);

			AdvancedSearchColumn advancedSearchColumn2 = new AdvancedSearchColumn();
			advancedSearchColumn2.setColumnHeader("Primary Identifier");
			advancedSearchColumn2.setValue(study.getPrimaryIdentifier());
			columnList.add(advancedSearchColumn2);
			
			AdvancedSearchColumn advancedSearchColumn3 = new AdvancedSearchColumn();
			advancedSearchColumn3.setColumnHeader("Status");
			advancedSearchColumn3.setValue(study.getCoordinatingCenterStudyStatus().getDisplayName());
			columnList.add(advancedSearchColumn3);
			
			AdvancedSearchColumn advancedSearchColumn4 = new AdvancedSearchColumn();
			advancedSearchColumn4.setColumnHeader("Phase");
			advancedSearchColumn4.setValue(study.getPhaseCode());
			columnList.add(advancedSearchColumn4);
			
			AdvancedSearchColumn advancedSearchColumn5 = new AdvancedSearchColumn();
			advancedSearchColumn5.setColumnHeader("Type");
			advancedSearchColumn5.setValue(study.getType());
			columnList.add(advancedSearchColumn5);
			
			AdvancedSearchColumn advancedSearchColumn6 = new AdvancedSearchColumn();
			advancedSearchColumn6.setColumnHeader("Target Accrual");
			advancedSearchColumn6.setValue(study.getTargetAccrualNumber());
			columnList.add(advancedSearchColumn6);
			
			AdvancedSearchColumn advancedSearchColumn7 = new AdvancedSearchColumn();
			advancedSearchColumn7.setColumnHeader("Companion");
			advancedSearchColumn7.setValue(study.getCompanionIndicator()?"Yes":"No");
			columnList.add(advancedSearchColumn7);
			
			advancedSearchRow.setColumnList(columnList);
			advancedSearchStudies.add(advancedSearchRow);
		}
		
		return advancedSearchStudies;
		
	}
}
