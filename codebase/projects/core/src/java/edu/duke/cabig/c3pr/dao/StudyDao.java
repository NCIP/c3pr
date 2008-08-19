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
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.emory.mathcs.backport.java.util.Collections;
import edu.nwu.bioinformatics.commons.CollectionUtils;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

/**
 * Hibernate implementation of StudyDao
 * 
 * @author Priyatam
 */

public class StudyDao extends GridIdentifiableDao<Study> implements MutableDomainObjectDao<Study> {

    private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("shortTitleText");

    private static final List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    private static Log log = LogFactory.getLog(StudyDao.class);
    
    private EpochDao epochDao;

    public void detach(Study study) {
        getHibernateTemplate().evict(study);
    }

    public void clear() {
        getHibernateTemplate().clear();
    }

    @Override
    public Class<Study> domainClass() {
        return Study.class;
    }

    public Study getStudyDesignById(int id) {
        return (Study) getHibernateTemplate().get(domainClass(), id);
    }

    @SuppressWarnings("unchecked")
    public Study getByIdentifier(SystemAssignedIdentifier identifier) {
        Criteria criteria = getSession().createCriteria(domainClass());
        criteria = criteria.createCriteria("identifiers");

        if (identifier.getType() != null) {
            criteria.add(Restrictions.eq("type", identifier.getType()));
        }

        if (identifier.getSystemName() != null) {
            criteria.add(Restrictions.eq("systemName", identifier.getSystemName()));
        }

        if (identifier.getValue() != null) {
            criteria.add(Restrictions.eq("value", identifier.getValue()));
        }
        return (Study) CollectionUtils.firstElement(criteria.list());
    }

    @SuppressWarnings("unchecked")
    public List<Study> searchByOrgIdentifier(OrganizationAssignedIdentifier id) {
        return (List<Study>) getHibernateTemplate()
                        .find(
                                        "select S from Study S, Identifier I where I.healthcareSite.id=?"
                                                        + " and I.value=? and I.type=? and I=any elements(S.identifiers)",
                                        new Object[] { id.getHealthcareSite().getId(),
                                                id.getValue(), id.getType() });
    }

    @Transactional(readOnly = false)
    public Study merge(Study study) {
        return (Study) getHibernateTemplate().merge(study);
    }

    @Transactional(readOnly = false)
    public void save(Study study) {
        getHibernateTemplate().saveOrUpdate(study);
    }

