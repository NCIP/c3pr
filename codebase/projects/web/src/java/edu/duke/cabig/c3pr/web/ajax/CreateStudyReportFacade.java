package edu.duke.cabig.c3pr.web.ajax;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.extremecomponents.table.bean.Export;
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

public class CreateStudyReportFacade extends BaseStudyAjaxFacade {

    private StudySubjectDao studySubjectDao;

    private StudyDao studyDao;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public Object build(TableModel model, Collection studies) throws Exception {

        Export export = model.getExportInstance();
        export.setView(TableConstants.VIEW_XLS);
        export.setViewResolver(TableConstants.VIEW_XLS);
        export.setImageName(TableConstants.VIEW_XLS);
        export.setText(TableConstants.VIEW_XLS);
        export.setFileName("study_report.xls");
        model.addExport(export);

        return super.build(model, studies, "Search Results", "/pages/report/createStudyReport");
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

        // this if -else ensures that participant is null if no data relevant to participant is
        // entered and the studyDao is called.
        if (StringUtils.isEmpty(params[2].toString()) && StringUtils.isEmpty(params[3].toString())
                        && StringUtils.isEmpty(params[4].toString())) {
            participant = null;
            // call the studyDao if participant is null.
            studyList = studyDao.searchByExample(study, true, 0);
        }
        else {
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
            studySubjectResults = studySubjectDao.advancedStudySearch(studySubject);
            Iterator iter = studySubjectResults.iterator();
            while (iter.hasNext()) {
                studyList.add(((StudySubject) iter.next()).getStudySite().getStudy());
            }
        }

        Context context = new HttpServletRequestContext(request, parameterMap);

        TableModel model = new TableModelImpl(context);
        try {
            return build(model, studyList).toString();
        }
        catch (Exception e) {
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
