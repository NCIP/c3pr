package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;

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

    @Transactional(readOnly = false)
    public void reassociate(Epoch epoch) {
        getHibernateTemplate().lock(epoch, LockMode.NONE);
    }

    @Transactional(readOnly = false)
    public void initialize(Epoch epoch) throws DataAccessException {
        getHibernateTemplate().initialize(epoch.getArmsInternal());
        getHibernateTemplate().initialize(epoch.getEligibilityCriteriaInternal());
        getHibernateTemplate().initialize(epoch.getStratificationCriteriaInternal());
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
                getHibernateTemplate().initialize(
                                stratumGroup.getBookRandomizationEntryInternal());
                getHibernateTemplate()
                                .initialize(
                                                stratumGroup
                                                                .getStratificationCriterionAnswerCombinationInternal());
            }
        }
    }
}
