package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;

/**
 * @author Priyatam
 */
public class StudyAjaxFacade {
    private StudyDao studyDao;       
    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

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
    

    public List<HealthcareSiteInvestigator> matchSiteInvestigators(String text, int siteId) {
        List<HealthcareSiteInvestigator> inv = healthcareSiteInvestigatorDao
        	.getBySubnames(extractSubnames(text), siteId);
        List<HealthcareSiteInvestigator> reducedInv = new ArrayList<HealthcareSiteInvestigator>(inv.size());
        for (HealthcareSiteInvestigator hcInv : inv) {
        	reducedInv.add(buildReduced(hcInv, Arrays.asList("id", "investigator"))
            );
        }
        
        return reducedInv;
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
    
     
}