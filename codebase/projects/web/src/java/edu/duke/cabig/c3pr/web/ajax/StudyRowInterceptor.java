package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.interceptor.RowInterceptor;

import edu.duke.cabig.c3pr.domain.Study;

public class StudyRowInterceptor implements RowInterceptor {

	public static final String VIEW_STUDY_URL = "../study/viewStudy?studyId=";
	
	public void addRowAttributes(TableModel tableModel, Row row) {
		// TODO Auto-generated method stub
		/*    	String url = "document.location='" + VIEW_STUDY_URL + study.getId().toString() + "'";
    	tableModel.getRowHandler().getRow().setOnclick(url);
    	tableModel.getRowHandler().getRow().setStyle("cursor:pointer");*/

	}

	public void modifyRowAttributes(TableModel model, Row row) {
		Study study= (Study) model.getCurrentRowBean();
		String url = "document.location='" + VIEW_STUDY_URL + study.getId().toString() + "'";
    	row.setOnclick(url);
    	row.setStyle("cursor:pointer");  
	}

}
