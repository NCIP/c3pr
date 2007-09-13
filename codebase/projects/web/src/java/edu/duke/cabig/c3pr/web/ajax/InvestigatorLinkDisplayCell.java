package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.Investigator;

public class InvestigatorLinkDisplayCell extends AbstractCell {

	 
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
    	Investigator inv = (Investigator) model.getCurrentRowBean();
          String cellValue = column.getValueAsString();
          String link = model.getContext().getContextPath() + "/pages/admin/createInvestigator?id=";
          //String jsCall = "javascript:$('nciIdentifier').value="+organization.getNciInstituteCode()+";${'searchForm'}.submit();";
          if (inv != null) {
                cellValue = "<a href=\"" + link + inv.getId() + "\">" + cellValue + "</a>";
          }
          return cellValue;
    }

}

