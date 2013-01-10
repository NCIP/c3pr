/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.CommonUtils;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 1:39:34 PM To
 * change this template use File | Settings | File Templates.
 */
public class StudySitesTab extends StudyTab {

	private StudyValidator studyValidator;

	protected Configuration configuration;

	protected HealthcareSiteDao healthcareSiteDao;

	private MessageSource c3prErrorMessages;

	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}

	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public StudySitesTab() {
		super("Sites", "Sites", "study/study_sites");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> referenceDataForTab(HttpServletRequest request,
			StudyWrapper wrapper) {
		Study study = wrapper.getStudy();
		 Map<String, Object> refdata = super.referenceDataForTab(request,wrapper);
		refdata.put("multisiteEnv", new Boolean(this.configuration.get(Configuration.MULTISITE_ENABLE)));
		refdata.put("localNCICode", this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE));
		refdata.put("openSections",request.getParameter("openSections"));
		refdata.put("studyVersionAssociationMap",isStudyVersionSetupValid(wrapper.getStudy()));
		refdata.put("currentDate",CommonUtils.getDateString(new Date()));
		return refdata;
	}

	@Override
	public void validate(StudyWrapper wrapper, Errors errors) {
		super.validate(wrapper, errors);
		this.studyValidator.validateStudySites(wrapper.getStudy(), errors);
	}

	public StudyValidator getStudyValidator() {
		return studyValidator;
	}

	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}

	private static List<String> getTokenList(String string) {
		List<String> tokenList = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(string, "|");
		while (st.hasMoreTokens()) {
			tokenList.add(st.nextToken());
		}
		return tokenList;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView changeStatus(HttpServletRequest request, Object obj,
			Errors errors) {
		String effectiveDateStr = request.getParameter("effectiveDate");
		Date effectiveDate = DateUtil.getUtilDateFromString(effectiveDateStr, "MM/dd/yyyy");
		StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();
		// updating study with irb approval date, target accrual and activation date.
		study = studyDao.merge(study);
		Map map = new HashMap();
		String nciInstituteCode = request.getParameter("primaryIdentifier");
		StudySite studySite;
		EndPoint endPoint = null;
		String studySiteType = request.getParameter("studySiteType");
		if (StringUtils.isBlank(studySiteType)) {
			studySite = study.getStudySite(nciInstituteCode);
		} else {
			studySite = study.getCompanionStudySite(nciInstituteCode);
		}
		
		String irbApprovalDateStr = request.getParameter("irbApprovalDate-" + nciInstituteCode);
		String targetAccrualStr = request.getParameter("targetAccrual-" + nciInstituteCode);
		if(StringUtils.isNotBlank(targetAccrualStr)){
			studySite.setTargetAccrualNumber(Integer.parseInt(targetAccrualStr));
		}
		if(StringUtils.isNotBlank(irbApprovalDateStr)){
			Date irbApprovalDate = DateUtil.getUtilDateFromString(irbApprovalDateStr, "MM/dd/yyyy");	
			studySite.setIrbApprovalDate(irbApprovalDate);
		}
		List<Identifier> studyIdentifiers = study.getIdentifiers();
		
		studySiteDao.merge(studySite);

		APIName apiName = APIName.valueOf(request.getParameter("action"));
		try {
			if (apiName == APIName.ACTIVATE_STUDY_SITE) {
				studySite = studyRepository.activateStudySite(studyIdentifiers,
							studySite, effectiveDate);
			} else if (apiName == APIName.CLOSE_STUDY_SITE_TO_ACCRUAL) {
				studySite = studyRepository.closeStudySiteToAccrual(studyIdentifiers,
							nciInstituteCode, effectiveDate);
			}else if (apiName == APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT) {
				studySite = studyRepository.closeStudySiteToAccrualAndTreatment(studyIdentifiers,
							nciInstituteCode, effectiveDate);
			}else if (apiName == APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL) {
				studySite = studyRepository.temporarilyCloseStudySiteToAccrual(studyIdentifiers,
							nciInstituteCode, effectiveDate);
			}else if (apiName == APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT) {
				studySite = studyRepository.temporarilyCloseStudySiteToAccrualAndTreatment(studyIdentifiers,
							nciInstituteCode, effectiveDate);
			}
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			map.put("errorMessage", e.getCodedExceptionMesssage());
		}
		if(endPoint!=null && endPoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_FAILED){
			map.put("errorMessage", endPoint.getLastAttemptError().getErrorMessage());
		}
		wrapper.setStudy(studyRepository
						.getUniqueStudy(study.getIdentifiers()));
		studyDao.initialize(wrapper.getStudy());
		// using the nci code to load the fresh studysite from the study.
		if (StringUtils.isBlank(studySiteType)) {
			studySite = wrapper.getStudy().getStudySite(nciInstituteCode);
		} else {
			studySite = wrapper.getStudy().getCompanionStudySite(
					nciInstituteCode);
		}
		map.put("site", studySite);
		map.put("apiName", apiName);
		map.put("action", request.getParameter("action"));
		Integer index=null;
		for(int i=0 ; i<wrapper.getStudy().getStudySites().size(); i++){
			if(wrapper.getStudy().getStudySites().get(i).getHealthcareSite().getPrimaryIdentifier().equals(nciInstituteCode)){
				index=i;
			}
		}
		map.put("siteIndex", index);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView addStudySite(HttpServletRequest request, Object obj,Errors errors) {
		StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();
		String primaryIdentifier = request.getParameter("primaryIdentifier");
		HealthcareSite healthcareSite = (HealthcareSite)healthcareSiteDao.getByPrimaryIdentifier(primaryIdentifier);
		for(StudySite site : study.getStudySites()){
			if(site.getHealthcareSite().getPrimaryIdentifier().equals(primaryIdentifier)){
				return new ModelAndView("study/exist_study_site");
			}
		}
		StudySite studySite = new StudySite();
		studySite.setHealthcareSite(healthcareSite);
		study.addStudySite(studySite);

		Map map = new HashMap();
		map.put("site", studySite);
		map.put("siteIndex", study.getStudySites().size() - 1);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView deleteStudySite(HttpServletRequest request, Object obj,Errors errors) {
		StudyWrapper wrapper = (StudyWrapper) obj;
		int id = wrapper.getStudy().getId();
		Study study = studyDao.getById(id);
		studyDao.initialize(study);
		String primaryIdentifier = request.getParameter("primaryIdentifier");
		StudySite studySite = study.getStudySite(primaryIdentifier);
		study.removeStudySite(studySite);
		for(CompanionStudyAssociation compStudyAssoc : study.getCompanionStudyAssociations()){
			Study compStudy = compStudyAssoc.getCompanionStudy();
			if(compStudy.getIsEmbeddedCompanionStudy()){
				StudySite studySiteAssociatedToCompanion = compStudy.getStudySite(primaryIdentifier);
				compStudy.removeStudySite(studySiteAssociatedToCompanion);
			}
		}
		
		studyDao.flush();
		studyDao.evict(study);
		for(CompanionStudyAssociation compStudyAssoc : study.getCompanionStudyAssociations()){
			Study compStudy = compStudyAssoc.getCompanionStudy();
			studyDao.evict(compStudy);
		}
		study = studyDao.getById(id);
		studyDao.initialize(study);
		//CPR-1595 Hack to initialize parent study association.
		study.getParentStudyAssociations().size();
		wrapper.setStudy(study);
		Map map = new HashMap();
		map.put("command", wrapper);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView applyAmendment(HttpServletRequest request, Object obj,Errors errors) {
		StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();
		int id = study.getId();
		String primaryIdentifier = request.getParameter("sitePrimaryId");
		String irbApprovalDateStr = request.getParameter("irbApprovalDate");
		String index = request.getParameter("index");
		String localNCICode = request.getParameter("localNCICode");
		String isMultisite = request.getParameter("isMultisite");
		String action = request.getParameter("action");
		String errorMessage = "";
		String versionName = request.getParameter("versionName");

		StudySite studySite = study.getStudySite(primaryIdentifier);
		Date irbApprovalDate = null;
		if(StringUtils.isNotBlank(irbApprovalDateStr)){
			irbApprovalDate = DateUtil.getUtilDateFromString(irbApprovalDateStr, "MM/dd/yyyy");
			Date currentDate = new Date();
	        GregorianCalendar calendar = new GregorianCalendar();
	        calendar.setTime(currentDate);
	        calendar.add(calendar.YEAR, -1);
	        calendar.add(calendar.DATE,  1);

	        String allowedOldDate = DateUtil.formatDate(calendar.getTime(), "MM/dd/yyyy");
	        String todayDate = DateUtil.formatDate(currentDate, "MM/dd/yyyy");
	        if (irbApprovalDate.before(calendar.getTime()) || irbApprovalDate.after(currentDate)) {
	        	errorMessage= "IRB approval should be between" + allowedOldDate + " and "+ todayDate ;
	        }else{
	        	//TODO write api in study repo to apply amendment, dont merge here.
	        	try{
	        		studySite.applyStudyAmendment(versionName, irbApprovalDate);
	        		studySiteDao.merge(studySite);
		        	studySiteDao.evict(studySite);
	        	}catch (Exception e) {
					errorMessage = e.getMessage();
				}
	        	studyDao.evict(study);
	        	study = studyDao.getById(id);
	        	studyDao.initialize(study);
	        	wrapper.setStudy(study);
	    		studySite = study.getStudySite(primaryIdentifier);
	        }
		}else{
			errorMessage =  "IRB approval date is mandatory";
		}
		Map map = new HashMap();
		map.put("command", wrapper);
		map.put("site", studySite);
		map.put("siteIndex", index);
		map.put("isMultisite", isMultisite);
		map.put("localNCICode", localNCICode);
		map.put("action", action);
		map.put("errorMessage", errorMessage);
		map.put("showActionButtons", true);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}

	private Map<String, List<String>> isStudyVersionSetupValid(Study study){
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		List<String> messages = new ArrayList<String>();
		for(StudySite studySite : study.getStudySites()){
			try{
				studySite.isStudyVersionSetupValid();
			}catch(C3PRCodedRuntimeException ex){
				ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		        resourceBundleMessageSource.setBasename("error_messages_c3pr");
		        messages.add(resourceBundleMessageSource.getMessage(Integer.toString(ex.getExceptionCode()) + ".COLOR", new String[]{}, null));
				messages.add(ex.getCodedExceptionMesssage());
				map.put(studySite.getHealthcareSite().getPrimaryIdentifier(), messages);
			}
		}
		return map;
	}
	
}
