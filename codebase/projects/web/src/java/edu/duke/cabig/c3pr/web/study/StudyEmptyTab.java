package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.DefaultCCTSMessageWorkflowCallbackFactory;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Tab that adds no additional refdata or does any processing <p/> Created by
 * IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:38:59 PM To change this
 * template use File | Settings | File Templates.
 */
public class StudyEmptyTab extends StudyTab {
    protected StudyService studyService;
    private edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster messageBroadcaster;
    private XmlMarshaller xmlUtility;
    private DefaultCCTSMessageWorkflowCallbackFactory cctsMessageWorkflowCallbackFactory;


    public StudyEmptyTab(String longTitle, String shortTitle, String viewName) {
        super(longTitle, shortTitle, viewName);
    }

    public ModelAndView getMessageBroadcastStatus(HttpServletRequest request, Object commandObj, Errors error){
        Study study = (Study) commandObj;
        String responseMessage = null;
        try {
            log.debug("Getting status for study");
            responseMessage =  studyService.getCCTSWofkflowStatus(study).getDisplayName();
        } catch (Exception e) {
            log.error(e);
            responseMessage = "Error getting status";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("broadcastResponse", responseMessage);
        return new ModelAndView(getAjaxViewName(request), map);
    }

    public ModelAndView sendMessageToESB(HttpServletRequest request, Object commandObj, Errors error) {
        String responseMessage = "Message send successful";
        try {
            log.debug("Sending message to CCTS esb");
            Study study = (Study) commandObj;
            messageBroadcaster.setNotificationHandler(cctsMessageWorkflowCallbackFactory.createWorkflowCallback(study));
            messageBroadcaster.broadcast(xmlUtility.toXML(study), study.getGridId());
        } catch (Exception e) {
            log.error(e);
            responseMessage = "Message send Failed!";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("broadcastResponse", responseMessage);
        return new ModelAndView(getAjaxViewName(request), map);
    }


    @SuppressWarnings("finally")
    @Override
    protected ModelAndView postProcessInPlaceEditing(
            HttpServletRequest request, Study command, String property,
            String value) {

        Map<String, String> map = new HashMap<String, String>();
        String retValue = "";

        if (property.startsWith("changedSiteStudy")) {

            int studySiteIndex = Integer.parseInt(property.split("_")[1]);

            if (property.startsWith("changedSiteStudyStatus")) {

                SiteStudyStatus statusObject = SiteStudyStatus.getByCode(value);
                try {
                    studyService.setSiteStudyStatus(command, command.getStudySites().get(studySiteIndex), statusObject);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    retValue = "<script>alert('" + e.getMessage() + "')</script>";
                    // e.printStackTrace();
                } finally {
                    retValue += command.getStudySites().get(studySiteIndex).getSiteStudyStatus().getCode();
                }

            } else if (property.startsWith("changedSiteStudyStartDate")) {

                try {
                    Date startDate = new SimpleDateFormat("mm/dd/yyyy").parse(value);
                    command.getStudySites().get(studySiteIndex).setStartDate(startDate);
                    retValue += command.getStudySites().get(studySiteIndex).getStartDateStr();
                } catch (ParseException e) {
                    retValue = "<script>alert('" + e.getMessage() + "')</script>";
                    // e.printStackTrace();
                }

            } else if (property.startsWith("changedSiteStudyIrbApprovalDate")) {
                try {
                    Date irbApprovalDate = new SimpleDateFormat("mm/dd/yyyy").parse(value);
                    command.getStudySites().get(studySiteIndex).setIrbApprovalDate(irbApprovalDate);
                    retValue += command.getStudySites().get(studySiteIndex).getIrbApprovalDateStr();
                } catch (ParseException e) {
                    retValue = "<script>alert('" + e.getMessage() + "')</script>";
                    //  e.printStackTrace();
                }
            }

        } else if (property.startsWith("changedCoordinatingCenterStudyStatus")) {
            CoordinatingCenterStudyStatus statusObject = CoordinatingCenterStudyStatus.getByCode(value);

            try {
                studyService.setStatuses(command, statusObject);
                //adding a callback incase the status change is successful
                //this callback is used to dynamically display/hide the amend study button
                retValue = "<script>statusChangeCallback('" + command.getCoordinatingCenterStudyStatus().getCode() + "')</script>";
            } catch (Exception e) {
                retValue = "<script>alert('" + e.getMessage() + "')</script>";
                // e.printStackTrace();
            } finally {
                retValue += command.getCoordinatingCenterStudyStatus().getCode();
            }
        } else {
            retValue += command.getCoordinatingCenterStudyStatus().getCode();
        }
        map.put(getFreeTextModelName(), retValue);
        return new ModelAndView("", map);
    }

    @Override
    public void validate(Study study, Errors errors) {
        super.validate(study, errors);
        try {
            studyService.setDataEntryStatus(study, true);
            study.setCoordinatingCenterStudyStatus(studyService.evaluateCoordinatingCenterStudyStatus(study));
        } catch (Exception e) {
            errors.reject("tempProperty", e.getMessage());
        }
    }

    protected boolean suppressValidation(HttpServletRequest request, Object study) {
        if (request.getParameter("_activate") != null && request.getParameter("_activate").equals("true")) {
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


    public CCTSMessageBroadcaster getMessageBroadcaster() {
        return messageBroadcaster;
    }

    public void setMessageBroadcaster(CCTSMessageBroadcaster messageBroadcaster) {
        this.messageBroadcaster = messageBroadcaster;
    }

    public XmlMarshaller getXmlUtility() {
        return xmlUtility;
    }

    public void setXmlUtility(XmlMarshaller xmlUtility) {
        this.xmlUtility = xmlUtility;
    }

    public DefaultCCTSMessageWorkflowCallbackFactory getCctsMessageWorkflowCallbackFactory() {
        return cctsMessageWorkflowCallbackFactory;
    }

    public void setCctsMessageWorkflowCallbackFactory(DefaultCCTSMessageWorkflowCallbackFactory cctsMessageWorkflowCallbackFactory) {
        this.cctsMessageWorkflowCallbackFactory = cctsMessageWorkflowCallbackFactory;
    }
}
