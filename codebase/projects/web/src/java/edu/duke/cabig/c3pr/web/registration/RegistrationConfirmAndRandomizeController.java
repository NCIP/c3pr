package edu.duke.cabig.c3pr.web.registration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.ArmDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.impl.StudySubjectServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;

public class RegistrationConfirmAndRandomizeController extends SimpleFormController {

    private StudySubjectDao studySubjectDao;

    private StudySubjectServiceImpl studySubjectService;

    private Configuration configuration;

    private ArmDao armDao;

    public StudySubjectDao getStudySubjectDao() {
        return studySubjectDao;
    }

    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

    public StudySubjectServiceImpl getStudySubjectService() {
        return studySubjectService;
    }

    public void setStudySubjectService(StudySubjectServiceImpl studySubjectService) {
        this.studySubjectService = studySubjectService;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
                    throws Exception {
        binder.registerCustomEditor(Arm.class, new CustomDaoEditor(armDao));
    }

    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors)
                    throws Exception {
        StudySubject studySubject = ((StudySubject) command);
        Map map = buildMap(studySubject);
        boolean actionRequired = false;
        String actionLabel = "";
        if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.UNAPPROVED
                        && studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.COMPLETE
                        && studySubject.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE) {
            actionRequired = true;
            if (studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.REGISTERED) {
                if (studySubject.getScheduledEpoch().getEpoch().isEnrolling()) actionLabel = "Register";
                else actionLabel = "Save";
            }
            else actionLabel = "Transfer Subject";
            if (studySubject.getScheduledEpoch().getRequiresRandomization()) {
                actionLabel += " & Randomize";
            }
        }
        map.put("actionRequired", actionRequired);
        map.put("actionLabel", actionLabel);
        map.put("requiresMultiSite", studySubjectService
                        .requiresCoordinatingCenterApproval(studySubject));
        addAppUrls(map);
        return map;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return studySubjectDao.getById(Integer.parseInt(request.getParameter("registrationId")));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
                    Object command, BindException errors) throws Exception {
        StudySubject studySubject = (StudySubject) command;
        if (!validSubmit(studySubject)) {
            throw new Exception(
                            "Subject is either already registered or the subject registration requires QC");
        }
        Map map = new HashMap();
        map.put("actionRequired", false);
        try {
            studySubject = studySubjectService.registerSubject(studySubject);
        }
        catch (C3PRBaseException e) {
            map.put("registrationException", e);
            return new ModelAndView(getSuccessView(), map);

        }
        if (logger.isDebugEnabled()) {
            logger
                            .debug("onSubmit(HttpServletRequest, HttpServletResponse, Object, BindException) - registration service call over"); //$NON-NLS-1$
        }
        map.putAll(buildMap(studySubject));
        map.put("command", command);
        addAppUrls(map);
        return new ModelAndView(getSuccessView(), map);
    }

    private Map buildMap(StudySubject studySubject) {
        Map map = new HashMap();
        boolean reg_unregistered = false;
        boolean reg_registered = false;
        boolean reg_unapproved = false;
        boolean reg_pending = false;
        boolean reg_reserved = false;
        boolean reg_disapproved = false;
        boolean reg_nonenrolled = false;
        boolean reg_unrandomized = false;
        boolean epoch_unapproved = false;
        boolean epoch_pending = false;
        boolean epoch_approved = false;
        boolean epoch_nonenrolled = false;
        boolean epoch_unrandomized = false;
        boolean epoch_disapproved = false;
        boolean newRegistration = true;
        String armAssigned = "";
        String armAssignedLabel = "";
        if (studySubject.getIfTreatmentScheduledEpoch()
                        && ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch())
                                        .getScheduledArm() != null) {
            if (studySubject.getStudySite().getStudy().getBlindedIndicator()) {
                armAssigned = ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch())
                                .getScheduledArm().getKitNumber();
                armAssignedLabel = "Kit Assigned";
            }
            else if (((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch()).getScheduledArm()
                            .getArm() != null) {
                armAssigned = ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch())
                                .getScheduledArm().getArm().getName();
                armAssignedLabel = "Arm Assigned";
            }

        }
        int count = 0;
        for (ScheduledEpoch scheduledEpoch : studySubject.getScheduledEpochs()) {
            if (scheduledEpoch.getEpoch().isEnrolling()) count++;
        }
        if (studySubject.getScheduledEpoch().getEpoch().isEnrolling()) {
            count--;
        }
        else if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.APPROVED) {
            reg_nonenrolled = true;
            epoch_nonenrolled = true;
        }
        if (count > 0) newRegistration = false;
        if (studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.COMPLETE
                        && studySubject.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE
                        && studySubject.getScheduledEpoch().getRequiresRandomization()
                        && studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.UNAPPROVED) {
            reg_unrandomized = true;
            epoch_unrandomized = true;
        }
        switch (studySubject.getRegWorkflowStatus()) {
            case UNREGISTERED:
                reg_unregistered = true;
                break;
            case REGISTERED:
                reg_registered = true;
                break;
            case PENDING:
                reg_pending = true;
                break;
            case RESERVED:
                reg_reserved = true;
                break;
            case DISAPPROVED:
                reg_disapproved = true;
                break;
        }
        switch (studySubject.getScheduledEpoch().getScEpochWorkflowStatus()) {
            case UNAPPROVED:
                epoch_unapproved = true;
                break;
            case APPROVED:
                epoch_approved = true;
                break;
            case PENDING:
                epoch_pending = true;
                break;
            case DISAPPROVED:
                epoch_disapproved = true;
                break;
        }
        map.put("reg_unregistered", reg_unregistered);
        map.put("reg_registered", reg_registered);
        map.put("reg_unapproved", reg_unapproved);
        map.put("reg_pending", reg_pending);
        map.put("reg_reserved", reg_reserved);
        map.put("reg_disapproved", reg_disapproved);
        map.put("epoch_unapproved", epoch_unapproved);
        map.put("epoch_pending", epoch_pending);
        map.put("epoch_approved", epoch_approved);
        map.put("epoch_disapproved", epoch_disapproved);
        map.put("newRegistration", newRegistration);
        map.put("armAssigned", armAssigned);
        map.put("armAssignedLabel", armAssignedLabel);
        map.put("reg_nonenrolled", reg_nonenrolled);
        map.put("reg_unrandomized", reg_unrandomized);
        map.put("epoch_nonenrolled", epoch_nonenrolled);
        map.put("epoch_unrandomized", epoch_unrandomized);
        return map;
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

    private boolean validSubmit(StudySubject studySubject) {
        return studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.UNAPPROVED;
    }

    public ArmDao getArmDao() {
        return armDao;
    }

    public void setArmDao(ArmDao armDao) {
        this.armDao = armDao;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
