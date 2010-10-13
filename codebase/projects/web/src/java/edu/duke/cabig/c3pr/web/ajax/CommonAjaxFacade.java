package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Required;

import edu.duke.cabig.c3pr.dao.DiseaseTermDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ICD9DiseaseSiteDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;

/**
 * @author Himanshu Gupta
 */
public class CommonAjaxFacade {
    private StudyDao studyDao;

    private DiseaseTermDao diseaseTermDao;
    private HealthcareSiteDao healthcareSiteDao;
    private InvestigatorDao investigatorDao;
    private ICD9DiseaseSiteDao icd9DiseaseSiteDao ;

    private static Log log = LogFactory.getLog(CommonAjaxFacade.class);

    @SuppressWarnings("unchecked")
    private <T> T buildReduced(T src, List<String> properties) {
        T dst = null;
        try {
            dst = (T) src.getClass().newInstance();
        }
        catch (InstantiationException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        }

        BeanWrapper source = new BeanWrapperImpl(src);
        BeanWrapper destination = new BeanWrapperImpl(dst);
        for (String property : properties) {
            // only for nested props
            String[] individualProps = property.split("\\.");
            String temp = "";
            for (int i = 0; i < individualProps.length - 1; i++) {
                temp += (i != 0 ? "." : "") + individualProps[i];
                Object o = source.getPropertyValue(temp);
                if (destination.getPropertyValue(temp) == null) {
                    try {
                        destination.setPropertyValue(temp, o.getClass().newInstance());
                    }
                    catch (BeansException e) {
                        log.error(e.getMessage());
                    }
                    catch (InstantiationException e) {
                        log.error(e.getMessage());
                    }
                    catch (IllegalAccessException e) {
                        log.error(e.getMessage());
                    }
                }
            }
            destination.setPropertyValue(property, source.getPropertyValue(property));
        }
        return dst;
    }
    
    public List<HealthcareSite> matchHealthcareSites(String text) throws Exception {
        List<HealthcareSite> healthcareSites = healthcareSiteDao.getBySubnames(extractSubnames(text));
        List<HealthcareSite> reducedHealthcareSites = new ArrayList<HealthcareSite>(healthcareSites.size());
        for (HealthcareSite healthcareSite : healthcareSites) {
        	if(healthcareSite instanceof RemoteHealthcareSite){
        		reducedHealthcareSites.add(buildReduced(healthcareSite, Arrays.asList("id", "name", "externalId", "identifiersAssignedToOrganization", "ctepCode")));
        	}
        	else {
        		reducedHealthcareSites.add(buildReduced(healthcareSite, Arrays.asList("id", "name", "identifiersAssignedToOrganization",  "ctepCode")));
        	}
        }
        return reducedHealthcareSites;
    }

 
    public List<DiseaseTerm> matchDiseaseTerms(String text) {
        List<DiseaseTerm> diseaseTerms = diseaseTermDao.getBySubnames(extractSubnames(text));
        List<DiseaseTerm> reducedList = new ArrayList<DiseaseTerm>(diseaseTerms.size());
        for (DiseaseTerm diseaseTerm: diseaseTerms) {
        	reducedList .add(buildReduced(diseaseTerm, Arrays.asList("id", "ctepTerm")));
        }
        return reducedList;
    }
    
	public List<Investigator> matchInvestigators(String text) throws Exception {
		List<Investigator> investigators = investigatorDao.getBySubnames(extractSubnames(text));
		List<Investigator> reducedInvestigators = new ArrayList<Investigator>(investigators.size());
		for (Investigator investigator: investigators) {
			reducedInvestigators .add(buildReduced(investigator, Arrays.asList("id", "fullName", "assignedIdentifier")));
        }
        return reducedInvestigators;
	}
	
	public List<ICD9DiseaseSite> matchDiseaseSites(String text) {
	  List<ICD9DiseaseSite> anatomicSites = icd9DiseaseSiteDao.getBySubnames(extractSubnames(text));
	  List<ICD9DiseaseSite> reducedAnatomicSites = new ArrayList<ICD9DiseaseSite>();
	  for (ICD9DiseaseSite anatomicSite : anatomicSites) {
	  	if(anatomicSite.getSelectable()){
	  		reducedAnatomicSites.add(buildReduced(anatomicSite, Arrays.asList("id", "name","code")));
	  	}
	  }
	  return reducedAnatomicSites;
	}

    private String[] extractSubnames(String text) {
        return text.split("\\s+");
    }

    @Required
    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public DiseaseTermDao getDiseaseTermDao() {
        return diseaseTermDao;
    }

    public void setDiseaseTermDao(DiseaseTermDao diseaseTermDao) {
        this.diseaseTermDao = diseaseTermDao;
    }

    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

    public InvestigatorDao getInvestigatorDao() {
        return investigatorDao;
    }

    public void setInvestigatorDao(InvestigatorDao investigatorDao) {
        this.investigatorDao = investigatorDao;
    }

	public ICD9DiseaseSiteDao getICD9DiseaseSiteDao() {
		return icd9DiseaseSiteDao;
	}

	public void setICD9DiseaseSiteDao(ICD9DiseaseSiteDao icd9DiseaseSiteDao) {
		this.icd9DiseaseSiteDao = icd9DiseaseSiteDao;
	}

}
