/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.EndPoint;
import gov.nih.nci.cabig.ctms.domain.DomainObject;

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
     * Returns all Endpoint objects (non-Javadoc)
     * 
     */
    public List<EndPoint> getAll() {
        return getHibernateTemplate().find("from EndPoint");
    }
    
    @Override
    public void save(DomainObject domainObject) {
    	// TODO Auto-generated method stub
    	super.save(domainObject);
    	getHibernateTemplate().initialize(((EndPoint)domainObject).getErrors());
    }
}
