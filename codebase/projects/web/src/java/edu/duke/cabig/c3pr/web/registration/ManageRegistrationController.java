package edu.duke.cabig.c3pr.web.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.registration.tabs.ManageCompanionRegistrationTab;
import edu.duke.cabig.c3pr.web.registration.tabs.ManageEpochTab;
import edu.duke.cabig.c3pr.web.registration.tabs.RegistrationOverviewTab;
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
        return null;
    }

//    public StudySubject createNewScheduledEpochSubject(HttpServletRequest request,
//                    StudySubject command, Errors error) {
//        Map map = new HashMap();
//        Integer id = Integer.parseInt(request.getParameter("epoch"));
//        Epoch epoch = epochDao.getById(id);
//        ScheduledEpoch scheduledEpoch = new ScheduledEpoch();
//        scheduledEpoch.setEpoch(epoch);
//        scheduledEpoch.setEligibilityIndicator(registrationControllerUtils.evaluateEligibilityIndicator(command));
//        command.addScheduledEpoch(scheduledEpoch);
//        return command;
//    }

    @Override
	protected C save(C command, Errors arg1) {
		StudySubject merged = (StudySubject) getDao().merge(
				getPrimaryDomainObject(command));
		command.setStudySubject(merged);
		return command;
	}

    @Override
    protected boolean isNextPageSavable(HttpServletRequest request, C command, Tab<C> tab) {
    	return false;
    }
}