package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;

/**
 * The Class OrganizationNciIdLinkDisplayCell.
 * This class is used to display the externalId(NCI Identifier) column value.
 * Note that this class is used by  
 * 	   SearchOrganizationAjaxFacade, 
 *     SearchResearchStaffAjaxFacade and
 *     SearchInvestigatorAjaxFacade.
 */
public class NciIdLinkDisplayCell extends AbstractCell {

    
    /**
     * Confirms the instance of the bean(whether staff or inveatigator or org) and then
     * returns the externalId if its remote.
     */
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        
    	String cellValue = "";
        
    	if(model.getCurrentRowBean() instanceof HealthcareSite){
    		HealthcareSite healthcareSite = (HealthcareSite)model.getCurrentRowBean();
    		//getting the external Id (NCI Identifier) if healthcareSite if remote.
    		if(healthcareSite instanceof RemoteHealthcareSite){
            	cellValue = column.getValueAsString();
            }
    	} else if(model.getCurrentRowBean() instanceof ResearchStaff ){
    		ResearchStaff rStaff = (ResearchStaff)model.getCurrentRowBean();
    		//getting the external Id (NCI Identifier) if ResearchStaff if remote.
    		if(rStaff instanceof RemoteResearchStaff){
            	cellValue = column.getValueAsString();
            }
    	} else if(model.getCurrentRowBean() instanceof Investigator ){
    		Investigator investigator = (Investigator)model.getCurrentRowBean();
    		//getting the external Id (NCI Identifier) if investigator if remote.
    		if(investigator instanceof RemoteInvestigator){
    			//return sthe externalId(set in the build method of the ajaxFacade)
            	cellValue = column.getValueAsString();
            }
    	}
        
    	return cellValue;
    }


}
