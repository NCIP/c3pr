/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.Person;
import edu.duke.cabig.c3pr.domain.RemotePersonUser;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.tools.Configuration;

/**
 * @author Priyatam
 */
public class UserAjaxFacade {
    
	private PersonUserDao personUserDao;
	
	private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
	
	private Configuration configuration; 
	
	private static Log log = LogFactory.getLog(UserAjaxFacade.class);
	
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
            // only for nested props
            String[] individualProps = property.split("\\.");
            String temp = "";
            String myTemp = "";
            try {
	            for (int i = 0; i < individualProps.length - 1; i++) {
	                temp += (i != 0 ? "." : "") + individualProps[i];
	                Object o = source.getPropertyValue(temp);
	                if(temp.charAt(temp.length() -1) == ']'){
	                	myTemp = temp.substring(0,temp.length()-3);
	                	if(destination.getPropertyValue(myTemp) == null){
	                		destination.setPropertyValue(myTemp, ArrayList.class.newInstance().add(o.getClass().newInstance()) );
	                	}	
	                	if(((ArrayList)destination.getPropertyValue(myTemp)).size() == 0){
	                			((ArrayList)destination.getPropertyValue(myTemp)).add(o.getClass().newInstance());
	                	}
	                } else {
	                		if(destination.getPropertyValue(temp) == null){
	                			destination.setPropertyValue(temp, o.getClass().newInstance());
	                	}
	                }		
	            }
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
            // for single and nested props
            destination.setPropertyValue(property, source.getPropertyValue(property));
        }
        /*for (String property : properties) {
            destination.setPropertyValue(property, source.getPropertyValue(property));
        }*/
        return dst;
    }

    public List<Person> matchNameAndEmail(String text, String assignedIdentifier) throws Exception {
    	//getting the site of the logged in user
    	PersonUser rStaff = personUserDao.getByAssignedIdentifierFromLocal(assignedIdentifier);
//    	String nciInstituteCode = null;
    	//FIXME : Vinay Gangoli (Security code should take care of getting research staff associted to correct healthcare site.)
//    	if(rStaff != null){
//    		nciInstituteCode =  rStaff.getHealthcareSite().getPrimaryIdentifier();
//    	} else {
//    		//Defaulting to the hosting site...as no site was found for logged in user.
//    		//nciInstituteCode = this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);
//    		log.error("No organization associated to the user.");
//    	}
    	
    	List<PersonUser> researchStaffList = new ArrayList<PersonUser>(new LinkedHashSet<PersonUser> (personUserDao.getBySubNameAndSubEmail(extractSubnames(text))));
        List<HealthcareSiteInvestigator> hcsInvestigatorsList = new ArrayList<HealthcareSiteInvestigator>(new LinkedHashSet<HealthcareSiteInvestigator> (healthcareSiteInvestigatorDao.getBySubNameAndSubEmail(extractSubnames(text))));

        List<Investigator> investigatorsList = new ArrayList<Investigator>();
        for(HealthcareSiteInvestigator hcsi: hcsInvestigatorsList){
        	investigatorsList.add(hcsi.getInvestigator());
        }
        
        List<PersonUser> reducedResearchStaffList = new ArrayList<PersonUser>(researchStaffList.size());
        for (PersonUser researchStaff : researchStaffList) {
        	if(researchStaff instanceof RemotePersonUser){
        		reducedResearchStaffList.add(buildReduced(researchStaff, Arrays.asList("id", "firstName", "lastName", "email", "externalId")));
        	} else {
        		reducedResearchStaffList.add(buildReduced(researchStaff, Arrays.asList("id", "firstName", "lastName", "email")));
        	}
        	
        }        
        
        List<Investigator> reducedInvestigatorList = new ArrayList<Investigator>(investigatorsList.size());
        for (Investigator investigator : investigatorsList) {
        	reducedInvestigatorList.add(buildReduced(investigator, Arrays.asList("id", "firstName", "lastName","email")));
        }     
        
        List<Person> personList = new ArrayList<Person>(reducedResearchStaffList.size() + reducedInvestigatorList.size());
        
        for(int i=0; i < reducedResearchStaffList.size(); i++){
        	personList.add((Person)reducedResearchStaffList.get(i));
        }
        for(int i=0; i < reducedInvestigatorList.size(); i++){
        	personList.add((Person)reducedInvestigatorList.get(i));
        }
        
        return personList;
    }

    //CONFIGURATION
    private String[] extractSubnames(String text) {
        return text.split("\\s+");
    }

	public PersonUserDao getPersonUserDao() {
		return personUserDao;
	}

	public void setPersonUserDao(PersonUserDao personUserDao) {
		this.personUserDao = personUserDao;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
		return healthcareSiteInvestigatorDao;
	}

	public void setHealthcareSiteInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
		this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
	}

}