    @Transactional(readOnly = false)
    public void initialize(Study study) throws DataAccessException {
    	getHibernateTemplate().initialize(study.getEpochs());
		for (Epoch epoch : study.getEpochs()) {
			if (epoch != null) {
				getHibernateTemplate().initialize(epoch.getArmsInternal());
				getHibernateTemplate().initialize(
						epoch.getExclusionEligibilityCriteriaInternal());
				getHibernateTemplate().initialize(
						epoch.getInclusionEligibilityCriteriaInternal());
				getHibernateTemplate().initialize(
						epoch.getStratificationCriteriaInternal());
				for (StratificationCriterion stratficationCriterion : epoch
						.getStratificationCriteriaInternal()) {
					if (stratficationCriterion != null) {
						getHibernateTemplate().initialize(
								stratficationCriterion.getPermissibleAnswersInternal());
					}
				}
				getHibernateTemplate().initialize(epoch.getStratumGroupsInternal());
				for (StratumGroup stratumGroup : epoch.getStratumGroupsInternal()) {

					if (stratumGroup != null) {
						getHibernateTemplate().initialize(
								stratumGroup.getBookRandomizationEntryInternal());
						getHibernateTemplate().initialize(
								stratumGroup.getStratificationCriterionAnswerCombinationInternal());
					}
				}
			}
		}
		getHibernateTemplate().initialize(study.getStudyAmendmentsInternal());
		getHibernateTemplate().initialize(study.getStudyDiseases());
		getHibernateTemplate().initialize(study.getStudyOrganizations());
		getHibernateTemplate().initialize(study.getIdentifiers());
		getHibernateTemplate().initialize(study.getPlannedNotificationsInternal());
		getHibernateTemplate().initialize(study.getParentStudyAssociations());
		getHibernateTemplate().initialize(study.getCompanionStudyAssociationsInternal());
		for (PlannedNotification plannedNotification : study.getPlannedNotificationsInternal()) {
			if (plannedNotification != null) {
				getHibernateTemplate().initialize(plannedNotification.getUserBasedRecipientInternal());
				getHibernateTemplate().initialize(plannedNotification.getRoleBasedRecipientInternal());
			}
		}

		for (StudyOrganization studyOrganization : study.getStudyOrganizations()) {
			if (studyOrganization != null) {
				getHibernateTemplate()
				.initialize(studyOrganization.getStudyInvestigatorsInternal());
				getHibernateTemplate().initialize(studyOrganization.getStudyPersonnelInternal());
			}
		}
	}
    public List<Study> getBySubnames(String[] subnames) {
        return findBySubname(subnames, SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }

    /*
     * Searches based on an example object. Typical usage from your service class: - If you want to
     * search based on diseaseCode, monitorCode, <li><code>Study study = new Study();</li></code>
     * <li>code>study.setDiseaseCode("diseaseCode");</li></code> <li>code>study.setDMonitorCode("monitorCode");</li></code>
     * <li>code>studyDao.searchByExample(study)</li></code> @return list of matching study
     * objects based on your sample study object
     */

    public List<Study> searchByExample(Study study, boolean isWildCard) {
        return searchByExample(study, isWildCard, 0);
    }

    public List<Study> searchByExample(Study study, boolean isWildCard, int maxResults,
                    String order, String orderBy) {
        List<Study> result = new ArrayList<Study>();

        Example example = Example.create(study).excludeZeroes().ignoreCase();
        try {
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
        }
        catch (DataAccessResourceFailureException e) {
            log.error(e.getMessage());
        }
        catch (IllegalStateException e) {
            e.printStackTrace(); // To change body of catch statement use File | Settings | File
            // Templates.
        }
        catch (HibernateException e) {
            log.error(e.getMessage());
        }
        return result;

    }

    public List<Study> searchByExample(Study study, boolean isWildCard, int maxResults) {
        return searchByExample(study, isWildCard, maxResults, "ascending", "shortTitleText");
    }

    public List<Study> searchByExample(Study study, String searchText, boolean isWildCard) {

        List<Study> result = new ArrayList<Study>();
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

    public List<OrganizationAssignedIdentifier> getCoordinatingCenterIdentifiersWithValue(
                    String coordinatingCetnerIdentifierValue, HealthcareSite site)
                    throws DataAccessException {
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

    public List<OrganizationAssignedIdentifier> getFundingSponsorIdentifiersWithValue(
                    String fundingSponsorIdentifierValue, HealthcareSite site)
                    throws DataAccessException {
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
     * Default Search without a Wildchar
     * 
     * @param study
     * @return Search Results
     */
    public List<Study> searchByExample(Study study) {
        return searchByExample(study, false, 0);
    }

    public List<Study> searchByExample(Study study, int maxResults) {
        return searchByExample(study, false, maxResults);
    }

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
     * Returns all study objects
     * 
     * @return list of study objects
     */
    public List<Study> getAll() {
        return getHibernateTemplate().find("from Study");
    }

    /**
     * Get all Arms associated with all of this study's epochs
     * 
     * @param studyId
     *                the study id
     * @return list of Arm objects given a study id
     */
    public List<Arm> getArmsForStudy(Integer studyId) {
        return getHibernateTemplate().find(
                        "select a from Study s join s.epochs e join e.arms a " + "where s.id = ?",
                        studyId);
    }

    /**
     * Get all Assignments associated with the given study
     * 
     * @param studyId
     *                the study id
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
    public List<StudyDisease> getByDiseaseTermId(Integer dTermId) {
        return getHibernateTemplate().find("from StudyDisease sd where sd.diseaseTerm.id = ?",
                        dTermId);
    }

    @Transactional(readOnly = false)
    public void reassociate(Study s) {
        getHibernateTemplate().update(s);
    }

    @Transactional(readOnly = false)
    public void refresh(Study s) {
        getHibernateTemplate().refresh(s);
    }

    public List<Study> getByIdentifiers(List<Identifier> studyIdentifiers) {
        List<Study> studies = new ArrayList<Study>();
        for (Identifier identifier : studyIdentifiers) {
            if (identifier instanceof SystemAssignedIdentifier) studies
                            .add(getByIdentifier((SystemAssignedIdentifier) identifier));
            else if (identifier instanceof OrganizationAssignedIdentifier) studies
                            .addAll(searchByOrgIdentifier((OrganizationAssignedIdentifier) identifier));
        }
        Set<Study> set = new LinkedHashSet<Study>();
        set.addAll(studies);
        return new ArrayList<Study>(set);
    }

    public void setEpochDao(EpochDao epochDao) {
        this.epochDao = epochDao;
    }
}
