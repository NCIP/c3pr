package edu.duke.cabig.c3pr.web.ajax;

import java.util.Collection;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.bean.Table;
import org.extremecomponents.table.core.TableModel;

/**
 * User: kherm
 * 
 * @author kherm manav.kher@semanticbits.com
 */
public class BaseStudyAjaxFacade {
	
    /**
     * Method to build extremecomponents table
     * 
     * @param model
     * @param studies
     * @param title
     * @param action
     *            custom action if any
     * @return
     * @throws Exception
     */
    protected Object build(TableModel model, Collection studies, String title, String action)
                    throws Exception {
   	
        Table table = model.getTableInstance();
        table.setTitle(title);

        table.setAction(model.getContext().getContextPath() + action);
        table.setTableId("studies");
        table.setItems(studies);
        table.setOnInvokeAction("buildTable('studies')");
        table.setShowPagination(false);
        table.setSortable(true);
        table.setImagePath(model.getContext().getContextPath() + "/images/table/*.gif");
        model.addTable(table);

        Row row = model.getRowInstance();
        row.setHighlightRow(Boolean.TRUE);
        model.addRow(row);

        Column columnTitle = model.getColumnInstance();
        columnTitle.setTitle("Short Title");
        columnTitle.setProperty("shortTitleText");
        columnTitle.setCell((ViewStudyLinkCustomCell.class).getName());
        model.addColumn(columnTitle);

        Column columnIdentifier = model.getColumnInstance();
        columnIdentifier.setProperty("primaryIdentifier");
        model.addColumn(columnIdentifier);

        Column columnPhase = model.getColumnInstance();
        columnPhase.setTitle("Phase");
        columnPhase.setProperty("phaseCode");
        model.addColumn(columnPhase);

        Column columnStatus = model.getColumnInstance();
        columnStatus.setTitle("Status");
        columnStatus.setProperty("coordinatingCenterStudyStatus.code");
        model.addColumn(columnStatus);

        return model.assemble();
    }
}
