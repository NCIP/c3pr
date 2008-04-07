package edu.duke.cabig.c3pr.web.registration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.registration.tabs.ManageEpochTab;
import edu.duke.cabig.c3pr.web.registration.tabs.RegistrationOverviewTab;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

public class ManageRegistrationController<C extends StudySubject> extends RegistrationController<C> {

    private XmlMarshaller xmlUtility;

    public XmlMarshaller getXmlUtility() {
        return xmlUtility;
    }

    public void setXmlUtility(XmlMarshaller xmlUtility) {
        this.xmlUtility = xmlUtility;
    }

    public ManageRegistrationController() {
        super("Manage Registration");
    }

    @Override
    protected void intializeFlows(Flow flow) {
        flow.addTab(new RegistrationOverviewTab<StudySubject>());
        flow.addTab(new ManageEpochTab<StudySubject>());
        setFlow(flow);
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest,
                    HttpServletResponse httpServletResponse) throws Exception {
        // study export
        if (httpServletRequest.getParameterMap().keySet().contains("_action")
                        && StringUtils.getBlankIfNull(httpServletRequest.getParameter("_action"))
                                        .equalsIgnoreCase("export")) {
            StudySubject studySubject = (StudySubject) currentFormObject(httpServletRequest,
                            httpServletRequest.getSession().getAttribute(
                                            getFormSessionAttributeName()));
            httpServletResponse.setContentType("application/xml");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=study-"
                            + studySubject.getId() + ".xml");
            xmlUtility.toXML(studySubject, httpServletResponse.getWriter());
            httpServletResponse.getWriter().close();
            return null;
        }

        return super.handleRequestInternal(httpServletRequest, httpServletResponse); // To change
                                                                                        // body of
                                                                                        // overridden
                                                                                        // methods
                                                                                        // use File
                                                                                        // |
                                                                                        // Settings
                                                                                        // | File
                                                                                        // Templates.
    }

    @Override
    protected boolean isFormSubmission(HttpServletRequest arg0) {
        return super.isFormSubmission(arg0);
    }

    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                    Object command, BindException errors) throws Exception {
        StudySubject studySubject = createNewScheduledEpochSubject(request, command, errors);
        studySubject = studySubjectService.register(studySubject);
        if (logger.isDebugEnabled()) {
            logger
                            .debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - registration service call over"); //$NON-NLS-1$
        }
        return new ModelAndView("redirect:confirm?registrationId=" + studySubject.getId());
    }

    public StudySubject createNewScheduledEpochSubject(HttpServletRequest request,
                    Object commandObj, Errors error) {
        StudySubject command = (StudySubject) commandObj;
        Map map = new HashMap();
        Integer id = Integer.parseInt(request.getParameter("epoch"));
        Epoch epoch = epochDao.getById(id);
        ScheduledNonTreatmentEpoch scheduledEpoch = new ScheduledNonTreatmentEpoch();
        scheduledEpoch.setEpoch(epoch);
        command.addScheduledEpoch(scheduledEpoch);
        return command;
    }

    @Override
    protected C save(C command, Errors arg1) {
        getDao().save(getPrimaryDomainObject(command));
        return command;
    }

}