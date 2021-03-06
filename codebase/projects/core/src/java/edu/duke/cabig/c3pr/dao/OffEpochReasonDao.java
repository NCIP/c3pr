/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
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
