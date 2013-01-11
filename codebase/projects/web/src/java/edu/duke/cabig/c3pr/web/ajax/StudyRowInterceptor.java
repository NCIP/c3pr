/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.interceptor.RowInterceptor;

import edu.duke.cabig.c3pr.domain.Study;

public class StudyRowInterceptor implements RowInterceptor {

	public static final String VIEW_STUDY_URL = "../study/viewStudy?studyId=";
	
	public void addRowAttributes(TableModel tableModel, Row row) {
		// TODO Auto-generated method stub
	}

	public void modifyRowAttributes(TableModel model, Row row) {
		Study study= (Study) model.getCurrentRowBean();
		String url = "document.location='" + VIEW_STUDY_URL + study.getId().toString() + "'";
    	row.setOnclick(url);
    	row.setStyle("cursor:pointer");  
	}

}
