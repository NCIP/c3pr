package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.MultisiteException;
import edu.duke.cabig.c3pr.tools.Configuration;
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

	protected OrganizationDao organizationDao;
	
	private MessageSource c3prErrorMessages;

	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}

	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public StudySitesTab() {
		super("Sites", "Sites", "study/study_sites");
	}

	@Override
	public Map<String, Object> referenceData(HttpServletRequest request,
			StudyWrapper wrapper) {
		Map<String, Object> refdata = super.referenceData(wrapper);
		refdata.put("multisiteEnv", new Boolean(this.configuration
				.get(Configuration.MULTISITE_ENABLE)));
		refdata.put("localNCICode", this.configuration
				.get(Configuration.LOCAL_NCI_INSTITUTE_CODE));
		refdata.put("openSections",request.getParameter("openSections"));
		return refdata;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView showEndpointMessage(HttpServletRequest request,
			Object obj, Errors errors) {
		StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();
		String nciCode = request.getParameter("nciCode");
		String localNciCode = request.getParameter("localNciCode");
		StudyOrganization studyOrganization = study.getStudyOrganization(nciCode);
		Map map = new HashMap();
		map.put("site", studyOrganization);
		map.put("localSite", study.getStudyOrganization(localNciCode));
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}

	@Override
	public void postProcessOnValidation(HttpServletRequest request,
			StudyWrapper wrapper, Errors errors) {
		Study study = wrapper.getStudy();
		for (StudySite studySite : study.getStudySites()) {
			setCoordinatingCenterStudyStatus(request, study, studySite);
		}
		for (CompanionStudyAssociation parentStudyAssociation : study
				.getParentStudyAssociations()) {
			for (StudySite studySite : parentStudyAssociation.getStudySites()) {
				setCoordinatingCenterStudyStatus(request, study, studySite);
			}
		}
	}

	private void setCoordinatingCenterStudyStatus(HttpServletRequest request,
			Study study, StudySite studySite) {
		if (studySite.getIsCoordinatingCenter() || studySite.getHostedMode()) {
			studySite.setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
		} else if (WebUtils.hasSubmitParameter(request, "submitted") 
				&& (!WebUtils.hasSubmitParameter(request, studySite.getHealthcareSite().getNciInstituteCode()+ "-wasHosted") 
				|| request.getParameter(studySite.getHealthcareSite().getNciInstituteCode()+ "-wasHosted").equalsIgnoreCase("true"))) 
		{
			studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
		}
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

	@SuppressWarnings("unchecked")
	public ModelAndView associateParentStudySites(HttpServletRequest request,
			Object obj, Errors errors) {
		HashMap map = new HashMap();
		List<StudySite> studySites = new ArrayList<StudySite>();
		StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();

		String parentAssociationId = request.getParameter("studyAssociationId");
		String nciCodes = request.getParameter("nciCodes");
		String irbApprovalSites = request.getParameter("irbApprovalSites");

		List<String> nciCodeList = getTokenList(nciCodes);
		List<String> irbApprovalList = getTokenList(irbApprovalSites);

		for (CompanionStudyAssociation parentStudyAssociation : study
				.getParentStudyAssociations()) {
			if (StringUtils.equals(parentAssociationId, parentStudyAssociation
					.getId().toString())) {
				for (String nciCode : nciCodeList) {
					HealthcareSite healthcareSite = (HealthcareSite) organizationDao
							.getByNciIdentifier(nciCode).get(0);
					StudySite studySite = new StudySite();
					studySite.setHealthcareSite(healthcareSite);
					studySite.setStudy(study);
					if (irbApprovalList.contains(nciCode)) {
						studySite.setIrbApprovalDate(parentStudyAssociation
								.getParentStudy().getStudySite(nciCode)
								.getIrbApprovalDate());
					}
					parentStudyAssociation.addStudySite(studySite);
				}
				map.put("parentStudyAssociation", parentStudyAssociation);
				map.put("parentIndex", request.getParameter("parentIndex"));
				break;
			}
		}
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
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
	public ModelAndView removeCompanionStudyAssociation(
			HttpServletRequest request, Object obj, Errors errors) {
		StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();
		HashMap map = new HashMap();
		String studySiteId = request.getParameter("studySiteId");
		if (!StringUtils.isBlank(studySiteId)) {
			StudySite studySite = studySiteDao.getById(Integer
					.parseInt(studySiteId));
			String nciCode = studySite.getHealthcareSite()
					.getNciInstituteCode();
			CompanionStudyAssociation companionStudyAssociation = study
					.getCompanionStudySite(nciCode)
					.getCompanionStudyAssociation();
			companionStudyAssociation.removeStudySite(studySite);
			map.put("parentStudyAssociation", companionStudyAssociation);
			map.put("parentIndex", request.getParameter("parentIndex"));
		}
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView changeStatus(HttpServletRequest request, Object obj,
			Errors errors) {
		StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();
		// updating study with irb approval date, target accrual and activation date.
		study = studyDao.merge(study);
		
		String nciInstituteCode = request.getParameter("nciCode");
		String studySiteType = request.getParameter("studySiteType");
		;
		List<Identifier> studyIdentifiers = study.getIdentifiers();
		StudySite studySite;
		EndPoint endPoint = null;
		if (StringUtils.isBlank(studySiteType)) {
			studySite = study.getStudySite(nciInstituteCode);
		} else {
			studySite = study.getCompanionStudySite(nciInstituteCode);
		}

		APIName apiName = APIName.valueOf(request.getParameter("action"));
		if (apiName == APIName.CREATE_STUDY_DEFINITION) {
			endPoint = studyRepository.createStudyAtAffiliate(studyIdentifiers,
					nciInstituteCode);
		} else if (apiName == APIName.CREATE_AND_OPEN_STUDY) {
			endPoint = studyRepository.createAndOpenStudyAtAffiliate(studyIdentifiers,
					nciInstituteCode);
		} else if (apiName == APIName.OPEN_STUDY) {
			endPoint = studyRepository.openStudyAtAffiliate(studyIdentifiers,
					nciInstituteCode);
		} else if (apiName == APIName.AMEND_STUDY) {
			studyRepository.amendStudyAtAffiliates(studyIdentifiers, study);
		} else if (apiName == APIName.CLOSE_STUDY_TO_ACCRUAL) {
			endPoint = studyRepository.closeStudyToAccrualAtAffiliate(studyIdentifiers,
					nciInstituteCode);
		}else if (apiName == APIName.CLOSE_STUDY_TO_ACCRUAL_AND_TREATMENT) {
			endPoint = studyRepository.closeStudyToAccrualAndTreatmentAtAffiliate(studyIdentifiers,
					nciInstituteCode);
		}else if (apiName == APIName.TEMPORARILY_CLOSE_STUDY_TO_ACCRUAL) {
			endPoint = studyRepository.temporarilyCloseStudyToAccrualAtAffiliate(studyIdentifiers,
					nciInstituteCode);
		}else if (apiName == APIName.TEMPORARILY_CLOSE_STUDY_TO_ACCRUAL_AND_TREATMENT) {
			endPoint = studyRepository.temporarilyCloseStudyToAccrualAndTreatmentAtAffiliate(studyIdentifiers,
					nciInstituteCode);
		} 
		else if (apiName == APIName.ACTIVATE_STUDY_SITE) {
			try {
				studySite = studyRepository.activateStudySite(studyIdentifiers,
						studySite);
			} catch (MultisiteException e) {
				e.printStackTrace();
			} catch (C3PRCodedRuntimeException e) {
				request.setAttribute("actionError", e);
			}
		} else if (apiName == APIName.CLOSE_STUDY_SITE_TO_ACCRUAL) {
			try {
				studySite = studyRepository.closeStudySiteToAccrual(studyIdentifiers,
						nciInstituteCode);
			} catch (MultisiteException e) {
				e.printStackTrace();
			} catch (C3PRCodedRuntimeException e) {
				request.setAttribute("actionError", e);
			}
		}else if (apiName == APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT) {
			try {
				studySite = studyRepository.closeStudySiteToAccrualAndTreatment(studyIdentifiers,
						nciInstituteCode);
			} catch (MultisiteException e) {
				e.printStackTrace();
			} catch (C3PRCodedRuntimeException e) {
				request.setAttribute("actionError", e);
			}
		}else if (apiName == APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL) {
			try {
				studySite = studyRepository.temporarilyCloseStudySiteToAccrual(studyIdentifiers,
						nciInstituteCode);
			} catch (MultisiteException e) {
				e.printStackTrace();
			} catch (C3PRCodedRuntimeException e) {
				request.setAttribute("actionError", e);
			}
		}else if (apiName == APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT) {
			try {
				studySite = studyRepository.temporarilyCloseStudySiteToAccrualAndTreatment(studyIdentifiers,
						nciInstituteCode);
			} catch (MultisiteException e) {
				e.printStackTrace();
			} catch (C3PRCodedRuntimeException e) {
				request.setAttribute("actionError", e);
			}
		}
		if (studySite == null) {
			studySite = wrapper.getStudy().getStudySite(
					studySite.getHealthcareSite().getNciInstituteCode());
		}
		Map map = new HashMap();
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

		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView addStudySite(HttpServletRequest request, Object obj,Errors errors) {
		StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();
		String nciCode = request.getParameter("nciCode");
		HealthcareSite healthcareSite = (HealthcareSite)organizationDao.getByNciIdentifier(nciCode).get(0);
		for(StudySite site : study.getStudySites()){
			if(site.getHealthcareSite().getNciInstituteCode().equals(nciCode)){
				return new ModelAndView("study/exist_study_site");
			}
		}
		StudySite studySite = new StudySite();
		studySite.setHealthcareSite(healthcareSite);
		studySite.setRoleCode("Affiliate Site");
		study.addStudySite(studySite);
		setCoordinatingCenterStudyStatus(request, study, studySite);
		Map map = new HashMap();
		map.put("site", studySite);
		map.put("index", study.getStudySites().size() - 1); 
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView deleteStudySite(HttpServletRequest request, Object obj,Errors errors) {
		StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();
		String nciCode = request.getParameter("nciCode");
		StudySite studySite = study.getStudySite(nciCode);
		study.removeStudySite(studySite);
		Study modifiedStudy = studyDao.merge(study);
		wrapper.setStudy(modifiedStudy);
		Map map = new HashMap();
		map.put("command", wrapper); 
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}
}
