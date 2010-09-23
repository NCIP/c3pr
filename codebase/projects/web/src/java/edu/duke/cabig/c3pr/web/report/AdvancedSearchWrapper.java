package edu.duke.cabig.c3pr.web.report;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.apache.commons.collections15.list.LazyList;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

public class AdvancedSearchWrapper {
	
	private List<AdvancedSearchCriteriaParameter> searchCriteriaList = LazyList.decorate(new ArrayList<AdvancedSearchCriteriaParameter>(), new InstantiateFactory<AdvancedSearchCriteriaParameter>(AdvancedSearchCriteriaParameter.class));

	/**
	 * @return the searchCriteriaList
	 */
	public List<AdvancedSearchCriteriaParameter> getSearchCriteriaList() {
		return searchCriteriaList;
	}

	/**
	 * @param searchCriteriaList the searchCriteriaList to set
	 */
	public void setSearchCriteriaList(
			List<AdvancedSearchCriteriaParameter> searchCriteriaList) {
		this.searchCriteriaList = searchCriteriaList;
	}
	
	public void addSearchCriteriaList(AdvancedSearchCriteriaParameter searchCriteria) {
		getSearchCriteriaList().add(searchCriteria);
	}
	
	public void removeSearchCriteriaList(AdvancedSearchCriteriaParameter searchCriteria) {
		getSearchCriteriaList().remove(searchCriteria);
	}
}
