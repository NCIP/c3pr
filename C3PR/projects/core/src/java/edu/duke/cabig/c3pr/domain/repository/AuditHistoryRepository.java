package edu.duke.cabig.c3pr.domain.repository;

import edu.duke.cabig.c3pr.dao.query.DataAuditEventQuery;
import gov.nih.nci.cabig.ctms.audit.domain.DataAuditEvent;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
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
	        String queryString = query.getQueryString();
	        logger.debug("query: " + queryString);
	        return (List<DataAuditEvent>) getHibernateTemplate().execute(new HibernateCallback() {

	            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
	                org.hibernate.Query hiberanteQuery = session.createQuery(query.getQueryString());
	                Map<String, Object> queryParameterMap = query.getParameterMap();
	                for (String key : queryParameterMap.keySet()) {
	                    Object value = queryParameterMap.get(key);
	                    hiberanteQuery.setParameter(key, value);

	                }
	                return hiberanteQuery.list();
	            }

	        });
	    }
}
