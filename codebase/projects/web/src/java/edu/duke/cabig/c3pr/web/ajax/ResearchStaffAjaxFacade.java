/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteStudy;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;

/**
 * @author Priyatam, Vinay G
 */
public class ResearchStaffAjaxFacade {
    private HealthcareSiteDao healthcareSiteDao;
    private StudyDao studyDao;
    public void setPersonUserDao(PersonUserDao personUserDao) {
		this.personUserDao = personUserDao;
	}

	private StudySiteDao studySiteDao;
    private PersonUserDao personUserDao;

    private static Log log = LogFactory.getLog(ResearchStaffAjaxFacade.class);

    /**
     * Builds the reduced version of the passed in objects in order to pas a light weight version to the UI.
     * Used for auto-completers in general.
     *
     * @param <T> the generic type
     * @param src the src
     * @param properties the properties
     * @return the t
     */
    @SuppressWarnings("unchecked")
    private <T> T buildReduced(T src, List<String> properties) {
        T dst = null;
        try {
            // it doesn't seem like this cast should be necessary
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
            destination.setPropertyValue(property, source.getPropertyValue(property));
        }
        return dst;
    }

    /**
     * Match healthcare sites. Used for the site auto-completer
     *
     * @param text the text
     * @return the list
     * @throws Exception the exception
     */
    public List<HealthcareSite> matchHealthcareSites(String text) throws Exception {

        List<HealthcareSite> healthcareSites = healthcareSiteDao
                        .getBySubnames(extractSubnames(text));
        List<HealthcareSite> reducedHealthcareSites = new ArrayList<HealthcareSite>(healthcareSites
                        .size());
        for (HealthcareSite healthcareSite : healthcareSites) {
        	if(healthcareSite instanceof RemoteHealthcareSite){
        		reducedHealthcareSites.add(buildReduced(healthcareSite, Arrays.asList("id", "name",
        				"identifiersAssignedToOrganization", "externalId")));
        	}
        	else {reducedHealthcareSites.add(buildReduced(healthcareSite, Arrays.asList("id", "name",
        			"identifiersAssignedToOrganization")));
        	}
        }
        return reducedHealthcareSites;
    }

    /**
     * Match studies. Used for the study auto-completer
     *
     * @param text the text
     * @return the list
     * @throws Exception the exception
     */
    public List<Study> matchStudies(String text) throws Exception {

        List<Study> studies = studyDao.getBySubnames(extractSubnames(text));
        List<Study> reducedStudies = new ArrayList<Study>(studies.size());
        for (Study study : studies) {
        	study.getStudyVersion();
        	if(study instanceof RemoteStudy){
        		reducedStudies.add(buildReduced(study, Arrays.asList("id", "shortTitleText",
        				"identifiers", "externalId")));
        	}
        	else {reducedStudies.add(buildReduced(study, Arrays.asList("id", "shortTitleText",
        			"identifiers")));
        	}
        }
        return reducedStudies;
    }
    
    /**
     * Match studies. Used for the study auto-completer
     *
     * @param text the text
     * @return the list
     * @throws Exception the exception
     */
    public List<PersonUser> matchPersonUsers(String text) throws Exception {

        List<PersonUser> personUsers = personUserDao.getBySubNameAndSubEmail(extractSubnames(text));
        List<PersonUser> reducedPersonUsers = new ArrayList<PersonUser>(personUsers.size());
        for (PersonUser personUser : personUsers) {
        	reducedPersonUsers.add(buildReduced(personUser, Arrays.asList("id", "firstName",
        				"lastName","assignedIdentifier")));
        }
        return reducedPersonUsers;
    }
    
    
    /**
     * Match studies for the given site primary identifiers. 
     * This is used to load only relevant studies in the study auto-completer on the user screen
     *
     * @param text the text
     * @param roleSites the role sites
     * @return the list
     * @throws Exception the exception
     */
    public List<Study> matchStudiesGivenSites(String text, String[] roleSites) throws Exception {
    	
    	log.debug("Fetching matching studies");
    	List<Study> studies = studyDao.getBySubnames(extractSubnames(text));
        List<Study> reducedStudies = new ArrayList<Study>(studies.size());
        
        boolean includeStudy = false;
        for (Study study : studies) {
        	//filter out studies which don't have passed in ctepCodes as studyOrgs
        	if(roleSites.length > 0){
        		for(StudySite ss: study.getStudySites()){
    	        	if(includeStudy){
    	        		break;
    	        	}
    	        	for(String primaryIdentifier: roleSites){
    	        		if(ss.getHealthcareSite().getPrimaryIdentifier().equalsIgnoreCase(primaryIdentifier)){
    	        			includeStudy = true;
    	        			break;
    	        		}
    	        	}
    	        }
        	} else {
        		includeStudy = true;
        	}
	        		
        	study.getStudyVersion();
        	if(includeStudy){
        		log.debug("Reducing for the auto-completer :" + study.getShortTitleText());
        		if(study instanceof RemoteStudy){
            		reducedStudies.add(buildReduced(study, Arrays.asList("id", "shortTitleText",
            				"identifiers", "externalId")));
            	}
            	else {reducedStudies.add(buildReduced(study, Arrays.asList("id", "shortTitleText",
            			"identifiers")));
            	}
        	} else {
        		log.debug("Excluding :" + study.getShortTitleText());
        	}
        	includeStudy = false;
        }
        return reducedStudies;
    }
    
    
    /**
     * Match study sites.
     *
     * @param shortTitleText the short title text
     * @return the list
     
    public List<StudySiteWrapper> matchStudySites(String shortTitleText){
    	List<StudySite> studySites = studySiteDao.getStudySitesByShortTitle(shortTitleText);
    	List<StudySiteWrapper> reducedStudySiteWrapperList = new ArrayList<StudySiteWrapper>(studySites.size());
    	StudySiteWrapper studySiteWrapper;
    	for (StudySite studySite : studySites) {
    		studySiteDao.initialize(studySite);
    		studySiteWrapper = new StudySiteWrapper();
    		studySiteWrapper.setId(studySite.getId());
    		studySiteWrapper.setShortTitleText(studySite.getStudy().getShortTitleText());
    		studySiteWrapper.setSiteName(studySite.getHealthcareSite().getName());
    		studySiteWrapper.setSitePrimaryIdentifier(studySite.getHealthcareSite().getPrimaryIdentifier());
    		reducedStudySiteWrapperList.add(studySiteWrapper);
        }
    	return reducedStudySiteWrapperList;
    }*/

    // //// CONFIGURATION
    private String[] extractSubnames(String text) {
        return text.split("\\s+");
    }

    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public StudySiteDao getStudySiteDao() {
		return studySiteDao;
	}

	public void setStudySiteDao(StudySiteDao studySiteDao) {
		this.studySiteDao = studySiteDao;
	}

}
