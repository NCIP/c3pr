package edu.duke.cabig.c3pr.web.registration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.registration.tabs.ManageCompanionRegistrationTab;
import edu.duke.cabig.c3pr.web.registration.tabs.ManageEpochTab;
import edu.duke.cabig.c3pr.web.registration.tabs.RegistrationOverviewTab;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

public class ManageRegistrationController<C extends StudySubjectWrapper> extends RegistrationController<C> {

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
    
    private RegistrationControllerUtils registrationControllerUtils;
    public void setRegistrationControllerUtils(RegistrationControllerUtils registrationControllerUtils) {
        this.registrationControllerUtils = registrationControllerUtils;
    }

    @Override
    protected void intializeFlows(Flow flow) {
        flow.addTab(new RegistrationOverviewTab<StudySubjectWrapper>());
        flow.addTab(new ManageEpochTab<StudySubjectWrapper>());
        flow.addTab(new ManageCompanionRegistrationTab<StudySubjectWrapper>());
        setFlow(flow);
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
        // study export
        if (request.getParameterMap().keySet().contains("_action")
                        && StringUtils.getBlankIfNull(request.getParameter("_action"))
                                        .equalsIgnoreCase("export")) {
        	response.reset();
        	
        	StudySubjectWrapper wrapper= (StudySubjectWrapper) currentFormObject(request,request.getSession().getAttribute(getFormSessionAttributeName()));
            StudySubject studySubject = wrapper.getStudySubject();
        	response.setContentType("application/xml");
            String fileName = "registration-"+ studySubject.getId() + ".xml" ; 
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
            xmlUtility.toXML(studySubject, response.getWriter());
            response.getWriter().close();
            return null;
        }

        return super.handleRequestInternal(request, response); // To change
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
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                    Object command, BindException errors) throws Exception {
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command ;
        StudySubject studySubject = createNewScheduledEpochSubject(request, wrapper.getStudySubject(), errors);
        studySubject = studySubjectService.register(studySubject);
        if (logger.isDebugEnabled()) {
            logger
                            .debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - registration service call over"); //$NON-NLS-1$
        }
        return new ModelAndView("redirect:confirm?registrationId=" + studySubject.getId());
    }

    public StudySubject createNewScheduledEpochSubject(HttpServletRequest request,
                    StudySubject command, Errors error) {
        Map map = new HashMap();
        Integer id = Integer.parseInt(request.getParameter("epoch"));
        Epoch epoch = epochDao.getById(id);
        ScheduledEpoch scheduledEpoch = new ScheduledEpoch();
        scheduledEpoch.setEpoch(epoch);
        scheduledEpoch.setEligibilityIndicator(registrationControllerUtils.evaluateEligibilityIndicator(command));
        command.addScheduledEpoch(scheduledEpoch);
        return command;
    }

    @Override
	protected C save(C command, Errors arg1) {
		StudySubject merged = (StudySubject) getDao().merge(
				getPrimaryDomainObject(command));
		command.setStudySubject(merged);
		return command;
	}

    @Override
    protected boolean shouldPersist(HttpServletRequest request, C command, Tab<C> tab) {
        return WebUtils.hasSubmitParameter(request, "dontSave")?true:super.shouldPersist(request, command, tab);
    }
    
    @Override
    protected boolean isNextPageSavable(HttpServletRequest request, C command, Tab<C> tab) {
    	return shouldPersist(request, command, tab);
    }
}