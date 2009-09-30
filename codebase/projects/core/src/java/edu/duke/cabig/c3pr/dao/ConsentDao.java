package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.Consent;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

public class ConsentDao extends GridIdentifiableDao<Consent> implements MutableDomainObjectDao<Consent> {
	
	@Override
	public Class<Consent> domainClass() {
		return Consent.class ;
	}

	public void save(Consent consent) {
		super.save(consent);
		
	}

}
