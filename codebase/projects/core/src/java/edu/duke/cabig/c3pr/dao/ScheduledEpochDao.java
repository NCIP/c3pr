package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.ScheduledEpoch;

/**
 * Hibernate implementation of ScheduledEpoch
 * 
 * @see edu.duke.cabig.c3pr.dao.ScheduledEpoch
 * @author Priyatam
 */
public class ScheduledEpochDao extends GridIdentifiableDao<ScheduledEpoch> {

    @Override
    public Class<ScheduledEpoch> domainClass() {
        return ScheduledEpoch.class;
    }
}
