package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.dao.DataAccessException;

import com.semanticbits.coppa.infrastructure.RemoteSession;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.domain.SiteInvestigatorGroupAffiliation;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.nwu.bioinformatics.commons.CollectionUtils;

/**
 * @author Priyatam
 */
public class InvestigatorDao extends GridIdentifiableDao<Investigator> {

    private static Log log = LogFactory.getLog(InvestigatorDao.class);
    
    /** The remote session. */
    private RemoteSession remoteSession;
    
    private HealthcareSiteDao healthcareSiteDao;
    
    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
    

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
            
            if (inv.getHealthcareSiteInvestigators() != null && inv.getHealthcareSiteInvestigators().get(0).getHealthcareSite() != null) {
            	Criteria healthcareSiteInvestigatorCriteria = orgCriteria.createCriteria("healthcareSiteInvestigatorsInternal");
                Criteria healthcareSiteCriteria = healthcareSiteInvestigatorCriteria.createCriteria("healthcareSite");
            	healthcareSiteCriteria.add(Expression.eq("id", inv.getHealthcareSiteInvestigators().get(0).getHealthcareSite().getId() ));
            }
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
    	remoteInvestigator.getHealthcareSiteInvestigators().addAll(investigator.getHealthcareSiteInvestigators());
    	remoteInvestigator.setLastName(investigator.getLastName());
    	remoteInvestigator.setNciIdentifier(investigator.getNciIdentifier());
    	return remoteInvestigator;
    }

    /*
     * Gets from the database only.
     */
    public Investigator getByEmailAddressFromLocal(String emailAddress) {
    	//Now that the remote inv's are in the db. Search the db.
        return CollectionUtils.firstElement((List<Investigator>) 
        		getHibernateTemplate().find("from Investigator i where i.contactMechanisms.value = '" +emailAddress+ "'"));
    }
    
    
    /* Gets from COPPA and the database
     * Created for the notifications use case.
     */
    public Investigator getByEmailAddress(String emailAddress) {
    	
    	Investigator investigator = getByEmailAddressFromLocal(emailAddress);
    	if(investigator != null){
    		//if a inv with this email is in our db then no need to check with coppa...as  it will be ignored eventually
    		//(as the email address is already in our db)
    		return investigator;
    	} else {
    		//First fetch the remote Inv's
        	RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
        	remoteInvestigator.setEmail(emailAddress);
        	getRemoteInvestigatorsAndUpdateDatabase(remoteInvestigator);
        	
        	//Now that the remote inv's are in the db. Search the db.
            return CollectionUtils.firstElement((List<Investigator>) 
            		getHibernateTemplate().find("from Investigator i where i.contactMechanisms.value = '" +emailAddress+ "'"));
    	}
    }
    
    /**Only looks in the database
     * @param nciIdentifier
     * @return
     */
    public Investigator getByNciIdentifierFromLocal(String nciIdentifier) {
    	if(nciIdentifier!= null){
    		return CollectionUtils.firstElement((List<Investigator>) getHibernateTemplate().find(
                        "from Investigator i where i.nciIdentifier = ?", nciIdentifier));
    	}
    	return null;
    }
    
    /**Looks in Coppa and then the database
     * @param nciIdentifier
     * @return
     */
    public Investigator getByNciIdentifier(String nciIdentifier) {
    	
    	Investigator investigator = getByNciIdentifierFromLocal(nciIdentifier);
    	if(investigator != null){
    		return investigator;
    	} else {
        	//First fetch the remote Inv's
        	RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
        	remoteInvestigator.setNciIdentifier(nciIdentifier);
        	getRemoteInvestigatorsAndUpdateDatabase(remoteInvestigator);
        	
        	//Now that the remote inv's are in the db. Search the db.
            return CollectionUtils.firstElement((List<Investigator>) getHibernateTemplate().find(
                            "from Investigator i where i.nciIdentifier = ?", nciIdentifier));
    	}
    	
    }
    
    
    /**
     * Gets the remote INV  from the resolver and updates the db.
     * 
     * @param remoteInvestigator the remote Investigator
     * @return the research staff by organization nci institute code
     */
    private void getRemoteInvestigatorsAndUpdateDatabase(RemoteInvestigator remoteInvestigator){
    	List<Object> remoteInvestigatorsFromCoppa = remoteSession.find(remoteInvestigator);
    	
    	//Update the remoteOrgs if any
    	RemoteInvestigator tempRemoteInvestigator = null;
		List<HealthcareSite> remoteHealthcareSiteList = new ArrayList<HealthcareSite>(); 
		for(Object object: remoteInvestigatorsFromCoppa){
			tempRemoteInvestigator = (RemoteInvestigator)object;
			for(HealthcareSiteInvestigator hcsi: tempRemoteInvestigator.getHealthcareSiteInvestigators()){
				remoteHealthcareSiteList.add(hcsi.getHealthcareSite());	
			}
			healthcareSiteDao.updateDatabaseWithRemoteHealthcareSites(remoteHealthcareSiteList);
			//set the saved orgs in the hcsi
			for(int index = 0; index < tempRemoteInvestigator.getHealthcareSiteInvestigators().size(); index++){
				tempRemoteInvestigator.getHealthcareSiteInvestigators().get(index).setHealthcareSite(remoteHealthcareSiteList.get(index));
			}
			remoteHealthcareSiteList.clear();
		}
		
		RemoteInvestigator remoteInvestigator2 = null;
		Investigator investigator = null;
    	for(Object object: remoteInvestigatorsFromCoppa){
    		//Update the database with the remote investigator
    		remoteInvestigator2 = (RemoteInvestigator)object;
    		investigator = updateDatabaseWithRemoteContent(remoteInvestigator2);
        	
        	//Update the remote hcsi
        	for(int index = 0; index < investigator.getHealthcareSiteInvestigators().size(); index++){
        		//set the saved investigator in the hcsi
        		HealthcareSiteInvestigator healthcareSiteInvestigator = investigator.getHealthcareSiteInvestigators().get(index);
        		healthcareSiteInvestigator.setInvestigator(investigator);
        		healthcareSiteInvestigator = healthcareSiteInvestigatorDao.updateDatabaseWithRemoteContent(healthcareSiteInvestigator);
        		investigator.getHealthcareSiteInvestigators().set(index, healthcareSiteInvestigator);
        	}
    	}
    }
    
    /**
     * Update database with remote content.
     * Determines if remoteInv exists in our database or not and calls the corresponding method.
     * 
     * @param remoteResearchStaff the remote research staff
     */
    public Investigator updateDatabaseWithRemoteContent(RemoteInvestigator retrievedRemoteInvestigator){
    	//See if the retrieved remoteInvs already exist in our database.
		Investigator matchingRemoteInvestigatorFromDb = this.getByUniqueIdentifier(retrievedRemoteInvestigator.getExternalId());
		if(matchingRemoteInvestigatorFromDb == null ){
			// check the uniqueness of email and nci identifier of new investigator in database before saving him
			Investigator investigatorsWithMatchingEmail = null;
			investigatorsWithMatchingEmail = getByEmailAddressFromLocal(retrievedRemoteInvestigator.getEmail());
			
			Investigator investigatorsWithMatchingNCICode = null;
			investigatorsWithMatchingNCICode = getByNciIdentifierFromLocal(retrievedRemoteInvestigator.getNciIdentifier());
			
			if(investigatorsWithMatchingEmail != null){
				log.debug("This remote investigator : "	+ retrievedRemoteInvestigator.getFullName()
						+ "'s email id : " + retrievedRemoteInvestigator.getEmail()	+ " is already in the database.");
				//add the hcsi to the existing inv and return it.
//				updateHealthcareSites(investigatorsWithMatchingEmail, retrievedRemoteInvestigator);
				return investigatorsWithMatchingEmail;
			} else if(investigatorsWithMatchingNCICode != null){
				log.debug("This remote investigator : "	+ retrievedRemoteInvestigator.getFullName()
						+ "'s NCI Identifier: " + retrievedRemoteInvestigator.getNciIdentifier() + " is already in the database.");
				//add the hcsi to the existing inv and return it.
				updateHealthcareSites(investigatorsWithMatchingNCICode, retrievedRemoteInvestigator);
				return investigatorsWithMatchingNCICode;
			} else {
				buildAndSaveNewRemoteInvestigator(retrievedRemoteInvestigator);
			}
		} else {
			//we have the retrieved staff's Org in our db...link up with the same and persist
			//only update if remote investigator exists.
			if(matchingRemoteInvestigatorFromDb instanceof RemoteInvestigator){
				updateHealthcareSites(matchingRemoteInvestigatorFromDb, retrievedRemoteInvestigator);
				return matchingRemoteInvestigatorFromDb;
			}
		}
		return retrievedRemoteInvestigator;
	}

    private void updateHealthcareSites(Investigator investigatorToBeUpdated, Investigator investigatorToBeDiscarded){
    	for(HealthcareSiteInvestigator hcsi: investigatorToBeDiscarded.getHealthcareSiteInvestigators()){
    		if(!investigatorToBeUpdated.getHealthcareSiteInvestigators().contains(hcsi)){
    			investigatorToBeUpdated.getHealthcareSiteInvestigators().add(hcsi);
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
            HealthcareSite healthcareSiteByExternalId = null;
            
            String ctepCode = "";
            
            //for every hcs_inv in the remoteInv.....ensure the corresponding org is in the database and link it to the hcs_inv before saving the remoteInv.
            for(HealthcareSiteInvestigator healthcareSiteInvestigator: retrievedRemoteInvestigator.getHealthcareSiteInvestigators()){
                    ctepCode = healthcareSiteInvestigator.getHealthcareSite().getPrimaryIdentifier();
                    healthcareSite = healthcareSiteDao.getByPrimaryIdentifierFromLocal(ctepCode);
                    if(healthcareSiteInvestigator.getHealthcareSite() instanceof RemoteHealthcareSite){
                    	healthcareSiteByExternalId  = healthcareSiteDao.getByUniqueIdentifier(((RemoteHealthcareSite)healthcareSiteInvestigator.getHealthcareSite()).getExternalId());
                    }
                    
                    //The org related to the remoteInv does not exist...load it from COPPA and save it; then link it to the remoteInv
                    if(healthcareSite == null && healthcareSiteByExternalId == null){
                    	try {
							healthcareSiteDao.createGroupForOrganization(healthcareSiteInvestigator.getHealthcareSite());
							healthcareSiteDao.save(healthcareSiteInvestigator.getHealthcareSite());
						} catch (C3PRBaseRuntimeException e) {
							log.error(e.getMessage());
						} catch (C3PRBaseException e) {
							log.error(e.getMessage());
						}
                    } else {
                        //update the hcs_inv with the org 
                    	if(healthcareSiteByExternalId != null){
                    		healthcareSiteInvestigator.setHealthcareSite(healthcareSiteByExternalId);
                    	}
                    	if(healthcareSite != null){
                    		healthcareSiteInvestigator.setHealthcareSite(healthcareSite);
                    	}
                    }
            }
            //save the investigator explicitly.
            this.save(retrievedRemoteInvestigator);
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


	/**
     * This method queries the external system to fetch all the matching ResearchStaff
     * @param researchStaff
     * @return
     */
    public List<Investigator> getRemoteInvestigators(final Investigator investigator){
    	Investigator searchCriteria = new RemoteInvestigator();
    	searchCriteria.setFirstName(investigator.getFirstName());
    	searchCriteria.setLastName(investigator.getLastName());
    	searchCriteria.setEmail(investigator.getEmail());
    	List<Investigator> remoteInvestigators = (List)remoteSession.find(searchCriteria); 
    	return remoteInvestigators;
    }
    
	public void initialize(Investigator inv) {
		for(HealthcareSiteInvestigator healthcareSiteInvestigator:inv.getHealthcareSiteInvestigators()){
			getHibernateTemplate().initialize(healthcareSiteInvestigator);
			for(SiteInvestigatorGroupAffiliation siteInvestigatorGroupAffiliation: 
						healthcareSiteInvestigator.getSiteInvestigatorGroupAffiliations()){
				getHibernateTemplate().initialize(siteInvestigatorGroupAffiliation);
			}
			for(StudyInvestigator studyInvestigator: 
				healthcareSiteInvestigator.getStudyInvestigators()){
				getHibernateTemplate().initialize(studyInvestigator);
			}
		}
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
	
	public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
		return healthcareSiteInvestigatorDao;
	}

	public void setHealthcareSiteInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
		this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
	}

}
