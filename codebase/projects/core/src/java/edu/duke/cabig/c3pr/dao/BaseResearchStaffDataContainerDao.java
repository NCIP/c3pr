/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.BaseResearchStaffDataContainer;

@Transactional(readOnly = true)
public class BaseResearchStaffDataContainerDao extends GridIdentifiableDao<BaseResearchStaffDataContainer>{

	@Override
	public Class<BaseResearchStaffDataContainer> domainClass() {
		return BaseResearchStaffDataContainer.class;
	}

    @Transactional(readOnly = false)
	public void save(BaseResearchStaffDataContainer baseResearchStaffDataContainer){
		getHibernateTemplate().saveOrUpdate(baseResearchStaffDataContainer);
	}
	
    @SuppressWarnings("unchecked")
	public BaseResearchStaffDataContainer getByEmailAddress(String email) {
        List<BaseResearchStaffDataContainer> results = getHibernateTemplate().find("from BaseResearchStaffDataContainer rs where rs.contactMechanisms.value = '" +email+ "'");
        return results.size() > 0 ? results.get(0) : null;
    }
    
    	/**
	 * Initialize.
	 *
	 * @param baseResearchStaffDataContainer the research staff
	 */
	public void initialize(BaseResearchStaffDataContainer baseResearchStaffDataContainer){
		getHibernateTemplate().initialize(baseResearchStaffDataContainer.getContactMechanisms());
	}
	
	@Override
	public BaseResearchStaffDataContainer getById(int id) {
		BaseResearchStaffDataContainer baseResearchStaffDataContainer =  super.getById(id);
		baseResearchStaffDataContainer.getBaseOrganizationDataContainers().size();
		baseResearchStaffDataContainer.getContactMechanisms().size();
		return baseResearchStaffDataContainer;
	}
}

