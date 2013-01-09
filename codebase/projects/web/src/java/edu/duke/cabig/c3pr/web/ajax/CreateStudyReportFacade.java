/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.bean.Table;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.StringUtils;

public class CreateStudyReportFacade {

	private StudySubjectDao studySubjectDao;

	private StudyDao studyDao;

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public Object build(TableModel model, Collection studies, String title,
			String action) throws Exception {

		Table table = model.getTableInstance();
		table.setTitle(title);

		table.setAction(model.getContext().getContextPath() + action);
		table.setTableId("studies");
		table.setItems(studies);
		table.setOnInvokeAction("buildTable('studies')");
		table.setShowPagination(false);
		table.setSortable(true);
		table.setShowExports(false);
		table.setImagePath(model.getContext().getContextPath()
				+ "/images/table/*.gif");
		model.addTable(table);

		Row row = model.getRowInstance();
		row.setInterceptor("edu.duke.cabig.c3pr.web.ajax.StudyRowInterceptor");
		model.addRow(row);

		Column columnTitle = model.getColumnInstance();
		columnTitle.setTitle("Short Title");
		columnTitle.setProperty("shortTitleText");
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

		Column columnCompanion = model.getColumnInstance();
		columnCompanion.setTitle("Companion Indicator");
		columnCompanion.setProperty("companionIndicatorDisplayValue");
		model.addColumn(columnCompanion);

		// Column columnSite = model.getColumnInstance();
		// columnSite.setTitle("Sites");
		// columnSite.setProperty("printStudySites");
		// model.addColumn(columnSite);

		return model.assemble();
	}

	public Object build(TableModel model, Collection studies) throws Exception {

		Export export = model.getExportInstance();
		export.setView(TableConstants.VIEW_XLS);
		export.setViewResolver(TableConstants.VIEW_XLS);
		export.setImageName(TableConstants.VIEW_XLS);
		export.setText(TableConstants.VIEW_XLS);
		export.setFileName("study_report.xls");
		model.addExport(export);

		return build(model, studies, "Search Results",
				"/pages/report/createStudyReport");
	}

	public String getTable(Map<String, List> parameterMap, String[] params,
			HttpServletRequest request) {

		List<StudySubject> studySubjectResults;
		Participant participant;
		SystemAssignedIdentifier id;

		Study study = new LocalStudy(true);
		if (!StringUtils.isEmpty(params[0].toString())) {
			study.setShortTitleText(params[0].toString());
		}
		if (!StringUtils.isEmpty(params[1].toString())) {
			id = new SystemAssignedIdentifier();
			id.setValue(params[1].toString());
			study.addIdentifier(id);
		}

		List<Study> studyList = new ArrayList<Study>();

		// this if -else ensures that participant is null if no data relevant to
		// participant is
		// entered and the studyDao is called.
		if (StringUtils.isEmpty(params[2].toString())
				&& StringUtils.isEmpty(params[3].toString())
				&& StringUtils.isEmpty(params[4].toString())) {
			participant = null;
			// call the studyDao if participant is null.
			studyList = studyDao.searchByExample(study, true, 0);
		} else {
			participant = new Participant();
			id = new SystemAssignedIdentifier();
			if (!StringUtils.isEmpty(params[2].toString())) {
				id.setValue(params[2].toString());
				participant.addIdentifier(id);
			}

			if (!StringUtils.isEmpty(params[3].toString())) {
				participant.setFirstName(params[3].toString());

			}
			if (!StringUtils.isEmpty(params[4].toString())) {
				participant.setLastName(params[4].toString());
			}

			StudySite studySite = new StudySite();
			study.addStudySite(studySite);

			StudySubject studySubject = new StudySubject();
			studySubject.setStudySite(studySite);
			studySubject.setParticipant(participant);

			// else call the studySubjectDao
			studySubjectResults = studySubjectDao
					.advancedStudySearch(studySubject);
			Iterator iter = studySubjectResults.iterator();
			while (iter.hasNext()) {
				studyList.add(((StudySubject) iter.next()).getStudySite()
						.getStudy());
			}
		}

		Context context = new HttpServletRequestContext(request, parameterMap);

		TableModel model = new TableModelImpl(context);
		try {
			return build(model, studyList).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

}
