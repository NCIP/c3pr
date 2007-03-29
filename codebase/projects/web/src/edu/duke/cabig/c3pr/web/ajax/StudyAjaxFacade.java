package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.HttpSessionRequiredException;

import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudyPersonnelDao;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.StudySite;

/**
 * @author Priyatam
 */
public class StudyAjaxFacade {
    private StudyDao studyDao;       
    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
    private StudyPersonnelDao studyPersonnelDao;
    private ResearchStaffDao researchStaffDao;
    
    @SuppressWarnings("unchecked")
    private <T> T buildReduced(T src, List<String> properties) {
        T dst = null;
        try {
            // it doesn't seem like this cast should be necessary
            dst = (T) src.getClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        }

        BeanWrapper source = new BeanWrapperImpl(src);
        BeanWrapper destination = new BeanWrapperImpl(dst);
        for (String property : properties) {
            destination.setPropertyValue(
                property,
                source.getPropertyValue(property)
            );
        }
        return dst;
    }
      
    public List<Study> matchStudies(String text) {
        List<Study> studies = studyDao.getBySubnames(extractSubnames(text));        
       //  cut down objects for serialization
        List<Study> reducedStudies = new ArrayList<Study>(studies.size());
        for (Study study : studies) {
            reducedStudies.add(buildReduced(study, Arrays.asList("id", "shortTitleText"))
            );
        }
        return reducedStudies;    	        
    }   

    public List<HealthcareSiteInvestigator> matchSiteInvestigators(String text, int siteIndex, 
    		HttpServletRequest request) throws Exception{
    	Study study = (Study) getCommandOnly(request);
    	int siteId = study.getStudySites().get(siteIndex).getSite().getId();
    	List<HealthcareSiteInvestigator> inv = healthcareSiteInvestigatorDao
        	.getBySubnames(extractSubnames(text), siteId);
        List<HealthcareSiteInvestigator> reducedInv = new ArrayList<HealthcareSiteInvestigator>(inv.size());
        for (HealthcareSiteInvestigator hcInv : inv) {
        	reducedInv.add(buildReduced(hcInv, Arrays.asList("id", "investigator"))
            );
        }
        
        return reducedInv;
    }
    
    public List<StudyPersonnel> matchStudyPersonnels(String text, int siteIndex, 
    		HttpServletRequest request) throws Exception{
    	Study study = (Study) getCommandOnly(request);
    	int siteId = study.getStudySites().get(siteIndex).getSite().getId();
    	List<StudyPersonnel> personnel = studyPersonnelDao.getBySubnames(extractSubnames(text), siteId);
        List<StudyPersonnel> reducedPersonnel = new ArrayList<StudyPersonnel>(personnel.size());
        for (StudyPersonnel hcInv : personnel) {
        	reducedPersonnel.add(buildReduced(hcInv, Arrays.asList("id", "researchStaff"))
            );
        }
        
        return reducedPersonnel;
    }

    public List<ResearchStaff> matchResearchStaffs(String text, 
    		HttpServletRequest request) throws Exception{
    	List<ResearchStaff> staffCol = researchStaffDao.getBySubnames(extractSubnames(text));
        List<ResearchStaff> reducedStaffCol = new ArrayList<ResearchStaff>(staffCol.size());
        for (ResearchStaff staff : staffCol) {
        	reducedStaffCol.add(buildReduced(staff, Arrays.asList("id", "firstName","lastName"))
            );
        }
        
        return reducedStaffCol;
    }
    private boolean onStudy(Study study, Integer studyId) {
        boolean onStudy = false;
        for (StudySite studySite : study.getStudySites()) {
            for (StudyParticipantAssignment assignment : studySite.getStudyParticipantAssignments()) {
                if (assignment.getParticipant().getId().equals(studyId)) {
                    onStudy = true;
                    break;
                }
            }
        }
        return onStudy;
    }

    private final Object getCommandOnly(HttpServletRequest request) throws Exception {		
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new HttpSessionRequiredException("Must have session when trying to bind (in session-form mode)");
		}
		String formAttrName = getFormSessionAttributeName();
		Object sessionFormObject = session.getAttribute(formAttrName);
		if (sessionFormObject == null) {
			throw new HttpSessionRequiredException("Form object not found in session (in session-form mode)");
		}

		return sessionFormObject;
	}
    
    private String getFormSessionAttributeName() {
		return "edu.duke.cabig.c3pr.web.CreateStudyController.FORM.command";
	}
    private String[] extractSubnames(String text) {
        return text.split("\\s+");
    } 

    ////// CONFIGURATION

    @Required
    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
		return healthcareSiteInvestigatorDao;
	}

	public void setHealthcareSiteInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
		this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
	}

	public StudyPersonnelDao getStudyPersonnelDao() {
		return studyPersonnelDao;
	}

	public void setStudyPersonnelDao(StudyPersonnelDao studyPersonnelDao) {
		this.studyPersonnelDao = studyPersonnelDao;
	}

	public ResearchStaffDao getResearchStaffDao() {
		return researchStaffDao;
	}

	public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
		this.researchStaffDao = researchStaffDao;
	}
    
     
}