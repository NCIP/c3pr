/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.BaseInvestigatorDataContainer;

@Transactional(readOnly = true)
public class BaseInvestigatorDataContainerDao extends GridIdentifiableDao<BaseInvestigatorDataContainer>{

	@Override
	public Class<BaseInvestigatorDataContainer> domainClass() {
		return BaseInvestigatorDataContainer.class;
	}

    @Transactional(readOnly = false)
	public void save(BaseInvestigatorDataContainer baseInvestigatorDataContainer){
		getHibernateTemplate().saveOrUpdate(baseInvestigatorDataContainer);
	}
	
    @SuppressWarnings("unchecked")
	public BaseInvestigatorDataContainer getByEmailAddress(String email) {
        List<BaseInvestigatorDataContainer> results = getHibernateTemplate().find("from BaseInvestigatorDataContainer ci where ci.contactMechanisms.value = '" +email+ "'");
        return results.size() > 0 ? results.get(0) : null;
    }
    
    @Override
	public BaseInvestigatorDataContainer getById(int id) {
    	BaseInvestigatorDataContainer baseInvestigatorDataContainer =  super.getById(id);
		baseInvestigatorDataContainer.getContactMechanisms().size();
		return baseInvestigatorDataContainer;
	}
}

