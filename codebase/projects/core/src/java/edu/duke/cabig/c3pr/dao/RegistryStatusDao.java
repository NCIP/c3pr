/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.duke.cabig.c3pr.domain.Reason;
import edu.duke.cabig.c3pr.domain.RegistryStatus;

/**
 * Hibernate implementation of ArmDao
 * 
 * @see RegistryStatusDao
 * @author Priyatam
 */
public class RegistryStatusDao extends GridIdentifiableDao<RegistryStatus> {

    @Override
    public Class<RegistryStatus> domainClass() {
        return RegistryStatus.class;
    }

    public List<RegistryStatus> getAll() {
        return getHibernateTemplate().find("from RegistryStatus");
    }
    
	public RegistryStatus getRegistryStatusByCode(String code) {
		final List results = getHibernateTemplate().find(
				"from RegistryStatus where code = ?", code);
		if (CollectionUtils.isEmpty(results)) {
			return null;
		} else {
			return (RegistryStatus) results.get(0);
		}
	}
}
