package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.StudySubject;

public class RegistrationLinkDisplayCell extends AbstractCell {

    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        StudySubject ss = (StudySubject) model.getCurrentRowBean();
        String cellValue = column.getValueAsString();
        String link = model.getContext().getContextPath()
                        + "/pages/registration/manageRegistration?registrationId=";
        if (ss != null) {
            cellValue = "<a href=\"" + link + ss.getId() + "\">" + cellValue + "</a>";
        }
        return cellValue;
    }

}
