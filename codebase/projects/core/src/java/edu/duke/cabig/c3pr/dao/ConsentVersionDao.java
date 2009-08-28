package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.ConsentVersion;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

public class ConsentVersionDao extends GridIdentifiableDao<ConsentVersion> implements MutableDomainObjectDao<ConsentVersion> {

	@Override
	public Class<ConsentVersion> domainClass() {
		return ConsentVersion.class ;
	}

	public void save(ConsentVersion consentVersion) {
		getHibernateTemplate().saveOrUpdate(consentVersion);
	}

}
