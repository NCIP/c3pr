package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import edu.emory.mathcs.backport.java.util.Collections;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSessionFactory;
import gov.nih.nci.cabig.ctms.suite.authorization.ScopeType;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;

/**
 * The Class StudyPersonnelDao.
 * 
 * @author Vinay G
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
    

	/** The provisioning session factory. This is from Suite Authorization Project for the unified roles*/
	private ProvisioningSessionFactory provisioningSessionFactory;
	
	/** The user provisioning manager. */
	private UserProvisioningManager userProvisioningManager;
	
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
	 * @param healthcareSiteId
	 * @return the by subnames
	 */
    public List<StudyPersonnel> getBySubnames(String[] subnames, int healthcareSiteId) {
        return findBySubname(subnames, "o.studyOrganization.healthcareSite.id = '" + healthcareSiteId + "'",
                        EXTRA_PARAMS, SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }
    
    /**
     * Save or update study personnel.
     * Also updates the study PG/PE's for the user.
     *
     * @param studyPersonnel the study personnel
     */
    public void saveOrUpdateStudyPersonnel(StudyPersonnel studyPersonnel, boolean isRemove){
    	try {
			assignUsersToStudy(studyPersonnel, isRemove);
		} catch (C3PRBaseException e) {
			log.error(e.getMessage());
		}
    	this.save(studyPersonnel);
    }
  

	/**
	 * Assign users to the associated Study protection groups.
	 * Since CCTS2.2: Uses ProvisioningSession to create the protection groups and attach them to the user.
	 * This only takes the unified groups in the groupList parameter. So it shouldn't contain C3PR specific roles like C3PR_admin.
	 *
	 * @param studyPersonnel the study personnel
	 * @throws C3PRBaseException the c3pr base exception
	 */
	private void assignUsersToStudy(StudyPersonnel studyPersonnel, boolean isRemove) throws C3PRBaseException {
		//provisioning as per the new suite authorization -start
		ResearchStaff researchStaff = studyPersonnel.getResearchStaff();
		try {
			gov.nih.nci.security.authorization.domainobjects.User csmUser = 
					userProvisioningManager.getUserById(researchStaff.getLoginId());
			Set groupSet = userProvisioningManager.getGroups(researchStaff.getLoginId());
			ProvisioningSession provisioningSession = 
					provisioningSessionFactory.createSession(csmUser.getUserId());
			
			
			if(isRemove){
				removeStudies(studyPersonnel, provisioningSession, groupSet);
			} else {
				setStudies(studyPersonnel, provisioningSession, groupSet);
			}
		} catch (CSObjectNotFoundException e) {
			log.error(e.getMessage());		
		}
		//provisioning as per the new suite authorization - end
	}
	
	/**
	 * Adds the studies as PG and associates them to the user provided the role is scoped by study.
	 *
	 * @param suiteRoleMembership the suite role membership
	 * @param healthcareSite the healthcare site
	 */
	private void setStudies(StudyPersonnel studyPersonnel,
			ProvisioningSession provisioningSession, Set<Group> groups) {
		
		Iterator<Group> iter = groups.iterator();
		SuiteRoleMembership suiteRoleMembership;
		SuiteRole suiteRole;
		C3PRUserGroupType group;
		while(iter.hasNext()){
			group = C3PRUserGroupType.getByCode(((Group)iter.next()).getGroupName());
			suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(group);
			Study study = studyPersonnel.getStudyOrganization().getStudy();
			if(suiteRole != null && suiteRole.getScopes().contains(ScopeType.STUDY)){
				suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
				suiteRoleMembership.addStudy(study.getCoordinatingCenterAssignedIdentifier().getValue());
				provisioningSession.replaceRole(suiteRoleMembership);
			}			
		}
	}


	/**
	 * Removes the studies PG/PE's associated with the user in CSM.
	 *
	 * @param suiteRoleMembership the suite role membership
	 * @param studyPersonnel the study personnel
	 */
	private void removeStudies(StudyPersonnel studyPersonnel, ProvisioningSession provisioningSession, Set<Group> groups) {
		Iterator<Group> iter = groups.iterator();
		SuiteRole suiteRole;
		SuiteRoleMembership suiteRoleMembership;
		Study study;
		while(iter.hasNext()){
			suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(C3PRUserGroupType.getByCode(iter.next().getGroupName()));
			study = studyPersonnel.getStudyOrganization().getStudy();
			suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
			if(suiteRole != null && suiteRole.getScopes().contains(ScopeType.STUDY) &&
					suiteRoleMembership.getStudyIdentifiers().contains(study.getCoordinatingCenterAssignedIdentifier().getValue())){
				suiteRoleMembership.removeStudy(study.getCoordinatingCenterAssignedIdentifier().getValue());
				provisioningSession.replaceRole(suiteRoleMembership);
			}			
		}	
	}
	
	
	public void setProvisioningSessionFactory(
			ProvisioningSessionFactory provisioningSessionFactory) {
		this.provisioningSessionFactory = provisioningSessionFactory;
	}

	public void setUserProvisioningManager(
			UserProvisioningManager userProvisioningManager) {
		this.userProvisioningManager = userProvisioningManager;
	}
    
}