package edu.duke.cabig.c3pr.web.ajax;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.bean.Table;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;
import org.springframework.web.HttpSessionRequiredException;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.service.StudySubjectXMLImporterService;
import edu.duke.cabig.c3pr.utils.ImportErrors;
import edu.duke.cabig.c3pr.web.beans.FileBean;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jul 13, 2007 Time: 4:55:08 PM To
 * change this template use File | Settings | File Templates.
 */
public class StudySubjectXMLFileImportAjaxFacade {

	private static Log log = LogFactory
			.getLog(StudySubjectXMLFileImportAjaxFacade.class);

	public String getTable(Map parameterMap, HttpServletRequest request, Collection<StudySubject> studySubjects)
			throws Exception {

		Context context = null;
		if (parameterMap == null) {
			context = new HttpServletRequestContext(request);
		} else {
			context = new HttpServletRequestContext(request, parameterMap);
		}

		TableModel model = new TableModelImpl(context);
		String action = "/pages/admin/importStudy";

		
		
		return build(model, studySubjects, "Imported Registrations", action)
				.toString();
	}

	protected Object build(TableModel model, Collection studySubjects, String title, String action) throws Exception {

		Table table = model.getTableInstance();
		table.setTitle(title);

		table.setAction(model.getContext().getContextPath() + action);
		table.setTableId("studySubjects");
		table.setItems(studySubjects);
		table.setOnInvokeAction("buildTable('studySubjects')");
		table.setShowPagination(false);
		table.setSortable(true);
		table.setImagePath(model.getContext().getContextPath()
				+ "/images/table/*.gif");
		model.addTable(table);

		Row row = model.getRowInstance();
		row.setHighlightRow(Boolean.TRUE);
		model.addRow(row);
		
		Column columnRegId = model.getColumnInstance();
		columnRegId.setTitle("Registration Id");
		columnRegId.setProperty("id");
		columnRegId.setCell((RegistrationLinkDisplayCell.class).getName());
		model.addColumn(columnRegId);

		Column columnTitle = model.getColumnInstance();
		columnTitle.setTitle("Study Short Title");
		columnTitle.setProperty("studySite.study.shortTitleText");
		model.addColumn(columnTitle);

		Column columnIdentifier = model.getColumnInstance();
		columnIdentifier.setTitle("Study Identifier");
		columnIdentifier.setProperty("studySite.study.primaryIdentifier");
		model.addColumn(columnIdentifier);

		Column columnSubject = model.getColumnInstance();
		columnSubject.setTitle("Name");
		columnSubject.setProperty("participant.fullName");
		model.addColumn(columnSubject);

		Column columnMRN = model.getColumnInstance();
		columnMRN.setTitle("Subject MRN");
		columnMRN.setProperty("participant.primaryIdentifier");
		model.addColumn(columnMRN);

		Column columnSite = model.getColumnInstance();
		columnSite.setTitle("Site");
		columnSite.setProperty("studySite.healthcareSite.name");
		model.addColumn(columnSite);

		Column columnStatus = model.getColumnInstance();
		columnStatus.setTitle("Registration Status");
		columnStatus.setProperty("regWorkflowStatus.code");
		model.addColumn(columnStatus);

		Column columnDate = model.getColumnInstance();
		columnDate.setTitle("Registration Date");
		columnDate.setProperty("startDateStr");
		model.addColumn(columnDate);

		Column columnPhysician = model.getColumnInstance();
		columnPhysician.setTitle("Treating Physician");
		columnPhysician.setProperty("treatingPhysicianFullName");
		model.addColumn(columnPhysician);

		return model.assemble();
	}
}
