package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.emory.mathcs.backport.java.util.Collections;
import edu.nwu.bioinformatics.commons.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Priyatam
 */
public class InvestigatorDao extends GridIdentifiableDao<Investigator> {

    private static Log log = LogFactory.getLog(InvestigatorDao.class);
    private static final List<String> SUBSTRING_MATCH_PROPERTIES
            = Arrays.asList("firstName", "lastName");
    private static final List<String> EXACT_MATCH_PROPERTIES
            = Collections.emptyList();

    public Class<Investigator> domainClass() {
        return Investigator.class;
    }

    /**
     * Get All Investigators
     *
     * @return
     * @throws DataAccessException
     */
    public List<Investigator> getAll() throws DataAccessException {
        return getHibernateTemplate().find("from Investigator");
    }
    
    public Investigator getLoadedInvestigatorById(int id){
    	Investigator inv = (Investigator)getHibernateTemplate().get(domainClass(), id);
    	for(HealthcareSiteInvestigator hcsInv :inv.getHealthcareSiteInvestigators()){
    		hcsInv.getSiteInvestigatorGroupAffiliations().size();
    		hcsInv.getStudyInvestigators().size();
    	}
    	return inv;
    }
    
    public List<Investigator> getBySubnames(String[] subnames) {
        return findBySubname(subnames,
                SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }

    public List<Investigator> searchByExample(Investigator inv, boolean isWildCard) {
        List<Investigator> result = new ArrayList<Investigator>();

        Example example = Example.create(inv).excludeZeroes().ignoreCase();
        try {
            Criteria orgCriteria = getSession().createCriteria(Investigator.class);
            orgCriteria.addOrder(Order.asc("nciIdentifier"));
            orgCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

            if (isWildCard) {
                example.enableLike(MatchMode.ANYWHERE);
                orgCriteria.add(example);
                result = orgCriteria.list();
            } else {
                result = orgCriteria.add(example).list();
            }

        } catch (DataAccessResourceFailureException e) {
            log.error(e.getMessage());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (HibernateException e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public Investigator getByNciInstituteCode(String nciIdentifier) {
        return CollectionUtils.firstElement((List<Investigator>) getHibernateTemplate().
                find("from Investigator i where i.nciIdentifier = ?", nciIdentifier)
        );
    }


	   
}
