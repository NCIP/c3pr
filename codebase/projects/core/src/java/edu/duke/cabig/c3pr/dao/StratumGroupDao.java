package edu.duke.cabig.c3pr.dao;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.StratumGroup;

/**
 * Hibernate implementation of StudySiteDao
 * 
 * @see edu.duke.cabig.c3pr.dao.StudySiteDao
 * @author Priyatam
 */
public class StratumGroupDao extends GridIdentifiableDao<StratumGroup> {

    @Override
    public Class<StratumGroup> domainClass() {
        return StratumGroup.class;
    }
    
    @Transactional(readOnly = false)
    public StratumGroup merge(StratumGroup stratumGroup) {
        return (StratumGroup) getHibernateTemplate().merge(stratumGroup);
    }
}
