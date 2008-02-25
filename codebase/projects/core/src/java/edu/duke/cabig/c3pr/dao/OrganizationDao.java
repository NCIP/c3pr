package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.dao.DataAccessResourceFailureException;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Organization;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

/**
 * @author Vinay Gangoli
 * @version 1.0
 */
public class OrganizationDao extends GridIdentifiableDao<HealthcareSite> implements MutableDomainObjectDao<HealthcareSite> {
	
	private static Log log = LogFactory.getLog(StudyDao.class);
	
	public Class<HealthcareSite> domainClass() {
	        return HealthcareSite.class;
	 }
	
	/*
	 * Returns all Organizations objects
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.OrganizationDao#getAll()
	 */
	 public List<HealthcareSite> getAll() {
		 return getHibernateTemplate().find("from HealthcareSite");
	 }
	 
   public List<HealthcareSite> searchByExample(HealthcareSite hcs, boolean isWildCard) {
        List<HealthcareSite> result = new ArrayList<HealthcareSite>();
        Example example = Example.create(hcs).excludeZeroes().ignoreCase();
        try {
            Criteria orgCriteria = getSession().createCriteria(Organization.class);
            orgCriteria.addOrder(Order.asc("name"));
            orgCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            
            if (isWildCard)
            {
                example.enableLike(MatchMode.ANYWHERE);
                orgCriteria.add(example);
                if(orgCriteria.list().size()> 30){
                	result =  orgCriteria.list().subList(0, 30);
                } else {
                	result =  orgCriteria.list();
                }
            }else{
            	result =  orgCriteria.add(example).list();
            }
            
        } catch (DataAccessResourceFailureException e) {
            log.error(e.getMessage());
        } catch (IllegalStateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (HibernateException e) {
            log.error(e.getMessage());
        }
        return result;
    }
   
   
   public List<HealthcareSite> getByNciIdentifier(String nciIdentifier){
	   
	   List<HealthcareSite> result = new ArrayList<HealthcareSite>();
	   HealthcareSite hcs = new HealthcareSite();
	   hcs.setNciInstituteCode(nciIdentifier);
       Example example = Example.create(hcs).excludeZeroes().ignoreCase();
       try{
    	   Criteria orgCriteria = getSession().createCriteria(Organization.class);
    	   result =  orgCriteria.add(example).list();
       }catch (DataAccessResourceFailureException e) {
           log.error(e.getMessage());
       } catch (IllegalStateException e) {
           e.printStackTrace();  
       } catch (HibernateException e) {
           log.error(e.getMessage());
       }
       return result;
   }
   
	 
	public void reassociate(HealthcareSite p) {
        getHibernateTemplate().lock(p,LockMode.NONE);
     }
	
   public void save(HealthcareSite obj) {
		getHibernateTemplate().saveOrUpdate(obj);
	}
}


