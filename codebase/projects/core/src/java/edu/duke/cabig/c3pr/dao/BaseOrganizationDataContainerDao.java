/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.BaseOrganizationDataContainer;

@Transactional(readOnly = true)
public class BaseOrganizationDataContainerDao extends GridIdentifiableDao<BaseOrganizationDataContainer>{

	@Override
	public Class<BaseOrganizationDataContainer> domainClass() {
		return BaseOrganizationDataContainer.class;
	}
	
    @Transactional(readOnly = false)
	public void save(BaseOrganizationDataContainer org){
		getHibernateTemplate().saveOrUpdate(org);
	}
	
    @SuppressWarnings("unchecked")
	public BaseOrganizationDataContainer getByName(final String name) {
        List<BaseOrganizationDataContainer> results = getHibernateTemplate().find("from BaseOrganizationDataContainer where name= ?",
                        name);
        return results.size() > 0 ? results.get(0) : null;
    }
}
