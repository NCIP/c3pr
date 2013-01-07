package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.OffEpochReason;

/**
 * Hibernate implementation of ScheduledEpoch
 * 
 * @see edu.duke.cabig.c3pr.dao.ScheduledEpoch
 * @author Priyatam
 */
public class OffEpochReasonDao extends GridIdentifiableDao<OffEpochReason> {

    @Override
    public Class<OffEpochReason> domainClass() {
        return OffEpochReason.class;
    }
}
