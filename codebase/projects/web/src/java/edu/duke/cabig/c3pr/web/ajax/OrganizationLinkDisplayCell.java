package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.HealthcareSite;

public class OrganizationLinkDisplayCell extends AbstractCell {

	public static final String VIEW_ORG_URL= "/c3pr/pages/admin/createOrganization?nciIdentifier=";
    
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        HealthcareSite organization = (HealthcareSite) model.getCurrentRowBean();
        String cellValue = column.getValueAsString();
        
        setRowOnClick(model, organization);
        
//        String link = model.getContext().getContextPath() + "/pages/admin/createOrganization?nciIdentifier=";
        // String jsCall =
        // "javascript:$('nciIdentifier').value="+organization.getNciInstituteCode()+";${'searchForm'}.submit();";
//        if (organization != null) {
//            cellValue = "<a href=\"" + link + organization.getNciInstituteCode() + "\">"
//                            + cellValue + "</a>";
//        }
        return cellValue;
    }
    
    public void setRowOnClick(TableModel tableModel, HealthcareSite organization){    	
    	String url = "document.location='" + VIEW_ORG_URL + organization.getNciInstituteCode().toString() + "'";
    	tableModel.getRowHandler().getRow().setOnclick(url);
    	tableModel.getRowHandler().getRow().setStyle("cursor:pointer");        
    }

}
