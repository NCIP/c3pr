package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;

public class ResearchStaffLinkDisplayCell extends AbstractCell {

	public static final String VIEW_RSTAFF_URL= "editResearchStaff?emailId=";
    
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        ResearchStaff rStaff = (ResearchStaff) model.getCurrentRowBean();
        String cellValue = column.getValueAsString();
        
        if(rStaff instanceof RemoteResearchStaff){
        	cellValue = cellValue + "&nbsp;<img src='/c3pr/images/chrome/nci_icon.png' alt='Calendar' width='17' height='16' border='0' align='middle'/>";
        }
        setRowOnClick(model, rStaff);
        return cellValue;
    }
    
    public void setRowOnClick(TableModel tableModel, ResearchStaff rStaff){    	
    	String url = "document.location='" + VIEW_RSTAFF_URL + rStaff.getEmailAsString() + "'";
    	tableModel.getRowHandler().getRow().setOnclick(url);
    	tableModel.getRowHandler().getRow().setStyle("cursor:pointer");        
    }
    
}
