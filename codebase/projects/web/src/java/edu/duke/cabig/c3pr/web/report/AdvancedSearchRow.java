/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
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
