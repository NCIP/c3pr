package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.domain.Investigator;
import edu.emory.mathcs.backport.java.util.Collections;

/**
 * @author Priyatam
 */
public class InvestigatorDao extends AbstractBaseDao<Investigator> {

	private static final List<String> SUBSTRING_MATCH_PROPERTIES
		= Arrays.asList("firstName", "lastName");
	private static final List<String> EXACT_MATCH_PROPERTIES
		= Collections.emptyList();

	public Class<Investigator> domainClass() {
		return Investigator.class;
	}

	/**
	 * Get All Investigators
	 * @return
	 * @throws DataAccessException
	 */
	public List<Investigator> getAll() throws DataAccessException {
		return getHibernateTemplate().find("from Investigator");
	}
	
	public List<Investigator> getBySubnames(String[] subnames) {
        return findBySubname(subnames,
            SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
	}
}
