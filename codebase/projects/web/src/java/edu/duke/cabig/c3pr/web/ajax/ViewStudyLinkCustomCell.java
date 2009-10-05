package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.ColumnBuilder;
import org.extremecomponents.util.HtmlBuilder;

import edu.duke.cabig.c3pr.domain.RemoteStudy;
import edu.duke.cabig.c3pr.domain.Study;

/**
 * User: kherm Custom colum to display value as a href
 * 
 * @author kherm manav.kher@semanticbits.com
 */
public class ViewStudyLinkCustomCell extends AbstractCell {

    public static final String VIEW_STUDY_URL = "../study/viewStudy?studyId=";

    @Override
    public String getHtmlDisplay(TableModel tableModel, Column column) {
        Study study = (Study) tableModel.getCurrentRowBean();
        CustomHtmlBuilder html = new CustomHtmlBuilder();

        setRowOnClick(tableModel, study);
        
        ColumnBuilder columnBuilder = new ColumnBuilder(html, column);
        columnBuilder.tdStart();
//		Call the html.a() method if you want only one cell to be a hyperlink.
//      This is now replaced with the setRowOnClick() above which makes the entire row clickable  

//      html.a(tableModel.getContext().getContextPath() + VIEW_STUDY_URL, "studyId", study.getId().toString());
//      html.close();
        columnBuilder.tdBody(getCellValue(tableModel, column));
//      html.aEnd();
        columnBuilder.tdEnd();
        return html.toString();
    }

    @Override
    public String getExportDisplay(TableModel model, Column column) {
        return column.getPropertyValueAsString();
    }

    protected String getCellValue(TableModel tableModel, Column column) {
    	Study study = null;
    	if(tableModel.getCurrentRowBean() instanceof Study){
    		study = (Study)tableModel.getCurrentRowBean();
    	} 
    	
    	String cellValue = column.getValueAsString();
    	if(study != null && study instanceof RemoteStudy){
        	cellValue = cellValue + "<img src='/c3pr/images/chrome/nci_icon.png' alt='Calendar' width='17' height='16' border='0' align='middle'/>";
        }
    	setRowOnClick(tableModel, study);
        return cellValue;
    }

    public void setRowOnClick(TableModel tableModel, Study study){    	
    	String url = "document.location='" + VIEW_STUDY_URL + study.getId().toString() + "'";
    	tableModel.getRowHandler().getRow().setOnclick(url);
    	tableModel.getRowHandler().getRow().setStyle("cursor:pointer");        
    }
    
    
    private class CustomHtmlBuilder extends HtmlBuilder {

        public HtmlBuilder a(String href, String paramName, String paramValue) {
            append("<a href=");
            quote();
            append(href);
            append("?");
            append(paramName);
            equals();
            append(paramValue);
            quote();
            return this;

        }
    }

}
