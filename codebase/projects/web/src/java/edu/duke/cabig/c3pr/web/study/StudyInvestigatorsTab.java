package edu.duke.cabig.c3pr.web.study;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 3:02:39 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyInvestigatorsTab extends StudyTab {
	
	private StudyValidator studyValidator;
	private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
	private OrganizationDao organizationDao;
	StudySiteDao studySiteDao = null;

    public StudyInvestigatorsTab() {
        this(false);
    }

    public StudyInvestigatorsTab(boolean editMode) {
            super("Investigators", "Investigators", editMode?"study/study_investigators_edit":"study/study_investigators");
    }

    @Override
    public Map<String, Object> referenceData(Study study) {
        Map<String, Object> refdata = super.referenceData(study);    //To change body of overridden methods use File | Settings | File Templates.
        addConfigMapToRefdata(refdata, "studyInvestigatorRoleRefData");
        addConfigMapToRefdata(refdata, "studyInvestigatorStatusRefData");

        return refdata;
    }

    
    @Override
	public void validate(Study study, Errors errors) {
		// TODO Auto-generated method stub
		super.validate(study, errors);
		this.studyValidator.validateStudyInvestigators(study, errors);
	}
    
    @Override
    public void postProcess(HttpServletRequest httpServletRequest, Study study, Errors errors) {

        String selected = httpServletRequest.getParameter("_selected");
        String action = httpServletRequest.getParameter("_actionx");
        Object selectedSite = httpServletRequest.getParameter("_selectedSite");
        StudyOrganization so = null;
        
    	//get the StudyOrganization to which we will add/remove investigator.
        List<StudyOrganization> soList = study.getStudyOrganizations();
        if( selectedSite != null && !selectedSite.toString().equals("")){
        	selectedSite = httpServletRequest.getParameter("_selectedSite").toString();
        	so = soList.get(new Integer(selectedSite.toString()).intValue());
        }        
    	
    	if ("siteChange".equals(action)) {
            httpServletRequest.getSession().setAttribute("_selectedSite", selectedSite);
            return;
        }

        if ("addStudyDisease".equals(action) && so != null) {        	
            String[] invIds = so.getStudyInvestigatorIds();
            if(invIds.length > 0){
            	HealthcareSiteInvestigator inv = null;
	            log.debug("Study InvestigatorIds Size : " + so.getStudyInvestigatorIds().length);
	            for (String invId : invIds) {
	                log.debug("Investigator Id : " + invId);
	                StudyInvestigator sInv = new StudyInvestigator();
	                inv = healthcareSiteInvestigatorDao.getById(new Integer(invId).intValue());
	                if(inv != null){
	                	inv.getStudyInvestigators().add(sInv);
	                	sInv.setHealthcareSiteInvestigator(inv);
	                	sInv.setRoleCode("Site Investigator");
	                	sInv.setStatusCode("Active");
	                	sInv.setStudyOrganization(so);
	                	so.getStudyInvestigators().add(sInv);
	                }else{
	                	log.error("StudyInvestigatorTab - postProcess(): healthcareSiteInvestigatorDao.getById() returned null");
	                }	                
	            }            	
            }
            return;
        } else if ("removeStudyDisease".equals(action) && so != null) {
            so.getStudyInvestigators().remove(Integer.parseInt(selected));
            return;
        }
    }

	public StudyValidator getStudyValidator() {
		return studyValidator;
	}

	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	public StudySiteDao getStudySiteDao() {
		return studySiteDao;
	}

	public void setStudySiteDao(StudySiteDao studySiteDao) {
		this.studySiteDao = studySiteDao;
	}

	public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
		return healthcareSiteInvestigatorDao;
	}

	public void setHealthcareSiteInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
		this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
	}

    
}

