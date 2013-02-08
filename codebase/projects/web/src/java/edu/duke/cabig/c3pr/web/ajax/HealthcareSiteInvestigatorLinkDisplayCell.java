/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;

public class HealthcareSiteInvestigatorLinkDisplayCell extends AbstractCell {

	public static final String VIEW_INVESTIGATOR_URL= "editInvestigator?assignedIdentifier=";
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        Investigator inv = (Investigator) model.getCurrentRowBean();
    	String cellValue = "";
    	for(HealthcareSiteInvestigator healthcareSiteInvestigator : inv.getHealthcareSiteInvestigators()){
    		cellValue += healthcareSiteInvestigator.getHealthcareSite().getName()+"<br>"  ;
    	}
    	
        setRowOnClick(model, inv);
        return cellValue;
    }

    public void setRowOnClick(TableModel tableModel, Investigator inv){    	
    	String url = "document.location='" + VIEW_INVESTIGATOR_URL + inv.getAssignedIdentifier() + "'";
    	tableModel.getRowHandler().getRow().setOnclick(url);
    	tableModel.getRowHandler().getRow().setStyle("cursor:pointer");        
    }
    
}
