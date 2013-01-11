/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.repository;

import edu.duke.cabig.c3pr.dao.query.DataAuditEventQuery;
import gov.nih.nci.cabig.ctms.audit.domain.DataAuditEvent;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @todo vinay: remove it once we migrate to use .9-snapshot of ctms -commons-core
 * @author Vinay
 *
 */
@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
public class AuditHistoryRepository extends gov.nih.nci.cabig.ctms.audit.dao.AuditHistoryRepository{
	
	 @SuppressWarnings("unchecked")
	    public List<DataAuditEvent> findDataAuditEvents(final DataAuditEventQuery query) {
	     return super.findDataAuditEvents(query);
	    }
}
