package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.ResearchStaff;

public class ResearchStaffLinkDisplayCell extends AbstractCell {

    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        ResearchStaff rStaff = (ResearchStaff) model.getCurrentRowBean();
        String cellValue = column.getValueAsString();
        String link = model.getContext().getContextPath() + "/pages/admin/createResearchStaff?id=";
        // String jsCall =
        // "javascript:$('nciIdentifier').value="+organization.getNciInstituteCode()+";${'searchForm'}.submit();";
        if (rStaff != null) {
            cellValue = "<a href=\"" + link + rStaff.getId() + "\">" + cellValue + "</a>";
        }
        return cellValue;
    }

}
