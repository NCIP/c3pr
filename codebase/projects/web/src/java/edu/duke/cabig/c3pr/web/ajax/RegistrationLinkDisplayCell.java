package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.StudySubject;

public class RegistrationLinkDisplayCell extends AbstractCell {

	public static final String VIEW_REG_URL = "/c3pr/pages/registration/manageRegistration?registrationId=";

    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        StudySubject studySubject = (StudySubject) model.getCurrentRowBean();
        String cellValue = column.getValueAsString();
        setRowOnClick(model, studySubject);
        
        /*String link = model.getContext().getContextPath()
                        + "/pages/registration/manageRegistration?registrationId=";
        if (ss != null) {
            cellValue = "<a href=\"" + link + ss.getId() + "\">" + cellValue + "</a>";
        }*/
        return cellValue;
    }

    public void setRowOnClick(TableModel tableModel, StudySubject studySubject){    	
    	String url = "document.location='" + VIEW_REG_URL + studySubject.getId().toString() + "'";
    	tableModel.getRowHandler().getRow().setOnclick(url);
    	tableModel.getRowHandler().getRow().setStyle("cursor:pointer");        
    }
    

}
