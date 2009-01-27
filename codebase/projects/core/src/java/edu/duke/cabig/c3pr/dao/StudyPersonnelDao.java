package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.emory.mathcs.backport.java.util.Collections;

/**
 * The Class StudyPersonnelDao.
 * 
 * @author Priyatam
 */
public class StudyPersonnelDao extends GridIdentifiableDao<StudyPersonnel> {

    /** The log. */
    private static Log log = LogFactory.getLog(StudyPersonnelDao.class);

    /** The Constant SUBSTRING_MATCH_PROPERTIES. */
    private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList(
                    "researchStaff.firstName", "researchStaff.lastName");

    /** The Constant EXACT_MATCH_PROPERTIES. */
    private static final List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    /** The Constant EXTRA_PARAMS. */
    private static final List<Object> EXTRA_PARAMS = Collections.emptyList();

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    public Class<StudyPersonnel> domainClass() {
        return StudyPersonnel.class;
    }

    /**
	 * Gets study personnel by subnames.
	 * 
	 * @param subnames
	 *            the subnames
	 * @param healthcareSiteId
	 *            the healthcare site id
	 * 
	 * @return the by subnames
	 */
    public List<StudyPersonnel> getBySubnames(String[] subnames, int healthcareSiteId) {
        return findBySubname(subnames, "o.studyOrganization.healthcareSite.id = '" + healthcareSiteId + "'",
                        EXTRA_PARAMS, SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }
}