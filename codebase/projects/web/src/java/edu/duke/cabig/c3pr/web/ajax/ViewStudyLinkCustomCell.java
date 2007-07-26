package edu.duke.cabig.c3pr.web.ajax;

import edu.duke.cabig.c3pr.domain.Study;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.ColumnBuilder;
import org.extremecomponents.util.HtmlBuilder;

/**
 * User: kherm
 * Custom colum to display value as a href
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class ViewStudyLinkCustomCell extends AbstractCell {

    public static final String VIEW_STUDY_URL = "viewStudy";

    @Override
    public String getHtmlDisplay(TableModel tableModel, Column column) {
        Study study = (Study) tableModel.getCurrentRowBean();

        CustomHtmlBuilder html = new CustomHtmlBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder(html, column);
        columnBuilder.tdStart();
        html.a(VIEW_STUDY_URL, "studyId", study.getId().toString());
        html.close();
        columnBuilder.tdBody(column.getValueAsString());
        html.aEnd();
        columnBuilder.tdEnd();
        return html.toString();
    }

    @Override
    public String getExportDisplay(TableModel model, Column column) {
        return column.getPropertyValueAsString();
    }


    protected String getCellValue(TableModel tableModel, Column column) {
        return column.getValueAsString();
    }

    private class CustomHtmlBuilder extends HtmlBuilder {

        public HtmlBuilder a(String href, String paramName, String paramValue) {
            append("<a href=");
            quote();
            append(href);
            append("?");
            append(paramName);
            equals();
            append(paramValue);
            quote();
            return this;

        }


    }

}
