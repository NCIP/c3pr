package edu.duke.cabig.c3pr.web.report;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.QueryBuilder;
import com.semanticbits.querybuilder.ResultsetFilter;
import com.semanticbits.querybuilder.TargetObject;


public class AdvancedSearchCommand{
	
	private static final Log logger = LogFactory.getLog(AdvancedSearchCommand.class);
	private QueryBuilder queryBuilder;
	private List<AdvancedSearchCriteriaParameter> criteriaParameters;
	private TargetObject targetObject;
	private String searchName;
	private String searchDescription;
	private String hql;
	private List<ResultsetFilter> resultsViewColumnList;
	private Integer numberOfResults;
	
	public AdvancedSearchCommand(){
		
	}
	
	public AdvancedSearchCommand(QueryBuilder QueryBuilder){
		setQueryBuilder(QueryBuilder);
	}
	
	
	public List<AdvancedSearchCriteriaParameter> getCriteriaParameters(){
		if(criteriaParameters == null)
			criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		return criteriaParameters;
	}
	
	public void setCriteriaParameters(List<AdvancedSearchCriteriaParameter> criteriaParameters){
		this.criteriaParameters = criteriaParameters;
	}
	
	public QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	public TargetObject getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(TargetObject targetObject) {
		this.targetObject = targetObject;
	}

	/**
	 * @return the searchName
	 */
	public String getSearchName() {
		return searchName;
	}

	/**
	 * @param searchName the searchName to set
	 */
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	/**
	 * @return the searchDescription
	 */
	public String getSearchDescription() {
		return searchDescription;
	}

	/**
	 * @param searchDescription the searchDescription to set
	 */
	public void setSearchDescription(String searchDescription) {
		this.searchDescription = searchDescription;
	}

	/**
	 * @return the hql
	 */
	public String getHql() {
		return hql;
	}

	/**
	 * @param hql the hql to set
	 */
	public void setHql(String hql) {
		this.hql = hql;
	}

	/**
	 * @return the numberOfResults
	 */
	public Integer getNumberOfResults() {
		return numberOfResults;
	}

	/**
	 * @param numberOfResults the numberOfResults to set
	 */
	public void setNumberOfResults(Integer numberOfResults) {
		this.numberOfResults = numberOfResults;
	}
	
	public List<ResultsetFilter> getResultsViewColumnList() {
		return resultsViewColumnList;
	}

	public void setResultsViewColumnList(List<ResultsetFilter> resultsViewColumnList) {
		this.resultsViewColumnList = resultsViewColumnList;
	}

}