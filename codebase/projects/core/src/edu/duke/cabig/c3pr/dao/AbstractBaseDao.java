package edu.duke.cabig.c3pr.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import edu.duke.cabig.c3pr.domain.DomainObject;
/**
 * @author Priyatam
 *
 */
public abstract class AbstractBaseDao<T extends DomainObject> extends HibernateDaoSupport implements BaseDao{
    public T getById(int id) {
        return (T) getHibernateTemplate().get(domainClass(), id);
    }
    public abstract Class<T> domainClass();
}
