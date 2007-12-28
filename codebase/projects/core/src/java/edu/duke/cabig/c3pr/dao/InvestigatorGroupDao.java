package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.domain.InvestigatorGroup;

/**
 * @author Priyatam
 */
public class InvestigatorGroupDao extends GridIdentifiableDao<InvestigatorGroup> {

    private static Log log = LogFactory.getLog(InvestigatorGroupDao.class);


    public Class<InvestigatorGroup> domainClass() {
        return InvestigatorGroup.class;
    }

    /**
     * Get All InvestigatorGroups
     * @throws DataAccessException
     */
    public List<InvestigatorGroup> getAll() throws DataAccessException {
        return getHibernateTemplate().find("from InvestigatorGroup");
    }
	   
}
