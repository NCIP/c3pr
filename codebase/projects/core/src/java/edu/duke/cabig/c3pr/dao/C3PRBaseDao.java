package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.dao.AbstractDomainObjectDao;
import gov.nih.nci.cabig.ctms.domain.DomainObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract BaseDao implementing BaseDao. Provides convenient methods for saving a base Dao
 * 
 * @author Priyatam
 */
public abstract class C3PRBaseDao<T extends DomainObject> extends AbstractDomainObjectDao<T> {

	private static Log log = LogFactory.getLog(C3PRBaseDao.class);
	private int maxSearchResultsForAutocompleter ; 

	public int getMaxSearchResultsForAutocompleter() {
		return maxSearchResultsForAutocompleter;
	}

	public void setMaxSearchResultsForAutocompleter(
			int maxSearchResultsForAutocompleter) {
		this.maxSearchResultsForAutocompleter = maxSearchResultsForAutocompleter;
	}

	/*
	 * Must override in subclass to return the domain class reference in the dao
	 */
	public abstract Class<T> domainClass();

	/*
	 * Saves a domain object
	 */
	@Transactional(readOnly = false)
	public void save(DomainObject domainObject) {
		getHibernateTemplate().saveOrUpdate(domainObject);
		postProcessSave();
	}

	/*
	 * Saves a domain object
	 */
	@Transactional(readOnly = false)
	public DomainObject merge(DomainObject domainObject) {
		domainObject = (DomainObject)getHibernateTemplate().merge(domainObject);
		postProcessSave();
		return domainObject;
	}

	/**
	 * To be implemented by subclasses for custom extension of save
	 */
	protected void postProcessSave() {
		// default is empty implementation
	}

	/**
	 * A variation of {@link #findBySubname} that does not allow for extra conditions
	 */
	protected List<T> findBySubname(String[] subnames, List<String> substringMatchProperties,
			List<String> exactMatchProperties) {
		return findBySubname(subnames, null, null, substringMatchProperties, exactMatchProperties, null);
	}

	/**
	 *Overloading the othe findBysubnames by adding the domainname and domainidentities arrays.
	 *This can be used to query multiple tables when necessary.
	 *
	 *This is currently not used by anyone
	 *

	@SuppressWarnings("unchecked")
	protected List<T> findBySubname(String[] subnames, String extraConditions,
			List<Object> extraParameters, List<String> substringMatchProperties,
			List<String> exactMatchProperties, List<String> domainNames, List<String> domainIdentities) {
		StringBuilder query = new StringBuilder("from ").append(domainClass().getName()).append(
		" o ");
		for(int i=0; i<domainNames.size();i++){
			query.append(",").append(domainNames.get(i)).append(" ").append(domainIdentities.get(i)).append(" ");
		}
		List<Object> params = new LinkedList<Object>();
		if(extraConditions != null || extraParameters != null || subnames.length > 0){
			query.append("where ") ;
			if (extraConditions != null){
				query.append(extraConditions).append(" and ");
			}
			if (extraParameters != null) {
				params.addAll(extraParameters);
			}

			for (int i = 0; i < subnames.length; i++) {
				buildSubnameQuery(subnames[i], query, params, substringMatchProperties, exactMatchProperties);
				if (i < subnames.length - 1) query.append(" and ");
			}
		}

		log.debug("query string = " + query);
		this.getHibernateTemplate().setMaxResults(30);
		return (getHibernateTemplate()).find(query.toString(), params.toArray());
	}	 */

	/**
	 * A query builder for use by subclass DAOs. It makes it easy to match a fragment of a name or
	 * identifier against multiple properties. This is intended for use in implementing
	 * user-friendly dynamic searches; e.g., autocompleters.
	 * 
	 * @param subnames
	 *                the name fragments to search on
	 * @param extraConditions
	 *                custom HQL conditions with which to constrain the fragment matches
	 * @param extraParameters
	 *                parameters for the custom conditions
	 * @param substringMatchProperties
	 *                a list of properties of the implementing object which should be matched as
	 *                case-insensitive substrings
	 * @param exactMatchProperties
	 *                a list of properties which should be matched as case-insensitive full strings
	 * @return a list of matching domain object instances
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findBySubname(String[] subnames, String extraConditions,
			List<Object> extraParameters, List<String> substringMatchProperties,
			List<String> exactMatchProperties, String extraClasses) {
		StringBuilder query = new StringBuilder("select o from ").append(domainClass().getName()).append(
		" o ");
		if(!StringUtils.isEmpty(extraClasses)){
			query.append(" , " + extraClasses);
		}
		List<Object> params = new LinkedList<Object>();
		if(extraConditions != null || extraParameters != null || subnames.length > 0){
			query.append("where ") ;
			if (extraConditions != null){
				query.append(extraConditions).append(" and ");
			}
			if (extraParameters != null) {
				params.addAll(extraParameters);
			}

			for (int i = 0; i < subnames.length; i++) {
				buildSubnameQuery(subnames[i], query, params, substringMatchProperties, exactMatchProperties);
				if (i < subnames.length - 1) query.append(" and ");
			}
		}

		log.debug("query string = " + query);
		this.getHibernateTemplate().setMaxResults(getMaxSearchResultsForAutocompleter());
		return (getHibernateTemplate()).find(query.toString(), params.toArray());
	}

	
	protected void buildSubnameQuery(String subname, StringBuilder query, List<Object> params,
			List<String> substringMatchProperties, List<String> exactMatchProperties) {
		query.append('(');
		if (hasAny(substringMatchProperties)) {
			for (Iterator<String> it = substringMatchProperties.iterator(); it.hasNext();) {
				String prop = it.next();
				query.append("LOWER(o.").append(prop).append(") LIKE ?");
				params.add('%' + subname.toLowerCase() + '%');
				if (it.hasNext()) query.append(" or ");
			}
			if (hasAny(exactMatchProperties)) {
				query.append(" or ");
			}
		}
		if (hasAny(exactMatchProperties)) {
			for (Iterator<String> it = exactMatchProperties.iterator(); it.hasNext();) {
				String prop = it.next();
				query.append("LOWER(o.").append(prop).append(") = ?");
				params.add(subname.toLowerCase());
				if (it.hasNext()) query.append(" or ");
			}
		}
		query.append(')');
	}

	public void evict(Object domainObject){
		getHibernateTemplate().evict(domainObject);
	}
	
	private boolean hasAny(List<String> properties) {
		return properties != null && properties.size() > 0;
	}

}