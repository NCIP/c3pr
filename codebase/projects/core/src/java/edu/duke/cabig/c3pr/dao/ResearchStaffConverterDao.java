package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.ConverterResearchStaff;

@Transactional(readOnly = true)
public class ResearchStaffConverterDao extends GridIdentifiableDao<ConverterResearchStaff>{

	@Override
	public Class<ConverterResearchStaff> domainClass() {
		return ConverterResearchStaff.class;
	}

    @Transactional(readOnly = false)
	public void save(ConverterResearchStaff converrterResearchStaff){
		getHibernateTemplate().saveOrUpdate(converrterResearchStaff);
	}
	
    @SuppressWarnings("unchecked")
	public ConverterResearchStaff getByEmailAddress(String email) {
        List<ConverterResearchStaff> results = getHibernateTemplate().find("from ConverterResearchStaff rs where rs.contactMechanisms.value = '" +email+ "'");
        return results.size() > 0 ? results.get(0) : null;
    }
}

