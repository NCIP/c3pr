/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Arm;

/**
 * Hibernate implementation of ArmDao
 * 
 * @see edu.duke.cabig.c3pr.dao.ArmDao
 * @author Priyatam
 */
public class ArmDao extends GridIdentifiableDao<Arm> {

    @Override
    public Class<Arm> domainClass() {
        return Arm.class;
    }

    /*
     * Returns all Arm objects (non-Javadoc)
     * 
     * @see edu.duke.cabig.c3pr.dao.Arm#getAll()
     */
    public List<Arm> getAll() {
        return getHibernateTemplate().find("from Arm");
    }
}
