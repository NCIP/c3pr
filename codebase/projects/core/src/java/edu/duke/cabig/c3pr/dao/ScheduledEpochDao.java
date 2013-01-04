/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
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
