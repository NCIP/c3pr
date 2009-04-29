package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.semanticbits.coppa.infrastructure.RemoteSession;

import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import gov.nih.nci.cabig.ctms.domain.DomainObject;

/**
 * @author Priyatam
 */
public class InvestigatorDao extends GridIdentifiableDao<Investigator> {

    private static Log log = LogFactory.getLog(InvestigatorDao.class);
    
    /** The remote session. */
    private RemoteSession remoteSession;
    
    private HealthcareSiteDao healthcareSiteDao;

    public Class<Investigator> domainClass() {
        return Investigator.class;
    }

    /**
     * Get All Investigators
     * 
     * @return
     * @throws DataAccessException
     */
    public List<Investigator> getAll() throws DataAccessException {
    	getRemoteInvestigatorsAndUpdateDatabase(new RemoteInvestigator());
        return getHibernateTemplate().find("from Investigator");
    }

    public Investigator getLoadedInvestigatorById(int id) {
        Investigator inv = (Investigator) getHibernateTemplate().get(domainClass(), id);
        for (HealthcareSiteInvestigator hcsInv : inv.getHealthcareSiteInvestigators()) {
            hcsInv.getSiteInvestigatorGroupAffiliations().size();
            hcsInv.getStudyInvestigators().size();
        }
        return inv;
    }

