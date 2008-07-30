package edu.duke.cabig.c3pr.web.study;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudyService;

/**
 * Tab for Study Overview/Summary page <p/> Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007
 * Time: 3:38:59 PM To change this template use File | Settings | File Templates.
 */
public class StudyOverviewTab extends StudyTab {
    protected StudyService studyService;

    public StudyOverviewTab(String longTitle, String shortTitle, String viewName) {
        super(longTitle, shortTitle, viewName);
    }

    public ModelAndView getMessageBroadcastStatus(HttpServletRequest request, Object commandObj,
                    Errors error) {
        Study study = (Study) commandObj;
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
        return new ModelAndView(getAjaxViewName(request), map);
    }

    public ModelAndView sendMessageToESB(HttpServletRequest request, Object commandObj, Errors error) {
        try {
            log.debug("Sending message to CCTS esb");
            Study study = (Study) commandObj;
            studyService.broadcastMessage(study);
            return getMessageBroadcastStatus(request, commandObj, error);
        }
        catch (C3PRBaseException e) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("responseMessage", e.getMessage());
            return new ModelAndView(getAjaxViewName(request), map);
        }
    }

    @SuppressWarnings("finally")
    @Override
    protected ModelAndView postProcessInPlaceEditing(HttpServletRequest request, Study command,
                    String property, String value) {

        Map<String, String> map = new HashMap<String, String>();
        String retValue = "";

        if (property.startsWith("changedSiteStudy")) {

            int studySiteIndex = Integer.parseInt(property.split("_")[1]);

            if (property.startsWith("changedSiteStudyStatus")) {

                SiteStudyStatus statusObject = SiteStudyStatus.getByCode(value);
                try {
                    command.getStudySites().get(
                                    studySiteIndex).setWorkFlowSiteStudyStatus(statusObject);
                }
                catch (C3PRCodedException e) {
                    if ((command.getStudySites().get(studySiteIndex).getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL || command
                                    .getStudySites().get(studySiteIndex).getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT)
                                    && statusObject == SiteStudyStatus.ACTIVE && isAdmin()) {
							command.getStudySites().get(studySiteIndex).setSiteStudyStatus(
							                SiteStudyStatus.ACTIVE);
                    }
                    else {
                        retValue = "<script>alert('" + e.getCodedExceptionMesssage() + "')</script>";
                    }
                }
                finally {
                    retValue += command.getStudySites().get(studySiteIndex).getSiteStudyStatus()
                                    .getCode();
                }

            }
            else if (property.startsWith("changedSiteStudyStartDate")) {

                try {
                    Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                    command.getStudySites().get(studySiteIndex).setStartDate(startDate);
                    retValue += command.getStudySites().get(studySiteIndex).getStartDateStr();
                }
                catch (ParseException e) {
                    retValue = "<script>alert('" + e.getMessage() + "')</script>";
                }

            }
            else if (property.startsWith("changedSiteStudyIrbApprovalDate")) {
                try {
                    Date irbApprovalDate = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                    command.getStudySites().get(studySiteIndex).setIrbApprovalDate(irbApprovalDate);
                    retValue += command.getStudySites().get(studySiteIndex).getIrbApprovalDateStr();
                }
                catch (ParseException e) {
                    retValue = "<script>alert('" + e.getMessage() + "')</script>";
                }
            }

        }
        else if (property.startsWith("changedCoordinatingCenterStudyStatus")) {
            CoordinatingCenterStudyStatus statusObject = CoordinatingCenterStudyStatus
                            .getByCode(value);

            try {
                command.setStatuses(statusObject);
                // adding a callback incase the status change is successful
                // this callback is used to dynamically display/hide the amend study button
                retValue = "<script>statusChangeCallback('"+ command.getCoordinatingCenterStudyStatus().getCode()+ "');reloadCompanion();" +
                		"</script>";
            }
            catch (C3PRCodedException e) {
                // case when the user has an admin role and he/she can change the study status to
                // Active even when the study is closed to accrual
                // or closed to accrual and treatment.
                if ((command.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL || command
                                .getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT)
                                && statusObject == CoordinatingCenterStudyStatus.ACTIVE
                                && isAdmin()) {
                    command.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
                }
                else {
                    retValue = "<script>alert('" + e.getMessage() + "')</script>";
                }
            }
            finally {
                retValue += command.getCoordinatingCenterStudyStatus().getCode();
            }
        }
        else if (property.startsWith("changedTargetAccrualNumber")) {
            command.setTargetAccrualNumber(new Integer(value));
            retValue = command.getTargetAccrualNumber().toString();
        }
        else {
            retValue += command.getCoordinatingCenterStudyStatus().getCode();
        }
        map.put(getFreeTextModelName(), retValue);
        return new ModelAndView("", map);
    }

    @Override
    public void validate(Study study, Errors errors) {
        super.validate(study, errors);
        try {
            study.setDataEntryStatus(true);
            if (study.getId() == null) {
                study.setStatuses(study.evaluateCoordinatingCenterStudyStatus());
            }
        }
        catch (Exception e) {
            errors.rejectValue("coordinatingCenterStudyStatus", "dummyCode", e.getMessage());
        }
    }

    protected boolean suppressValidation(HttpServletRequest request, Object study) {
        if (request.getParameter("_activate") != null
                        && request.getParameter("_activate").equals("true")) {
            return false;
        }
        return true;
    }

    public StudyService getStudyService() {
        return studyService;
    }

    public void setStudyService(StudyService studyService) {
        this.studyService = studyService;
    }
    
    public ModelAndView reloadCompanion(HttpServletRequest request, Object command , Errors error) {
		return new ModelAndView(getAjaxViewName(request));
	}
}
