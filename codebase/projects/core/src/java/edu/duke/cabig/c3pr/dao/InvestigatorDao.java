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
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;

/**
 * @author Priyatam
 */
public class InvestigatorDao extends GridIdentifiableDao<Investigator> {

    private static Log log = LogFactory.getLog(InvestigatorDao.class);
    
    /** The remote session. */
    private RemoteSession remoteSession;

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
    	getRemoteInvestigatorFromResolverByExample(new RemoteInvestigator());
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
    	getRemoteInvestigatorFromResolverByExample(remoteInvestigator);
    	
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
     * Created for the notifications use case.
     */
    public List<Investigator> getByEmailAddress(String emailAddress) {
    	//First fetch the remote Inv's
    	RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
    	ContactMechanism contactMechanism = new ContactMechanism();
		contactMechanism.setType(ContactMechanismType.EMAIL);
		contactMechanism.setValue(emailAddress);
		remoteInvestigator.addContactMechanism(contactMechanism);
		
    	getRemoteInvestigatorFromResolverByExample(remoteInvestigator);
    	
    	//Now that the remote inv's are in the db. Search the db.
        return getHibernateTemplate().find("from Investigator i where i.contactMechanisms.value = '" +emailAddress+ "'");
    }
    
    public List<Investigator> getInvestigatorsByNciIdentifier(String nciIdentifier) {
    	//First fetch the remote Inv's
    	RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
    	remoteInvestigator.setNciIdentifier(nciIdentifier);
    	getRemoteInvestigatorFromResolverByExample(remoteInvestigator);
    	
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
    public List<RemoteInvestigator> getRemoteInvestigatorFromResolverByExample(RemoteInvestigator remoteInvestigator){
    	List<Object> objectList = remoteSession.find(remoteInvestigator);
    	List<RemoteInvestigator> remoteInvestigatorsList = new ArrayList<RemoteInvestigator>();
    	
    	RemoteInvestigator retrievedRemoteInvestigator;
    	for(Object object: objectList){
    		retrievedRemoteInvestigator = (RemoteInvestigator)object;
    		remoteInvestigatorsList.add(retrievedRemoteInvestigator);
    	}
    	//update the database with the remote content
    	updateDatabaseWithRemoteContent(remoteInvestigatorsList);
    	
    	return remoteInvestigatorsList;
    }
    
    /**
     * Update database with remote content.
     * 
     * @param remoteResearchStaffList the remote research staff list
     */
    private void updateDatabaseWithRemoteContent(List<RemoteInvestigator> remoteInvestigatorsList){
    	try {
			for (RemoteInvestigator remoteInvestigator: remoteInvestigatorsList) {
				List<RemoteInvestigator> remoteInvestigatorsFromDatabase = getByUniqueIdentifier(remoteInvestigator.getUniqueIdentifier());
				if(remoteInvestigatorsFromDatabase.size() > 0){
					//this guy already exists....update the database with the latest coppa data
					merge(remoteInvestigator);
				} else{
					save(remoteInvestigator);
				}
			}
			getHibernateTemplate().flush();
		} catch (DataAccessException e) {
			log.error(e.getMessage());
		}
	}
    
    /**
     * Gets the by unique identifier. Created for the remote Investigator use case.
     * 
     * @param emailAddress the email address
     * @return the Investigator List
     */
    public List<RemoteInvestigator> getByUniqueIdentifier(String uniqueIdentifier) {
    	List<RemoteInvestigator> investigatorList = new ArrayList<RemoteInvestigator>();
    	investigatorList.addAll(getHibernateTemplate().find("from RemoteInvestigator rs where rs.uniqueIdentifier = '" +uniqueIdentifier+ "'"));
        return investigatorList;
    }
    
    @Transactional(readOnly = false)
    public Investigator merge(Investigator investigator) {
        return (Investigator) getHibernateTemplate().merge(investigator);
    }

	public RemoteSession getRemoteSession() {
		return remoteSession;
	}

	public void setRemoteSession(RemoteSession remoteSession) {
		this.remoteSession = remoteSession;
	}

}
