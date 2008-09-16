package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.ResearchStaff;

public class ResearchStaffLinkDisplayCell extends AbstractCell {

	public static final String VIEW_RSTAFF_URL= "createResearchStaff?id=";
    
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        ResearchStaff rStaff = (ResearchStaff) model.getCurrentRowBean();
        String cellValue = column.getValueAsString();
        
        setRowOnClick(model, rStaff);
//        String link = model.getContext().getContextPath() + "/pages/admin/createResearchStaff?id=";
        // String jsCall =
        // "javascript:$('nciIdentifier').value="+organization.getNciInstituteCode()+";${'searchForm'}.submit();";
//        if (rStaff != null) {
//            cellValue = "<a href=\"" + link + rStaff.getId() + "\">" + cellValue + "</a>";
//        }
        return cellValue;
    }
    
    public void setRowOnClick(TableModel tableModel, ResearchStaff rStaff){    	
    	String url = "document.location='" + VIEW_RSTAFF_URL + rStaff.getId().toString() + "'";
    	tableModel.getRowHandler().getRow().setOnclick(url);
    	tableModel.getRowHandler().getRow().setStyle("cursor:pointer");        
    }

}
