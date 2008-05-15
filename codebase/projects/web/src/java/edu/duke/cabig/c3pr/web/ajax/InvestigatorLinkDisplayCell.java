package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.Investigator;

public class InvestigatorLinkDisplayCell extends AbstractCell {

	public static final String VIEW_INVESTIGATOR_URL= "/c3pr/pages/admin/createInvestigator?id=";
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        Investigator inv = (Investigator) model.getCurrentRowBean();
        String cellValue = column.getValueAsString();
        
        setRowOnClick(model, inv);
//        String link = model.getContext().getContextPath() + "/pages/admin/createInvestigator?id=";
        // String jsCall =
        // "javascript:$('nciIdentifier').value="+organization.getNciInstituteCode()+";${'searchForm'}.submit();";
//        if (inv != null) {
//            cellValue = "<a href=\"" + link + inv.getId() + "\">" + cellValue + "</a>";
//        }
        return cellValue;
    }

    public void setRowOnClick(TableModel tableModel, Investigator inv){    	
    	String url = "document.location='" + VIEW_INVESTIGATOR_URL + inv.getId().toString() + "'";
    	tableModel.getRowHandler().getRow().setOnclick(url);
    	tableModel.getRowHandler().getRow().setStyle("cursor:pointer");        
    }

}
