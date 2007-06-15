package edu.duke.cabig.c3pr.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.domain.StudyInvestigator;

/**
 * @author Priyatam
 */
public class StudyInvestigatorDao extends GridIdentifiableDao<StudyInvestigator> {
	
	private static Log log = LogFactory.getLog(StudyInvestigatorDao.class);
	
	public Class<StudyInvestigator> domainClass() {
	        return StudyInvestigator.class;
	 }
}