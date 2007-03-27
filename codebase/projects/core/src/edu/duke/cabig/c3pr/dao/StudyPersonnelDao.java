package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.emory.mathcs.backport.java.util.Collections;

/**
 * @author Priyatam
 */
public class StudyPersonnelDao extends AbstractBaseDao<StudyPersonnel> {
	
	private static Log log = LogFactory.getLog(StudyPersonnelDao.class);
	
	private static final List<String> SUBSTRING_MATCH_PROPERTIES
		= Arrays.asList("researchStaff.firstName", "researchStaff.lastName");
	private static final List<String> EXACT_MATCH_PROPERTIES
		= Collections.emptyList();
	private static final List<Object> EXTRA_PARAMS
		= Collections.emptyList();
	
	public Class<StudyPersonnel> domainClass() {
	        return StudyPersonnel.class;
	 }
	
	/*
	 * Returns all HealthcarSite objects
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.HealthcareSiteDao#getAll()
	 */
	 public List<HealthcareSite> getAll() {
		 return getHibernateTemplate().find("from HealthcareSiteInvestigator");
	 }

	 public List<StudyPersonnel> getBySubnames(String[] subnames, int healthcareSite) {
	        return findBySubname(subnames,"o.studySite.site.id = '"+healthcareSite+"'",EXTRA_PARAMS,
	            SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
	 }	 				
}