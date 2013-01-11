/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;

public class OrganizationLinkDisplayCell extends AbstractCell {

	public static final String VIEW_ORG_URL= "editOrganization?primaryIdentifier=";
    
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        HealthcareSite healthcareSite = (HealthcareSite)model.getCurrentRowBean();
    	String cellValue = column.getValueAsString();
        if(healthcareSite != null && healthcareSite instanceof RemoteHealthcareSite){
        	cellValue = cellValue + "<img src='/c3pr/images/chrome/nci_icon.png' alt='Calendar' width='17' height='16' border='0' align='middle'/>";
        }
        setRowOnClick(model, healthcareSite);
        return cellValue;
    }
    
    public void setRowOnClick(TableModel tableModel, HealthcareSite organization){    	
    	String url = "document.location='" + VIEW_ORG_URL + organization.getPrimaryIdentifier().toString() + "'";
    	tableModel.getRowHandler().getRow().setOnclick(url);
    	tableModel.getRowHandler().getRow().setStyle("cursor:pointer");        
    }

}
