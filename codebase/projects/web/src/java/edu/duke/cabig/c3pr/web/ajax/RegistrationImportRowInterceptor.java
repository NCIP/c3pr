package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.interceptor.RowInterceptor;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;

public class RegistrationImportRowInterceptor implements RowInterceptor {

	public static final String VIEW_REG_URL= "../registration/manageRegistration?";
	
	public void addRowAttributes(TableModel tableModel, Row row) {
		// TODO Auto-generated method stub

	}

	public void modifyRowAttributes(TableModel model, Row row) {
		StudySubject studySubject= (StudySubject) model.getCurrentRowBean();
		String url = "document.location='" + VIEW_REG_URL + ControllerTools.createParameterString(studySubject.getSystemAssignedIdentifiers().get(0)) + "'";
    	row.setOnclick(url);
    	row.setStyle("cursor:pointer");  
	}

}
