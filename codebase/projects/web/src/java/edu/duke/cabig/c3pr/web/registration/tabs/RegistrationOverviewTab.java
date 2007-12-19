package edu.duke.cabig.c3pr.web.registration.tabs;

import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class RegistrationOverviewTab<C extends StudySubject> extends RegistrationTab<C> {

    private StudySubjectService studySubjectService;


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
        if (studySubject.getIfTreatmentScheduledEpoch() && ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch()).getScheduledArm() != null
                && ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch()).getScheduledArm().getArm() != null) {
            armAssigned = ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch()).getScheduledArm().getArm().getName();
            armAssignedLabel = "Arm Assigned";
        }
        if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.UNAPPROVED
                && studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.COMPLETE
                && studySubject.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE) {
            actionRequired = true;
            if (studySubject.getScheduledEpochs().size() > 1) {
                actionLabel = "Transfer Subject";
            } else if (studySubject.getScheduledEpoch().getEpoch().isEnrolling()) {
                actionLabel = "Register";
            } else {
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
        map.put("requiresMultiSite", studySubjectService.requiresCoordinatingCenterApproval(studySubject));
        return map;
    }

    public ModelAndView getMessageBroadcastStatus(HttpServletRequest request, Object commandObj, Errors error) {
        C command = (C) commandObj;
        String responseMessage = null;
        try {
            responseMessage = studySubjectService.getCCTSWofkflowStatus(command).getDisplayName();
        } catch (Exception e) {
            responseMessage = "Error getting status";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("responseMessage", responseMessage);
        return new ModelAndView(getAjaxViewName(request), map);
    }

    public ModelAndView broadcastRegistration(HttpServletRequest request, Object commandObj, Errors error) {
        C command = (C) commandObj;
        try {
            this.studySubjectService.broadcastMessage(command);
            return getMessageBroadcastStatus(request, commandObj, error);
        } catch (C3PRCodedException e) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("responseMessage", e.getMessage());
            return new ModelAndView(getAjaxViewName(request), map);
        }
    }


    public StudySubjectService getStudySubjectService() {
        return studySubjectService;
    }

    public void setStudySubjectService(StudySubjectService studySubjectService) {
        this.studySubjectService = studySubjectService;
    }
}
