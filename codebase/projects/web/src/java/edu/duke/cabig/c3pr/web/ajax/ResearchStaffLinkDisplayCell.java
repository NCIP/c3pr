/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.RemotePersonUser;
import edu.duke.cabig.c3pr.domain.PersonUser;

public class ResearchStaffLinkDisplayCell extends AbstractCell {

	//public static final String VIEW_RSTAFF_URL= "editResearchStaff?assignedIdentifier=";
	public static final String VIEW_RSTAFF_URL_I= "editPersonOrUser?assignedIdentifier=";
	public static final String VIEW_RSTAFF_URL_II = "&loginId=";

	
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        PersonUser rStaff = (PersonUser) model.getCurrentRowBean();
        String cellValue = column.getValueAsString();
        
        if(rStaff instanceof RemotePersonUser){
        	cellValue = cellValue + "&nbsp;<img src='/c3pr/images/chrome/nci_icon.png' alt='Calendar' width='17' height='16' border='0' align='middle'/>";
        }
        setRowOnClick(model, rStaff);
        return cellValue;
    }
    
    public void setRowOnClick(TableModel tableModel, PersonUser rStaff){    
    	String assignedId = (StringUtils.isBlank(rStaff.getAssignedIdentifier())) ? "" : rStaff.getAssignedIdentifier();
    	String loginId = (StringUtils.isBlank(rStaff.getLoginId())) ? "" : rStaff.getLoginId();
    	String url = "document.location='" + VIEW_RSTAFF_URL_I + assignedId + VIEW_RSTAFF_URL_II + loginId  + "'";
    	tableModel.getRowHandler().getRow().setOnclick(url);
    	tableModel.getRowHandler().getRow().setStyle("cursor:pointer");        
    }
    
}
