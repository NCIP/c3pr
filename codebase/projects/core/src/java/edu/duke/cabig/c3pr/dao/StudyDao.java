package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.ContactMechanismBasedRecipient;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.emory.mathcs.backport.java.util.Collections;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

// TODO: Auto-generated Javadoc
/**
 * Hibernate implementation of StudyDao.
 * 
 * @author Priyatam
 */

public class StudyDao extends GridIdentifiableDao<Study> implements MutableDomainObjectDao<Study> {

    /** The Constant SUBSTRING_MATCH_PROPERTIES. */
    private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("shortTitleText");

    /** The Constant EXACT_MATCH_PROPERTIES. */
    private static final List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

	 /** The Constant extraParameters. */
    private static final List EXTRA_PARAMETERS = Arrays.asList("%center%");
    
    /** The log. */
    private static Log log = LogFactory.getLog(StudyDao.class);
    
    /** The epoch dao. */
    private EpochDao epochDao;

    /**
     * Clears the Hibernate Session.
     */
    public void clear() {
        getHibernateTemplate().clear();
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    @Override
    public Class<Study> domainClass() {
        return Study.class;
    }

    /**
     * Search by system identifier.
     * 
     * @param id the id
     * 
     * @return the list< study>
     */
    @SuppressWarnings("unchecked")
    public List<Study> searchBySysIdentifier(SystemAssignedIdentifier id) {
        return (List<Study>) getHibernateTemplate()
                        .find(
                                        "select S from Study S, Identifier I where I.systemName=?"
                                                        + " and I.value=? and I.type=? and I=any elements(S.identifiers)",
                                        new Object[] { id.getSystemName(),
                                                id.getValue(), id.getType() });
    }

    /**
     * Search by organization identifier.
     * 
     * @param id the id
     * 
     * @return the list< study>
     */
    @SuppressWarnings("unchecked")
    public List<Study> searchByOrgIdentifier(OrganizationAssignedIdentifier id) {
        return (List<Study>) getHibernateTemplate()
                        .find(
                                        "select S from Study S, Identifier I where I.healthcareSite.nciInstituteCode=?"
                                                        + " and I.value=? and I.type=? and I=any elements(S.identifiers)",
                                        new Object[] { id.getHealthcareSite().getNciInstituteCode(),
                                                id.getValue(), id.getType() });
    }

    /**
     * Merges the study with the persistence store
     * 
     * @param study the study
     * 
     * @return the study
     */
    @Transactional(readOnly = false)
    public Study merge(Study study) {
    	return (Study) getHibernateTemplate().merge(study);
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao#save(gov.nih.nci.cabig.ctms.domain.MutableDomainObject)
     */
    @Transactional(readOnly = false)
    public void save(Study study) {
        getHibernateTemplate().saveOrUpdate(study);
    }
    

    /**
     * Initializes the study.
     * 
     * @param study the study
     * 
     */
    @Transactional(readOnly = false)
    public void initialize(Study study) 	{
    	getHibernateTemplate().initialize(study.getEpochsInternal());
		for (Epoch epoch : study.getEpochsInternal()) {
			if (epoch != null) {
				epochDao.initialize(epoch);
			}
		}
    	getHibernateTemplate().initialize(study.getStudyAmendmentsInternal());
		getHibernateTemplate().initialize(study.getStudyDiseases());
		getHibernateTemplate().initialize(study.getStudyOrganizations());
		getHibernateTemplate().initialize(study.getIdentifiers());
		getHibernateTemplate().initialize(study.getPlannedNotificationsInternal());
		getHibernateTemplate().initialize(study.getParentStudyAssociations());
		getHibernateTemplate().initialize(study.getCompanionStudyAssociationsInternal());
		for (CompanionStudyAssociation companionStudyAssociation : study.getCompanionStudyAssociations()) {
			this.initialize(companionStudyAssociation.getCompanionStudy());
		}
		for(CompanionStudyAssociation parentStudyAssociation : study.getParentStudyAssociations()){
			getHibernateTemplate().initialize(parentStudyAssociation.getStudySites());
			getHibernateTemplate().initialize(parentStudyAssociation.getParentStudy().getStudyOrganizations());
		}
		
		for(CompanionStudyAssociation companionStudyAssociation : study.getCompanionStudyAssociations()){
			getHibernateTemplate().initialize(companionStudyAssociation.getStudySites());
		}
		
		for(CompanionStudyAssociation parentStudyAssociation : study.getParentStudyAssociations()){
			getHibernateTemplate().initialize(parentStudyAssociation.getParentStudy().getStudyOrganizations());
		}
		
		for(CompanionStudyAssociation companionStudyAssociation : study.getCompanionStudyAssociations()){
			getHibernateTemplate().initialize(companionStudyAssociation.getCompanionStudy().getStudyOrganizations());
		}
		
		for (PlannedNotification plannedNotification : study.getPlannedNotificationsInternal()) {
			if (plannedNotification != null) {
				getHibernateTemplate().initialize(plannedNotification.getUserBasedRecipientInternal());
				getHibernateTemplate().initialize(plannedNotification.getRoleBasedRecipientInternal());
				getHibernateTemplate().initialize(plannedNotification.getContactMechanismBasedRecipientInternal());
			}
			for(ContactMechanismBasedRecipient cmbr: plannedNotification.getContactMechanismBasedRecipient()){
				getHibernateTemplate().initialize(cmbr.getContactMechanismsInternal());
			}
		}
		
		
		for (StudyOrganization studyOrganization : study.getStudyOrganizations()) {
			if (studyOrganization != null) {
				getHibernateTemplate().initialize(studyOrganization.getStudyInvestigatorsInternal());
				getHibernateTemplate().initialize(studyOrganization.getStudyPersonnelInternal());
				getHibernateTemplate().initialize(studyOrganization.getEndpoints());
			}
		}
		
	}
    
    /**
     * Gets the study by subnames.
     * 
     * @param subnames the subnames
     * 
     * @return List of studies
     */
    public List<Study> getBySubnames(String[] subnames) {
        return findBySubname(subnames, SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }

    
    /**
     * Gets the study by comapring subnames against short title and coordinating center identifiers.
     * 
     * @param subnames the enetered string. used by comapnion study autocompleter
     * 
     * @return List of studies
     */
    public List<Study> getStudiesBySubnamesWithExtraConditionsForPrimaryIdentifier(String[] subnames) {
    	
    	List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("shortTitleText","identifiers.value");
    	
    	List<Study> studies = findBySubname(subnames, "LOWER(o.identifiers.type) LIKE ? ", EXTRA_PARAMETERS, SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    	for(Study study: studies){
    		getHibernateTemplate().initialize(study.getIdentifiers());
    	}
    	//remove duplicates if any
    	Set setItems = new LinkedHashSet(studies);
    	studies.clear();
    	studies.addAll(setItems); 
    	
    	return studies;
    }
    
    
    /*
     * Searches based on an example object. Typical usage from your service class: - If you want to
     * search based on diseaseCode, monitorCode, <li><code>Study study = new Study();</li></code>
     * <li>code>study.setDiseaseCode("diseaseCode");</li></code> <li>code>study.setDMonitorCode("monitorCode");</li></code>
     * <li>code>studyDao.searchByExample(study)</li></code> @return list of matching study
     * objects based on your sample study object
     */

    /**
     * Searches by example.
     * 
     * @param study the example study
     * @param isWildCard the is wild card
     * 
     * @return the list< study>
     */
    public List<Study> searchByExample(Study study, boolean isWildCard) {
        return searchByExample(study, isWildCard, 0);
    }

    /**
     * Search by example.
     * 
     * @param study the exmple study
     * @param isWildCard the wild card
     * @param maxResults the max results
     * @param order the order
     * @param orderBy the order by
     * 
     * @return the list< study>
     */
    public List<Study> searchByExample(Study study, boolean isWildCard, int maxResults,
                    String order, String orderBy) {
        List<Study> result = new ArrayList<Study>();

        Example example = Example.create(study).excludeZeroes().ignoreCase();
        Criteria studyCriteria = getSession().createCriteria(Study.class);

        if ("ascending".equals(order)) {
            studyCriteria.addOrder(Order.asc(orderBy));
        }
        else if ("descending".equals(order)) {
            studyCriteria.addOrder(Order.desc(orderBy));
        }

        studyCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        if (maxResults > 0) studyCriteria.setMaxResults(maxResults);

        if (isWildCard) {
            example.excludeProperty("doNotUse").enableLike(MatchMode.ANYWHERE);
            studyCriteria.add(example);
            if (study.getIdentifiers().size() > 1) {
                studyCriteria.createCriteria("identifiers").add(
                                Restrictions.ilike("value", "%"
                                                + study.getIdentifiers().get(0).getValue()
                                                + "%")).add(
                                Restrictions.ilike("value", "%"
                                                + study.getIdentifiers().get(1).getValue()
                                                + "%"));
            }
            else if (study.getIdentifiers().size() > 0) {
                studyCriteria.createCriteria("identifiers").add(
                                Restrictions.ilike("value", "%"
                                                + study.getIdentifiers().get(0).getValue()
                                                + "%"));
            }
            result = studyCriteria.list();
        }
        result = studyCriteria.add(example).list();
        return result;

    }

    /**
     * Search by example.
     * 
     * @param study the study
     * @param isWildCard the wild card
     * @param maxResults the max results
     * 
     * @return the list< study>
     */
    public List<Study> searchByExample(Study study, boolean isWildCard, int maxResults) {
        return searchByExample(study, isWildCard, maxResults, "ascending", "shortTitleText");
    }

    /**
     * Search by example.
     * 
     * @param study the study
     * @param searchText the search text
     * @param isWildCard the wild card
     * 
     * @return the list< study>
     */
    public List<Study> searchByStatus(Study study, String searchText, boolean isWildCard) {

        List<Study> result = new ArrayList<Study>();
        	searchText = searchText.replace(" ", "_");
        if (isWildCard) {
            result = (List<Study>) getHibernateTemplate().find(
                            "from Study where status like '%" + searchText.toUpperCase() + "%'");
        }
        else {
            result = (List<Study>) getHibernateTemplate().find("from Study where status like '?",
                            searchText.toUpperCase());
        }
        return result;
    }

    /**
     * Gets the coordinating center identifiers with the given value.
     * 
     * @param coordinatingCetnerIdentifierValue the coordinating center identifier value
     * @param site the site
     * 
     * @return the coordinating center identifiers
     * 
     */
    public List<OrganizationAssignedIdentifier> getCoordinatingCenterIdentifiersWithValue(
                    String coordinatingCetnerIdentifierValue, HealthcareSite site) {
        List<OrganizationAssignedIdentifier> orgAssignedIdentifiers = (List<OrganizationAssignedIdentifier>) getHibernateTemplate()
                        .find(
                                        "from Identifier I where I.type='Coordinating Center Identifier' and I.healthcareSite = ?",
                                        site);
        List<OrganizationAssignedIdentifier> ccIdentifiers = new ArrayList<OrganizationAssignedIdentifier>();
        for (OrganizationAssignedIdentifier studyIdent : orgAssignedIdentifiers) {
            if (studyIdent.getValue().equalsIgnoreCase(coordinatingCetnerIdentifierValue)) {
                ccIdentifiers.add(studyIdent);
            }
        }
        return ccIdentifiers;
    }

    /**
     * Gets the funding sponsor identifiers with given value.
     * 
     * @param fundingSponsorIdentifierValue the funding sponsor identifier value
     * @param site the site
     * 
     * @return the funding sponsor identifiers
     * 
     */
    public List<OrganizationAssignedIdentifier> getFundingSponsorIdentifiersWithValue(
                    String fundingSponsorIdentifierValue, HealthcareSite site) {
        List<OrganizationAssignedIdentifier> orgAssignedIdentifiers = (List<OrganizationAssignedIdentifier>) getHibernateTemplate()
                        .find(
                                        "from Identifier I where I.type='Protocol Authority Identifier' and I.healthcareSite = ?",
                                        site);
        List<OrganizationAssignedIdentifier> fsIdentifiers = new ArrayList<OrganizationAssignedIdentifier>();
        for (OrganizationAssignedIdentifier subIdent : orgAssignedIdentifiers) {
            if (subIdent.getValue().equalsIgnoreCase(fundingSponsorIdentifierValue)) {
                fsIdentifiers.add(subIdent);
            }
        }
        return fsIdentifiers;
    }

    /**
     * Default Search without a Wildchar.
     * 
     * @param study the study
     * 
     * @return Search Results
     */
    public List<Study> searchByExample(Study study) {
        return searchByExample(study, false, 0);
    }

    /**
     * Counts acrruals by date.
     * 
     * @param study the study
     * @param startDate the start date
     * @param endDate the end date
     * 
     * @return the accural
     */
    public int countAcrrualsByDate(Study study, Date startDate, Date endDate) {
        Criteria regCriteria = getHibernateTemplate().getSessionFactory().getCurrentSession()
                        .createCriteria(StudySubject.class);
        Criteria studySiteCriteria = regCriteria.createCriteria("studySite");
        Criteria studyCriteria = studySiteCriteria.createCriteria("study");

        regCriteria.add(Expression.between("startDate", startDate, endDate));
        studyCriteria.add(Restrictions.eq("id", study.getId()));

        return regCriteria.list().size();
    }

    /**
     * Returns all study objects.
     * 
     * @return list of study objects
     */
    public List<Study> getAll() {
        return getHibernateTemplate().find("from Study");
    }

    /**
     * Get all Assignments associated with the given study.
     * 
     * @param studyId the study id
     * 
     * @return list of StudySubjects
     */
    public List<StudySubject> getStudySubjectsForStudy(Integer studyId) {
        return getHibernateTemplate().find(
                        "select a from Study s join s.studyOrganizations so "
                                        + "join so.studySubjects a where s.id = ? ", studyId);
    }

    /*
     * Primarily created for Generating test reports.
     */
    /**
     * Gets the study diseases by disease term id.
     * 
     * @param dTermId the disease term id
     * 
     * @return list study diseases
     */
    public List<StudyDisease> getByDiseaseTermId(Integer dTermId) {
        return getHibernateTemplate().find("from StudyDisease sd where sd.diseaseTerm.id = ?",
                        dTermId);
    }

    /**
     * Reassociate.
     * 
     * @param s the s
     */
    @Transactional(readOnly = false)
    public void reassociate(Study s) {
        getHibernateTemplate().update(s);
    }

    /**
     * Refresh.
     * 
     * @param s the s
     */
    @Transactional(readOnly = false)
    public void refresh(Study s) {
        getHibernateTemplate().refresh(s);
    }

    /**
     * Gets the studies by identifiers.
     * 
     * @param studyIdentifiers the study identifiers
     * 
     * @return List of studies
     */
    public List<Study> getByIdentifiers(List<Identifier> studyIdentifiers) {
        List<Study> studies = new ArrayList<Study>();
        for (Identifier identifier : studyIdentifiers) {
            if (identifier instanceof SystemAssignedIdentifier) studies
                            .addAll(searchBySysIdentifier((SystemAssignedIdentifier) identifier));
            else if (identifier instanceof OrganizationAssignedIdentifier) studies
                            .addAll(searchByOrgIdentifier((OrganizationAssignedIdentifier) identifier));
        }
        Set<Study> set = new LinkedHashSet<Study>();
        set.addAll(studies);
        return new ArrayList<Study>(set);
    }

    /**
     * Sets the epoch dao.
     * 
     * @param epochDao the new epoch dao
     */
    public void setEpochDao(EpochDao epochDao) {
        this.epochDao = epochDao;
    }
    
    /**
     * Search by identifier.
     * 
     * @param id the identifier
     * 
     * @return the list< study>
     */
    @SuppressWarnings("unchecked")
    public List<Study> searchByIdentifier(int id) {
        return (List<Study>) getHibernateTemplate().find(
                                        "select S from Study S, Identifier I where I.id=? and I=any elements(S.identifiers)",
                                        new Object[] {id});
    }
    
    
}
