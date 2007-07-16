package edu.duke.cabig.c3pr.web.ajax;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.web.beans.StudyXMLFileBean;
import edu.duke.cabig.c3pr.xml.StudyXMLImporter;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.bean.Table;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;
import org.springframework.web.HttpSessionRequiredException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jul 13, 2007
 * Time: 4:55:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudyXMLFileImportAjaxFacade {

    private StudyXMLImporter studyXMLImporter;

    private Object build(TableModel model, Collection studies) throws Exception {

        Table table = model.getTableInstance();
        table.setTableId("studies");
        table.setItems(studies);
        table.setTitle("Studies Imported");
        table.setOnInvokeAction("buildTable('studies')");

        table.setShowExports(false);
        table.setShowStatusBar(false);
        table.setShowTitle(false);
        table.setShowTooltips(false);
        table.setShowPagination(false);

        model.addTable(table);

        Row row = model.getRowInstance();
        row.setHighlightRow(Boolean.FALSE);
        model.addRow(row);

        Column columnIdentifier = model.getColumnInstance();
        columnIdentifier.setProperty("primaryIdentifier");
        model.addColumn(columnIdentifier);

        Column columnTitle = model.getColumnInstance();
        columnTitle.setProperty("shortTitleText");
        model.addColumn(columnTitle);

        Column columnStatus = model.getColumnInstance();
        columnStatus.setProperty("status");
        model.addColumn(columnStatus);

        Column columnPresis = model.getColumnInstance();
        columnPresis.setProperty("precisText");
        model.addColumn(columnPresis);

        Column columnPhase = model.getColumnInstance();
        columnPhase.setProperty("phaseCode");
        model.addColumn(columnPhase);

        Column columnAccrual = model.getColumnInstance();
        columnAccrual.setProperty("targetAccrualNumber");

        model.addColumn(columnAccrual);

        return model.assemble();
    }


    public String getTable(Map parameterMap, HttpServletRequest request)
            throws Exception{
        StudyXMLFileBean studyXMLFile = (StudyXMLFileBean) getCommandOnly(request);
        Context context = null;
        if (parameterMap == null) {
             context = new HttpServletRequestContext(request);
           } else {
             context = new HttpServletRequestContext(request, parameterMap);
           }

        TableModel model = new TableModelImpl(context);
        try {
            Collection<Study> studies = new ArrayList<Study>();
            studies.add(studyXMLImporter.getStudy(studyXMLFile.getReader()));

            return build(model, studies).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

   
    private final Object getCommandOnly(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new HttpSessionRequiredException("Must have session when trying to bind (in session-form mode)");
        }
        String formAttrName = "edu.duke.cabig.c3pr.web.admin.StudyXMLFileUploadController.FORM.command";
        Object sessionFormObject = session.getAttribute(formAttrName);

        return sessionFormObject;
    }

    public StudyXMLImporter getStudyXMLImporter() {
        return studyXMLImporter;
    }

    public void setStudyXMLImporter(StudyXMLImporter studyXMLImporter) {
        this.studyXMLImporter = studyXMLImporter;
    }
}
