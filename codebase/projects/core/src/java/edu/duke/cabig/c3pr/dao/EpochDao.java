/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratumGroup;

/**
 * Hibernate implementation of EpochDao
 * 
 * @see edu.duke.cabig.c3pr.dao.EpochDao
 * @author Priyatam
 */
public class EpochDao extends GridIdentifiableDao<Epoch> {

    @Override
    public Class<Epoch> domainClass() {
        return Epoch.class;
    }

    /*
     * Returns all Epoch objects (non-Javadoc)
     * 
     * @see edu.duke.cabig.c3pr.dao.Epoch#getAll()
     */
    public List<Epoch> getAll() {
        return getHibernateTemplate().find("from Epoch");
    }

    /**
     * Initialize.
     * 
     * @param epoch the epoch
     * 
     * @throws DataAccessException the data access exception
     */
    @Transactional(readOnly = false)
    public void initialize(Epoch epoch) throws DataAccessException {
        getHibernateTemplate().initialize(epoch.getArmsInternal());
        getHibernateTemplate().initialize(epoch.getEligibilityCriteria());
        getHibernateTemplate().initialize(epoch.getStratificationCriteriaInternal());
        if (epoch.getRandomization() != null
                        && epoch.getRandomization() instanceof BookRandomization) {
            getHibernateTemplate().initialize(
                            ((BookRandomization) epoch.getRandomization())
                                            .getBookRandomizationEntryInternal());
        }
        for (StratificationCriterion stratficationCriterion : epoch
                        .getStratificationCriteriaInternal()) {
            if (stratficationCriterion != null) {
                getHibernateTemplate().initialize(
                                stratficationCriterion.getPermissibleAnswersInternal());
            }
        }
        getHibernateTemplate().initialize(epoch.getStratumGroupsInternal());
        for (StratumGroup stratumGroup : epoch.getStratumGroupsInternal()) {
            if (stratumGroup != null) {
                getHibernateTemplate().initialize(stratumGroup.getBookRandomizationEntryInternal());
                getHibernateTemplate().initialize(
                                stratumGroup.getStratificationCriterionAnswerCombinationInternal());
            }
        }
    }
    
    @Transactional(readOnly = false)
    public Epoch merge(Epoch epoch) {
        return (Epoch) getHibernateTemplate().merge(epoch);
    }
}
