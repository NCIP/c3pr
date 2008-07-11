package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class RegistrationOverviewTab<C extends StudySubject> extends RegistrationTab<C> {

    private StudySubjectService studySubjectService;

    private Configuration configuration;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public RegistrationOverviewTab() {
        super("Overview", "Overview", "registration/reg_overview");
    }

    @Override
    public Map<String, Object> referenceData(C command) {
        StudySubject studySubject = (StudySubject) command;
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
            armAssignedLabel = "Arm Assigned";
        }
        if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.UNAPPROVED
                        && studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.COMPLETE
                        && studySubject.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE) {
            actionRequired = true;
            if (studySubject.getScheduledEpochs().size() > 1) {
                actionLabel = "Transfer Subject";
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
        map.put("actionRequired", actionRequired);
        map.put("actionLabel", actionLabel);
        map.put("newRegistration", newRegistration);
        map.put("armAssigned", armAssigned);
        map.put("armAssignedLabel", armAssignedLabel);
        map.put("requiresMultiSite", studySubjectService
                        .requiresExternalApprovalForRegistration(studySubject));
        map.put("multisiteEnable", new Boolean(this.configuration.get(Configuration.MULTISITE_ENABLE)));
        addAppUrls(map);
        return map;
    }

    public ModelAndView getMessageBroadcastStatus(HttpServletRequest request, Object commandObj,
                    Errors error) {
        C command = (C) commandObj;
        String responseMessage = null;
        try {
            responseMessage = studySubjectService.getCCTSWofkflowStatus(command).getDisplayName();
        }
        catch (Exception e) {
            responseMessage = "error";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("responseMessage", responseMessage);
        return new ModelAndView(getAjaxViewName(request), map);
    }

    public ModelAndView broadcastRegistration(HttpServletRequest request, Object commandObj,
                    Errors error) {
        C command = (C) commandObj;
        try {
            this.studySubjectService.broadcastMessage(command);
            return getMessageBroadcastStatus(request, commandObj, error);
        }
        catch (C3PRCodedException e) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("responseMessage", e.getMessage());
            return new ModelAndView(getAjaxViewName(request), map);
        }
    }
    
    public ModelAndView broadcastMultiSiteRegistration(HttpServletRequest request, Object commandObj,
                    Errors error) {
        C command = (C) commandObj;
        String responseMessage = null;
        try {
            if(command.getMultisiteWorkflowStatus()==CCTSWorkflowStatusType.MESSAGE_SEND_FAILED)
                this.studySubjectService.sendRegistrationRequest(command);
            else
                this.studySubjectService.sendRegistrationResponse(command);
            responseMessage = studySubjectService.getMultiSiteWofkflowStatus(command).getDisplayName();
        }
        catch (Exception e) {
            responseMessage=e.getMessage();
        }finally{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("responseMessage", responseMessage);
            return new ModelAndView(getAjaxViewName(request), map);
        }
    }

    public StudySubjectService getStudySubjectService() {
        return studySubjectService;
    }

    public void setStudySubjectService(StudySubjectService studySubjectService) {
        this.studySubjectService = studySubjectService;
    }

    private void addAppUrls(Map<String, Object> map) {
        if (this.configuration.get(this.configuration.AUTHENTICATION_MODEL).equals("webSSO")) {
            map.put("hotlinkEnable", new Boolean(true));
            if (!StringUtils
                            .getBlankIfNull(this.configuration.get(this.configuration.PSC_BASE_URL))
                            .equalsIgnoreCase("")) {
                map.put("pscBaseUrl", this.configuration.get(this.configuration.PSC_BASE_URL));
            }
            if (!StringUtils.getBlankIfNull(
                            this.configuration.get(this.configuration.CAAERS_BASE_URL))
                            .equalsIgnoreCase("")) {
                map
                                .put("caaersBaseUrl", this.configuration
                                                .get(this.configuration.CAAERS_BASE_URL));
            }
            if (!StringUtils
                            .getBlankIfNull(this.configuration.get(this.configuration.C3D_BASE_URL))
                            .equalsIgnoreCase("")) {
                map.put("c3dBaseUrl", this.configuration.get(this.configuration.C3D_BASE_URL));
            }
        }
        else {
            map.put("hotlinkEnable", new Boolean(false));
        }
    }
}
