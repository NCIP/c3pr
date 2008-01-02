package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.domain.SiteInvestigatorGroupAffiliation;


/**
 * @author Ramakrishna
 */
public class InvestigatorGroupDao extends GridIdentifiableDao<InvestigatorGroup> {

	public Class<InvestigatorGroup> domainClass() {
        return InvestigatorGroup.class;
    }

	@SuppressWarnings("unchecked")
    public List<InvestigatorGroup> getAll() throws DataAccessException {
        return getHibernateTemplate().find("from InvestigatorGroup");
    }
    
    @SuppressWarnings("unchecked")
    public List<SiteInvestigatorGroupAffiliation> getAffiliationsByGroupId(Integer parentId) throws DataAccessException {
        return getHibernateTemplate().find("from SiteInvestigatorGroupAffiliation siga where siga.investigatorGroup.id =?",
        		new Object[] { parentId });
    }
    
}
