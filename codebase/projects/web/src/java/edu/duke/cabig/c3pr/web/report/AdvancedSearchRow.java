package edu.duke.cabig.c3pr.web.report;

import java.util.ArrayList;
import java.util.List;

public class AdvancedSearchRow {
	List<AdvancedSearchColumn> columnList;
	/**
	 * @return the columnList
	 */
	public List<AdvancedSearchColumn> getColumnList() {
		if(columnList == null)
			columnList = new ArrayList<AdvancedSearchColumn>();
		return columnList;
	}
	/**
	 * @param columnList the columnList to set
	 */
	public void setColumnList(List<AdvancedSearchColumn> columnList) {
		this.columnList = columnList;
	}


}
