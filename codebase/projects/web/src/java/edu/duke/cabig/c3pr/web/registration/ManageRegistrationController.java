package edu.duke.cabig.c3pr.web.registration;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.web.registration.tabs.ManageCompanionRegistrationTab;
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
        setFlow(flow);
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
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

        return super.handleRequestInternal(request, response); 
    }
    
    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    	// this condition is added to transfer epoch, if epoch doesn't require additional input from the user.
    	if(WebUtils.hasSubmitParameter(request, "epoch")){
    		StudySubjectWrapper wrapper= (StudySubjectWrapper)command ;
        	StudySubject studySubject = wrapper.getStudySubject();
         	ScheduledEpoch scheduledEpoch;
         	
    		Integer id = Integer.parseInt(request.getParameter("epoch"));
	        Epoch epoch = epochDao.getById(id);
	        epochDao.initialize(epoch);
	        if (epoch.getTreatmentIndicator()) {
	            (epoch).getArms().size();
	            scheduledEpoch = new ScheduledEpoch();
	        }
	        else {
	            scheduledEpoch = new ScheduledEpoch();
	        }
	        scheduledEpoch.setEpoch(epoch);
	        scheduledEpoch.setEligibilityIndicator(registrationControllerUtils.evaluateEligibilityIndicator(studySubject));
	        scheduledEpoch.setScEpochDataEntryStatus(scheduledEpoch.evaluateScheduledEpochDataEntryStatus(scheduledEpoch.getStratumGroupNumber()));
	        studySubject.addScheduledEpoch(scheduledEpoch);
	        registrationControllerUtils.buildCommandObject(studySubject);
	        studySubjectDao.initialize(studySubject);
	        if(wrapper.getShouldTransfer())
	        	studySubject = studySubjectRepository.transferSubject(studySubject);
	        else if(wrapper.getShouldEnroll()){
	        	studySubject=studySubjectRepository.enroll(studySubject);
	        }else if(wrapper.getShouldRegister()){
	        	studySubject=studySubjectRepository.register(studySubject.getIdentifiers());
	        }else if(wrapper.getShouldReserve()){
	        	studySubject=studySubjectRepository.reserve(studySubject.getIdentifiers());
	        }else{
	        	studySubject=studySubjectRepository.save(studySubject);
	        }
	        return new ModelAndView("redirect:confirm?" + ControllerTools.createParameterString(studySubject.getSystemAssignedIdentifiers().get(0)));
    	}
    	return null;
    }

    @Override
	protected C save(C command, Errors arg1) {
		StudySubject merged = (StudySubject) getDao().merge(getPrimaryDomainObject(command));
		studySubjectDao.initialize(merged);
		command.setStudySubject(merged);
		return command;
	}

    @Override
    protected boolean isNextPageSavable(HttpServletRequest request, C command, Tab<C> tab) {
    	return false;
    }
    
//    @Override
//    protected void postProcessPage(HttpServletRequest request, Object command,Errors errors, int page) throws Exception {
//    	super.postProcessPage(request, command, errors, page);
//    	StudySubjectWrapper wrapper = (StudySubjectWrapper)command ;
//    	Identifier identifier=ControllerTools.getIdentifierInRequest(request);
//    	if(identifier != null){
//    		List<Identifier> identifiers=new ArrayList<Identifier>();
//    		identifiers.add(identifier);
//    		StudySubject studySubject=studySubjectRepository.getUniqueStudySubjects(identifiers);
//    		studySubjectDao.initialize(studySubject);
//    		wrapper.setStudySubject(studySubject);
//    	}
//    }
}