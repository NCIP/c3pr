package edu.duke.cabig.c3pr.web.ajax;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.bean.Table;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * @author Vinay Gangoli
 */
public class CreateReportFacade {

    private static Log log = LogFactory.getLog(CreateReportFacade.class);

    private StudySubjectDao studySubjectDao;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public Object build(TableModel model, Collection studySubjects) throws Exception {

        Table table = model.getTableInstance();
        table.setAutoIncludeParameters(false);
        table.setTableId("assembler");
        table.setItems(studySubjects);
        table.setAction(model.getContext().getContextPath() + "/pages/report/createReport");
        table.setTitle("Registrations");
        table.setShowPagination(false);
        table.setOnInvokeAction("buildTable('assembler')");
        table.setImagePath(model.getContext().getContextPath() + "/images/table/*.gif");
        table.setShowExports(true);
        table.setSortable(false);
        model.addTable(table);

        Export export = model.getExportInstance();
        export.setView(TableConstants.VIEW_XLS);
        export.setViewResolver(TableConstants.VIEW_XLS);
        export.setImageName(TableConstants.VIEW_XLS);
        export.setText(TableConstants.VIEW_XLS);
        export.setFileName("registration_report.xls");
        model.addExport(export);

        Row row = model.getRowInstance();
        row.setHighlightRow(Boolean.TRUE);
        model.addRow(row);

        Column columnShortTitle = model.getColumnInstance();
        columnShortTitle.setTitle("Study Short Title");
        columnShortTitle.setProperty("studySite.study.trimmedShortTitleText");
        columnShortTitle.setCell((ViewRegistrationLinkCustomCell.class).getName());
        model.addColumn(columnShortTitle);

        Column columnStudyPrimaryIdentifier = model.getColumnInstance();
        columnStudyPrimaryIdentifier.setTitle("Study Identifier");
        columnStudyPrimaryIdentifier.setProperty("studySite.study.primaryIdentifier");
        model.addColumn(columnStudyPrimaryIdentifier);

        Column columnLastName = model.getColumnInstance();
        columnLastName.setTitle("Last Name");
        columnLastName.setProperty("participant.lastName");
        model.addColumn(columnLastName);

        Column columnParticipantPrimaryIdentifier = model.getColumnInstance();
        columnParticipantPrimaryIdentifier.setTitle("Subject Primary Identifier");
        columnParticipantPrimaryIdentifier.setProperty("participant.primaryIdentifier");
        model.addColumn(columnParticipantPrimaryIdentifier);

        Column columnSiteName = model.getColumnInstance();
        columnSiteName.setTitle("Site");
        columnSiteName.setProperty("studySite.healthcareSite.name");
        model.addColumn(columnSiteName);

        Column columnRegistrationStatus = model.getColumnInstance();
        columnRegistrationStatus.setTitle("Registration Status");
        columnRegistrationStatus.setProperty("regWorkflowStatus.displayName");
        model.addColumn(columnRegistrationStatus);

        Column columnInformedConsentSignedDateStr = model.getColumnInstance();
        columnInformedConsentSignedDateStr.setTitle("Registration Date");
        columnInformedConsentSignedDateStr.setProperty("informedConsentSignedDate");
        model.addColumn(columnInformedConsentSignedDateStr);

        Column columnInvestigatorFullName = model.getColumnInstance();
        columnInvestigatorFullName.setTitle("Treating Physician");
        columnInvestigatorFullName.setProperty("treatingPhysicianFullName");
        model.addColumn(columnInvestigatorFullName);

        return model.assemble();
    }

    public String getTable(Map parameterMap, String[] params, HttpServletRequest request) {

        Study study = new Study();
        String studyShortTitle = "";
        String studyCoordinatingSiteId = "";
        String siteName = "";
        String siteNciId = "";
        String rStartDate = "";
        String rEndDate = "";
        String birthDate = "";
        String raceCode = "";

        if (params[0] != null && params[0].length() > 0) {
            studyShortTitle = params[0].toString();
        }

        if (params[1] != null && params[1].length() > 0) {
            studyCoordinatingSiteId = params[1].toString();
        }

        if (params[2] != null && params[2].length() > 0) {
            siteName = params[2].toString();
        }

        if (params[3] != null && params[3].length() > 0) {
            siteNciId = params[3].toString();
        }

        if (params[4] != null && params[4].length() > 0) {
            rStartDate = params[4].toString();
        }

        if (params[5] != null && params[5].length() > 0) {
            rEndDate = params[5].toString();
        }

        if (params[6] != null && params[6].length() > 0) {
            birthDate = params[6].toString();
        }

        if (params[7] != null && params[7].length() > 0) {
            raceCode = params[7].toString();
        }

        if (studyShortTitle != null && studyShortTitle != "") {
            study.setShortTitleText(studyShortTitle);
        }

        HealthcareSite hcs = new HealthcareSite();
        if (siteName != null && siteName != "") {
            hcs.setName(siteName);
        }

        if (siteNciId != null && siteNciId != "") {
            hcs.setNciInstituteCode(siteNciId);
        }

        StudySite studySite = new StudySite();
        studySite.setStudy(study);
        studySite.setHealthcareSite(hcs);

        Participant participant = new Participant();
        participant.setRaceCode(raceCode);

        Date regStartDate = null;
        Date regEndDate = null;
        try {
            if (birthDate != null && !birthDate.equals("")) {
                participant.setBirthDate(simpleDateFormat.parse(birthDate));
            }
            if (rStartDate != null && !rStartDate.equals("")) {
                regStartDate = simpleDateFormat.parse(rStartDate);
            }
            if (rEndDate != null && !rEndDate.equals("")) {
                regEndDate = simpleDateFormat.parse(rEndDate);
            }
        }
        catch (ParseException pr) {
            log.error("DateFormat Exception in CreateReportFacade");
        }

        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySite);
        studySubject.setParticipant(participant);

        List<StudySubject> registrationResults = studySubjectDao.advancedSearch(studySubject,
                        regStartDate, regEndDate, studyCoordinatingSiteId);

        Context context = null;
        if (parameterMap == null) {
            context = new HttpServletRequestContext(request);
        }
        else {
            context = new HttpServletRequestContext(request, parameterMap);
        }

        TableModel model = new TableModelImpl(context);
        try {
            return build(model, registrationResults).toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public StudySubjectDao getRegistrationDao() {
        return studySubjectDao;
    }

    public void setRegistrationDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

}