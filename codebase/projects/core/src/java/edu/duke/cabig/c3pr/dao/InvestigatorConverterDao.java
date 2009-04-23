package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.ConverterInvestigator;

@Transactional(readOnly = true)
public class InvestigatorConverterDao extends GridIdentifiableDao<ConverterInvestigator>{

	@Override
	public Class<ConverterInvestigator> domainClass() {
		return ConverterInvestigator.class;
	}

    @Transactional(readOnly = false)
	public void save(ConverterInvestigator converterInvestigator){
		getHibernateTemplate().saveOrUpdate(converterInvestigator);
	}
	
    @SuppressWarnings("unchecked")
	public ConverterInvestigator getByEmailAddress(String email) {
        List<ConverterInvestigator> results = getHibernateTemplate().find("from ConverterInvestigator ci where ci.contactMechanisms.value = '" +email+ "'");
        return results.size() > 0 ? results.get(0) : null;
    }
}

