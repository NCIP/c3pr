package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 1:39:34 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudySitesTab extends StudyTab {

    private StudyValidator studyValidator;
    
    protected Configuration configuration;
    
    protected OrganizationDao organizationDao;

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
        super("Study Sites", "Study Sites", "study/study_studysites");
    }

    @Override
    public Map<String, Object> referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        addConfigMapToRefdata(refdata, "studySiteStatusRefData");
        addConfigMapToRefdata(refdata, "studySiteRoleCodeRefData");
        refdata.put("multisiteEnv", new Boolean(this.configuration.get(Configuration.MULTISITE_ENABLE)));
        boolean isAdmin = isAdmin();
        return refdata;
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper,
                    Errors errors) {
    	Study study = wrapper.getStudy();
        for(StudySite studySite: study.getStudySites()){
            setCoordinatingCenterStudyStatus(request, study, studySite);
        }
        for(CompanionStudyAssociation parentStudyAssociation : study.getParentStudyAssociations()){
        	for(StudySite studySite: parentStudyAssociation.getStudySites()){
                setCoordinatingCenterStudyStatus(request, study, studySite);
            }
        }
    }

	private void setCoordinatingCenterStudyStatus(HttpServletRequest request,
			Study study, StudySite studySite) {
		if(studySite.getIsCoordinatingCenter() || studySite.getHostedMode()){
		    studySite.setCoordinatingCenterStudyStatus(study.getCoordinatingCenterStudyStatus());
		}else if(WebUtils.hasSubmitParameter(request, "submitted") && (!WebUtils.hasSubmitParameter(request, studySite.getHealthcareSite().getNciInstituteCode()+"-wasHosted") || request.getParameter(studySite.getHealthcareSite().getNciInstituteCode()+"-wasHosted").equalsIgnoreCase("true"))){
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
    
    public ModelAndView associateParentStudySites(HttpServletRequest request, Object obj, Errors errors) {

		HashMap map = new HashMap();
		List<StudySite> studySites = new ArrayList<StudySite>();

		StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();
		
		String parentAssociationId = request.getParameter("studyAssociationId");
		String nciCodes = request.getParameter("nciCodes");
		String irbApprovalSites = request.getParameter("irbApprovalSites");
		
		List<String> nciCodeList = getNciCodeList(nciCodes);
		List<String> irbApprovalList = getNciCodeList(irbApprovalSites);
		
		if(irbApprovalList.size() > 0){
			studyDao.initialize(study);
		}
		
		for (CompanionStudyAssociation parentStudyAssociation : study.getParentStudyAssociations()) {
			if (StringUtils.equals(parentAssociationId, parentStudyAssociation.getId().toString())) {
				for (String nciCode : nciCodeList) {
					HealthcareSite healthcareSite = (HealthcareSite) organizationDao.getByNciIdentifier(nciCode).get(0);
					StudySite studySite = new StudySite();
					studySite.setHealthcareSite(healthcareSite);
					studySite.setStudy(study);
					if(irbApprovalList.contains(nciCode)){
						studySite.setIrbApprovalDate(getParentStudySite(parentStudyAssociation, nciCode).getIrbApprovalDate());	
					}
					parentStudyAssociation.addStudySite(studySite);
				}
				map.put("parentStudyAssociation", parentStudyAssociation);
				map.put("parentIndex",request.getParameter("parentIndex"));
				break;
			}
		}
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}

	private StudySite getParentStudySite(CompanionStudyAssociation parentStudyAssociation, String nciCode) {
		Study study = parentStudyAssociation.getParentStudy();
		return study.getStudySite(nciCode);
	}

	private static List<String> getNciCodeList(String nciCodes) {
		List<String> nciCodeList = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(nciCodes,"|");
	     while (st.hasMoreTokens()) {
	         nciCodeList.add(st.nextToken());
	     }
		return nciCodeList;
	}
	
	 public ModelAndView removeCompanionStudyAssociation(HttpServletRequest request, Object obj, Errors errors) {
			StudyWrapper wrapper = (StudyWrapper) obj;
			Study study = wrapper.getStudy();
			
			HashMap map = new HashMap();
			String studySiteId = request.getParameter("studySiteId");
			if(!StringUtils.isBlank(studySiteId)){
				StudySite studySite = studySiteDao.getById(Integer.parseInt(studySiteId));
				CompanionStudyAssociation companionStudyAssociation = studySite.getCompanionStudyAssociation();
				companionStudyAssociation.removeStudySite(studySite);
				map.put("parentStudyAssociation", companionStudyAssociation);
			}
			return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
		}
	
}
