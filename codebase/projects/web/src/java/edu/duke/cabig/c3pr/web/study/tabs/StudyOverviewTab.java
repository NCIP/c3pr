package edu.duke.cabig.c3pr.web.study.tabs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StudyStatusHelper;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Tab for Study Overview/Summary page <p/> Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007
 * Time: 3:38:59 PM To change this template use File | Settings | File Templates.
 */
public class StudyOverviewTab extends StudyTab {
    protected StudyService studyService;
    protected Configuration configuration;


	public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public StudyOverviewTab(String longTitle, String shortTitle, String viewName) {
        super(longTitle, shortTitle, viewName);
    }

    public ModelAndView getMessageBroadcastStatus(HttpServletRequest request, Object commandObj,
                                                  Errors error) {
        Study study = ((StudyWrapper) commandObj).getStudy();
        String responseMessage = null;
        try {
            log.debug("Getting status for study");
            responseMessage = studyService.getCCTSWofkflowStatus(study).getDisplayName();
        }
        catch (Exception e) {
            log.error(e);
            responseMessage = "error";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("responseMessage", responseMessage);
        return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
    }

    public ModelAndView sendMessageToESB(HttpServletRequest request, Object commandObj, Errors error) {
        try {
            log.debug("Sending message to CCTS esb");
            Study study = ((StudyWrapper) commandObj).getStudy();
            studyService.broadcastMessage(study);
            return getMessageBroadcastStatus(request, commandObj, error);
        }
        catch (C3PRBaseException e) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("responseMessage", e.getMessage());
            return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
        }
    }

    @Override
    public Map referenceData(HttpServletRequest request, StudyWrapper command) {
        request.setAttribute("isCCTSEnv", isCCTSEnv());
        try {
            command.getStudy().setDataEntryStatus(command.getStudy().evaluateDataEntryStatus());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return super.referenceData(request, command);
    }

    @SuppressWarnings("finally")
    @Override
    protected ModelAndView postProcessInPlaceEditing(HttpServletRequest request, StudyWrapper command,
                                                     String property, String value) {

        Map<String, String> map = new HashMap<String, String>();
        String retValue = "";

        if (property.contains("changedSiteStudy")) {

            int studySiteIndex = Integer.parseInt(property.split("_")[1]);

            if (property.contains("changedSiteStudyStatus")) {

                SiteStudyStatus statusObject = SiteStudyStatus.getByCode(value);
                try {
                    command.getStudy().getStudySites().get(
                            studySiteIndex).setWorkFlowSiteStudyStatus(statusObject);
                }
                catch (C3PRCodedRuntimeException e) {
                    if ((command.getStudy().getStudySites().get(studySiteIndex).getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL || command
                            .getStudy().getStudySites().get(studySiteIndex).getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT)
                            && statusObject == SiteStudyStatus.ACTIVE && isAdmin()) {
                        command.getStudy().getStudySites().get(studySiteIndex).setSiteStudyStatus(
                                SiteStudyStatus.ACTIVE);
                    } else {
                        retValue = "<script>alert('" + e.getCodedExceptionMesssage() + "')</script>";
                    }
                }
                finally {
                    retValue += command.getStudy().getStudySites().get(studySiteIndex).getSiteStudyStatus()
                            .getCode();
                }

            } else if (property.contains("changedSiteStudyStartDate")) {

                try {
                    Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                    command.getStudy().getStudySites().get(studySiteIndex).setStartDate(startDate);
                    retValue += command.getStudy().getStudySites().get(studySiteIndex).getStartDateStr();
                }
                catch (ParseException e) {
                    retValue = "<script>alert('" + e.getMessage() + "')</script>";
                }

            } else if (property.contains("changedSiteStudyIrbApprovalDate")) {
                try {
                    Date irbApprovalDate = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                    command.getStudy().getStudySites().get(studySiteIndex).setIrbApprovalDate(irbApprovalDate);
                    retValue += command.getStudy().getStudySites().get(studySiteIndex).getIrbApprovalDateStr();
                }
                catch (ParseException e) {
                    retValue = "<script>alert('" + e.getMessage() + "')</script>";
                }
            }

        } else if (property.contains("changedCoordinatingCenterStudyStatus")) {
            CoordinatingCenterStudyStatus statusObject = CoordinatingCenterStudyStatus
                    .getByCode(value);

            try {
            	 StudyStatusHelper.setStatus(command.getStudy(), statusObject);
                // adding a callback incase the status change is successful
                // this callback is used to dynamically display/hide the amend study button
                retValue = "<script>statusChangeCallback('" + command.getStudy().getCoordinatingCenterStudyStatus().getCode() + "');reloadCompanion();" +
                        "</script>";
            }
            catch (C3PRCodedRuntimeException e) {
                // case when the user has an admin role and he/she can change the study status to
                // Active even when the study is closed to accrual
                // or closed to accrual and treatment.
                if ((command.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL || command
                        .getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT)
                        && statusObject == CoordinatingCenterStudyStatus.OPEN
                        && isAdmin()) {
                    command.getStudy().setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
                } else {
                    retValue = "<script>alert('" + e.getMessage() + "')</script>";
                }
            }
            finally {
                retValue += command.getStudy().getCoordinatingCenterStudyStatus().getCode();
            }
        } else if (property.contains("changedTargetAccrualNumber")) {
            command.getStudy().setTargetAccrualNumber(new Integer(value));
            retValue = command.getStudy().getTargetAccrualNumber().toString();
        } else {
            retValue += command.getStudy().getCoordinatingCenterStudyStatus().getCode();
        }
        map.put(AjaxableUtils.getFreeTextModelName(), retValue);
        return new ModelAndView("", map);
    }

    public void validate(StudyWrapper wrapper, Errors errors) {
        super.validate(wrapper, errors);
        try {
            wrapper.getStudy().updateDataEntryStatus();
            studyRepository.open(wrapper.getStudy());
        }
        catch (Exception e) {
            errors.rejectValue("study.coordinatingCenterStudyStatus", "dummyCode", e.getMessage());
        }
    }

    protected boolean suppressValidation(HttpServletRequest request, Object study) {
        if (request.getParameter("_activate") != null
                && request.getParameter("_activate").equals("true")) {
            return false;
        }
        return true;
    }

    private boolean isCCTSEnv() {
        return this.configuration.get(Configuration.ESB_ENABLE).equalsIgnoreCase("true");
    }

    public StudyService getStudyService() {
        return studyService;
    }

    public void setStudyService(StudyService studyService) {
        this.studyService = studyService;
    }

    public ModelAndView reloadCompanion(HttpServletRequest request, Object command , Errors error) {
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
	}
}
