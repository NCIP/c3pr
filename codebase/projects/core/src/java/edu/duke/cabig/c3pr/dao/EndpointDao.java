package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.EndPoint;

/**
 * Hibernate implementation of ArmDao
 * 
 * @see EndpointDao
 * @author Priyatam
 */
public class EndpointDao extends GridIdentifiableDao<EndPoint> {

    @Override
    public Class<EndPoint> domainClass() {
        return EndPoint.class;
    }

    /*
     * Returns all Arm objects (non-Javadoc)
     * 
     * @see edu.duke.cabig.c3pr.dao.Arm#getAll()
     */
    public List<EndPoint> getAll() {
        return getHibernateTemplate().find("from EndPoint");
    }
}
