package edu.duke.cabig.c3pr.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.semanticbits.coppa.infrastructure.RemoteSession;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;
import gov.nih.nci.cabig.ctms.domain.DomainObject;

/**
 * The Class OrganizationDao.
 * 
 * @author Vinay Gangoli
 * @version 1.0
 */
public class OrganizationDao extends GridIdentifiableDao<HealthcareSite> implements
                MutableDomainObjectDao<HealthcareSite> {

    private static Log log = LogFactory.getLog(StudyDao.class);

	/** The remote session. */
	private RemoteSession remoteSession;
	
    public Class<HealthcareSite> domainClass() {
        return HealthcareSite.class;
    }
    

    /* Saves a domain object
     * @see gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao#save(gov.nih.nci.cabig.ctms.domain.MutableDomainObject)
     */
    @Transactional(readOnly = false)
    public void save(HealthcareSite healthcareSite) {
    	if(healthcareSite instanceof RemoteHealthcareSite){
    		Object healthcareSiteObject = remoteSession.saveOrUpdate(healthcareSite);
    		if(healthcareSiteObject != null && healthcareSiteObject instanceof HealthcareSite){
    			getHibernateTemplate().saveOrUpdate((HealthcareSite)healthcareSite);
    		}
    	} else {
    		getHibernateTemplate().saveOrUpdate(healthcareSite);
    	}
    }
    
	/* Saves a domain object
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#merge(gov.nih.nci.cabig.ctms.domain.DomainObject)
     */
    @Transactional(readOnly = false)
	public Organization merge(DomainObject domainObject) {
    	if(domainObject instanceof RemoteHealthcareSite){
    		//domainObject = remoteSession.saveOrUpdate();
    	}
    	return (Organization)getHibernateTemplate().merge(domainObject);
	}

	public RemoteSession getRemoteSession() {
		return remoteSession;
	}

	public void setRemoteSession(RemoteSession remoteSession) {
		this.remoteSession = remoteSession;
	}

}
