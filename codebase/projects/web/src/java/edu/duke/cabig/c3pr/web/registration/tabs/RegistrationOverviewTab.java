package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.registration.RegistrationControllerUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class RegistrationOverviewTab<C extends StudySubjectWrapper> extends RegistrationTab<C> {

    private StudySubjectService studySubjectService;

    private RegistrationControllerUtils registrationControllerUtils;
   
    public void setRegistrationControllerUtils(RegistrationControllerUtils registrationControllerUtils) {
        this.registrationControllerUtils = registrationControllerUtils;
    }

    public RegistrationOverviewTab() {
        super("Overview", "Overview", "registration/reg_overview");
    }

    @Override
    public Map<String, Object> referenceData(C command) {
        StudySubjectWrapper wrapper = (StudySubjectWrapper) command ;
    	StudySubject studySubject = wrapper.getStudySubject();
        Map<String, Object> map = new HashMap<String, Object>();
        boolean actionRequired = false;
        boolean newRegistration = false;
        String actionLabel = "";
        String armAssigned = "";
        String armAssignedLabel = "";
        if (studySubject.getScheduledEpoch()!=null
                        && (studySubject.getScheduledEpoch())
                                        .getScheduledArm() != null
                        && (studySubject.getScheduledEpoch())
                                        .getScheduledArm().getArm() != null) {
            armAssigned = (studySubject.getScheduledEpoch())
                            .getScheduledArm().getArm().getName();
            armAssignedLabel = "Arm";
        }
        if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING
                        && studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.COMPLETE
                        && studySubject.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE) {
            actionRequired = true;
            if (studySubject.getScheduledEpochs().size() > 1) {
                actionLabel = "Transfer subject";
            }
            else if (studySubject.getScheduledEpoch().getEpoch().isEnrolling()) {
                actionLabel = "Register";
            }
            else {
                actionLabel = "Save";
            }
            if (studySubject.getScheduledEpoch().getRequiresRandomization()) {
                actionLabel += " & Randomize";
            }
        }
        if (studySubject.getScheduledEpochs().size() == 1) {
            newRegistration = true;
        }
        map.put("hasCompanions", studySubject.getStudySite().getStudy().getCompanionStudyAssociations().size()>0);
        map.put("actionRequired", actionRequired);
        map.put("actionLabel", actionLabel);
        map.put("newRegistration", newRegistration);
        map.put("armAssigned", armAssigned);
        map.put("armAssignedLabel", armAssignedLabel);
        map.put("registerableWithCompanions", registrationControllerUtils.registerableAsorWithCompanion(studySubject));
        map.put("requiresMultiSite", studySubjectService
                        .requiresExternalApprovalForRegistration(studySubject));
        map.put("hasEndpointMessages", new Boolean(studySubject.getStudySite().getStudy().getStudyCoordinatingCenter().getRegistrationEndpoints().size()>0));
        registrationControllerUtils.addAppUrls(map);
        return map;
    }

    public ModelAndView getMessageBroadcastStatus(HttpServletRequest request, Object commandObj,
                    Errors error) {
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) commandObj ;
    	StudySubject command = wrapper.getStudySubject();
        String responseMessage = null;
        try {
            responseMessage = studySubjectService.getCCTSWofkflowStatus(command).getDisplayName();
        }
        catch (Exception e) {
            responseMessage = "error";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("responseMessage", responseMessage);
        return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
    }

    public ModelAndView broadcastRegistration(HttpServletRequest request, Object commandObj,
                    Errors error) {
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) commandObj ;
    	StudySubject command = wrapper.getStudySubject();
        try {
            this.studySubjectService.broadcastMessage(command);
            return getMessageBroadcastStatus(request, commandObj, error);
        }
        catch (C3PRCodedException e) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("responseMessage", e.getMessage());
            return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
        }
    }
    
    public ModelAndView showEndpointMessage(HttpServletRequest request, Object obj, Errors errors) {
        StudySubjectWrapper wrapper=((StudySubjectWrapper)obj);
        StudySubject studySubject=wrapper.getStudySubject();
        StudyOrganization studyOrganization=studySubject.getStudySite().getStudy().getStudyCoordinatingCenter();
        Map map=new HashMap();
        map.put("site", studyOrganization);
        return new ModelAndView(AjaxableUtils.getAjaxViewName(request),map);
    }

    public StudySubjectService getStudySubjectService() {
        return studySubjectService;
    }

    public void setStudySubjectService(StudySubjectService studySubjectService) {
        this.studySubjectService = studySubjectService;
    }
    
    public ModelAndView refreshEnrollmentSection(HttpServletRequest request, Object obj, Errors errors) {
		StudySubjectWrapper wrapper = (StudySubjectWrapper) obj;
		String regId = request.getParameter("registrationId");
		StudySubject studySubject = studySubjectDao.getById(Integer.parseInt(regId)); 
		wrapper.setStudySubject(studySubject);
		HashMap map = new HashMap();
		map.put("command",wrapper);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}

}
