package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;

public class OrganizationLinkDisplayCell extends AbstractCell {

	public static final String VIEW_ORG_URL= "createOrganization?nciIdentifier=";
    
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        HealthcareSite healthcareSite = null;
        
    	if(model.getCurrentRowBean() instanceof HealthcareSite){
    		healthcareSite = (HealthcareSite)model.getCurrentRowBean();
    	} else if(model.getCurrentRowBean() instanceof ResearchStaff ){
    		ResearchStaff rStaff = (ResearchStaff)model.getCurrentRowBean();
    		healthcareSite = rStaff.getHealthcareSite();
    	}
    	
    	String cellValue = column.getValueAsString();
        
        if(healthcareSite != null && healthcareSite instanceof RemoteHealthcareSite){
        	cellValue = cellValue + "<img src='/c3pr/images/chrome/nci_icon.png' alt='Calendar' width='17' height='16' border='0' align='middle'/>";
        }
        setRowOnClick(model, healthcareSite);
        return cellValue;
    }
    
    public void setRowOnClick(TableModel tableModel, HealthcareSite organization){    	
    	String url = "document.location='" + VIEW_ORG_URL + organization.getNciInstituteCode().toString() + "'";
    	tableModel.getRowHandler().getRow().setOnclick(url);
    	tableModel.getRowHandler().getRow().setStyle("cursor:pointer");        
    }

}
