package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.navigation.Task;
import edu.duke.cabig.c3pr.web.study.tabs.StudyIdentifiersTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyInvestigatorsTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyNotificationTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyOverviewTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyPersonnelTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyRegistrationsTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudySitesTab;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 19, 2007 Time: 1:10:17 PM To change this template
 * use File | Settings | File Templates.
 */
public class ViewStudyController extends StudyController<StudyWrapper> {
    private edu.duke.cabig.c3pr.utils.web.navigation.Task editTask;

    private XmlMarshaller xmlUtility;
    private final String DO_NOT_SAVE = "_doNotSave" ; 

    public ViewStudyController() {
        super("View Study Details");
        setBindOnNewForm(true);
    }

    /**
     * Create a nested object graph that Create Study Design needs
     *
     * @param request -
     *                HttpServletRequest
     * @throws javax.servlet.ServletException
     */
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        StudyWrapper wrapper = new StudyWrapper();
        Study study = studyDao.getById(Integer.parseInt(request.getParameter("studyId")));
        studyDao.initialize(study);
        if (study != null) {
            log.debug("Retrieving Study Details for Id: " + study.getId());
        }
        wrapper.setStudy(study);
        return wrapper;
    }

    @Override
    protected void layoutTabs(Flow flow) {
        flow.addTab(new StudyOverviewTab("Summary", "Summary", "study/study_summary_view"));
        flow.addTab(new StudySitesTab());
        flow.addTab(new StudyIdentifiersTab());
        flow.addTab(new StudyInvestigatorsTab());
        flow.addTab(new StudyPersonnelTab());
        flow.addTab(new StudyNotificationTab());
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
    protected Map referenceData(HttpServletRequest request, Object o, Errors errors,
                                int i) throws Exception {
        Map<String, Object> refdata = super.referenceData(request, o, errors, i);
//        String softDelete = "false";
//        String isAdmin = "false";
        
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        GrantedAuthority[] groups = auth.getAuthorities();
        boolean isRegistrarOnly = true;
        for (GrantedAuthority ga : groups) {
            if (ga.getAuthority().endsWith("admin") || ga.getAuthority().endsWith("ordinator")) {
                isRegistrarOnly = false;
            }
//            if (ga.getAuthority().endsWith("admin")) {
//                isAdmin = "true";
//            }
        }
       
//        if (((StudyWrapper) o).getStudy().getCoordinatingCenterStudyStatus() != CoordinatingCenterStudyStatus.PENDING) {
//            softDelete = "true";
//        }
//        request.setAttribute("softDelete", softDelete);
//        request.setAttribute("isAdmin", isAdmin);
        refdata.put("isRegistrar", isRegistrarOnly);
        refdata.put("editAuthorizationTask", editTask);
        return refdata;
    }

    /**
     * Change access modifier for testing *
     */
    @Override
    protected boolean isFormSubmission(HttpServletRequest httpServletRequest) {
        return super.isFormSubmission(httpServletRequest); // To change body of overridden methods
        // use File | Settings | File Templates.
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        // study export
        if (request.getParameterMap().keySet().contains("_action")
                && StringUtils.getBlankIfNull(request.getParameter("_action"))
                .equalsIgnoreCase("export")) {
            response.reset();

            StudyWrapper wrapper = (StudyWrapper) currentFormObject(request, request.getSession().getAttribute(getFormSessionAttributeName()));
            Study study = wrapper.getStudy();
            response.setContentType("application/xml");
            String fileName = "study-" + study.getId() + ".xml";
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            xmlUtility.toXML(study, response.getWriter());
            response.getWriter().close();

            return null;
        }

        return super.handleRequestInternal(request, response);
    }

    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException e)
            throws Exception {
        Study study = ((StudyWrapper) command).getStudy();
        ModelAndView modelAndView = new ModelAndView(new RedirectView("editStudy?studyId="
                + study.getId()));
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

    @Override
    protected boolean shouldPersist(HttpServletRequest request, StudyWrapper command,
                    Tab<StudyWrapper> tab) {
        if(WebUtils.hasSubmitParameter(request, DO_NOT_SAVE) && StringUtils.equals(request.getParameter(DO_NOT_SAVE), "true")){
            return false;
        }
        return true;
    }
}