    public List<Investigator> searchByExample(Investigator inv, boolean isWildCard) {
        RemoteInvestigator remoteInvestigator = convertToRemoteInvestigatorForCoppaQuery(inv);
    	getRemoteInvestigatorsAndUpdateDatabase(remoteInvestigator);
    	
    	List<Investigator> result = new ArrayList<Investigator>();

        Example example = Example.create(inv).excludeZeroes().ignoreCase();
        try {
            Criteria orgCriteria = getSession().createCriteria(Investigator.class);
            orgCriteria.addOrder(Order.asc("nciIdentifier"));
            orgCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

            if (isWildCard) {
                example.enableLike(MatchMode.ANYWHERE);
                orgCriteria.add(example);
                result = orgCriteria.list();
            }
            else {
                result = orgCriteria.add(example).list();
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }
    
    public RemoteInvestigator convertToRemoteInvestigatorForCoppaQuery(Investigator investigator){
    	if(investigator instanceof RemoteInvestigator){
    		return (RemoteInvestigator)investigator;
    	}
    	RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
    	remoteInvestigator.setAddress(investigator.getAddress());
    	remoteInvestigator.setContactMechanisms(investigator.getContactMechanisms());
    	remoteInvestigator.setFirstName(investigator.getFirstName());
    	remoteInvestigator.setHealthcareSiteInvestigators(investigator.getHealthcareSiteInvestigators());
    	remoteInvestigator.setLastName(investigator.getLastName());
    	remoteInvestigator.setNciIdentifier(investigator.getNciIdentifier());
    	return remoteInvestigator;
    }

    /*
     * Gets from the database only.
     */
    public List<Investigator> getByEmailAddressFromLocal(String emailAddress) {
    	//Now that the remote inv's are in the db. Search the db.
        return getHibernateTemplate().find("from Investigator i where i.contactMechanisms.value = '" +emailAddress+ "'");
    }
    
    
    /* Gets from COPPA and the database
     * Created for the notifications use case.
     */
    public List<Investigator> getByEmailAddress(String emailAddress) {
    	//First fetch the remote Inv's
    	RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
    	ContactMechanism contactMechanism = new ContactMechanism();
		contactMechanism.setType(ContactMechanismType.EMAIL);
		contactMechanism.setValue(emailAddress);
		remoteInvestigator.addContactMechanism(contactMechanism);
		
    	getRemoteInvestigatorsAndUpdateDatabase(remoteInvestigator);
    	
    	//Now that the remote inv's are in the db. Search the db.
        return getHibernateTemplate().find("from Investigator i where i.contactMechanisms.value = '" +emailAddress+ "'");
    }
    
    /**Only looks in the database
     * @param nciIdentifier
     * @return
     */
    public List<Investigator> getByNciIdentifierFromLocal(String nciIdentifier) {
    	if(nciIdentifier!= null){
    		return ((List<Investigator>) getHibernateTemplate().find(
                        "from Investigator i where i.nciIdentifier = ?", nciIdentifier));
    	}
    	return new ArrayList<Investigator>();
    }
    
    /**Looks in Coppa and then the database
     * @param nciIdentifier
     * @return
     */
    public List<Investigator> getByNciIdentifier(String nciIdentifier) {
    	//First fetch the remote Inv's
    	RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
    	remoteInvestigator.setNciIdentifier(nciIdentifier);
    	getRemoteInvestigatorsAndUpdateDatabase(remoteInvestigator);
    	
    	//Now that the remote inv's are in the db. Search the db.
        return ((List<Investigator>) getHibernateTemplate().find(
                        "from Investigator i where i.nciIdentifier = ?", nciIdentifier));
    }
    
    
    /**
     * Gets the remote INV by organization nci institute code from the resolver and updates the db.
     * 
     * @param nciInstituteCode the nci institute code
     * @return the research staff by organization nci institute code
     */
    private void getRemoteInvestigatorsAndUpdateDatabase(RemoteInvestigator remoteInvestigator){
    	List<Object> remoteInvestigatorsFromCoppa = remoteSession.find(remoteInvestigator);
    	RemoteInvestigator retrievedRemoteInvestigator;
    	for(Object object: remoteInvestigatorsFromCoppa){
    		retrievedRemoteInvestigator = (RemoteInvestigator)object;
			
    		//update the database with the remote content
        	updateDatabaseWithRemoteContent(retrievedRemoteInvestigator);
    	}
    }
    
    /**
     * Update database with remote content.
     * Determines if remoteInv exists in our database or not and calls the corresponding method.
     * 
     * @param remoteResearchStaff the remote research staff
     */
    private void updateDatabaseWithRemoteContent(RemoteInvestigator retrievedRemoteInvestigator){
    	//See if the retrieved remoteInvs already exist in our database.
		Investigator matchingRemoteInvestigatorFromDb = this.getByUniqueIdentifier(retrievedRemoteInvestigator.getExternalId());
		if(matchingRemoteInvestigatorFromDb == null ){
			buildAndSaveNewRemoteInvestigator(retrievedRemoteInvestigator);
		} else {
			//we have the retrieved staff's Org in our db...link up with the same and persist
			// only update if remote investigator exists
			if(matchingRemoteInvestigatorFromDb instanceof RemoteInvestigator){
				buildAndUpdateExistingRemoteInvestigator(retrievedRemoteInvestigator,(RemoteInvestigator) matchingRemoteInvestigatorFromDb);
			}
		}
	}

    /**In this case the remoteInv does not exist in our database.
     * Hence we need to save. Before saving we also handle the related orgs andthe hcs_inv links.
     *
     * @param retrievedRemoteInvestigator
     */
    public void buildAndSaveNewRemoteInvestigator(RemoteInvestigator retrievedRemoteInvestigator) {
            HealthcareSite healthcareSite = null;
            String nciInstituteCode = "";
            
            //for every hcs_inv in the remoteInv.....ensure the corresponding org is in the database and link it to the hcs_inv before saving the remoteInv.
            for(HealthcareSiteInvestigator healthcareSiteInvestigator: retrievedRemoteInvestigator.getHealthcareSiteInvestigators()){
                    nciInstituteCode = healthcareSiteInvestigator.getHealthcareSite().getNciInstituteCode();
                    healthcareSite = healthcareSiteDao.getByNciInstituteCodeFromLocal(nciInstituteCode);
                    //The org related to the remoteInv does not exist...load it from COPPA and save it; then link it to the remoteInv
                    if(healthcareSite == null){
                            //healthcareSite = healthcareSiteDao.getByNciInstituteCode(nciInstituteCode);
                            //if(healthcareSite != null){
                                    healthcareSiteDao.save(healthcareSiteInvestigator.getHealthcareSite());
                            //} else {
                                    //cannot find this org in COPPA either...abandoning this hcs_inv
                                    //log.error("Could not find org with nciInstitueCode: " +nciInstituteCode+ "in COPPA.");
                            //}
                    } else {
                            //update the hcs_inv with the org and save the investigator explicitly.
                            healthcareSiteInvestigator.setHealthcareSite(healthcareSite);
                            this.save(retrievedRemoteInvestigator);
                    }
            }
    }
	
    /**In this case the remoteInv does not exist in our database. Hence we need to update.
     * 
     * @param retrievedRemoteInvestigator
     * @param matchingRemoteInvestigatorFromDb
     */
    private void buildAndUpdateExistingRemoteInvestigator(RemoteInvestigator retrievedRemoteInvestigator, RemoteInvestigator matchingRemoteInvestigatorFromDb) {
		//currently we do not handle the hcs_inv changes in the merge flow.
    	this.merge(matchingRemoteInvestigatorFromDb);
	}


	/**
     * Gets the by unique identifier. Created for the remote Investigator use case.
     * returns null if no match is found.
     * 
     * @param emailAddress the email address
     * @return the Investigator List
     */
    public Investigator getByUniqueIdentifier(String uniqueIdentifier) {
    	List<Investigator> investigatorList = new ArrayList<Investigator>();
    	investigatorList.addAll(getHibernateTemplate().find("from Investigator inv where inv.externalId = '" +uniqueIdentifier+ "'"));
    	if(investigatorList.size() > 0){
    		return investigatorList.get(0);
    	}
        return null;
    }
    
    public Investigator merge(Investigator investigator) {
        Investigator mergedInvestigator = (Investigator) getHibernateTemplate().merge(investigator);
        getHibernateTemplate().flush();
        return mergedInvestigator;
    }
    
    /*
	 * Saves a domain object
	 */
	public void save(Investigator investigator) {
		getHibernateTemplate().saveOrUpdate(investigator);
		getHibernateTemplate().flush();
	}


	public RemoteSession getRemoteSession() {
		return remoteSession;
	}

	public void setRemoteSession(RemoteSession remoteSession) {
		this.remoteSession = remoteSession;
	}

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}
	
	/**
     * This method queries the external system to fetch all the matching ResearchStaff
     * @param researchStaff
     * @return
     */
    public List<Investigator> getRemoteInvestigators(final Investigator investigator){
    	Investigator searchCriteria = new RemoteInvestigator();
    	searchCriteria.setFirstName(investigator.getFirstName());
    	searchCriteria.setLastName(investigator.getLastName());
    	ContactMechanism emailContactMechanism = new ContactMechanism();
    	emailContactMechanism.setType(ContactMechanismType.EMAIL);
    	emailContactMechanism.setValue(investigator.getEmailAsString());
    	searchCriteria.addContactMechanism(emailContactMechanism);
    	List<Investigator> remoteInvestigators = (List)remoteSession.find(searchCriteria); 
    	return remoteInvestigators;
    }

}
