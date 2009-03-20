package edu.duke.cabig.c3pr.web.ajax;

import java.util.List;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;

public class InvestigatorLinkDisplayCell extends AbstractCell {

	public static final String VIEW_INVESTIGATOR_URL= "createInvestigator?emailId=";
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        Investigator inv = (Investigator) model.getCurrentRowBean();
        String cellValue = column.getValueAsString();
        
        if(inv instanceof RemoteInvestigator){
        	cellValue = cellValue + "&nbsp;<img src='/c3pr/images/chrome/nci_icon.png' alt='Calendar' width='17' height='16' border='0' align='middle'/>";
        }
        
        setRowOnClick(model, inv);
        return cellValue;
    }

    public void setRowOnClick(TableModel tableModel, Investigator inv){    	
    	String url = "document.location='" + VIEW_INVESTIGATOR_URL + getEmailId(inv) + "'";
    	tableModel.getRowHandler().getRow().setOnclick(url);
    	tableModel.getRowHandler().getRow().setStyle("cursor:pointer");        
    }
    
    private String getEmailId(Investigator inv){
    	List<ContactMechanism> contactMechanisms = inv.getContactMechanisms();
    	for(ContactMechanism contactMechanism : contactMechanisms){
    		if(contactMechanism.getType() == ContactMechanismType.EMAIL){
    			return contactMechanism.getValue();
    		}
    	}
    	return "" ;
    }

}
