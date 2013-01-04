/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
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
