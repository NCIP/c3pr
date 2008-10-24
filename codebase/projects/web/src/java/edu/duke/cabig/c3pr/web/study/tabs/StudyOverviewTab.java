package edu.duke.cabig.c3pr.web.study.tabs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
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

    public StudyOverviewTab(String longTitle, String shortTitle, String viewName, Boolean willSave) {
        super(longTitle, shortTitle, viewName,willSave);
    }
    
    @Override
    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper,
                    Errors errors) {
        if(WebUtils.hasSubmitParameter(request, "statusChange")){
            if(request.getParameter("statusChange").equals("readyToOpen")){
                studyRepository.createStudy(wrapper.getStudy().getIdentifiers());
            }else if(request.getParameter("statusChange").equals("open")){
                studyRepository.openStudy(wrapper.getStudy().getIdentifiers());
            }
        }
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

    private String handleInPlaceEditing(HttpServletRequest request, StudyWrapper command,
                    String property, String value) throws Exception{
        if (property.contains("changedSiteStudy")) {

            int studySiteIndex = Integer.parseInt(property.split("_")[1]);

            if (property.contains("changedSiteStudyStatus")) {

                SiteStudyStatus statusObject = SiteStudyStatus.getByCode(value);
                if(statusObject==SiteStudyStatus.ACTIVE){
                    StudySite studySite=studyRepository.activateStudySite(command.getStudy().getIdentifiers(), command.getStudy().getStudySites().get(studySiteIndex).getHealthcareSite().getNciInstituteCode());
                }else if(statusObject==SiteStudyStatus.APPROVED_FOR_ACTIVTION){
                    StudySite studySite=studyRepository.approveStudySiteForActivation(command.getStudy().getIdentifiers(), command.getStudy().getStudySites().get(studySiteIndex).getHealthcareSite().getNciInstituteCode());
                }else if(statusObject==SiteStudyStatus.CLOSED_TO_ACCRUAL){
                    StudySite studySite=studyRepository.closeStudySite(command.getStudy().getIdentifiers(), command.getStudy().getStudySites().get(studySiteIndex).getHealthcareSite().getNciInstituteCode());
                }else if(statusObject==SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT){
                    StudySite studySite=studyRepository.closeStudySiteToAccrualAndTreatment(command.getStudy().getIdentifiers(), command.getStudy().getStudySites().get(studySiteIndex).getHealthcareSite().getNciInstituteCode());
                }else if(statusObject==SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL){
                    StudySite studySite=studyRepository.temporarilyCloseStudySite(command.getStudy().getIdentifiers(), command.getStudy().getStudySites().get(studySiteIndex).getHealthcareSite().getNciInstituteCode());
                }else if(statusObject==SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT){
                    StudySite studySite=studyRepository.temporarilyCloseStudySiteToAccrualAndTreatment(command.getStudy().getIdentifiers(), command.getStudy().getStudySites().get(studySiteIndex).getHealthcareSite().getNciInstituteCode());
                }
                return command.getStudy().getStudySites().get(studySiteIndex).getSiteStudyStatus()
                .getCode();
            } else if (property.contains("changedSiteStudyStartDate")) {
                    Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                    command.getStudy().getStudySites().get(studySiteIndex).setStartDate(startDate);
                    return command.getStudy().getStudySites().get(studySiteIndex).getStartDateStr();
            } else if (property.contains("changedSiteStudyIrbApprovalDate")) {
                    Date irbApprovalDate = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                    command.getStudy().getStudySites().get(studySiteIndex).setIrbApprovalDate(irbApprovalDate);
                    return command.getStudy().getStudySites().get(studySiteIndex).getIrbApprovalDateStr();
            }

        } else if (property.contains("changedCoordinatingCenterStudyStatus")) {
            CoordinatingCenterStudyStatus statusObject = CoordinatingCenterStudyStatus
                    .getByCode(value);

            if(statusObject==CoordinatingCenterStudyStatus.OPEN){
                Study study=studyRepository.openStudy(command.getStudy().getIdentifiers());
                command.setStudy(study);
            }else if(statusObject==CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL){
                Study study=studyRepository.closeStudy(command.getStudy().getIdentifiers());
                command.setStudy(study);
            }else if(statusObject==CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT){
                Study study=studyRepository.closeStudyToAccrualAndTreatment(command.getStudy().getIdentifiers());
                command.setStudy(study);
            }else if(statusObject==CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL){
                Study study=studyRepository.temporarilyCloseStudy(command.getStudy().getIdentifiers());
                command.setStudy(study);
            }else if(statusObject==CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT){
                Study study=studyRepository.temporarilyCloseStudyToAccrualAndTreatment(command.getStudy().getIdentifiers());
                command.setStudy(study);
            }else if(statusObject==CoordinatingCenterStudyStatus.READY_TO_OPEN){
                Study study=studyRepository.createStudy(command.getStudy().getIdentifiers());
                command.setStudy(study);
            }
            return command.getStudy().getCoordinatingCenterStudyStatus().getCode();
        } else if (property.contains("changedTargetAccrualNumber")) {
            command.getStudy().setTargetAccrualNumber(new Integer(value));
            return command.getStudy().getTargetAccrualNumber().toString();
        } else {
            return command.getStudy().getCoordinatingCenterStudyStatus().getCode();
        }
        return value;
    }
    
    @SuppressWarnings("finally")
    @Override
    protected ModelAndView postProcessInPlaceEditing(HttpServletRequest request, StudyWrapper command,
                                                     String property, String value) {

        Map<String, String> map = new HashMap<String, String>();
        String retValue = "";
        try {
            retValue=handleInPlaceEditing(request, command, property, value);
        }
        catch (Exception e) {
            retValue = "<script>alert('" + e.getMessage() + "')</script>";
        }
        map.put(AjaxableUtils.getFreeTextModelName(), retValue);
        return new ModelAndView("", map);
    }

    public ModelAndView adminOverride(HttpServletRequest request, Object commandObj, Errors error) {
        StudyWrapper command=(StudyWrapper)commandObj;
        String property=request.getParameter("property");
        String value=request.getParameter("value");
        String retValue="";
        Map<String, String> map = new HashMap<String, String>();
        if(!isAdmin()){
            retValue = "<script>alert('You dont have admin privileges to take this action.')</script>";
            map.put(AjaxableUtils.getFreeTextModelName(), retValue);
            return new ModelAndView("", map);        
        }
        if(property==null || value==null){
            retValue = "<script>alert('no value specified')</script>";
            map.put(AjaxableUtils.getFreeTextModelName(), retValue);
            return new ModelAndView("", map);
        }
        if (property.contains("changedSiteStudyStatus")) {

            int studySiteIndex = Integer.parseInt(property.split("_")[1]);
            SiteStudyStatus statusObject = SiteStudyStatus.getByCode(value);
            command.getStudy().getStudySites().get(
                            studySiteIndex).setSiteStudyStatus(statusObject);
            retValue = command.getStudy().getStudySites().get(studySiteIndex).getSiteStudyStatus()
                        .getCode();

        } else if (property.contains("changedCoordinatingCenterStudyStatus")) {
            CoordinatingCenterStudyStatus statusObject = CoordinatingCenterStudyStatus
                    .getByCode(value);
            command.getStudy().setCoordinatingCenterStudyStatus(statusObject);
            retValue = command.getStudy().getCoordinatingCenterStudyStatus().getCode();
        }
        map.put(AjaxableUtils.getFreeTextModelName(), retValue);
        return new ModelAndView("", map);
    }
    
//    public void validate(StudyWrapper wrapper, Errors errors) {
//        super.validate(wrapper, errors);
//        try {
//            wrapper.getStudy().updateDataEntryStatus();
//            studyRepository.open(wrapper.getStudy());
//        }
//        catch (Exception e) {
//            errors.rejectValue("study.coordinatingCenterStudyStatus", "dummyCode", e.getMessage());
//        }
//    }

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
