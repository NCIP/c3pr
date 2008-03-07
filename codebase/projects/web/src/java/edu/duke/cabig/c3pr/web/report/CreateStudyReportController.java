package edu.duke.cabig.c3pr.web.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.ajax.CreateStudyReportFacade;

public class CreateStudyReportController extends SimpleFormController {

    private StudySubjectDao studySubjectDao;

    private StudyDao studyDao;

    private StudyReportCommand studyReportCommand;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
                    throws Exception {
        super.initBinder(request, binder);

        if (request.getMethod().equals(METHOD_GET)) {
            CreateStudyReportFacade studyFacade = new CreateStudyReportFacade();
            Context context = null;
            context = new HttpServletRequestContext(request);

            TableModel model = new TableModelImpl(context);
            Object viewData = null;
            try {
                viewData = studyFacade.build(model, null);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("assembler", viewData);
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
                    Object oCommand, BindException errors) throws Exception {
        List<StudySubject> studySubjectResults;
        Participant participant;
        SystemAssignedIdentifier id;
        CreateStudyReportFacade studyReportFacade = new CreateStudyReportFacade();
        Context context = new HttpServletRequestContext(request);
        TableModel model = new TableModelImpl(context);
        String[] params = studyReportCommand.getParams();

        Map map = errors.getModel();
        ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
        if (params == null) {
            return modelAndView;
        }

        Study study = new Study(true);
        if (!StringUtils.isEmpty(params[0].toString())) {
            study.setShortTitleText(params[0].toString());
        }

        if (!StringUtils.isEmpty(params[1].toString())) {
            id = new SystemAssignedIdentifier();
            id.setValue(params[1].toString());
            study.addIdentifier(id);
        }

        // this if -else ensures that participant is null if no data relevant to participant is
        // entered and the studyDao is called.
        if (StringUtils.isEmpty(params[2].toString()) && StringUtils.isEmpty(params[3].toString())
                        && StringUtils.isEmpty(params[4].toString())) {
            participant = null;
            List<Study> studyResults;
            // call the studyDao if participant is null.
            studyResults = studyDao.searchByExample(study);
            // create a list of studysub from list of studies
            Iterator iter = studyResults.iterator();
            studySubjectResults = new ArrayList<StudySubject>();
            StudySubject studySub;
            StudySite studySite;
            while (iter.hasNext()) {
                studySub = new StudySubject();
                studySite = new StudySite();
                studySite.setStudy((Study) (iter.next()));
                studySub.setStudySite(studySite);
                studySubjectResults.add(studySub);
            }
        }
        else {
            participant = new Participant();

            id = new SystemAssignedIdentifier();
            id.setValue(params[2].toString());
            participant.addIdentifier(id);
            participant.setFirstName(params[3].toString());
            participant.setLastName(params[4].toString());

            StudySite studySite = new StudySite();
            studySite.setStudy(study);

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setParticipant(participant);

            // else call the studySubjectDao
            studySubjectResults = studySubjectDao.advancedStudySearch(studySubject);
        }

        Object viewData = null;
        List<Study> studyList = new ArrayList<Study>();
        Iterator iter = studySubjectResults.iterator();
        StudySubject ss;
        while (iter.hasNext()) {
            ss = (StudySubject) iter.next();
            studyList.add(ss.getStudySite().getStudy());
        }
        try {
            viewData = studyReportFacade.build(model, studyList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("assembler", viewData);

        return modelAndView;
    }

    public StudySubjectDao getStudySubjectDao() {
        return studySubjectDao;
    }

    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

    public StudyReportCommand getStudyReportCommand() {
        return studyReportCommand;
    }

    public void setStudyReportCommand(StudyReportCommand studyReportCommand) {
        this.studyReportCommand = studyReportCommand;
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

}
