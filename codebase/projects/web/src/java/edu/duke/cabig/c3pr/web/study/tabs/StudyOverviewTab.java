/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.AmendmentType;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Error;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StringUtils;
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
        setShowSummary("false");
    }
    
    public StudyOverviewTab(String longTitle, String shortTitle, String viewName, Boolean willSave) {
        super(longTitle, shortTitle, viewName,willSave);
        setShowSummary("false");
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper,
                    Errors errors) {
    	Study study = wrapper.getStudy();
        if(WebUtils.hasSubmitParameter(request, "statusChange")){
            if(request.getParameter("statusChange").equals("readyToOpen")){
                study = studyRepository.createStudy(study.getIdentifiers());
                request.setAttribute("studyMessage", "STUDY.CREATED_SUCCESSFULLY");
            }else if(request.getParameter("statusChange").equals("open")){
            	/*
            	 * TODO: Adding a temporary fix to capture open date. Need to remove this.
            	 */
            	if(study.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING){
	            	Date versionDate = study.getStudyVersion().getVersionDate();
	            	study = studyDao.getById(study.getId());
	            	study.getStudyVersion().setVersionDate(versionDate);
	            	for(CompanionStudyAssociation compStudyAssoc : study.getCompanionStudyAssociations() ){
	            		if(!compStudyAssoc.getCompanionStudy().getStandaloneIndicator()){
	            			compStudyAssoc.getCompanionStudy().getStudyVersion().setVersionDate(versionDate);
	            		}
	            	}
	            	studyRepository.merge(study);
            	}
            	
            	study = studyRepository.openStudy(study.getIdentifiers());
            	request.setAttribute("studyMessage", "STUDY.OPENED_SUCCESSFULLY");
            }else if(request.getParameter("statusChange").equals("close")){
            	String closeStudyStatus=request.getParameter("closeStatus");
            	if(closeStudyStatus.equals("Closed_To_Accrual_And_Treatment")){
            		study = studyRepository.closeStudyToAccrualAndTreatment(study.getIdentifiers());
            	}else if(closeStudyStatus.equals("Closed_To_Accrual")){
            		study = studyRepository.closeStudyToAccrual(study.getIdentifiers());
            	}else if(closeStudyStatus.equals("Temporarily_Closed_To_Accrual_And_Treatment")){
            		study = studyRepository.temporarilyCloseStudyToAccrualAndTreatment(study.getIdentifiers());
            	}else if(closeStudyStatus.equals("Temporarily_Closed_To_Accrual")){
            		study = studyRepository.temporarilyCloseStudy(study.getIdentifiers());
            	}
            	if(!StringUtils.getBlankIfNull(closeStudyStatus).equals("")){
            		request.setAttribute("studyMessage", "STUDY.CLOSED_SUCCESSFULLY");
            	}
            }else if(request.getParameter("statusChange").equals("applyAmendment")){
            	// this should come after some validation , temp fix, fix it later
            	for(StudySite studySite : study.getStudySites()){
            		GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(study.getCurrentStudyAmendment().getVersionDate());
            		StudySiteStudyVersion studySiteStudyVersion = studySite.getStudySiteStudyVersion();
            		if(studySiteStudyVersion != null && studySiteStudyVersion.getStartDate()!= null){
            			AmendmentType amendmentType = study.getCurrentStudyAmendment().getAmendmentType() ;
                		if(amendmentType == AmendmentType.IMMEDIATE){
                			cal.add(Calendar.DATE, -1);
                			studySiteStudyVersion.setEndDate(cal.getTime());
                		}else if(amendmentType == AmendmentType.IMMEDIATE_AFTER_GRACE_PERIOD){
                			int gracePeriod = study.getCurrentStudyAmendment().getGracePeriod();
                			cal.add(Calendar.DATE, -1+gracePeriod);
                			studySiteStudyVersion.setEndDate(cal.getTime());
                		}
            		}
            	}
            	studyRepository.merge(study);
            	study = studyRepository.applyAmendment(study.getIdentifiers());
            	request.setAttribute("studyMessage", "STUDY.AMENDED_SUCCESSFULLY");
            }
            wrapper.setStudy(study);
        }
    }

    public ModelAndView getMessageBroadcastStatus(HttpServletRequest request, Object commandObj,
                                                  Errors error) {
    	Study study = ((StudyWrapper) commandObj).getStudy();
    	study = studyDao.getById(study.getId());
        ((StudyWrapper) commandObj).setStudy(study);
        return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
    }

    public ModelAndView sendMessageToESB(HttpServletRequest request, Object commandObj, Errors error) {
    	Study study = ((StudyWrapper) commandObj).getStudy();
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
            log.debug("Sending message to CCTS esb");
            studyService.broadcastMessage(study);
            return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
        }
        catch (C3PRCodedException e) {
        	log.error(e);
            map.put("codedError", e);
            return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
        }
        catch (Exception e) {
        	log.error(e);
            map.put("generalError", e);
            return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
        }finally{
        	study = studyDao.getById(study.getId());
            ((StudyWrapper) commandObj).setStudy(study);
        }
    }

    @Override
    public Map referenceDataForTab(HttpServletRequest request, StudyWrapper command) {
        request.setAttribute("isCCTSEnv", isCCTSEnv());
        List<Error> dataEntryErrors = new ArrayList<Error>();
        command.getStudy().setDataEntryStatus(command.getStudy().evaluateDataEntryStatus(dataEntryErrors));
        request.setAttribute("errors", dataEntryErrors);
        Map<String, Object> refdata = super.referenceDataForTab(request, command);
        refdata.put("canAmendStudy", command.canAmendStudy());
        refdata.put("resumeAmendment", command.resumeAmendment());
        refdata.put("applyAmendment", command.applyAmendment());
        if(configuration.get(Configuration.ESB_ENABLE).equals("true")){
        	request.setAttribute("canBroadcast", "true");
        }
        return refdata ;
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

    public ModelAndView updateTargetAccrual(HttpServletRequest request, Object command , Errors error) {
    	return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
	}

}
