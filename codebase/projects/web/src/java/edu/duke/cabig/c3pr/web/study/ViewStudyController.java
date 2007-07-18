package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.web.navigation.Task;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 19, 2007
 * Time: 1:10:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewStudyController extends StudyController<Study> {
    private edu.duke.cabig.c3pr.utils.web.navigation.Task editTask;
    private XmlMarshaller xmlUtility;


    public ViewStudyController() {
        super("View Study Details");
        setBindOnNewForm(true);
    }

    /**
     * Create a nested object graph that Create Study Design needs
     *
     * @param request - HttpServletRequest
     * @throws javax.servlet.ServletException
     */
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        Study study = studyDao.getStudyDesignById(Integer.parseInt(request.getParameter("studyId")));
        if (study != null) {
            log.debug("Retrieving Study Details for Id: " + study.getId());
        }
        return study;
    }

    @Override
    protected boolean shouldSave(HttpServletRequest request, Study command, Tab<Study> tab) {
        return false;

    }

    @Override
    protected void layoutTabs(Flow flow) {
        flow.addTab(new StudyEmptyTab("Summary", "Summary", "study/study_summary_view"));
        flow.addTab(new StudyRegistrationsTab());

    }

    @Override
    protected boolean isSummaryEnabled() {
        return false;
    }

    @Override
    /**
     * Reference data to enable Edit button in view
     */
    protected Map referenceData(HttpServletRequest httpServletRequest, Object o, Errors errors, int i) throws Exception {
        Map<String, Object> refdata = super.referenceData(httpServletRequest, o, errors, i);    //To change body of overridden methods use File | Settings | File Templates.
        refdata.put("editAuthorizationTask", editTask);
        return refdata;
    }


    protected ModelAndView processFinish(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object command, BindException e) throws Exception {
        Study study = (Study) command;

        // study export
        if (httpServletRequest.getParameter("_action").equals("export")) {
            log.debug("Exporting Study " + study.getId());
            httpServletResponse.setContentType("application/xml");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=study-" + study.getId() + ".xml");
            xmlUtility.toXML(study, httpServletResponse.getWriter());
            httpServletResponse.getWriter().close();

            return null;
        }


        ModelAndView modelAndView = new ModelAndView(new RedirectView("editStudy?studyId=" + study.getId()));
        return modelAndView;
    }

    public Task getEditTask() {
        return editTask;
    }

    public void setEditTask(Task editTask) {
        this.editTask = editTask;
    }

    public XmlMarshaller getXmlUtility() {
        return xmlUtility;
    }

    public void setXmlUtility(XmlMarshaller xmlUtility) {
        this.xmlUtility = xmlUtility;
    }
}
